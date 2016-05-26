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
package com.apiomat.frontend.helper;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonHelper
{
	public static <T> Map<String, T> toMap( JSONObject jo ) throws JSONException
	{
		Map<String, T> result = new ConcurrentHashMap( );
		if ( jo != null )
		{
			Iterator keys = jo.keys( );
			while ( keys.hasNext( ) )
			{
				String key = ( String ) keys.next( );
				result.put( key, ( T ) jo.get( key ) );
			}
		}
		return result;
	}

	public static <T> Map<String, List<T>> toMapWithList( JSONObject jo ) throws JSONException
	{
		Map<String, List<T>> result = new ConcurrentHashMap( );
		if ( jo != null )
		{
			Iterator keys = jo.keys( );
			while ( keys.hasNext( ) )
			{
				String key = ( String ) keys.next( );
				JSONArray ja = jo.getJSONArray( key );
				List<T> resultValue = new ArrayList( );
				for ( int i = 0; i < ja.length( ); i++ )
				{
					resultValue.add( ( T ) ja.get( i ) );
				}
				result.put( key, resultValue );
			}
		}
		return result;
	}

	public static <T> JSONObject fromMap( Map<String, T> m ) throws JSONException
	{
		JSONObject result = new JSONObject( );
		for ( String key : m.keySet( ) )
		{
			result.put( key, m.get( key ) );
		}
		return result;
	}

	/**
	 * Helper method to convert a JSON array to a list
	 *
	 * @param array
	 * @return a list containing all elements of the JSON array
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public static List fromJSONArray( JSONArray array ) throws JSONException
	{
		List l = new ArrayList( );
		if ( array != null )
		{
			for ( int i = 0; i < array.length( ); i++ )
			{
				l.add( array.get( i ) );
			}
		}
		return l;
	}

	public static List<String> getHrefListFromJsonArrayWithAomModels( String jsonArrayString ) throws JSONException
	{
		return getHrefListFromJsonArrayWithAomModels( new JSONArray( jsonArrayString ) );
	}

	public static List<String> getHrefListFromJsonArrayWithAomModels( JSONArray ja ) throws JSONException
	{
		List<String> result = new ArrayList<String>( );
		List<JSONObject> aomModelsAsJsonObject = getJsonObjectListFromJsonArrayWithAomModels( ja );
		String currentActualHref;
		for ( JSONObject jo : aomModelsAsJsonObject )
		{
			currentActualHref = jo.optString( "href", null );
			result.add( currentActualHref );
		}
		return result;
	}

	public static List<JSONObject> getJsonObjectListFromJsonArrayWithAomModels( JSONArray ja ) throws JSONException
	{
		List<JSONObject> result = new ArrayList<JSONObject>( );

		String currentObjectJson;
		JSONObject currentObject;
		for ( int i = 0; i < ja.length( ); i++ )
		{
			currentObjectJson = ja.getString( i );
			currentObject = new JSONObject( currentObjectJson );
			result.add( currentObject );
		}

		return result;
	}

	public static Map<String, String> getHrefLMMapFromJsonArrayWithAomModels( JSONArray ja ) throws JSONException
	{
		Map<String, String> result = new ConcurrentHashMap<String, String>( );

		List<JSONObject> aomModelsAsJsonObject = getJsonObjectListFromJsonArrayWithAomModels( ja );
		String currentActualHref;
		String currentId;
		String currentLastModified;
		for ( JSONObject jo : aomModelsAsJsonObject )
		{
			currentActualHref = jo.optString( "href", null );
			currentLastModified = jo.optString( "lastModifiedAt", null );
			if ( TextUtils.isEmpty( currentActualHref ) == false && TextUtils.isEmpty( currentLastModified ) == false )
			{
				currentId = currentActualHref.substring( currentActualHref.lastIndexOf( "/" ) + 1 );
				result.put( currentId, currentLastModified );
			}
		}

		return result;
	}

	public static boolean stringJsonArrayContains( JSONArray ja, String string ) throws JSONException
	{
		for ( int i = 0; i < ja.length( ); i++ )
		{
			if ( string.equals( ja.getString( i ) ) )
			{
				return true;
			}
		}
		return false;
	}
}
