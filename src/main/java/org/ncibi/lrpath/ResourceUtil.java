package org.ncibi.lrpath;

import java.util.ResourceBundle;

public class ResourceUtil {
	
	private ResourceUtil(){} // no instances allowed
	
	private static final ResourceBundle urlResoruceBundle = ResourceBundle.getBundle("org.ncibi.resource.bundle.url");

	
	public static String getResultsDirectoryUrl(){
		return urlResoruceBundle.getString("resultDirectory");
	}

}
