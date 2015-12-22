package com.danielbchapman;

public abstract class Event<Data>
{
  private Data data;
  /**
   * <p>
   * Return a type of data specified by this event. This is entirely generic so 
   * an event can use that data in any way needed. Common functionality might be 
   * to implement a method to return: <pre>Event<ClazzDTO></pre> so that the returned
   * value can be processed in some way. Other events might return error messages.
   * </p>
   * <p>
   * This is designed to be abuse so that modules can pass event information to
   * the event and then use that information to fire a refresh or whatever they may 
   * need.
   * </p> 
   * <em> THIS IS DESIGNED FOR RAPID PROTOTYPING, NOT REFACTORING, A PATTERN MAY EMERGE</em>
   * @return   the generic specified for this event;
   * 
   */
  public Data getData()
  {
    return data;
  }
  
  /**
   * Set the data to be returned be accessible by this event.
   * @param data the data to set.
   * 
   */
  public void setData(Data data)
  {
    this.data = data;
  }
  
  /**
   * Perform the event action.  
   */
  public abstract void fire();
}
