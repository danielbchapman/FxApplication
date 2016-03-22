package com.danielbchapman.application;

import java.net.URL;
import java.util.ResourceBundle;

import com.danielbchapman.international.MessageUtility;
import com.danielbchapman.international.MessageUtility.Instance;

import javafx.fxml.Initializable;

public abstract class Controller implements Initializable
{
  private Instance msg;
  public Application app()
  {
    return Application.getCurrentInstance();
  }
  
  public <T extends Module> T module(Class<T> clazz)
  {
    return (T) app().getModule(clazz);
  }
  
  public <T extends Service> T service(Class<T> clazz)
  {
    return app().getService(clazz);
  }
  
  public <T extends Resource> T resource(Class<T> clazz)
  {
    return app().getResource(clazz);
  }
  
  public String msg(String key)
  {
    if(msg == null)
      msg = MessageUtility.getInstance(getClass());
    
    return msg.get(key);
  }
  
  public String msg(String key, Object ... args)
  {
    if(msg == null)
      msg = MessageUtility.getInstance(getClass());
    
    return msg.get(key, args);
  }

  protected abstract void initialize();
  @Override
  public void initialize(URL arg0, ResourceBundle arg1)
  {
    initialize();
  }
}
