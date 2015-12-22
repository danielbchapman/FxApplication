package com.danielbchapman.application.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import com.danielbchapman.application.FxDialog;
import com.danielbchapman.fx.builders.Fx;
import com.danielbchapman.fx.builders.GridBuilder;

public abstract class FxDialogTwoButton extends FxDialog
{

  private BorderPane content;
  
  protected abstract String getTitle();
  protected abstract Pane getDialogContent();
  protected abstract String getButtonOneText();
  protected abstract String getButtonTwoText();
  protected abstract void onOneClick();
  protected abstract void onTwoClick();
  
  /* (non-Javadoc)
   * @see com.danielbchapman.application.FxDialog#getContent()
   */
  @Override
  public Pane getContent()
  {
    if(content != null)
      return content;
    content = new BorderPane();
    
    content.setCenter(getDialogContent());
    
    final Button one = new Button(getButtonOneText());
    final Button two = new Button(getButtonTwoText());
    one.setOnAction(new EventHandler<ActionEvent>(){

      @Override
      public void handle(ActionEvent evt)
      {
        onOneClick();
      }});
    
    two.setOnAction(new EventHandler<ActionEvent>(){

      @Override
      public void handle(ActionEvent evt)
      {
        onTwoClick();
      }});
    
    GridPane grid = GridBuilder
        .create()
        .columns(2)
        .margin(5, 0, 0, 10)
        .padding(0, 0, 10, 0)
        .add(one, two)
        .build();
    
    grid.setAlignment(Pos.BOTTOM_RIGHT);
    
    content.setTop(Fx.title(getTitle()));
    content.setMinSize(400, 200);
    content.setBottom(grid);
    return content;
  }
}

