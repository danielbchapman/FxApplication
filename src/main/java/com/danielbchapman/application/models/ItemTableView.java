package com.danielbchapman.application.models;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import com.danielbchapman.application.CSS;
import com.danielbchapman.application.IInternationalized;
import com.danielbchapman.application.functional.FunctionNoArgs;
import com.danielbchapman.application.functional.Procedure;
import com.danielbchapman.groups.Item;
import com.danielbchapman.groups.JSON;
import com.danielbchapman.international.MessageUtility;

public class ItemTableView extends TableView<Item> implements IInternationalized
{

  /**
   * <p>
   * Create a combo box editor that will automatically pull values from the columns in the group
   * as well as allowing the user to add a new value.
   * </p>
   * <p>
   * This method is designed to use a funcation call so that the data can be refreshed as needed.
   * </p>
   * @param column the column to assign this editor to.
   * @param list A list&lt;JSON&gt; of items to use for this combo box. 
   * @return a new instance of the editor factory
   * 
   */
  public static Callback<TableColumn<Item, JSON>, TableCell<Item, JSON>> getComboBoxEditor(final String column, final FunctionNoArgs<List<JSON>> items)
  {
    return new Callback<TableColumn<Item, JSON>, TableCell<Item, JSON>>()
    {

      @Override
      public TableCell<Item, JSON> call(TableColumn<Item, JSON> arg0)
      {
        List<JSON> set = items.call();

        ObservableList<String> list = FXCollections.observableArrayList();
        for (JSON j : set)
          list.add(j.getString());

        final JSONComboCell cell = new JSONComboCell(list);
        return cell;
      }
    };
  }

  private Type[] columnTypes;
  private ObservableList<Item> data;
  private com.danielbchapman.international.MessageUtility.Instance msg;
  private Callback<Item, Object> onCommit;

  private ItemTableView(List<Item> items, String[] columns, Type[] columnTypes, double[] columnMinimumSize, double[] columnSize, boolean editable, Callback<Item, Object> editCallback, Callback<Item, Object> clickCallback, Class<?> i1n8Mapping,
      HashMap<String, Callback<TableColumn<Item, JSON>, TableCell<Item, JSON>>> editors)
  {
    this.onCommit = editCallback;
    if (i1n8Mapping == null)
      msg = MessageUtility.getInstance(getClass());
    else
      msg = MessageUtility.getInstance(i1n8Mapping);

    data = items == null ? FXCollections.observableArrayList(new ArrayList<Item>()) : FXCollections.observableArrayList(items);
    setItems(data);

    if (columnTypes == null || columnTypes.length == 0)
    {
      this.columnTypes = new Type[columns.length];
      for (int i = 0; i < columns.length; i++)
        this.columnTypes[i] = Type.UNDEFINED;
    }
    else
    {
      if (columns.length != columnTypes.length)
        throw new IllegalArgumentException("Columns must match columnTypes in length: " + "You provided [columns, types] [" + columns.length + ", " + columnTypes.length + "]");

      this.columnTypes = columnTypes;
    }

    if (columnMinimumSize != null && columnMinimumSize.length != columns.length)
      throw new IllegalArgumentException("ColumnSize must match Columns in length: " + "You provided [columns, minimumSizes] [" + columns.length + ", " + columnMinimumSize.length + "]");

    if (columnSize != null && columnSize.length != columns.length)
      throw new IllegalArgumentException("Columns must match Sizes in length: " + "You provided [columns, sizes] [" + columns.length + ", " + columnSize.length + "]");

    for (int i = 0; i < columns.length; i++)
    {
      final String key = columns[i];
      final Type type = this.columnTypes[i];
      JSONTableColumn column = new JSONTableColumn(msg(key), this.columnTypes[i]); // Set Title

      if (columnMinimumSize != null)
        column.setMinWidth(columnMinimumSize[i]);

      if (columnSize != null)
        column.setPrefWidth(columnSize[i]);

      column.setCellValueFactory(new Callback<CellDataFeatures<Item, JSON>, ObservableValue<JSON>>()
      {
        public ObservableValue<JSON> call(CellDataFeatures<Item, JSON> callback)
        {
          JSON value = callback.getValue().getValue(key);
          if (value == null)
            value = JSON.UNDEFINED;
          return new SimpleObjectProperty<JSON>(value);
        }
      });

      final Callback<TableColumn<Item, JSON>, TableCell<Item, JSON>> factory = editors.get(key);
      if (factory != null)
        column.setCellFactory(factory);
      else
        column.setCellFactory(new Callback<TableColumn<Item, JSON>, TableCell<Item, JSON>>()
        {
          @Override
          public TableCell<Item, JSON> call(TableColumn<Item, JSON> c)
          {
            switch (type)
            {
            case BOOLEAN_NO_EDIT:
            case CURRENCY_NO_EDIT:
            case DATE_LONG_NO_EDIT:
            case DECIMAL_NO_EDIT:
            case DATE_NO_EDIT:
            case INTEGER_NO_EDIT:
            case STRING_NO_EDIT:
              return new JSONStringCellDisplayOnly();
            case BOOLEAN:
              return new JSONBooleanCell();
            case INTEGER:
              return new JSONIntegerCell();
            case CURRENCY:
            case DECIMAL:
              return new JSONDecimalCell();
            case DATE_LONG:
            case DATE:
              return new JSONDateCell();
            case STRING:
            default:
              return new JSONStringCell();
            }
          }
        });

      column.setEditable(editable);

      column.setOnEditCommit(new EventHandler<CellEditEvent<Item, JSON>>()
      {

        @Override
        public void handle(CellEditEvent<Item, JSON> evt)
        {
          if (evt.getOldValue() != evt.getNewValue())
          {
            System.out.println("Updated value " + evt.getNewValue() + " for item " + evt.getRowValue().getId() + " from " + evt.getOldValue());
            evt.getRowValue().setValueIgnore(key, evt.getNewValue());
          }

        }
      });
      getColumns().add(column);
    }

    setEditable(editable);
  }

  /**
   * @return <Return Description>  
   */
  public ObservableList<Item> getData()
  {
    return data;
  }

  /*
   * (non-Javadoc)
   * @see com.danielbchapman.application.IInternationalized#msg(java.lang.String)
   */
  @Override
  public String msg(String key)
  {
    return msg.get(key);
  }

  /*
   * (non-Javadoc)
   * @see com.danielbchapman.application.IInternationalized#msg(java.lang.String, java.lang.Object[])
   */
  @Override
  public String msg(String key, Object... params)
  {
    return msg.get(key, params);
  }

  public void executeCallback(Item item)
  {
    if (onCommit != null)
      onCommit.call(item);
  }

  public void setItems(List<Item> items)
  {
    super.setItems(FXCollections.observableList(items));
  }

  public void setOnEditCallback(Callback<Item, Object> callback)
  {
    this.onCommit = callback;
  }

  /**
   * Create a control column of N controls as provided by the data. If the data length is
   * zero an exception will be thrown.
   * @param data <Return Description>  
   * 
   */
  // FIXME Java Doc Needed
  public void addControlColumn(final ControlData... data)
  {
    if (data == null || data.length < 1)
      throw new IllegalArgumentException("Control columns must have at least one control: " + data);

    TableColumn<Item, Procedure<Item>> column = new TableColumn<>();

    column.setCellFactory(new Callback<TableColumn<Item, Procedure<Item>>, TableCell<Item, Procedure<Item>>>()
    {

      @Override
      public ControlCell call(TableColumn<Item, Procedure<Item>> arg0)
      {
        return new ControlCell(data);
      }
    });

    getColumns().add(column);
  }

  /**
   * Add a control to the control column.
   *
   * @param action the action to perform when this button is clicked.
   * @param label The text to label the button 
   */
  public void addControlColumn(final Procedure<Item> action, final String text)
  {
    addControlColumn(new ControlData(action, text));
  }

  /**
   * @return A new instance of the ItemTableView.Builder  
   * 
   */
  public static Builder builder()
  {
    return Builder.create();
  }

  /**
   * <Class Definitions>
   *
   ***************************************************************************
   * @author Daniel B. Chapman 
   * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
   * @since Oct 23, 2012
   * @version 2 Development
   * @link http://www.lightassistant.com
   ***************************************************************************
   */

  public static class ControlData
  {
    private Procedure<Item> action;
    private String label;

    /**
     * @param action the action to perform when this button is clicked. The Item will be passed to 
     * the function to act upon. Should the function return <tt>Boolean.FALSE</tt> the table will not
     * be refreshed. Should it return <tt>Boolean.TRUE</tt> the table will be refreshed. This is effectively
     * a toggle for canceling the action.
     * @param label The text to label the button
     */
    public ControlData(Procedure<Item> action, String label)
    {
      this.action = action;
      this.label = label;
    }
  }

  private static class ControlCell extends TableCell<Item, Procedure<Item>>
  {
    public ControlCell(final ControlData... data)
    {
      HBox box = new HBox();
      for (int i = 0; i < data.length; i++)
      {
        Button button = new Button(data[i].label);
        final int index = i;
        button.setOnAction(new EventHandler<ActionEvent>()
        {

          @Override
          public void handle(ActionEvent arg0)
          {
            getTableView().getSelectionModel().select(getIndex());
            Item item = getTableView().getSelectionModel().getSelectedItem();
            data[index].action.call(item);
          }
        });
        box.getChildren().add(button);
      }
      setGraphic(box);
    }

    @Override
    public void startEdit()
    {
      return;
    }

    public void cancelEdit()
    {
      return;
    }

    public void updateItem(Procedure<Item> action, boolean empty)
    {
      return;
    }
  }

  /**
   * A simple implementation that handles rendering for the JSON type. It assumes
   * that the custom renderer won't work and handles it with some basic constraints.
   * 
   * You must handle the listeners on the editor to force changes (it isn't magic).
   * Generally this will take the form:
   * <p>
   * <code>
   * <pre>
   *     {literal @}Override
   *     public TextField getEditor()
   *     {
   *       final TextField editor = new TextField();
   *       editor.setMinWidth(getWidth() - getGraphicTextGap() * 2);
   *       editor.setOnKeyReleased(new EventHandler&lt;KeyEvent&gt;(){
   * 
   *         {literal @}Override
   *         public void handle(KeyEvent evt)
   *         {
   *           if(evt.getCode() == KeyCode.ENTER)
   *             doCommit(editor);
   *           else if (evt.getCode() == KeyCode.ESCAPE)
   *             cancelEdit();
   *         }
   *       });
   *       
   *       editor.focusedProperty().addListener(new ChangeListener&lt;Boolean&gt;(){
   * 
   *         {literal @}Override
   *         public void changed(ObservableValue&lt;? extends Boolean&gt; obsrv, Boolean old, Boolean nw)
   *         {
   *           if(!obsrv.getValue())
   *             doCommit(editor);
   *         }
   *       });
   *       
   *       return editor;
   *     }
   * </pre>
   * </code>
   * </p>
   ***************************************************************************
   * @author Daniel B. Chapman 
   * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
   * @since Aug 25, 2012
   * @version 2 Development
   * @link http://www.lightassistant.com
   ***************************************************************************
   */
  private static abstract class JSONCell<E extends Control, R extends Control> extends TableCell<Item, JSON>
  {
    E editor;
    R renderer;

    @Override
    public void cancelEdit()
    {
      dirty();
      if (renderer == null)
        renderer = getRenderer();

      super.cancelEdit();
      render(renderer, getJsonData());
      setGraphic(renderer);
    }

    @Override
    public void startEdit()
    {
      dirty();
      Platform.runLater(new Runnable()
      {

        public void run()
        {
          JSONCell.super.startEdit();

          if (editor == null)
            editor = getEditor();

          edit(editor, getJsonData());

          setGraphic(editor);
          editor.requestFocus();

          if (editor instanceof TextInputControl)
            ((TextInputControl) editor).selectAll();
        }
      });
    }

    protected void dirty()
    {
      if (isDirty())
      {
        editor = null;
        renderer = null;
      }
    }

    @Override
    public void updateItem(JSON json, boolean empty)
    {
      dirty();
      super.updateItem(json, empty);

      if (empty)
      {
        if (renderer == null)
          renderer = getRenderer();

        render(renderer, json);
        setGraphic(renderer);
      }
      else
      {
        if (isEditing())
        {
          if (editor == null)
            editor = getEditor();

          edit(editor, getJsonData());
          setGraphic(editor);
        }
        else
        {
          if (renderer == null)
            renderer = getRenderer();
          render(renderer, json);
          setGraphic(renderer);
        }
      }
    }

    /**
     * Return an instance of this editor.
     * The class should maintain a reference to
     * this object.
     * @return The editor to return  
     * 
     */
    public abstract E getEditor();

    /**
     * Return an instance of this renderer.
     * The class should maintain a reference to
     * this object.
     * @return The renderer to return  
     * 
     */
    public abstract R getRenderer();

    /**
     * @param control the control to render
     * @param value the value to render to the control
     */
    public abstract void render(R control, JSON value);

    public abstract void edit(E control, JSON value);

    public abstract JSON getEditorValue(E editor);

    protected void doCommit(E editor)
    {
      JSON value = getEditorValue(editor);
      commitEdit(value);
      ((ItemTableView) getTableView()).executeCallback(getTableView().getSelectionModel().getSelectedItem());
    }

    /**
     * return true if the editors should be nulled
     * and redrawn (for instance if this is a multi-render
     * component)
     * @return true if redraw, otherwise false  
     * 
     */
    public abstract boolean isDirty();

    protected JSON getJsonData()
    {
      return getItem() == null ? JSON.UNDEFINED : getItem();
    }
  }

  private static class JSONStringCell extends JSONCell<TextField, Label>
  {
    public JSONStringCell()
    {
    }

    // private void provideEditor(JSONType type)
    // {
    //
    // control = new TextField(getValue().toString());
    // control.setMinWidth(getWidth() - getGraphicTextGap() * 2);
    // control.setOnKeyReleased(new EventHandler<KeyEvent>(){
    //
    // @Override
    // public void handle(KeyEvent evt)
    // {
    // if(evt.getCode() == KeyCode.ENTER)
    // doCommit();
    // else if (evt.getCode() == KeyCode.ESCAPE)
    // cancelEdit();
    // }
    // });
    //
    // control.focusedProperty().addListener(new ChangeListener<Boolean>(){
    //
    // @Override
    // public void changed(ObservableValue<? extends Boolean> obsrv, Boolean old, Boolean nw)
    // {
    // if(!obsrv.getValue())
    // doCommit();
    // }
    // });
    //
    // switch(type)
    // {
    // case DATE:
    // case BOOLEAN:
    // {
    // break;
    // }
    // case NUMBER:
    // case NULL:
    // case UNDEFINED:
    // case STRING:
    // default:
    // {
    //
    // break;
    // }
    // }
    // }

    @Override
    public TextField getEditor()
    {
      final TextField editor = new TextField();
      editor.setMinWidth(getWidth() - getGraphicTextGap() * 2);
      editor.setOnKeyReleased(new EventHandler<KeyEvent>()
      {

        @Override
        public void handle(KeyEvent evt)
        {
          if (evt.getCode() == KeyCode.ENTER)
            doCommit(editor);
          else
            if (evt.getCode() == KeyCode.ESCAPE)
              cancelEdit();
        }
      });

      editor.focusedProperty().addListener(new ChangeListener<Boolean>()
      {

        @Override
        public void changed(ObservableValue<? extends Boolean> obsrv, Boolean old, Boolean nw)
        {
          // FIXME Focus is unreliable.
          if (!obsrv.getValue() && old)
            doCommit(editor);
        }
      });

      return editor;
    }

    @Override
    public Label getRenderer()
    {
      Label label = new Label();
      label.setMinWidth(getWidth() - getGraphicTextGap() * 2);
      return label;
    }

    @Override
    public void render(Label control, JSON value)
    {
      if (JSON.empty(value))
      {
        control.setText("");
        return;
      }

      control.setText(value.getString());
    }

    @Override
    public void edit(TextField control, JSON value)
    {
      if (JSON.empty(value))
        control.setText("");
      else
      {
        control.setText(value.getString());
      }
    }

    @Override
    public JSON getEditorValue(TextField editor)
    {
      return JSON.wrap(editor.getText());
    }

    @Override
    public boolean isDirty()
    {
      return false;
    }
  }

  private class JSONBooleanCell extends JSONCell<CheckBox, CheckBox>
  {

    @Override
    public CheckBox getEditor()
    {
      final CheckBox editor = new CheckBox();

      editor.setOnKeyReleased(new EventHandler<KeyEvent>()
      {

        @Override
        public void handle(KeyEvent evt)
        {
          if (evt.getCode() == KeyCode.ESCAPE)
            cancelEdit();
        }
      });

      editor.focusedProperty().addListener(new ChangeListener<Boolean>()
      {

        @Override
        public void changed(ObservableValue<? extends Boolean> obsrv, Boolean old, Boolean nw)
        {
          if (!obsrv.getValue() && old)
            doCommit(editor);
        }
      });

      return editor;
    }

    @Override
    public CheckBox getRenderer()
    {
      CheckBox render = new CheckBox();
      render.setDisable(true);
      return render;
    }

    @Override
    public void render(CheckBox control, JSON value)
    {
      if (JSON.NULL.equals(value))
      {
        control.setSelected(false);
        return;
      }
      control.setSelected(value.getBoolean());
    }

    @Override
    public void edit(CheckBox control, JSON value)
    {
      control.setSelected(value.getBoolean());
      control.setText(msg("bool", getJsonData().getBoolean()));
    }

    @Override
    public JSON getEditorValue(CheckBox editor)
    {
      return JSON.wrap(editor.isSelected());
    }

    @Override
    public boolean isDirty()
    {
      return false;
    }

  }

  @SuppressWarnings("unused")
  private class JSONTableColumn extends TableColumn<Item, JSON>
  {
    private Type type;

    public Type getType()
    {
      return type;
    }

    public void setType(Type type)
    {
      this.type = type;
    }

    public JSONTableColumn(String title, Type type)
    {
      super(title);
      this.type = type;
    }
  }

  @SuppressWarnings("unused")
  private static class JSONComboCell extends JSONCell<ComboBox<String>, Label>
  {
    //private JSONType type = JSONType.UNDEFINED;
    private ObservableList<String> items;

    public JSONComboCell()
    {
    }

    public JSONComboCell(ObservableList<String> items)
    {
      this.items = items;
    }

    @Override
    public ComboBox<String> getEditor()
    {
      final ComboBox<String> editor = new ComboBox<>();

      if (items != null)
      {
        editor.setItems(items);
        System.out.print("Editor using items....");
        for (String j : editor.getItems())
          System.out.print(j + ", ");

        System.out.println("");
      }

      editor.setEditable(true);
      editor.setMinWidth(getWidth() - getGraphicTextGap() * 2);
      editor.setOnKeyReleased(new EventHandler<KeyEvent>()
      {

        @Override
        public void handle(KeyEvent evt)
        {
          if (evt.getCode() == KeyCode.ENTER)
            doCommit(editor);
          else
            if (evt.getCode() == KeyCode.ESCAPE)
              cancelEdit();
        }
      });

      editor.focusedProperty().addListener(new ChangeListener<Boolean>()
      {

        @Override
        public void changed(ObservableValue<? extends Boolean> obsrv, Boolean old, Boolean nw)
        {// OnSet -> false/true
          System.out.println("Combo Box [" + obsrv + "] | old/new [" + old + "/" + nw + "]" + " | Editor " + editor.getValue());
          if (!obsrv.getValue() && old)
            doCommit(editor);
        }
      });

      // editor.onActionProperty().addListener(new ChangeListener<Boolean>(){
      //
      // @Override
      // public void changed(ObservableValue<? extends Boolean> obsrv, Boolean old, Boolean nw)
      // {
      // if(!obsrv.getValue())
      // doCommit(editor);
      // }});

      return editor;
    }

    @Override
    public Label getRenderer()
    {
      Label label = new Label();
      label.setMinWidth(getWidth() - getGraphicTextGap() * 2);
      return label;
    }

    @Override
    public void render(Label control, JSON value)
    {
      if (value == null || value.isNullOrUndefined())
        control.setText("");
      else
        control.setText(value.getString());
    }

    @Override
    public void edit(ComboBox<String> control, JSON value)
    {
      if (value == null)
        value = JSON.NULL;

      // FIXME add converters
      control.getSelectionModel().select(value.getString());
    }

    @Override
    public JSON getEditorValue(ComboBox<String> editor)
    {
      // FIXME add converters
      JSON ret = JSON.wrap(editor.getValue());
      return ret == null ? JSON.NULL : ret;
    }

    @Override
    public boolean isDirty()
    {
      return false;
    }
  }

  private class JSONDecimalCell extends JSONStringCell
  {
    protected String pattern = "^[0-9,.\\-]";
    protected String format = ",###.###";

    @Override
    public TextField getEditor()
    {
      TextField editor = super.getEditor();

      editor.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
      {

        @Override
        public void handle(KeyEvent evt)
        {
          if (!evt.getCharacter().matches(pattern))
            evt.consume();
        }
      });

      return editor;
    }

    @Override
    public void render(Label label, JSON value)
    {
      if (JSON.NULL.equals(value))
      {
        label.setText("");
        return;
      }

      DecimalFormat df = new DecimalFormat(format);
      Double num = value.getNumber();
      if (num == null)
        label.setText("");
      else
      {
        String out = df.format(value.getNumber());
        label.setText(out);
      }
    }

    public void edit(TextField text, JSON value)
    {
      if (JSON.NULL.equals(value))
      {
        text.setPromptText(msg("promptNumber"));
        return;
      }

      DecimalFormat df = new DecimalFormat(format);
      Double num = value.getNumber();
      if (num == null)
        text.setText("");
      else
      {
        text.setText(df.format(value.getNumber()));
        text.selectAll();
      }
    }

    /**
     * An override of the commit that formats for a number, and on failure highlights the cell.
     * @see com.danielbchapman.application.models.ItemTableView.JSONCell#doCommit(javafx.scene.control.Control)
     */
    public JSON getEditorValue(TextField editor)
    {
      String value = editor.getText();
      if (value == null || value.trim().isEmpty())
        return JSON.NULL;

      try
      {
        DecimalFormat df = new DecimalFormat(format);
        Number number = df.parse(value);
        return JSON.wrap(number);
      }
      catch (ParseException e)
      {
        editor.getStyleClass().add(CSS.FIELD_ERROR);
        return getJsonData(); // Return an unchanged value...
      }
    }

  }

  private class JSONIntegerCell extends JSONDecimalCell
  {
    public JSONIntegerCell()
    {
      format = ",###";
    }
  }

  private class JSONDateCell extends JSONStringCell
  {
    protected String pattern = "^[0-9\\/]";
    protected String format = "MM/dd/yy";

    @Override
    public TextField getEditor()
    {
      TextField editor = super.getEditor();

      editor.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
      {

        @Override
        public void handle(KeyEvent evt)
        {
          if (!evt.getCharacter().matches(pattern))
            evt.consume();
        }
      });

      return editor;
    }

    @Override
    public void render(Label label, JSON value)
    {
      if (JSON.NULL.equals(value))
      {
        label.setText("");
        return;
      }

      SimpleDateFormat df = new SimpleDateFormat(format);
      String out = df.format(value.getDate());
      label.setText(out);
    }

    public void edit(TextField text, JSON value)
    {
      if (JSON.NULL.equals(value))
      {
        text.setPromptText(msg("promptNumber"));
        return;
      }

      SimpleDateFormat df = new SimpleDateFormat(format);
      text.setText(df.format(value.getDate()));
      text.selectAll();
    }

    /**
     * An override of the commit that formats for a number, and on failure highlights the cell.
     * @see com.danielbchapman.application.models.ItemTableView.JSONCell#doCommit(javafx.scene.control.Control)
     */
    public JSON getEditorValue(TextField editor)
    {
      String value = editor.getText();
      if (value == null || value.trim().isEmpty())
        return JSON.NULL;

      try
      {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return JSON.wrap(df.parse(value));
      }
      catch (ParseException e)
      {
        editor.getStyleClass().add(CSS.FIELD_ERROR);
        return getJsonData(); // Return an unchanged value...
      }
    }
  }

  private static class JSONStringCellDisplayOnly extends JSONStringCell
  {
    public JSONStringCellDisplayOnly()
    {
    }

    @Override
    public TextField getEditor()
    {
      final TextField editor = super.getEditor();
      editor.setEditable(false);
      return editor;
    }

  }

  //
  // private class CallbackComboJSONEditor extends JSONCell<ComboBox<String>, Label>
  // {
  // private ComboBox<String> editor;
  // private Callback<JSON, Object> onClick;
  // private Callback<JSON, Object> onChange;
  //
  // @Override
  // public ComboBox<String> getEditor()
  // {
  // }
  //
  // @Override
  // public Label getRenderer()
  // {
  // //TODO Auto Generated Sub
  // throw new RuntimeException("Not Implemented...");
  //
  // }
  //
  // @Override
  // public void render(Label control, JSON value)
  // {
  // //TODO Auto Generated Sub
  // throw new RuntimeException("Not Implemented...");
  //
  // }
  //
  // @Override
  // public void edit(ComboBox<String> control, JSON value)
  // {
  // //TODO Auto Generated Sub
  // throw new RuntimeException("Not Implemented...");
  //
  // }
  //
  // @Override
  // public JSON getEditorValue(ComboBox<String> editor)
  // {
  // //TODO Auto Generated Sub
  // throw new RuntimeException("Not Implemented...");
  //
  // }
  //
  // @Override
  // public boolean isDirty()
  // {
  // //TODO Auto Generated Sub
  // throw new RuntimeException("Not Implemented...");
  //
  // }
  //
  // }
  /**
   * A simple builder to provide fluent method calls 
   * to the constructor (Which is very long and has arrays)
   *
   ***************************************************************************
   * @author Daniel B. Chapman 
   * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
   * @since Aug 23, 2012
   * @version 2 Development
   * @link http://www.lightassistant.com
   ***************************************************************************
   */
  public static class Builder
  {
    private List<Item> items;
    private String[] columns;

    private HashMap<String, Callback<TableColumn<Item, JSON>, TableCell<Item, JSON>>> editors = new HashMap<>();
    private Type[] types;
    private double[] minimum;
    private double[] preferred;
    private boolean editable;
    private Class<?> internationalize;
    private Callback<Item, Object> editCallback;
    private Callback<Item, Object> clickCallback;

    public Builder editCallback(Callback<Item, Object> callback)
    {
      this.editCallback = callback;
      return this;
    }

    public Builder clickCallback(Callback<Item, Object> callback)
    {
      this.clickCallback = callback;
      return this;
    }

    /**
     * Provide a class to pass to the MessageUtility.Instance class for 
     * mapping these values. If null the base class will be used.
     * @param mapping the class to map by name
     * @return fluent instance of this Builder.
     */
    public Builder i1n8(Class<?> mapping)
    {
      if (mapping != null)
        this.internationalize = mapping;
      return this;
    }

    public ItemTableView build()
    {
      return new ItemTableView(items, columns, types, minimum, preferred, editable, editCallback, clickCallback, internationalize, editors);
    }

    /**
     * Assign a list of editors to this TableCell
     * @param editors
     * @return <Return Description>  
     * 
     */
    public Builder editor(String column, Callback<TableColumn<Item, JSON>, TableCell<Item, JSON>> factory)
    {
      editors.put(column, factory);
      return this;
    }

    public Builder editable(boolean editable)
    {
      this.editable = editable;
      return this;
    }

    public Builder columns(String... columns)
    {
      this.columns = columns;
      return this;
    }

    public Builder types(Type... types)
    {
      this.types = types;
      return this;
    }

    public Builder items(List<Item> items)
    {
      this.items = items;
      return this;
    }

    public Builder items(Item... items)
    {
      this.items = new ArrayList<Item>();
      for (Item i : items)
        this.items.add(i);

      return this;
    }

    public Builder columnMinimums(double... minimum)
    {
      this.minimum = minimum;
      return this;
    }

    public Builder columnSize(double... preferred)
    {
      this.preferred = preferred;
      return this;
    }

    public static Builder create()
    {
      return new Builder();
    }

  }

  public enum Type
  {
    UNDEFINED, STRING, DECIMAL, INTEGER, CURRENCY, DATE, DATE_LONG, BOOLEAN, CUSTOM, UNDEFINED_NO_EDIT, STRING_NO_EDIT, DECIMAL_NO_EDIT, INTEGER_NO_EDIT, CURRENCY_NO_EDIT, DATE_NO_EDIT, DATE_LONG_NO_EDIT, BOOLEAN_NO_EDIT, CUSTOM_NO_EDIT
  }
}
