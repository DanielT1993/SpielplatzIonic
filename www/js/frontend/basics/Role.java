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
 * Generated class for Role 
 */
public class Role extends AbstractClientDataModel
{
    /**
     * Default constructor. Needed for internal processing.
     */
    public Role ( )
    {
        super( );
    }
    


    /**
     * Returns the simple name of this class 
     */
    public String getSimpleName( )
    {
        return "Role";
    }

    /**
     * Returns the name of the module where this class belongs to
     */
    public String getModuleName( )
    {
        return "Basics";
    }

     
    /**
     * Returns a list of objects of this class filtered by the given query from server
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<Role> getRoles( String query ) throws ApiomatRequestException
    {
        return getRoles( query, Datastore.getOfflineUsageForClass(Role.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from the server.
     * Important: The boolean parameter indicates the use of offline storage, not if referenced HREFs should be included as in older SDK versions.
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in the persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<Role> getRoles( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        Role o = new Role();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), Role.class, query, false, false, usePersistentStorage );
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<List<Role>> getRolesAsync(final String query, final AOMCallback<List<Role>> listAOMCallback)
    {
        return getRolesAsync( query, listAOMCallback, Datastore.getOfflineUsageForClass(Role.class) );
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
    public static AOMTask<List<Role>> getRolesAsync(final String query, final AOMCallback<List<Role>> listAOMCallback, final boolean usePersistentStorage)
    {
         Role o = new  Role();
         return Datastore.getInstance().loadFromServerAsync(o.getModuleName(), o.getSimpleName(), Role.class, query, false, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<Role> getRolesWithReferencedHrefs( String query ) throws ApiomatRequestException
    {
        return getRolesWithReferencedHrefs( query, Datastore.getOfflineUsageForClass(Role.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<Role> getRolesWithReferencedHrefs( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        Role o = new Role();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), Role.class, query, true, false, usePersistentStorage);
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background
     */
    public static AOMTask<List<Role>> getRolesWithReferencedHrefsAsync(final String query, final AOMCallback<List<Role>> listAOMCallback)
    {
         return getRolesWithReferencedHrefsAsync( query, Datastore.getOfflineUsageForClass(Role.class), listAOMCallback); 
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
    public static AOMTask<List<Role>> getRolesWithReferencedHrefsAsync(final String query, final boolean usePersistentStorage, final AOMCallback<List<Role>> listAOMCallback)
    {
         Role o = new  Role();
         return Datastore.getInstance().loadFromServerAsync( o.getModuleName(), o.getSimpleName(), Role.class, query, true, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a count of objects of this class filtered by the given query from server
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final long getRolesCount( String query ) throws ApiomatRequestException
    {
        final Role o = new Role();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServer( countHref, Role.class, query, false, false );
    }
    
    /**
     * Get a count of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<Long> getRolesCountAsync(final String query, final AOMCallback<Long> listAOMCallback)
    {
        final Role o = new Role();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServerAsync( countHref, Role.class, query, false, listAOMCallback);
    }


        public List getMembers()
    {
         List retList = new ArrayList();
        if(this.data.has( "members" ))
        {
            final JSONArray array = this.data.optJSONArray( "members" );
            try
            {
                retList = JsonHelper.fromJSONArray(array);
            }
            catch ( JSONException e )
            {
                throw new RuntimeException( e );
            }
        }
        return retList;
    }

    public void setMembers(List members )
    {
        try
        {
            this.data.put( "members",new JSONArray( members ));
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getName()
    {
         return this.data.optString( "name" );
    }

    public void setName(String name )
    {
        try
        {
            this.data.put( "name",name);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }



 
    /**
     * Deletes the classes that have been fetched with getRoles() (or its async version) before
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
     * Deletes the classes that have been fetched with getRolesWithReferencedHref (or its async version) before
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
        final Role o = new Role();
        String collectionHref = Datastore.getInstance().createModelHrefWithParams( o.getModuleName( ),
            o.getSimpleName( ), withReferencedHrefs, query );
        return Datastore.getInstance().deleteCollectionFromStorage( collectionHref );
    }
}
