/*
 * Copyright (c) 2011 - 2015, Apinauten GmbH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this 
 *    list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * THIS FILE IS GENERATED AUTOMATICALLY. DON'T MODIFY IT.
 */
package com.apiomat.frontend.offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class SQLiteStorage extends AbstractStorage
{

    /* Attributes */

	private static OfflineDbHelper offlineDbHelper;
	private static SQLiteStorage instance;
	private static SQLiteDatabase db;

	/* 2048 KB according to some config file, but hardware vendors can change this,
	 * and some metadata or checksum gets added, + we want to be on the safe side.
	 * value could be int, but is used later where longs are used, so already declare as long here.
	 *
	 * Could be final, but needs to get set in tests via reflection and that's not reliable with final fields
	 */
	private static long MAX_COLUMN_SIZE = 1 * 1000 * 1000;
		// In tests the limit was exactly (2 * 1024 * 1024 - 433) = 2096719

    /*
	static
    {
        byte[] temp = new byte[MAX_COLUMN_SIZE];
        new Random().nextBytes(temp);
        MAX_COLUMN_SIZE_ACTUAL = binaryToString(temp).length();
    }
    */

    /* Constructors */

	private SQLiteStorage( Context context )
	{
		super( );
		offlineDbHelper = new OfflineDbHelper( context );

		db = offlineDbHelper.getWritableDatabase( );
	}

    /* Methods */

	public static SQLiteStorage getInstance( Context context )
	{
		if ( instance == null )
		{
			instance = new SQLiteStorage( context );
		}
		return instance;
	}

	/**
	 * Stores an object to the DB or updates it if it already exists
	 *
	 * @param href       The HREF of the object. If a url with query parameters gets passed, it will be cut off.
	 * @param json       The object as json
	 * @param httpMethod e.g. "GET", "PUT"
	 * @return Returns true if storing the object was successfull. False otherwise.
	 */
	@Override
	public boolean storeObject( String href, String json, String httpMethod )
	{
		href = cutOffParameters( href );
		String hashedRequestUrl = hash( href );

		long lastUpdated = new Date( ).getTime( );

		ContentValues values = new ContentValues( );
		values.put( SQLiteContract.ObjectStorage._ID, hashedRequestUrl );
		values.put( SQLiteContract.ObjectStorage.COLUMN_NAME_HREF, href );
		values.put( SQLiteContract.ObjectStorage.COLUMN_NAME_LAST_UPDATE, lastUpdated );
		values.put( SQLiteContract.ObjectStorage.COLUMN_NAME_BODY, json );
		values.put( SQLiteContract.ObjectStorage.COLUMN_NAME_HTTP_METHOD, httpMethod );

		return dbInsertReplace( SQLiteContract.ObjectStorage.TABLE_NAME, values );
	}

	/* Stores a collection of objects to the DB or updates it if it already exists */
	@Override
	public boolean storeCollection( String requestUrl, String json )
	{
		String hashedRequestUrl = hash( requestUrl );
		/* Store the objects that are in the collection on their own,
         * and create a mapping entry for each one */
		try
		{
			JSONArray collection = new JSONArray( json );
			for ( int i = 0; i < collection.length( ); i++ )
			{
				JSONObject object = ( JSONObject ) collection.get( i );
                /* Store the objects and at the same time, if one storage procedure fails, return false.
                 * Storing the objects takes care of insert vs. update.
                 * Speedup might be possible with beginTransaction/endTransaction or bulk insert */
				String objectHref = object.getString( "href" );
				objectHref = cutOffParameters( objectHref );
				String hashedObjectRequestUrl = hash( objectHref );
				if ( storeObject( objectHref, object.toString( ), "GET" ) == false )
				{
					return false;
				}
                /* mapping */
				if ( storeMapping( hashedRequestUrl, hashedObjectRequestUrl ) == false )
				{
					return false;
				}
			}
		}
		catch ( Exception e )
		{
			return false;
		}

        /* collection meta infos */
		long lastUpdated = new Date( ).getTime( );
		ContentValues values = new ContentValues( );
		values.put( SQLiteContract.CollectionStorage._ID, hashedRequestUrl );
		values.put( SQLiteContract.CollectionStorage.COLUMN_NAME_HREF, requestUrl );
		values.put( SQLiteContract.CollectionStorage.COLUMN_NAME_LAST_UPDATE, lastUpdated );

		return dbInsertReplace( SQLiteContract.CollectionStorage.TABLE_NAME, values );
	}

	/* Returns the JSON of a cached object. NULL if not found. */
	@Override
	public String getStoredObject( String href )
	{
		String result;

		href = cutOffParameters( href );
		String hashedHref = hash( href );

		long bodyLength =
			getColumnLength( SQLiteContract.ObjectStorage.TABLE_NAME, SQLiteContract.ObjectStorage.COLUMN_NAME_BODY,
				SQLiteContract.ObjectStorage._ID, hashedHref );
		boolean needsChunking = bodyLength > MAX_COLUMN_SIZE;
		if ( needsChunking )
		{
			long fromPos = 1;
			StringBuffer sb = new StringBuffer( );
			do
			{
				String chunk =
					selectChunk( SQLiteContract.ObjectStorage.TABLE_NAME, SQLiteContract.ObjectStorage.COLUMN_NAME_BODY,
						SQLiteContract.ObjectStorage._ID, hashedHref, fromPos );
                /* As soon as one chunk can't be fetched, we need to cancel the operation */
				if ( chunk == null )
				{
					break;
				}
				sb.append( chunk );
				fromPos = fromPos + MAX_COLUMN_SIZE;
			} while ( fromPos <= bodyLength );
            /* Even if previous operation was cancelled, empty sb would be empty string, but null needs to be returned */
			if ( sb.length( ) == 0 )
			{
				result = null;
			}
			else
			{
				result = sb.toString( );
			}
		}
		else // either below limit or length couldn't be determined (-1)
		{
			result =
				selectColumn( SQLiteContract.ObjectStorage.TABLE_NAME, SQLiteContract.ObjectStorage.COLUMN_NAME_BODY,
					SQLiteContract.ObjectStorage._ID, hashedHref );
		}

		return result;
	}

	private String selectChunk( String tableName, String columnName, String idColumnName, String id, long from )
	{
		String columnChunk = "substr(" + columnName + ", " + from + ", " + MAX_COLUMN_SIZE + ")";
		return selectColumn( tableName, columnChunk, idColumnName, id );
	}

	private String selectColumn( String tableName, String columnName, String idColumnName, String id )
	{
		String[] returnColumns = { columnName };

		String selection = idColumnName + " = " + "?";
		String[] selectionArgs = { id };

		Cursor cursor = db.query(
			tableName,
			returnColumns,
			selection,
			selectionArgs,
			null,
			null,
			null
		);

		if ( cursor.moveToFirst( ) == false )
		{
			return null;
		}
		else
		{
			return cursor.getString(
				cursor.getColumnIndex( columnName )
			);
		}
	}

	private long getColumnLength( String tableName, String columnName, String idColumnName, String id )
	{
		String bodyLengthColumnName = "length(" + columnName + ")";
		String[] returnColumns = { bodyLengthColumnName };

		String selection = idColumnName + " = " + "?";
		String[] selectionArgs = { id };

		Cursor cursor = db.query(
			tableName,
			returnColumns,
			selection,
			selectionArgs,
			null,
			null,
			null
		);

		if ( cursor.moveToFirst( ) == false )
		{
			return -1;
		}
		else
		{
			String lengthString = cursor.getString( cursor.getColumnIndex( bodyLengthColumnName ) );
			return Long.valueOf( lengthString );
		}
	}

	@Override
	public String getStoredCollection( String url )
	{
		String result = "[";
		StringBuilder sb = new StringBuilder( result );

        /* fetch all objects with a join of the collection with the mapping table */
		String mappingJoin = "SELECT objectstorage.body " +
			"from objectstorage " +
			"inner join " +
			"  (select * from collectionobjectmapping where fidcollection = ?) mapping " +
			"on objectstorage._id = mapping.fidobject;";

		Cursor cursor = db.rawQuery( mappingJoin, new String[] { hash( url ) } );

		if ( cursor.moveToFirst( ) == false )
		{
			return null;
		}
		else
		{
			while ( cursor.isAfterLast( ) == false )
			{
				sb.append( cursor.getString( 0 ) );
				sb.append( "," );
				cursor.moveToNext( );
			}
		}

		result = sb.toString( );
		result = result.substring( 0, result.length( ) - 1 ); // cut off last ","
		result += "]";

		return result;
	}

	@Override
	public boolean removeObject( String href )
	{
		String whereClause = SQLiteContract.ObjectStorage._ID + " = " + "?";
		String[] whereArgs = { hash( cutOffParameters( href ) ) };
		return db.delete( SQLiteContract.ObjectStorage.TABLE_NAME, whereClause, whereArgs ) == 1 ? true : false;
	}

	@Override
	public int removeCollection( final String url )
	{
		int result = 0;
		final String hashedUrl = hash( url );
		String[] whereArgs = { hashedUrl };

        /* delete all object hrefs that are in the result after a join of the collection with the mapping table
         * but ONLY delete the objects that are not part of another collection (count = 1).
         */
		String whereClause = "_id IN " +
		"  (SELECT filteredmappingfidobject " +
		"   FROM ( " +
		"     (select collectionobjectmapping.fidobject as filteredmappingfidobject from collectionobjectmapping where collectionobjectmapping.fidcollection = ? ) as filteredmapping " +
		"     inner join " +
		"      (select collectionobjectmapping.fidobject as countedmappingfidobject, count( collectionobjectmapping.fidobject ) as objectcount " +
		"      from collectionobjectmapping " +
		"      group by collectionobjectmapping.fidobject ) as countedmapping " +
		"     on filteredmappingfidobject = countedmappingfidobject " +
		"   ) WHERE objectcount = 1 )";
		result = db.delete( SQLiteContract.ObjectStorage.TABLE_NAME, whereClause, whereArgs );

		/* also delete mapping entries */

		whereClause = SQLiteContract.CollectionObjectMapping.COLUMN_NAME_FID_COLLECTION + " = " + "?";
		db.delete( SQLiteContract.CollectionObjectMapping.TABLE_NAME, whereClause, whereArgs );

		/* also delete collection entry */

		whereClause = SQLiteContract.CollectionStorage._ID + " = " + "?";
		db.delete( SQLiteContract.CollectionStorage.TABLE_NAME, whereClause, whereArgs );

		return result;
	}

	@Override
	public int clear( )
	{
		/* pass "1" as whereClause because otherwise, if no where clause is defined,
		 * delete() returns 0 independently from the number of affected rows
		 */
		final String whereClause = "1";
		final int result = db.delete( SQLiteContract.ObjectStorage.TABLE_NAME, whereClause, null );
		db.delete( SQLiteContract.CollectionStorage.TABLE_NAME, whereClause, null );
		db.delete( SQLiteContract.CollectionObjectMapping.TABLE_NAME, whereClause, null );
		return result;
	}

	public void close( )
	{
        /* db is offlineDbHelper.getWritableDatabase(); */
		if ( db != null )
		{
			db.close( ); // is equivalent to calling db.releaseReference()
		}
		offlineDbHelper.close( );
	}

    /* Helper methods */

	/**
	 * Inserts or replaces a row. Is not an update - needs all values!
	 *
	 * @param tableName
	 * @param values
	 * @return
	 */
	private boolean dbInsertReplace( String tableName, ContentValues values )
	{
		long newRowId = db.insertWithOnConflict(
			tableName,
			"null",
			values,
			SQLiteDatabase.CONFLICT_REPLACE );

		return newRowId != -1;
	}

	private boolean dbInsert( String tableName, ContentValues values )
	{
		long newRowId = db.insert(
			tableName,
			"null",
			values );

		return newRowId != -1;
	}

	private boolean dbUpdate( String tableName, String idColumnName, String id, ContentValues values )
	{
		String whereClause = idColumnName + " = " + "?";
		String[] whereArgs = { id };

		int count = db.update(
			tableName,
			values,
			whereClause,
			whereArgs );
		return count == 1;
	}

	private boolean checkIfRecordExists( String tableName, String whereColumnName, String whereValue )
	{
		String[] returnColumns = { whereColumnName };

		String selection = whereColumnName + " = " + "?";
		String[] selectionArgs = { whereValue };

		Cursor cursor = db.query(
			tableName,
			returnColumns,
			selection,
			selectionArgs,
			null,
			null,
			null
		);

		if ( cursor.moveToFirst( ) == false )
		{
			cursor.close( );
			return false;
		}
		else
		{
			cursor.close( );
			return true;
		}
	}

	private boolean storeMapping( final String collectionUrlHash, final String objectUrlHash )
	{
		ContentValues values = new ContentValues( );
		values.put( SQLiteContract.CollectionObjectMapping.COLUMN_NAME_FID_COLLECTION, collectionUrlHash );
		values.put( SQLiteContract.CollectionObjectMapping.COLUMN_NAME_FID_OBJECT, objectUrlHash );

		return dbInsertReplace( SQLiteContract.CollectionObjectMapping.TABLE_NAME, values );
	}
}
