package com.danielbchapman.fx.builders;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FloatField extends TextField
{
  public FloatField()
  {
    addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
    {

      @Override
      public void handle(KeyEvent evt)
      {
        if (!evt.getCharacter().matches(Fx.PATTERN_DECIMAL))
          evt.consume();
      }
    });
  }

  public FloatField(double i)
  {
    this();
    setDouble(i);
  }

  public Float getFloat()
  {
    try
    {
      if (getText() == null || getText().trim().length() == 0)
        return null;

      DecimalFormat df = new DecimalFormat();
      return df.parse(getText()).floatValue();
    }
    catch (ParseException e)
    {
      System.err.println("Unable to parse field -> " + getText() + " with pattern " + Fx.FORMAT_DECIMAL);
      return null;
    }
  }

  public void setDouble(Double d)
  {
    if (d == null)
    {
      setText("");
      return;
    }
    DecimalFormat df = new DecimalFormat(Fx.PATTERN_DECIMAL);
    setText(df.format(d));
  }
}
