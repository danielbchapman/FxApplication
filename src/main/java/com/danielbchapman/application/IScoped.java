package com.danielbchapman.application;


/**
 * An interface that forced common naming for the ScopeType methods. This
 * is to determine the lifetime of resource and module objects in the application.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since May 2, 2012
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public interface IScoped
{
  /**
   * @return The scope for this object. Null is an invalid response  
   * 
   */
  public abstract ScopeType getScopeType();
}
