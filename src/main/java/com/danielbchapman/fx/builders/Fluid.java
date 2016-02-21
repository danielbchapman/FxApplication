package com.danielbchapman.fx.builders;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class Fluid
{
  
  public static Form form()
  {
    return new Form();
  }
  
  public static class Form
  {
    private TilePane currentRow;
    ArrayList<Node> nodes = new ArrayList<Node>();
    private Insets padding;
    private Insets margin;
    private int columns = -1;
    
    public Form columns(int i)
    {
      this.columns = i < 1 ? -1 : i;
      return this;
    }
    
    public Form padding(double top, double right, double bottom, double left)
    {
      this.padding = new Insets(top, right, bottom, left);
      return this;
    }
    
    public Form margin(double top, double right, double bottom, double left)
    {
      this.margin = new Insets(top, right, bottom, left);
      return this;
    }
    
    public Form margin(Insets insets)
    {
      this.margin = insets;
      return this;
    }
    
    public Form padding(Insets insets)
    {
      this.padding = insets;
      return this;
    }
    
    public VBox build()
    {
      VBox build = new VBox();
      if(padding != null)
        build.setPadding(padding);

      build.getChildren().addAll(nodes);
      
      for(Node n : nodes)
        if(margin != null)
          VBox.setMargin(n, margin);
      
      return build;
    }
    
    public Form row(Node ... nodes)
    {
      endRow();
      group(nodes);
      return this;
    }
    
    public Form row()
    {
      if(currentRow != null)
        endRow();
        
      currentRow = new TilePane();
      currentRow.setPrefColumns(col());
      return this;
    }
    
    public Form endRow()
    {
      if(currentRow != null)
        nodes.add(currentRow);
      
      currentRow = null;
      return this;
    }
    
    public Form group(Node ... n)
    {
      TilePane group = new TilePane();
      group.setPrefColumns(col());
      group.getChildren().addAll(n);
      push(group);
      return this;
    }
    
    public Form add(Node ... n)
    {
      push(n);
      return this;
    } 
    
    public Form label(String text)
    {
      push(Fx.label(text));
      return this;
    }
    
    public Form hint(String text)
    {
      push(Fx.hint(text));
      return this;
    }
    
    public Form legend(String text)
    {
      push(Fx.legend(text));
      return this;
    }
    
    protected void push(Node ... nodes)
    {
      if(nodes != null)
        if(currentRow != null)
          currentRow.getChildren().addAll(nodes);
        else
          for(Node n : nodes)
            this.nodes.add(n);
    }
    
    private int col()
    {
      return 1 > columns ? 1 : columns;
    }
  }
}
