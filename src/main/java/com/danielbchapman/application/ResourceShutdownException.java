package com.danielbchapman.application;

/**
 * A simple message that allows a resource to fail when loaded. This will
 * then be handled by the exception handler registered to the application.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Apr 29, 2011
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public class ResourceShutdownException extends Exception
{
  private static final long serialVersionUID = 1L;

  public ResourceShutdownException(String message, Throwable chainable)
  {
    super(message, chainable);
  }
  
  public ResourceShutdownException(String message)
  {
    super(message);
  }
}
