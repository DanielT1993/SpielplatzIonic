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
package com.apiomat.frontend.basics;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import android.text.TextUtils;
import android.util.Log;
import com.apiomat.frontend.*;
import com.apiomat.frontend.callbacks.*;
import com.apiomat.frontend.helper.*;

import org.json.*;


/**
 * Generated class representing a user in your app 
 */
public class User extends AbstractClientDataModel
{
    public static final String apiKey = "8941539240849356519";
    public static final String baseURL = "https://apiomat.org/yambas/rest/apps/Spielplatz";
    public static final String system = "LIVE";
    public static final String sdkVersion = "2.0.9-2552";
    /**
     * Default constructor. Needed for internal processing.
     */
    public User ( )
    {
        super( );
    }
    

    /**
     * Contructor for initializing the user with a username and password
     */
    public User ( String userName, String password )
    {
        this.setUserName ( userName );
        this.setPassword ( password );
    }

    /**
     * Returns the simple name of this class 
     */
    public String getSimpleName( )
    {
        return "User";
    }

    /**
     * Returns the name of the module where this class belongs to
     */
    public String getModuleName( )
    {
        return "Basics";
    }

    /**
     * Initialize the Datastore if it hasn't been configured yet.
     * First tries to use username + password. If not present: Session token. If not present and parameter allowGuest set to true: Init as GUEST
     * @param allowGuest Indicates whether the datastore may be configured as guest when neither credentials nor a session token is present in the user object
     * @exception IllegalStateException When the parameter allowGuest is set to false and neither user credentials nor a session token is present in the user object.
     */
    protected void initDatastoreIfNeeded(boolean allowGuest)
    {
        try 
        {
            Datastore.getInstance();
        } 
        catch (IllegalStateException e) 
        {
            if (this.getUserName( ) != null && !this.getUserName( ).isEmpty() && this.getPassword( ) != null && !this.getPassword( ).isEmpty())
            {
                Datastore.configureWithCredentials(this);
            }
            else if (this.getSessionToken( ) != null && !this.getSessionToken( ).isEmpty())
            {
                Datastore.configureWithSessionToken(baseURL, apiKey, system, this.getSessionToken( ));
            }
            else if (allowGuest)
            {
                Datastore.configureAsGuest(baseURL, apiKey, system);
            }
            else
            {
                throw new IllegalStateException("The Datastore needs to be configured with user credentials or a session token for this method to work." );
            }
        }
    }
    
    /**
     * Updates this class from server 
     */
    public void loadMe( ) throws ApiomatRequestException
    {
        initDatastoreIfNeeded(false);
        load( "models/me" );
    }
    
    /**
     * Updates this class from server in the background and not on the UI thread
     * 
     * @param callback
     * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an error.
     */
    public AOMTask<Void> loadMeAsync(AOMEmptyCallback callback)
    {
        initDatastoreIfNeeded(false);
        return loadAsync("models/me", callback);
    }

    /**
     * Saves this object. If it has no HREF this leads to a post and the class instance 
     * is created on the server, else it is updated. After the save a load will
     * be called to load the actual object from the server. 
     */
    @Override
    public void save() throws ApiomatRequestException 
    {
        save( true );
    }

    /**
     * Saves this object. If it has no HREF this leads to a post and the class instance 
     * is created on the server, else it is updated. After the save a load will
     * be called to load the actual object from the server. 
     * @param loadAfterwards Indicates whether after saving the object, the local object should be loaded with the
     *        values from the server (on the first save, new values like createdAt and href get added on the server)
     */
    @Override
    public void save( boolean loadAfterwards ) throws ApiomatRequestException 
    {
        initDatastoreIfNeeded(false);
        super.save( loadAfterwards );
    }

    /**
     * Saves this object. If it has no HREF this leads to a post and the class instance 
     * is created on the server, else it is updated. After the save a load will
     * be called to load the actual object from the server.
     * 
     * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an error.
     */
    public AOMTask<Void> saveAsync( final AOMEmptyCallback callback )
    {
        initDatastoreIfNeeded(false);
        return super.saveAsync(callback);
    }

    /**
     * Requests a new password; user will receive an email to confirm; errors are silently ignored.
     * Runs asynchronously.
     */
    public void requestNewPassword( )
    {
        this.requestNewPasswordAsync( new AOMCallback<String>(){
            /* The old isDone will be deprecated soon */
            @Override
            public void isDone(String result, ApiomatRequestException exception) {}
            @Override
            public void isDone(String result, boolean wasLoadedFromStorage, ApiomatRequestException exception)
            {
                // ignore
            }
        });
    }

    /**
     * Requests a new password; user will receive an email to confirm
     * Runs asynchronously.
     * @param cb The callback
     */
    public void requestNewPasswordAsync( final AOMCallback<String> cb )
    {
        Datastore.getInstance( ).postOnServerAsync("models/requestResetPassword/", this, false, false, cb );
    }
    
    /**
     * Reset password 
     * @param newPassword the new password
     */
    public void resetPassword( String newPassword ) throws ApiomatRequestException
    {
        this.setPassword( newPassword );
        Datastore.getInstance( ).updateOnServer( this, false, false );
        Datastore.configure(baseURL, apiKey, this.getUserName(), this.getPassword(), sdkVersion, system);
    }

    /**
     * Reset password asynchronously
     * @param newPassword the new password
     * @return A reference to the task that runs in the background
     */
    public AOMTask<Void> resetPasswordAsync( final String newPassword, final AOMEmptyCallback callback )
    {
        if ( getCurrentState( ).equals( ObjectState.PERSISTING ) )
        {
            throw new IllegalStateException(
                "Object is in persisting process. Please try again later" );
        }
        AOMTask<Void> task = new AOMTask<Void>( )
        {
            @Override
            public Void doRequest( AtomicReference<Boolean> ignore ) throws ApiomatRequestException
            {
                resetPassword( newPassword );
                return null;
            }
        };
        task.execute( callback );
        return task;
    }

    /**
     * Requests a session token with the credentials saved in this User object.
     * Also configures the Datastore with the received token and saves it in the user object.
     *
     * @return A HashMap that maps "sessionToken", "refreshToken", "expirationDate" (Unix UTC timestamp in ms) as well as
     * the additional fields "module", and "model" to their values, which can be different from getModuleName() and getSimpleName()
     *
     * @deprecated Use {@link #requestTokenContainer()} instead
     */
     @Deprecated
     public Map<String, String> requestSessionToken() throws ApiomatRequestException
     {
         return requestSessionToken(true);
     }
     
    /**
     * Requests a TokenContainer with the credentials saved in this User object.
     * Also configures the Datastore with the received session token and saves it in the user object.
     *
     * @return A TokenContainer
     */
     public TokenContainer requestTokenContainer() throws ApiomatRequestException
     {
         return requestTokenContainer(true);
     }
     
    /**
     * Requests a session token with the credentials saved in this User object.
     * Optionally sets the session token attribute of the user and configures the Datastore with the session token automatically.
     *
     * @param configure Set flag to false if you don't want the Datastore to automatically be configured with the received session token and also don't want to save the token in the user object
     * @return A HashMap that maps "sessionToken", "refreshToken", "expirationDate" (Unix UTC timestamp in ms) as well as
     * the additional fields "module", and "model" to their values, which can be different from getModuleName() and getSimpleName()
     *
     * @deprecated Use {@link #requestTokenContainer(boolean)} instead
     */
     @Deprecated
    public Map<String, String> requestSessionToken( final boolean configure ) throws ApiomatRequestException
    {
        initDatastoreIfNeeded(false);
        if (!configure)
        {
            return Datastore.getInstance().requestSessionToken();
        }
        Map<String, String> tokenMap = Datastore.getInstance().requestSessionToken();
        String sessionToken = tokenMap.get( "sessionToken" );
        if ( sessionToken == null )
        {
            throw new ApiomatRequestException( Status.NO_TOKEN_RECEIVED );
        }
        this.setSessionToken( sessionToken );
        Datastore.configureWithSessionToken( baseURL, apiKey, system, sessionToken );
        return tokenMap;
    }
    
    /**
     * Requests a TokenContainer with the credentials saved in this User object.
     * Optionally sets the session token attribute of the user and configures the Datastore with the session token automatically.
     *
     * @param configure Set flag to false if you don't want the Datastore to automatically be configured with the received session token and also don't want to save the token in the user object
     * @return A TokenContainer
     */
    public TokenContainer requestTokenContainer( final boolean configure ) throws ApiomatRequestException
    {
        initDatastoreIfNeeded(false);
        if (!configure)
        {
            return Datastore.getInstance().requestTokenContainer();
        }
        TokenContainer tokenContainer = Datastore.getInstance().requestTokenContainer();
        String sessionToken = tokenContainer.getSessionToken();
        if ( sessionToken == null )
        {
            throw new ApiomatRequestException( Status.NO_TOKEN_RECEIVED );
        }
        this.setSessionToken( sessionToken );
        Datastore.configureWithSessionToken( baseURL, apiKey, system, sessionToken );
        return tokenContainer;
    }

    /**
     * Request a session token with the credentials saved in this User object.
     * Automatically saves the token in the user object and configures the Datastore with the token.
     * This method runs in a background task.
     *
     * @param callback The callback
     * @return A reference to the task that runs in the background
     *
     * @deprecated Use {@link #requestTokenContainerAsync(AOMCallback)} instead
     */
     @Deprecated
    public AOMTask<Map<String, String>> requestSessionTokenAsync( final AOMCallback<Map<String,String>> callback )
    {
        return requestSessionTokenAsync(true, callback);
    }

    /**
     * Request a TokenContainer with the credentials saved in this User object.
     * Automatically saves the token in the user object and configures the Datastore with the token.
     * This method runs in a background task.
     *
     * @param callback The callback
     * @return The task that runs in the background
     */
    public AOMTask<TokenContainer> requestTokenContainerAsync( final AOMCallback<TokenContainer> callback )
    {
        return requestTokenContainerAsync(true, callback);
    }

    /**
     * Request a session token with the credentials saved in this User object.
     * Optionally sets the attribute of the user and configures the Datastore with the session token automatically.
     * This method runs in a background task.
     *
     * @param configure Set flag to true if you want the Datastore to automatically be configured with the received session token and also save it in the user object
     * @param callback The callback
     * @return A reference to the task that runs in the background
     *
     * @deprecated Use {@link #requestTokenContainerAsync(boolean, AOMCallback)} instead
     */
     @Deprecated
    public AOMTask<Map<String, String>> requestSessionTokenAsync( final boolean configure, final AOMCallback<Map<String,String>> callback )
    {
        AOMTask<Map<String, String>> requestTask = new AOMTask<Map<String, String>>( )
        {
            @Override
            public Map<String, String> doRequest( AtomicReference<Boolean> ignore ) throws ApiomatRequestException
            {
                return requestSessionToken( configure );
            }
        };
        requestTask.execute( callback );
        return requestTask;
    }

    /**
     * Request a TokenContainer with the credentials saved in this User object.
     * Optionally sets the attribute of the user and configures the Datastore with the session token automatically.
     * This method runs in a background task.
     *
     * @param configure Set flag to true if you want the Datastore to automatically be configured with the received session token and also save it in the user object
     * @param callback The callback
     * @return A reference to the task that runs in the background
     */
    public AOMTask<TokenContainer> requestTokenContainerAsync( final boolean configure, final AOMCallback<TokenContainer> callback )
    {
        AOMTask<TokenContainer> requestTask = new AOMTask<TokenContainer>( )
        {
            @Override
            public TokenContainer doRequest( AtomicReference<Boolean> ignore ) throws ApiomatRequestException
            {
                return requestTokenContainer( configure );
            }
        };
        requestTask.execute( callback );
        return requestTask;
    }

    /**
     * Request a session token with a refresh token.
     * Optionally configures the datastore with the received token and saves it in the user object.
     *
     * @param refreshToken The refresh token to use for requesting a new session token
     * @param configure Set flag to true if you want the Datastore to automatically be configured with the received session token and also save it in the user object.
     * @return A HashMap that maps "sessionToken", "refreshToken", "expirationDate" (Unix UTC timestamp in ms) as well as
     * the additional fields "module", and "model" to their values, which can be different from getModuleName() and getSimpleName()
     *
     * @deprecated Use {@link #requestTokenContainer(String, boolean)} instead
     */
     @Deprecated
    public Map<String, String> requestSessionToken(final String refreshToken, final boolean configure) throws ApiomatRequestException
    {
        initDatastoreIfNeeded(true);
        if (!configure)
        {
            return Datastore.getInstance().requestSessionToken( refreshToken );
        }
        Map<String, String> tokenMap = Datastore.getInstance().requestSessionToken( refreshToken );
        String sessionToken = tokenMap.get( "sessionToken" );
        if ( sessionToken == null )
        {
            throw new ApiomatRequestException( Status.NO_TOKEN_RECEIVED );
        }
        // Disabled since the field got removed from the User class. The field might get re-enabled in the future.
//        this.setSessionToken( sessionToken );
        Datastore.configureWithSessionToken( baseURL, apiKey, system, sessionToken );
        return tokenMap;
    }

    /**
     * Request a TokenContainer with a refresh token.
     * Optionally configures the datastore with the received session token and saves it in the user object.
     *
     * @param refreshToken The refresh token to use for requesting a new TokenContainer
     * @param configure Set flag to true if you want the Datastore to automatically be configured with the received session token and also save it in the user object.
     * @return A TokenContainer
     */
    public TokenContainer requestTokenContainer(final String refreshToken, final boolean configure) throws ApiomatRequestException
    {
        initDatastoreIfNeeded(true);
        if (!configure)
        {
            return Datastore.getInstance().requestTokenContainer( refreshToken );
        }
        TokenContainer tokenContainer = Datastore.getInstance().requestTokenContainer( refreshToken );
        String sessionToken = tokenContainer.getSessionToken();
        if ( sessionToken == null )
        {
            throw new ApiomatRequestException( Status.NO_TOKEN_RECEIVED );
        }
        // Disabled since the field got removed from the User class. The field might get re-enabled in the future.
//        this.setSessionToken( sessionToken );
        Datastore.configureWithSessionToken( baseURL, apiKey, system, sessionToken );
        return tokenContainer;
    }

    /**
     * Request a session token with a refresh token. Optionally configures the datastore with the received token and saves it in the user object. Runs in the background.
     * @param refreshToken The refresh token to use for requesting a new session token
     * @param configure Set flag to true if you want the Datastore to automatically be configured with the received session token and also save it in the user object.
     * @return A reference to the task that runs in the background
     *
     * @deprecated Use {@link #requestTokenContainerAsync(String, boolean, AOMCallback)} instead
     */
     @Deprecated
    public AOMTask<Map<String, String>> requestSessionTokenAsync(final String refreshToken, final boolean configure, final AOMCallback<Map<String,String>> callback)
    {
        AOMTask<Map<String, String>> requestTask = new AOMTask<Map<String, String>>( )
        {
            @Override
            public Map<String, String> doRequest( AtomicReference<Boolean> ignore ) throws ApiomatRequestException
            {
                return requestSessionToken( refreshToken, configure );
            }
        };
        requestTask.execute(callback);
        return requestTask;
    }

    /**
     * Request a TokenContainer with a refresh token.
     * Optionally configures the Datastore with the received session token and saves it in the user object.
     * Runs in the background.
     *
     * @param refreshToken The refresh token to use for requesting a new TokenContainer
     * @param configure Set flag to true if you want the Datastore to automatically be configured with the received session token and also save it in the user object.
     * @return A reference to the task that runs in the background
     */
    public AOMTask<TokenContainer> requestTokenContainerAsync(final String refreshToken, final boolean configure, final AOMCallback<TokenContainer> callback)
    {
        AOMTask<TokenContainer> requestTask = new AOMTask<TokenContainer>( )
        {
            @Override
            public TokenContainer doRequest( AtomicReference<Boolean> ignore ) throws ApiomatRequestException
            {
                return requestTokenContainer( refreshToken, configure );
            }
        };
        requestTask.execute(callback);
        return requestTask;
    }

     
    /**
     * Returns a list of objects of this class filtered by the given query from server
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<User> getUsers( String query ) throws ApiomatRequestException
    {
        return getUsers( query, Datastore.getOfflineUsageForClass(User.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from the server.
     * Important: The boolean parameter indicates the use of offline storage, not if referenced HREFs should be included as in older SDK versions.
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in the persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<User> getUsers( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        User o = new User();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), User.class, query, false, false, usePersistentStorage );
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<List<User>> getUsersAsync(final String query, final AOMCallback<List<User>> listAOMCallback)
    {
        return getUsersAsync( query, listAOMCallback, Datastore.getOfflineUsageForClass(User.class) );
    }
    
    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<List<User>> getUsersAsync(final String query, final AOMCallback<List<User>> listAOMCallback, final boolean usePersistentStorage)
    {
         User o = new  User();
         return Datastore.getInstance().loadFromServerAsync(o.getModuleName(), o.getSimpleName(), User.class, query, false, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<User> getUsersWithReferencedHrefs( String query ) throws ApiomatRequestException
    {
        return getUsersWithReferencedHrefs( query, Datastore.getOfflineUsageForClass(User.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<User> getUsersWithReferencedHrefs( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        User o = new User();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), User.class, query, true, false, usePersistentStorage);
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background
     */
    public static AOMTask<List<User>> getUsersWithReferencedHrefsAsync(final String query, final AOMCallback<List<User>> listAOMCallback)
    {
         return getUsersWithReferencedHrefsAsync( query, Datastore.getOfflineUsageForClass(User.class), listAOMCallback); 
    }
    
    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background
     */
    public static AOMTask<List<User>> getUsersWithReferencedHrefsAsync(final String query, final boolean usePersistentStorage, final AOMCallback<List<User>> listAOMCallback)
    {
         User o = new  User();
         return Datastore.getInstance().loadFromServerAsync( o.getModuleName(), o.getSimpleName(), User.class, query, true, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a count of objects of this class filtered by the given query from server
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final long getUsersCount( String query ) throws ApiomatRequestException
    {
        final User o = new User();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServer( countHref, User.class, query, false, false );
    }
    
    /**
     * Get a count of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<Long> getUsersCountAsync(final String query, final AOMCallback<Long> listAOMCallback)
    {
        final User o = new User();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServerAsync( countHref, User.class, query, false, listAOMCallback);
    }


        public Date getDateOfBirth( )
    {
        if(this.data.has( "dateOfBirth" ))
        {
            try
            {
                return new Date( this.data.getLong( "dateOfBirth" ) );
            }
            catch ( JSONException e )
            {
                throw new RuntimeException( e );
            }
        }
        return null;
    }

    public void setDateOfBirth( Date dateOfBirth )
    {
        try
        {
            this.data.putOpt( "dateOfBirth", dateOfBirth.getTime( ) );
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }


        public Map getDynamicAttributes()
    {
        Map map = new HashMap();
        if(this.data.has( "dynamicAttributes" ))
        {
            try
            {
                map = JsonHelper.toMap( this.data.optJSONObject( "dynamicAttributes" ) );
            }
            catch ( JSONException e )
            {
                throw new RuntimeException( e );
            }
        }
        return map;
    }

    public void setDynamicAttributes( Map map )
    {
        try
        {
            this.data.put("dynamicAttributes", new JSONObject(map));
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getFirstName()
    {
         return this.data.optString( "firstName" );
    }

    public void setFirstName(String firstName )
    {
        try
        {
            this.data.put( "firstName",firstName);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getLastName()
    {
         return this.data.optString( "lastName" );
    }

    public void setLastName(String lastName )
    {
        try
        {
            this.data.put( "lastName",lastName);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public double getLocLatitude( )
    {
         final JSONArray loc = this.data.optJSONArray( "loc" );
         try
         {
            final Object raw = loc.get( 0 );
            return convertNumberToDouble( raw );
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }
    
    public double getLocLongitude( )
    {
         final JSONArray loc = this.data.optJSONArray( "loc" );
         try
         {
            final Object raw = loc.get( 1 );
            return convertNumberToDouble( raw );
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }
    public void setLocLatitude( double latitude )
    {
        if ( this.data.has( "loc" ) == false || this.data.opt("loc") == null )
        {
            try
            {
                this.data.put( "loc", new JSONArray( ) );
            }
            catch ( JSONException e )
            {
                throw new RuntimeException( e );
            }
        }

        try
        {
            this.data.getJSONArray( "loc" ).put( 0, latitude );
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }
    
    public void setLocLongitude( double longitude )
    {
        try
        {
            if ( this.data.has( "loc" ) == false || this.data.opt("loc") == null )
            {
                this.data.put( "loc", new JSONArray( ) );
            }
            if ( this.data.getJSONArray( "loc" ).length( ) == 0 )
            {
                this.data.getJSONArray( "loc" ).put( 0, 0 );
            }
    
            this.data.getJSONArray( "loc" ).put( 1, longitude );
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }


        public String getPassword()
    {
         return this.data.optString( "password" );
    }

    public void setPassword(String password )
    {
        try
        {
            this.data.put( "password",password);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getSessionToken()
    {
         return this.data.optString( "sessionToken" );
    }

    public void setSessionToken(String sessionToken )
    {
        try
        {
            this.data.put( "sessionToken",sessionToken);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getUserName()
    {
         return this.data.optString( "userName" );
    }

    public void setUserName(String userName )
    {
        try
        {
            this.data.put( "userName",userName);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }



 
    /**
     * Deletes the classes that have been fetched with getUsers() (or its async version) before
     *
     * Note: The SDK doesn't contain a query parser, so you need to pass the same query as in the fetch request.
     * Also, if you fetched objects from a class with different queries, the object doesn't get deleted
     *
     * @param query
     */
    public static int deleteAllFromStorage(final String query)
    {
        return deleteAllFromStorage(false, query);
    }

    /**
     * Deletes the classes that have been fetched with getUsersWithReferencedHref (or its async version) before
     *
     * Note:
     * - The SDK doesn't contain a query parser, so you need to pass the same query as in the fetch request.
     * - Also, if you fetched objects from a class with different queries, the object doesn't get deleted
     * - Lastly, if objects of this class were fetched in the context of loading a reference collection,
     *   those objects won't be deleted as well (use the respective delete..FromStorage method for that).
     *
     * @param query
     */
    public static int deleteAllFromStorageWithReferencedHrefs( final String query)
    {
        return deleteAllFromStorage(true, query);
    }

    private static int deleteAllFromStorage(final boolean withReferencedHrefs, final String query)
    {
        final User o = new User();
        String collectionHref = Datastore.getInstance().createModelHrefWithParams( o.getModuleName( ),
            o.getSimpleName( ), withReferencedHrefs, query );
        return Datastore.getInstance().deleteCollectionFromStorage( collectionHref );
    }
}
