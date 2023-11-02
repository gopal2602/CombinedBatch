package com.sg.methods;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.sg.driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/*************************************
	 * Method Name	: getDateTime
	 * Purpose		: generate the timestamp
	 * 
	 *************************************/
	public String getDateTime(String strDateFormat) {
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(strDateFormat);
			return sdf.format(dt);
		}catch(Exception e) {
			System.out.println("Exception in 'getDateTime()' method. " + e);
			return null;
		}
		finally{
			dt = null;
			sdf = null;
		}
	}
	
	
	/*************************************
	 * Method Name	: getPropData
	 * Purpose		: get the prop data in the form of Map<String, String) object
	 * 
	 *************************************/
	public Map<String, String> getPropData(String fileName){
		FileInputStream fin = null;
		Properties prop = null;
		Map<String, String> objData = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir") + "\\Configuration\\"+fileName+".properties");
			prop = new Properties();
			objData = new HashMap<String, String>();
			prop.load(fin);
			
			Set<Map.Entry<Object, Object>> oBoth = prop.entrySet();
			Iterator<Map.Entry<Object, Object>> it = oBoth.iterator();
			while(it.hasNext() == true) {
				Map.Entry<Object, Object> mp = it.next();
				objData.put(mp.getKey().toString(), mp.getValue().toString());
			}
			return objData;
		}catch(Exception e) {
			System.out.println("Exception in 'getPropData()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				prop = null;
				objData = null;
			}catch(Exception e) {
				System.out.println("Exception in 'getPropData()' method. " + e);
				return null;
			}
		}
	}
	
	
	/*************************************
	 * Method Name	: clickObject()
	 * Purpose		: click the webElement
	 * 
	 *************************************/
	public boolean clickObject(WebDriver oBrowser, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oEles.get(0).click();
				reports.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' was clicked successful");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM structure");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'clickObject()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	/*************************************
	 * Method Name	: setObject()
	 * Purpose		: set the value to the webElement
	 * 
	 *************************************/
	public boolean setObject(WebDriver oBrowser, By objBy, String strData) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oEles.get(0).sendKeys(strData);
				reports.writeReport(oBrowser, "Pass", "The value '"+strData+"' was set to the element '"+String.valueOf(objBy)+"'");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM structure");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'setObject()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: setObjectBasedOnData()
	 * Purpose		: set the value to the webElement based on the input data. IF input is blank, don't enter the data
	 * 
	 *************************************/
	public boolean setObjectBasedOnData(WebDriver oBrowser, By objBy, String strData) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				if(!strData.isEmpty())
					oEles.get(0).clear();
					oEles.get(0).sendKeys(strData);
				reports.writeReport(oBrowser, "Pass", "The value '"+strData+"' was set to the element '"+String.valueOf(objBy)+"'");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM structure");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'setObjectBasedOnData()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: clearAndsetObject()
	 * Purpose		: set the value to the webElement by removing old data
	 * 
	 *************************************/
	public boolean clearAndsetObject(WebDriver oBrowser, By objBy, String strData) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oEles.get(0).clear();
				oEles.get(0).sendKeys(strData);
				reports.writeReport(oBrowser, "Pass", "The value '"+strData+"' was set to the element '"+String.valueOf(objBy)+"' by clearing old data");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM structure");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'clearAndsetObject()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: verifyElementPresent()
	 * Purpose		: to check the presence of WebElement in the DOM
	 * 
	 *************************************/
	public boolean verifyElementPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				reports.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' exist in the DOM");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM structure");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyElementPresent()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	/*************************************
	 * Method Name	: verifyElementNotPresent()
	 * Purpose		: to check the non-presence of WebElement in the DOM
	 * 
	 *************************************/
	public boolean verifyElementNotPresent(WebDriver oBrowser, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was still found in the DOM");
				return false;
			}else {
				reports.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' was not found in the DOM");
				return true;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyElementNotPresent()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: verifyOptionalElement()
	 * Purpose		: to check the presence of Optional WebElement in the DOM
	 * 
	 *************************************/
	public boolean verifyOptionalElement(WebDriver oBrowser, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyOptionalElement()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: compareValue()
	 * Purpose		: to compare the actual and expected data
	 * 
	 *************************************/
	public boolean compareValue(WebDriver oBrowser, String actualData, String expectedData) {
		try {
			if(actualData.equalsIgnoreCase(expectedData)) {
				reports.writeReport(oBrowser, "Pass", "Both actual '"+actualData+"' & expected '"+expectedData+"' values are matching");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "Mis-match in both actual '"+actualData+"' & expected '"+expectedData+"' values");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'compareValue()' method. " + e);
			return false;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: verifyText()
	 * Purpose		: to verify the data from Text, attributes & dropdown
	 * 
	 *************************************/
	public boolean verifyText(WebDriver oBrowser, By objBy, String strObjectType, String expectedData) {
		List<WebElement> oEles = null;
		Select oSelect = null;
		String actualData = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				switch(strObjectType.toLowerCase()) {
					case "text":
						actualData = oEles.get(0).getText();
						break;
					case "value":
						actualData = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSelect = new Select(oEles.get(0));
						actualData = oSelect.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeReport(oBrowser, "Fail", "Invalid object type '"+strObjectType+"' was provided");
						return false;
				}
				
				Assert.assertTrue(compareValue(oBrowser, actualData, expectedData));
				reports.writeReport(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' is exist in the DOM to get the data");
				return true;
			}else {
				reports.writeReport(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM structure");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'verifyText()' method. " + e);
			return false;
		}
		finally {
			oEles = null;
			oSelect = null;
			actualData = null;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: setObjectBasedOnData()
	 * Purpose		: set the value to the webElement based on the input data. IF input is blank, don't enter the data
	 * 
	 *************************************/
	public WebDriver launchBrowser(String browserName) {
		WebDriver driver = null;
		try {
			switch(browserName.toLowerCase()) {
				case "chrome":
					driver = new ChromeDriver();
					break;
				case "firefox":
					driver = new FirefoxDriver();
					break;
				case "edge":
					driver = new EdgeDriver();
					break;
				default:
					reports.writeReport(null, "Fail", "Invalid browser name '"+browserName+"'");
					Assert.fail("Invalid browser name '"+browserName+"'");
			}
			
			if(driver!=null) {
				driver.manage().window().maximize();
				reports.writeReport(driver, "Pass", "The '"+browserName+"' browser was launched successful");
				return driver;
			}else {
				reports.writeReport(driver, "Fail", "Failed to launch the '"+browserName+"' browser");
				return null;
			}
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in 'launchBrowser()' method. " + e);
			return null;
		}
	}
	
	
	/*************************************
	 * Method Name	: threadSleep()
	 * Purpose		: set the value to the webElement based on the input data. IF input is blank, don't enter the data
	 * 
	 *************************************/
	public void threadSleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}catch(Exception e) {}
	}
}

