package com.danielbchapman.application;


/**
 * The state in which an object should live.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since May 2, 2012
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public enum ScopeType
{
  
  /**
   * <p>
   * LOCAL scope means that the application will
   * generate a new object for each request
   * </p>
   * <p>
   * <pre>
   * Example:
   * 
   *   [Resource of scope Local]
   *   
   *   -Module A:
   *     - application.getResource(Resource.class); //returns ref 1;
   *       ...
   *       application.getResource(Resource.class); //returns ref x;
   *   - Module B, load():
   *       application.getResource(Resource.class); //returns ref x + 1;
   * </pre>
   * </p>
   */
  LOCAL,
  
  /**
   * <p>
   * Module scope means that the application will generate a
   * new object if the module is dirty (has recently been changed)
   * or if the object is null
   * </p>
   * <p>
   * <pre>
   * Example:
   * 
   *   [Resource of scope Module]
   *   
   *   -Module A:
   *     - application.getResource(Resource.class); //returns ref 1;
   *       ...
   *       application.getResource(Resource.class); //returns ref 1;
   *   - Module B, load():
   *       application.getResource(Resource.class); //returns ref 2;
   * </pre>
   * </p>
   * 
   */
  MODULE,
  
  /**
   * <p>
   * The application will only create a new object if the the
   * object is null. 
   * </p>
   * <p>
   * <pre>
   * Example:
   *   [Resource of scope Module]
   *   
   *   -Module A:
   *     - application.getResource(Resource.class); //returns ref 1;
   *       ...
   *       application.getResource(Resource.class); //returns ref 1;
   *   - Module B, load():
   *       application.getResource(Resource.class); //returns ref 1;
   * </pre>
   * </p>
   * 
   */
  APPLICATION
}
