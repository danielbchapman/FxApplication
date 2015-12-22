package com.danielbchapman.application.dialogs;

import com.danielbchapman.application.functional.Procedure;
import com.danielbchapman.fx.builders.Fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ConfirmaionDialog extends FxDialogTwoButton
{
  private String title;
  private String message;
  
  public ConfirmaionDialog(String title, String message, Procedure<Boolean> callback)
  {
    this.title = title;
    this.message = message;
    setCallback(callback);
  }
  
  private VBox content;
  @Override
  protected Pane getDialogContent()
  {
    if(content != null)
      return content;
    
    content = new VBox();
    content.setPadding(new Insets(20,10,20,10));
    content.setAlignment(Pos.TOP_LEFT);
    content.getChildren().addAll(
        Fx.content(message)
        );
    return content;
  }

  @Override
  protected String getButtonOneText()
  {
    return msg("yes");
    
  }

  @Override
  protected String getButtonTwoText()
  {
    return msg("no");
  }

  @Override
  protected void onOneClick()
  {
    closeDialog();
  }

  @Override
  protected void onTwoClick()
  {
    cancelDialog();
  }

  @Override
  public Type getType()
  {
    return Type.CENTER;
  }

  @Override
  protected void init()
  {    
  }

  @Override
  protected String getTitle()
  {
    return title;
  }
}
