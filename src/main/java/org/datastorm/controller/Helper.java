package org.datastorm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.datastorm.gui.swt.View;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class Helper {
public static Image readImage(Display display, String filename) {
	ImageData imgData = new ImageData(locateFile(filename));
	return new Image(display, imgData);
}

private static InputStream locateFile(String filename) throws IllegalStateException {
	// load from jar
	InputStream istream = View.class.getResourceAsStream(filename);
	// for testing try local resource
	if( istream == null ) {
		try {
			istream = new FileInputStream(new File("img", filename));
		}
		catch(FileNotFoundException e1) { // don't worry
		}
	}
	
	if( istream == null ) {
		throw new IllegalStateException("Can't find image '" + filename + "'");
	}
	return istream;
}

}
