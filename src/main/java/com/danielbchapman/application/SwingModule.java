package com.danielbchapman.application;

import javax.swing.JComponent;


/**
 * <p>
 * Modules are the UI Building blocks of the application. They provide an 
 * encapsulated, scoped set of methods and interactions that the user and other
 * modules can access to provide functionality 
 * </p>
 * 
 * <p> 
 * An example of a module would be something that allows you to create a user,
 * delete the user, and then access a list of all users. It would likely present 
 * several options to do this
 * </p>
 * <code>
 * <pre>
 * Example:
 *   - UserModule
 *     - Dependencies
 *       - UserView
 *         - UserService
 *           - UserResource
 *     - UserUI
 *     - UserDropDown
 * </pre>
 * </code>
 * It is a high level object that provides a way for the application to blindly initialize 
 * it and let it fend for itself short of critical exceptions.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since May 2, 2012
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public abstract class SwingModule extends Resource
{
  /** 
   * The initialize method replaces the constructor. It is 
   * called after the object has a reference to the application
   * and after the dependencies are registered with the application (in turn 
   * making sure all the resources and modules needed are already loaded in the
   * application.
   */
  public abstract void initialize();
  

  /**
   * <p>
   * This is the primary method for the module and should be considered
   * the entry point for all GUI builders.
   * </p>
   * 
   * <p>
   * As this is designed to pass a reference the reference to the UI should
   * be maintained internally. This means that subsequent calls to {@link #getModuleUI()} 
   * should return the same object.
   * </p>
   * @return a component that contains this module's UI.   
   */
  public abstract JComponent getModuleUI();
  
}
