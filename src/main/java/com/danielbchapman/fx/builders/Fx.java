package com.danielbchapman.fx.builders;

import java.util.List;

import com.danielbchapman.application.CSS;
import com.danielbchapman.application.functional.Call;
import com.danielbchapman.application.functional.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class Fx
{
  public final static String PATTERN_INTEGER = "^[0-9]";
  public final static String PATTERN_DECIMAL = "^[0-9,.\\-]";
  public final static String PATTERN_DATE = "^[0-9\\/]";
  
  public final static String FORMAT_INTEGER = ",###";
  public final static String FORMAT_DECIMAL = ",###.###]";
  public final static String FORMAT_DATE = "MM/dd/yy";
  
  public static TextField promptText(String s)
  {
    TextField f = new TextField();
    f.setPromptText(s);
    return f;
  }
  
  public static TextField promptText(String value, String prompt)
  {
    TextField f = promptText(prompt);
    if(value != null)
      f.setText(value);
    return f;
  }
  
  public static Label legend(String text)
  {
    Label ret = new Label(text);
    ret.getStyleClass().add(CSS.TEXT_LEGEND);
    ret.setStyle("-fx-text-fill: red;");
    return ret;
  }
  
  public static TextArea promptArea(String text)
  {
    TextArea area = new TextArea();
    area.setPromptText(text);
    return area;
  }
  
  public static TextArea promptArea(String value, String prompt)
  {
    TextArea ret = promptArea(prompt);
    if(value != null)
      ret.setText(value);
    return ret;
  }
  
  public static Label label(String text)
  {
    Label ret = new Label(text);
    ret.setStyle("-fx-background-color: yellow;");
    return ret;
  }
  
  /**
   * A placeholder method to apply styles to 
   * any labels that need to be made.
   * @param text the text
   * @return a new label with the proper CSS class  
   */
  public static Label labelFor(String text)
  {
    return new Label(text);
  }
  public static Label title(String title)
  {
    Label ret = new Label(title);
    ret.setStyle("-fx-font: 18px \"Serif\";-fx-padding: 10; -fx-text-fill: red;");
    ret.getStyleClass().add(CSS.TEXT_TITLE);
    return ret;
  }
  
  public static Label content(String content)
  {
    Label ret = new Label(content);
    ret.getStyleClass().add(CSS.TEXT_CONTENT);
    return ret;
  }
  
  public static HBox hbox(final Node ... nodes)
  {
    HBox box = new HBox();
    box.getChildren().addAll(nodes);
    return box;
  }
  
  public static HBox hbox(double width, Node ... nodes)
  {
    HBox ret = hbox(nodes);
    ret.setMaxWidth(width);
    return ret;
  }
  
  public static Label hint(String hint)
  {
    Label ret = new Label(hint);
    ret.getStyleClass().add(CSS.TEXT_HINT);
    ret.setStyle("-fx-text-fill: blue;");
    return ret;
  }
  
  public static Label subTitle(String subTitle)
  {
    Label sub = new Label();
    sub.setStyle("-fx-font: 16px \"Serif\";-fx-padding: 10; -fx-text-fill: orange;");
    sub.getStyleClass().add(CSS.TEXT_TITLE);
    return sub;
  }
  
  public static TilePane group(int prefColumns, Insets padding, Insets margin, Node ... nodes)
  {
    TilePane ret = new TilePane();
    ret.setPrefColumns(prefColumns);
    ret.getChildren().addAll(nodes);
    
    if(padding != null)
      ret.setPadding(padding);
    
    for(Node n : nodes)
      if(margin != null)
        TilePane.setMargin(n, margin);
    
    return ret; 
  }
  
  public static TilePane group(Node ... nodes)
  {
    int columns = nodes.length;
    return group(columns, null, null, nodes);
  }
  
  public static TilePane group(int col, Node ... nodes)
  {
    return group(col, null, null, nodes);
  }
  
  public static TextField input()
  {
    return new TextField();
  }
  
  public static TextField input(String value)
  {
    if(value == null)
      return input();
    
    return new TextField(value);
  }
  
  public static TextArea area()
  {
    return new TextArea();
  }
  
  public static TextArea area(String value)
  {
    if(value == null)
      return area();
    
    return new TextArea(value);
  }
  
  public static TextField prompt(String prompt)
  {
    TextField text = input();
    text.setPromptText(prompt);
    return text;
  }
  
  public static IntegerField promptInt(int value, String prompt)
  {
    IntegerField integer = promptInt(prompt);
    integer.setText(Integer.toString(value));
    return integer;
  }
  
  public static IntegerField promptInt(String prompt)
  {
    IntegerField integer = new IntegerField();
    integer.setPromptText(prompt);
    return integer;
  }
  
  public static DecimalField promptDecimal(String prompt)
  {
    DecimalField decimal = new DecimalField();
    decimal.setPromptText(prompt);
    return decimal;
  }
  
  public static DecimalField promptDecimal(String prompt, double width)
  {
    DecimalField ret = promptDecimal(prompt);
    ret.setMinWidth(width);
    return ret;
  }
  
  public static DateField promptDate(String prompt)
  {
    DateField date = new DateField();
    date.setPromptText(prompt);
    return date;
  }
  
  public static Pane groupHorizontal(Node ... nodes)
  {
    return GridBuilder
        .create()
        .columns(nodes.length)
        .margin(5, 5, 0, 0)
        .add(nodes)
        .build();
  }
  
  public static Pane pair(Node one, Node two)
  {
    return GridBuilder
        .create()
        .columns(3)
        .margin(5, 5, 0, 0)
        .add(one)
        .label("/")
        .add(two)
        .build();
  }
  
  public static <T> ComboBox<T> comboBoxEditable(String prompt, List<T> values)
  {
    ObservableList<T> list = FXCollections.observableList(values);
    ComboBox<T> box = new ComboBox<T>(list);
    box.setPromptText(prompt);
    box.setEditable(true);
    return box;
  }
  
  public static <T> ComboBox<T> comboBox(List<T> values)
  {
    ObservableList<T> list = FXCollections.observableList(values);
    ComboBox<T> box = new ComboBox<T>(list);
    box.setEditable(true);
    return box;
  }
//  
//  public static TextField autoComplete(String prompt, Function<String, String> autoComplete, int minimumCharacters)
//  {
//    TextField ret = Fx.prompt(prompt);
//    ret.add
//    return ret;
//  }
  
  
  public static Pane groupVertical(Node ... nodes)
  {
    return GridBuilder
        .create()
        .columns(1)
        .margin(5, 0, 0, 0)
        .add(nodes)
        .build();
  }
}
