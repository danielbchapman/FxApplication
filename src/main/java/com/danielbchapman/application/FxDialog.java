package com.danielbchapman.application;

import com.danielbchapman.application.functional.Procedure;
import com.danielbchapman.international.MessageUtility;
import com.danielbchapman.international.MessageUtility.Instance;

import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class FxDialog implements IInternationalized
{
  private Instance msg;
  @Getter
  @Setter
  private Procedure<Boolean> callback;
  @Getter(value=AccessLevel.PROTECTED)
  @Setter(value=AccessLevel.PROTECTED)
  private Application.UiDialog reference;

  public FxDialog()
  {
    msg = MessageUtility.getInstance(getClass());
  }
  
  /**
   * Close this dialog and remove it from display. This
   * will pass FALSE to the procedure onClose;  
   */  
  public final void cancelDialog()
  {
    closeDialog(false);
  }
  
  /**
   * Close this dialog and remove it from display. This
   * will pass TRUE to the procedure onClose;  
   */
  public final void closeDialog()
  {
    closeDialog(true);
  }

  /**
   * <p>
   * Return the content for this dialog. When subsequent calls
   * are made to this method they should return the same object.
   * </p>
   * <em>
   * An effort should be made to keep these as minimal as possible
   * </em>
   * @return the Pane to display
   */
  public abstract Pane getContent();
  
  
  /**
   * Return how this dialog should be displayed. This, obviously, can
   * be set on the fly if needed.
   * 
   * @return The type of this dialog  
   */
  
  public abstract Type getType();
  /* (non-Javadoc)
   * @see com.danielbchapman.application.IInternationalized#msg(java.lang.String)
   */
  public String msg(String key)
  {
    return msg.get(key);
  }
  

  /* (non-Javadoc)
   * @see com.danielbchapman.application.IInternationalized#msg(java.lang.String, java.lang.Object[])
   */
  public String msg(String key, Object ... params)
  {
    return msg.get(key, params);
  }
  
  /**
   * Initializes the dialog after the object is created, but before #getContent is 
   * called.  
   * 
   */
  protected abstract void init();
  private final void closeDialog(boolean dataChanged)
  {
    getReference().getHideTimeline().play();
    if(callback != null)
      callback.call(dataChanged);
  }
  
  public enum Type
  {
    BOTTOM,
    CENTER,
    FULL_SCREEN,
    LEFT,
    RIGHT,
    TOP
  }

}
