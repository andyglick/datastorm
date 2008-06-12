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
	InputStream istream = locateFile(filename);
	ImageData imgData = new ImageData(istream);
	final Image img = new Image(display, imgData);
	return img;
}

/**
 * @param filename
 * @return
 * @throws IllegalStateException
 */
private static InputStream locateFile(String filename) throws IllegalStateException {
	// load from jar
	InputStream istream = View.class.getClass().getResourceAsStream(filename);
	
	// for testing try local resource
	if( istream == null ) {
		try {
			istream = new FileInputStream(new File("img", filename));
		}
		catch(FileNotFoundException e1) { // don't worry
			try {
				istream = new FileInputStream(new File("img\\vista-icons\\", filename));
			}
			catch(FileNotFoundException e) {}
		}
	}
	
	if( istream == null ) {
		throw new IllegalStateException("cant find image '" + filename + "'");
	}
	return istream;
}
//
// public static Image readAndResizeImage(Display display, String filename, int width, int height) {
// final Image image = new Image(display, new ImageData(locateFile(filename)));
//	
// Image scaled = new Image(Display.getDefault(), width, height);
// GC gc = new GC(scaled);
// gc.setAntialias(SWT.ON);
// gc.setInterpolation(SWT.HIGH);
// gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
// gc.dispose();
// scaled.setBackground(new Color(display, 255, 255, 255));
// image.dispose(); // don't forget about me!
// return scaled;
// }

}
