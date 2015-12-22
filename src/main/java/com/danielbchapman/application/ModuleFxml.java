package com.danielbchapman.application;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class ModuleFxml<Controller> extends Module
{
  private Parent root;
  private Controller controller;
  /**
   * Return a reference to an URL for this FXML file.
   */
  public abstract InputStream getFxml();
  
  /**
   * Create a reference to the local controller. this will be cached. 
   * @return a reference to the backing bean for this module. Initialize  
   * 
   */
  public abstract Controller initializeController();
  
  public Controller getController()
  {
    if(controller == null)
      controller = initializeController();
    
    return controller;
  }

  @Override
  protected Node getNode()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader();
      Parent fxml = (Parent) loader.load(getFxml());
      root = fxml;
    }
    catch (IOException e)
    {
      throw new RuntimeException(e.getMessage(), e);
    }
    
    if(root == null)
      throw new RuntimeException("Unable to load module...");
    
    return root;
  }
}
