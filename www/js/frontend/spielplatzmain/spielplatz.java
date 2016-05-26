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
package com.apiomat.frontend.spielplatzmain;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import android.text.TextUtils;
import android.util.Log;
import com.apiomat.frontend.*;
import com.apiomat.frontend.basics.*;
import com.apiomat.frontend.callbacks.*;
import com.apiomat.frontend.helper.*;

import org.json.*;


/**
 * Generated class for spielplatz 
 */
public class spielplatz extends AbstractClientDataModel
{
    private List<ausstattung> ausstattungsliste = new ArrayList<ausstattung>();
    private List<bewertungen> bewertungsliste = new ArrayList<bewertungen>();
    /**
     * Default constructor. Needed for internal processing.
     */
    public spielplatz ( )
    {
        super( );
    }
    


    /**
     * Returns the simple name of this class 
     */
    public String getSimpleName( )
    {
        return "spielplatz";
    }

    /**
     * Returns the name of the module where this class belongs to
     */
    public String getModuleName( )
    {
        return "SpielplatzMain";
    }

     
    /**
     * Returns a list of objects of this class filtered by the given query from server
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<spielplatz> getspielplatzs( String query ) throws ApiomatRequestException
    {
        return getspielplatzs( query, Datastore.getOfflineUsageForClass(spielplatz.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from the server.
     * Important: The boolean parameter indicates the use of offline storage, not if referenced HREFs should be included as in older SDK versions.
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in the persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<spielplatz> getspielplatzs( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        spielplatz o = new spielplatz();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), spielplatz.class, query, false, false, usePersistentStorage );
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<List<spielplatz>> getspielplatzsAsync(final String query, final AOMCallback<List<spielplatz>> listAOMCallback)
    {
        return getspielplatzsAsync( query, listAOMCallback, Datastore.getOfflineUsageForClass(spielplatz.class) );
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
    public static AOMTask<List<spielplatz>> getspielplatzsAsync(final String query, final AOMCallback<List<spielplatz>> listAOMCallback, final boolean usePersistentStorage)
    {
         spielplatz o = new  spielplatz();
         return Datastore.getInstance().loadFromServerAsync(o.getModuleName(), o.getSimpleName(), spielplatz.class, query, false, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<spielplatz> getspielplatzsWithReferencedHrefs( String query ) throws ApiomatRequestException
    {
        return getspielplatzsWithReferencedHrefs( query, Datastore.getOfflineUsageForClass(spielplatz.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<spielplatz> getspielplatzsWithReferencedHrefs( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        spielplatz o = new spielplatz();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), spielplatz.class, query, true, false, usePersistentStorage);
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background
     */
    public static AOMTask<List<spielplatz>> getspielplatzsWithReferencedHrefsAsync(final String query, final AOMCallback<List<spielplatz>> listAOMCallback)
    {
         return getspielplatzsWithReferencedHrefsAsync( query, Datastore.getOfflineUsageForClass(spielplatz.class), listAOMCallback); 
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
    public static AOMTask<List<spielplatz>> getspielplatzsWithReferencedHrefsAsync(final String query, final boolean usePersistentStorage, final AOMCallback<List<spielplatz>> listAOMCallback)
    {
         spielplatz o = new  spielplatz();
         return Datastore.getInstance().loadFromServerAsync( o.getModuleName(), o.getSimpleName(), spielplatz.class, query, true, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a count of objects of this class filtered by the given query from server
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final long getspielplatzsCount( String query ) throws ApiomatRequestException
    {
        final spielplatz o = new spielplatz();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServer( countHref, spielplatz.class, query, false, false );
    }
    
    /**
     * Get a count of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<Long> getspielplatzsCountAsync(final String query, final AOMCallback<Long> listAOMCallback)
    {
        final spielplatz o = new spielplatz();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServerAsync( countHref, spielplatz.class, query, false, listAOMCallback);
    }


        public Long getAltersgruppe()
    {
         return this.data.optLong( "altersgruppe" );
    }

    public void setAltersgruppe(Long altersgruppe )
    {
        try
        {
            this.data.put( "altersgruppe",altersgruppe);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

    
    public List<ausstattung> loadAusstattungsliste( final String query ) throws ApiomatRequestException
    {
        return loadAusstattungsliste( ausstattung.class, query );
    }
    
    public <T extends ausstattung> List<T> loadAusstattungsliste( final Class<T> referenceClass, final String query ) throws ApiomatRequestException
    {
        return loadAusstattungsliste( referenceClass, query, Datastore.getOfflineUsageForClass(referenceClass) );
    }
    
    public List<ausstattung> loadAusstattungsliste( final String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        return loadAusstattungsliste( ausstattung.class, query, Datastore.getOfflineUsageForClass(ausstattung.class) );
    }
    
    public <T extends ausstattung> List<T> loadAusstattungsliste( final Class<T> referenceClass, final String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String refUrl = this.data.optString( "ausstattungslisteHref" );
        if( TextUtils.isEmpty(refUrl) )
        {
            throw new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200);
        }
        List<T> result = Datastore.getInstance( ).loadFromServer( refUrl, referenceClass, query, false, true, false, usePersistentStorage );
        ausstattungsliste = (List<ausstattung>) result;
        return result;
    }
    
    /**
     * Getter for local linked variable
     */
    public List<ausstattung> getAusstattungsliste() 
    {
        return ausstattungsliste;
    }
    
    /**
     * Returns a count of referenced objects of this class filtered by the given query from server
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public final long getAusstattungslisteCount( String query ) throws ApiomatRequestException
    {
        final String countHref = this.getHref() + "/ausstattungsliste/count";
        return Datastore.getInstance( ).countFromServer( countHref, ausstattung.class, query, false, false );
    }
    
    /**
     * Returns a count of referenced objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<Long> getAusstattungslisteCountAsync(final String query, final AOMCallback<Long> listAOMCallback)
    {
         final String countHref = this.getHref() + "/ausstattungsliste/count";
         return Datastore.getInstance( ).countFromServerAsync( countHref, ausstattung.class, query, false, listAOMCallback);
    }

    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     *
     * @deprecated Please use {@link this.loadAusstattungslisteAsync(String, AOMCallback)} instead
     *
     * @param query filter returned references by query
     * @param callback callback method which will called after request is finished
     *
     */
    @Deprecated
    public void loadAusstattungslisteAsync(final String query, final AOMEmptyCallback callback )
    {
        AOMCallback<List<ausstattung>> cb = new AOMCallback<List<ausstattung>>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(List<ausstattung> result, ApiomatRequestException ex) {}
            @Override
            public void isDone(List<ausstattung> result, boolean wasLoadedFromStorage, ApiomatRequestException ex)
            {
                if (ex == null) {
                    ausstattungsliste.clear();
                    ausstattungsliste.addAll(result);
                }
                if(callback != null) 
                {
                    callback.isDone(wasLoadedFromStorage, ex);
                }
                else
                {
                    if(ex != null)
                    {
                        Log.e("spielplatz", "Error occurred: " + ex.getMessage(), ex);
                    }
                }
            }
        };
        loadAusstattungslisteAsync(query, cb);
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public AOMTask<List<ausstattung>> loadAusstattungslisteAsync(final String query, final AOMCallback<List<ausstattung>> callback)
    {
        return loadAusstattungslisteAsync(ausstattung.class, query, callback);
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param referenceClass The class of the referenced object - can be a subclass of List<ausstattung>
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public <T extends ausstattung> AOMTask<List<T>> loadAusstattungslisteAsync( final Class<T> referenceClass, final String query, final AOMCallback<List<T>> callback)
    {
        return loadAusstattungslisteAsync( referenceClass, query, callback, Datastore.getOfflineUsageForClass(referenceClass));
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public AOMTask<List<ausstattung>> loadAusstattungslisteAsync( final String query, final AOMCallback<List<ausstattung>> callback, final boolean usePersistentStorage)
    {
        return loadAusstattungslisteAsync( ausstattung.class, query, callback, usePersistentStorage);
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param referenceClass The class of the referenced object - can be a subclass of List<ausstattung>
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public <T extends ausstattung> AOMTask<List<T>> loadAusstattungslisteAsync( final Class<T> referenceClass, final String query, final AOMCallback<List<T>> callback, final boolean usePersistentStorage )
    {
        final String refUrl = this.data.optString("ausstattungslisteHref");
        if ( TextUtils.isEmpty(refUrl) )
        {
            if(callback != null) 
            {
                callback.isDone(null, false, new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200));
            }
            else
            {
                Log.e("spielplatz", "Error occurred: " + Status.ATTACHED_HREF_MISSING.getReasonPhrase());
            }
            return null;
        }
        
        AOMCallback<List<T>> cb = new AOMCallback<List<T>>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(List<T> result, ApiomatRequestException ex) {}
            @Override
            public void isDone(List<T> result, boolean wasLoadedFromStorage, ApiomatRequestException ex)
            {
                if (ex == null) {
                    ausstattungsliste.clear();
                    ausstattungsliste.addAll(result);
                }
                if(callback != null) 
                {
                    callback.isDone(result, wasLoadedFromStorage, ex);
                }
                else
                {
                    if(ex != null)
                    {
                        Log.e("spielplatz", "Error occurred: " + ex.getMessage(), ex);
                    }
                }
            }
        };
        return Datastore.getInstance().loadFromServerAsync( refUrl, referenceClass, query, false, true, usePersistentStorage, cb);
    }

    public String postAusstattungsliste( ausstattung refData ) throws ApiomatRequestException
    {
        return postAusstattungsliste( refData, Datastore.getOfflineUsageForClass( ausstattung.class ) );
    }

    public String postAusstattungsliste( ausstattung refData, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        String href = refData.getHref();
        if( TextUtils.isEmpty(href) ) 
        {
            throw new ApiomatRequestException(Status.SAVE_REFERENECE_BEFORE_REFERENCING, 201);
        }
        
        String refHref = null;
        /* Let's check if we use offline storage or send req to server */
        if(Datastore.getInstance().sendOffline("POST"))
        {
            refHref = Datastore.getInstance().getOfflineHandler().addTask("POST", getHref(), refData, "ausstattungsliste", true, usePersistentStorage );
        } 
        else
        {
            refHref = Datastore.getInstance( ).postOnServer(this.data.optString("ausstattungslisteHref"), refData, true, usePersistentStorage);
        }
        
        if( TextUtils.isEmpty(refHref) == false)
        {
            //check if local list contains refData with same href
            if(ModelHelper.containsHref(ausstattungsliste, refHref)==false)
            {
                ausstattungsliste.add(refData);
            }
        }
        return href;
    }
    
    /**
     * Adds a given reference to this object
     *
     * @deprecated Please use {@link this.postAusstattungslisteAsync(ausstattung, AOMEmptyCallback )} instead
     */
    @Deprecated
    public void postAusstattungslisteAsync(final ausstattung refData, final AOMEmptyCallback callback )
    {
        AOMCallback<String> cb = new AOMCallback<String>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(String refHref, ApiomatRequestException ex) {}
            @Override
            public void isDone(String refHref, boolean wasLoadedFromStorage, ApiomatRequestException ex)
            {
                if(ex == null && TextUtils.isEmpty(refHref) == false)
                {
                    //check if local list contains refData with same href
                    if(ModelHelper.containsHref(ausstattungsliste, refHref)==false)
                    {
                        ausstattungsliste.add(refData);
                    }
                }
                if(callback != null)
                {
                    callback.isDone(wasLoadedFromStorage, ex);
                }
                else if (ex != null)
                {
                    Log.e("spielplatz", "Exception was thrown: " + ex.getMessage(), ex);
                }
                else
                {
                    Log.e("spielplatz", "Exception was thrown during posting the reference to the object.");
                }
            }
        };
        postAusstattungslisteAsync(refData, cb);
    }
    
    /**
     * Adds a given reference to this object
     * 
     * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an error.
     */
    public AOMTask<String> postAusstattungslisteAsync(final ausstattung refData, final AOMCallback<String> callback )
    {
        return postAusstattungslisteAsync( refData, callback, Datastore.getOfflineUsageForClass( this.getClass() ) );
    }

    /**
     * Adds a given reference to this object
     * 
     * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an error.
     */
    public AOMTask<String> postAusstattungslisteAsync(final ausstattung refData, final AOMCallback<String> callback, final boolean usePersistentStorage )
    {
        String href = refData.getHref();
        if( TextUtils.isEmpty(href) )
        {
            if(callback != null)
            {
                callback.isDone(null, false, new ApiomatRequestException(Status.SAVE_REFERENECE_BEFORE_REFERENCING, 201));
            }
            else
            {
                Log.e("spielplatz", "Error occurred: " + Status.SAVE_REFERENECE_BEFORE_REFERENCING.getReasonPhrase());
            }
            return null;
        }
         /* check if we have to use offline storage */
        if(Datastore.getInstance().sendOffline("POST"))
        {
            final String refHref = Datastore.getInstance().getOfflineHandler().addTask("POST", getHref(), refData, "ausstattungsliste", true, usePersistentStorage );
            /* check if local list contains refData with same href */
            if(ModelHelper.containsHref(ausstattungsliste, refHref)==false)
            {
                ausstattungsliste.add(refData);
            }
            if(callback != null)
            {
                callback.isDone(refHref, false, null);
            }
            return null;
        }
        else
        {
            AOMCallback<String> cb = new AOMCallback<String>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
                @Override
                public void isDone(String refHref, ApiomatRequestException ex) {}
                @Override
                public void isDone(String refHref, boolean wasLoadedFromStorage, ApiomatRequestException ex)
                {
                    if(ex == null && TextUtils.isEmpty(refHref) == false)
                    {
                        //check if local list contains refData with same href
                        if(ModelHelper.containsHref(ausstattungsliste, refHref)==false)
                        {
                            ausstattungsliste.add(refData);
                        }
                    }
                    if(callback != null)
                    {
                        callback.isDone(refHref, wasLoadedFromStorage, ex);
                    }
                    else if (ex != null)
                    {
                        Log.e("spielplatz", "Exception was thrown: " + ex.getMessage(), ex);
                    }
                    else
                    {
                        Log.e("spielplatz", "Exception was thrown during posting the object.");
                    }
                }
            };
            return Datastore.getInstance( ).postOnServerAsync( this.data.optString("ausstattungslisteHref"), refData, true, usePersistentStorage, cb );
        }
    }

    public void removeAusstattungsliste( ausstattung refData ) throws ApiomatRequestException
    {
        removeAusstattungsliste( refData, Datastore.getOfflineUsageForClass(ausstattung.class) );
    }

    public void removeAusstattungsliste( ausstattung refData, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String id = refData.getHref( ).substring( refData.getHref( ).lastIndexOf( "/" ) + 1 );
        if(Datastore.getInstance().sendOffline("DELETE"))
        {
            Datastore.getInstance().getOfflineHandler().addTask("DELETE", getHref(), refData, "ausstattungsliste", true, usePersistentStorage );
        }
        else
        {
            Datastore.getInstance( ).deleteOnServer( this.data.optString("ausstattungslisteHref") + "/" + id, true, usePersistentStorage );
        }
            ausstattungsliste.remove(refData);
    }
    
    public AOMTask<Void> removeAusstattungslisteAsync( final ausstattung refData, final AOMEmptyCallback callback )
    {
        return removeAusstattungslisteAsync( refData, callback, Datastore.getOfflineUsageForClass(ausstattung.class) );
    }
    
    public AOMTask<Void> removeAusstattungslisteAsync( final ausstattung refData, final AOMEmptyCallback callback, final boolean usePersistentStorage )
    {
        final String id = refData.getHref( ).substring( refData.getHref( ).lastIndexOf( "/" ) + 1 );
        if(Datastore.getInstance().sendOffline("DELETE"))
        {
            Datastore.getInstance().getOfflineHandler().addTask("DELETE", getHref(), refData, "ausstattungsliste", true, usePersistentStorage);
            ausstattungsliste.remove(refData);
            if(callback != null)
            {
                callback.isDone(false, null);
            }
            return null;
        }
        else
        {
            AOMEmptyCallback cb = new AOMEmptyCallback()
            {
                /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
                @Override
                public void isDone(ApiomatRequestException ex) {}
                @Override
                public void isDone(boolean wasLoadedFromStorage, ApiomatRequestException ex)
                {
                    if(ex == null) {
                        ausstattungsliste.remove(refData);
                    }
                    if (callback != null)
                    {
                    	callback.isDone(wasLoadedFromStorage, ex);
                    }
                }
            };
            return Datastore.getInstance( ).deleteOnServerAsync( this.data.optString("ausstattungslisteHref") + "/" + id, true, usePersistentStorage, cb );
        }
    }

    public int deleteAusstattungslisteFromStorage( String query )
    {
        final String refUrl = this.data.optString( "ausstattungslisteHref" );
        StringBuilder sb = new StringBuilder( refUrl );
        sb.append( "?withReferencedHrefs=false&q=" );
        if (query != null)
        {
            sb.append( query );
        }
        String fullUrl = sb.toString();
        return Datastore.getInstance().deleteCollectionFromStorage( fullUrl );
    }
 

    
    public List<bewertungen> loadBewertungsliste( final String query ) throws ApiomatRequestException
    {
        return loadBewertungsliste( bewertungen.class, query );
    }
    
    public <T extends bewertungen> List<T> loadBewertungsliste( final Class<T> referenceClass, final String query ) throws ApiomatRequestException
    {
        return loadBewertungsliste( referenceClass, query, Datastore.getOfflineUsageForClass(referenceClass) );
    }
    
    public List<bewertungen> loadBewertungsliste( final String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        return loadBewertungsliste( bewertungen.class, query, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    public <T extends bewertungen> List<T> loadBewertungsliste( final Class<T> referenceClass, final String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String refUrl = this.data.optString( "bewertungslisteHref" );
        if( TextUtils.isEmpty(refUrl) )
        {
            throw new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200);
        }
        List<T> result = Datastore.getInstance( ).loadFromServer( refUrl, referenceClass, query, false, true, false, usePersistentStorage );
        bewertungsliste = (List<bewertungen>) result;
        return result;
    }
    
    /**
     * Getter for local linked variable
     */
    public List<bewertungen> getBewertungsliste() 
    {
        return bewertungsliste;
    }
    
    /**
     * Returns a count of referenced objects of this class filtered by the given query from server
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public final long getBewertungslisteCount( String query ) throws ApiomatRequestException
    {
        final String countHref = this.getHref() + "/bewertungsliste/count";
        return Datastore.getInstance( ).countFromServer( countHref, bewertungen.class, query, false, false );
    }
    
    /**
     * Returns a count of referenced objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<Long> getBewertungslisteCountAsync(final String query, final AOMCallback<Long> listAOMCallback)
    {
         final String countHref = this.getHref() + "/bewertungsliste/count";
         return Datastore.getInstance( ).countFromServerAsync( countHref, bewertungen.class, query, false, listAOMCallback);
    }

    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     *
     * @deprecated Please use {@link this.loadBewertungslisteAsync(String, AOMCallback)} instead
     *
     * @param query filter returned references by query
     * @param callback callback method which will called after request is finished
     *
     */
    @Deprecated
    public void loadBewertungslisteAsync(final String query, final AOMEmptyCallback callback )
    {
        AOMCallback<List<bewertungen>> cb = new AOMCallback<List<bewertungen>>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(List<bewertungen> result, ApiomatRequestException ex) {}
            @Override
            public void isDone(List<bewertungen> result, boolean wasLoadedFromStorage, ApiomatRequestException ex)
            {
                if (ex == null) {
                    bewertungsliste.clear();
                    bewertungsliste.addAll(result);
                }
                if(callback != null) 
                {
                    callback.isDone(wasLoadedFromStorage, ex);
                }
                else
                {
                    if(ex != null)
                    {
                        Log.e("spielplatz", "Error occurred: " + ex.getMessage(), ex);
                    }
                }
            }
        };
        loadBewertungslisteAsync(query, cb);
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public AOMTask<List<bewertungen>> loadBewertungslisteAsync(final String query, final AOMCallback<List<bewertungen>> callback)
    {
        return loadBewertungslisteAsync(bewertungen.class, query, callback);
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param referenceClass The class of the referenced object - can be a subclass of List<bewertungen>
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public <T extends bewertungen> AOMTask<List<T>> loadBewertungslisteAsync( final Class<T> referenceClass, final String query, final AOMCallback<List<T>> callback)
    {
        return loadBewertungslisteAsync( referenceClass, query, callback, Datastore.getOfflineUsageForClass(referenceClass));
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public AOMTask<List<bewertungen>> loadBewertungslisteAsync( final String query, final AOMCallback<List<bewertungen>> callback, final boolean usePersistentStorage)
    {
        return loadBewertungslisteAsync( bewertungen.class, query, callback, usePersistentStorage);
    }
    
    /** 
     * Load referenced object(s) on a background thread and
     * add result from server to member variable of this class.
     * 
     * @param referenceClass The class of the referenced object - can be a subclass of List<bewertungen>
     * @param query filter returned references by query
          * @param callback callback method which will called after request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background. Can be null if the task wasn't started due to an error.
     */
    public <T extends bewertungen> AOMTask<List<T>> loadBewertungslisteAsync( final Class<T> referenceClass, final String query, final AOMCallback<List<T>> callback, final boolean usePersistentStorage )
    {
        final String refUrl = this.data.optString("bewertungslisteHref");
        if ( TextUtils.isEmpty(refUrl) )
        {
            if(callback != null) 
            {
                callback.isDone(null, false, new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200));
            }
            else
            {
                Log.e("spielplatz", "Error occurred: " + Status.ATTACHED_HREF_MISSING.getReasonPhrase());
            }
            return null;
        }
        
        AOMCallback<List<T>> cb = new AOMCallback<List<T>>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(List<T> result, ApiomatRequestException ex) {}
            @Override
            public void isDone(List<T> result, boolean wasLoadedFromStorage, ApiomatRequestException ex)
            {
                if (ex == null) {
                    bewertungsliste.clear();
                    bewertungsliste.addAll(result);
                }
                if(callback != null) 
                {
                    callback.isDone(result, wasLoadedFromStorage, ex);
                }
                else
                {
                    if(ex != null)
                    {
                        Log.e("spielplatz", "Error occurred: " + ex.getMessage(), ex);
                    }
                }
            }
        };
        return Datastore.getInstance().loadFromServerAsync( refUrl, referenceClass, query, false, true, usePersistentStorage, cb);
    }

    public String postBewertungsliste( bewertungen refData ) throws ApiomatRequestException
    {
        return postBewertungsliste( refData, Datastore.getOfflineUsageForClass( bewertungen.class ) );
    }

    public String postBewertungsliste( bewertungen refData, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        String href = refData.getHref();
        if( TextUtils.isEmpty(href) ) 
        {
            throw new ApiomatRequestException(Status.SAVE_REFERENECE_BEFORE_REFERENCING, 201);
        }
        
        String refHref = null;
        /* Let's check if we use offline storage or send req to server */
        if(Datastore.getInstance().sendOffline("POST"))
        {
            refHref = Datastore.getInstance().getOfflineHandler().addTask("POST", getHref(), refData, "bewertungsliste", true, usePersistentStorage );
        } 
        else
        {
            refHref = Datastore.getInstance( ).postOnServer(this.data.optString("bewertungslisteHref"), refData, true, usePersistentStorage);
        }
        
        if( TextUtils.isEmpty(refHref) == false)
        {
            //check if local list contains refData with same href
            if(ModelHelper.containsHref(bewertungsliste, refHref)==false)
            {
                bewertungsliste.add(refData);
            }
        }
        return href;
    }
    
    /**
     * Adds a given reference to this object
     *
     * @deprecated Please use {@link this.postBewertungslisteAsync(bewertungen, AOMEmptyCallback )} instead
     */
    @Deprecated
    public void postBewertungslisteAsync(final bewertungen refData, final AOMEmptyCallback callback )
    {
        AOMCallback<String> cb = new AOMCallback<String>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(String refHref, ApiomatRequestException ex) {}
            @Override
            public void isDone(String refHref, boolean wasLoadedFromStorage, ApiomatRequestException ex)
            {
                if(ex == null && TextUtils.isEmpty(refHref) == false)
                {
                    //check if local list contains refData with same href
                    if(ModelHelper.containsHref(bewertungsliste, refHref)==false)
                    {
                        bewertungsliste.add(refData);
                    }
                }
                if(callback != null)
                {
                    callback.isDone(wasLoadedFromStorage, ex);
                }
                else if (ex != null)
                {
                    Log.e("spielplatz", "Exception was thrown: " + ex.getMessage(), ex);
                }
                else
                {
                    Log.e("spielplatz", "Exception was thrown during posting the reference to the object.");
                }
            }
        };
        postBewertungslisteAsync(refData, cb);
    }
    
    /**
     * Adds a given reference to this object
     * 
     * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an error.
     */
    public AOMTask<String> postBewertungslisteAsync(final bewertungen refData, final AOMCallback<String> callback )
    {
        return postBewertungslisteAsync( refData, callback, Datastore.getOfflineUsageForClass( this.getClass() ) );
    }

    /**
     * Adds a given reference to this object
     * 
     * @return A reference to the task that runs in the background. Can be null if if the task wasn't started due to an error.
     */
    public AOMTask<String> postBewertungslisteAsync(final bewertungen refData, final AOMCallback<String> callback, final boolean usePersistentStorage )
    {
        String href = refData.getHref();
        if( TextUtils.isEmpty(href) )
        {
            if(callback != null)
            {
                callback.isDone(null, false, new ApiomatRequestException(Status.SAVE_REFERENECE_BEFORE_REFERENCING, 201));
            }
            else
            {
                Log.e("spielplatz", "Error occurred: " + Status.SAVE_REFERENECE_BEFORE_REFERENCING.getReasonPhrase());
            }
            return null;
        }
         /* check if we have to use offline storage */
        if(Datastore.getInstance().sendOffline("POST"))
        {
            final String refHref = Datastore.getInstance().getOfflineHandler().addTask("POST", getHref(), refData, "bewertungsliste", true, usePersistentStorage );
            /* check if local list contains refData with same href */
            if(ModelHelper.containsHref(bewertungsliste, refHref)==false)
            {
                bewertungsliste.add(refData);
            }
            if(callback != null)
            {
                callback.isDone(refHref, false, null);
            }
            return null;
        }
        else
        {
            AOMCallback<String> cb = new AOMCallback<String>() {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
                @Override
                public void isDone(String refHref, ApiomatRequestException ex) {}
                @Override
                public void isDone(String refHref, boolean wasLoadedFromStorage, ApiomatRequestException ex)
                {
                    if(ex == null && TextUtils.isEmpty(refHref) == false)
                    {
                        //check if local list contains refData with same href
                        if(ModelHelper.containsHref(bewertungsliste, refHref)==false)
                        {
                            bewertungsliste.add(refData);
                        }
                    }
                    if(callback != null)
                    {
                        callback.isDone(refHref, wasLoadedFromStorage, ex);
                    }
                    else if (ex != null)
                    {
                        Log.e("spielplatz", "Exception was thrown: " + ex.getMessage(), ex);
                    }
                    else
                    {
                        Log.e("spielplatz", "Exception was thrown during posting the object.");
                    }
                }
            };
            return Datastore.getInstance( ).postOnServerAsync( this.data.optString("bewertungslisteHref"), refData, true, usePersistentStorage, cb );
        }
    }

    public void removeBewertungsliste( bewertungen refData ) throws ApiomatRequestException
    {
        removeBewertungsliste( refData, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }

    public void removeBewertungsliste( bewertungen refData, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String id = refData.getHref( ).substring( refData.getHref( ).lastIndexOf( "/" ) + 1 );
        if(Datastore.getInstance().sendOffline("DELETE"))
        {
            Datastore.getInstance().getOfflineHandler().addTask("DELETE", getHref(), refData, "bewertungsliste", true, usePersistentStorage );
        }
        else
        {
            Datastore.getInstance( ).deleteOnServer( this.data.optString("bewertungslisteHref") + "/" + id, true, usePersistentStorage );
        }
            bewertungsliste.remove(refData);
    }
    
    public AOMTask<Void> removeBewertungslisteAsync( final bewertungen refData, final AOMEmptyCallback callback )
    {
        return removeBewertungslisteAsync( refData, callback, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    public AOMTask<Void> removeBewertungslisteAsync( final bewertungen refData, final AOMEmptyCallback callback, final boolean usePersistentStorage )
    {
        final String id = refData.getHref( ).substring( refData.getHref( ).lastIndexOf( "/" ) + 1 );
        if(Datastore.getInstance().sendOffline("DELETE"))
        {
            Datastore.getInstance().getOfflineHandler().addTask("DELETE", getHref(), refData, "bewertungsliste", true, usePersistentStorage);
            bewertungsliste.remove(refData);
            if(callback != null)
            {
                callback.isDone(false, null);
            }
            return null;
        }
        else
        {
            AOMEmptyCallback cb = new AOMEmptyCallback()
            {
                /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
                @Override
                public void isDone(ApiomatRequestException ex) {}
                @Override
                public void isDone(boolean wasLoadedFromStorage, ApiomatRequestException ex)
                {
                    if(ex == null) {
                        bewertungsliste.remove(refData);
                    }
                    if (callback != null)
                    {
                    	callback.isDone(wasLoadedFromStorage, ex);
                    }
                }
            };
            return Datastore.getInstance( ).deleteOnServerAsync( this.data.optString("bewertungslisteHref") + "/" + id, true, usePersistentStorage, cb );
        }
    }

    public int deleteBewertungslisteFromStorage( String query )
    {
        final String refUrl = this.data.optString( "bewertungslisteHref" );
        StringBuilder sb = new StringBuilder( refUrl );
        sb.append( "?withReferencedHrefs=false&q=" );
        if (query != null)
        {
            sb.append( query );
        }
        String fullUrl = sb.toString();
        return Datastore.getInstance().deleteCollectionFromStorage( fullUrl );
    }
 

        public Long getGröße()
    {
         return this.data.optLong( "größe" );
    }

    public void setGröße(Long größe )
    {
        try
        {
            this.data.put( "größe",größe);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

    
    /**
     * Returns the URL of the resource, including API key and system as parameters
     * 
     * @return the URL of the resource, including API key and system as parameters
     */
    public String getHauptbildURL()
    {
        return getHauptbildURL( true );
    }

    /**
     * Returns the plain URL of the resource, without any parameters.
     * To access the resource, you can either append your API key and system as parameters or as request headers
     *
     * @return the plain URL of the resource, without any parameters
     */
    public String getHauptbildResourceURL()
    {
        return getHauptbildURL( false );
    }

    /**
     * Returns the URL of the resource, either with API key and system parameters or without.
     * You can also use the methods getHauptbildURL() and getHauptbildResourceURL().
     *
     * @param withApikeyAndSystem
     *
     * @return the URL of the resource, either with API key and system parameters or without
     */
    public String getHauptbildURL(final boolean withApikeyAndSystem)
    {
        String result;
        if(this.data.isNull( "hauptbildURL" ))
        {
            result = null;
        }
        else
        {
            result = this.data.optString( "hauptbildURL" ) + ".img";
            if (withApikeyAndSystem)
            {
                result += getApiKeyAndSystemAsUrlParams(true);
            }
        }
        return result;
    }
    
    /**
     * Request property hauptbild on server and returns data.
     *
     * @return byte[] The resource data
     * @throws ApiomatRequestException
     */
    public byte[] loadHauptbild() throws ApiomatRequestException
    {
        return loadHauptbild( Datastore.getOfflineUsageForClass(spielplatz.class) );
    }
    
    /**
     * Request property hauptbild on server and returns data.
     *
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return byte[] The resource data
     * @throws ApiomatRequestException
     */
    public byte[] loadHauptbild(final boolean usePersistentStorage) throws ApiomatRequestException
    {
        String resUrl = this.getHauptbildResourceURL();
        if ( TextUtils.isEmpty(resUrl) )
        {
            throw new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200);
        }
        return Datastore.getInstance().loadResource( resUrl, true, false, usePersistentStorage );
    }
    
    /**
     * Request property hauptbild on server and returns data.
     * Data will be loaded on a seperate thread and not on the UI thread.
     *
     * @param callback Callback object which will called after request is finished
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<byte[]> loadHauptbildAsync( final AOMCallback<byte[ ]> callback )
    {
        return loadHauptbildAsync( callback, Datastore.getOfflineUsageForClass(spielplatz.class) );
    }
    
    /**
     * Request property hauptbild on server and returns data.
     * Data will be loaded on a seperate thread and not on the UI thread.
     *
     * @param callback Callback object which will called after request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<byte[]> loadHauptbildAsync( final AOMCallback<byte[ ]> callback, final boolean usePersistentStorage )
    {
        String resUrl = this.getHauptbildResourceURL();
        if ( TextUtils.isEmpty(resUrl) )
        {
            if (callback != null)
            {
                callback.isDone(null, false, new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200));
            }
            return null;
        }
        return Datastore.getInstance().loadResourceAsync(resUrl, true, usePersistentStorage, callback);
    }

    public String postHauptbild( byte[] data ) throws ApiomatRequestException
    {
        return postHauptbild( data, Datastore.getOfflineUsageForClass(spielplatz.class) );
    }

    public String postHauptbild( byte[] data, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        String href = null;
        if(Datastore.getInstance().sendOffline("POST"))
        {
            final String sendHREF = Datastore.getInstance().createStaticDataHref( true );
            href = Datastore.getInstance().getOfflineHandler().addTask( "POST", sendHREF, data,  true , true, usePersistentStorage );
        }
        else
        {
            href = Datastore.getInstance( ).postStaticDataOnServer( data,  true , true, usePersistentStorage );
        }
        
        if( TextUtils.isEmpty(href) == false )
        {
            try
            {
                this.data.put( "hauptbildURL", href );
            }
            catch ( JSONException e )
            {
                throw new RuntimeException( e );
            }
            
            try
            {
                this.save(true, usePersistentStorage); // loadAfterwards default is true
            }
            catch ( ApiomatRequestException e )
            {
                Datastore.getInstance( ).deleteOnServer( href, true, usePersistentStorage );
                this.data.remove( "hauptbildURL" );
                throw e;
            }
        }
        return href;
    }

    public AOMTask<String> postHauptbildAsync( final byte[] data, final AOMEmptyCallback _callback )
    {
        return postHauptbildAsync( data, _callback, Datastore.getOfflineUsageForClass( spielplatz.class ) );
    }

    public AOMTask<String> postHauptbildAsync( final byte[] data, final AOMEmptyCallback _callback, final boolean usePersistentStorage )
    {
        AOMCallback<String> cb = new AOMCallback<String>()
        {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(final String href, final ApiomatRequestException ex) {}
            @Override
            public void isDone(final String href, final boolean wasLoadedFromStorage, final ApiomatRequestException ex)
            {
                if(ex == null && TextUtils.isEmpty(href) == false)
                {
                    try
                    {
                        spielplatz.this.data.put( "hauptbildURL", href );
                    }
                    catch ( JSONException e )
                    {
                        throw new RuntimeException( e );
                    }
                    /* save new data reference in object */
                    spielplatz.this.saveAsync( usePersistentStorage, new AOMEmptyCallback()
                    {
                        /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
                        @Override
                        public void isDone(ApiomatRequestException exception) {}
                        @Override
                        public void isDone(boolean wasLoadedFromStorage2, ApiomatRequestException exception)
                        {
                            if(_callback != null)
                            {
                                _callback.isDone(wasLoadedFromStorage2, exception);
                            }
                            else if (exception != null)
                            {
                                Log.e("spielplatz", "Exception was thrown: " + exception.getMessage(), exception);
                                try
                                {
                                    Datastore.getInstance( ).deleteOnServer( href, true, usePersistentStorage );
                                    spielplatz.this.data.remove( "hauptbildURL" );
                                }
                                catch ( ApiomatRequestException e )
                                {
                                    Log.e("spielplatz", "Exception was thrown: " + e.getMessage(), e);
                                }
                            }
                        }
                    });
                }
                else
                {
                    if(_callback != null && ex != null)
                    {
                        _callback.isDone(wasLoadedFromStorage, ex);
                    }
                    else if(_callback != null && ex == null)
                    {
                        _callback.isDone(wasLoadedFromStorage, new ApiomatRequestException(Status.HREF_NOT_FOUND, 201));
                    }
                    else if (ex != null)
                    {
                        Log.e("spielplatz", "Exception was thrown: " + ex.getMessage(), ex);
                    }
                    else
                    {
                        Log.e("spielplatz", "Exception was thrown: " + Status.HREF_NOT_FOUND.toString());
                    }
                }
            }
        };
        
        if(Datastore.getInstance().sendOffline("POST"))
        {
            final String sendHREF = Datastore.getInstance().createStaticDataHref( true );
            String refHref = Datastore.getInstance().getOfflineHandler().addTask( "POST", sendHREF, data, true , true, usePersistentStorage );
            cb.isDone(refHref, false, null);
            return null;
        }
        else
        {
            return Datastore.getInstance( ).postStaticDataOnServerAsync( data,  true , true, usePersistentStorage, cb );
        }
    }
    
    public void deleteHauptbild() throws ApiomatRequestException
    {
        deleteHauptbild( Datastore.getOfflineUsageForClass(spielplatz.class) );
    }

    public void deleteHauptbild( final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String dataURL = this.data.optString( "hauptbildURL" );
        /* First try to remove the attribute and send a PUT, so we know if the caller is allowed to do so */
        try
        {
            this.data.remove( "hauptbildURL" );
            this.save(true, usePersistentStorage); // loadAfterwards default is true
            
            /* Save was successful, so the caller has the appropriate rights. Delete the data from the server. */
            if(Datastore.getInstance().sendOffline("DELETE"))
            {
                Datastore.getInstance().getOfflineHandler().addTask("DELETE", dataURL,  true , true, usePersistentStorage);
            }
            else
            {
                Datastore.getInstance( ).deleteOnServer( dataURL, true, usePersistentStorage );
            }
        }
        catch ( ApiomatRequestException ex)
        {
            /* re-set the data url */
            try
            {
                this.data.put( "hauptbildURL", dataURL );
            }
            catch (JSONException ex2)
            {
                Log.e( "spielplatz", "The data URL couldn't be re-set to its original value.", ex2 );
            }
            throw ex;
        }
    }

    public AOMTask<Void> deleteHauptbildAsync(final AOMEmptyCallback _callback)
    {
        return deleteHauptbildAsync( _callback, Datastore.getOfflineUsageForClass(spielplatz.class) );
    }
    
    public AOMTask<Void> deleteHauptbildAsync( final AOMEmptyCallback _callback, final boolean usePersistentStorage )
    {
        AOMEmptyCallback cb = new AOMEmptyCallback()
        {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(ApiomatRequestException ex) {}
            @Override
            public void isDone(boolean wasLoadedFromStorage, ApiomatRequestException ex)
            {
                if(ex == null )
                {
                    spielplatz.this.data.remove( "hauptbildURL" );
                    /* save deleted image reference in object */
                    spielplatz.this.saveAsync( usePersistentStorage, new AOMEmptyCallback()
                    {
                        /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
                        @Override
                        public void isDone(ApiomatRequestException exception) {}
                        @Override
                        public void isDone(boolean wasLoadedFromStorage2, ApiomatRequestException exception)
                        {
                            if(_callback != null)
                            {
                                _callback.isDone(wasLoadedFromStorage2, exception);
                            }
                            else if (exception != null)
                            {
                                Log.e("spielplatz", "Exception was thrown: " + exception.getMessage(), exception);
                            }
                        }
                    } );
                }
                else if (_callback != null)
                {
                    _callback.isDone(wasLoadedFromStorage, ex);
                }
            }
        };
        
        final String url = getHauptbildResourceURL().replace( ".img", "" );
        spielplatz.this.data.remove( "hauptbildURL" );

        AOMEmptyCallback saveCallback = new AOMEmptyCallback( )
        {
            /* The old isDone will be deprecated soon, but needs to be here as long as it's abstract */
            @Override
            public void isDone(ApiomatRequestException exception) {}
            @Override
            public void isDone(boolean wasLoadedFromStorage, ApiomatRequestException exception)
            {
                if (exception != null)
                {
                    /* re-set data url */
                    try
                    {
                        data.put( "hauptbildURL", url );
                    }
                    catch (JSONException ex2)
                    {
                        Log.e( "spielplatz", "The data URL couldn't be re-set to its original value.", ex2 );
                    }
                    if ( _callback != null )
                    {
                        _callback.isDone( wasLoadedFromStorage, exception );
                    }
                    else
                    {
                        Log.e( "spielplatz", "Exception was thrown: " + exception.getMessage( ), exception );
                    }
                }
                else
                {
                    /* Save was successful, so the caller has the appropriate rights.
                     * Delete the data from the server. */
                    ApiomatRequestException exception2 = null;
                    if(Datastore.getInstance().sendOffline("DELETE"))
                    {
                        Datastore.getInstance().getOfflineHandler().addTask( "DELETE", url,  true , true, usePersistentStorage );
                    }
                    else
                    {
                        try
                        {
                            /* call sync, because this already is in an async task */
                            Datastore.getInstance( ).deleteOnServer( url, true, usePersistentStorage );
                        }
                        catch (ApiomatRequestException ex)
                        {
                            exception2 = ex;
                        }
                    }
                    if (_callback != null)
                    {
                        _callback.isDone( wasLoadedFromStorage, exception2 );
                    }
                }
            }
        };
        return spielplatz.this.saveAsync( false, usePersistentStorage, saveCallback, null );
    }


    /**
     * Returns a URL of the image, including API key and system parameters. <br/>
     * You can provide several parameters to manipulate the image:
     *
     * @param width the width of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param height the height of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param backgroundColorAsHex the background color of the image, null or empty uses the original background color. Caution: Don't send the '#' symbol!
     *        Example: <i>ff0000</i>
     * @param alpha the alpha value of the image (between 0 and 1), null to take the original value.
     * @param format the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
     * @return the URL of the image
     */
    public String getHauptbildURL(int width, int height, String backgroundColorAsHex, 
        Double alpha, String format)
    {
        return getHauptbildURL(width, height, backgroundColorAsHex, alpha, format, true);
    }
        
    /**
     * Returns a URL of the image. <br/>
     * You can provide additional parameters to manipulate the image and to include API key and system
     *
     * @param width the width of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param height the height of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param backgroundColorAsHex the background color of the image, null or empty uses the original background color. Caution: Don't send the '#' symbol!
     *        Example: <i>ff0000</i>
     * @param alpha the alpha value of the image (between 0 and 1), null to take the original value.
     * @param format the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
     * @withApikeyAndSystem indicates whether to append API key and system or not
     *
     * @return the URL of the image
     */
    public String getHauptbildURL(int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, boolean withApikeyAndSystem)
    {
        StringBuffer sb = new StringBuffer( );
        String imageURL = getHauptbildURL(withApikeyAndSystem);
        if (TextUtils.isEmpty(imageURL))
        {
            return null;
        }
        sb.append(imageURL);
        if (withApikeyAndSystem)
        {
            sb.append("&width=");
        }
        else
        {
            sb.append("?width=");
        }
        sb.append(width);
        sb.append("&height=");
        sb.append(height);
        if(backgroundColorAsHex != null) 
        {
            sb.append("&bgcolor=");
            sb.append(backgroundColorAsHex);
        }
        if(alpha != null)
        {
            sb.append("&alpha=");
            sb.append(alpha);
        }
        if(format != null)
        {
            sb.append("&format=");
            sb.append(format);
        }
        return sb.toString();
    }
    
    /**
     * Request image  hauptbild on server and returns data.
     * You can provide several parameters to manipulate the image.
     *
     * @param width the width of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param height the height of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param backgroundColorAsHex the background color of the image, null or empty uses the original background color. Caution: Don't send the '#' symbol!
     *        Example: <i>ff0000</i>
     * @param alpha the alpha value of the image (between 0 and 1), null to take the original value.
     * @param format the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
     * @return byte[] The image data as byte array
     * @throws ApiomatRequestException
     */
    public byte[] loadHauptbild( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format ) throws ApiomatRequestException
    {
        return loadHauptbild( width, height, backgroundColorAsHex, alpha, format, false );
    }
    
    /**
     * Request image  hauptbild on server and returns data.
     * You can provide several parameters to manipulate the image.
     *
     * @param width the width of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param height the height of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param backgroundColorAsHex the background color of the image, null or empty uses the original background color. Caution: Don't send the '#' symbol!
     *        Example: <i>ff0000</i>
     * @param alpha the alpha value of the image (between 0 and 1), null to take the original value.
     * @param format the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return byte[] The image data as byte array
     * @throws ApiomatRequestException
     */
    public byte[] loadHauptbild( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String resUrl = this.getHauptbildURL(width, height, backgroundColorAsHex, alpha, format, false);
        if ( TextUtils.isEmpty(resUrl) )
        {
            throw new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200);
        }
        return Datastore.getInstance().loadResource(resUrl, true, false, usePersistentStorage);
    }
    
    /**
     * Request property hauptbild on server and returns data.
     * Data will be loaded on a seperate thread and not on the UI thread.
     * You can provide several parameters to manipulate the image.
     *
     * @param width the width of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param height the height of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param backgroundColorAsHex the background color of the image, null or empty uses the original background color. Caution: Don't send the '#' symbol!
     *        Example: <i>ff0000</i>
     * @param alpha the alpha value of the image (between 0 and 1), null to take the original value.
     * @param format the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
     * @param callback Callback object which will called after request is finished
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<byte[]> loadHauptbildAsync( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, final AOMCallback<byte[ ]> callback )
    {
        return loadHauptbildAsync( width, height, backgroundColorAsHex, alpha, format, callback, Datastore.getOfflineUsageForClass(spielplatz.class) );
    }
    
    /**
     * Request property hauptbild on server and returns data.
     * Data will be loaded on a seperate thread and not on the UI thread.
     * You can provide several parameters to manipulate the image.
     *
     * @param width the width of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param height the height of the image, 0 to use the original size. If only width or height are provided, 
     *        the other value is computed.
     * @param backgroundColorAsHex the background color of the image, null or empty uses the original background color. Caution: Don't send the '#' symbol!
     *        Example: <i>ff0000</i>
     * @param alpha the alpha value of the image (between 0 and 1), null to take the original value.
     * @param format the file format of the image to return, e.g. <i>jpg</i> or <i>png</i>
     * @param callback Callback object which will called after request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<byte[]> loadHauptbildAsync( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, final AOMCallback<byte[ ]> callback, final boolean usePersistentStorage )
    {
        final String resUrl = this.getHauptbildURL(width, height, backgroundColorAsHex, alpha, format, false);
        if ( TextUtils.isEmpty(resUrl) )
        {
            if (callback != null)
            {
                callback.isDone(null, false, new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200));
            }
            return null;
        }
        return Datastore.getInstance().loadResourceAsync(resUrl, true, usePersistentStorage, callback);
    }


        public Long getHausnummer()
    {
         return this.data.optLong( "hausnummer" );
    }

    public void setHausnummer(Long hausnummer )
    {
        try
        {
            this.data.put( "hausnummer",hausnummer);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public Double getLatitude()
    {
         return this.data.optDouble( "latitude" );
    }

    public void setLatitude(Double latitude )
    {
        try
        {
            this.data.put( "latitude",latitude);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public double getLocationLatitude( )
    {
         final JSONArray loc = this.data.optJSONArray( "location" );
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
    
    public double getLocationLongitude( )
    {
         final JSONArray loc = this.data.optJSONArray( "location" );
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
    public void setLocationLatitude( double latitude )
    {
        if ( this.data.has( "location" ) == false || this.data.opt("location") == null )
        {
            try
            {
                this.data.put( "location", new JSONArray( ) );
            }
            catch ( JSONException e )
            {
                throw new RuntimeException( e );
            }
        }

        try
        {
            this.data.getJSONArray( "location" ).put( 0, latitude );
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }
    
    public void setLocationLongitude( double longitude )
    {
        try
        {
            if ( this.data.has( "location" ) == false || this.data.opt("location") == null )
            {
                this.data.put( "location", new JSONArray( ) );
            }
            if ( this.data.getJSONArray( "location" ).length( ) == 0 )
            {
                this.data.getJSONArray( "location" ).put( 0, 0 );
            }
    
            this.data.getJSONArray( "location" ).put( 1, longitude );
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }


        public Double getLongitude()
    {
         return this.data.optDouble( "longitude" );
    }

    public void setLongitude(Double longitude )
    {
        try
        {
            this.data.put( "longitude",longitude);
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

        public String getSitzgelegenheiten()
    {
         return this.data.optString( "sitzgelegenheiten" );
    }

    public void setSitzgelegenheiten(String sitzgelegenheiten )
    {
        try
        {
            this.data.put( "sitzgelegenheiten",sitzgelegenheiten);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getSpielplatzid()
    {
         return this.data.optString( "spielplatzid" );
    }

    public void setSpielplatzid(String spielplatzid )
    {
        try
        {
            this.data.put( "spielplatzid",spielplatzid);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getStadtteil()
    {
         return this.data.optString( "stadtteil" );
    }

    public void setStadtteil(String stadtteil )
    {
        try
        {
            this.data.put( "stadtteil",stadtteil);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getStatus()
    {
         return this.data.optString( "status" );
    }

    public void setStatus(String status )
    {
        try
        {
            this.data.put( "status",status);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getStraße()
    {
         return this.data.optString( "straße" );
    }

    public void setStraße(String straße )
    {
        try
        {
            this.data.put( "straße",straße);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }


    private String getApiKeyAndSystemAsUrlParams(boolean isParamListStart)
    {
        StringBuffer sb = new StringBuffer( );
        String startChar = isParamListStart ? "?" : "&";
        sb.append( startChar );
        sb.append( "apiKey=");
        try
        {
            sb.append( Datastore.getInstance().getConfiguredApiKey() );
        }
        catch ( IllegalStateException ex )
        {
            sb.append( User.apiKey );
        }
        sb.append( "&system=");
        try
        {
            sb.append( Datastore.getInstance().getConfiguredSystem() );
        }
        catch ( IllegalStateException ex )
        {
            sb.append( this.getSystem() );
        }
        return sb.toString();
    }

 
    /**
     * Deletes the classes that have been fetched with getspielplatzs() (or its async version) before
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
     * Deletes the classes that have been fetched with getspielplatzsWithReferencedHref (or its async version) before
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
        final spielplatz o = new spielplatz();
        String collectionHref = Datastore.getInstance().createModelHrefWithParams( o.getModuleName( ),
            o.getSimpleName( ), withReferencedHrefs, query );
        return Datastore.getInstance().deleteCollectionFromStorage( collectionHref );
    }
}
