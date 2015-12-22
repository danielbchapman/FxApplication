package com.danielbchapman.application;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * A holder class for all the classes in order to generate a stylesheet for the base application.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Aug 20, 2012
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public class CSS
{
  //@formatter:off
  /* DIALOGS */
  public final static String DIALOG = "dbc-application-dialog";
  public final static String DIALOG_CONTENT = "dbc-application-dialog-content";
  public final static String DIALOG_ERROR = "dbc-application-dialog-error";
  public final static String DIALOG_WARNING = "dbc-application-dialog-warning";
  public final static String DIALOG_INFORMATION = "dbc-application-dialog-information";
  
  /* PANES */
  public final static String BACKGROUND = "dbc-application-background";
  public final static String BACKGROUND_DARK = "dbc-application-background-dark";
  public final static String BACKGROUND_LIGHT = "dbc-application-background-light";
  
  
  /* FIELDS */
  public final static String FIELD_NORMAL = "dbc-application-field-normal";
  public final static String FIELD_WARNING = "dbc-application-field-warning";
  public final static String FIELD_ERROR = "dbc-application-field-error";
  
  
  /* TEXT */
  public final static String TEXT_TITLE = "dbc-application-text-title";
  public final static String TEXT_TITLE_SUB = "dbc-application-text-subtitle";
  public final static String TEXT_CONTENT = "dbc-application-text-content";
  public final static String TEXT_HINT = "dbc-application-text-hint";
  public final static String TEXT_LEGEND = "dbc-application-text-legend";
  
  public final static String[] all;
  //@formatter:on
  
  static
  {
    all = new String[]{
        DIALOG,
        DIALOG_CONTENT,
        DIALOG_ERROR,
        DIALOG_WARNING,
        DIALOG_INFORMATION,
        BACKGROUND,
        BACKGROUND_DARK,
        BACKGROUND_LIGHT,
        FIELD_NORMAL,
        FIELD_ERROR,
        FIELD_WARNING,
        TEXT_CONTENT,
        TEXT_TITLE,
        TEXT_TITLE_SUB
    };
  }
  
  public static void main(String ... args)
  {
    print(System.out);
  }
  public static void print(PrintStream out)
  {
    out.println("#######################");
    out.println("# Generated Stylesheet");
    out.println("# danielbchapman.com");
    out.println("# Application Framework");
    out.println("#######################");
    out.println("");
    for(String s : all)
    {
      out.print(".");
      out.print(s);
      out.println("{");
      out.println("}");
      out.println();
    }

  }
}
