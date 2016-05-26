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

/*
 public static final String TABLE_NAME = "objectstorage";
 public static final String COLUMN_NAME_ID = "id"; // Hash of URL or HREF
 public static final String COLUMN_NAME_HREF = "url";
 public static final String COLUMN_NAME_LAST_UPDATE = "lastupdate";
 public static final String COLUMN_NAME_HTTP_METHOD = "httpmethod";
 public static final String COLUMN_NAME_BODY = "body"; // JSON or binary data
 public static final String COLUMN_NAME_MIMETYPE = "mimetype"; // Needed to interpret text as binary data
 */
public class ObjectStorageContainer extends AbstractStorageContainer
{
	private String httpMethod;
	private String mimeType;

	// Constructors for the fields that must not be null
	public ObjectStorageContainer( String url, String body, String httpMethod )
	{
		super( url, body );
		this.httpMethod = httpMethod;
	}

	public String getHttpMethod( )
	{
		return httpMethod;
	}

	public void setHttpMethod( String httpMethod )
	{
		this.httpMethod = httpMethod;
	}

	public String getMimeType( )
	{
		return mimeType;
	}

	public void setMimeType( String mimeType )
	{
		this.mimeType = mimeType;
	}
}
