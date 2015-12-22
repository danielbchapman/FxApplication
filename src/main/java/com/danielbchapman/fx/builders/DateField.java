package com.danielbchapman.fx.builders;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class DateField extends TextField
{
  public DateField()
  {
    addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
    {

      @Override
      public void handle(KeyEvent evt)
      {
        if (!evt.getCharacter().matches(Fx.PATTERN_DATE))
          evt.consume();
      }
    });
  }

  public DateField(Date i)
  {
    this();
    setDate(i);
  }

  public Date getDate()
  {
    try
    {
      if (getText() == null || getText().trim().length() == 0)
        return null;

      SimpleDateFormat df = new SimpleDateFormat(Fx.FORMAT_DATE);
      return df.parse(getText());
    }
    catch (ParseException e)
    {
      System.err.println("Unable to parse field -> " + getText() + " with pattern " + Fx.FORMAT_DATE);
      return null;
    }
  }

  public void setDate(Date d)
  {
    if (d == null)
    {
      setText("");
      return;
    }

    DecimalFormat df = new DecimalFormat();
    setText(df.format(d));
  }
}
