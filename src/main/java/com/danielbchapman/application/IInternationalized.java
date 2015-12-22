package com.danielbchapman.application;

public interface IInternationalized
{ 
  /**
   * A simple alias call to the message utility to 
   * save typing and keep the code clean.
   * @param key
   * @return <Return Description>  
   * @see #getMessageUtility()
   */
  abstract String msg(String key);
  /**
   * A simple alias call to the message utility to 
   * save typing and keep the code clean.
   * 
   * @param key
   * @param params  
   * @see #getMessageUtility()
   */
  abstract String msg(String key, Object ... params);
}
