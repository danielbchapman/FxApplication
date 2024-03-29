package com.danielbchapman.application;

import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;

import javax.swing.JFrame;

import com.danielbchapman.application.dialogs.ConfirmaionDialog;
import com.danielbchapman.application.functional.Procedure;
import com.danielbchapman.international.MessageUtility;
import com.danielbchapman.international.MessageUtility.Instance;
import com.danielbchapman.logging.Log;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
@SuppressWarnings("deprecation")
public abstract class Application extends javafx.application.Application implements IInternationalized
{
  public class WindowSettings
  {
	  public double minSizeX;
	  public double minSizeY;
	  public double resolutionX;
	  public double resolutionY;
  }
  
  protected class UiDialog extends StackPane
  {
    private FxDialog dialog;
    private Timeline hideTimeline;
    
    public UiDialog()
    {
    }
    
    public void setFxDialog(FxDialog dialog)
    {
      this.dialog = dialog;
      dialog.setReference(this);
      dialog.init();
      
      FxDialog.Type type = dialog.getType();
      
      if(type == null)
        throw new RuntimeException("Dialogs must have a type " + dialog.getClass().getName() + " is in error");
      
      final Pane content = dialog.getContent();
      content.getStyleClass().add(CSS.DIALOG_CONTENT);
      switch(type)
      {
        case BOTTOM:
        {
          
        }
        case FULL_SCREEN:
        {
          
          
        }
        case LEFT:
        {
          setAlignment(Pos.TOP_LEFT);
          
          HBox hBox = new HBox();
          hBox.getStyleClass().add(CSS.DIALOG);
          hBox.setFillHeight(true);
          hBox.setPrefWidth(content.getPrefWidth());
          hBox.setMaxWidth(dialogs.getWidth() / 2.5);
          hBox.setAlignment(Pos.TOP_LEFT);
          hBox.setStyle("-fx-background-color: green;");
          
          VBox vBox = new VBox();
          vBox.setPadding(new Insets(10));
          vBox.setFillWidth(true);
          vBox.setAlignment(Pos.CENTER);
          vBox.getChildren().add(content);
          
          hBox.getChildren().add(vBox);
          
          HBox.setHgrow(content, Priority.NEVER);
          VBox.setVgrow(hBox, Priority.NEVER);
          
          getChildren().add(hBox);
          break;
        }
        case RIGHT:
        {
          setAlignment(Pos.TOP_RIGHT);
          
          HBox hBox = new HBox();
          hBox.getStyleClass().add(CSS.DIALOG);
          hBox.setFillHeight(true);
          hBox.setPrefWidth(content.getPrefWidth());
          hBox.setMaxWidth(dialogs.getWidth() / 2.5);
          hBox.setAlignment(Pos.TOP_RIGHT);
          hBox.setStyle("-fx-background-color: green;");
          
          VBox vBox = new VBox();
          vBox.setPadding(new Insets(10));
          vBox.setFillWidth(true);
          vBox.setAlignment(Pos.CENTER);
          vBox.getChildren().add(content);
          
          hBox.getChildren().add(vBox);
          
          HBox.setHgrow(content, Priority.NEVER);
          VBox.setVgrow(hBox, Priority.NEVER);
          
          getChildren().add(hBox);
          break;
        }
        case TOP:
        {
          setAlignment(Pos.TOP_CENTER);
          
          VBox vBox = new VBox();
          vBox.getStyleClass().add(CSS.DIALOG);
          vBox.setPadding(new Insets(10));
          vBox.setPrefWidth(dialogs.getWidth());
          vBox.setPrefHeight(dialogs.getHeight() / 2);
          vBox.setFillWidth(true);
          vBox.getChildren().add(content);
          vBox.setAlignment(Pos.TOP_CENTER);
          
          HBox hBox = new HBox();
          hBox.setFillHeight(true);
          hBox.setAlignment(Pos.CENTER);
          
          hBox.getChildren().add(content);
          vBox.getChildren().add(hBox);
          
          HBox.setHgrow(content, Priority.NEVER);
          VBox.setVgrow(hBox, Priority.ALWAYS);
          
          getChildren().add(vBox);
          break;
        }
        default: //Center 
        {
          setAlignment(Pos.CENTER);
          
          VBox vBox = new VBox();
          vBox.getStyleClass().add(CSS.DIALOG);
          vBox.setPadding(new Insets(10));
          vBox.setPrefWidth(dialogs.getWidth());
          vBox.setPrefHeight(dialogs.getHeight() / 2);
          vBox.setFillWidth(true);
          vBox.getChildren().add(content);
          vBox.setAlignment(Pos.CENTER);
          
          HBox hBox = new HBox();
          hBox.setFillHeight(true);
          hBox.setAlignment(Pos.CENTER);
          
          hBox.getChildren().add(content);
          vBox.getChildren().add(hBox);
          
          HBox.setHgrow(content, Priority.NEVER);
          VBox.setVgrow(hBox, Priority.ALWAYS);
          
          getChildren().add(vBox);
          setMaxHeight(dialogs.getHeight() / 2.2);
          break;
        }
      }
      
      return;
    }

    protected FxDialog getDialog()
    {
      return dialog;
    }

    protected Timeline getHideTimeline()
    {
      return hideTimeline;
    }

    protected void setDialog(FxDialog dialog)
    {
      this.dialog = dialog;
    }

    protected void setHideTimeline(Timeline hideTimeline)
    {
      this.hideTimeline = hideTimeline;
    }
  }
  
  public final static String BS_CONTENT = "Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.";
  public final static String FILE_LAST_DIRECTORY = "lastDirectory";
  public final static String LOCATION_X = "locationX";
  public final static String LOCATION_Y = "locationY";

  public final static String RESOLUTION_X = "resolutionX";

  public final static String RESOLUTION_Y = "resolutionY";
  private static Application applicationInstance;
  
  public static Application getCurrentInstance()
  {
    return applicationInstance;
  }
  /**
   * <p>
   * This is the current instance of the application from the
   * last "start" call. This provides utility references for listeners and
   * allows them to find a reference to fire action listeners and other
   * methods. 
   * </p>
   */
//  public static Application APPLICATION_INSTANCE;
  
  /* Application actions */
  public final ApplicationAction exitApplication = createAction("applicationExitAction", "applicationExitActionDetail", new EventHandler<ActionEvent>(){

    @Override
    public void handle(ActionEvent arg0)
    {
      showConfirmationYesNo(msg("exitApplicationConfirm"), msg("exitApplicationConfirmDetail"), new Procedure<Boolean>(){

        @Override
        public void call(Boolean bool)
        {
          if(bool)
            shutdownAndClean();
        }

      });     
    }
  });
  // Application Variables
  private Module activeModule;
  // Resource Managements
  private WeakHashMap<Class<?>, IConverter<?>> converterMap = new WeakHashMap<Class<?>, IConverter<?>>();
  private WeakHashMap<Class<? extends IConverter<?>>, IConverter<?>> converters = new WeakHashMap<Class<? extends IConverter<?>>, IConverter<?>>();
  private StackPane critical;
  private StackPane dialogs;
  
  /**
   * This boolean tracks to see if the next call should flag all modules as dirty
   */
  private int dirty = Integer.MIN_VALUE;
  
//  private ApplicationDesktop desktop;

  private BorderPane display;
  private ScrollPane displayCenter;
  private BorderPane displayWrapper;
  private Scene mainScene;
  //JavaFX Window
  private Stage mainStage;
  //Swing Window
  private java.awt.Window mainWindow;

  private WeakHashMap<Class<? extends Module>, Module> modules = new WeakHashMap<Class<? extends Module>, Module>();
  // Properties and Internationalization
  private Instance msg = MessageUtility.getInstance(getClass());
  private Properties properties;
  
  
  private WeakHashMap<Class<? extends Resource>, Resource> resources = new WeakHashMap<Class<? extends Resource>, Resource>();

  private StackPane root;
  private WeakHashMap<Class<? extends Service>, Service> services = new WeakHashMap<Class<? extends Service>, Service>();
  
  private HashMap<String, Object> systemProperties = new HashMap<String, Object>();
  
  public Application()
  {
    if(applicationInstance != null)
      System.err.println("Warning: two or more application instances are open, static methods are likely invalid.");
    applicationInstance = this;
    appInitialize();
  }
  
  /**
   * Creates an internationalized action for the key and descriptionKey provided
   * @param textKey the key for the NAME property
   * @param descriptionKey the key for the SHORT_DESCRIPTION property
   * @param listener the listener to execute
   * @return An ApplicationAction that reflects the parameters
   * 
   */
  public ApplicationAction createAction(String textKey, String descriptionKey, EventHandler<ActionEvent> actionEvent)
  {
    return new ApplicationAction(textKey, descriptionKey, actionEvent, this);  
  }
  
  /**
   * Create an action that will load the specified module when executed. This
   * method creates the internationalized keys for you in a standard format and
   * is the preferred method.
   * @param module the module to load
   * @return the ApplicationAction created around this parameter
   * 
   */
  public ApplicationAction createActionLoadmodule(Class<? extends Module> module)
  {
    String textKey = "load" + module.getSimpleName();
    String descriptionKey = "load" + module.getSimpleName() + "Details";
    return new ApplicationAction(textKey, descriptionKey, module, this);
  }

  /**
   * @param textKey the internationalized key for this text
   * @param descriptionkey the key for the short description 
   * @param module the module to load
   * @return An ApplicationActoin that offers the functionality for the parameters 
   * 
   */
  public ApplicationAction createActionLoadModule(String textKey, String descriptionkey, Class<? extends Module> module)
  {
    return new ApplicationAction(textKey, descriptionkey, module, this);
  }

  public void exit()
  {
    shutdownAndClean();
  }

  public File fileChooser(File file, boolean open)
  {
	FileChooser chooser = new FileChooser();
	File dir = file == null ? new File("") : file;
	if(!dir.isDirectory())
		dir = dir.getParentFile();
	chooser.setInitialDirectory(dir);
	try
	{
		File ret = null;
		if(open)
			ret = chooser.showOpenDialog(mainStage);
		else
			ret = chooser.showSaveDialog(mainStage);
		return ret;
	}
	catch(IllegalArgumentException e)
	{
		e.printStackTrace();
		return file;
	}
  }

  public ArrayList<IConverter<?>> getAllConverters()
  {
    ArrayList<IConverter<?>> ret = new ArrayList<IConverter<?>>();
    for (IConverter<?> con : converterMap.values())
      ret.add(con);
    return ret;
  }

  public double getContentHeight()
  {
    return displayCenter.getContent().getBoundsInLocal().getHeight(); 
  }

  public double getContentWidth()
  {
    return displayCenter.getContent().getBoundsInLocal().getWidth();
  }
  
  @SuppressWarnings({ "unchecked" })
  public <T> IConverter<T> getConverterForClass(Class<T> clazz)
  {
    return (IConverter<T>) converterMap.get(clazz);
  }
  
  public abstract MenuBar getMenuBar();

  /**
   * Retrieves a reference to a module. This looks at scope so it
   * returns a module that may or may not be initialized. As such it 
   * should be called sparingly and in a asynchronous manner as to 
   * minimize load times. 
   * @param clazz
   * @return <Return Description>  
   * 
   */
  @SuppressWarnings("unchecked")
  public <T extends Module> T getModule(Class<T> clazz)
  {
    Log.info("Getting Module -> " + clazz);
    T module = (T) modules.get(clazz);

    if (module != null && isDirty(module))
    {
      modules.remove(module);
      module = null;
    }

    if (module == null)
    {
      try
      {
        module = clazz.newInstance();
        module.setApplication(this);
        module.initialize();

        modules.put(clazz, module);
        /*
         * Intentional, this is to make it "Sync" life-span with a module using it as a dependency
         */
        module.setDirty(dirty);
      }
      catch (InstantiationException e)
      {
        throw new ResourceCreationException(e.getMessage(), e);
      }
      catch (IllegalAccessException e)
      {
        throw new ResourceCreationException(e.getMessage(), e);
      }
    }

    return module;
  }
  
  public Double getNumericPropert(Class<?> clazz, String key, Double defaultValue)
  {
    if(defaultValue == null)
      throw new IllegalArgumentException("Default value can not be null");
    try
    {
      String def = getProperty(clazz,  key, defaultValue.toString());
      return Double.valueOf(def);
    }
    catch (Exception e)
    {
      setProperty(clazz, key, defaultValue.toString());
      return defaultValue;
    }
  }
  
  public Properties getProperties()
  {
    return properties;
  }
  
  public String getProperty(Class<?> clazz, String key, String defaultValue)
  {
    return getProperty(clazz.getName() + "." + key, defaultValue);
  }
  
  public String getProperty(String key, String defaultValue)
  {
    return getProperties().getProperty(key, defaultValue);
  }
  @SuppressWarnings({ "unchecked"})
  public <T extends Resource> T getResource(Class<? extends T> clazz)
  {
    return ((T) manageResourceAdd(clazz, resources));
  }

  @SuppressWarnings("unchecked")
  public <T extends Service> T getService(Class<? extends T> clazz)
  {
    return (T) manageResourceAdd(clazz, services);
  }
  
  public WindowSettings getSettings()
  {
	  WindowSettings ret = new WindowSettings();
	  ret.minSizeX = 640;
	  ret.minSizeY = 480;
	  ret.resolutionX = Double.valueOf(getProperty(Application.class, RESOLUTION_X, "800"));
	  ret.resolutionY = Double.valueOf(getProperty(Application.class, RESOLUTION_Y, "600"));
	  return ret;
  }
  
  /**
   * Return a reference to the system properties which are a map of String/Objects. This is
   * serialized at the beginning and end of each session and allows variables to be persisted to the
   * local database between sessions. This accounts for things such as size, position, last open
   * states, etc... The Application Object can be serialized as well allowing the system to
   * reinstate itself at the last known state.
   * 
   * @return <Return Description>
   * 
   */
  public HashMap<String, Object> getSystemProperties()
  {
    return systemProperties;
  }

  protected final void appInitialize()
  {
    double percent = 0.0;
    preloadNotification(msg("startingApplication"), msg("startingApplicationDetail"), percent);
    //Load System Properties    
    preloadNotification(msg("loadingProperties"), msg("loadingPropertiesDetail", getPropertiesFile().getAbsolutePath()), ++percent);
    loadProperties();

    initialize();
  }

  public void loadModule(final Class<? extends Module> clazz)
  {
    if(activeModule != null)
      if(activeModule.getClass().equals(clazz))
        return;
    loadModule(clazz, false);
  }

  public void loadProperties()
  {
    properties = new Properties();
    try
    {
      properties.load(new FileInputStream(getPropertiesFile()));
    }
    catch (InvalidPropertiesFormatException e)
    {
      properties = new Properties();
    }
    catch (FileNotFoundException e)
    {
      properties = new Properties();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.application.IInternationalized#msg(java.lang.String)
   */
  @Override
  public synchronized String msg(String key)
  {
    return msg.get(key);
  }

  @Override
  public synchronized String msg(String key, Object... params)
  {
    return msg.get(key, params);
  }

  public File openFile()
  {
    return fileChooser(null, true, false);
  }
  
  public File openFile(String extension)
  {
    return openFile(extension, false);
  }
  
//TODO Evaluate this old code
//  public void loadModuleAsDialog(final Class<? extends GraphicalModule> clazz)
//  {
//    msg("loadingModule", clazz.getSimpleName());
//    GraphicalModule module = (GraphicalModule) getModule(clazz);
//    JPanel show = module.getContentPanel();
//    /* If this already shows the title in a "chisel" them don't title the frame */
//    if (show instanceof AbstractBaseContainer)
//      loadPanelAsDialog(show, "");
//    else
//      loadPanelAsDialog(show, module.getName());
//  }
//TODO Evaluation this code as useful or not
//  public void loadPanelAsDialog(final JPanel panel, final String title)
//  {
//    SwingUtilities.invokeLater(new Runnable()
//    {
//      @Override
//      public void run()
//      {
//        BasicDialog dialog = new BasicDialog(Application.this, panel, title);
//        UiUtility.centerFrame(Application.this, dialog);
//        dialog.setVisible(true);
//
//        // Provide a reference to the module to close itself
//        if (panel instanceof AbstractBaseContainer)
//          ((AbstractBaseContainer) panel).setBasicDialog(dialog);
//
//      }
//    });
//  }
  
  public File openFile(String extension, boolean force)
  {
    return fileChooser(extension, true, force);
  }

  public void preload()
  {
    preloadNotification("testKey", "messageKey", 50.0);
  }

  @SuppressWarnings("Modify this so the preload only fires on a JavaFX Thread")
  public void preloadNotification(String titleKey, String messageKey, double newProgress)
  {
	  try 
	  {
		  notifyPreloader(new ApplicationPreloaderNotification(msg(titleKey), msg(messageKey), newProgress));	  
	  }
	  catch(java.lang.NoClassDefFoundError t)
	  {  
	  }
  }

  public void processException(Thread thread, Throwable exception)
  {
    String title = msg("unknownError", exception.getClass().getSimpleName());
    String info = msg("unknownErrorMsg", exception.getMessage());

    showCriticalDialog(title, info);
  }

  @SuppressWarnings("rawtypes")
  public <T extends IConverter<?>> T registerConverter(Class<T> clazz)
  {
    /* invalid warning, these are mapped by class */
    @SuppressWarnings("unchecked")
    T converter = (T) converters.get(clazz);

    if (converter != null)
      return converter;
    else
    {
      try
      {
        converter = clazz.newInstance();
        if (converter instanceof AbstractConverter)
          ((AbstractConverter) converter).setApplication(this);

        converters.put(clazz, converter);
        converterMap.put(converter.getType(), converter);

        return converter;
      }
      catch (InstantiationException e)
      {
        throw new ResourceCreationException(e.getMessage(), e);
      }
      catch (IllegalAccessException e)
      {
        throw new ResourceCreationException(e.getMessage(), e);
      }
    }
  }

  /**
   * Remove a resource from management by the application. This will
   * set the targeted class to null for the next call resulting in a 
   * new instance of the resource regardless of scope.
   * @param clazz The class extending {@link Resource} to remove.
   */
  public void removeResource(Class<? extends Resource> clazz)
  {
    manageResourceRemove(clazz, resources);
  }

  /**
   * Removed the current scene, disposes it and recreates it from scratch. This does not
   * restart the application entirely.  
   */
  public void reset()
  {
  	mainStage.setScene(null);
  	
  	try
		{
			start(mainStage);
		}
		catch (Exception e)
		{
			logException(e);
		}
  }

  
  //TODO Does this make sense? Are modules registered anymore? Do they require menu items?
//  /**
//   * Adds the specified module to the modules list so that it can be launched. And additional
//   * modules can
//   * 
//   * @param name
//   * @param clazz
//   *          <Return Description>
//   * 
//   */
//  public void registerModule(String name, Class<? extends Module> clazz)
//  {
//    if (splash != null)
//      splash.setMessage(msg("registerModule", name));
//
//    if (name != null)
//      menus.getMenu(msg("modulesMenu")).addMenuItem(new ApplicationClassMenuItem(name, this, clazz));
//  }

  /**
   * This method allows the framework to take an exception and throw it in another thread. This
   * bypasses the standard termination of an uncaught exception and allows the application to log it
   * and keep running. This is a last resort and typically is thrown via a module loading or
   * unloading.
   * 
   * @param t
   *          the Throwable to rethrow
   * 
   */
  public void rethrowExceptionAsRuntime(final Throwable t)
  {
    new Thread()
    {
      @Override
      public void run()
      {
        logException(t);
        if (t instanceof RuntimeException)
          throw (RuntimeException) t;
        else
          throw new RuntimeException(t.getMessage(), t);
      }
    }.run();
  }

  /**
   * Run an operation that has a long lifespan and 
   * can cause the application to hang.
   * @param operation <Return Description>  
   * 
   */
  public void runLongOperation(final ThreadedOperation operation)
  {
    operation.setApplication(this);
    new Thread(operation).start();
  }

  public File saveFile()
  {
    return fileChooser(null, false, false);
  }

  public File saveFile(String extension)
  {
    return saveFile(extension, false);
  }

  public File saveFile(String extension, boolean force)
  {
    return fileChooser(extension, false, true);
  }

  public void saveProperties()
  {
    try
    {
      File target = getPropertiesFile();
      if(!target.exists())
      {
        if(target.getParentFile() != null)
          target.getParentFile().mkdirs();
        
        boolean complete = target.createNewFile();
        if(!complete)
          throw new RuntimeException("IO Error: the file '" + target.getAbsolutePath() + "' could not be created.");
      }

      properties.store(new FileOutputStream(target), "Updated ");
      
    }
    catch (FileNotFoundException e)
    {
      throw new RuntimeException(e.getMessage(), e);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  //TODO this seems like overkill, yes, a system of measures is important, but shouldn't the data always be in centimeters and just use a big decimal?
//  public void setSystemOfMeasures(SystemOfMeasures system)
//  {
//    if (SystemOfMeasures.ACTIVE_SYSTEM == system)
//      return;
//    // FIXME Raise warning dialog
//    // FIXME -> Dump all modules
//    // FIXME -> Dump all Conveters
//    // FIXME -> Deactivate all Resources
//
//    showInformationDialog("System of Measures Changed", "The system of measures has been changed to " + system);
//    SystemOfMeasures.ACTIVE_SYSTEM = system;
//  }

  public void setProperty(Class<?> clazz, String key, String value)
  {
    setProperty(clazz.getName() + "." + key, value);
  }
  
  public void setProperty(String key, String value)
  {
    getProperties().setProperty(key, value);
  }
  
  public void showConfirmationYesNo(String title, String information, Procedure<Boolean> callback)
  {    
    final ConfirmaionDialog dialog = new ConfirmaionDialog(title, information, callback);
    showDialog(dialog);
  }

  public void showCriticalDialog(String title, String information)
  {
    showBasicDialog(title, information, DialogType.ERROR);
    
  }

  public void showDialog(FxDialog dialog)
  {
    showDialog(dialog, 1d);
  }

  public void showDialog(FxDialog dialog, double time)
  {
    UiDialog toShow = new UiDialog();
    toShow.setFxDialog(dialog);
    displayDialog(toShow, time);    
  }

  public void showInformationDialog(String title, String information)
  {
    showBasicDialog(title, information, DialogType.INFORMATION);
  }
  
  public void showWarningDialog(String title, String information)
  {
    showBasicDialog(title, information, DialogType.WARNING);
  }
  public abstract void shutdown();
  
  @Override
  public void start(Stage provided) throws Exception
  {
	WindowSettings win = getSettings();
	
    //Hook listeners
    provided.setOnCloseRequest(new EventHandler<WindowEvent>()
    {

      @Override
      public void handle(WindowEvent evt)
      {  
        shutdownAndClean();
      }
    });
    
    
    Platform.setImplicitExit(exitOnClose());

    javaFxSceneStartup();
    
    mainStage = provided;    
    mainStage.setTitle(getTitle());
    mainStage.setMinWidth(win.minSizeX);
    mainStage.setMinHeight(win.minSizeY);
    
    mainStage.setScene(mainScene);
    mainStage.setOpacity(0.0);
    mainStage.setWidth(win.resolutionX > win.minSizeX ? win.resolutionX : win.minSizeX);
    mainStage.setHeight(win.resolutionY > win.minSizeY ? win.resolutionY : win.minSizeY);
    mainStage.show();
    
    mainStage.setOnCloseRequest((e)->
    {
    	stop();
    });
//    mainStage.getOwner().setX(locX);
//    mainStage.getOwner().setY(locY);
    
    Timeline fade = new Timeline();
    fade.setAutoReverse(false);
    fade.setCycleCount(1);
    fade.getKeyFrames().add(
        new KeyFrame(Duration.seconds(.5), new KeyValue(mainStage.opacityProperty(), (1.0)))
        );
    
    fade.onFinishedProperty().set((x)->
    {
    	Platform.runLater(()->this.postStartup());	
    });
    fade.play();
  }
  
  @Override
  public void stop()
  {
  	shutdown();
  }
  
  protected synchronized final void dirty(Resource resource)
  {
    if (dirty == Integer.MAX_VALUE)
      throw new RuntimeException("Integer underflow: this method is not implemented and the max tracking has been reached, restaring the application will fix the issue. Please contact support.");

    if (resource != null)
      resource.setDirty(++dirty);

    // TODO PUSH DIRTY TO CLIENTS AND VIEWS

  }
  
  protected abstract boolean exitOnClose();
  
  
  /**
   * The properties file is used to save day to day operations of the 
   * application and works as a persistent store outside the databases.
   * 
   * @return a link to the properties file for this application 
   */
  protected abstract File getPropertiesFile(); 
  
  protected abstract String getStyleSheets();
  
  /**
   * @return the prefix title for this application. This will
   * display information such as:
   * <pre>
   *   ${getTitle()} | ${MODULE_NAME} | ${SUB_MODULE}
   * </pre>
   * 
   */
  protected String getTitle()
  {
    return msg("applicationTitle");
  }
  
  /**
   * @return the dimensions of this active Window before exit. This
   * allows the settings to be saved.
   */
  protected WindowSettings getWindowDimensions()
  {
	  if(mainStage != null)
	  {
		  WindowSettings win = new WindowSettings();
		  win.minSizeX = mainStage.getMinWidth();
		  win.minSizeY = mainStage.getMinHeight();
		  win.resolutionX = mainStage.getWidth();
		  win.resolutionY = mainStage.getWidth();
		  return win;
	  }
	  else if(mainWindow != null)
	  {
		  WindowSettings win = new WindowSettings();
		  win.minSizeX = mainWindow.getMinimumSize().getWidth();
		  win.minSizeY = mainWindow.getMinimumSize().getHeight();
		  win.resolutionX = mainWindow.getWidth();
		  win.resolutionY =  mainWindow.getHeight();
		  return win;
	  }
	  else
		  throw new IllegalStateException("The stage and window are not initialized. Application not initialized");
		  
  }
  
  /**
   * Perform all custom initialization here. The number passed is the starting percentage for 
   * the end user in the preloader. Use {@link #notifyPreloader(javafx.application.Preloader.PreloaderNotification)} to
   * communicate with the preloader.  
   */
  protected abstract void initialize();
  
  /**
   * Determines if the resource should be considered dirty. Rather
   * than internalize this so the class this is a protected final 
   * method that is used to manipulate the resources.
   * @param resource the resource to check
   * @return <tt>true</tt> if dirty <tt>false</tt> otherwise
   * </pre>
   * 
   */
  protected final <Q extends Resource> boolean isDirty(Q resource)
  {
    if (ScopeType.LOCAL.equals(resource.getScopeType()))
      return true;

    if (ScopeType.APPLICATION.equals(resource.getScopeType()))
      return false;

    return resource.getDirty() < dirty;
  }
  
  public void startSwing(JFrame frame)
  {
	  this.mainWindow = frame;
	  WindowSettings win = getSettings();
	  
	  frame.addWindowListener(
		new WindowListener()
		{
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) 
			{
			}

			@Override
			public void windowClosing(java.awt.event.WindowEvent e) 
			{	
				shutdown();
				shutdownAndClean();
			}

			@Override
			public void windowClosed(java.awt.event.WindowEvent e) 
			{	
			}

			@Override
			public void windowIconified(java.awt.event.WindowEvent e) 
			{
			}

			@Override
			public void windowDeiconified(java.awt.event.WindowEvent e) 
			{
				
			}

			@Override
			public void windowActivated(java.awt.event.WindowEvent e) 
			{
			}

			@Override
			public void windowDeactivated(java.awt.event.WindowEvent e) 
			{	
			}
		});

	  if(exitOnClose())
	  {
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  }
	  else
		  frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	  
	  double width = win.resolutionX > win.minSizeX ? win.resolutionX : win.minSizeX;
	  double height = win.resolutionY > win.minSizeY ? win.resolutionY : win.minSizeY;
	  double minWidth = win.minSizeX;
	  double minHeight = win.minSizeY;

	  frame.setTitle(getTitle());  
	  frame.setMinimumSize(new Dimension((int) minWidth, (int) minHeight));
	  frame.setSize((int)width, (int)height);
	  frame.setVisible(true);
	  
	  Platform.runLater(()->
	  {
		  JFXPanel jfxPanel = new JFXPanel();
		  javaFxSceneStartup();
		  jfxPanel.setScene(mainScene);
		  frame.add(jfxPanel);
		  Platform.runLater(()->this.postStartup());		
	  });

  }
  protected void javaFxSceneStartup()
  {
	  WindowSettings win = new WindowSettings();
	  root = new StackPane();
	    
	  displayWrapper = new BorderPane();
	  display = new BorderPane();
	  dialogs = new StackPane();
	  critical = new StackPane();

	  display.getStyleClass().add(CSS.BACKGROUND);

	  root.setMinWidth(win.minSizeX);
	  root.setMinHeight(win.minSizeY);

	  displayWrapper.setMinWidth(win.minSizeX);
	  displayWrapper.setMinHeight(win.minSizeY);

	  dialogs.setMinWidth(win.minSizeX);
	  dialogs.setMinHeight(win.minSizeY);

	  critical.setMinWidth(win.minSizeX);
	  critical.setMinHeight(win.minSizeY);


	  mainScene = new Scene(root);
	  if(getStyleSheets() != null)
		  mainScene.getStylesheets().addAll(getStyleSheets());
	  else
		  mainScene.getStylesheets().addAll(Application.class.getClassLoader().getResource("application.css").toExternalForm());

	  dialogs.setOpacity(0.0);
	  critical.setOpacity(0.0);
	  dialogs.setVisible(false);
	  critical.setVisible(false);

	  ArrayList<Stop> stops = new ArrayList<>();
	  stops.add(new Stop(0, Color.RED));
	  stops.add(new Stop(0, Color.GREEN));
	  stops.add(new Stop(0, Color.BLUE));
	  Paint p = new LinearGradient(0.0,0.0, 1.0,1.0, true, CycleMethod.REFLECT, stops);

	  
	  mainScene.setFill(p);

	  //Set Menus
	  displayWrapper.setTop(getMenuBar());
	  displayWrapper.setCenter(display);

	  displayCenter = new ScrollPane();
	  displayCenter.setPrefHeight(display.getPrefHeight());
	  displayCenter.setFitToHeight(true);
	  displayCenter.setFitToWidth(true);
	  //FIXME Color Debugs
	  //	    displayCenter.setStyle("-fx-background-color: pink;");
	  displayCenter.setPrefWidth(display.getPrefWidth());    

	  display.setCenter(displayCenter);

	  root.getChildren().addAll(displayWrapper, dialogs, critical);

	  for(Node n : root.getChildren())
		  StackPane.setAlignment(n, Pos.TOP_LEFT);
	  //Set notifications
  }
  
  protected abstract void logException(Throwable t);
  
  /**
   * A method that is called after the application has started.
   */
  protected abstract void postStartup();
  
  protected void shutdownAndClean()
  {
    
    ArrayList<Throwable> errors = new ArrayList<Throwable>();
    try
    {
      shutdown();
    }
    catch (Throwable t)
    {
      errors.add(t);
    }
//TODO Native resource management--do modules have a "shutdown"?
    for (Module m : modules.values())
    {
      try
      {
        m.shutdown();
      }
      catch (Throwable t)
      {
        errors.add(t);
      }
    }

    for (Resource r : resources.values())
    {
      try
      {
        r.shutdown();
      }
      catch (Throwable t)
      {
        errors.add(t);
      }
    }

    /* Save Screen Locations */
    WindowSettings win = getWindowDimensions();
    setProperty(Application.class, RESOLUTION_X, Double.toString(win.resolutionX));
    setProperty(Application.class, RESOLUTION_Y, Double.toString(win.resolutionY));

    /* Save after all modules shutdown */
    try
    {
      saveProperties();  
    }
    catch(Throwable e)
    {
      errors.add(e);
    }

    Platform.setImplicitExit(true);
    if (errors.size() > 0)
    {
      for (Throwable t : errors)
        logException(t);

      System.out.println("System exited with errors:");
      System.exit(-1);
    }
    else
    {
      System.out.println("System exiting successfully");
      System.exit(1);
    }
  }

  protected void swingStartup(JFrame frame)
  {
	  
  }
  private void displayDialog(final UiDialog dialog, final double seconds)
  {
	  Platform.runLater(()->
	  {
		  double left = dialogs.getWidth();
		   
		    dialogs.getChildren().add(dialog);
		    dialogs.setOpacity(0.0);
		    dialogs.setVisible(true);
		    
		    final GaussianBlur blur = new GaussianBlur();
		    blur.setRadius(0.0);
		    displayWrapper.setEffect(blur);
		    
		    Timeline timeline = new Timeline();
		    timeline.setCycleCount(1);
		    timeline.setAutoReverse(false);
		    timeline.getKeyFrames().add(
		        new KeyFrame(Duration.seconds(.2), new KeyValue(blur.radiusProperty(), 4d))
		        );
		    
		    //Undoes the dialog and removes it.
		    final Timeline reset = new Timeline();
		    reset.setCycleCount(1);
		    reset.setAutoReverse(false);
		    reset.getKeyFrames().add(
		        new KeyFrame(
		            Duration.seconds(.1), 
		            new EventHandler<ActionEvent>(){

		              @Override
		              public void handle(ActionEvent arg0)
		              {
		                dialogs.setVisible(false);
		                dialogs.setOpacity(0.0);
		                blur.setRadius(0.0);
		                dialogs.getChildren().remove(dialog);
		                displayWrapper.setEffect(null);
		              }
		            },
		              
		            new KeyValue(blur.radiusProperty(), 0d),
		            new KeyValue(dialogs.opacityProperty(), 0d)
		            )
		        );
		    
		    FadeTransition trans = new FadeTransition(Duration.seconds(seconds));
		    trans.setNode(dialog);
		    trans.setFromValue(0.0);
		    trans.setToValue(1.0);
		    trans.setCycleCount(1);
		    trans.setAutoReverse(true);
		    
		    TranslateTransition animation = new TranslateTransition();
		    animation.setNode(dialog);
		    animation.setFromX(-left);
		    animation.setFromY(0);
		    animation.setToX(0);
		    animation.setToY(0);
		    animation.setDuration(Duration.seconds(seconds / 4));
		    animation.setAutoReverse(false);
		    
		    dialog.setHideTimeline(reset);
		    animation.play();
		    trans.play();
		    timeline.play();
	  });
  }
  private File fileChooser(String extension, boolean open, boolean force)
  {

    FileChooser chooser = new FileChooser();
    File initial = new File(getProperty(FILE_LAST_DIRECTORY, "/"));
    chooser.setInitialDirectory(initial);
    if(extension != null)
      chooser.getExtensionFilters().add(new ExtensionFilter("*." + extension, "*." + extension));
    File ret = null;
    try
    {
      if(open)
        ret = chooser.showOpenDialog(mainStage);
      else
        ret = chooser.showSaveDialog(mainStage);  
    }
    catch (IllegalArgumentException e)
    {
      initial = new File(".");
      chooser.setInitialDirectory(initial);
      chooser.getExtensionFilters().clear();
      
      if(open)
        ret = chooser.showOpenDialog(mainScene.getWindow());
      else
        ret = chooser.showSaveDialog(mainScene.getWindow());
    }
    
    
    if(ret != null)
    {
      String dir = ret.isDirectory() ? ret.getAbsolutePath() : ret.getParent();
      setProperty(FILE_LAST_DIRECTORY, dir);
    }
    if(force && ret != null)
      if(!ret.getAbsolutePath().toLowerCase().endsWith(extension))
        ret = new File(ret.getAbsolutePath() + "." + extension);
        
    return ret;
    
  }
  
  private void loadModule(final Class<? extends Module> clazz, boolean asDialog)
  {
    Module rollback = getModule(clazz);
    try
    {
      Module module = getModule(clazz);

      if (activeModule != module)// Intentional, this is a reference check
      {
        dirty(activeModule = module);
        dirty(module);
      }

      if (asDialog)
      {
        throw new RuntimeException("Not implemented");
        // TODO Implement this
      }
      
      final Node old = displayCenter.getContent();
      final Node active = module.getNode(); 
      swapModules(old, active, 1.0);
      
      activeModule = module;
      if(!module.postInitCalled)
      {
        module.postInitCalled = true;
        module.postInitialize();
      }      
    }
    catch(Throwable error)
    {
      activeModule = rollback;
      throw new RuntimeException("Module Error handler" + error.getMessage(), error);
    }
  }

  private <T extends Resource> T manageResourceAdd(Class<? extends T> clazz, Map<Class<? extends T>, T> map)
  {
    T instance = map.get(clazz);
    if (instance != null && isDirty(instance))
    {
      instance.shutdown();
      map.remove(clazz);
      instance = null;
    }

    if (instance == null)
    {
      try
      {
        instance = (T) clazz.newInstance();
        instance.setApplication(this);
        instance.initialize();

        map.put(clazz, instance);
      }
      catch (InstantiationException e)
      {
        throw new ResourceCreationException(e.getMessage(), e);
      }
      catch (IllegalAccessException e)
      {
        throw new ResourceCreationException(e.getMessage(), e);
      }
    }

    return (T) instance;
  }

  private <T extends Resource> void manageResourceRemove(Class<? extends T> clazz, Map<Class<? extends T>, T> map)
  {
    T instance = map.get(clazz);
    if (instance != null)
      map.remove(instance);
  }

  private void showBasicDialog(final String title, final String information, final DialogType type)
  {
    showConfirmationYesNo(title, information, (x)->{});
  }
  
  private void swapModules(final Node oldNode, final Node newNode, final double time)
  {
    newNode.setOpacity(0.0);
    newNode.setVisible(true);
    
    final TranslateTransition newRight = new TranslateTransition(Duration.seconds(time), newNode);
    newRight.setFromX(-display.getWidth());
    newRight.setToX(0);


    final FadeTransition newFade = new FadeTransition(Duration.seconds(time / 2));
    newFade.setCycleCount(1);
    newFade.setToValue(1.0);
    newFade.setFromValue(0.0);
    newFade.setAutoReverse(true);
    newFade.setNode(newNode);

    final EventHandler<ActionEvent> swap = new EventHandler<ActionEvent>(){

      @Override
      public void handle(ActionEvent arg0)
      {
        if(oldNode != null)
          oldNode.setVisible(false);
        
        displayCenter.setContent(newNode);
        
        newRight.play();
        newFade.play();
      }
    };

    if(oldNode == null)
    {
      swap.handle(new ActionEvent());
      return;
    }
      
    TranslateTransition oldRight = new TranslateTransition(Duration.seconds(time / 3), oldNode);
    oldRight.setFromX(0);
    oldRight.setToX(display.getWidth());
    oldRight.setOnFinished(swap);


    FadeTransition oldFade = new FadeTransition(Duration.seconds(time / 2 / 3), oldNode);
	oldFade.setFromValue(1.0);
	oldFade.setToValue(0);
	oldFade.setCycleCount(1);
	oldFade.setAutoReverse(true);
	
    oldFade.play();
    oldRight.play();
  }
}
