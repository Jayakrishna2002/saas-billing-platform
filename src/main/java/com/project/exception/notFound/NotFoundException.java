
package com.project.exception.notFound;

import com.project.exception.base.BaseApplicationException;

public class NotFoundException extends BaseApplicationException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -938346813717203224L;
	
	public NotFoundException( String message )
	{
		super( message );
	}
	
}
