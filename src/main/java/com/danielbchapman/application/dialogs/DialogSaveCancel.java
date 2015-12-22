package com.danielbchapman.application.dialogs;


public abstract class DialogSaveCancel extends FxDialogTwoButton
{

  @Override
  protected String getButtonOneText()
  {
    return msg("save");
  }

  @Override
  protected String getButtonTwoText()
  {
    return msg("cancel");
  }

  @Override
  protected void onOneClick()
  {
    onSave();
    closeDialog();
  }
  
  protected abstract void onSave();
  protected abstract void onCancel();

  @Override
  protected void onTwoClick()
  {
    onCancel();
    cancelDialog();
  }

  @Override
  public Type getType()
  {
    return Type.CENTER;
  }
}
