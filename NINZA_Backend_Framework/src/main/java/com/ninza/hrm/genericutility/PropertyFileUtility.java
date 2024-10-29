package com.ninza.hrm.genericutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.ninza.hrm.constants.IPathConstants;

public class PropertyFileUtility {

	public String getDataFromPropertyFile(String key) throws IOException {
		FileInputStream fis = new FileInputStream(IPathConstants.propertyFilePath);
		Properties properties = new Properties();
		properties.load(fis);
		String data = properties.getProperty(key);
		return data;
	}
}
