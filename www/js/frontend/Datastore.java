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

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.apiomat.frontend.basics.User;
import com.apiomat.frontend.callbacks.AOMCallback;
import com.apiomat.frontend.callbacks.AOMEmptyCallback;
import com.apiomat.frontend.helper.*;
import com.apiomat.frontend.offline.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is your interface to the apiOmat service. Each method lets you
 * post, put, get or delete your class instances. Basic handling is already
 * implemented in your generated module classes, so it won't be necessary in
 * most cases to call the {@link com.apiomat.frontend.Datastore} methods directly.
 * 
 * @author andreasfey, phimi
 */
public class Datastore
{
	private static Datastore myInstance;

	private static AOMCacheStrategy cacheStrategy = AOMCacheStrategy.NETWORK_ELSE_CACHE;
	private static boolean useETag = true; // default for NETWORK_ELSE_CACHE default strategy
	private static AOMOfflineStrategy offlineStrategy = AOMOfflineStrategy.NO_OFFLINE_HANDLING;
	private final String baseUrl;
	private AOMOfflineHandler offlineHandler = null;

	/**
	 * Authentication types the datastore can be configured with
	 */
	public static enum AuthType
	{
		/**
		 * Configuration without credentials.
		 */
		GUEST,
		/**
		 * Configuration with username and password
		 */
		USERNAME_PASSWORD,
		/**
		 * Configuration with an OAuth2 token
		 */
		OAUTH2_TOKEN
	}

	private final AuthType authType;

	private static Map<Class<?>, Boolean> offlineMapping = new ConcurrentHashMap<Class<?>, Boolean>( );

	private boolean wasLoadedFromStorage;

	private boolean useDeltaSync;

	private boolean checkObjectState;

	private Datastore( final String baseUrl, final String apiKey, final String userName,
		final String password, final String system )
	{
		this.baseUrl = baseUrl;
		this.authType =
			TextUtils.isEmpty( userName ) || TextUtils.isEmpty( password ) ? AuthType.GUEST
				: AuthType.USERNAME_PASSWORD;
		AOMHttpClientFactory.createHttpClient( baseUrl, apiKey, userName, password, system, this.authType, null );
		useDeltaSync = false;
		checkObjectState = true;
	}

	private Datastore( final String baseUrl, final String apiKey, final String system,
		final String sessionToken )
	{
		this.baseUrl = baseUrl;
		this.authType = TextUtils.isEmpty( sessionToken ) ? AuthType.GUEST : AuthType.OAUTH2_TOKEN;
		AOMHttpClientFactory.createHttpClient( baseUrl, apiKey, null, null, system, this.authType, sessionToken );
		useDeltaSync = false;
		checkObjectState = true;
	}

	/**
	 * Returns a singleton instance of the {@link com.apiomat.frontend.Datastore}
	 * 
	 * @return singleton instance
	 */
	public static Datastore getInstance( )
	{
		if ( myInstance != null )
		{
			return myInstance;
		}

		throw new IllegalStateException(
			"The datastore hasn't been configured yet - call Datastore.configure(...) before sending requests." );
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @deprecated Please use {@link #configureWithCredentials(com.apiomat.frontend.basics.User)} instead
	 * 
	 * @param baseUrl
	 *        The base URL of the APIOMAT service; usually <a
	 *        href="https://apiomat.org/yambas/rest/apps/">https://apiomat.org/yambas/rest/apps/</a> (see the User
	 *        class)
	 * @param apiKey
	 *        The api key of your application (see the User class)
	 * @param userName
	 *        Your username
	 * @param password
	 *        Your password
	 * @return A configured Datastore instance
	 */
	@Deprecated
	public static Datastore configure( final String baseUrl,
		final String apiKey, final String userName, final String password )
	{
		myInstance = new Datastore( baseUrl, apiKey, userName, password, User.system );
		return myInstance;
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @deprecated Use {@link #configureWithCredentials(com.apiomat.frontend.basics.User)} instead
	 * 
	 * @param user
	 *        The user where userName and password are the login credentials
	 * @return A configured Datastore instance
	 */
	@Deprecated
	public static Datastore configure( final User user )
	{
		return configureWithCredentials( user );
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @param user
	 *        The user where userName and password are the login credentials
	 * @return A configured Datastore instance
	 */
	public static Datastore configureWithCredentials( final User user )
	{
		myInstance = new Datastore( User.baseURL, User.apiKey,
			user.getUserName( ), user.getPassword( ), User.system );
		return myInstance;
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @deprecated Use {@link #configureWithCredentials(com.apiomat.frontend.basics.User)} instead
	 * 
	 * @param baseUrl The base URL of the APIOMAT service; usually <a
	 *        href="https://apiomat.org/yambas/rest/apps/">https://apiomat.org/yambas/rest/apps/</a> (see the User
	 *        class)
	 * @param apiKey The api key of your application (see the User class)
	 * @param userName Your username
	 * @param password Your password
	 * @param sdkVersion The SDK version (see the User class)
	 * @param system The system which will be used (see the User class) (should be LIVE,TEST,STAGING)
	 * @return A configured Datastore instance
	 */
	@Deprecated
	public static Datastore configure( final String baseUrl, final String apiKey, final String userName,
		final String password, final String sdkVersion, final String system )
	{
		myInstance = new Datastore( baseUrl, apiKey, userName, password, system );
		return myInstance;
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @deprecated Use {@link #configureAsGuest(String, String, String)} instead
	 * 
	 * @param baseUrl The base URL of the APIOMAT service; usually <a
	 *        href="https://apiomat.org/yambas/rest/apps/">https://apiomat.org/yambas/rest/apps/</a> (see the User
	 *        class)
	 * @param apiKey The api key of your application (see the User class)
	 * @param system The system which will be used (see the User class) (should be LIVE,TEST,STAGING)
	 * @return A configured Datastore instance
	 */
	@Deprecated
	public static Datastore configure( final String baseUrl, final String apiKey, final String system )
	{
		return configureAsGuest( baseUrl, apiKey, system );
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @param baseUrl The base URL of the APIOMAT service; usually <a
	 *        href="https://apiomat.org/yambas/rest/apps/">https://apiomat.org/yambas/rest/apps/</a> (see the User
	 *        class)
	 * @param apiKey The api key of your application (see the User class)
	 * @param system The system which will be used (see the User class) (should be LIVE,TEST,STAGING)
	 * @return A configured Datastore instance
	 */
	public static Datastore configureAsGuest( final String baseUrl, final String apiKey, final String system )
	{
		myInstance = new Datastore( baseUrl, apiKey, null, null, system );
		return myInstance;
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @param baseUrl The base URL of the APIOMAT service; usually <a
	 *        href="https://apiomat.org/yambas/rest/apps/">https://apiomat.org/yambas/rest/apps/</a> (see the User
	 *        class)
	 * @param apiKey The api key of your application (see the User class)
	 * @param system The system which will be used (see the User class) (should be LIVE,TEST,STAGING)
	 * @param sessionToken The session token to use for configuring the Datastore
	 * @return A configured Datastore instance
	 */
	public static Datastore configureWithSessionToken( final String baseUrl, final String apiKey, final String system,
		final String sessionToken )
	{
		myInstance = new Datastore( baseUrl, apiKey, system, sessionToken );
		return myInstance;
	}

	/**
	 * Configures and returns a {@link com.apiomat.frontend.Datastore} instance
	 * 
	 * @param user The user that has a session token attached
	 * @return A configured Datastore instance
	 */
	public static Datastore configureWithSessionToken( final User user )
	{
		myInstance = new Datastore( User.baseURL, User.apiKey, User.system, user.getSessionToken( ) );
		return myInstance;
	}

	/**
	 * Gets the caching strategy of this datastore
	 *
	 * @return the currently set AOMCacheStrategy
	 */
	public static AOMCacheStrategy getCachingStrategy( )
	{
		return Datastore.cacheStrategy;
	}

	/**
	 * Sets the caching strategy for this datastore.
	 * 
	 * @param cacheStrategy the caching strategy to use; see {@link com.apiomat.frontend.Datastore.AOMCacheStrategy}
	 * @param context The application context
	 * @return the datastore
	 */
	public static Datastore setCachingStrategy( AOMCacheStrategy cacheStrategy, Context context )
	{
		if ( myInstance == null )
		{
			throw new IllegalStateException(
				"The datastore hasn't been configured yet - call Datastore.configure(...) before sending requests." );
		}
		Datastore.cacheStrategy = cacheStrategy;
		if ( cacheStrategy.equals( AOMCacheStrategy.NETWORK_ELSE_CACHE ) == false )
		{
			useETag = false;
		}
		else
		{
			useETag = true;
		}
		return setContext( context );
	}

	/**
	 * Sets the context and initializes the offline handler
	 * 
	 * @param _context
	 * @return The datastore instance
	 */
	public static Datastore setContext( Context _context )
	{
		return setContext( _context, null );
	}

	/**
	 * Sets the context and initializes the offline handler
	 * 
	 * @param _context
	 * @param _listener (optional) This listener will be informed if offline task are executed successfully or with
	 *        errors
	 * @return The datastore instance
	 */
	public static Datastore setContext( Context _context, AOMOfflineHandler.AOMOfflineListener _listener )
	{
		/* initialize handler */
		if ( myInstance.offlineHandler == null )
		{
			myInstance.offlineHandler = new AOMOfflineHandler( _context );
		}
		if ( _listener != null )
		{
			myInstance.offlineHandler.addListener( _listener );
		}
		return myInstance;
	}

	/**
	 * Registers the AOMNetworkHandler BroadcastReceiver if it isn't registered yet
	 *
	 * @param context
	 */
	public void registerReceiver(Context context)
	{
		myInstance.offlineHandler.registerReceiver(context);
	}

	/**
	 * Unregisters the AOMNetworkHandler BroadcastReceiver if it's registered
	 */
	public void unregisterReceiver()
	{
		myInstance.offlineHandler.unregisterReceiver( );
	}

	/**
	 * Returns if the AOMNetworkHandler BroadcastReceiver is registered
	 *
	 * @return if the AOMNetworkHandler BroadcastReceiver is registered
	 */
	public boolean isReceiverRegistered()
	{
		return myInstance.offlineHandler.isReceiverRegistered();
	}

	/**
	 * Performs activity-related actions like registering the AOMNetworkHandler BroadcastReceiver
	 *
	 * @param context
	 */
	public void onResume(Context context)
	{
		/* Currently only receiver registration, but can be extended in the future */
		registerReceiver( context );
	}

	/**
	 * Performs activity-related actions like deregistering the AOMNetworkHandler BroadcastReceiver
	 */
	public void onPause()
	{
		/* Currently only receiver deregistration, but can be extended in the future */
		unregisterReceiver();
	}

	/**
	 * Set the offline strategy for your application.
	 *
	 * @deprecated AOMOfflineStrategy was replaced by {@link com.apiomat.frontend.Datastore.AOMCacheStrategy}. Please use setCachingStrategy(AOMCacheStrategy, Context)
	 *
	 * @param _offlineStrategy The strategy
	 * @param _context         The context (for offline initalization) Normally it will be teh application context
	 * @param _listener        (optional) This listener will be informed if offline task are executed successfully or with
	 *                         errors
	 * @return Configured {@link com.apiomat.frontend.Datastore} instance
	 */
	@Deprecated
	public static Datastore setOfflineStrategy( AOMOfflineStrategy _offlineStrategy, Context _context,
		AOMOfflineHandler.AOMOfflineListener _listener ) throws IllegalStateException, RuntimeException
	{
		if ( myInstance == null )
		{
			throw new IllegalStateException(
				"The datastore hasn't been configured yet - call Datastore.configure(...) before sending requests." );
		}
		Datastore.offlineStrategy = _offlineStrategy;
		/* initialize handler */
		myInstance = setContext( _context );
		if ( _listener != null )
		{
			myInstance.offlineHandler.addListener( _listener );
		}
		return myInstance;
	}

	/**
	 * Method for posting the class instance to the server <u>initially</u>. That is, at
	 * the point using this method the class instance has not HREF yet.
	 * 
	 * @param dataModel the class instance to save on the server
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return the HREF of the posted class instance
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public String postOnServer( final AbstractClientDataModel dataModel, final boolean isRef,
		final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		return postOnServer( createModelHref( dataModel.getModuleName( ), dataModel.getSimpleName( ) ),
			dataModel, isRef, usePersistentStorage );
	}

	/**
	 * Method for posting the class instance to the server in an update manner. That is,
	 * at the point using this method the class instance has a HREF and exists on the
	 * server.
	 *
	 * @param href HREF of the class instance to post (or the address to post the class instance to)
	 * @param dataModel the class instance which will be saved on server
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return the HREF of the posted class instance
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public String postOnServer( final String href, final AbstractClientDataModel dataModel,
		final boolean isRef, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		final String data = dataModel.toJson( );
		final boolean isCollection = false;
		return postOnServer( href, data, isCollection, isRef, usePersistentStorage );
	}

	/**
	 * Method for posting the class instance to the server in an update manner. That is,
	 * at the point using this method the class instance has a HREF.
	 * Request works background thread and not on the UI thread
	 *
	 * @param href HREF of the class instance to post (or the address to post the class instance to)
	 * @param dataModel the class instance which will be saved on server
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param callback The method which will be called if response come back from server.
	 * @return A reference to the task that runs in the background.
	 *         Can be null if if the task wasn't started due to an error.
	 */
	public AOMTask<String> postOnServerAsync( final String href, final AbstractClientDataModel dataModel,
		final boolean isRef, final boolean usePersistentStorage, final AOMCallback<String> callback )
	{
		AOMTask<String> postTask = null;
		final String data = dataModel.toJson( );
		postTask = new AOMTask<String>( )
		{
			@Override
			public String doRequest( AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				final boolean isCollection = false;
				return postOnServer( href, data, isCollection, isRef, usePersistentStorage );
			}
		};
		postTask.execute( callback );
		return postTask;
	}

	/**
	 * Method to post static data to the server. Do not forget to store the
	 * returned HREF to the owner object, since this method only stores the byte
	 * array on the server.
	 * 
	 * @param rawData raw data as byte array
	 * @param isImage TRUE to store the raw data as image, FALSE to store as video
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @return HREF of the posted data
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public String postStaticDataOnServer( final byte[ ] rawData, final boolean isImage, final boolean isRef )
		throws ApiomatRequestException
	{
		final boolean usePersistentStorage = false;
		return postStaticDataOnServer( rawData, isImage, isRef, usePersistentStorage );
	}

	/**
	 * Method to post static data to the server. Do not forget to store the
	 * returned HREF to the owner object, since this method only stores the byte
	 * array on the server.
	 *
	 * Note: The order of the isRef and usePersistentStorage parameters got changed in v1.16
	 * 
	 * @param rawData raw data as byte array
	 * @param isImage TRUE to store the raw data as image, FALSE to store as video
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return HREF of the posted data
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public String postStaticDataOnServer( final byte[ ] rawData, final boolean isImage,
		final boolean isRef, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		return postStaticDataOnServer( createStaticDataHref( isImage ), rawData, isImage, isRef, usePersistentStorage );
	}

	/**
	 * Method to post static data to the server. Do not forget to store the
	 * returned HREF to the owner object, since this method only stores the byte
	 * array on the server.
	 * Request works in background thread and not on the UI thread
	 * 
	 * @param rawData raw data as byte array
	 * @param isImage TRUE to store the raw data as image, FALSE to store as video
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param _callback The object which will called if response come back from server.
	 * @return A reference to the task that runs in the background.
	 */
	public AOMTask<String> postStaticDataOnServerAsync( final byte[ ] rawData, final boolean isImage,
		final boolean isRef, final AOMCallback<String> _callback )
	{
		final boolean usePersistentStorage = false;
		return postStaticDataOnServerAsync( rawData, isImage, isRef, usePersistentStorage, _callback );
	}

	/**
	 * Method to post static data to the server. Do not forget to store the
	 * returned HREF to the owner object, since this method only stores the byte
	 * array on the server.
	 * Request works in background thread and not on the UI thread
	 * 
	 * @param rawData raw data as byte array
	 * @param isImage TRUE to store the raw data as image, FALSE to store as video
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param _callback The object which will called if response come back from server.
	 * @return A reference to the task that runs in the background.
	 */
	public AOMTask<String> postStaticDataOnServerAsync( final byte[ ] rawData, final boolean isImage,
		final boolean isRef, final boolean usePersistentStorage,
		final AOMCallback<String> _callback )
	{
		AOMTask<String> postTask = new AOMTask<String>( )
		{
			@Override
			public String doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				return postStaticDataOnServer( createStaticDataHref( isImage ), rawData, isImage, isRef, usePersistentStorage );
			}
		};
		postTask.execute( _callback );
		return postTask;
	}

	/**
	 * Loads an existing class instance from the server
	 *
	 * @param dataModelHref HREF address of the class instance
	 * @param clazz the class of the object
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return the class instance
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public <T extends AbstractClientDataModel> T loadFromServer( final String dataModelHref,
		final Class<T> clazz,
		final boolean withReferencedHrefs,
		final boolean isRef,
		final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		T element;
		try
		{
			element = clazz.newInstance( );
		}
		catch ( Exception e )
		{
			throw new ApiomatRequestException( Status.INSTANTIATE_EXCEPTION, HttpURLConnection.HTTP_OK );
		}
		final boolean cacheThenNetworkFirstCall = false;
		return loadFromServer( dataModelHref, element, withReferencedHrefs, isRef, cacheThenNetworkFirstCall,
			usePersistentStorage );
	}

	/**
	 * Loads an existing class instance from the server, but in the background and not on
	 * the UI thread
	 *
	 * @param dataModelHref HREF address of the class instance
	 * @param clazz the class of the object
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param getCallback The method which will called if response come back from server
	 * @return A reference to the task that runs in the background.
	 *         Can be null if if the task wasn't started due to an error.
	 */
	public <T extends AbstractClientDataModel> AOMTask<T> loadFromServerAsync(
		final String dataModelHref, final Class<T> clazz, final boolean withReferencedHrefs,
		final boolean isRef, final boolean usePersistentStorage, final AOMCallback<T> getCallback )
	{
		T element;
		try
		{
			element = clazz.newInstance( );
		}
		catch ( Exception e )
		{
			getCallback.isDone( null, false, new ApiomatRequestException( Status.INSTANTIATE_EXCEPTION, HttpURLConnection.HTTP_OK ) );
			return null;
		}
		return loadFromServerAsync( dataModelHref, element, withReferencedHrefs, isRef, usePersistentStorage,
			getCallback );
	}

	/**
	 * Loads an existing class instance from the server. The new values from server are
	 * written directly to the dataModel parameter.
	 *
	 * @param dataModelHref HREF address of the class instance
	 * @param dataModel the class instance
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param cacheThenNetworkFirstCall
	 *        When the caching strategy is set to CACHE_THEN_NETWORK, this flag indicates whether it's the first call or
	 *        not. For other caching strategies this should always be false.
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return the class instance
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public <T extends AbstractClientDataModel> T loadFromServer(
		final String dataModelHref, final T dataModel, final boolean withReferencedHrefs,
		final boolean isRef, final boolean cacheThenNetworkFirstCall, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
        return loadFromServer( dataModelHref, dataModel, withReferencedHrefs, isRef, cacheThenNetworkFirstCall, usePersistentStorage, null);
	}

    /* Would usually be private, but protected because a method in ACDM calls it */
    protected <T extends AbstractClientDataModel> T loadFromServer(
            final String dataModelHref, final T dataModel, final boolean withReferencedHrefs,
            final boolean isRef, final boolean cacheThenNetworkFirstCall, final boolean usePersistentStorage,
            final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef)
            throws ApiomatRequestException
    {
        if ( TextUtils.isEmpty( dataModelHref ) )
        {
            throw new ApiomatRequestException( Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_OK );
        }

        AtomicReference<Integer> returnedStatusCode = new AtomicReference<Integer>( );
        List<Integer> expectedCodes = new ArrayList<Integer>( );
        expectedCodes.add( HttpURLConnection.HTTP_OK );
        expectedCodes.add( HttpURLConnection.HTTP_NOT_MODIFIED );
        if ( isRef )
        {
            expectedCodes.add( HttpURLConnection.HTTP_NO_CONTENT );
        }

        final boolean asOctetStream = false;
        final boolean isCollection = false;
        String returnStr =
                sendRequest( AOMHttpClient.HTTP_METHOD.GET, dataModelHref, "", withReferencedHrefs, null,
                        asOctetStream, expectedCodes, returnedStatusCode, isCollection,
                        isRef, cacheThenNetworkFirstCall, usePersistentStorage, wasLoadedFromStorageAtomicRef );

        if ( TextUtils.isEmpty( returnStr ) == false )
        {
            dataModel.fromJson( returnStr );
            return dataModel;
        }
        else
        {
			/* Okay, for example when loading a reference attribute, but no reference has been set yet */
            return null;
        }
    }

	/**
	 * Loads an existing class instance from the server but in the background and not on
	 * the UI thread. The new values from server are written directly to the
	 * dataModel parameter.
	 *
	 * @param dataModelHref HREF address of the class instance
	 * @param dataModel the class instance
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param getCallback the callback method
	 * @return A reference to the task that runs in the background.
	 */
	public <T extends AbstractClientDataModel> AOMTask<T> loadFromServerAsync(
		final String dataModelHref, final T dataModel, final boolean withReferencedHrefs,
		final boolean isRef, final boolean usePersistentStorage, final AOMCallback<T> getCallback )
	{
		if ( cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) )
		{
			AOMTask<T> loadTask = new AOMTask<T>( )
			{
				@Override
				public T doRequest(AtomicReference<Boolean> wasLoadedFromStorageAtomicRef ) throws ApiomatRequestException
				{
					loadFromServer( dataModelHref, dataModel, withReferencedHrefs, isRef, true, usePersistentStorage, wasLoadedFromStorageAtomicRef );
					return dataModel;
				}
			};
			loadTask.execute( getCallback );
		}
		AOMTask<T> loadTask = new AOMTask<T>( )
		{
			@Override
			public T doRequest(AtomicReference<Boolean> wasLoadedFromStorageAtomicRef ) throws ApiomatRequestException
			{
				loadFromServer( dataModelHref, dataModel, withReferencedHrefs, isRef, false, usePersistentStorage, wasLoadedFromStorageAtomicRef );
				return dataModel;
			}
		};
		loadTask.execute( getCallback );
		return loadTask;
	}

	/**
	 * Loads existing class instances from the server
	 *
	 * @param moduleName name of the module that the class belongs to
	 * @param simpleModelName the simple name of the class
	 * @param clazz the class of the objects
	 * @param query a query string to filter the results
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return all class instances fitting the search parameters
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public <T extends AbstractClientDataModel> List<T> loadFromServer(
		final String moduleName, final String simpleModelName, final Class<T> clazz,
		final String query, final boolean withReferencedHrefs,
		final boolean isRef, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		final boolean cacheThenNetworkFirstCall = false;
		return loadFromServer( createModelHref( moduleName, simpleModelName ), clazz, query, withReferencedHrefs,
			isRef, cacheThenNetworkFirstCall, usePersistentStorage );
	}

	/**
	 * Loads existing class instance from the server in the background
	 *
	 * @param moduleName name of the module that the class belongs to
	 * @param simpleModelName the simple name of the class
	 * @param clazz the class of the objects
	 * @param query a query string to filter the results
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param getCallback method which will called when request is finished
	 * @return A reference to the task that runs in the background
	 */
	public <T extends AbstractClientDataModel> AOMTask<List<T>> loadFromServerAsync(
		final String moduleName,
		final String simpleModelName,
		final Class<T> clazz,
		final String query,
		final boolean withReferencedHrefs,
		final boolean isRef,
		final boolean usePersistentStorage,
		final AOMCallback<List<T>> getCallback )
	{
		return loadFromServerAsync( createModelHref( moduleName, simpleModelName ), clazz, query, withReferencedHrefs,
			isRef, usePersistentStorage, getCallback );
	}

	/**
	 * Loads existing class instances from the server
	 *
	 * @param dataModelHref HREF of the class instances
	 * @param clazz the class of the objects
	 * @param query a query string to filter the results
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param cacheThenNetworkFirstCall When the caching strategy is set to
	 *        CACHE_THEN_NETWORK, this flag indicates whether it's the first call or not.
	 *        For other caching strategies this should always be false.
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return all class instances fitting the search parameters
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public <T extends AbstractClientDataModel> List<T> loadFromServer(
		final String dataModelHref, final Class<T> clazz, final String query, final boolean withReferencedHrefs,
		final boolean isRef, final boolean cacheThenNetworkFirstCall, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
        return loadFromServer( dataModelHref, clazz, query, withReferencedHrefs, isRef, cacheThenNetworkFirstCall, usePersistentStorage, null );
	}

    private <T extends AbstractClientDataModel> List<T> loadFromServer(
            final String dataModelHref, final Class<T> clazz, final String query, final boolean withReferencedHrefs,
            final boolean isRef, final boolean cacheThenNetworkFirstCall, final boolean usePersistentStorage,
            final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef)
            throws ApiomatRequestException
    {
        if ( TextUtils.isEmpty( dataModelHref ) )
        {
            throw new ApiomatRequestException( Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_OK );
        }

        AtomicReference<Integer> returnedStatusCode = new AtomicReference<Integer>( );
        List<Integer> expectedCodes = new ArrayList<Integer>( );
        expectedCodes.add( HttpURLConnection.HTTP_OK );
        expectedCodes.add( HttpURLConnection.HTTP_NOT_MODIFIED );

        final boolean asOctetStream = false;
        final boolean isCollection = true;
        String resultStr =
                sendRequest( AOMHttpClient.HTTP_METHOD.GET, dataModelHref, query, withReferencedHrefs, null, asOctetStream,
                        expectedCodes, returnedStatusCode, isCollection, isRef, cacheThenNetworkFirstCall, usePersistentStorage,
                        wasLoadedFromStorageAtomicRef );
        if ( TextUtils.isEmpty( resultStr ) )
        {
			/* If loading a reference list attribute but no references have been set before, the valid result is "[]".
			 * In that case it's not empty. */
            throw new RuntimeException( "Result is empty, which should never be the case" );
        }
        else
        {
            try
            {
                final JSONArray listOfModels = new JSONArray( resultStr );
                List<T> returnValue = new LinkedList<T>( );
                for ( int i = 0; i < listOfModels.length( ); i++ )
                {
                    T element;
                    try
                    {
                        final String jsonStr = listOfModels.getJSONObject( i ).toString( );
                        element = clazz.newInstance( );
                        element.fromJson( jsonStr );
                        returnValue.add( element );
                    }
                    catch ( Exception e )
                    {
                        throw new ApiomatRequestException( Status.INSTANTIATE_EXCEPTION,
							HttpURLConnection.HTTP_OK, e.getMessage( ) );
                    }
                }
                return returnValue;
            }
            catch ( JSONException e )
            {
                throw new RuntimeException( "JSON could not be parsed", e );
            }
        }
    }

	/**
	 * Loads existing class instance from the server in the background
	 *
	 * @param dataModelHref HREF of the class instances
	 * @param clazz the class of the objects
	 * @param query a query string to filter the results
	 * @param withReferencedHrefs set to true to get also all HREFs of referenced class instances
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param getCallback method which will called when request is finished
	 * @return A reference to the task that runs in the background
	 */
	public <T extends AbstractClientDataModel> AOMTask<List<T>> loadFromServerAsync(
		final String dataModelHref, final Class<T> clazz, final String query,
		final boolean withReferencedHrefs, final boolean isRef, final boolean usePersistentStorage,
		final AOMCallback<List<T>> getCallback )
	{
		if ( cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) )
		{
			AOMTask<List<T>> loadTask = new AOMTask<List<T>>( )
			{
				@Override
				public List<T> doRequest(AtomicReference<Boolean> wasLoadedFromStorageAtomicRef ) throws ApiomatRequestException
				{
					final boolean cacheThenNetworkFirstCall = true;
					return loadFromServer( dataModelHref, clazz, query, withReferencedHrefs,
						isRef, cacheThenNetworkFirstCall, usePersistentStorage, wasLoadedFromStorageAtomicRef );
				}
			};
			loadTask.execute( getCallback );
		}
		AOMTask<List<T>> loadTask = new AOMTask<List<T>>( )
		{
			@Override
			public List<T> doRequest(AtomicReference<Boolean> wasLoadedFromStorageAtomicRef ) throws ApiomatRequestException
			{
				final boolean cacheThenNetworkFirstCall = false;
				return loadFromServer( dataModelHref, clazz, query, withReferencedHrefs, isRef,
					cacheThenNetworkFirstCall, usePersistentStorage, wasLoadedFromStorageAtomicRef );
			}
		};
		loadTask.execute( getCallback );
		return loadTask;
	}

	/**
	 * Counts existing class instances from server
	 * The callback method will called after finished the request
	 *
	 * @param countHref HREF of count
	 * @param clazz class of the objects
	 * @param query a query string to filter the results
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param cacheThenNetworkFirstCall When the caching strategy is set to
	 *        CACHE_THEN_NETWORK, this flag indicates whether it's the first call or not.
	 *        For other caching strategies this should always be false.
	 * @return count of existing class instances from server
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public <T extends AbstractClientDataModel> Long countFromServer( final String countHref, final Class<T> clazz,
		final String query, final boolean isRef, final boolean cacheThenNetworkFirstCall )
		throws ApiomatRequestException
	{
		AtomicReference<Integer> returnedStatusCode = new AtomicReference<Integer>( );
		List<Integer> expectedCodes = new ArrayList<Integer>( );
		expectedCodes.add( HttpURLConnection.HTTP_OK );
		expectedCodes.add( HttpURLConnection.HTTP_NOT_MODIFIED );

		final boolean withReferencedHrefs = false;
		final boolean asOctetSTream = false;
		final boolean isCollection = true;
		final boolean usePersistentStorage = false;
		String resultStr =
			sendRequest( AOMHttpClient.HTTP_METHOD.GET, countHref, query, withReferencedHrefs, null, asOctetSTream,
				expectedCodes, returnedStatusCode, isCollection, isRef, cacheThenNetworkFirstCall,
				usePersistentStorage, null );
		if ( TextUtils.isEmpty( resultStr ) )
		{
			throw new RuntimeException( "Result is empty, which should never be the case" );
		}
		else
		{
			return Long.parseLong( resultStr );
		}
	}

	/**
	 * Counts existing class instances from server in background
	 * The callback method will called after finished the request
	 *
	 * @param countHref HREF of count
	 * @param clazz class of the objects
	 * @param query a query string to filter the results
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param getCallback method which will called if request is finished
	 */
	public <T extends AbstractClientDataModel> AOMTask<Long> countFromServerAsync( final String countHref,
		final Class<T> clazz,
		final String query,
		final boolean isRef,
		final AOMCallback<Long> getCallback )
	{
		AOMTask<Long> loadTask = new AOMTask<Long>( )
		{
			@Override
			public Long doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				final boolean cacheThenNetworkFirstCall = true;
				Long result = countFromServer( countHref, clazz, query, isRef, cacheThenNetworkFirstCall );

				if ( cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) )
				{
					AOMTask<Long> loadTask2 = new AOMTask<Long>( )
					{
						@Override
						public Long doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
						{
							final boolean cacheThenNetworkFirstCall = true;
							return countFromServer( countHref, clazz, query, isRef, cacheThenNetworkFirstCall );
						}
					};
					loadTask2.execute( getCallback );
				}
				return result;
			}
		};
		loadTask.execute( getCallback );
		return loadTask;
	}

	/**
	 * Loads a resource, e.g. an image with the user credentials as byte array.
	 * 
	 * @param href the URl of the image
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param cacheThenNetworkFirstCall When the caching strategy is set to
	 *        CACHE_THEN_NETWORK, this flag indicates whether it's the first call or not.
	 *        For other caching strategies this should always be false.
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return the resource as byte array
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public byte[ ] loadResource( String href, final boolean isRef, final boolean cacheThenNetworkFirstCall,
		final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
        return loadResource( href, isRef, cacheThenNetworkFirstCall, usePersistentStorage, null );
	}

    private byte[ ] loadResource( String href, final boolean isRef, final boolean cacheThenNetworkFirstCall,
                                  final boolean usePersistentStorage,
                                  final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
            throws ApiomatRequestException
    {
        if ( TextUtils.isEmpty( href ) )
        {
            throw new IllegalArgumentException( "href may not be null or empty" );
        }
        byte[ ] result = null;

        if ( this.getOfflineHandler( ) == null ) // no offline, just cache. Unknown if connected or not.
        {
			/* The following only applies to network_else_cache and it's only necessary for that one.
			 * If another strategy was set, there's a context (and offline handler). */
            // Send request, but take care of a timeout
            try
            {
                result = sendResourceRequestWithETagAndSave( href, usePersistentStorage, wasLoadedFromStorageAtomicRef );
            }
            catch ( IOException ex )
            {
                if ( ex instanceof ConnectException || ex instanceof SocketTimeoutException ||
                        ex instanceof SocketException )
                {
                    result = chooseStorageImpl( usePersistentStorage ).getStoredBinary( href );
                    setWasLoadedFromStorage(true, wasLoadedFromStorageAtomicRef);
					/* Throw an exception if no data was found in offline storage */
                    if ( result == null )
                    {
                        throw new ApiomatRequestException( Status.ID_NOT_FOUND_OFFLINE, HttpURLConnection.HTTP_OK );
                    }
                }
                else
                {
                    throw new RuntimeException( ex );
                }
            }
        }
        else
        // we have an offline handler
        {
            if ( this.getOfflineHandler( ).isConnected( ) == false )
            {
				/* If caching is deactivated and device is offline return immediately */
                if ( this.cacheStrategy == AOMCacheStrategy.NETWORK_ONLY )
                {
                    throw new ApiomatRequestException( Status.NO_NETWORK, HttpURLConnection.HTTP_OK );
                }
				/* Else: Any strategy that includes caching. Because the device is offline, get from cache now. */
                result = chooseStorageImpl( usePersistentStorage ).getStoredBinary( href );
                setWasLoadedFromStorage(true, wasLoadedFromStorageAtomicRef);
				/* Throw an exception if no data was found in offline storage */
                if ( result == null )
                {
                    throw new ApiomatRequestException( Status.ID_NOT_FOUND_OFFLINE, HttpURLConnection.HTTP_OK );
                }
                // Else don't do anything here, the result gets returned at the end
            }
            // connected
            else
            {
                if ( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_ELSE_NETWORK )
                        || ( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) && cacheThenNetworkFirstCall ) )
                {
					/* Read from cache */
                    result = chooseStorageImpl( usePersistentStorage ).getStoredBinary( href );
                    setWasLoadedFromStorage(true, wasLoadedFromStorageAtomicRef);
                }
                // Send a request in every case except 1) cache_else_network and a result was just retrieved; 2)
                // cache_then_network and it's the first call; 3) cache_then_network second call but there's a result
				/* Explanation of case 3:
				 * When a resource was found in cache, it doesn't make sense to load it from the network, because
				 * resources at URLs don't change.
				 * When a resource gets changed, the model receives a new URL. (in which case nothing gets found in the
				 * cache and a network request gets executed) */
                if ( ( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_ELSE_NETWORK ) && result != null ) == false
                        &&
                        ( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK )
                                && cacheThenNetworkFirstCall ) == false
                        &&
                        ( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) &&
                                cacheThenNetworkFirstCall == false && result != null ) == false )
                {
                    try
                    {
                        result = sendResourceRequestWithETagAndSave( href, usePersistentStorage, wasLoadedFromStorageAtomicRef );
                    }
                    catch ( ConnectException ex )
                    {
                        throw new ApiomatRequestException( Status.REQUEST_TIMEOUT, HttpURLConnection.HTTP_OK, ex.getMessage( ) );
                    }
                    catch ( SocketTimeoutException ex )
                    {
                        throw new ApiomatRequestException( Status.REQUEST_TIMEOUT, HttpURLConnection.HTTP_OK, ex.getMessage( ) );
                    }
                    catch ( SocketException ex )
                    {
                        throw new ApiomatRequestException( Status.REQUEST_TIMEOUT, HttpURLConnection.HTTP_OK, ex.getMessage( ) );
                    }
                }
            }
        }
        return result;
    }

	private byte[ ] sendResourceRequestWithETagAndSave( final String href, final boolean usePersistentStorage, final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
		throws ApiomatRequestException, ConnectException, SocketTimeoutException, SocketException
	{
		byte[ ] result;

		/* load ETag */
		AtomicReference<String> eTag = new AtomicReference<String>( );
		if ( useETag )
		{
			String eTagString = chooseStorageImpl( usePersistentStorage ).loadObjectLastModified( href );
			if ( eTagString != null )
			{
				eTag.set( eTagString );
			}
		}
		AtomicReference<Boolean> notModified = new AtomicReference<Boolean>( );

		/* send actual request */
		result = AOMHttpClientFactory.getAomHttpClient( ).sendActualResourceRequest( href, eTag, notModified );
        setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);

		/* If server responded with 304 not modified, get from storage.
		 *
		 * Boolean is used here - can be null */
		if ( notModified != null && notModified.get( ) != null && notModified.get( ) )
		{
			result = chooseStorageImpl( usePersistentStorage ).getStoredBinary( href );
            setWasLoadedFromStorage(true, wasLoadedFromStorageAtomicRef);
			/* if nothing was found in storage, make actual request, without any etag. but we need the etag from the reponse */
			if ( result == null )
			{
				eTag.set( null );
				result = AOMHttpClientFactory.getAomHttpClient( ).sendActualResourceRequest( href, eTag, null );
	            setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);
			}
		}
		else
		{
			if ( cacheStrategy != AOMCacheStrategy.NETWORK_ONLY && result != null && getWasLoadedFromStorage() == false )
			{
				AbstractStorage storage = chooseStorageImpl( usePersistentStorage );
				/* store ETag */
				if ( useETag && eTag != null && TextUtils.isEmpty( eTag.get( ) ) == false )
				{
					storage.storeObjectLastModified( href, eTag.get( ) );
				}
				/* store data */
				storage.storeBinary( href, result, "GET" );
			}
		}
		return result;
	}

	/**
	 * Loads a resource, e.g. an image with the user credentials as byte array in the background.
	 * 
	 * @param href the URl of the image
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param callback Method which will called after response from from server returned
	 * @return A reference to the task that runs in the background
	 */
	public AOMTask<byte[ ]> loadResourceAsync( final String href, final boolean isRef,
		final boolean usePersistentStorage,
		final AOMCallback<byte[ ]> callback )
	{
		AOMTask<byte[ ]> loadTask;
		if ( cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) )
		{
			loadTask = new AOMTask<byte[ ]>( )
			{
				@Override
				public byte[ ] doRequest(AtomicReference<Boolean> wasLoadedFromStorageAtomicRef ) throws ApiomatRequestException
				{
					final boolean cacheThenNetworkFirstCall = true;
					return loadResource( href, isRef, cacheThenNetworkFirstCall, usePersistentStorage, wasLoadedFromStorageAtomicRef );
				}
			};
			loadTask.execute( callback );
		}
		loadTask = new AOMTask<byte[ ]>( )
		{
			@Override
			public byte[ ] doRequest(AtomicReference<Boolean> wasLoadedFromStorageAtomicRef ) throws ApiomatRequestException
			{
				final boolean cacheThenNetworkFirstCall = false;
				return loadResource( href, isRef, cacheThenNetworkFirstCall, usePersistentStorage, wasLoadedFromStorageAtomicRef );
			}
		};
		loadTask.execute( callback );
		return loadTask;
	}

	/**
	 * Deletes the class instance from the server
	 *
	 * @param dataModel the class instance
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void deleteOnServer( AbstractClientDataModel dataModel, final boolean isRef )
		throws ApiomatRequestException
	{
		deleteOnServer( dataModel.getHref( ), isRef, getOfflineUsageForClass( dataModel.getClass( ) ) );
	}

	/**
	 * Deletes the class instance from the server on a background thread and not on UI
	 * 
	 * @param dataModel the class instance
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param callback method which will called after delete request is finished
	 * @return A reference to the task that runs in the background
	 */
	public AOMTask<Void> deleteOnServerAsync( AbstractClientDataModel dataModel, final boolean isRef,
		final AOMEmptyCallback callback )
	{
		final String href = dataModel.getHref( );
		return deleteOnServerAsync( href, isRef, getOfflineUsageForClass( dataModel.getClass( ) ), callback );
	}

	/**
	 * Deletes the class instance from the server based on its href
	 * 
	 * @param href the class instance href
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void deleteOnServer( String href, final boolean isRef, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		if ( TextUtils.isEmpty( href ) )
		{
			throw new ApiomatRequestException( Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_NO_CONTENT );
		}
		final boolean withReferencedHrefs = false;
		final boolean asOctetStream = false;
		final boolean isCollection = false;
		final boolean cacheThenNetworkFirstCall = false;
		sendRequest( AOMHttpClient.HTTP_METHOD.DELETE, href, "", withReferencedHrefs, null, asOctetStream,
			Arrays.asList( HttpURLConnection.HTTP_NO_CONTENT ), null, isCollection, isRef,
			cacheThenNetworkFirstCall, usePersistentStorage, null );
	}

	/**
	 * Deletes the class instance with given href from the server on a background thread and not on UI
	 * 
	 * @param href the class instance href
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param callback method which will called after delete request is finished
	 * @return A reference to the task that runs in the background
	 */
	public AOMTask<Void> deleteOnServerAsync( final String href, final boolean isRef,
		final boolean usePersistentStorage, final AOMEmptyCallback callback )
	{
		AOMTask<Void> deleteTask = new AOMTask<Void>( )
		{
			@Override
			public Void doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				deleteOnServer( href, isRef, usePersistentStorage );
				return null;
			}
		};
		deleteTask.execute( callback );
		return deleteTask;
	}

	/**
	 * Updates the class instance from the server
	 *
	 * @param dataModel the class instance
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void updateOnServer( AbstractClientDataModel dataModel, final boolean isRef )
		throws ApiomatRequestException
	{
		updateOnServer( dataModel.getHref( ), dataModel.toJson( ), isRef,
			getOfflineUsageForClass( dataModel.getClass( ) ) );
	}

	/**
	 * Updates the class instance from the server in a background task
	 *
	 * @param dataModel the class instance
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param cb The method which will called if response come back from server.
	 */
	public void updateOnServerAsync( AbstractClientDataModel dataModel, final boolean isRef, final AOMEmptyCallback cb )
	{
		updateOnServerAsync( dataModel, isRef, getOfflineUsageForClass( dataModel.getClass( ) ), cb );
	}

	/**
	 * Updates the class instance from the server
	 * 
	 * @param dataModel the class instance
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void updateOnServer( AbstractClientDataModel dataModel, final boolean isRef,
		final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		updateOnServer( dataModel.getHref( ), dataModel.toJson( ), isRef, usePersistentStorage );
	}

	/**
	 * Updates the class instance from the server in a background task
	 *
	 * @param dataModel the class instance
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 */
	public void updateOnServerAsync( AbstractClientDataModel dataModel, final boolean isRef,
		final boolean usePersistentStorage, AOMEmptyCallback callback )
	{
		updateOnServerAsync( dataModel.getHref( ), dataModel.toJson( ), isRef, usePersistentStorage, callback );
	}

	/**
	 * Send a PUT request with given json data to the server
	 * 
	 * @param href
	 * @param json
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void updateOnServer( final String href, final String json,
		final boolean isRef, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		if ( TextUtils.isEmpty( href ) )
		{
			throw new ApiomatRequestException( Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_OK );
		}
		try
		{
			final boolean withReferencedHrefs = false;
			final boolean asOctetStream = false;
			final boolean isCollection = false;
			final boolean cacheThenNetworkFirstCall = false;
			sendRequest( AOMHttpClient.HTTP_METHOD.PUT, href, "", withReferencedHrefs, json.getBytes( "UTF-8" ),
				asOctetStream, Arrays.asList( HttpURLConnection.HTTP_OK ), null, isCollection,
				isRef, cacheThenNetworkFirstCall, usePersistentStorage, null );
		}
		catch ( UnsupportedEncodingException e )
		{
			throw new ApiomatRequestException( Status.UNSUPPORTED_ENCODING, HttpURLConnection.HTTP_OK, e.getMessage( ) );
		}
	}

	/**
	 * Send a PUT request with given json data to the server.
	 * Request works on background thread and not on the UI thread
	 * 
	 * @param href HREF of the class instance to put (or the address to put the data to)
	 * @param json The JSON string which will be send to server
	 * @param isRef A flag indicating whether the call is for a reference or not
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param callback The method which will called if response come back from server.
	 * @return A reference to the task that runs in the background.
	 * 
	 */
	public AOMTask<Void> updateOnServerAsync( final String href, final String json, final boolean isRef,
		final boolean usePersistentStorage, final AOMEmptyCallback callback )
	{
		AOMTask<Void> updateTask = new AOMTask<Void>( )
		{
			@Override
			public Void doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				updateOnServer( href, json, isRef, usePersistentStorage );
				return null;
			}
		};
		updateTask.execute( callback );
		return updateTask;
	}

	public AOMOfflineHandler getOfflineHandler( )
	{
		return offlineHandler;
	}

	private String postOnServer( final String dataModelHref, final String data, final boolean isCollection,
		final boolean isRef, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		if ( TextUtils.isEmpty( dataModelHref ) )
		{
			throw new ApiomatRequestException( Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_CREATED );
		}
		try
		{
			final boolean withReferencedHrefs = false;
			final boolean asOctetStream = false;
			final boolean cacheThenNetworkFirstCall = false;
			return sendRequest( AOMHttpClient.HTTP_METHOD.POST, dataModelHref, "", withReferencedHrefs,
				data.getBytes( "UTF-8" ), asOctetStream, Arrays.asList( HttpURLConnection.HTTP_CREATED ), null,
				isCollection, isRef, cacheThenNetworkFirstCall, usePersistentStorage, null );
		}
		catch ( UnsupportedEncodingException e )
		{
			throw new ApiomatRequestException(
				Status.UNSUPPORTED_ENCODING, HttpURLConnection.HTTP_OK,
				e.getMessage( ) );
		}
	}

	private String postStaticDataOnServer( final String staticDataHref, final byte[] entity,
		final boolean isImage, final boolean isRef, final boolean usePersistentStorage )
		throws ApiomatRequestException
	{
		String location;
		if ( TextUtils.isEmpty( staticDataHref ) )
		{
			throw new ApiomatRequestException( Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_CREATED );
		}
		if ( Datastore.getInstance( ).sendOffline( "POST" ) )
		{
			location = Datastore.getInstance( ).getOfflineHandler( ).addTask(
				"POST", staticDataHref, entity, isImage, isRef, usePersistentStorage );
		}
		else
		{
			final boolean withReferencedHrefs = true;
			final boolean asOctetStream = true;
			final boolean isCollection = false;
			final boolean cacheThenNetworkFirstCall = false;
			location = sendRequest( AOMHttpClient.HTTP_METHOD.POST, staticDataHref, "", withReferencedHrefs, entity,
				asOctetStream, Arrays.asList( HttpURLConnection.HTTP_CREATED ), null, isCollection,
				isRef, cacheThenNetworkFirstCall, usePersistentStorage, null );
		}
		return location;
	}

	public static String fixHref( String baseUrl, String href )
	{
		if ( href.startsWith( "http" ) )
		{
			return href;
		}

		if ( href.startsWith( "/apps" ) )
		{
			return baseUrl.substring( 0, baseUrl.indexOf( "/apps" ) )
				+ href;
		}

		return baseUrl + "/" + href;
	}

	public String createStaticDataHref( final boolean image )
	{
		final StringBuilder sb = new StringBuilder( );
		sb.append( this.baseUrl );
		sb.append( "/data/" );
		sb.append( image ? "images" : "files" );
		sb.append( '/' );
		return sb.toString( );
	}

	public String createModelHref( final String moduleName, final String simpleModelName )
	{
		final StringBuilder sb = new StringBuilder( );
		sb.append( this.baseUrl );
		sb.append( "/models/" );
		sb.append( moduleName );
		sb.append( '/' );
		sb.append( simpleModelName );
		return sb.toString( );
	}

	/**
	 * Creates a href for the class REST resource with parameters.
	 * Param "withReferencedHrefs" gets set to false.
	 *
	 * Don't use this method for appending a model object ID!
	 *
	 * @param moduleName e.g. "Basics"
	 * @param simpleModelName e.g. "User"
	 * @param withReferencedHrefs
	 * @param query
	 * @return The created href.
	 */
	public String createModelHrefWithParams( final String moduleName, final String simpleModelName, final boolean withReferencedHrefs, final String query )
	{
		final StringBuilder sb = new StringBuilder( createModelHref( moduleName, simpleModelName ) );
		sb.append( "?withReferencedHrefs=" );
		sb.append( String.valueOf( withReferencedHrefs ) );
		sb.append( "&q=" );
		if (query != null)
		{
			sb.append( query );
		}
		return sb.toString();
	}

	private String sendRequest( final AOMHttpClient.HTTP_METHOD method,
		String href,
		final String query,
		boolean withReferencedHrefs,
		byte[ ] postEntity,
		final boolean asOctetStream,
		final List<Integer> expectedCodes,
		AtomicReference<Integer> returnedStatusCode,
		boolean isCollection,
		final boolean isRef,
		final boolean cacheThenNetworkFirstCall,
		final boolean usePersistentStorage,
        final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef)
		throws ApiomatRequestException
	{
		String resultStr = null;

		try
		{
			href = fixHref( this.baseUrl, href );
			URL url = new URL( href );
			if ( AOMHttpClient.HTTP_METHOD.GET.equals( method ) )
			{
				url = new URL( Uri.parse( url.toString( ) ).buildUpon( )
					.appendQueryParameter( "withReferencedHrefs", String.valueOf( withReferencedHrefs ) )
					.appendQueryParameter( "q", query == null ? "" : query ).toString( ) );
				String urlString = url.toExternalForm( );
				/* Offline handling */

				if ( this.getOfflineHandler( ) == null ) // no offline, just cache. Unknown if connected or not.
				{
					/* The following only applies to network_else_cache and it's only necessary for that one.
					 * If another strategy was set, there's a context (and offline handler). */
					// Send request, but take care of a timeout
					final boolean usePersistentStorage2 = false;
					try
					{
						resultStr = sendWithETagAndSave( method, url, postEntity, asOctetStream, expectedCodes,
							returnedStatusCode, isCollection, isRef, usePersistentStorage2, wasLoadedFromStorageAtomicRef );
					}
					catch ( ApiomatRequestException ex )
					{
						if ( ex.getStatus( ) == Status.NO_NETWORK )
						{
							resultStr = readFromStorage( urlString, isCollection, isRef, usePersistentStorage2, wasLoadedFromStorageAtomicRef );
							/* Throw an exception if no data was found in offline storage */
							if ( resultStr == null )
							{
								throw new ApiomatRequestException( Status.ID_NOT_FOUND_OFFLINE, HttpURLConnection.HTTP_OK );
							}
						}
						else
						{
							throw ex;
						}
					}
					/* Avoid code duplication. Convert to multiple catch in Java 1.7 (API Lvl 19+) */
					catch ( Exception ex )
					{
						if ( ex instanceof ConnectException || ex instanceof SocketTimeoutException ||
							ex instanceof SocketException )
						{
							resultStr = readFromStorage( urlString, isCollection, isRef, usePersistentStorage2, wasLoadedFromStorageAtomicRef );
							/* Throw an exception if no data was found in offline storage */
							if ( resultStr == null )
							{
								throw new ApiomatRequestException( Status.ID_NOT_FOUND_OFFLINE, HttpURLConnection.HTTP_OK );
							}
						}
						else if ( ex instanceof RuntimeException )
						{
							throw ( RuntimeException ) ex;
						}
						else
						{
							throw new RuntimeException( ex );
						}
					}
				}
				else
				// we have an offline handler
				{
					boolean connected = this.getOfflineHandler( ).isConnected( );
					if ( connected == false )
					{
						/* If caching is deactivated and device is offline return immediately */
						if ( this.cacheStrategy == AOMCacheStrategy.NETWORK_ONLY )
						{
							throw new ApiomatRequestException( Status.NO_NETWORK, expectedCodes.get( 0 ) );
						}
						/* Else: Any strategy that includes caching. Because the device is offline, get from cache now. */
						resultStr = readFromStorage( urlString, isCollection, isRef, usePersistentStorage, wasLoadedFromStorageAtomicRef );
						/* Throw an exception if no data was found in offline storage */
						if ( resultStr == null )
						{
							throw new ApiomatRequestException( Status.ID_NOT_FOUND_OFFLINE, expectedCodes.get( 0 ) );
						}
					}
					// connected
					else
					{
						if ( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_ELSE_NETWORK )
							||
							( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) && cacheThenNetworkFirstCall ) )
						{
							/* Read from cache */
							resultStr = readFromStorage( urlString, isCollection, isRef, usePersistentStorage, wasLoadedFromStorageAtomicRef );
						}
						// send a request in every case except 1) cache_else_network and a result was just retrieved; 2)
						// cache_then_network and it's the first call
						if ( ( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_ELSE_NETWORK ) && resultStr != null ) == false
							&&
							( this.cacheStrategy.equals( AOMCacheStrategy.CACHE_THEN_NETWORK ) && cacheThenNetworkFirstCall == true ) == false )
						{
							resultStr = sendWithETagAndSave( method, url, postEntity, asOctetStream, expectedCodes,
								returnedStatusCode, isCollection, isRef, usePersistentStorage, wasLoadedFromStorageAtomicRef );
						}
					}
				}
			}
			else if ( AOMHttpClient.HTTP_METHOD.DELETE.equals( method ) )
			{
				if ( isCollection ) // Should never be the case / not possible
				{
					throw new RuntimeException( "An SDK internal error occured: Collections can't be deleted" );
				}
				resultStr = AOMHttpClientFactory.getAomHttpClient( ).sendActualRequest( method, url, postEntity,
					asOctetStream, expectedCodes, returnedStatusCode, null, null );
				/* delete from storage */
				deleteObjectFromStorage( url.toExternalForm( ), isRef );
			}
			else if ( AOMHttpClient.HTTP_METHOD.PUT.equals( method ) )
			{
				/* When updating an object, the offline object needs to be updated as well for this and maybe other
				 * cases:
				 * When using cache_else_network, loading an obj (gets stored in cache), updating it and then loading
				 * again
				 * -> loads from cache, so it's important the update has been applied to the cached object */
				if ( this.cacheStrategy != AOMCacheStrategy.NETWORK_ONLY )
				{
					String dataString;
					try
					{
						dataString = new String( postEntity, "UTF-8" );
						/* modify lastmodified */
						try
						{
							JSONObject dataJson = new JSONObject( dataString );
							dataJson.put( "lastModifiedAt", System.currentTimeMillis( ) );
							dataString = dataJson.toString( );
						}
						catch ( JSONException ex )
						{
							Log.w( "Datastore", "During updating the offline stored object the lastmodified attribute" +
								"couldn't be updated due to a JSONException." +
								"The update will continue nevertheless.", ex );
						}
						/* no need to differentiate between isRef or not at this point
						 * - so actualHrefs can be null and actualHref = url */
						String putUrl = url.toExternalForm( );
						final boolean isCollection2 = false;
						storeObjectOrCollection( method, putUrl, dataString, null, putUrl, isCollection2, isRef,
							usePersistentStorage );
					}
					catch ( UnsupportedEncodingException ex )
					{
						Log.w( "Datastore", "Update couldn't be applied to offline stored object due to an" +
							"UnsupportedEncodingException, but an online update will be attempted.", ex );
					}
				}
				resultStr = AOMHttpClientFactory.getAomHttpClient( ).sendActualRequest( method, url, postEntity,
					asOctetStream, expectedCodes, returnedStatusCode, null, null );
                setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);
			}
			else
			// POST
			{
				resultStr = AOMHttpClientFactory.getAomHttpClient( ).sendActualRequest( method, url, postEntity,
					asOctetStream, expectedCodes, returnedStatusCode, null, null );
                setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);
			}
		}
		catch ( MalformedURLException e )
		{
			throw new ApiomatRequestException( Status.WRONG_URI_SYNTAX, expectedCodes.get( 0 ), e.getMessage( ) );
		}
		catch ( ConnectException e )
		{
			throw new ApiomatRequestException( Status.REQUEST_TIMEOUT, expectedCodes.get( 0 ), e.getMessage( ) );
		}
		catch ( SocketTimeoutException e )
		{
			throw new ApiomatRequestException( Status.REQUEST_TIMEOUT, expectedCodes.get( 0 ), e.getMessage( ) );
		}
		catch ( SocketException e )
		{
			throw new ApiomatRequestException( Status.REQUEST_TIMEOUT, expectedCodes.get( 0 ), e.getMessage( ) );
		}
		return resultStr;
	}

	/* Currently used for GET only */
	private String sendWithETagAndSave( final AOMHttpClient.HTTP_METHOD method,
		final URL url,
		final byte[ ] postEntity,
		final boolean asOctetStream,
		final List<Integer> expectedCodes,
		final AtomicReference<Integer> returnedStatusCode,
		final boolean isCollection,
		final boolean isRef,
		final boolean usePersistentStorage,
        final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef)
		throws ApiomatRequestException, ConnectException, SocketTimeoutException, SocketException
	{
		String resultStr;
		final String urlString = url.toExternalForm( );
		final AbstractStorage storage = chooseStorageImpl( usePersistentStorage );

		// Load ETag
		final AtomicReference<String> eTag = new AtomicReference<String>( );
		if ( useETag )
		{
			AbstractStorage eTagStorage = chooseStorageImpl( usePersistentStorage );
			String eTagString = null;
			if ( isCollection )
			{
				eTagString = eTagStorage.loadCollectionETag( urlString );
			}
			else
			{
				eTagString = eTagStorage.loadObjectLastModified( urlString );
			}
			eTag.set( eTagString );
		}

		/* Delta-Sync */
		AtomicReference<String> deltaSync = null;
		String storageResultStr = null;
		if ( useDeltaSync && isCollection && cacheStrategy.equals( AOMCacheStrategy.NETWORK_ONLY ) == false )
		{
			storageResultStr =
				storageResultStr == null ? readFromStorage( urlString, isCollection, isRef, usePersistentStorage, wasLoadedFromStorageAtomicRef )
					: storageResultStr;
			if ( TextUtils.isEmpty( storageResultStr ) == false )
			{
				try
				{
					final JSONArray ja = new JSONArray( storageResultStr );
					/* Only send deltaSync header if we have cached objects. */
					if ( ja.length( ) > 0 )
					{
						final Map<String, String> hrefLMMap = JsonHelper.getHrefLMMapFromJsonArrayWithAomModels( ja );
						final JSONObject hrefLMJsonObject = new JSONObject( hrefLMMap );
						deltaSync = new AtomicReference<String>( );
						deltaSync.set( hrefLMJsonObject.toString( ) );
					}
				}
				catch ( JSONException e )
				{
					// deltaSyncValue stays null, which is ok
				}
			}
		}

		// Actual request
		resultStr = AOMHttpClientFactory.getAomHttpClient( ).sendActualRequest( method, url, postEntity, asOctetStream,
			expectedCodes, returnedStatusCode, eTag, deltaSync );
        setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);

		/* In the case of 304 (currently only possible with strategy network_else_cache),
		 * when the result is null or empty: get the data from cache */
		if ( TextUtils.isEmpty( resultStr ) && returnedStatusCode != null
			&& returnedStatusCode.get( ) == HttpURLConnection.HTTP_NOT_MODIFIED )
		{
			resultStr =
				storageResultStr =
					storageResultStr == null ? readFromStorage( urlString, isCollection, isRef, usePersistentStorage, wasLoadedFromStorageAtomicRef )
						: storageResultStr;
            /* wasLoadedFromStorageAtomicRef gets set within readFromStorage */

			/* If no offline data could be found, the network request needs to be repeated, but this time without
			 * Etag/Not-Modified header to get all data */
			if ( resultStr == null )
			{
				/* don't send the etag header in the request, but we need the etag from the response */
				eTag.set( null );
				resultStr = AOMHttpClientFactory.getAomHttpClient( ).sendActualRequest( method, url, postEntity,
					asOctetStream, expectedCodes, returnedStatusCode, eTag, null );
                setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);
			}
		}

		/* Note: The result string can be empty for 204 responses,
		 * e.g. when a reference attribute gets fetched, but there's no reference attached. */

		/* For references the actual href needs to be extracted to save a mapping later */
		String actualHref = null;
		List<String> actualHrefs = null;
		if ( isRef && TextUtils.isEmpty( resultStr ) == false )
		{
			try
			{
				if ( isCollection )
				{
					actualHrefs = JsonHelper.getHrefListFromJsonArrayWithAomModels( resultStr );
				}
				else
				{
					final JSONObject resultJO = new JSONObject( resultStr );
					actualHref = resultJO.optString( "href", null );
				}
			}
			catch ( JSONException e )
			{
				throw new RuntimeException( "JSON could not be parsed", e );
			}
		}

		// Save ETag
		if ( useETag && returnedStatusCode != null && returnedStatusCode.get( ) == HttpURLConnection.HTTP_OK &&
			eTag != null && TextUtils.isEmpty( eTag.get( ) ) == false && wasLoadedFromStorage == false )
		{
			if ( isCollection )
			{
				storage.storeCollectionETag( urlString, eTag.get( ) );
				/* Also store the lastModifieds of the objects within the collection,
				 * so that a load of an object that was in a requested collection before doesn't have to be reloaded.
				 * In the actual process of storing the collection the single objects get saved too.
				 * So this works well together. */
				try
				{
					JSONArray collection = new JSONArray( resultStr );
					for ( int i = 0; i < collection.length( ); i++ ) // no foreach for JSONArray
					{
						JSONObject object = ( JSONObject ) collection.get( i );
						String objectHref = object.getString( "href" );
						String lastModified = object.getString( "lastModifiedAt" );
						storage.storeObjectLastModified( objectHref, lastModified );
					}
				}
				catch ( JSONException ex )
				{
					Log.w(
						"Datastore",
						"An error occured during parsing the collection from the response to save the contained objects eTag: " +
							ex.getMessage( ) );
				}
			}
			else
			{
				storage.storeObjectLastModified( urlString, eTag.get( ) );
			}
		}

		/* In the case of DeltaSync, reconstruct the actual response. Only necessary if not 304.
		 * Also only necessary if the deltaSync AtomicReference is not null, because that
		 * means we never sent a DeltaSync header and thus the response can't contain DeltaSync info as well. */
		if ( useDeltaSync && isCollection && cacheStrategy.equals( AOMCacheStrategy.NETWORK_ONLY ) == false
			&& returnedStatusCode != null && returnedStatusCode.get( ) != HttpURLConnection.HTTP_NOT_MODIFIED
			&& deltaSync != null )
		{
			try
			{
				/* Load from cache */
				storageResultStr =
					storageResultStr == null ? readFromStorage( urlString, isCollection, isRef, usePersistentStorage, wasLoadedFromStorageAtomicRef )
						: storageResultStr;
				List<JSONObject> cachedJsonObjectList = null;
				if ( TextUtils.isEmpty( storageResultStr ) == false )
				{
					final JSONArray ja = new JSONArray( storageResultStr );
					if ( ja.length( ) > 0 )
					{
						cachedJsonObjectList = JsonHelper.getJsonObjectListFromJsonArrayWithAomModels( ja );
						/* Delete what's in the response delete header. */
						final JSONArray deltaDeleteIds = new JSONArray( deltaSync.get( ) );
						if ( deltaDeleteIds.length( ) > 0 )
						{
							String currentHref;
							String currentId;
							/* Use iterator to loop and be able to remove elements without ConcurrentModificationException */
							for (final Iterator iterator = cachedJsonObjectList.iterator(); iterator.hasNext(); )
							{
								JSONObject jo = (JSONObject) iterator.next();
								currentHref = jo.getString( "href" );
								currentId = currentHref.substring( currentHref.lastIndexOf( "/" ) + 1 );
								if ( JsonHelper.stringJsonArrayContains( deltaDeleteIds, currentId ) )
								{
									iterator.remove( );
								}
							}
						}
					}
				}
				/* Add what's in the response - but only necessary if we have cached objects */
				if ( cachedJsonObjectList != null
					&& cachedJsonObjectList.size( ) > 0
					&& TextUtils.isEmpty( resultStr ) == false )
				{
                    setWasLoadedFromStorage(true, wasLoadedFromStorageAtomicRef);
					final List<JSONObject> returnedJsonObjectList =
						JsonHelper.getJsonObjectListFromJsonArrayWithAomModels( new JSONArray( resultStr ) );
					for ( final JSONObject jo : returnedJsonObjectList )
					{
						cachedJsonObjectList.add( jo );
					}
					if ( returnedJsonObjectList.size( ) > 0 )
					{
						/* It's actually a mix of both, but false must be set so the result gets stored in next step */
                        setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);
					}
					resultStr = cachedJsonObjectList.toString( );
				}
			}
			catch ( JSONException e )
			{
				Log.w( "Datastore", "JSONException during processing for delta sync: " + e.getMessage( ) );
				resultStr =
					AOMHttpClientFactory.getAomHttpClient( ).sendActualRequest( method, url, postEntity, asOctetStream,
						expectedCodes,
						returnedStatusCode, null, null );
                setWasLoadedFromStorage(false, wasLoadedFromStorageAtomicRef);
			}
		}

		/* Store in cache or offline (chosen by chooseStorageImpl) and differentiate between object and collection
		 * But only when strategy is not "NETWORK_ONLY" */
		if ( cacheStrategy != AOMCacheStrategy.NETWORK_ONLY && wasLoadedFromStorage == false )
		{
			final boolean isCollection2 = resultStr.startsWith( "[" ); // can differ from isCollection
			storeObjectOrCollection( method, urlString, resultStr, actualHrefs, actualHref, isCollection2, isRef,
				usePersistentStorage );
		}

		return resultStr;
	}

	private void storeObjectOrCollection( final AOMHttpClient.HTTP_METHOD method,
		final String urlString,
		final String resultStr,
		final List<String> actualHrefs,
		final String actualHref,
		final boolean isCollection,
		final boolean isRef,
		final boolean usePersistentStorage )
	{
		final AbstractStorage storage = chooseStorageImpl( usePersistentStorage );
		if ( isCollection )
		{
			storage.storeCollection( urlString, resultStr );
			if ( isRef && actualHrefs != null )
			{
				// also save refhref mapping (necessary for delete)
				for ( String currentActualHref : actualHrefs )
				{
					final String id =
						currentActualHref.substring( currentActualHref.lastIndexOf( "/" ) + 1,
							currentActualHref.length( ) );
					final String mappingHref = addIdToRefHref( urlString, id );
					storage.storeObject( mappingHref, currentActualHref, "GET" );
				}
			}
		}
		else
		{
			if ( isRef && TextUtils.isEmpty( actualHref ) == false )
			{
				// two mappings are needed, one for later loading, one for later deleting
				// Mapping 1, e.g. /session/123/conference -> /session/123/conference/abc
				final String id = actualHref.substring( actualHref.lastIndexOf( "/" ) + 1, actualHref.length( ) );
				final String mapping1Href =
					urlString.contains( "?" ) ? urlString.substring( 0, urlString.indexOf( "?" ) ) : urlString;
				final String intermediateHref = addIdToRefHref( urlString, id );
				storage.storeObject( mapping1Href, intermediateHref, method.name( ) );
				// Mapping 2, e.g. /session/123/conference/abc -> /conference/abc
				storage.storeObject( intermediateHref, actualHref, method.name( ) );
				// Real href, e.g. /conference/abc -> body
				storage.storeObject( actualHref, resultStr, method.name( ) );
			}
			else
			{
				storage.storeObject( urlString, resultStr, method.name( ) );
			}
		}
	}

	private String addIdToRefHref( String refHref, String id )
	{
		// remove params and add id
		// Remove url parameters here, because otherwise, when deleting an object later, there are no params and the url
		// can't be found
		refHref = refHref.contains( "?" ) ? refHref.substring( 0, refHref.indexOf( "?" ) ) : refHref;
		refHref = refHref + "/" + id;
		return refHref;
	}

	/* Differentiate between cache vs. offline (via chooseStorageImpl) and object vs. collection */
	private String readFromStorage( final String hrefOrUrl,
		final boolean isCollection,
		final boolean isRef,
		final boolean usePersistentStorage,
        final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef)
	{
		final AbstractStorage storage = chooseStorageImpl( usePersistentStorage );
		return readFromStorage( hrefOrUrl, isCollection, isRef, storage, wasLoadedFromStorageAtomicRef );
	}

	/* In case usePersistentStorage is false and the cache gets chosen, it will still look up the SQLite db if the
	 * object /
	 * collection isn't found in the cache.
	 * UNLESS no context is available. In that case, the pure in-memory cache one gets chosen. */
	private AbstractStorage chooseStorageImpl( final boolean usePersistentStorage )
	{
		if ( usePersistentStorage && this.getOfflineHandler( ) == null )
		{
			// Context must be set
			throw new RuntimeException(
				"No context is set. Please set the context in the Datastore with setContext(Context)" );
		}

		if ( usePersistentStorage )
		{
			return SQLiteStorage.getInstance( this.getOfflineHandler( ).getContext( ) );
		}
		else
		{
			if ( this.getOfflineHandler( ) == null )
			{
				return InMemoryCache.getInstance( );
			}
			else
			{
				return MemoryElseOfflineStorage.getInstance( this.getOfflineHandler( ).getContext( ) );
			}
		}
	}

	/* Differentiate between object or collection */
	private String readFromStorage( final String hrefOrUrl,
		final boolean isCollection,
		final boolean isRef,
		final AbstractStorage storageImpl,
        final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef)
	{
		String result = null;
        setWasLoadedFromStorage(true, wasLoadedFromStorageAtomicRef);
		if ( isCollection )
		{
			result = storageImpl.getStoredCollection( hrefOrUrl );
		}
		else
		{
			result = storageImpl.getStoredObject( hrefOrUrl );
			if ( isRef && TextUtils.isEmpty( result ) == false )
			{
				// First load was only for mapping
				result = storageImpl.getStoredObject( result );
				// second depends - when storage was during offline-post, then not
				if ( TextUtils.isEmpty( result ) == false && result.startsWith( "{" ) == false )
				{
					result = storageImpl.getStoredObject( result );
				}
			}
		}
		return result;
	}

	/**
	 * Deletes an object from offline storage (from both - cache and persistent storage)
	 *
	 * @param href The href of the object
	 * @param isRef Indicates whether the object is a reference or not
	 * @return If an object was deleted
	 */
	public boolean deleteObjectFromStorage( String href, final boolean isRef )
	{
		if (TextUtils.isEmpty( href ) || isHref( href ) == false)
		{
			throw new IllegalArgumentException( "Argument href is null, empty or not an actual href." );
		}
		boolean result = false;
		final List<AbstractStorage> storages = getStorages();
		for ( int i = 0; i < storages.size(); i++ ) // can't use foreach here
		{
			final AbstractStorage storage = storages.get(i);
			/* href can be for object, image/file or ref.
			 * In case of ref, a mapping needs to be considered.
			 * But not if it's an image or a file (isRef is true for another reason)
			 */
			if ( isRef )
			{
				// mapping
				int levels = 0;
				final String refHref = new String(href);
				String refHref2 = null;
				href = storage.getStoredObject( href );
				if ( TextUtils.isEmpty( href ) == false && isHref( href ) )
				{
					levels++;
					/* another mapping level ? */
					refHref2 = new String(href);
					href = storage.getStoredObject( refHref2 );
					if ( TextUtils.isEmpty( href ) == false && isHref( href ) )
					{
						levels++;
					}
					else
					{
						/* to deep -> reset href */
						href = refHref2;
					}
				}
				else
				{
					/* to deep -> reset href */
					href = refHref;
				}
				/* Always delete the final object */
				result = result || storage.removeObject( href );
				/* Also delete mappings, depending on level.
				 * No need to check refHref or refHref2 for emptyness or content
				 * because they're derived from href which gets checked for every level (see above) */
				if (levels > 0)
				{
					storage.removeObject( refHref );
				}
				if (levels > 1)
				{
					storage.removeObject( refHref2 );
				}
			}
			else // no ref -> no mapping
			{
				result = result || storage.removeObject( href );
			}
		}

		return result;
	}

	/**
	 * Deletes a collection from offline storage (from both - cache and persistent storage)
	 *
	 * @param href The href of the collection
	 */
	public int deleteCollectionFromStorage( final String href )
	{
		int result = 0;
		final List<AbstractStorage> storages = getStorages();
		/* In case one storage is MemoryElseOfflineStorage and the other is SQLiteStorage,
		 * the same delete query gets executed twice.
		 * But that's ok (second delete doesn't affect any rows) and shouldn't be changed
		 * in case one of their implementations gets changed in the future */
		for ( AbstractStorage storage : storages )
		{
			result += storage.removeCollection( href );
		}
		return result;
	}

	private List<AbstractStorage> getStorages()
	{
		final List<AbstractStorage> storages = new ArrayList<AbstractStorage>( );
		storages.add( chooseStorageImpl( false ) ); // cache
		if ( this.getOfflineHandler( ) != null )
		{
			storages.add( chooseStorageImpl( true ) ); // persistent storage
		}
		return storages;
	}

	/**
	 * Return true if we have to use offline handler
	 * The decision depends on selected {@link com.apiomat.frontend.Datastore.AOMCacheStrategy} and given _method
	 * 
	 * @param _method The HTTP method
	 * @return true if we will use offline store else false
	 */
	public boolean sendOffline( String _method )
	{
		return cacheStrategy.equals( AOMCacheStrategy.NETWORK_ONLY ) == false &&
			this.offlineHandler != null && this.offlineHandler.isConnected( ) == false;
	}

	public static enum AOMCacheStrategy
	{
		NETWORK_ONLY, // Don't use caching (on save as well as read)
		NETWORK_ELSE_CACHE, // Use the cache only if the server is unreachable or returns 304
		CACHE_ELSE_NETWORK, // Use the cache, but if nothing is found there send a request
		CACHE_THEN_NETWORK; // First read from cache, than send a request to the server
	}

	/**
	 * @deprecated Use {@link com.apiomat.frontend.Datastore.AOMCacheStrategy} instead
	 */
	@Deprecated
	public static enum AOMOfflineStrategy
	{
		NO_OFFLINE_HANDLING,
		USE_OFFLINE_CACHE
	}

	/**
	 * Returns the authentication type that the Datastore currently uses
	 * 
	 * @return the authentication type that the Datastore currently uses
	 */
	public AuthType getAuthType( )
	{
		return this.authType;
	}

	/**
	 * Returns a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 * "model")
	 * 
	 * @return a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 *         "model")
	 * @throws ApiomatRequestException When the request fails or the Datastore has not been
	 *         properly configured
	 *
	 * @deprecated Use {@link #requestTokenContainer()} instead
	 */
	@Deprecated
	public Map<String, String> requestSessionToken( ) throws ApiomatRequestException
	{
		if ( this.authType != AuthType.USERNAME_PASSWORD )
		{
			throw new ApiomatRequestException( Status.BAD_DATASTORE_CONFIG );
		}
		wasLoadedFromStorage = false;
		JSONObject originalTokenJson = AOMHttpClientFactory.getAomHttpClient( ).sendActualTokenRequest( );
		return convertOriginalTokenMapToSdkTokenMap( originalTokenJson );
	}

	/**
	 * Requests a TokenContainer
	 *
	 * @return A TokenContainer
	 * @throws ApiomatRequestException When the request fails or the Datastore has not been configured properly
	 */
	public TokenContainer requestTokenContainer( ) throws ApiomatRequestException
	{
		return convertTokenMapToTokenContainer( requestSessionToken( ) );
	}

	/**
	 * Returns a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 * "model")
	 * 
	 * @param callback The callback
	 * @return a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 *         "model")
	 *
	 * @deprecated Will be removed. Use the methods in the User class instead.
	 */
	@Deprecated
	public AOMTask<Map<String, String>> requestSessionTokenAsync( final AOMCallback<Map<String, String>> callback )
	{
		AOMTask<Map<String, String>> requestTask = new AOMTask<Map<String, String>>( )
		{
			@Override
			public Map<String, String> doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				return requestSessionToken( );
			}
		};
		requestTask.execute( callback );
		return requestTask;
	}

	/**
	 * Returns a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 * "model")
	 *
	 * @param refreshToken The refresh token to use for requesting a new session token
	 * @return a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 *         "model")
	 * @throws ApiomatRequestException
	 *
	 * @deprecated Use {@link #requestTokenContainer(String)} instead
	 */
	@Deprecated
	public Map<String, String> requestSessionToken( final String refreshToken ) throws ApiomatRequestException
	{
		wasLoadedFromStorage = false;
		JSONObject originalTokenJson = AOMHttpClientFactory.getAomHttpClient( ).sendActualTokenRequest( refreshToken );
		return convertOriginalTokenMapToSdkTokenMap( originalTokenJson );
	}

	/**
	 * Requests a TokenContainer with a refresh token
	 *
	 * @param refreshToken The refresh token to use for requesting a new TokenContainer
	 * @return A TokenContainer
	 * @throws ApiomatRequestException
	 */
	public TokenContainer requestTokenContainer( final String refreshToken ) throws ApiomatRequestException
	{
		return convertTokenMapToTokenContainer( requestSessionToken( refreshToken ) );
	}

	/**
	 * Returns a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 * "model")
	 *
	 * @param refreshToken The refresh token to use for requesting a new session token
	 * @param callback The callback
	 * @return a map with tokens and other values (keys: "sessionToken", "refreshToken", "expirationDate", "module",
	 *         "model")
	 *
	 * @deprecated Will be removed. Use the methods in the User class instead.
	 */
	@Deprecated
	public AOMTask<Map<String, String>> requestSessionTokenAsync( final String refreshToken,
		final AOMCallback<Map<String, String>> callback )
	{
		AOMTask<Map<String, String>> requestTask = new AOMTask<Map<String, String>>( )
		{
			@Override
			public Map<String, String> doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				return requestSessionToken( refreshToken );
			}
		};
		requestTask.execute( callback );
		return requestTask;
	}

	public static void setOfflineUsageForClass( Class<?> cl, boolean usePersistentStorage )
	{
		offlineMapping.put( cl, usePersistentStorage );
	}

	public static boolean getOfflineUsageForClass( Class<?> cl )
	{
		Boolean result = offlineMapping.get( cl );
		return result == null ? false : result;
	}

	public boolean getUseDeltaSync( )
	{
		return useDeltaSync;
	}

	public void setUseDeltaSync( boolean useDeltaSync )
	{
		this.useDeltaSync = useDeltaSync;
	}

	/**
	 * Login via facebook. If it's the first login (sign up), a user gets created in the backend, otherwise an existing
	 * user gets returned.
	 * If a user didn't login via facebook before, but has the same token as attribute, the existing user gets used (and
	 * enhanced with info from the facebook user).
	 *
	 * Always requires an internet connection. No caching will be used.
	 *
	 * @param facebookToken The facebook token of the user
	 * @param ignored Gets ignored
	 * @return A tuple class that contains the user and the token info (apiOmat token, not facebook token)
	 * @throws ApiomatRequestException
	 *
	 * @deprecated Use {@link #loginFacebookUser(String)} instead
	 */
	@Deprecated
	public UserTokenMapTuple getOrCreateUser( final String facebookToken, final boolean ignored )
		throws ApiomatRequestException
	{
		return getOrCreateUser( facebookToken );
	}

	/**
	 * Login via facebook. If it's the first login (sign up), a user gets created in the backend, otherwise an existing
	 * user gets returned.
	 * If a user didn't login via facebook before, but has the same token as attribute, the existing user gets used (and
	 * enhanced with info from the facebook user).
	 *
	 * Always requires an internet connection. No caching will be used.
	 *
	 * @param facebookToken The facebook token of the user
	 * @return A tuple class that contains the user and the token info (apiOmat token, not facebook token)
	 * @throws ApiomatRequestException
	 *
	 * @deprecated Use {@link #loginFacebookUser(String)} instead
	 */
	@Deprecated
	public UserTokenMapTuple getOrCreateUser( final String facebookToken ) throws ApiomatRequestException
	{
		UserTokenMapTuple result = null;

		final String appName = this.baseUrl.substring( this.baseUrl.lastIndexOf( '/' ) + 1 );
		final String aomFacebookUrl =
			this.baseUrl.substring( 0, this.baseUrl.indexOf( "yambas/rest" ) + 11 ) + "/modules/facebook/spec/" +
				appName + "/aomuser" + "?facebookToken=" + facebookToken;

		/* Temporarily switch to network_only, because the token-info shouldn't be cached,
		 * and also the facebook token could have become invalid.
		 * In case there's no offlinehandler/context, the strategy is network_else_cache,
		 * which means the cache is only accessed when the device is offline or if 304 is returned.
		 * In the case of a FB login request a special resource is being used where 304 is not returned.
		 * So actually, cache is only accessed when the device is offline. In that case, return null. */
		AOMCacheStrategy origCacheStrategy = cacheStrategy;

		String userTokenMapTupleJsonString = null;
		try
		{
			if ( this.getOfflineHandler( ) != null )
			{
				setCachingStrategy( AOMCacheStrategy.NETWORK_ONLY, offlineHandler.getContext( ) );
			}
			/* Else: only if the user never set another strategy (and it's network_else_cache)
			 * In that case, check "wasLoadedFromStorage" and return null */
			userTokenMapTupleJsonString =
				sendRequest( AOMHttpClient.HTTP_METHOD.GET, aomFacebookUrl, null, false, null, false,
					Arrays.asList( HttpURLConnection.HTTP_OK ), null, false, false, false, false, null );
			if ( wasLoadedFromStorage )
			{
				userTokenMapTupleJsonString = null;
			}
		}
		/* Always re-set the original strategy */
		finally
		{
			if ( offlineHandler != null )
			{
				setCachingStrategy( origCacheStrategy, offlineHandler.getContext( ) );
			}
			/* Else: In that case the strategy was never changed */
		}

		if ( userTokenMapTupleJsonString == null )
		{
			result = null;
		}
		else
		{
			try
			{
				JSONObject userTokenMapTupleJson = new JSONObject( userTokenMapTupleJsonString );
				JSONObject userJson = userTokenMapTupleJson.getJSONObject( "user" );
				JSONObject tokenMapJson = userTokenMapTupleJson.getJSONObject( "tokenMap" );

				User user = new User( );
				user.fromJson( userJson );
				/* need to convert to SDK tokenMap */
				Map<String, String> sdkTokenMap = convertOriginalTokenMapToSdkTokenMap( tokenMapJson );

				result = new UserTokenMapTuple( user, sdkTokenMap );
			}
			catch ( JSONException e )
			{
				Log.w( "Datastore", "JSONException during handling userTokenMapTuple JSON: " + e.getMessage( ) );
				result = null;
			}
		}
		return result;
	}

	/**
	 * Login via facebook. If it's the first login (sign up), a user gets created in the backend, otherwise an existing
	 * user gets returned.
	 * If a user didn't login via facebook before, but has the same token as attribute, the existing user gets used (and
	 * enhanced with info from the facebook user).
	 *
	 * Always requires an internet connection. No caching will be used.
	 *
	 * @param facebookToken The facebook token of the user
	 * @return A tuple class that contains the User and a TokenContainer with the token info (apiOmat token, not
	 *         facebook token)
	 * @throws ApiomatRequestException
	 */
	public UserTokenContainerTuple loginFacebookUser( final String facebookToken ) throws ApiomatRequestException
	{
		UserTokenMapTuple userTokenMapTuple = getOrCreateUser( facebookToken );
		TokenContainer tokenContainer = convertTokenMapToTokenContainer( userTokenMapTuple.getTokenMap( ) );
		return new UserTokenContainerTuple( userTokenMapTuple.getUser( ), tokenContainer );
	}

	/**
	 * @deprecated Use {@link #convertTokenMapToTokenContainer(Map )} instead
	 */
	@Deprecated
	private static Map<String, String> convertOriginalTokenMapToSdkTokenMap( JSONObject tokenResponseJson )
		throws ApiomatRequestException
	{
		Map<String, Object> originalTokenMap;
		try
		{
			originalTokenMap = JsonHelper.toMap( tokenResponseJson );
		}
		catch ( JSONException ex )
		{
			throw new ApiomatRequestException( Status.JSON_FORMAT_ERROR, HttpURLConnection.HTTP_OK );
		}

		Map<String, String> result = new ConcurrentHashMap<String, String>( );

		final Object accessTokenObject = originalTokenMap.get( "access_token" );
		if ( accessTokenObject != null && accessTokenObject.equals( JSONObject.NULL ) == false )
		{
			final String accessTokenString = ( String ) accessTokenObject;
			if ( TextUtils.isEmpty( accessTokenString ) == false )
			{
				result.put( "sessionToken", accessTokenString );
			}
		}
		final Object refreshTokenObject = originalTokenMap.get( "refresh_token" );
		if ( refreshTokenObject != null && refreshTokenObject.equals( JSONObject.NULL ) == false )
		{
			final String refreshTokenString = ( String ) refreshTokenObject;
			if ( TextUtils.isEmpty( refreshTokenString ) == false )
			{
				result.put( "refreshToken", refreshTokenString );
			}
		}
		final Object expiresInObject = originalTokenMap.get( "expires_in" );
		if ( expiresInObject != null && expiresInObject.equals( JSONObject.NULL ) == false )
		{
			final String expiresInString = String.valueOf( ( Integer ) expiresInObject );
			if ( TextUtils.isEmpty( expiresInString ) == false )
			{
				result.put( "expirationDate",
					String.valueOf( new Date( ).getTime( ) + Integer.valueOf( expiresInString ) * 1000 ) );
			}
		}
		final Object moduleObject = originalTokenMap.get( "aom_module" );
		if ( moduleObject != null && moduleObject.equals( JSONObject.NULL ) == false )
		{
			final String moduleString = ( String ) moduleObject;
			if ( TextUtils.isEmpty( moduleString ) == false )
			{
				result.put( "module", moduleString );
			}
		}
		final Object modelObject = originalTokenMap.get( "aom_model" );
		if ( modelObject != null && modelObject.equals( JSONObject.NULL ) == false )
		{
			final String modelString = ( String ) modelObject;
			if ( TextUtils.isEmpty( modelString ) == false )
			{
				result.put( "model", modelString );
			}
		}
		final Object extraObject = originalTokenMap.get( "aom_extra" );
		if ( extraObject != null && extraObject.equals( JSONObject.NULL ) == false )
		{
			final String extraString = ( String ) extraObject;
			if ( TextUtils.isEmpty( extraString ) == false )
			{
				result.put( "extra", extraString );
			}
		}

		return result;
	}

	/**
	 * Currently converts the deprecated token map to the container object.
	 * In the future it's going to convert the original token map to the container object.
	 * The result will be the same
	 */
	private static TokenContainer convertTokenMapToTokenContainer( Map<String, String> tokenMap )
	{
		return new TokenContainer( ( String ) tokenMap.get( "sessionToken" ),
			( String ) tokenMap.get( "refreshToken" ),
			( String ) tokenMap.get( "expirationDate" ),
			true,
			( String ) tokenMap.get( "module" ),
			( String ) tokenMap.get( "model" ),
			( String ) tokenMap.get( "extra" ) );
	}

	/**
	 * Check if apiOmat service is reachable
	 * The request will timeout after x ms or if connection cannot be established in y ms (values can be changed)
	 * 
	 * @return true if service is available otherwise false
	 */
	public boolean isServiceAvailable( ) throws ApiomatRequestException
	{
		if ( this.getOfflineHandler( ) == null )
		{
			/* We have to check with trying to send a request */
			return AOMHttpClientFactory.getAomHttpClient( ).isServiceAvailable( );
		}
		else
		{
			return this.getOfflineHandler( ).isConnected( );
		}
	}

	/**
	 * [Async] Check if apiOmat service is reachable
	 * The request will timeout after x ms or if connection cannot be established in y ms (values can be changed)
	 * 
	 * @return true if service is available otherwise false
	 */
	public AOMTask<Boolean> isServiceAvailableAsync( AOMCallback<Boolean> callback )
	{
		AOMTask<Boolean> isServiceAvailableTask = null;

		isServiceAvailableTask = new AOMTask<Boolean>( )
		{
			@Override
			public Boolean doRequest(AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				return isServiceAvailable( );
			}
		};
		isServiceAvailableTask.execute( callback );
		return isServiceAvailableTask;
	}

	public void setTimeout( final int connectTimeout, final int readTimeout, final int requestTimeout )
	{
		AOMHttpClientFactory.getAomHttpClient( ).setConnectTimeout( connectTimeout );
		AOMHttpClientFactory.getAomHttpClient( ).setReadTimeout( readTimeout );
		AOMHttpClientFactory.getAomHttpClient( ).setRequestTimeout( requestTimeout );
	}

	/**
	 * Sets the maximum time in milliseconds to wait while connecting.
	 * Connecting to a server will fail with an ApiomatRequestException if the timeout elapses before a connection is
	 * established.
	 *
	 * @param connectTimeout in milliseconds
	 */
	public void setConnectTimeout( final int connectTimeout )
	{
		AOMHttpClientFactory.getAomHttpClient( ).setConnectTimeout( connectTimeout );
	}

	public int getConnectTimeout( )
	{
		return AOMHttpClientFactory.getAomHttpClient( ).getConnectTimeout( );
	}

	/**
	 * Sets the maximum time to wait for an input stream read (chunk - not all) before giving up.
	 * Reading will fail with an ApiomatRequestException if the timeout elapses before a new chunk was read.
	 *
	 * @param readTimeout in milliseconds
	 */
	public void setReadTimeout( final int readTimeout )
	{
		AOMHttpClientFactory.getAomHttpClient( ).setReadTimeout( readTimeout );
	}

	public int getReadTimeout( )
	{
		return AOMHttpClientFactory.getAomHttpClient( ).getReadTimeout( );
	}

	/**
	 * Sets the maximum time to wait for an input stream read (complete response) before giving up.
	 * Reading will fail with an ApiomatRequestException if the timeout elapses before the whole response was read.
	 *
	 * @param requestTimeout in milliseconds
	 */
	public void setRequestTimeout( final int requestTimeout )
	{
		AOMHttpClientFactory.getAomHttpClient( ).setRequestTimeout( requestTimeout );
	}

	public int getRequestTimeout( )
	{
		return AOMHttpClientFactory.getAomHttpClient( ).getRequestTimeout( );
	}

	public String getConfiguredApiKey()
	{
		/* A correct AOMHttpClient is set up, because this method can only be called on an instance of Datastore and
		 * all constructors set up a proper AOMHttpClient.
		 */
		return AOMHttpClientFactory.getAomHttpClient().getApiKey();
	}

	public String getConfiguredSystem()
	{
		/* A correct AOMHttpClient is set up, because this method can only be called on an instance of Datastore and
		 * all constructors set up a proper AOMHttpClient.
		 */
		return AOMHttpClientFactory.getAomHttpClient().getSystem();
	}

	public void setCheckObjectState(final boolean checkObjectState)
	{
		this.checkObjectState = checkObjectState;
	}

	public boolean getCheckObjectState()
	{
		return this.checkObjectState;
	}

	/* The check for "h" is to make sure it's "http..." instead of "{" or "[". */
	private boolean isHref (final String href )
	{
		return "h".equals( href.substring( 0, 1 ) );
	}

    private void setWasLoadedFromStorage(boolean wasLoadedFromStorage, AtomicReference<Boolean> wasLoadedFromStorageAtomicRef)
    {
        this.wasLoadedFromStorage = wasLoadedFromStorage;
        if (wasLoadedFromStorageAtomicRef != null)
        {
            wasLoadedFromStorageAtomicRef.set(wasLoadedFromStorage);
        }
    }

    /**
     * Returns if the last response to the last request was loaded from storage or not.
     * This method is only reliable when the request was done synchronously.
     * When doing asynchronous requests, please use the parameter in the isDone() method of the callback.
     *
     * @return If the last response to the last request was loaded from storage or not.
     */
    public boolean getWasLoadedFromStorage()
    {
        return this.wasLoadedFromStorage;
    }
}
