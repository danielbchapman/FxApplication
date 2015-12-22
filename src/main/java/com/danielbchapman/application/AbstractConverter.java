package com.danielbchapman.application;

public abstract class AbstractConverter<T> implements IConverter<T>
{
  private Application application;

  protected Application getAppliciation()
  {
    return application;
  }

  protected final void setApplication(Application application)
  {
    this.application = application;
  }
}
