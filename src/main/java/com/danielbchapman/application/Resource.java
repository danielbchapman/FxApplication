package com.danielbchapman.application;

import com.danielbchapman.international.MessageUtility;


public abstract class Resource implements IScoped, IInternationalized
{
  private int dirty;
  
  protected final void setDirty(int dirty)
  {
    this.dirty = dirty;
  }
  
  protected final int getDirty()
  {
    return dirty;
  }
  
  private Application application;
  
  /**
   * This is the method that should be used in place of the 
   * constructor. It will be called after <tt>setApplication(application);</tt> and
   * as such will have access to all that application's resources.  
   */
  protected abstract void initialize();

  /**
   * This is the method that should be used when the resource is closed/removed.
   * This should clean all local resources.  
   */
  protected abstract void shutdown();
  public final Application getApplication()
  {
    return application;
  }

  /*
   * (non-Javadoc)
   * @see com.lightassistant.core.application.IInternationalized#msg(java.lang.String)
   */
  @Override
  public String msg(String key)
  {
    return MessageUtility.getInstance(getClass()).get(key);
  }

  /*
   * (non-Javadoc)
   * @see com.lightassistant.core.application.IInternationalized#msg(java.lang.String, java.lang.Object[])
   */
  @Override
  public String msg(String key, Object... params)
  {
    return MessageUtility.getInstance(getClass()).get(key, params);
  }

  public final void setApplication(Application application)
  {
    this.application = application;
  }
  
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
