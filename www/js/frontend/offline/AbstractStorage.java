/* Copyright (c) 2011 - 2015, Apinauten GmbH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
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
 * THIS FILE IS GENERATED AUTOMATICALLY. DON'T MODIFY IT. */
package com.apiomat.frontend.offline;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Base class for different storage implementations with some shared methods already implemented
public abstract class AbstractStorage
{
	// Data ===================================================================

	private static int base64Flags = Base64.NO_WRAP | Base64.NO_PADDING;

	private static Map<String, String> eTagMap = new ConcurrentHashMap<String, String>( );

	// Constructor ============================================================

	public AbstractStorage( )
	{
	}

	// Public methods =========================================================

	public abstract boolean storeObject( String href, String json, String httpMethod );

	public boolean storeBinary( String href, byte[] binaryData, String httpMethod )
	{
		/* SQLite supports blob, but to reuse the existing object table, we convert the bytearray to hex text */
		String binaryDataString = Base64.encodeToString( binaryData, base64Flags );
		href = addImg( href );
		return storeObject( href, binaryDataString, httpMethod );
	}

	public abstract boolean storeCollection( String requestUrl, String json );

	public abstract String getStoredObject( String href );

	public byte[] getStoredBinary( String href )
	{
		href = addImg( href );
		String binaryDataString = getStoredObject( href );
		if ( binaryDataString == null )
		{
			return null;
		}
		else
		{
			return Base64.decode( binaryDataString, base64Flags );
		}
	}

	/**
	 * Returns the stored collection for the url. Returns null if nothing found.
	 *
	 * @param url
	 * @return The stored collection for the url. Null if nothing found.
	 */
	public abstract String getStoredCollection( String url );

	public abstract boolean removeObject( String href );

	public abstract int removeCollection( String href );

	public abstract int clear( );

	// ETag

	public boolean storeObjectLastModified( String url, String lastModified )
	{
		url = cutOffParameters( url );
		// We currently don't need to differentiate between object lastModified and collection ETag
		return storeCollectionETag( url, lastModified );
	}

	public boolean storeCollectionETag( String url, String eTag )
	{
		try
		{
			eTagMap.put( url, eTag );
			return true;
		}
		catch ( Exception e )
		{
			return false;
		}
	}

	public String loadObjectLastModified( String url )
	{
		url = cutOffParameters( url );
		// We currently don't need to differentiate between object lastModified and collection ETag
		return loadCollectionETag( url );
	}

	public String loadCollectionETag( String url )
	{
		if ( eTagMap.containsKey( url ) == false )
		{
			return null;
		}
		else
		{
			return eTagMap.get( url );
		}
	}

	// Protected + private methods ============================================

	protected static String hash( String message )
	{
		/* In case SHA-1 or MD5 can't be found, the messageDigest can be null */
		final MessageDigest messageDigest = getNewMessageDigest( );
		if ( messageDigest == null )
		{
			return message;
		}
		byte[] hashByteArray = messageDigest.digest( message.getBytes( ) );
		StringBuilder sb = new StringBuilder( );
		for ( byte b : hashByteArray )
		{
			sb.append( String.format( "%02X", b ) );
		}
		return sb.toString( );
	}

	/**
	 * Note: Ignoring queries / not saving query responses is important, because otherwise if an object gets modified offline, it might be included in the wrong queries.
	 * Considering queries for offline storage can only be used when there's a query engine in the SDK
	 * <p/>
	 * Note: don't cut off params for images, because they contain crop infos that mustn't get lost when storing in cache / persistent
	 *
	 * @param href
	 * @return The href without URL parameters
	 */
	protected static String cutOffParameters( String href )
	{
		if ( href.contains( "?" ) && href.contains( "/data/images/" ) == false )
		{
			return href.substring( 0, href.indexOf( "?" ) );
		}
		else
		{
			return href;
		}
	}

	// Images get saved with /images/<id>, but requested with /images/<id>.img, which doesn't match
	// Note: other files also get requested with .img.
	private String addImg( final String href )
	{
		if ( href.contains( ".img" ) )
		{
			return href;
		}
		String result = href;
		String imgEnding = ".img";
		if ( href.contains( "?" ) )
		{
			String firstPart = href.substring( 0, href.indexOf( "?" ) );
			String lastPart = href.substring( href.indexOf( "?" ), href.length( ) );
			result = firstPart + imgEnding + lastPart;
		}
		else
		{
			result += imgEnding;
		}
		return result;
	}

	public static MessageDigest getNewMessageDigest( )
	{
		// According to the Oracle Java docs, every Java implementation is required to implement SHA-1, MD5 and SHA-265
		// http://docs.oracle.com/javase/7/docs/api/java/security/MessageDigest.html
		MessageDigest messageDigest = null;
		try
		{
			messageDigest = MessageDigest.getInstance( "SHA-1" );
			messageDigest.reset( );
		}
		catch ( NoSuchAlgorithmException e )
		{
			try
			{
				messageDigest = MessageDigest.getInstance( "MD5" );
				messageDigest.reset( );
			}
			catch ( NoSuchAlgorithmException e1 )
			{
				/* Everywhere where the messageDigest gets used, the messages won't be hashed! */
				messageDigest = null;
			}
		}
		return messageDigest;
	}
}
