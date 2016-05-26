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

import java.util.Date;

public class TokenContainer
{
	private final String sessionToken;
	private final String refreshToken;
	private final Date expirationDate;

	private final String module;
	private final String model;

	private final String extra;

	/**
	 * @param sessionToken
	 * @param refreshToken
	 * @param expiration       Can be either a date (UNIX timestamp) or the number of seconds until the expiration of the sessionToken.
	 *                         You need to set the parameter isDate accordingly.
	 * @param expirationIsDate Indicates whether the parameter expiration is a date or the number of seconds until the expiration of the sessionToken
	 * @param model
	 * @param module
	 * @param extra
	 */
	public TokenContainer( String sessionToken, String refreshToken, String expiration, boolean expirationIsDate,
		String module, String model, String extra )
	{
		this.sessionToken = sessionToken;
		this.refreshToken = refreshToken;
		if ( TextUtils.isEmpty( expiration ) == false )
		{
			if ( expirationIsDate )
			{
				Date temp = new Date( );
				temp.setTime( Long.valueOf( expiration ) );
				this.expirationDate = temp;
			}
			else
			{
				this.expirationDate = new Date( new Date( ).getTime( ) + Integer.valueOf( expiration ) * 1000 );
			}
		}
		else
		{
			this.expirationDate = null;
		}
		this.module = module;
		this.model = model;
		this.extra = extra;
	}

	public String getSessionToken( )
	{
		return this.sessionToken;
	}

	public String getRefreshToken( )
	{
		return this.refreshToken;
	}

	public Date getExpirationDate( )
	{
		return this.expirationDate;
	}

	public String getModule( )
	{
		return this.module;
	}

	public String getModel( )
	{
		return this.model;
	}

	public String getExtra( )
	{
		return this.extra;
	}
}
