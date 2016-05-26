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
 * Generated class for bewertungen 
 */
public class bewertungen extends AbstractClientDataModel
{
    /**
     * Default constructor. Needed for internal processing.
     */
    public bewertungen ( )
    {
        super( );
    }
    


    /**
     * Returns the simple name of this class 
     */
    public String getSimpleName( )
    {
        return "bewertungen";
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
    public static final List<bewertungen> getbewertungens( String query ) throws ApiomatRequestException
    {
        return getbewertungens( query, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from the server.
     * Important: The boolean parameter indicates the use of offline storage, not if referenced HREFs should be included as in older SDK versions.
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in the persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<bewertungen> getbewertungens( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        bewertungen o = new bewertungen();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), bewertungen.class, query, false, false, usePersistentStorage );
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<List<bewertungen>> getbewertungensAsync(final String query, final AOMCallback<List<bewertungen>> listAOMCallback)
    {
        return getbewertungensAsync( query, listAOMCallback, Datastore.getOfflineUsageForClass(bewertungen.class) );
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
    public static AOMTask<List<bewertungen>> getbewertungensAsync(final String query, final AOMCallback<List<bewertungen>> listAOMCallback, final boolean usePersistentStorage)
    {
         bewertungen o = new  bewertungen();
         return Datastore.getInstance().loadFromServerAsync(o.getModuleName(), o.getSimpleName(), bewertungen.class, query, false, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final List<bewertungen> getbewertungensWithReferencedHrefs( String query ) throws ApiomatRequestException
    {
        return getbewertungensWithReferencedHrefs( query, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    /**
     * Returns a list of objects of this class filtered by the given query from server
     *
     * @query A query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     */
    public static final List<bewertungen> getbewertungensWithReferencedHrefs( String query, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        bewertungen o = new bewertungen();
        return Datastore.getInstance( ).loadFromServer( o.getModuleName( ), o.getSimpleName( ), bewertungen.class, query, true, false, usePersistentStorage);
    }

    /**
     * Get a list of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query a query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background
     */
    public static AOMTask<List<bewertungen>> getbewertungensWithReferencedHrefsAsync(final String query, final AOMCallback<List<bewertungen>> listAOMCallback)
    {
         return getbewertungensWithReferencedHrefsAsync( query, Datastore.getOfflineUsageForClass(bewertungen.class), listAOMCallback); 
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
    public static AOMTask<List<bewertungen>> getbewertungensWithReferencedHrefsAsync(final String query, final boolean usePersistentStorage, final AOMCallback<List<bewertungen>> listAOMCallback)
    {
         bewertungen o = new  bewertungen();
         return Datastore.getInstance().loadFromServerAsync( o.getModuleName(), o.getSimpleName(), bewertungen.class, query, true, false, usePersistentStorage, listAOMCallback);
    }

    /**
     * Returns a count of objects of this class filtered by the given query from server
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     */
    public static final long getbewertungensCount( String query ) throws ApiomatRequestException
    {
        final bewertungen o = new bewertungen();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServer( countHref, bewertungen.class, query, false, false );
    }
    
    /**
     * Get a count of objects of this class filtered by the given query from server
     * This method works in the background and call the callback function when finished
     *
     * @param query filtering the results in SQL style (@see <a href="http://doc.apiomat.com">documentation</a>)
     * @param listAOMCallback The callback method which will called when request is finished
     * @return A reference to the task that runs in the background.
     */
    public static AOMTask<Long> getbewertungensCountAsync(final String query, final AOMCallback<Long> listAOMCallback)
    {
        final bewertungen o = new bewertungen();
        final String countHref = Datastore.getInstance().createModelHref( o.getModuleName( ),
            o.getSimpleName( ) ) + "/count";
        return Datastore.getInstance( ).countFromServerAsync( countHref, bewertungen.class, query, false, listAOMCallback);
    }


        public Long getBewertungsid()
    {
         return this.data.optLong( "bewertungsid" );
    }

    public void setBewertungsid(Long bewertungsid )
    {
        try
        {
            this.data.put( "bewertungsid",bewertungsid);
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
    public String getBildURL()
    {
        return getBildURL( true );
    }

    /**
     * Returns the plain URL of the resource, without any parameters.
     * To access the resource, you can either append your API key and system as parameters or as request headers
     *
     * @return the plain URL of the resource, without any parameters
     */
    public String getBildResourceURL()
    {
        return getBildURL( false );
    }

    /**
     * Returns the URL of the resource, either with API key and system parameters or without.
     * You can also use the methods getBildURL() and getBildResourceURL().
     *
     * @param withApikeyAndSystem
     *
     * @return the URL of the resource, either with API key and system parameters or without
     */
    public String getBildURL(final boolean withApikeyAndSystem)
    {
        String result;
        if(this.data.isNull( "bildURL" ))
        {
            result = null;
        }
        else
        {
            result = this.data.optString( "bildURL" ) + ".img";
            if (withApikeyAndSystem)
            {
                result += getApiKeyAndSystemAsUrlParams(true);
            }
        }
        return result;
    }
    
    /**
     * Request property bild on server and returns data.
     *
     * @return byte[] The resource data
     * @throws ApiomatRequestException
     */
    public byte[] loadBild() throws ApiomatRequestException
    {
        return loadBild( Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    /**
     * Request property bild on server and returns data.
     *
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return byte[] The resource data
     * @throws ApiomatRequestException
     */
    public byte[] loadBild(final boolean usePersistentStorage) throws ApiomatRequestException
    {
        String resUrl = this.getBildResourceURL();
        if ( TextUtils.isEmpty(resUrl) )
        {
            throw new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200);
        }
        return Datastore.getInstance().loadResource( resUrl, true, false, usePersistentStorage );
    }
    
    /**
     * Request property bild on server and returns data.
     * Data will be loaded on a seperate thread and not on the UI thread.
     *
     * @param callback Callback object which will called after request is finished
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<byte[]> loadBildAsync( final AOMCallback<byte[ ]> callback )
    {
        return loadBildAsync( callback, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    /**
     * Request property bild on server and returns data.
     * Data will be loaded on a seperate thread and not on the UI thread.
     *
     * @param callback Callback object which will called after request is finished
     * @param usePersistentStorage Indicates whether to save the response in persistent storage. Has a higher priority than the setting per class in the Datastore.
     * @return A reference to the task that runs in the background.
     */
    public AOMTask<byte[]> loadBildAsync( final AOMCallback<byte[ ]> callback, final boolean usePersistentStorage )
    {
        String resUrl = this.getBildResourceURL();
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

    public String postBild( byte[] data ) throws ApiomatRequestException
    {
        return postBild( data, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }

    public String postBild( byte[] data, final boolean usePersistentStorage ) throws ApiomatRequestException
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
                this.data.put( "bildURL", href );
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
                this.data.remove( "bildURL" );
                throw e;
            }
        }
        return href;
    }

    public AOMTask<String> postBildAsync( final byte[] data, final AOMEmptyCallback _callback )
    {
        return postBildAsync( data, _callback, Datastore.getOfflineUsageForClass( bewertungen.class ) );
    }

    public AOMTask<String> postBildAsync( final byte[] data, final AOMEmptyCallback _callback, final boolean usePersistentStorage )
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
                        bewertungen.this.data.put( "bildURL", href );
                    }
                    catch ( JSONException e )
                    {
                        throw new RuntimeException( e );
                    }
                    /* save new data reference in object */
                    bewertungen.this.saveAsync( usePersistentStorage, new AOMEmptyCallback()
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
                                Log.e("bewertungen", "Exception was thrown: " + exception.getMessage(), exception);
                                try
                                {
                                    Datastore.getInstance( ).deleteOnServer( href, true, usePersistentStorage );
                                    bewertungen.this.data.remove( "bildURL" );
                                }
                                catch ( ApiomatRequestException e )
                                {
                                    Log.e("bewertungen", "Exception was thrown: " + e.getMessage(), e);
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
                        Log.e("bewertungen", "Exception was thrown: " + ex.getMessage(), ex);
                    }
                    else
                    {
                        Log.e("bewertungen", "Exception was thrown: " + Status.HREF_NOT_FOUND.toString());
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
    
    public void deleteBild() throws ApiomatRequestException
    {
        deleteBild( Datastore.getOfflineUsageForClass(bewertungen.class) );
    }

    public void deleteBild( final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String dataURL = this.data.optString( "bildURL" );
        /* First try to remove the attribute and send a PUT, so we know if the caller is allowed to do so */
        try
        {
            this.data.remove( "bildURL" );
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
                this.data.put( "bildURL", dataURL );
            }
            catch (JSONException ex2)
            {
                Log.e( "bewertungen", "The data URL couldn't be re-set to its original value.", ex2 );
            }
            throw ex;
        }
    }

    public AOMTask<Void> deleteBildAsync(final AOMEmptyCallback _callback)
    {
        return deleteBildAsync( _callback, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    public AOMTask<Void> deleteBildAsync( final AOMEmptyCallback _callback, final boolean usePersistentStorage )
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
                    bewertungen.this.data.remove( "bildURL" );
                    /* save deleted image reference in object */
                    bewertungen.this.saveAsync( usePersistentStorage, new AOMEmptyCallback()
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
                                Log.e("bewertungen", "Exception was thrown: " + exception.getMessage(), exception);
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
        
        final String url = getBildResourceURL().replace( ".img", "" );
        bewertungen.this.data.remove( "bildURL" );

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
                        data.put( "bildURL", url );
                    }
                    catch (JSONException ex2)
                    {
                        Log.e( "bewertungen", "The data URL couldn't be re-set to its original value.", ex2 );
                    }
                    if ( _callback != null )
                    {
                        _callback.isDone( wasLoadedFromStorage, exception );
                    }
                    else
                    {
                        Log.e( "bewertungen", "Exception was thrown: " + exception.getMessage( ), exception );
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
        return bewertungen.this.saveAsync( false, usePersistentStorage, saveCallback, null );
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
    public String getBildURL(int width, int height, String backgroundColorAsHex, 
        Double alpha, String format)
    {
        return getBildURL(width, height, backgroundColorAsHex, alpha, format, true);
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
    public String getBildURL(int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, boolean withApikeyAndSystem)
    {
        StringBuffer sb = new StringBuffer( );
        String imageURL = getBildURL(withApikeyAndSystem);
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
     * Request image  bild on server and returns data.
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
    public byte[] loadBild( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format ) throws ApiomatRequestException
    {
        return loadBild( width, height, backgroundColorAsHex, alpha, format, false );
    }
    
    /**
     * Request image  bild on server and returns data.
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
    public byte[] loadBild( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, final boolean usePersistentStorage ) throws ApiomatRequestException
    {
        final String resUrl = this.getBildURL(width, height, backgroundColorAsHex, alpha, format, false);
        if ( TextUtils.isEmpty(resUrl) )
        {
            throw new ApiomatRequestException(Status.ATTACHED_HREF_MISSING, 200);
        }
        return Datastore.getInstance().loadResource(resUrl, true, false, usePersistentStorage);
    }
    
    /**
     * Request property bild on server and returns data.
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
    public AOMTask<byte[]> loadBildAsync( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, final AOMCallback<byte[ ]> callback )
    {
        return loadBildAsync( width, height, backgroundColorAsHex, alpha, format, callback, Datastore.getOfflineUsageForClass(bewertungen.class) );
    }
    
    /**
     * Request property bild on server and returns data.
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
    public AOMTask<byte[]> loadBildAsync( int width, int height, String backgroundColorAsHex, 
        Double alpha, String format, final AOMCallback<byte[ ]> callback, final boolean usePersistentStorage )
    {
        final String resUrl = this.getBildURL(width, height, backgroundColorAsHex, alpha, format, false);
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


        public Long getGesamtbewertung()
    {
         return this.data.optLong( "gesamtbewertung" );
    }

    public void setGesamtbewertung(Long gesamtbewertung )
    {
        try
        {
            this.data.put( "gesamtbewertung",gesamtbewertung);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public Long getSauberkeit()
    {
         return this.data.optLong( "sauberkeit" );
    }

    public void setSauberkeit(Long sauberkeit )
    {
        try
        {
            this.data.put( "sauberkeit",sauberkeit);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public Long getSicherheit()
    {
         return this.data.optLong( "sicherheit" );
    }

    public void setSicherheit(Long sicherheit )
    {
        try
        {
            this.data.put( "sicherheit",sicherheit);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public Long getSpielspass()
    {
         return this.data.optLong( "spielspass" );
    }

    public void setSpielspass(Long spielspass )
    {
        try
        {
            this.data.put( "spielspass",spielspass);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getTextbewertung()
    {
         return this.data.optString( "textbewertung" );
    }

    public void setTextbewertung(String textbewertung )
    {
        try
        {
            this.data.put( "textbewertung",textbewertung);
        }
        catch ( JSONException e )
        {
            throw new RuntimeException( e );
        }
    }

        public String getUeberschriftbewertungskommentar()
    {
         return this.data.optString( "ueberschriftbewertungskommentar" );
    }

    public void setUeberschriftbewertungskommentar(String ueberschriftbewertungskommentar )
    {
        try
        {
            this.data.put( "ueberschriftbewertungskommentar",ueberschriftbewertungskommentar);
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
     * Deletes the classes that have been fetched with getbewertungens() (or its async version) before
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
     * Deletes the classes that have been fetched with getbewertungensWithReferencedHref (or its async version) before
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
        final bewertungen o = new bewertungen();
        String collectionHref = Datastore.getInstance().createModelHrefWithParams( o.getModuleName( ),
            o.getSimpleName( ), withReferencedHrefs, query );
        return Datastore.getInstance().deleteCollectionFromStorage( collectionHref );
    }
}
