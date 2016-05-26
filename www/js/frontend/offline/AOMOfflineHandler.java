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
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.apiomat.frontend.AbstractClientDataModel;
import com.apiomat.frontend.ApiomatRequestException;
import com.apiomat.frontend.Datastore;
import com.jakewharton.disklrucache.DiskLruCache;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AOMOfflineHandler
{
	public static final String TASKS_KEY = "tasks";
	public static final String HREFMAP_KEY = "hrefmap_keys";

	private static final long MAX_CACHE_SIZE_BYTE = 20000;
	public static String TAG = "AOMOfflineHandler";
	private static DiskLruCache fileCache;
	private final AOMNetworkHandler.AOMNetworkListener networkListener;
	Object connectLock = new Object( );
	/* This queue hold all POST/PUT/DELETE tasks */
	ConcurrentLinkedQueue<AOMOfflineInfo> tasks = new ConcurrentLinkedQueue<AOMOfflineInfo>( );
	/* Map which maps localID -> HREF */
	Map<String, String> mapIdToHref = new HashMap<String, String>( );
	/* maps localID -> Reference to real class instance */
	Map<String, AbstractClientDataModel> mapIdToObj = new HashMap<String, AbstractClientDataModel>( );
	private Set<AOMOfflineListener> listeners = new HashSet<AOMOfflineListener>( );
	private boolean isConnected = true;
	private boolean isWorking = false;
	private SecureRandom random = new SecureRandom( );
	private static Context context;

	public AOMOfflineHandler( Context _context ) throws RuntimeException
	{
		context = _context;
		String errorMsg =
			"Can't get application context. Please set it on Datastore.getInstance().setOfflineStrategy(...)";
		if ( context == null )
		{
			Log.e( TAG, errorMsg );
			throw new RuntimeException( errorMsg );
		}

		if ( context.checkCallingOrSelfPermission( "android.permission.ACCESS_NETWORK_STATE" ) !=
			PackageManager.PERMISSION_GRANTED )
		{
			errorMsg =
				"Can't get access to network state! Please set permission android.permission.ACCESS_NETWORK_STATE in your AndroidManifest.xml";
			Log.e( TAG, errorMsg );
			networkListener = null;
			throw new RuntimeException( errorMsg );
		}

		/* Initialize disk cache */
		try
		{
			fileCache =
				DiskLruCache.open( context.getDir( "apiomat", Context.MODE_PRIVATE ), 1, 1, MAX_CACHE_SIZE_BYTE );
		}
		catch ( IOException e )
		{
			errorMsg =
				"Can't access cache directory. Please make sure you have correct rights to write on internal disk";
			Log.e( TAG, errorMsg );
			throw new RuntimeException( errorMsg );
		}

		/* Check if we've persisted tasks and get them */
		try
		{
			DiskLruCache.Snapshot cacheEntry = fileCache.get( TASKS_KEY );
			if ( cacheEntry != null )
			{
				ObjectInputStream objIS = new ObjectInputStream( cacheEntry.getInputStream( 0 ) );
				tasks = ( ConcurrentLinkedQueue<AOMOfflineInfo> ) objIS.readObject( );
				objIS.close( );
				cacheEntry.close( );
			}
		}
		catch ( Exception e )
		{
			Log.e( TAG, "Can't get tasks list back from disc cache: " + e.getMessage( ) );
			tasks = new ConcurrentLinkedQueue<AOMOfflineInfo>( );
		}

		/* Check for persisted href map */
		try
		{
			DiskLruCache.Snapshot cacheEntry = fileCache.get( HREFMAP_KEY );
			if ( cacheEntry != null )
			{
				ObjectInputStream objIS = new ObjectInputStream( cacheEntry.getInputStream( 0 ) );
				mapIdToHref = ( Map<String, String> ) objIS.readObject( );
				objIS.close( );
				cacheEntry.close( );
			}
		}
		catch ( Exception e )
		{
			Log.e( TAG, "Can't get hrefmap  back from disc cache: " + e.getMessage( ) );
			mapIdToHref = new HashMap<String, String>( );
		}

		networkListener = new AOMNetworkHandler.AOMNetworkListener( )
		{
			@Override
			public void networkStateChanged( boolean _isConnected )
			{
				Log.d( TAG, "Is connected: " + _isConnected );
				setConnected( _isConnected );
				if ( isConnected( ) )
				{
					sendTasks( );
				}
			}
		};

		AOMNetworkHandler.getInstance( ).addListener( networkListener );
		registerReceiver( context );
		setConnected( AOMNetworkHandler.getInstance( ).isConnected( context ) );
		/* check for open tasks */
		sendTasks( );
	}

	public void addListener( AOMOfflineListener _listener )
	{
		this.listeners.add( _listener );
	}

	public void removeListener( AOMOfflineListener _listener )
	{
		this.listeners.remove( _listener );
	}

	/**
	 * This method will go through the queue and sends saved data to backend
	 */
	private void sendTasks( )
	{
		/* how we prevent running twice time */
		AsyncTask<Void, Void, Void> worker = new AsyncTask<Void, Void, Void>( )
		{
			@Override
			protected Void doInBackground( Void... params )
			{
				isWorking = true;
				while ( isConnected( ) && tasks.size( ) > 0 )
				{
					AOMOfflineInfo task = tasks.poll( );
					Log.d( TAG, "Size of tasks " + tasks.size( ) );

					if ( task != null )
					{
						try
						{
							/* also write to persisted list of tasks */
							writeInfosToCache( );

							final boolean isStaticData = task.getClazz( ) == null;
							AbstractClientDataModel tmpModel = null;
							byte[] staticData = null;

							DiskLruCache.Snapshot cacheEntry = fileCache.get( task.getFileKey( ) );

							if ( cacheEntry != null )
							{
								if ( isStaticData )
								{
									InputStream in = null;
									ByteArrayOutputStream out = null;
									try
									{
										/* Copy cached inputstream to bytes */
										in = cacheEntry.getInputStream( 0 );
										out = new ByteArrayOutputStream( );
										byte[] buffer = new byte[ 1024 ];
										int length;

										while ( ( length = in.read( buffer ) ) != -1 )
										{
											out.write( buffer, 0, length );
										}
										staticData = out.toByteArray( );
									}
									finally
									{
										if ( in != null )
										{
											in.close( );
										}
										if ( out != null )
										{
											out.close( );
										}
									}
								}
								else
								{
									/* generate new ACDM */
									tmpModel = ( AbstractClientDataModel ) task.getClazz( ).newInstance( );
									/* Get JSON from disc cache */
									tmpModel.fromJson( cacheEntry.getString( 0 ) );
								}

								/* remove file from cache */
								cacheEntry.close( );
								fileCache.remove( task.getFileKey( ) );

								if ( tmpModel != null )
								{
									sendModeltoServer( task, tmpModel );
								}
								else if ( staticData != null )
								{
									sendStaticDataToServer( task, staticData );
								}
							}
							else
							{
								Log.e( TAG,
									"Can't find persisted class instance. Maybe cache size was exceeded and class instance was deleted" );
								throw new ApiomatRequestException( com.apiomat.frontend.Status.CANT_WRITE_IN_CACHE, 0 );
							}
						}
						catch ( Exception e )
						{
							Log.e( TAG, "Can't update class instance: " + e.getMessage( ) );
							informListeners( task, null, new ApiomatRequestException(
								com.apiomat.frontend.Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_OK ) );
						}
					}
				}
				mapIdToObj.clear( );
				isWorking = false;
				return null;
			}

			private void sendStaticDataToServer( AOMOfflineInfo task, byte[] data )
			{
				try
				{
					if ( task.getHttpMethod( ).equals( "POST" ) )
					{
						String href = Datastore.getInstance( ).postStaticDataOnServer( data, task.getIsImage( ),
							task.getIsRef( ), task.getUsePersistentStorage( ) );
						if ( href != null && href.length( ) > 0 )
						{
							mapIdToHref.put( task.getLocalId( ), href );
							writeHrefMapToCache( );
							informListeners( task, task.getLocalId( ), null );
						}
						else
						{
							throw new ApiomatRequestException( com.apiomat.frontend.Status.HREF_NOT_FOUND,
								HttpURLConnection.HTTP_CREATED );
						}
					}
					else if ( task.getHttpMethod( ).equals( "DELETE" ) )
					{
						Datastore.getInstance( ).deleteOnServer( task.getUrl( ), task.getIsRef( ),
							task.getUsePersistentStorage( ) );
					}
				}
				catch ( ApiomatRequestException e )
				{
					Log.e( TAG, "Can't delete or save static data: " + e.getMessage( ) );
					informListeners( task, task.getLocalId( ), e );
				}
			}

			private void sendModeltoServer( final AOMOfflineInfo task, AbstractClientDataModel tmpModel )
			{
				/* check httpMethod and decide if we have to call post/put/delete on datastore */
				if ( task.getHttpMethod( ).equals( "POST" ) )
				{
					try
					{
						String url = task.getUrl( );
						/* seems to be a reference */
						if ( task.getRefName( ) != null && task.getRefName( ).length( ) > 0 )
						{
							String parentID = url.substring( url.lastIndexOf( "/" ) + 1 );
							/* add correct href to referenced class instance */
							getHref( tmpModel );
							String parentHref = mapIdToHref.get( parentID );
							if ( TextUtils.isEmpty( parentHref ) )
							{
								parentHref = task.getUrl( );
							}
							url = parentHref + "/" + task.getRefName( );
						}
						if ( url != null )
						{
							if ( task.getIsRef( ) == false )
							{
								Field hrefField = AbstractClientDataModel.class.getDeclaredField( "href" );
								hrefField.setAccessible( true );
								hrefField.set( tmpModel, null );
							}
							final String href = Datastore.getInstance( ).postOnServer( url, tmpModel, task.getIsRef( ),
								task.getUsePersistentStorage( ) );
							/* inform listeners */
							if ( href != null && href.length( ) > 0 )
							{
								mapIdToHref.put( task.getLocalId( ), href );
								writeHrefMapToCache( );
								/* update reference object if there */
								AbstractClientDataModel realModel =
									updateRealModelAfterPost( task.getLocalId( ), href );
								if ( realModel != null )
								{
									updateOfflineStorageAfterPost( task.getLocalId( ), href, realModel.toJson( ),
										task.getUsePersistentStorage( ) );
								}
								informListeners( task, task.getLocalId( ), null );

                                /* Load, because otherwise when loading a reference later, there might be no reference href in some cases */
								realModel.load( task.getUsePersistentStorage( ) );
							}
							else
							{
								ApiomatRequestException e =
									new ApiomatRequestException( com.apiomat.frontend.Status.HREF_NOT_FOUND,
										HttpURLConnection.HTTP_CREATED );
								Log.e( TAG, "Can't save class instance: " + e.getMessage( ) );
								informListeners( task, task.getLocalId( ), e );
							}
						}
						else
						{
							ApiomatRequestException e =
								new ApiomatRequestException( com.apiomat.frontend.Status.HREF_NOT_FOUND,
									HttpURLConnection.HTTP_CREATED );
							Log.e( TAG, "Can't save class instance: " + e.getMessage( ) );
							informListeners( task, task.getLocalId( ), e );
						}
					}
					catch ( NoSuchFieldException e )
					{
						Log.e( TAG, "Can't save class instance: " + e.getMessage( ) );
						informListeners( task, task.getLocalId( ), e );
					}
					catch ( IllegalAccessException e )
					{
						Log.e( TAG, "Can't save class instance: " + e.getMessage( ) );
						informListeners( task, task.getLocalId( ), e );
					}
					catch ( ApiomatRequestException e )
					{
						Log.e( TAG, "Can't save class instance: " + e.getMessage( ) );
						informListeners( task, task.getLocalId( ), e );
					}
				}
				else if ( task.getHttpMethod( ).equals( "PUT" ) )
				{
					tmpModel.setOffline( true );
					String href = getHref( tmpModel );
					if ( href != null && href.length( ) > 0 )
					{
						try
						{
							Datastore.getInstance( ).updateOnServer( tmpModel, task.getIsRef( ),
								task.getUsePersistentStorage( ) );
							AbstractClientDataModel realModel = updateRealModelAfterPut( tmpModel.getID( ) );
							informListeners( task, href, null );
							if ( realModel != null )
							{
								/* Necessary so that the object contains for example an imageUrl
                                 * similar to load above (in POST - necessary for references) */
								realModel.load( task.getUsePersistentStorage( ) );
							}
						}
						catch ( ApiomatRequestException e )
						{
							Log.e( TAG, "Can't update class instance: " + e.getMessage( ) );
							informListeners( task, href, e );
						}
					}
					else
					{
						ApiomatRequestException e =
							new ApiomatRequestException( com.apiomat.frontend.Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_OK );
						Log.e( TAG, "Can't save class instance: " + e.getMessage( ) );
						informListeners( task, task.getLocalId( ), e );
					}
				}
				else if ( task.getHttpMethod( ).equals( "DELETE" ) )
				{
					String href = getHref( tmpModel );
					final boolean isRef = task.getRefName( ) != null && task.getRefName( ).length( ) > 0;
					/* seems to be a reference */
					if ( isRef )
					{
						String parentID = task.getUrl( ).substring( task.getUrl( ).lastIndexOf( "/" ) + 1 );
						/* add correct href to referenced class instance */
						String parentHref = mapIdToHref.get( parentID );
						if ( TextUtils.isEmpty( parentHref ) )
						{
							parentHref = task.getUrl( );
						}
						href =
							parentHref + "/" + task.getRefName( ) + "/" +
								tmpModel.getHref( ).substring( tmpModel.getHref( ).lastIndexOf( "/" ) + 1 );
					}
					if ( href != null && href.length( ) > 0 )
					{
						try
						{
							Datastore.getInstance( ).deleteOnServer( href, task.getIsRef( ),
								task.getUsePersistentStorage( ) );
							informListeners( task, href, null );
						}
						catch ( ApiomatRequestException e )
						{
							Log.e( TAG, "Can't delete class instance: " + e.getMessage( ) );
							informListeners( task, href, e );
						}
					}
					else
					{
						informListeners( task, task.getLocalId( ), new ApiomatRequestException(
							com.apiomat.frontend.Status.HREF_NOT_FOUND, HttpURLConnection.HTTP_NO_CONTENT ) );
					}
				}
			}
		};

		synchronized ( connectLock )
		{
			worker.execute( );
		}
	}

	/**
	 * Add a new task tu request queue and return temp HREf aka local href
	 *
	 * @param _httpMethod
	 * @param _url
	 * @param _content
	 * @param _isImage
	 * @param _isRef
	 * @param _usePersistentStorage
	 * @return
	 */
	public String addTask( final String _httpMethod, final String _url, final byte[] _content, final boolean _isImage,
		final boolean _isRef, final boolean _usePersistentStorage )
	{
		String returnedUri = _url;
		String localId = null;
		/* decide if we need new localId */
		if ( _httpMethod.equals( "POST" ) )
		{
			localId = createNewLocalId( );
			returnedUri += localId;
			saveBinaryToStorage( returnedUri, _content, _isRef, _usePersistentStorage );
		}
		else if ( _httpMethod.equals( "PUT" ) )
		{
			saveBinaryToStorage( returnedUri, _content, _isRef, _usePersistentStorage );
		}
		try
		{
			final String fileKey = _httpMethod.toLowerCase( ) + "_" + System.currentTimeMillis( );
			if ( _httpMethod.equals( "DELETE" ) == false )
			{
				DiskLruCache.Editor creator = fileCache.edit( fileKey );
				OutputStream out = creator.newOutputStream( 0 );
				out.write( _content );
				creator.commit( );
				out.close( );
			}
			final AOMOfflineInfo info =
				new AOMOfflineInfo( _httpMethod, _url, fileKey, null, localId, _isImage, _isRef, _usePersistentStorage );
			tasks.add( info );
			/* also commit task to disc cache */
			writeInfosToCache( );

		}
		catch ( IOException e )
		{
			Log.e( TAG, "Can't add task to disc cache: " + e.getMessage( ) );
		}

		return returnedUri;
	}

	private boolean saveBinaryToStorage( String _url, byte[] _content,
		final boolean _isRef, boolean usePersistentStorage )
	{
		AbstractStorage storage;
		if ( usePersistentStorage )
		{
			storage = SQLiteStorage.getInstance( Datastore.getInstance( ).getOfflineHandler( ).getContext( ) );
		}
		else
		{
			storage = InMemoryCache.getInstance( );
		}
		return storage.storeBinary( _url, _content, "GET" );
	}

	/**
	 *
	 * @param _httpMethod
	 * @param _url
	 * @param _dataModel
	 * @param _parentHref
	 * @param _isRef
	 * @param _usePersistentStorage
	 * @return
	 */
	public String addTask( final String _httpMethod, final String _url, final AbstractClientDataModel _dataModel,
		final String _parentHref, final boolean _isRef, final boolean _usePersistentStorage )
	{
		String returnedUri = _url;
		String localId = null;
		/* decide if we need new localId */
		if ( _httpMethod.equals( "POST" ) )
		{
			localId = createNewLocalId( );
			if ( _parentHref != null )
			{
				returnedUri += "/" + _parentHref;
			}
			returnedUri += "/" + localId;
			mapIdToObj.put( localId, _dataModel );
			// Addition for also saving to storage and not just outgoing queue
			saveObjectToStorage( returnedUri, _dataModel, true, _isRef, _usePersistentStorage );
		}
		else if ( _httpMethod.equals( "PUT" ) )
		{
            /* Needed because otherwise there's an error in e.g. the following case:
             * save model online, post image to model offline, go online (sync), then load image.
             * In that case, when going online, the image url (with local id) in the real data model needs to be
             * exchanged by the one from the temp data model with image url with real id.
             */
			String id = _url.substring( _url.lastIndexOf( "/" ) + 1, _url.length( ) );
			mapIdToObj.put( id, _dataModel );
			saveObjectToStorage( returnedUri, _dataModel, false, _isRef, _usePersistentStorage );
		}
		else if ( _httpMethod.equals( "DELETE" ) )
		{
            /* immediately delete from offline storage */
			Datastore.getInstance( ).deleteObjectFromStorage( _url, _isRef );
		}
		try
		{
			final String fileKey =
				_httpMethod.toLowerCase( ) + "_" + System.currentTimeMillis( ) + "_" +
					( localId != null ? localId : createNewLocalId( ) );
			DiskLruCache.Editor creator = fileCache.edit( fileKey );
			creator.set( 0, _dataModel.toJson( ) );
			creator.commit( );
			final AOMOfflineInfo info =
				new AOMOfflineInfo( _httpMethod, _url, fileKey, _dataModel.getClass( ), localId, _parentHref, false,
					_isRef, _usePersistentStorage );
			tasks.add( info );
			/* also commit tasks to disc cache */
			writeInfosToCache( );

		}
		catch ( IOException e )
		{
			Log.e( TAG, "Can't add task to disc cache: " + e.getMessage( ) );
		}
		return returnedUri;
	}

	private boolean saveObjectToStorage( final String _url, final AbstractClientDataModel _dataModel,
		final boolean injectHref, final boolean isRef, final boolean usePersistentStorage )
	{
		AbstractStorage storage;
		if ( usePersistentStorage )
		{
			storage = SQLiteStorage.getInstance( Datastore.getInstance( ).getOfflineHandler( ).getContext( ) );
		}
		else
		{
			storage = InMemoryCache.getInstance( );
		}
		String dataToStore;
		JSONObject data;
		try
		{
			Field dataField = AbstractClientDataModel.class.getDeclaredField( "data" );
			dataField.setAccessible( true );
			data = ( JSONObject ) dataField.get( _dataModel );
			if ( injectHref && isRef == false )
			{
				data.put( "href", _url );
			}
		}
		// NoSuchFieldException, IllegalAccessException, JSONException
		catch ( Exception e )
		{
			return false;
		}
		dataToStore = data.toString( );
		if ( isRef )
		{
			// store mapping - in opposite to ref mapping at GET while online, the id needs to be removed instead of added
			String mappingHref = _url.substring( 0, _url.lastIndexOf( "/" ) );
			storage.storeObject( mappingHref, _url, "GET" );
		}
		return storage.storeObject( _url, dataToStore, "GET" );
	}

	/**
	 *
	 * @param _httpMethod
	 * @param _url
	 * @param isImage
	 * @param isRef
	 * @param usePersistentStorage
	 */
	public void addTask( final String _httpMethod, final String _url, final boolean isImage, final boolean isRef,
		final boolean usePersistentStorage )
	{
		addTask( _httpMethod, _url, null, isImage, isRef, usePersistentStorage );
	}

	public synchronized String createNewLocalId( )
	{
		return new BigInteger( 130, random ).toString( 32 );
	}

	/**
	 * Writes waiting tasks to disc cache
	 *
	 * @throws java.io.IOException
	 * @throws ClassNotFoundException
	 */
	private void writeInfosToCache( )
	{
		writeObjToCache( TASKS_KEY, this.tasks );
	}

	/**
	 * Writes mapped hrefs to disc cache
	 *
	 * @throws java.io.IOException
	 */
	private void writeHrefMapToCache( )
	{
		writeObjToCache( HREFMAP_KEY, this.mapIdToHref );
	}

	/**
	 * Writes given object to disc cache
	 *
	 * @throws java.io.IOException
	 */
	private void writeObjToCache( final String key, final Object object )
	{
		try
		{
			DiskLruCache.Editor creator = fileCache.edit( key );
			ObjectOutputStream objectOS = new ObjectOutputStream( creator.newOutputStream( 0 ) );
			objectOS.writeObject( object );
			creator.commit( );
			objectOS.close( );
		}
		catch ( Exception e )
		{
			Log.e( TAG, "Can't serialize '" + object.getClass( ) + "': " + e.getMessage( ) );
		}
	}

	private void informListeners( final AOMOfflineInfo _info, final String _href, final Throwable _exception )
	{
		for ( AOMOfflineListener listener : this.listeners )
		{
			/* it seems that all want ok */
			if ( _exception == null )
			{
				listener.onTaskExecuted( _info, _href );
			}
			else
			{
				listener.onTaskExecutionError( _info, _href, _exception );
			}
		}
	}

	private String getHref( AbstractClientDataModel tmpModel )
	{
		String href = tmpModel.getHref( );
		if ( tmpModel.isOffline( ) )
		{
			injectDataUrl( tmpModel );

			final String id = tmpModel.getID( );
			if ( id != null && id.length( ) > 0 )
			{
				String tempHref = mapIdToHref.get( id );
				href = tempHref != null && href.length( ) > 0 ? tempHref : href;
				href = injectHref( tmpModel, href );
			}
			else
			{
				Log.e( TAG, "No local ID found" );
			}

		}
		return href;
	}

	/**
	 * Returns "real" href for given local href, if exists otherwise false
	 *
	 * @return "real" href or null if not found in list
	 */
	private String getHrefForLocalHref( String _localHref )
	{
		String id = _localHref.substring( _localHref.lastIndexOf( "/" ) + 1 );
		return mapIdToHref.get( id );
	}

	private String injectHref( AbstractClientDataModel tmpModel, String href )
	{
		/* inject server href */
		Field f = null;
		try
		{
			f = AbstractClientDataModel.class.getDeclaredField( "href" );
			f.setAccessible( true );
			f.set( tmpModel, href );
			f.setAccessible( false );

			// Also inject into data, because at some points fromJson gets called
			JSONObject data;
			Field dataField = AbstractClientDataModel.class.getDeclaredField( "data" );
			dataField.setAccessible( true );
			data = ( JSONObject ) dataField.get( tmpModel );
			data.put( "href", href );
		}
		catch ( Exception e )
		{
			Log.e( TAG, "Can't inject href" );
			href = null;
		}
		return href;
	}

	private void injectDataUrl( AbstractClientDataModel model )
	{
		try
		{
			boolean wasUpdateFound = false;
				/* check if there are also localHREFs for files/images in class instances */
			String jsonStr = model.toJson( );
			JSONObject jsonRep = new JSONObject( jsonStr );
			Iterator jsonKeys = jsonRep.keys( );
			while ( jsonKeys.hasNext( ) )
			{
				String jsonKey = String.valueOf( jsonKeys.next( ) );
					/* we only check properties with URL at the end */
				if ( jsonKey.endsWith( "URL" ) )
				{
					String jsonObj = jsonRep.getString( jsonKey );
					if ( jsonObj != null )
					{
						String realHref = getHrefForLocalHref( jsonObj );
						if ( realHref != null )
						{
							jsonRep.put( jsonKey, realHref );
							wasUpdateFound = true;
						}
					}
				}
			}
			/* if we updated a field than update the class instance */
			if ( wasUpdateFound )
			{
				model.fromJson( jsonRep );
			}
		}
		catch ( JSONException e )
		{
			Log.d( TAG, "injecting data url failed: " + e.getMessage( ) );
		}
	}

	/**
	 * This method updates the reference class instance with a new href from server
	 *
	 * @param _localId
	 * @param _href
	 */
	private AbstractClientDataModel updateRealModelAfterPost( final String _localId, final String _href )
	{
		Log.d( TAG, "Size: " + mapIdToObj.size( ) );
		AbstractClientDataModel model = mapIdToObj.remove( _localId );
		if ( model != null && model.isOffline( ) )
		{
			Log.d( TAG, "LocalID is " + _localId );
			/* inject new HREF to class instance */
			injectHref( model, _href );
			model.setOffline( false );
			mapIdToObj.put( model.getID( ), model );
		}
		return model;
	}

	private void updateOfflineStorageAfterPost( final String _localId, final String _newHref, final String _newJson,
		final boolean usePersistentStorage )
	{
		String oldHref =
			_newHref.replace( _newHref.substring( _newHref.lastIndexOf( "/" ) + 1, _newHref.length( ) ), _localId );
		AbstractStorage storage;
		if ( usePersistentStorage )
		{
			storage = SQLiteStorage.getInstance( Datastore.getInstance( ).getOfflineHandler( ).getContext( ) );
		}
		else
		{
			storage = InMemoryCache.getInstance( );
		}
		String loadedObj = storage.getStoredObject( oldHref );
		if ( TextUtils.isEmpty( loadedObj ) )
		{
			return;
		}
		storage.removeObject( oldHref );
		storage.storeObject( _newHref, _newJson, "GET" );
	}

	/**
	 * Updates real model binary *URLs with changed tempModel binary *URLs
	 */
	private AbstractClientDataModel updateRealModelAfterPut( final String _realId )
	{
		Log.d( TAG, "Size: " + mapIdToObj.size( ) );
		AbstractClientDataModel model = mapIdToObj.get( _realId );
		if ( model != null )
		{
			Log.d( TAG, "LocalID is " + _realId );
			/* inject new HREF to class instance */
			injectDataUrl( model );
			return model;
		}
		return null;
	}

	public boolean isConnected( )
	{
		return isConnected;
	}

	protected void setConnected( boolean connected )
	{
		synchronized ( connectLock )
		{
			isConnected = connected;
		}
	}

	/**
	 * This method removes all waiting tasks from queue and also the cached data from disc
	 */
	public void clearCache( )
	{
		tasks.clear( );
		mapIdToHref.clear( );
		/* remove also persisted elements */
		try
		{
			DiskLruCache.Snapshot cacheEntry = fileCache.get( TASKS_KEY );
			if ( cacheEntry != null )
			{
				ObjectInputStream in = new ObjectInputStream( cacheEntry.getInputStream( 0 ) );
				ConcurrentLinkedQueue<AOMOfflineInfo> cachedTasks =
					( ConcurrentLinkedQueue<AOMOfflineInfo> ) in.readObject( );
				if ( cachedTasks != null )
				{
					for ( AOMOfflineInfo task : cachedTasks )
					{
						/* remove serialized JSON from cache */
						try
						{
							fileCache.remove( task.getFileKey( ) );
						}
						catch ( IOException e )
						{
							Log.e( TAG,
								"Can't remove cache entry for task  '" + task.toString( ) + "'" + e.getMessage( ) );
						}
					}
				}
				cacheEntry.close( );
				fileCache.remove( TASKS_KEY );
			}
		}
		catch ( Exception e )
		{
			Log.e( TAG, "Can't clear cache: " + e.getMessage( ) );
		}

		try
		{
			fileCache.remove( HREFMAP_KEY );
		}
		catch ( IOException e )
		{
			Log.e( TAG, "Can't remove href map: " + e.getMessage( ) );
		}
	}

	public interface AOMOfflineListener
	{
		public void onTaskExecuted( final AOMOfflineInfo _offlineObj, final String _href );

		public void onTaskExecutionError( final AOMOfflineInfo _offlineObj, final String _href,
			final Throwable _exception );
	}

	public Context getContext( )
	{
		return context;
	}

	/**
	 * Registers the AOMNetworkHandler BroadcastReceiver if it isn't registered yet
	 *
	 * @param context
	 */
	public void registerReceiver(Context context)
	{
		AOMNetworkHandler.getInstance().register( context );
	}

	/**
	 * Unregisters the AOMNetworkHandler BroadcastReceiver if it's registered
	 */
	public void unregisterReceiver()
	{
		AOMNetworkHandler.getInstance().unregister();
	}

	/**
	 * Returns if the AOMNetworkHandler BroadcastReceiver is registered
	 *
	 * @return if the AOMNetworkHandler BroadcastReceiver is registered
	 */
	public boolean isReceiverRegistered()
	{
		return AOMNetworkHandler.getInstance().isRegistered();
	}
}
