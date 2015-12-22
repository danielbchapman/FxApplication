package com.danielbchapman.application;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.danielbchapman.application.models.SelectItem;

/**
 * A IConverter is a simple interface that takes a type T and converts the value
 * to a String. In general this is to provide an easy consistent way to work from 
 * and render to user readable content. 
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Nov 19, 2011
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public interface IConverter<T>
{
  /**
   * Return the value of T as a string
   * @param <T> the Type of this converter
   * @param value the value to parse
   * @return a string representation of this value
   * 
   */
  public String getAsString(T value);

  /**
   * Return a SelectItem supporting the generic type for this
   * component. This allows the framework to quickly generate ComboBoxes 
   * for the component that make sense
   * @param value the value to wrap
   * @return a SelectItem that supports the name/value pairing.
   */
  public SelectItem<T> getSelectItem(T value);

  /**
   * @return a TableCellEditor for this component<br /> <b>null</b> if not supported or 
   * needed and #toString() will suffice.  
   * 
   */
  public TableCellEditor getTableCellEditor();

  /**
   * @return  a TableCellRenderer for this component<br /> <b>null</b> if not supported or 
   * needed and #toString() will suffice.  
   * 
   */
  public TableCellRenderer getTableCellRenderer();

  /**
   * @param t the object [T] to process
   * @return a tool tip if it should be displayed (notes/history) etc... <b>null</b> if 
   * this is not applicable.  
   */
  public String getToolTip(T value);

  /**
   * 
   * @return the class this converter manages <em> (i.e. A IConverter&lt;T&gt; would return T.class)</em> 
   */
  public Class<T> getType();
}
