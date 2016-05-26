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

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.apiomat.frontend.basics.User;
import com.apiomat.frontend.callbacks.AOMEmptyCallback;
import com.apiomat.frontend.helper.AOMTask;
import com.apiomat.frontend.helper.CallBackRunnable;
import com.apiomat.frontend.helper.JsonHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class defines the base class of all classes for frontend developers. All data is stored in a JSON data
 * object except the HREF of an incance of this class, originally containing the type of this class.
 * 
 * @author andreasfey
 */
public abstract class AbstractClientDataModel implements Serializable
{
	/**
	 * The representation of the data of an instance of this class as JSON object
	 */
	protected JSONObject data;
	protected String href;
	private ObjectState currentState;

	/**
	 * Constructor
	 */
	@SuppressWarnings( "rawtypes" )
	public AbstractClientDataModel( )
	{
		this.data = new JSONObject( );
		try
		{
			this.data.put( "@type", getType( ) );
			if ( ( this.getSimpleName( ).equals( "MemberModel" ) || this.getSimpleName( ).equals( "User" ) ) &&
				getModuleName( ).equals( "Basics" ) )
			{
				this.data.put( "dynamicAttributes", new JSONObject( ) );
			}
			setCurrentState( ObjectState.PERSISTED );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Helper method to convert a list to a vector
	 * 
	 * @param list
	 * @return a vector containing all elements of the list
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	protected static Vector toVector( List list )
	{
		Vector v = new Vector( );
		for ( Object o : list )
		{
			v.add( o );
		}
		return v;
	}

	/**
	 * Converts a number to double
	 * 
	 * @param number
	 * @return double value; 0 if number is not either Integer or Double
	 */
	protected static double convertNumberToDouble( final Object number )
	{
		double returnValue = 0.0d;

		if ( number instanceof Integer )
		{
			returnValue = ( ( Integer ) number ).doubleValue( );
		}
		else if ( number instanceof Double )
		{
			returnValue = ( ( Double ) number ).doubleValue( );
		}

		return returnValue;
	}

	/**
	 * Returns the system to connect to
	 * 
	 * @return TEST for test system, LIVE for production
	 */
	public String getSystem( )
	{
		return User.system;
	}

	/**
	 * Returns the unique type of this class to get identified via REST interface
	 * 
	 * @return
	 */
	private String getType( )
	{
		return this.getModuleName( ) + "$" + this.getSimpleName( );
	}

	/**
	 * Returns the HREF of this class instance
	 * 
	 * @return HREF of this class instance, NULL if it was created but not saved yet
	 */
	public final String getHref( )
	{
		return this.href;
	}

	/**
	 * Returns the foreign id for this object.
	 * A foreign id is a NON apiomat id (like facebook/twitter id)
	 * 
	 * @return String the foreign id
	 */
	public final String getForeignId( )
	{
		return this.data.optString( "foreignId" );
	}

	/**
	 * Set the foreign id for this object.
	 * A foreign id is a NON apiomat id (like facebook/twitter id)
	 * 
	 * @param foreignId the foreign id
	 */
	public final void setForeignId( final String foreignId )
	{
		try
		{
			this.data.put( "foreignId", foreignId );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Returns a boolean value if the access to resources is restricted by the defined roles for this object
	 * 
	 * @return boolean value if the access to resources is restricted
	 */
	public final boolean getRestrictResourceAccess( )
	{
		return this.data.optBoolean( "restrictResourceAccess" );
	}

	/**
	 * Sets if the access to resources is restricted by the defined roles for this object
	 * 
	 * @param restrictResourceAccess boolean value if the access to resources is restricted
	 */
	public final void setRestrictResourceAccess( boolean restrictResourceAccess )
	{
		try
		{
			this.data.put( "restrictResourceAccess", restrictResourceAccess );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Returns a set of all role names allowed to grant privileges on this object
	 * 
	 * @return set of all roles allowed to grant privileges on this object
	 */
	public final Set<String> getAllowedRolesGrant( )
	{
		JSONArray array = this.data.optJSONArray( "allowedRolesGrant" );
		Set<String> ret = new HashSet<String>( );
		try
		{
			for ( int i = 0; i < array.length( ); i++ )
			{
				ret.add( array.getString( i ) );
			}
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
		return ret;
	}

	/**
	 * Sets the set of all role names allowed to write this object
	 * 
	 * @param allowedRolesGrant role names allowed to write this object
	 */
	public final void setAllowedRolesGrant( final Set<String> allowedRolesGrant )
	{
		JSONArray array = new JSONArray( );
		for ( String roleName : allowedRolesGrant )
		{
			array.put( roleName );
		}
		try
		{
			this.data.put( "allowedRolesGrant", array );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Returns a set of all role names allowed to write this object
	 * 
	 * @return set of all roles allowed to write this object
	 */
	public final Set<String> getAllowedRolesWrite( )
	{
		JSONArray array = this.data.optJSONArray( "allowedRolesWrite" );
		Set<String> ret = new HashSet<String>( );
		try
		{
			for ( int i = 0; i < array.length( ); i++ )
			{
				ret.add( array.getString( i ) );
			}
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
		return ret;
	}

	/**
	 * Sets the set of all role names allowed to write this object
	 * 
	 * @param allowedRolesWrite role names allowed to write this object
	 */
	public final void setAllowedRolesWrite( final Set<String> allowedRolesWrite )
	{
		JSONArray array = new JSONArray( );
		for ( String roleName : allowedRolesWrite )
		{
			array.put( roleName );
		}
		try
		{
			this.data.put( "allowedRolesWrite", array );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Returns a set of all role names allowed to read this object
	 * 
	 * @return set of all roles allowed for this object
	 */
	public final Set<String> getAllowedRolesRead( )
	{
		JSONArray array = this.data.optJSONArray( "allowedRolesRead" );
		Set<String> ret = new HashSet<String>( );
		try
		{
			for ( int i = 0; i < array.length( ); i++ )
			{
				ret.add( array.getString( i ) );
			}
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
		return ret;
	}

	/**
	 * Sets the set of all role names allowed to read this object
	 * 
	 * @param allowedRolesRead names allowed to read this object
	 */
	public final void setAllowedRolesRead( final Set<String> allowedRolesRead )
	{
		JSONArray array = new JSONArray( );
		for ( String roleName : allowedRolesRead )
		{
			array.put( roleName );
		}
		try
		{
			this.data.put( "allowedRolesRead", array );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Returns a map containing all referenced class instance HREFs. Use it for caching purposes.
	 * 
	 * @return a map containing all referenced class instance HREFs
	 */
	public final Map<String, List<String>> getRefModelHrefs( )
	{
		try
		{
			return JsonHelper.toMapWithList( this.data.optJSONObject( "referencedHrefs" ) );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Returns HREFs of a referenced class instance, given by its name
	 * 
	 * @param name
	 * @return HREFs of a referenced class instance
	 */
	@SuppressWarnings( "unchecked" )
	public final List<String> getRefModelHrefsForName( String name )
	{
		Map<String, List<String>> referencedHrefs = getRefModelHrefs( );
		if ( referencedHrefs != null && referencedHrefs.containsKey( name ) )
		{
			return referencedHrefs.get( name );
		}
		return null;
	}

	/**
	 * Returns the date when this object was created on server side
	 * 
	 * @return date when this object was created on server side, NULL if it was created but not saved yet
	 */
	public final Date getCreatedAt( )
	{
		Date d = Calendar.getInstance( TimeZone.getTimeZone( "GMT" ) ).getTime( );
		d.setTime( this.data.optLong( "createdAt" ) );
		return d;
	}

	/**
	 * Returns the date when this object was modified last on server side
	 * 
	 * @return date when this object was modified last on server side, NULL if it was created but not saved yet
	 */
	public final Date getLastModifiedAt( )
	{
		Date d = Calendar.getInstance( TimeZone.getTimeZone( "GMT" ) ).getTime( );
		d.setTime( this.data.optLong( "lastModifiedAt" ) );
		return d;
	}

	/**
	 * Returns the name of the app where this class belongs to
	 * 
	 * @return name of the app where this class belongs to
	 */
	public final String getAppName( )
	{

		try
		{
			return this.data.getString( "applicationName" );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Returns the simple class name
	 * 
	 * @return simple class name
	 */
	public abstract String getSimpleName( );

	/**
	 * Returns the module name where this class belongs to
	 * 
	 * @return name of the module where this class belongs to
	 */
	public abstract String getModuleName( );

	/**
	 * @return the currentState
	 */
	public ObjectState getCurrentState( )
	{
		return this.currentState;
	}

	/**
	 * @param currentState
	 *        the currentState to set
	 */
	public void setCurrentState( ObjectState currentState )
	{
		this.currentState = currentState;
	}

	/**
	 * Decodes this class instance from a JSON string; used to communicate with the
	 * REST interface
	 * 
	 * @param jsonData
	 * @return this object
	 */
	public final AbstractClientDataModel fromJson( final String jsonData )
	{
		try
		{
			this.data = new JSONObject( jsonData );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
		this.href = this.data.optString( "href" );
		return this;
	}

	/**
	 * Decodes this class instance from a JSON object; used to communicate with the REST interface
	 * 
	 * @param jsonData
	 * @return this object
	 */
	public final AbstractClientDataModel fromJson( final JSONObject jsonData )
	{
		this.data = jsonData;
		this.href = this.data.optString( "href" );
		return this;
	}

	/**
	 * Encodes this class instance as a JSON string; used to communicate with the REST interface
	 * 
	 * @return this class instance as JSON string
	 */
	public final String toJson( )
	{
		String json = "";
		if ( getHref( ) != null && getHref( ).length( ) > 0 )
		{
			try
			{
				this.data.put( "id", getHref( ).substring( getHref( ).lastIndexOf( "/" ) + 1 ) );
			}
			catch ( JSONException e )
			{
				throw new RuntimeException( e );
			}
			json = this.data.toString( );
			this.data.remove( "id" );
		}
		else
		{
			json = this.data.toString( );
		}
		return json;
	}

	/**
	 * Saves this class instance. It is - based on HREF - automatically determined,
	 * if this class instance exists on the server, leading to an update, or not, leading
	 * to a post command.
	 * 
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void save( ) throws ApiomatRequestException
	{
		save( true );
	}

	/**
	 * Saves this class instance. It is - based on HREF - automatically determined,
	 * if this class instance exists on the server, leading to an update, or not, leading
	 * to an post command.
	 * 
	 * @param loadAfterwards Indicates whether after saving the object, the local object should be loaded with the
	 *        values from the server (on the first save, new values like createdAt get added on the server)
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void save( boolean loadAfterwards ) throws ApiomatRequestException
	{
		/* equals previous behavior (where just load() got called) */
		save( loadAfterwards, Datastore.getOfflineUsageForClass( this.getClass( ) ) );
	}

	/**
	 * Saves this class instance. It is - based on HREF - automatically determined,
	 * if this class instance exists on the server, leading to an update, or not, leading
	 * to an post command.
	 * 
	 * @param loadAfterwards Indicates whether after saving the object, the local object should be loaded with the
	 *        values from the server (on the first save, new values like createdAt get added on the server)
	 * @param usePersistentStorage Indicates whether to save the response of the subsequent load in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore. Only gets considered when
	 *        loadAfterwards is set to true.
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public void save( boolean loadAfterwards, final boolean usePersistentStorage ) throws ApiomatRequestException
	{
		boolean wasLocalSave = save( usePersistentStorage, null );
		if ( loadAfterwards && wasLocalSave == false )
		{
			boolean isCacheThenNetworkFirstCall = false;
			load( getHref( ), isCacheThenNetworkFirstCall, usePersistentStorage, null );
		}
	}

	private boolean save( final boolean usePersistentStorage,
		final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
		throws ApiomatRequestException
	{
		if ( isIllegalState( ) )
		{
			throw new IllegalStateException( "Object is in persisting or deleting process. Please try again later" );
		}
		setCurrentState( ObjectState.PERSISTING );
		boolean wasLocalSave = false;
		try
		{
			if ( this.href == null )
			{
				if ( Datastore.getInstance( ).sendOffline( "POST" ) )
				{
					this.setOffline( true );
					final String sendHREF =
						Datastore.getInstance( ).createModelHref( getModuleName( ), getSimpleName( ) );
					String location =
						Datastore.getInstance( ).getOfflineHandler( )
							.addTask( "POST", sendHREF, this, null, false, usePersistentStorage );
					wasLocalSave = true;
					if ( location != null )
					{
						this.href = location;
					}
				}
				else
				{
					String location = Datastore.getInstance( ).postOnServer( this, false, usePersistentStorage );
					this.href = location;
					this.setOffline( false );
				}
			}
			else
			{
				if ( Datastore.getInstance( ).sendOffline( "PUT" ) )
				{
					/* update lastmodified */
					try
					{
						this.data.put( "lastModifiedAt", System.currentTimeMillis( ) );
					}
					catch ( JSONException ex )
					{
						Log.w( "AbstractClientDataModel", "During storing the queued object to offline storage" +
							" the lastmodified attribute couldn't be updated due to a JSONException." +
							" It will be stored nevertheless.", ex );
					}
					this.setOffline( true );
					Datastore.getInstance( ).getOfflineHandler( )
						.addTask( "PUT", getHref( ), this, null, false, usePersistentStorage );
					wasLocalSave = true;
				}
				else
				{
					Datastore.getInstance( ).updateOnServer( this, false, usePersistentStorage );
					this.setOffline( false );
				}
			}
		}
		finally
		{
			setCurrentState( wasLocalSave ? ObjectState.LOCAL_PERSISTED : ObjectState.PERSISTED );
		}
		return wasLocalSave;
	}

	/**
	 * Saves the object in background and not on the UI thread.
	 * Automatically loads the object after the save.
	 * 
	 * @param callback
	 *        The callback which will get executed after the save AND the subsequent load are both done
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public AOMTask<Void> saveAsync( final AOMEmptyCallback callback )
	{
		return saveAsync( true, Datastore.getOfflineUsageForClass( this.getClass( ) ), null, callback );
	}

	/**
	 * Saves the object in background and not on the UI thread.
	 * Automatically loads the object after the save.
	 *
	 * @param saveCallback
	 *        The callback which will get executed after the save is done
	 * @param loadCallback
	 *        The callback which will get executed after subsequent load is done
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public AOMTask<Void> saveAsync( final AOMEmptyCallback saveCallback, final AOMEmptyCallback loadCallback )
	{
		return saveAsync( true, Datastore.getOfflineUsageForClass( this.getClass( ) ), saveCallback, loadCallback );
	}

	/**
	 * Saves the object in background and not on the UI thread.
	 * Automatically loads the object after the save.
	 * 
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param callback
	 *        The callback which will get executed after the save AND the subsequent load are both done
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public AOMTask<Void> saveAsync( final boolean usePersistentStorage, final AOMEmptyCallback callback )
	{
		return saveAsync( true, usePersistentStorage, null, callback );
	}

	/**
	 * Saves the object in background and not on the UI thread.
	 * Automatically loads the object after the save.
	 *
	 * @param loadAfterwards Indicates whether after saving the object, the local object should be loaded with the
	 *        values from the server (on the first save, new values like createdAt get added on the server)
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param callback
	 *        The callback which will get executed after the save is done (if loadAfterwards is set to false) or after
	 *        the save AND the subsequent load (if loadAfterwards is true) are BOTH done
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public AOMTask<Void> saveAsync( final boolean loadAfterwards, final boolean usePersistentStorage,
		final AOMEmptyCallback callback )
	{
		if ( loadAfterwards )
		{
			return saveAsync( loadAfterwards, usePersistentStorage, null, callback );
		}
		return saveAsync( loadAfterwards, usePersistentStorage, callback, null );
	}

	/**
	 * Saves the object in background and not on the UI thread.
	 * Automatically loads the object after the save.
	 *
	 * @param loadAfterwards Indicates whether after saving the object, the local object should be loaded with the
	 *        values from the server (on the first save, new values like createdAt get added on the server)
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param saveCallback
	 *        The callback which will get executed after the save is done
	 * @param loadCallback
	 *        The callback which will get executed after subsequent load is done
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public AOMTask<Void> saveAsync( final boolean loadAfterwards, final boolean usePersistentStorage,
		final AOMEmptyCallback saveCallback, final AOMEmptyCallback loadCallback )
	{
		/* Note: This method calls the sync save method from an async task,
		 * so the setting of the states gets done there. Only check the state now. */
		if ( isIllegalState( ) )
		{
			throw new IllegalStateException( "Object is in persisting or deleting process. Please try again later" );
		}
		final AOMTask<Void> saveTask = new AOMTask<Void>( )
		{
			@Override
			public Void doRequest( AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
				throws ApiomatRequestException
			{
				AbstractClientDataModel.this.save( usePersistentStorage, wasLoadedFromStorageAtomicRef );
				return null;
			}
		};

		if ( loadAfterwards )
		{
			/* If called by other saveAsync methods from this class, saveCallback can be null.
			 * In that case, execute the task without a callback. The loadCallback will be called later. */
			if ( saveCallback == null )
			{
				saveTask.execute( );
			}
			else
			{
				saveTask.execute( saveCallback );
			}
		}
		else
		{
			/* Again, if called by other saveAsync method from this class, saveCallback can be null.
			 * But this time the callback needs to be executed with the loadCallback, although it's a save. */
			saveTask.execute( saveCallback );
			return saveTask;
		}

		/* loadAfterwards is true from here on */
		/* this loadTask wraps the waiting for the save task and the actual load task */
		final AOMTask<Void> loadTask = new AOMTask<Void>( )
		{
			@Override
			public Void doRequest( AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
				throws ApiomatRequestException
			{
				try
				{
					/* wait for the save task to finish to continue */
					saveTask.get( );
					/* only actually load afterwards if the previous save was not local */
					if ( getCurrentState( ) == ObjectState.PERSISTED )
					{
						if ( Datastore.getCachingStrategy( ) == Datastore.AOMCacheStrategy.CACHE_THEN_NETWORK )
						{
							boolean wasLoadedFromStorage = false;
							ApiomatRequestException ex = null;
							try
							{
								load( getHref( ), true, usePersistentStorage, wasLoadedFromStorageAtomicRef );
							}
							catch ( ApiomatRequestException e )
							{
								ex = e;
							}
							if ( wasLoadedFromStorageAtomicRef.get( ) != null )
							{
								wasLoadedFromStorage = wasLoadedFromStorageAtomicRef.get( );
							}
							if ( this.callbackMethod != null )
							{
								final Runnable test =
									new CallBackRunnable( this.callbackMethod, wasLoadedFromStorage, ex );
								new Handler( Looper.getMainLooper( ) ).post( test );
							}
						}
						load( href, false, usePersistentStorage, wasLoadedFromStorageAtomicRef );
					}
					/* If persisted locally because the device is offline, don't actually load, but still execute the
					 * callback */
					else if ( getCurrentState( ) == ObjectState.LOCAL_PERSISTED && this.callbackMethod != null )
					{
						/* not loaded at all, but locally persisted, so data is like loaded from storage */
						wasLoadedFromStorageAtomicRef.set( true );
					}
				}
				catch ( InterruptedException ex )
				{
					wasLoadedFromStorageAtomicRef.set( false );
					/* exception will be catched by AOMAsyncTask */
					throw new ApiomatRequestException( com.apiomat.frontend.Status.ASYNC_WAIT_ERROR,
						HttpURLConnection.HTTP_OK, "The task was interrupted" );
				}
				catch ( ExecutionException ex )
				{
					/* According to javadoc this gets thrown "If the computation threw an exception."
					 * If that means exceptions within the task (probably ApiomatRequestExceptions), they need to get
					 * unpacked.
					 * In a unit test it's different though - an ApiomatRequestException gets thrown directly. */
					wasLoadedFromStorageAtomicRef.set( false );
					/* exception will be catched by AOMAsyncTask */
					throw new ApiomatRequestException( com.apiomat.frontend.Status.ASYNC_WAIT_ERROR,
						HttpURLConnection.HTTP_OK, "An exception occured in the task while waiting for it." +
							" Type: " + ex.getCause( ).getClass( ) + "; Message: " + ex.getCause( ).getMessage( ) );
				}
				return null;
			}
		};
		loadTask.execute( loadCallback );
		return loadTask;
	}

	/**
	 * Loads (updates) this class instance with server values
	 * 
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public final void load( ) throws ApiomatRequestException
	{
		load( Datastore.getOfflineUsageForClass( this.getClass( ) ) );
	}

	/**
	 * Loads (updates) this class instance with server values
	 * 
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public final void load( boolean usePersistentStorage ) throws ApiomatRequestException
	{
		load( null, usePersistentStorage );
	}

	/**
	 * Loads (updates) this class instance with server values. Since you have to
	 * pass the HREF for this method, only use it when loading a class instance which
	 * has no HREF in it (was not sent/loaded before). Else use {@link #load()}
	 * 
	 * @param href The HREF of this class instance
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public final void load( final String href ) throws ApiomatRequestException
	{
		load( href, Datastore.getOfflineUsageForClass( this.getClass( ) ) );
	}

	/**
	 * Loads (updates) this class instance with server values. Since you have to
	 * pass the HREF for this method, only use it when loading a class instance which
	 * has no HREF in it (was not sent/loaded before). Else use {@link #load()}
	 * 
	 * @param href The HREF of this class instance
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @throws com.apiomat.frontend.ApiomatRequestException
	 */
	public final void load( final String href, final boolean usePersistentStorage ) throws ApiomatRequestException
	{
		load( href, false, usePersistentStorage, null );
	}

	private final void load( final String href, final boolean isCacheThenNetworkFirstCall,
		final boolean usePersistentStorage, final AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
		throws ApiomatRequestException
	{
		if ( isIllegalState( ) )
		{
			throw new IllegalStateException( "Object is in persisting or deleting process. Please try again later" );
		}
		Datastore.getInstance( ).loadFromServer( href == null ? this.getHref( ) : href, this, false, false,
			isCacheThenNetworkFirstCall, usePersistentStorage, wasLoadedFromStorageAtomicRef );
		// Set href only if was given
		if ( href != null )
		{
			this.href = this.data.optString( "href", null );
		}
	}

	/**
	 * Loads (updates) this class instance with server values in the background
	 * 
	 * @param callback The callback method which will called when request is finished
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public final AOMTask<Void> loadAsync( final AOMEmptyCallback callback )
	{
		return loadAsync( null, callback, Datastore.getOfflineUsageForClass( this.getClass( ) ) );
	}

	/**
	 * Loads (updates) this class instance with server values in the background
	 *
	 * @param callback The callback method which will called when request is finished
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public final AOMTask<Void> loadAsync( final AOMEmptyCallback callback, boolean usePersistentStorage )
	{
		return loadAsync( null, callback, usePersistentStorage );
	}

	/**
	 * Loads (updates) this class instance with server values in background. Since
	 * you have to pass the HREF for this method, only use it when loading a
	 * class instance which has no HREF in it (was not sent/loaded before). Else use
	 * {@link #loadAsync(com.apiomat.frontend.callbacks.AOMEmptyCallback callback)}
	 * 
	 * Throws an IllegalStateException if object is in persisting process
	 * 
	 * @param href The HREF of this class instance
	 * @param callback The callback method which will called when request is finished
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public final AOMTask<Void> loadAsync( final String href, final AOMEmptyCallback callback )
	{
		return loadAsync( href, callback, Datastore.getOfflineUsageForClass( this.getClass( ) ) );
	}

	/**
	 * Loads (updates) this class instance with server values in background. Since
	 * you have to pass the HREF for this method, only use it when loading a
	 * class instance which has no HREF in it (was not sent/loaded before). Else use
	 * {@link #loadAsync(com.apiomat.frontend.callbacks.AOMEmptyCallback callback)}
	 * 
	 * Throws an IllegalStateException if object is in persisting process
	 * 
	 * @param href The HREF of this class instance
	 * @param callback The callback method which will get called when request is finished
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an
	 *         error.
	 */
	public final AOMTask<Void> loadAsync( final String href, final AOMEmptyCallback callback,
		final boolean usePersistentStorage )
	{
		if ( isIllegalState( ) )
		{
			throw new IllegalStateException( "Object is in persisting or deleting process. Please try again later" );
		}
		if ( Datastore.getCachingStrategy( ) == Datastore.AOMCacheStrategy.CACHE_THEN_NETWORK )
		{
			AOMTask<Void> loadTask = new AOMTask<Void>( )
			{
				@Override
				public Void doRequest( AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
					throws ApiomatRequestException
				{
					AbstractClientDataModel.this.load( href, true, usePersistentStorage, wasLoadedFromStorageAtomicRef );
					return null;
				}
			};
			loadTask.execute( callback );
		}
		AOMTask<Void> loadTask = new AOMTask<Void>( )
		{
			@Override
			public Void doRequest( AtomicReference<Boolean> wasLoadedFromStorageAtomicRef )
				throws ApiomatRequestException
			{
				AbstractClientDataModel.this.load( href, false, usePersistentStorage, wasLoadedFromStorageAtomicRef );
				return null;
			}
		};
		loadTask.execute( callback );
		return loadTask;
	}

	/**
	 * Deletes this class instance on the server
	 * 
	 * @throws Exception
	 */
	public final void delete( ) throws ApiomatRequestException
	{
		delete( Datastore.getOfflineUsageForClass( this.getClass( ) ) );
	}

	/**
	 * Deletes this class instance on the server
	 * 
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @throws Exception
	 */
	public final void delete( final boolean usePersistentStorage ) throws ApiomatRequestException
	{
		if ( isIllegalState( ) )
		{
			throw new IllegalStateException( "Object is in persisting or deleting process. Please try again later" );
		}
		setCurrentState( ObjectState.DELETING );
		boolean wasLocalDelete = false;
		try
		{
			final boolean isRef = false;
			if ( Datastore.getInstance( ).sendOffline( "DELETE" ) )
			{
				Datastore.getInstance( ).getOfflineHandler( )
					.addTask( "DELETE", getHref( ), this, null, isRef, usePersistentStorage );
				wasLocalDelete = true;
			}
			else
			{
				Datastore.getInstance( ).deleteOnServer( this.getHref( ), isRef, usePersistentStorage );
			}
		}
		finally
		{
			setCurrentState( wasLocalDelete ? ObjectState.LOCAL_DELETED : ObjectState.DELETED );
		}
	}

	/**
	 * Deletes this class instance on the server in a background task
	 * 
	 * @param callback Callback method which is called after deletion was finished on server
	 * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an
	 *         error.
	 */
	public AOMTask<Void> deleteAsync( AOMEmptyCallback callback )
	{
		return deleteAsync( Datastore.getOfflineUsageForClass( this.getClass( ) ), callback );
	}

	/**
	 * Deletes this class instance on the server in a background task
	 * 
	 * @param usePersistentStorage Indicates whether to save the response in persistent storage.
	 *        Has a higher priority than the setting per class in the Datastore.
	 * @param callback Callback method which is called after deletion was finished on server
	 * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an
	 *         error.
	 */
	public AOMTask<Void> deleteAsync( final boolean usePersistentStorage, AOMEmptyCallback callback )
	{
		/* Note: This method calls the sync delete method from an async task,
		 * so the setting of the states gets done there. Only check the state now. */
		if ( isIllegalState( ) )
		{
			throw new IllegalStateException( "Object is in persisting or deleting process. Please try again later" );
		}
		AOMTask<Void> deleteTask = new AOMTask<Void>( )
		{
			@Override
			public Void doRequest( AtomicReference<Boolean> ignore ) throws ApiomatRequestException
			{
				AbstractClientDataModel.this.delete( usePersistentStorage );
				return null;
			}
		};
		deleteTask.execute( callback );
		return deleteTask;
	}

	private void readObject( ObjectInputStream ois )
		throws IOException
	{
		fromJson( ois.readUTF( ) );
	}

	private void writeObject( ObjectOutputStream ois )
		throws IOException
	{
		ois.writeUTF( toJson( ) );
	}

	public boolean isOffline( )
	{
		return this.data.optBoolean( "isOffline", false );
	}

	public void setOffline( boolean offline )
	{

		try
		{
			this.data.put( "isOffline", offline );
		}
		catch ( JSONException e )
		{
			throw new RuntimeException( e );
		}
	}

	public String getID( )
	{
		String id = this.data.optString( "id", null );
		if ( id == null )
		{
			/* extract from HREF */
			id = getHref( ).substring( getHref( ).lastIndexOf( "/" ) + 1 );
		}
		return id;
	}

	public static enum ObjectState
	{
		DELETING, DELETED, PERSISTING, PERSISTED, LOCAL_PERSISTED, LOCAL_DELETED;
	}

	/**
	 * Deletes this object from the storage (cache and persistent storage)
	 */
	public boolean deleteFromStorage( )
	{
		return Datastore.getInstance( ).deleteObjectFromStorage( this.getHref( ), false );
	}

	private boolean isIllegalState( )
	{
		/* assume legal state when state should not be checked */
		boolean result = false;

		if ( Datastore.getInstance( ).getCheckObjectState( ) )
		{
			result =
				getCurrentState( ) != null &&
					( getCurrentState( ).equals( ObjectState.PERSISTING ) || getCurrentState( ).equals(
						ObjectState.DELETING ) );
		}
		return result;
	}
}