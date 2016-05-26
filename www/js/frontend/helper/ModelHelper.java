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

import com.apiomat.frontend.AbstractClientDataModel;

import java.util.List;

/**
 * Static class which provides helper methods for apiOmat classes
 *
 * @author phimi
 */
public class ModelHelper
{
	/**
	 * Method returns true if given list contains HREF already
	 *
	 * @param list list for search
	 * @param href HREF for which we search
	 * @return true if class instance with HREF already in list
	 */
	public static boolean containsHref( final List<? extends AbstractClientDataModel> list, final String href )
	{
		boolean containsHref = false;
		for ( AbstractClientDataModel model : list )
		{
			if ( model.getHref( ).equals( href ) )
			{
				containsHref = true;
				break;
			}
		}
		return containsHref;
	}

	/**
	 * Returns the index of an object in a list if it's contained in that list
	 *
	 * @param list the list that's supposed to contain the object
	 * @param href The href of the object that's supposed to be in the list
	 * @return the index of the object in the list, or -1 if the object is not in the list
	 */
	public static int objectAtIndex( final List<? extends AbstractClientDataModel> list, final String href )
	{
		int index = -1;
		for ( int i = 0; i < list.size( ); i++ )
		{
			if ( list.get( i ).getHref( ).equals( href ) )
			{
				return i;
			}
		}
		return -1;
	}

	public static String getIdFromModel( final AbstractClientDataModel model )
	{
		String href = model.getHref( );
		return href.substring( href.lastIndexOf( "/" ) + 1 );
	}
}
