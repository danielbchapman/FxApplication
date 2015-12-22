package com.danielbchapman.application;


/**
 * A simple exception class that contains information on how to display the informatiom
 * to the user.
 * <p>
 * This allows application service to place dialogs "on the gui" and determine the response. This
 * defaults to Warning which should just display the information and abort the current method
 * logic.
 * </p>
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Sep 10, 2012
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public class UserNotificationException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public enum Level
  {
    /**
     * Abort the current method and display a warning dialog
     */
    WARNING,
    
    /**
     * Abort the current module (unload) and force a dirty state onto all 
     * services. This will display a custom error message in a frightening way.
     */
    SEVERE,
    
    /**
     * Abort the current method and display information to the user (friendlier!) 
     */
    INFORMATION
  }
  
  private Level level = Level.WARNING;
  
  public UserNotificationException(String message, Level level)
  {
    super(message);
    this.level = level;
  }
}
