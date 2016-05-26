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
 * Generated class for ausstattung 
 */
public class ausstattung extends AbstractClientDataModel
{
    /**
     * Default constructor. Needed for internal processing.
     */
    public ausstattung ( )
    {
        super( );
    }
    


    /**
     * Returns the simple name of this class 
     */
    public String getSimpleName( )
    {
        return "ausstattung";
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
    public static final List<ausstattung> getausstattungs( String query ) throws ApiomatRequestException
    {
        return getausstattungs( query, Datastore.getOfflineUsageForClass(ausstattung.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from the server.
     * Important: The boolean parameter indicates the use of offline storage, not if referenced HREFs should be included as in older SDK versions.
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in the persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<ausstattung> getausstattungs( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        ausstattung o = new ausstattung();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), ausstattung.class, query, false, false, usePersistentStorage );
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<List<ausstattung>> getausstattungsAsync(final String query, final AOMCallback<List<ausstattung>> listAOMCallback)
    {
        return getausstattungsAsync( query, listAOMCallback, Datastore.getOfflineUsageForClass(ausstattung.class) );
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
    public static AOMTask<List<ausstattung>> getausstattungsAsync(final String query, final AOMCallback<List<ausstattung>> listAOMCallback, final boolean usePersistentStorage)
    {
         ausstattung o = new  ausstattung();
         return Datastore.getInstance().loadFromServerAsync(o.getModuleName(), o.getSimpleName(), ausstattung.class, query, false, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<ausstattung> getausstattungsWithReferencedHrefs( String query ) throws ApiomatRequestException
    {
        return getausstattungsWithReferencedHrefs( query, Datastore.getOfflineUsageForClass(ausstattung.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<ausstattung> getausstattungsWithReferencedHrefs( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        ausstattung o = new ausstattung();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), ausstattung.class, query, true, false, usePersistentStorage);
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background
     */
    public static AOMTask<List<ausstattung>> getausstattungsWithReferencedHrefsAsync(final String query, final AOMCallback<List<ausstattung>> listAOMCallback)
    {
         return getausstattungsWithReferencedHrefsAsync( query, Datastore.getOfflineUsageForClass(ausstattung.class), listAOMCallback); 
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
    public static AOMTask<List<ausstattung>> getausstattungsWithReferencedHrefsAsync(final String query, final boolean usePersistentStorage, final AOMCallback<List<ausstattung>> listAOMCallback)
    {
         ausstattung o = new  ausstattung();
         return Datastore.getInstance().loadFromServerAsync( o.getModuleName(), o.getSimpleName(), ausstattung.class, query, true, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a count of objects of this class filtered by the given query from server
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final long getausstattungsCount( String query ) throws ApiomatRequestException
    {
        final ausstattung o = new ausstattung();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServer( countHref, ausstattung.class, query, false, false );
    }
    
    /**
     * Get a count of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<Long> getausstattungsCountAsync(final String query, final AOMCallback<Long> listAOMCallback)
    {
        final ausstattung o = new ausstattung();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServerAsync( countHref, ausstattung.class, query, false, listAOMCallback);
    }


        public Long getAusstattungsid()
    {
         return this.data.optLong( "ausstattungsid" );
    }

    public void setAusstattungsid(Long ausstattungsid )
    {
        try
        {
            this.data.put( "ausstattungsid",ausstattungsid);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getBallsport()
    {
         return this.data.optString( "ballsport" );
    }

    public void setBallsport(String ballsport )
    {
        try
        {
            this.data.put( "ballsport",ballsport);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getFederwippe()
    {
         return this.data.optString( "federwippe" );
    }

    public void setFederwippe(String federwippe )
    {
        try
        {
            this.data.put( "federwippe",federwippe);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getKlettergerüst()
    {
         return this.data.optString( "klettergerüst" );
    }

    public void setKlettergerüst(String klettergerüst )
    {
        try
        {
            this.data.put( "klettergerüst",klettergerüst);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getKletterturm()
    {
         return this.data.optString( "kletterturm" );
    }

    public void setKletterturm(String kletterturm )
    {
        try
        {
            this.data.put( "kletterturm",kletterturm);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getRutsche()
    {
         return this.data.optString( "rutsche" );
    }

    public void setRutsche(String rutsche )
    {
        try
        {
            this.data.put( "rutsche",rutsche);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getSandkasten()
    {
         return this.data.optString( "sandkasten" );
    }

    public void setSandkasten(String sandkasten )
    {
        try
        {
            this.data.put( "sandkasten",sandkasten);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getSanitäranlagen()
    {
         return this.data.optString( "sanitäranlagen" );
    }

    public void setSanitäranlagen(String sanitäranlagen )
    {
        try
        {
            this.data.put( "sanitäranlagen",sanitäranlagen);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getSchaukel()
    {
         return this.data.optString( "schaukel" );
    }

    public void setSchaukel(String schaukel )
    {
        try
        {
            this.data.put( "schaukel",schaukel);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getSeilbahn()
    {
         return this.data.optString( "seilbahn" );
    }

    public void setSeilbahn(String seilbahn )
    {
        try
        {
            this.data.put( "seilbahn",seilbahn);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getTischtennis()
    {
         return this.data.optString( "tischtennis" );
    }

    public void setTischtennis(String tischtennis )
    {
        try
        {
            this.data.put( "tischtennis",tischtennis);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getWasserspiele()
    {
         return this.data.optString( "wasserspiele" );
    }

    public void setWasserspiele(String wasserspiele )
    {
        try
        {
            this.data.put( "wasserspiele",wasserspiele);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getWippe()
    {
         return this.data.optString( "wippe" );
    }

    public void setWippe(String wippe )
    {
        try
        {
            this.data.put( "wippe",wippe);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }



 
    /**
     * Deletes the classes that have been fetched with getausstattungs() (or its async version) before
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
     * Deletes the classes that have been fetched with getausstattungsWithReferencedHref (or its async version) before
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
        final ausstattung o = new ausstattung();
        String collectionHref = Datastore.getInstance().createModelHrefWithParams( o.getModuleName( ),
            o.getSimpleName( ), withReferencedHrefs, query );
        return Datastore.getInstance().deleteCollectionFromStorage( collectionHref );
    }
}
