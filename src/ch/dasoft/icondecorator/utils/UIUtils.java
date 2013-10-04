package ch.dasoft.icondecorator.utils;

import java.io.File;
import java.io.FileInputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author David Sommer
 */
public class UIUtils {

	public static final String[] IMAGE_EXTENSIONS  = {"*.gif", "*.png", "*.bmp", "*.jpg", "*.tif"};
	public static final int      IMG_WIDTH         = 16;
  public static final int      IMG_HEIGHT        = 16;

	public static boolean isImageFile(String fileName) {
		int dot = fileName.lastIndexOf(".");
		if (dot==-1) {
			return false;
		}
	  String fileExt = fileName.substring(dot,fileName.length());
	  for (String extension : IMAGE_EXTENSIONS){
			extension = extension.substring(1);
			if (fileExt.equalsIgnoreCase(extension)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isImageFile(IResource resource){
		return isImageFile(resource.getName());
	}
	
	public static boolean isImageFile(File file){
		return isImageFile(file.getName());
	}

	public static Image getImageFromResource(IResource resource) {
		return getImage(getIResourceAbsPath(resource));
	}
	
	public static Image getImage(String absolutePath) {
		try {
			if (absolutePath == null){
				return null;
			}
			Image img = new Image(Display.getDefault(), new FileInputStream(absolutePath));

			return resize(img, IMG_WIDTH, IMG_HEIGHT);
		} catch (Exception e) {
			return null;
		}
	}
	
	private static Image resize(Image image, int width, int height) {
	  Image scaled = new Image(Display.getDefault(), width, height);
	  GC gc = new GC(scaled);
	  gc.setAntialias(SWT.ON);
	  gc.setInterpolation(SWT.HIGH);
	  gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
	  gc.dispose();
	  image.dispose();
	  return scaled;
	  }

	public static String getIResourceAbsPath(IResource resource){
		if (resource.getLocation() == null){
			return null;
		}
		return resource.getLocation().toOSString();
	}
	
}
