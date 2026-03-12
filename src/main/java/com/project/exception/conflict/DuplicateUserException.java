
package com.project.exception.conflict;

import com.project.exception.base.BaseApplicationException;

public class DuplicateUserException extends BaseApplicationException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4307293590762936063L;
	
	public DuplicateUserException( String message )
	{
		super( message );
	}
	
}
