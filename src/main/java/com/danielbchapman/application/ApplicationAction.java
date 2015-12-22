package com.danielbchapman.application;

import com.danielbchapman.international.MessageUtility;
import com.danielbchapman.international.MessageUtility.Instance;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import lombok.Getter;
import lombok.Setter;


/**
 * A class that represents an action (as per swing) that has a label and a 
 * descriptoin and handles an action event. It is designed so that it will
 * be passed to the element that it is assigned to, unfortunately this may
 * be in vein.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Aug 17, 2012
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public class ApplicationAction implements EventHandler<ActionEvent>
{
  private Instance msg;
  @Getter
  @Setter
  private String labelText;
  @Getter
  @Setter
  private String descriptionText;
  private EventHandler<ActionEvent> action;
  @Getter
  private Application application;
  private static final long serialVersionUID = 1L;

  /**
   * @param textKey the internationalized to key to use
   * @param descriptionKey the internationalized key for the description
   * @param moduleToLoad the Class to load
   */
  ApplicationAction(String textKey, String descriptionKey, final Class<? extends Module> moduleToLoad, final Application application)
  {
    this.application = application;
    msg = MessageUtility.getInstance(getClass());
    labelText = msg.get(textKey);
    descriptionText = msg.get(descriptionKey);
    this.action = new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent arg0)
      {
        application.loadModule(moduleToLoad);
      }
    };
  }

  /**
   * 
   * @param textKey the internationalized key to use
   * @param descriptionKey the internationalized key for the description
   * @param action the {@link #ActionListener} to fire
   */
  ApplicationAction(String textKey, String descriptionKey, EventHandler<ActionEvent> action, Application application)
  {
    this.application = application;
    msg = MessageUtility.getInstance(getClass());
    labelText = msg.get(textKey);
    descriptionText = msg.get(descriptionKey);
    this.action = action;
  }

  @Override
  public void handle(ActionEvent arg0)
  {
    if (action != null)
      action.handle(arg0);
    else
      throw new RuntimeException(msg.get("errorApplicationActionNullAction", msg.get("errorApplicationActionNullActionDescription", labelText, descriptionText)));
  }
  
  public MenuItem asMenuItem()
  {
    MenuItem ret = new MenuItem();
    ret.setText(labelText);
    ret.setOnAction(action);
    return ret;
  }

}
