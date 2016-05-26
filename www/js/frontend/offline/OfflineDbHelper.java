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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OfflineDbHelper extends SQLiteOpenHelper
{
	/* When you change the database schema, you must increment the database version.
	 * 
	 * History:
	 * 1: Initial
	 * 2: AOM-1751
	 */
	private static final int DATABASE_VERSION = 2;

	/* No final, because setting final field via reflection (in tests) is unreliable */
	private static String DATABASE_NAME = "Offline.db";

	private static final String CREATE = " CREATE TABLE IF NOT EXISTS ";
	private static final String DROP = " DROP TABLE IF EXISTS ";
	private static final String TEXT_TYPE = " TEXT ";
	private static final String NOT_NULL = " NOT NULL ";
	private static final String COMMA_SEP = " , ";

    /* Create strings */

	private static final String CREATE_OBJECT_TABLE =
		CREATE + SQLiteContract.ObjectStorage.TABLE_NAME + " ( " +
			SQLiteContract.ObjectStorage._ID + TEXT_TYPE + " PRIMARY KEY " + NOT_NULL + COMMA_SEP +
			SQLiteContract.ObjectStorage.COLUMN_NAME_HREF + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			SQLiteContract.ObjectStorage.COLUMN_NAME_LAST_UPDATE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			SQLiteContract.ObjectStorage.COLUMN_NAME_HTTP_METHOD + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			SQLiteContract.ObjectStorage.COLUMN_NAME_BODY + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			SQLiteContract.ObjectStorage.COLUMN_NAME_MIMETYPE + TEXT_TYPE +
			" ) ";

	private static final String CREATE_COLLECTION_TABLE =
		CREATE + SQLiteContract.CollectionStorage.TABLE_NAME + " ( " +
			SQLiteContract.CollectionStorage._ID + TEXT_TYPE + " PRIMARY KEY " + NOT_NULL + COMMA_SEP +
			SQLiteContract.CollectionStorage.COLUMN_NAME_HREF + TEXT_TYPE + NOT_NULL + COMMA_SEP +
			SQLiteContract.CollectionStorage.COLUMN_NAME_LAST_UPDATE + TEXT_TYPE + NOT_NULL +
			" ) ";

	private static final String CREATE_COLLECTION_OBJECT_MAPPING_TABLE =
		CREATE + SQLiteContract.CollectionObjectMapping.TABLE_NAME + " ( " +
			SQLiteContract.CollectionObjectMapping.COLUMN_NAME_FID_COLLECTION + TEXT_TYPE + NOT_NULL +
			" REFERENCES " + SQLiteContract.CollectionStorage.TABLE_NAME +
			" (" + SQLiteContract.CollectionStorage._ID + " ) " + COMMA_SEP +
			SQLiteContract.CollectionObjectMapping.COLUMN_NAME_FID_OBJECT + TEXT_TYPE + NOT_NULL +
			" REFERENCES " + SQLiteContract.ObjectStorage.TABLE_NAME +
			" (" + SQLiteContract.ObjectStorage._ID + " ) " + COMMA_SEP +
			" PRIMARY KEY ( " +
			SQLiteContract.CollectionObjectMapping.COLUMN_NAME_FID_COLLECTION + COMMA_SEP +
			SQLiteContract.CollectionObjectMapping.COLUMN_NAME_FID_OBJECT + " ) " +
			" ) ";

    /* Delete strings */

	private static final String DELETE_OBJECT_CACHE_TABLE =
		DROP + SQLiteContract.ObjectStorage.TABLE_NAME;

	private static final String DELETE_COLLECTION_CACHE_TABLE =
		DROP + SQLiteContract.CollectionStorage.TABLE_NAME;

	private static final String DELETE_COLLECTION_OBJECT_MAPPING_TABLE =
		DROP + SQLiteContract.CollectionObjectMapping.TABLE_NAME;

    /* Constructor */

	public OfflineDbHelper( Context context )
	{
		super( context, DATABASE_NAME, null, DATABASE_VERSION );
	}

    /* Methods */

    /* From abstract super class */

	public void onCreate( SQLiteDatabase db )
	{
		db.execSQL( CREATE_OBJECT_TABLE );
		db.execSQL( CREATE_COLLECTION_TABLE );
		db.execSQL( CREATE_COLLECTION_OBJECT_MAPPING_TABLE );
	}

	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{
		db.execSQL( DELETE_OBJECT_CACHE_TABLE );
		db.execSQL( DELETE_COLLECTION_CACHE_TABLE );
		db.execSQL( DELETE_COLLECTION_OBJECT_MAPPING_TABLE );
		onCreate( db );
	}

	@Override
	public void onDowngrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{
		onUpgrade( db, oldVersion, newVersion );
	}
}
