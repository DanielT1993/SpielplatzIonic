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

import java.io.Serializable;

public class AOMOfflineInfo implements Serializable
{
	private static final long serialVersionUID = 7351787574963321245L;

	String httpMethod;
	String fileKey;
	long timestamp;
	String url;
	Class clazz;
	String localId;
	String refName;
	boolean isImage;
	boolean usePersistentStorage;
	boolean isRef;

	public AOMOfflineInfo( String httpMethod, String url, String fileKey, Class _class, final String _localId,
		final boolean _isImage, final boolean _isRef, final boolean _usePersistentStorage )
	{
		this( httpMethod, url, fileKey, _class, _localId, null, _isImage, _isRef, _usePersistentStorage );
	}

	public AOMOfflineInfo( String httpMethod, String url, String fileKey, Class _class, final String _localId,
		final String _refName, final boolean _isImage, final boolean _isRef, final boolean _usePersistentStorage )
	{
		this.httpMethod = httpMethod;
		this.url = url;
		this.clazz = _class;
		this.fileKey = fileKey;
		this.localId = _localId;
		this.timestamp = System.currentTimeMillis( );
		this.refName = _refName;
		this.isImage = _isImage;
		this.isRef = _isRef;
		this.usePersistentStorage = _usePersistentStorage;
	}

	public String getHttpMethod( )
	{
		return httpMethod;
	}

	public void setHttpMethod( String httpMethod )
	{
		this.httpMethod = httpMethod;
	}

	public String getFileKey( )
	{
		return fileKey;
	}

	public void setFileKey( String fileKey )
	{
		this.fileKey = fileKey;
	}

	public long getTimestamp( )
	{
		return timestamp;
	}

	public void setTimestamp( long timestamp )
	{
		this.timestamp = timestamp;
	}

	public String getUrl( )
	{
		return url;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

	public Class getClazz( )
	{
		return clazz;
	}

	public void setClazz( Class clazz )
	{
		this.clazz = clazz;
	}

	public String getLocalId( )
	{
		return localId;
	}

	public void setLocalId( String localId )
	{
		this.localId = localId;
	}

	public String getRefName( )
	{
		return refName;
	}

	public void setRefName( String refName )
	{
		this.refName = refName;
	}

	public boolean getIsImage( )
	{
		return isImage;
	}

	public void setIsImage( boolean isImage )
	{
		this.isImage = isImage;
	}

	public boolean getUsePersistentStorage( )
	{
		return usePersistentStorage;
	}

	public void setUsePersistentStorage( boolean usePersistentStorage )
	{
		this.usePersistentStorage = usePersistentStorage;
	}

	public boolean getIsRef( )
	{
		return isRef;
	}

	public void setIsRef( boolean isRef )
	{
		this.isRef = isRef;
	}

	@Override
	public String toString( )
	{
		return "AOMOfflineInfo: fileKey: " + getFileKey( ) + " - HTTP: " + getHttpMethod( ) + " " + getUrl( );
	}
}
