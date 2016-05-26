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

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache extends AbstractStorage
{

    /* Attributes */

	private static InMemoryCache instance;
	// One map is enough - object and collection hrefs are always different from each other
	private static Map<String, AbstractStorageContainer> storage;

    /* Constructors */

	public InMemoryCache( )
	{
		super( );
		if ( storage == null )
		{
			storage = new ConcurrentHashMap<String, AbstractStorageContainer>( );
		}
	}

    /* Methods */

	public static InMemoryCache getInstance( )
	{
		if ( instance == null )
		{
			instance = new InMemoryCache( );
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

		ObjectStorageContainer container = new ObjectStorageContainer( href, json, httpMethod );
		storage.put( hashedRequestUrl, container );
		return true;
	}

	/* Stores a collection of objects to the DB or updates it if it already exists */
	@Override
	public boolean storeCollection( String requestUrl, String json )
	{
		String hashedRequestUrl = hash( requestUrl );
		/* Store the objects that are in the collection on their own,
         * create a json array with just the object urls inside,
         * store both and the according mapping */
		JSONArray hrefCollection = new JSONArray( );
		try
		{
			JSONArray collection = new JSONArray( json );
			for ( int i = 0; i < collection.length( ); i++ ) // no foreach for JSONArray
			{
				JSONObject object = ( JSONObject ) collection.get( i );
                /* Store the objects and at the same time, if one storage procedure fails, return false.
                 * Storing the objects takes care of insert vs. update */
				String objectHref = object.getString( "href" );
				if ( storeObject( objectHref, object.toString( ), "GET" ) == false )
				{
					return false;
				}
				hrefCollection.put( objectHref );
			}
		}
		catch ( Exception e )
		{
			return false;
		}

		CollectionStorageContainer container = new CollectionStorageContainer( requestUrl, hrefCollection.toString( ) );
		storage.put( hashedRequestUrl, container );
		return true;
	}

	/* Returns the JSON of a cached object. NULL if not found. */
	@Override
	public String getStoredObject( String href )
	{
		href = cutOffParameters( href );
		String hashedHref = hash( href );
		return storage.containsKey( hashedHref ) ? storage.get( hashedHref ).getBody( ) : null;
	}

	@Override
	public String getStoredCollection( String url )
	{
		final String hashedUrl = hash( url );

		final String body = storage.containsKey( hashedUrl ) ? storage.get( hashedUrl ).getBody( ) : null;
		if ( body == null )
		{
			return null;
		}

		StringBuilder sb = new StringBuilder( "[" );

        /* Now go through the entries and fetch all objects.
         * If the body is "[]" it's okay, no Exception will be thrown and "[]" will be returned. */
		try
		{
			final JSONArray collectionJson = new JSONArray( body );
			String fetchedObjectJson;
			for ( int i = 0; i < collectionJson.length( ); i++ )
			{
				fetchedObjectJson = getStoredObject( collectionJson.getString( i ) );
				if ( fetchedObjectJson != null )
				{
					final char previousChar = sb.charAt( sb.length( ) - 1 );
					if ( i != 0 && previousChar != '[' && previousChar != ',' )
					{
						sb.append( "," );
					}
					sb.append( fetchedObjectJson );
				}
			}
		}
		catch ( Exception e )
		{
			return null;
		}
		sb.append( "]" );
		return sb.toString();
	}

	@Override
	public boolean removeObject( String href )
	{
		if ( href.contains( "?" ) )
		{
			href = href.substring( 0, href.indexOf( "?" ) );
		}
		String hashedRequestUrl = hash( href );

		return storage.remove( hashedRequestUrl ) == null ? false : true;
	}

	@Override
	public int removeCollection( final String url )
	{
		int result = 0;
		/* get all object hrefs that belong to a collection
		 *
		 * note: this code is similar to getStoredCollection. factorize!
		 */

		final String hashedUrl = hash( url );

		final String body = storage.containsKey( hashedUrl ) ? storage.get( hashedUrl ).getBody( ) : null;
		if ( body != null )
		{
			/* Now go through the entries and delete all objects.
			 * If the body is "[]" it's okay, no Exception will be thrown and "[]" will be returned. */
			try
			{
				final JSONArray collectionJson = new JSONArray( body );
				String hrefOfObjectToRemove;
				for ( int i = 0; i < collectionJson.length( ); i++ )
				{
					try
					{
						hrefOfObjectToRemove = collectionJson.getString( i ); // JSONException might be thrown here
						removeObject( hrefOfObjectToRemove ); // actual deletion
						result++;
					}
					catch ( JSONException ex )
					{
						Log.e( "InMemoryCache", "A JSONException occured while trying to get an item from a JSONArray" +
							" to delete it from the storage", ex );
					}
				}
			}
			catch ( JSONException ex )
			{
				Log.e( "InMemoryCache", "A JSONException occured while trying to create a JSONArray from a collection",
					ex );
			}

			/* delete the collection entry */
			storage.remove( hashedUrl );
		}
		/* else: body is null. ignore */

		return result;
	}

	@Override
	public int clear( )
	{
		final int result = storage.size();
		storage.clear( );
		return result;
	}
}
