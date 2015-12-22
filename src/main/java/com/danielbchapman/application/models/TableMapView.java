package com.danielbchapman.application.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class TableMapView<S> extends TableView<S>
{
  List<Map<String, S>> data;
  ArrayList<TableColumn<Map<String, S>, S>> columnList = new ArrayList<>();

  public TableMapView(final List<Map<String, S>> data, final String... columns)
  {
    this.data = data;

    for (final String key : columns)
    {
      final TableColumn<Map<String, S>, S> column = new TableColumn<>();
      column.setCellValueFactory(new Callback<CellDataFeatures<Map<String, S>, S>, ObservableValue<S>>()
      {
        @Override
        public ObservableValue<S> call(CellDataFeatures<Map<String, S>, S> cellDataFeatures)
        {
          return new ObjectInMapProperty(cellDataFeatures.getValue(), key);
        }

      });

      columnList.add(column);
    }

  }

  public class ObjectInMapProperty extends ObjectPropertyBase<S>
  {
    Map<String, S> bean;
    String key;

    public ObjectInMapProperty(Map<String, S> bean, String key)
    {
      this.bean = bean;
      this.key = key;
    }

    @Override
    public Map<String, S> getBean()
    {
      return bean;
    }

    @Override
    public String getName()
    {
      return key;
    }

    @Override
    public S get()
    {
      return bean.get(key);
    }

    @Override
    public void set(S value)
    {
      bean.put(key, value);
    }
  }

}
