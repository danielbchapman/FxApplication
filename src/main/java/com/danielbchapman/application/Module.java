package com.danielbchapman.application;

import javafx.scene.Node;

public abstract class Module extends Resource
{
  /**
   * Do not use, this is for tracking calls.
   */
  protected boolean postInitCalled;
  // I think I need to look at if the Scene is correct--probably it isn't.
  protected Node panelRoot;
  
  /**
   * <p>
   * This is the primary method for the module and should be considered
   * the entry point for all GUI builders.
   * </p>
   * 
   * <p>
   * As this is designed to pass a reference the reference to the UI should
   * be maintained internally. This means that subsequent calls to {@link #getNode()} 
   * should return the same object.
   * </p>
   * 
   * @return The node to be played in the application panel.  
   */
  protected abstract Node getNode();
  
  /** 
   * The initialize method replaces the constructor. It is 
   * called after the object has a reference to the application
   * and after the dependencies are registered with the application (in turn 
   * making sure all the resources and modules needed are already loaded in the
   * application.
   */
  protected abstract void initialize();
  
  /**
   * A method that is fired after the module is initialized and rendered.
   * This can be used to have a user set a default value etc...  
   */
  protected abstract void postInitialize();
  
  /**
   * An alias for getApplication()
   * @see {@link #getApplication()} 
   * 
   */
  protected Application app()
  {
    return getApplication();
  }
  
  /**
   * An alias for {@link #getApplication()} and {@link Application#getService(Class)}  
   * @see {@link Application#getService(Class)}
   */
  protected <T extends Service> T service(Class<T> service)
  {
    return app().getService(service);
  }
  
  /**
   * An alias for {@link #getApplication()} and {@link Application#getResource(Class)}
   * @see {@link Application#getResource(Class)}
   */
  protected <T extends Resource> T resource(Class<T> resource)
  {
    return app().getResource(resource);
  }
  
}
