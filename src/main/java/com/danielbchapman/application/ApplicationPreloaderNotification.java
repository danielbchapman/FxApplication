package com.danielbchapman.application;

import javafx.application.Preloader;
import lombok.Getter;
import lombok.Setter;


/**
 * A text based progress notification.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Aug 19, 2012
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public class ApplicationPreloaderNotification extends Preloader.ProgressNotification
{
  @Getter
  @Setter
  private String title;
  @Getter
  @Setter
  private String message;
  
  public ApplicationPreloaderNotification(String title, String message, double progress)
  {
    super(progress);
    this.title = title;
    this.message = message;  
  }
}
