package com.danielbchapman.application;

public abstract class ThreadedOperation implements Runnable
{
  private Application application;
  private int count;
  private int max = -1;
  private String message;

  /*
   * (non-Javadoc)
   * @see java.lang.Thread#run()
   */
  public abstract void doWork();

  public Application getApplication()
  {
    return application;
  }

  // FIXME Tie in notifications.
  public int getCount()
  {
    return count;
  }

  public int getMaximum()
  {
    return max;
  }

  public String getMessage()
  {
    return message;
  }

  public abstract void onComplete();

  /**
   * Not implemented  
   */
  public abstract void onInterrupt();

  @Override
  final public void run()
  {
    if (getMaximum() < 1)
      System.out.println("Inteterminate bar...");
//      application.getNotifications().getProgressBar().setIndeterminate(true);

    try
    {
      doWork();
      onComplete();
    }
    catch (Throwable t)
    {
      onInterrupt();
//      application.getNotifications().getProgressBar().setIndeterminate(false);
      throw new RuntimeException(t);
    }
//    application.getNotifications().getProgressBar().setIndeterminate(false);
  }

  public void setApplication(final Application application)
  {
    this.application = application;
  }

  public void setCount(int newCount)
  {
    this.count = newCount;
  }

  /**
   * Set to 0 or less for indefinite.
   * @param max [int] the maximum number of steps. 
   */
  public void setMaximum(int max)
  {
    this.max = max;
  }

  public void setMessage(String message)
  {
    this.message = message;
//    getApplication().getNotifications().setMessage(message, 3);
  }

  public void updateCount(int newCount)
  {
    count = newCount;
  }
}
