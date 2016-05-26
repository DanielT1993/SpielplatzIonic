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
package com.apiomat.frontend;

/* Not a real factory known from the pattern, but a way to move away the creation and enable the use of mocked AOMHttpClients.

* In the DS constructor createHttpClient() gets called.
* When the client is needed in the DS, getAomHttpClient gets called.
* In case of an offline test, setDataEnabled(boolean) either sets a mocked AOMHttpClient or restores the original one.
*
* Setting a client variable in the DS from a test method via reflection is not as good,
* because the backup client would have to be saved manually instead of automatically when configuring the DS
* */
public class AOMHttpClientFactory
{

	private static AOMHttpClient aomHttpClient;
	private static AOMHttpClient backup;

	public static void createHttpClient( String baseUrl, String apiKey, String userName, String password, String system,
		Datastore.AuthType authType, String sessionToken )
	{
		aomHttpClient = new AOMHttpClient( baseUrl, apiKey, userName, password, system, authType, sessionToken );
		// Restore is always for the original AOMHttpClient, which gets created above, so also backup here
		backup = aomHttpClient;
	}

	public static AOMHttpClient getAomHttpClient( )
	{
		return aomHttpClient;
	}

	public static void setAOMHttpClient( AOMHttpClient aomHttpClientReplacement )
	{
		aomHttpClient = aomHttpClientReplacement;
	}

	public static void restoreAomHttpClient( )
	{
		aomHttpClient = backup;
	}
}

