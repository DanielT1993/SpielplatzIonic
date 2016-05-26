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
package com.apiomat.frontend;

/**
 * Custom exception class
 *
 * @author andreas, phimi
 */
public class ApiomatRequestException extends Exception
{
    private static final long serialVersionUID = -319955397589517084L;
    private final int expectedReturnCode;
    private final int returnCode;
    private final String reason;
    private final Status status;

    /**
     * Constructor
     *
     * @param returnCode The HTTP/apiOmat return code
     * @param expectedReturnCode Expected HTTP/apiOmat return code
     * @param reason (optional) Reason why exception is thrown
     */
    public ApiomatRequestException( int returnCode, int expectedReturnCode, final String reason )
    {
        super( parseReason( returnCode, expectedReturnCode, reason ) );

        this.expectedReturnCode = expectedReturnCode;
        this.returnCode = returnCode;
        this.reason = getMessage( );
        this.status = Status.getStatusForCode( returnCode );
    }

    /**
     * Constructor for {@link ApiomatRequestException}
     *
     * @deprecated Please use {@link #ApiomatRequestException(int, int, String)} instead
     *
     * @param expectedReturnCode
     * @param returnCode
     * @param reasonTitle
     * @param reasonBody
     */
    @Deprecated
    public ApiomatRequestException( int returnCode, int expectedReturnCode, String reasonTitle, String reasonBody )
    {
        super( parseReason( returnCode, expectedReturnCode, reasonTitle ) );

        this.expectedReturnCode = expectedReturnCode;
        this.returnCode = returnCode;
        this.reason = getMessage( );
        this.status = Status.getStatusForCode( returnCode );
    }

    /**
     * Create {@link ApiomatRequestException} based on {@link Status}
     *
     * @deprecated Please use {@link #ApiomatRequestException(Status, int)} instead
     * @param status
     */
    @Deprecated
    public ApiomatRequestException( final Status status )
    {
        this( status, 0 );
    }

    /**
     * Constructor for creating {@link ApiomatRequestException} from given {@link Status} and given expected code
     *
     * @param status the status object
     * @param expectedReturnCode Expected HTTP/apiOmat return code
     */
    public ApiomatRequestException( final Status status, final int expectedReturnCode )
    {
        this( status, expectedReturnCode, null );
    }

    /**
     * Constructor for creating {@link ApiomatRequestException} from given {@link Status} and given expected code
     *
     * @param status the status object
     * @param expectedReturnCode Expected HTTP/apiOmat return code
     * @param reason (optional) Reason why exception is thrown. If null reason of {@code status} is used
     */
    public ApiomatRequestException( final Status status, final int expectedReturnCode, final String reason )
    {
        super( reason != null && reason.isEmpty( ) == false ? reason : status.getReasonPhrase( ) );

        this.expectedReturnCode = expectedReturnCode;
        this.returnCode = status.getStatusCode( );
        this.reason = getMessage( );
        this.status = status;
    }

    public int getExpectedReturnCode( )
    {
        return this.expectedReturnCode;
    }

    public int getReturnCode( )
    {
        return this.returnCode;
    }

    /**
     * @deprecated Please use {@link #getMessage()} instead
     * @return
     */
    @Deprecated
    public String getReason( )
    {
        return this.reason;
    }

    public Status getStatus( )
    {
        return this.status;
    }

    private static String parseReason( int returnCode, int expectedReturnCode, String reasonTitle )
    {
        String text = "Return code " + returnCode + " does not match expected one (" + expectedReturnCode + ")";
        Status s = null;

        if ( reasonTitle != null && reasonTitle.length( ) > 0 )
        {
            text = reasonTitle;
        }
        else if ( ( s = Status.getStatusForCode( returnCode ) ) != null )
        {
            text = s.getReasonPhrase( );
        }
        return text;
    }
}