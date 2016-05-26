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

/* Looks up SQLiteStorage if nothing was found in InMemoryCache (but always saves to InMemoryCache)

 * Extended InMemoryCache in the past, so that no store-methods had to be in this class,
 * but after adding the parameter to the getInstance method that had to be changed,
 * because otherwise the getInstance method without parameter could be called on this class,
 * but the parameter is always needed.
 */
public class MemoryElseOfflineStorage extends AbstractStorage
{
	private static MemoryElseOfflineStorage instance;
	private static Context context;

	private MemoryElseOfflineStorage( Context context )
	{
		super( );
		this.context = context;
	}

	public static MemoryElseOfflineStorage getInstance( Context context )
	{
		if ( instance == null )
		{
			instance = new MemoryElseOfflineStorage( context );
		}
		return instance;
	}

	// Methods ================================================================

	@Override
	public boolean storeObject( String href, String json, String httpMethod )
	{
		return InMemoryCache.getInstance( ).storeObject( href, json, httpMethod );
	}

	@Override
	public boolean storeCollection( String requestUrl, String json )
	{
		return InMemoryCache.getInstance( ).storeCollection( requestUrl, json );
	}

	@Override
	public String getStoredObject( String href )
	{
		String result = InMemoryCache.getInstance( ).getStoredObject( href );
		return result == null ? SQLiteStorage.getInstance( this.context ).getStoredObject( href ) : result;
	}

	@Override
	public String getStoredCollection( String url )
	{
		String result = InMemoryCache.getInstance( ).getStoredCollection( url );
		return result == null ? SQLiteStorage.getInstance( this.context ).getStoredCollection( url ) : result;
	}

	@Override
	public boolean removeObject( String href )
	{
		boolean result = InMemoryCache.getInstance( ).removeObject( href );
		result = result || SQLiteStorage.getInstance( this.context ).removeObject( href );
		return result;
	}

	@Override
	public int removeCollection( final String href )
	{
		int result = 0;
		result += InMemoryCache.getInstance( ).removeCollection( href );
		result += SQLiteStorage.getInstance( this.context ).removeCollection( href );
		return result;
	}

	@Override
	public int clear( )
	{
		int result = 0;
		result += InMemoryCache.getInstance( ).clear( );
		result += SQLiteStorage.getInstance( this.context ).clear( );
		return result;
	}
}
