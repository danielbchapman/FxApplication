package com.danielbchapman.fx.builders;

import java.io.File;
import java.util.function.Consumer;

import com.danielbchapman.application.Application;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("FILE FIELD Implement a slick componet that actually show the right information")
public class FileField extends Button 
{
	@Getter
	@Setter
	private File file;
	
	@Getter
	@Setter
	private Consumer<File> fileChanged;
	
	public FileField(File start)
	{
		this.file = start;
		setOnAction(x -> 
		{
			if(file == null)
				file = new File("");
			
			File test = Application.getCurrentInstance().fileChooser(file, true); 
			if(test != null)
			{
				file = test;
				setText(file == null ? null : file.getAbsolutePath());
				if(fileChanged != null)
					fileChanged.accept(file);
			}
		});
		
		setText(file == null ? null : file.getAbsolutePath());
	}
	
	public FileField(String file)
	{
		this(new File(file == null ? "" : file));
	}
	
}
