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
package com.apiomat.frontend.callbacks;

import android.util.Log;
import com.apiomat.frontend.ApiomatRequestException;

/**
 * @author phimi
 */
public abstract class AOMEmptyCallback extends AOMCallback<Void> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apiomat.frontend.helper.callbacks.AOMCallback#isDone(java.lang.Object,
	 * com.apiomat.frontend.ApiomatRequestException)
	 */
	@Override
    @Deprecated
	public void isDone(Void resultObject, ApiomatRequestException exception) {
		isDone(exception);
	}

    /**
     * @deprecated Override {@link #isDone(boolean, com.apiomat.frontend.ApiomatRequestException)} instead
     *
     * @param exception
     */
    @Deprecated
	public abstract void isDone(ApiomatRequestException exception);

    /**
     * Gets called when the request is finished.
     *
     * Will be marked as abstract as soon as the deprecated method {@link #isDone(com.apiomat.frontend.ApiomatRequestException)} gets removed.
     *
     * @param wasLoadedFromStorage
     *            true if the object was loaded from storage (cache or persistent), false if it was loaded from the server
     * @param exception
     *            An exception object on failure else null
     */
    public void isDone(boolean wasLoadedFromStorage, ApiomatRequestException exception)
    {
        Log.w("AOMEmptyCallback", "You're overriding the deprecated method AOMEmptyCallback.isDone(ApiomatRequestException).\n" +
                "\tPlease use AOMEmptyCallback.isDone(boolean, ApiomatRequestException) instead");
        isDone(exception);
    }


    /*
     * (non-Javadoc)
     *
     * @see com.apiomat.frontend.helper.callbacksAOMCallback#isDone(java.lang.Object, boolean, com.apiomat.frontend.ApiomatRequestException)
     */
    public void isDone(Void resultObject, boolean wasLoadedFromStorage, ApiomatRequestException exception)
    {
        isDone(wasLoadedFromStorage, exception);
    }
}
