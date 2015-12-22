package com.danielbchapman.fx.builders;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class IntegerField extends TextField
{
  public IntegerField()
  {
    addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
    {

      @Override
      public void handle(KeyEvent evt)
      {
        if (!evt.getCharacter().matches(Fx.PATTERN_INTEGER))
          evt.consume();
      }
    });
  }

  public IntegerField(int i)
  {
    this();
    setInteger(i);
  }

  public Integer getInteger()
  {
    try
    {
      if (getText() == null || getText().trim().length() == 0)
        return null;

      DecimalFormat df = new DecimalFormat("#");
      return df.parse(getText()).intValue();
    }
    catch (ParseException e)
    {
      System.err.println("Unable to parse field -> " + getText() + " with pattern " + Fx.FORMAT_INTEGER);
      return null;
    }
  }

  public void setInteger(Integer i)
  {
    if (i == null)
    {
      setText("");
      return;
    }
    DecimalFormat df = new DecimalFormat();
    setText(df.format(i));
  }
}
