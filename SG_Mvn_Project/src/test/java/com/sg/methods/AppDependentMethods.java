package com.sg.methods;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.sg.driver.DriverScript;
import com.sg.locators.HomePage;

public class AppDependentMethods extends DriverScript{
	/*************************************
	 * Method Name	: navigateURL()
	 * Purpose		: to navigate the URL
	 * 
	 *************************************/
	public boolean navigateURL(WebDriver oBrowser, String strURL) {
		try {
			oBrowser.navigate().to(strURL);
			waitForElement(oBrowser, HomePage.obj_Login_Btn, "Clickable", "", minWaitTime);
			Assert.assertTrue(appInd.compareValue(oBrowser, oBrowser.getTitle(), "actiTIME - Login"), "Invalid Page title 'actiTIME - Login' was displayed");
			reports.writeReport(oBrowser, "Screenshot", "URL is navigated successful");
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'navigateURL()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'navigateURL()' method. " + e);
			return false;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: loginToApplication()
	 * Purpose		: to login to ACtiTime
	 * 
	 *************************************/
	public boolean loginToApplication(WebDriver oBrowser, String userName, String password) {
		try {
			Assert.assertTrue(appInd.setObject(oBrowser, HomePage.obj_UserName_Edit, userName), "Failed to enter the UserName '"+userName+"' during login");
			Assert.assertTrue(appInd.setObject(oBrowser, HomePage.obj_Password_Edit, password), "Failed to enter the UserName '"+password+"' during login");
			Assert.assertTrue(appInd.clickObject(oBrowser, HomePage.obj_Login_Btn), "Failed to click the '"+String.valueOf(HomePage.obj_Login_Btn)+"' element");
			waitForElement(oBrowser, HomePage.obj_TimeTrack_PageHeader, "Text", "Enter Time-Track", minWaitTime);
			Assert.assertTrue(appInd.verifyText(oBrowser, HomePage.obj_TimeTrack_PageHeader, "Text", "Enter Time-Track"));
			reports.writeReport(oBrowser, "Screenshot", "Login to ActiTime was successful");
			
			//Handle the optional shortcut window
			if(appInd.verifyOptionalElement(oBrowser, HomePage.obj_Shortcut_Window)) {
				Assert.assertTrue(appInd.clickObject(oBrowser, HomePage.obj_Shortcut_Window_Close_Btn));
				reports.writeReport(oBrowser, "Screenshot", "Shortcut window was closed successful");
			}
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'loginToApplication()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'loginToApplication()' method. " + e);
			return false;
		}
	}
	
	
	
	/*************************************
	 * Method Name	: logoutFromActiTime()
	 * Purpose		: to logout from ACtiTime
	 * 
	 *************************************/
	public boolean logoutFromActiTime(WebDriver oBrowser) {
		try {
			Assert.assertTrue(appInd.clickObject(oBrowser, HomePage.obj_Logout_Link));
			waitForElement(oBrowser, HomePage.obj_LoginHeader_Text, "Text", "Please identify yourself", minWaitTime);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, HomePage.obj_LoginLogo_Img));
			reports.writeReport(oBrowser, "Screenshot", "logout from the '"+propData.get("appName")+"' is successful");
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'logoutFromActiTime()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'logoutFromActiTime()' method. " + e);
			return false;
		}
	}
	
	
	
	
	/*************************************
	 * Method Name	: waitForElement()
	 * Purpose		: to wait for the element to load based on the condition specified
	 * 
	 *************************************/
	public boolean waitForElement(WebDriver oBrowser, By objBy, String strWaitReason, String strText, long waitTime) {
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oBrowser, Duration.ofSeconds(waitTime));
			switch(strWaitReason.toLowerCase()) {
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objBy));
					break;
				case "visibility":
					oWait.until(ExpectedConditions.visibilityOfElementLocated(objBy));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, strText));
					break;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, strText));
					break;
				case "alert":
					oWait.until(ExpectedConditions.alertIsPresent());
					break;
				default:
					reports.writeReport(oBrowser, "Fail", "Invalid wait condition '"+strWaitReason+"' was provided.");
					return false;
			}
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'waitForElement()' method. " + e);
			return false;
		}finally {oWait = null;}
	}
}
