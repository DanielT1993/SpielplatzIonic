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
package com.apiomat.frontend.helper;

import android.os.AsyncTask;
import com.apiomat.frontend.ApiomatRequestException;
import com.apiomat.frontend.callbacks.AOMCallback;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AsyncTask which is used to communicate with the server in background
 *
 * @author phimi
 * @param <T>
 */
public abstract class AOMTask<T> extends
AsyncTask<AOMCallback<T>, Void, T> {

	private ApiomatRequestException thrownException;
	protected AOMCallback<T> callbackMethod;
     boolean wasLoadedFromStorage;

	public abstract T doRequest(AtomicReference<Boolean> wasLoadedFromStorageAtomicRef) throws ApiomatRequestException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected T doInBackground(AOMCallback<T>... params) {
        T result = null;
        if (params == null || params.length == 0)
        {
            this.callbackMethod = null;
        }
        else
        {
            this.callbackMethod = params[0];
        }
		try
        {
            AtomicReference<Boolean> wasLoadedFromStorageAtomicRef = new AtomicReference<Boolean>();
			result = doRequest(wasLoadedFromStorageAtomicRef);
            Boolean wasLoadedFromStorageBoolean = wasLoadedFromStorageAtomicRef.get();
            if (wasLoadedFromStorageBoolean != null)
            {
                this.wasLoadedFromStorage = wasLoadedFromStorageBoolean.booleanValue();
            }
            /* wasLoadedFromStorageBoolean might be null, which is the case in POST/PUT/DELETE requests */
            else
            {
                this.wasLoadedFromStorage = false;
            }
		} catch (ApiomatRequestException e) {
			this.thrownException = e;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		if (this.callbackMethod != null)
        {
            this.callbackMethod.isDone(result, this.wasLoadedFromStorage, this.thrownException);
		}
	}
}

