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
package com.apiomat.frontend;

import android.text.TextUtils;
import android.util.Base64;
import com.apiomat.frontend.basics.User;
import com.apiomat.frontend.helper.InterruptThread;
import com.apiomat.frontend.helper.NoSSLv3Factory;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;

public class AOMHttpClient
{
	protected static enum HTTP_METHOD
	{
		POST, GET, PUT, DELETE
	}

	private static final String AOM_HEADER_APIKEY = "X-apiomat-apikey";
	private static final String AOM_HEADER_SDKVERION = "X-apiomat-sdkVersion";
	private static final String AOM_HEADER_SYSTEM = "X-apiomat-system";
	private static final String AOM_HEADER_FULLUPDATE = "X-apiomat-fullupdate";
	private static final String AOM_HEADER_DELTA = "X-apiomat-delta";
	private static final String AOM_HEADER_DELTADELETED = "X-apiomat-delta-deleted";

	private final String baseUrl;
	private final String apiKey;
	private final String userName;
	private final String password;
	private final String system;
	private final Datastore.AuthType authType;
	private final String sessionToken;

	/* Standard values is 0 for both which means the call would block indefinitely. App users aren't that patient. */
	private int connectTimeout = 10000; // 10 s - for the time the client needs to connect to the server
	private int readTimeout = 5000; // 5 s - for the time between chunks the client receives from the server
	private int requestTimeout = 15000; // 15 s - for the time the whole requests takes

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final int EOF = -1;

	private static CookieManager cookieManager;

	static
	{
		HttpsURLConnection.setDefaultSSLSocketFactory( new NoSSLv3Factory( ) );
		/* enable vm-wide cookies */
		cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
	}

	public AOMHttpClient( String baseUrl, String apiKey, String userName, String password, String system,
		Datastore.AuthType authType, String sessionToken )
	{
		this.baseUrl = baseUrl;
		this.apiKey = apiKey;
		this.userName = userName;
		this.password = password;
		this.system = system;
		this.authType = authType;
		this.sessionToken = sessionToken;
	}

	protected String sendActualRequest( final HTTP_METHOD method, final URL url, final byte[] postEntity,
		final boolean asOctetStream, final List<Integer> expectedCodes,
		final AtomicReference<Integer> returnedStatusCode,
		final AtomicReference<String> eTag, final AtomicReference<String> deltaSync )
		throws ApiomatRequestException, ConnectException, SocketTimeoutException, SocketException
	{
		String resultStr = null;
		HttpURLConnection urlConnection = null;
		OutputStream os = null;
		ByteArrayInputStream byteArrayIS = null;
		InputStream is = null;
		try
		{
			urlConnection = ( HttpURLConnection ) url.openConnection( );

			List<HTTP_METHOD> allowedMethods =
				Arrays.asList( HTTP_METHOD.GET, HTTP_METHOD.POST, HTTP_METHOD.PUT, HTTP_METHOD.DELETE );
			if ( allowedMethods.contains( method ) )
			{
				String methodString;
				switch ( method )
				{
				case GET:
					methodString = "GET";
					break;
				case POST:
					methodString = "POST";
					break;
				case PUT:
					methodString = "PUT";
					break;
				case DELETE:
					methodString = "DELETE";
					break;
				default:
					methodString = "GET";
				}
				urlConnection.setRequestMethod( methodString );
			}

			Map<String, String> standardHeaders = getStandardHeaders( eTag, true, deltaSync );
			for ( Map.Entry<String, String> entry : standardHeaders.entrySet( ) )
			{
				urlConnection.setRequestProperty( entry.getKey( ), entry.getValue( ) );
			}
			/* If the HTTP responseCode is maybe a 304, we don't send gzip encoding
			 * because of an issue in HTTPUrlConnection a EOFException will be thrown
			 * (https://code.google.com/p/android/issues/detail?id=58637) */
			if ( urlConnection.getRequestProperty( "If-Modified-Since" ) != null ||
				urlConnection.getRequestProperty( "If-None-Match" ) != null )
			{
				urlConnection.setRequestProperty( "Accept-Encoding", "" );
			}
			if ( method == HTTP_METHOD.PUT )
			{
				urlConnection.setRequestProperty( AOM_HEADER_FULLUPDATE, "true" );
			}
			
			urlConnection = addManagedCookies( urlConnection );
			
			List<HTTP_METHOD> methodsWithContent = Arrays.asList( HTTP_METHOD.POST, HTTP_METHOD.PUT );
			if ( methodsWithContent.contains( method ) && postEntity != null && postEntity.length > 0 )
			{
				if ( asOctetStream )
				{
					urlConnection.setRequestProperty( "Content-Type", "application/octet-stream" );
				}
				else
				{
					urlConnection.setRequestProperty( "Content-Type", "application/json" );
				}
				urlConnection.setDoOutput( true );
				urlConnection.setFixedLengthStreamingMode( postEntity.length );
				os = urlConnection.getOutputStream( );
				byteArrayIS = new ByteArrayInputStream( postEntity );
				copyStreams( byteArrayIS, os );
			}
			/* All headers are set, check them (if method is overwritten in subclassed AOMHttpClient) */
			checkRequestHeaders( urlConnection );

			urlConnection.setConnectTimeout( this.connectTimeout );
			urlConnection.setReadTimeout( this.readTimeout );
			new Thread( new InterruptThread( urlConnection, this.requestTimeout ) ).start( );

			int statusCode = Status.NULL.getStatusCode( );
			boolean errorOccured = false;
			try
			{
				statusCode = urlConnection.getResponseCode( );
				if (statusCode >= 400)
				{
					/* If error is 401 or 407, ErrorStream won't be set, so checking it for != null won't work later */
					errorOccured = true;
					is = urlConnection.getErrorStream( );
				}
				else
				{
					is = urlConnection.getInputStream( );
				}
			}
			/* ConnectException, SocketTimeoutException and SocketException are IOException,
			 * but need special treatment in calling method. */
			catch ( ConnectException ex )
			{
				throw ex;
			}
			catch ( SocketTimeoutException ex )
			{
				throw ex;
			}
			catch ( SocketException ex )
			{
				throw ex;
			}
			catch ( IOException ex )
			{
				is = urlConnection.getErrorStream( );
			}

			/* creating a GZIPInputStream when status code is 304 not modified throws an ioexception */
			if ( is != null && statusCode != Status.NULL.getStatusCode( ) && statusCode != HttpURLConnection.HTTP_NOT_MODIFIED &&
				"gzip".equals( urlConnection.getContentEncoding( ) ) )
			{
				is = new GZIPInputStream( is );
			}

			if ( returnedStatusCode != null )
			{
				returnedStatusCode.set( statusCode );
			}
			if ( expectedCodes.contains( statusCode ) )
			{
				// Get etag from header if there and atomic reference settable
				if ( eTag != null )
				{
					final String eTagHeaderVal = urlConnection.getHeaderField( "ETag" );
					if ( eTagHeaderVal != null )
					{
						eTag.set( eTagHeaderVal );
					}
				}
				// Result string for post methods is the location in header (HREF)
				if ( HTTP_METHOD.POST.equals( method ) )
				{
					resultStr = urlConnection.getHeaderField( "Location" );
				}
				else
				{
					resultStr = copyStreamToString( is );
				}
			}
			else
			{
				/* Doesn't have to be an HTTP error, maybe just the statusCode whas not the one as expected.
				 * So only put the response body in the exception if an HTTP error occured. */
				if ( errorOccured )
				{
					final String errorStr = copyStreamToString( is );
					throw new ApiomatRequestException( statusCode, expectedCodes.get( 0 ), errorStr );
				}
				else
				{
					throw new ApiomatRequestException( Status.getStatusForCode( statusCode ), expectedCodes.get( 0 ) );
				}
			}
		}
		/* ConnectException, SocketTimeoutException and SocketException are IOException,
		 * but need special treatment in calling method.
		 * Catch and throw again here, because we need to catch IOException here and the previously thrown
		 * Connect/Socket exceptions would be catched by that. */
		catch ( ConnectException ex )
		{
			throw ex;
		}
		catch ( SocketTimeoutException ex )
		{
			throw ex;
		}
		catch ( SocketException ex )
		{
			throw ex;
		}
		catch ( IOException ex )
		{
			throw new ApiomatRequestException( Status.IO_EXCEPTION, HttpURLConnection.HTTP_OK, ex.getMessage( ) );
		}
		finally
		{
			/* Close streams before disconnecting */
			closeStreams( os, byteArrayIS, is );
			if ( urlConnection != null )
			{
				checkResponseHeaders( urlConnection );
				/* Set delta deleted header value to atomic reference, so that callers can access it */
				if ( deltaSync != null )
				{
					String deltaDeletedHeaderValue = urlConnection.getHeaderField( AOM_HEADER_DELTADELETED );
					if ( deltaDeletedHeaderValue != null )
					{
						deltaSync.set( deltaDeletedHeaderValue );
					}
				}
			}
		}

		checkResponseString( resultStr );
		return resultStr;
	}

	protected byte[] sendActualResourceRequest( final String href, final AtomicReference<String> eTag,
		final AtomicReference<Boolean> notModified )
		throws ApiomatRequestException, ConnectException, SocketTimeoutException, SocketException
	{
		byte[] result = null;
		try
		{
			final URL url = new URL( href );
			HttpURLConnection urlConnection = ( HttpURLConnection ) url.openConnection( );
			InputStream is = null;
			try
			{
				if ( this.userName != null && this.password != null )
				{
					urlConnection.setRequestProperty( "Authorization", getAuthenticationHeader( this.authType ) );
				}
				urlConnection.setRequestProperty( AOM_HEADER_APIKEY, this.apiKey );
				urlConnection.setRequestProperty( AOM_HEADER_SDKVERION, User.sdkVersion );
				urlConnection.setRequestProperty( AOM_HEADER_SYSTEM, this.system );
				if ( eTag != null && TextUtils.isEmpty( eTag.get( ) ) == false )
				{
					urlConnection.setRequestProperty( "If-None-Match", eTag.get( ) );
					/* Workaround for EOFException issue - http://stackoverflow.com/a/17638671/3596676
					 * Necessary after using NoSSLv3Factory for urlConnection.
					 * Update: Always necessary, otherwise there might be errors with GzipInputStream, see AOM-2122 */
					// urlConnection.setRequestProperty( "Accept-Encoding", "" );
				}
				urlConnection.setRequestProperty( "Accept-Encoding", "identity" ); // For AOM-2122

				urlConnection = addManagedCookies(urlConnection);

				/* All headers are set, check them (if method is overwritten in subclassed AOMHttpClient) */
				checkRequestHeaders( urlConnection );

				urlConnection.setConnectTimeout( this.connectTimeout );
				urlConnection.setReadTimeout( this.readTimeout );
				new Thread( new InterruptThread( urlConnection, this.requestTimeout ) ).start( );

				/* executes the request */
				int statusCode = Status.NULL.getStatusCode();
				try
				{
					statusCode = urlConnection.getResponseCode( );
					if (statusCode >= 400)
					{
						is = urlConnection.getErrorStream( );
					}
					else
					{
						is = urlConnection.getInputStream( );
					}
				}
				catch ( IOException e )
				{
					is = urlConnection.getErrorStream( );
				}


				if ( HttpURLConnection.HTTP_OK == statusCode || HttpURLConnection.HTTP_PARTIAL == statusCode )
				{
					result = copyStreamToByteArray( is );
					// Get etag from header if there and atomic reference settable
					if ( eTag != null )
					{
						final String eTagHeaderVal = urlConnection.getHeaderField( "ETag" );
						if ( eTagHeaderVal != null )
						{
							eTag.set( eTagHeaderVal );
						}
					}
				}
				else if ( HttpURLConnection.HTTP_NOT_MODIFIED == statusCode && notModified != null )
				{
					notModified.set( true );
				}
				else
				{
					final String errorStr = urlConnection.getContentLength( ) <= 0 ? "" : copyStreamToString( is );
					throw new ApiomatRequestException( statusCode, HttpURLConnection.HTTP_OK, errorStr );
				}
			}
			finally
			{
				if ( urlConnection != null )
				{
					checkResponseHeaders( urlConnection );
				}
				closeStreams( is );
			}
		}
		catch ( MalformedURLException e )
		{
			throw new ApiomatRequestException(
				Status.WRONG_URI_SYNTAX, HttpURLConnection.HTTP_OK, e.getMessage( ) );
		}
		/* ConnectException, SocketTimeoutException and SocketException are IOException,
		 * but need special treatment in calling method. */
		catch ( ConnectException ex )
		{
			throw ex;
		}
		catch ( SocketTimeoutException ex )
		{
			throw ex;
		}
		catch ( SocketException ex )
		{
			throw ex;
		}
		catch ( IOException ex )
		{
			throw new ApiomatRequestException( Status.IO_EXCEPTION, HttpURLConnection.HTTP_OK, ex.getMessage( ) );
		}
		return result;
	}

	protected JSONObject sendActualTokenRequest( ) throws ApiomatRequestException
	{
		final String appName = this.baseUrl.substring( baseUrl.lastIndexOf( '/' ) + 1 );
		final Map<String, String> queryParams = new ConcurrentHashMap<String, String>(  );
		queryParams.put( "grant_type", "aom_user" );
		queryParams.put( "client_id", appName );
		queryParams.put( "client_secret", this.apiKey );
		queryParams.put( "scope", "read write" );
		queryParams.put( "username", userName );
		queryParams.put( "password", password );
		queryParams.put( "app", appName );
		queryParams.put( "system", system );
		return sendActualTokenRequest( queryParams );
	}

	protected JSONObject sendActualTokenRequest( final String refreshToken ) throws ApiomatRequestException
	{
		final String appName = this.baseUrl.substring( baseUrl.lastIndexOf( '/' ) + 1 );
		final Map<String, String> queryParams = new ConcurrentHashMap<String, String>(  );
		queryParams.put( "grant_type", "refresh_token" );
		queryParams.put( "client_id", appName );
		queryParams.put( "client_secret", this.apiKey );
		queryParams.put( "refresh_token", refreshToken );
		return sendActualTokenRequest( queryParams );
	}

	/**
	 * Returns a map with token info for either the user that this Datastore has been configured with or the refresh
	 * token
	 *
	 * @param queryParams A map of query parameters
	 * @return A JsonObject with token info for either the user that this Datastore has been configured with or the
	 * refresh token
	 */
	protected JSONObject sendActualTokenRequest( final Map<String, String> queryParams ) throws ApiomatRequestException
	{
		JSONObject resultJsonObject = null;

		String responseString;

		final int expectedCode = HttpURLConnection.HTTP_OK;
		try
		{
			StringBuilder sb = new StringBuilder( );
			for ( final String key : queryParams.keySet() )
			{
				if ( sb.length( ) > 0 )
				{
					sb.append( "&" );
				}
				sb.append( key );
				sb.append( "=" );
				sb.append( URLEncoder.encode( queryParams.get( key ), "UTF-8" ) );
			}
			byte[] urlParametersAsBytes = sb.toString( ).getBytes( );
			final URL url =
				new URL( this.baseUrl.substring( 0, this.baseUrl.indexOf( "yambas" ) + 6 ) + "/oauth/token" );
			HttpURLConnection urlConnection = ( HttpURLConnection ) url.openConnection( );
			OutputStream os = null;
			ByteArrayInputStream byteArrayIS = null;
			InputStream is = null;
			try
			{
				urlConnection.setRequestMethod( "POST" );
				urlConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
				urlConnection.setRequestProperty("X-apiomat-system",this.system);
				urlConnection = addManagedCookies(urlConnection);

				urlConnection.setDoOutput( true );
				urlConnection.setFixedLengthStreamingMode( urlParametersAsBytes.length );
				os = urlConnection.getOutputStream( );
				byteArrayIS = new ByteArrayInputStream( urlParametersAsBytes );
				copyStreams( byteArrayIS, os );

				/* All headers are set, check them (if method is overwritten in subclassed AOMHttpClient) */
				checkRequestHeaders( urlConnection );

				/* executes the request */
				/* IOException is thrown for example when status code is 803 */
				final int statusCode = urlConnection.getResponseCode( );

				if ( statusCode >= 400 )
				{
					is = urlConnection.getErrorStream( );
				}
				else
				{
					is = urlConnection.getInputStream( );
				}

				/* creating a GZIPInputStream when status code is 304 not modified throws an ioexception */
				if ( is != null && statusCode != HttpURLConnection.HTTP_NOT_MODIFIED &&
					"gzip".equals( urlConnection.getContentEncoding( ) ) )
				{
					is = new GZIPInputStream( is );
				}
				if ( statusCode == expectedCode )
				{
					responseString = copyStreamToString( is );
				}
				else
				{
					String errorStr = copyStreamToString( is );
					throw new ApiomatRequestException( statusCode, expectedCode, errorStr );
				}
			}
			finally
			{
				if ( urlConnection != null )
				{
					checkResponseHeaders( urlConnection );
				}
				closeStreams( os, byteArrayIS, is );
			}
		}
		catch ( MalformedURLException e )
		{
			throw new ApiomatRequestException(
				Status.WRONG_URI_SYNTAX, expectedCode, e.getMessage( ) );
		}
		catch ( IOException e )
		{
			throw new ApiomatRequestException(
				Status.IO_EXCEPTION, HttpURLConnection.HTTP_OK, e.getMessage( ) );
		}

		checkResponseString( responseString );

		/* Get tokens and date from json and put them into a map */
		try
		{
			resultJsonObject = new JSONObject( responseString );
		}
		catch ( JSONException e )
		{
			throw new ApiomatRequestException( Status.JSON_FORMAT_ERROR, HttpURLConnection.HTTP_OK );
		}

		return resultJsonObject;
	}

	/**
	 * Check if apiOmat service is reachable
	 * The request will timeout after x ms or if connection cannot be established in y ms (values can be changed)
	 *
	 * @return true if service is available otherwise false
	 */
	public boolean isServiceAvailable( ) throws ApiomatRequestException
	{
		HttpURLConnection connection = null;
		boolean isAvailable = false;
		InputStream is = null;
		try
		{
			URL tmpUrl = new URL( this.baseUrl );
			URL url = new URL( tmpUrl.getProtocol( ), tmpUrl.getHost( ), tmpUrl.getPort( ), "/yambas/rest" );
			connection = ( HttpURLConnection ) url.openConnection( );
			/* set timeouts */
			connection.setConnectTimeout( this.connectTimeout );
			connection.setReadTimeout( this.readTimeout );
			new Thread( new InterruptThread( connection, this.requestTimeout ) ).start( );
			/* send */
			is = connection.getInputStream( );
			isAvailable = connection.getResponseCode( ) == HttpURLConnection.HTTP_OK;
		}
		catch ( Exception e )
		{
			isAvailable = false;
		}
		finally
		{
			if ( connection != null )
			{
				connection.disconnect( );
			}
			closeStreams( is );
		}
		return isAvailable;
	}

	public void setConnectTimeout( final int connectTimeout )
	{
		this.connectTimeout = connectTimeout;
	}

	public int getConnectTimeout( )
	{
		return this.connectTimeout;
	}

	public void setReadTimeout( final int readTimeout )
	{
		this.readTimeout = readTimeout;
	}

	public int getReadTimeout( )
	{
		return this.readTimeout;
	}

	public void setRequestTimeout( final int requestTimeout )
	{
		this.requestTimeout = requestTimeout;
	}

	public int getRequestTimeout( )
	{
		return this.requestTimeout;
	}

	public String getApiKey()
	{
		return this.apiKey;
	}

	public String getSystem()
	{
		return this.system;
	}

	private Map<String, String> getStandardHeaders( final AtomicReference<String> eTag, final boolean useEncoding,
		final AtomicReference<String> deltaSync )
	{
		Map<String, String> headerMap = new ConcurrentHashMap<String, String>( );
		headerMap.put( "Accept", "application/json" );
		headerMap.put( AOM_HEADER_APIKEY, this.apiKey );
		headerMap.put( AOM_HEADER_SDKVERION, User.sdkVersion );
		headerMap.put( AOM_HEADER_SYSTEM, this.system );
		if ( this.authType != Datastore.AuthType.GUEST )
		{
			headerMap.put( "Authorization", getAuthenticationHeader( this.authType ) );
		}
		if ( useEncoding )
		{
			headerMap.put( "Accept-Encoding", "gzip" );
		}
		if ( eTag != null && TextUtils.isEmpty( eTag.get( ) ) == false )
		{
			headerMap.put( "If-None-Match", eTag.get( ) );
			/* Workaround for EOFException issue - http://stackoverflow.com/a/17638671/3596676
			 * Necessary after using NoSSLv3Factory for urlConnection */
			headerMap.put( "Accept-Encoding", "" );
		}
		if ( deltaSync != null && TextUtils.isEmpty( deltaSync.get( ) ) == false )
		{
			headerMap.put( AOM_HEADER_DELTA, deltaSync.get( ) );
		}
		return headerMap;
	}

	private String getAuthenticationHeader( Datastore.AuthType authType )
	{
		switch ( authType )
		{
		case USERNAME_PASSWORD:
			final String credentials = this.userName + ":" + this.password;
			final String encoded = Base64.encodeToString( credentials.getBytes( ), Base64.NO_WRAP | Base64.NO_PADDING );
			return "Basic " + encoded;
		case OAUTH2_TOKEN:
			return "Bearer " + this.sessionToken;
		default:
			return "";
		}
	}

	private static String copyStreamToString( final InputStream is ) throws IOException
	{
		if ( is == null )
		{
			return "";
		}
		BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
		String result = copyBufferedReaderToString( reader );
		reader.close( );
		return result;
	}

	private static String copyBufferedReaderToString( final BufferedReader bufferedReader ) throws IOException
	{
		StringBuffer sb = new StringBuffer( );
		String line;
		while ( ( line = bufferedReader.readLine( ) ) != null )
		{
			sb.append( line );
		}
		return sb.toString( );
	}

	private static byte[] copyStreamToByteArray( InputStream is ) throws IOException
	{
		if ( is == null )
		{
			return new byte[ 0 ];
		}
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream( );
		copyStreams( is, byteOutStream );
		return byteOutStream.toByteArray( );
	}

	private static int copyStreams( InputStream input, OutputStream output ) throws IOException
	{
		long count = copyLarge( input, output, new byte[ DEFAULT_BUFFER_SIZE ] );
		if ( count > Integer.MAX_VALUE )
		{
			return -1;
		}
		return ( int ) count;
	}

	private static long copyLarge( InputStream input, OutputStream output, byte[] buffer ) throws IOException
	{
		long count = 0;
		int n = 0;
		while ( EOF != ( n = input.read( buffer ) ) )
		{
			output.write( buffer, 0, n );
			count += n;
		}
		return count;
	}

	private static void closeStreams( Closeable... streams )
	{
		for ( Closeable stream : streams )
		{
			if ( stream != null )
			{
				try
				{
					stream.close( );
				}
				catch ( IOException ignore )
				{
				}
			}
		}
	}

	/* Override this if you need the info.
	 * Note: The requestProperties of the urlConnection contain more than just headers.
	 * Note2: The connection must be closed before examining the headers,
	 * otherwise an IllegalStateException ("Already connected") gets thrown. */
	protected void checkRequestHeaders( HttpURLConnection urlConnection )
	{
	}

	/* Override this if you need the info. */
	protected void checkResponseHeaders( HttpURLConnection urlConnection )
	{
	}

	/* Override this if you need the info. */
	protected void checkResponseString( String responseString )
	{
	}

	private HttpURLConnection addManagedCookies(HttpURLConnection urlConnection)
	{
		if(cookieManager.getCookieStore().getCookies().size() > 0)
		{
			try
			{
				urlConnection.setRequestProperty( "Cookie",
					TextUtils.join( ";", cookieManager.getCookieStore( ).getCookies( ) ) );
			}
			catch (IllegalStateException ex) // "Already connected"
			{
				if (ex.getMessage().contains( "Already connected" ) == false)
				{
					throw ex;
				}
				/* else: urlConnection is already connected.
				 * Can't set cookies. Cookies from last connection are set.
				 * Disconnecting leads to read timeouts when request gets sent.
				 * Disconnecting before and reconnecting after setting cookies leads to "Socket closed" error
				 * when actual request gets sent.
				 */
			}
		}
		return urlConnection;
	}
}
