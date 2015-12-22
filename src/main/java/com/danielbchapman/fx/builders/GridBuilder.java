package com.danielbchapman.fx.builders;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GridBuilder
{
  private int columns = 2;
  private Insets padding;
  private Insets margin;
  private ArrayList<Node> elements = new ArrayList<>();
  
  public static GridBuilder create()
  {
    return new GridBuilder();
  }
  
  public GridBuilder padding(double top, double right, double bottom, double left)
  {
    this.padding = new Insets(top, right, bottom, left);
    return this;
  }
  
  public GridBuilder margin(double top, double right, double bottom, double left)
  {
    this.margin = new Insets(top, right, bottom, left);
    return this;
  }
  
  public GridBuilder padding(Insets insets)
  {
    this.padding = insets;
    return this;
  }
  
  public GridBuilder columns(int i)
  {
    this.columns = i;
    return this;
  }
  
  public GridBuilder add(Node n)
  {
    this.elements.add(n);
    return this;
  }
  
  public GridBuilder add(Node ... nodes)
  {
    for(Node n : nodes)
      elements.add(n);
    
    return this;
  }
  
  public GridBuilder label(String string)
  {
    add(new Label(string));
    return this;
  }
  
  public GridBuilder spacer()
  {
    add(new Group());
    return this;
  }
  
  public GridBuilder hint(String text)
  {
    add(Fx.hint(text));
    return this;
  }
  
  public GridPane build()
  {
    GridPane grid = new GridPane();
    if(padding != null)
      grid.setPadding(padding);
    
    int row = 0;
    int column = 0;
    if(columns < 1)
      columns = elements.size();
    for(int i = 0; i < elements.size(); i++)
    {
      if(i % columns == 0)
        row++;
      
      if(++column >= columns + 1)
        column = 1;
      
//      System.out.println("Adding at " + column + ", " + row + " node: " + elements.get(i));
      grid.add(elements.get(i), column, row);
      
      if(margin != null)
        GridPane.setMargin(elements.get(i), margin);
    }

    return grid;
  }
  
}
