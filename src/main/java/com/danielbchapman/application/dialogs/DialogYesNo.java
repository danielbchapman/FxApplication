package com.danielbchapman.application.dialogs;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class DialogYesNo extends FxDialogTwoButton
{

  private HBox content;
  @Override
  protected Pane getDialogContent()
  {
    if(content != null)
      return content;
    
    content = new HBox();
    content.setStyle("-fx-background-color: #aaaaff");
    content.getChildren().addAll(new Label("Test dialog (yes, no)"));
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
  public Type getType()
  {
    return Type.CENTER;
  }

}
