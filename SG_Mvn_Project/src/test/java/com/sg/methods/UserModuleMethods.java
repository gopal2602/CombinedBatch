package com.sg.methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.sg.driver.DriverScript;
import com.sg.locators.UsersPage;

public class UserModuleMethods extends DriverScript{
	
	/*************************************
	 * Method Name	: createUser()
	 * Purpose		: to create the new user in actiTime
	 * 
	 *************************************/
	public String createUser(WebDriver oBrowser, Map<String, String> objData) {
		String userName = null;
		String firstName = null;
		try {
			Assert.assertTrue(appInd.clickObject(oBrowser, UsersPage.obj_USERS_Menu));
			appDep.waitForElement(oBrowser, UsersPage.obj_AddUsers_Btn, "Clickable", "", minWaitTime);
			Assert.assertTrue(appInd.clickObject(oBrowser, UsersPage.obj_AddUsers_Btn));
			appDep.waitForElement(oBrowser, UsersPage.obj_CreateUser_Btn, "Clickable", "", minWaitTime);
			reports.writeReport(oBrowser, "Screenshot", "Add User page has opened");
			firstName = objData.get("User_FirstName")+appInd.getDateTime("hhmmss");
			userName = objData.get("User_LastName") + ", " + firstName;
			Assert.assertTrue(appInd.setObject(oBrowser, UsersPage.obj_Users_FirstName_Edit, firstName));
			Assert.assertTrue(appInd.setObject(oBrowser, UsersPage.obj_Users_LastName_Edit, objData.get("User_LastName")));
			Assert.assertTrue(appInd.setObject(oBrowser, UsersPage.obj_Users_Email_Edit, objData.get("User_Email")));
			Assert.assertTrue(appInd.setObject(oBrowser, UsersPage.obj_Users_UserName_Edit, objData.get("User_UserName")));
			Assert.assertTrue(appInd.setObject(oBrowser, UsersPage.obj_Users_Password_Edit, objData.get("User_Password")));
			Assert.assertTrue(appInd.setObject(oBrowser, UsersPage.obj_Users_RetypePassword_Edit, objData.get("User_RetypePassword")));
			Assert.assertTrue(appInd.clickObject(oBrowser, UsersPage.obj_CreateUser_Btn));
			appDep.waitForElement(oBrowser, By.xpath(UsersPage.obj_Created_UserName_Link.replace("%", userName)), "Clickable", "", maxWaitTime);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(UsersPage.obj_Created_UserName_Link.replace("%", userName))));
			reports.writeReport(oBrowser, "Screenshot", "The new user '"+userName+"' was created successful");
			return userName;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'createUser()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'createUser()' method. " + e);
			return null;
		}finally {userName = null;}
	}
	
	
	
	/*************************************
	 * Method Name	: deleteUser()
	 * Purpose		: to delete the user in actiTime
	 * 
	 *************************************/
	public boolean deleteUser(WebDriver oBrowser, String userName) {
		try {
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(UsersPage.obj_Created_UserName_Link.replace("%", userName))));
			appInd.threadSleep(2000);
			appDep.waitForElement(oBrowser, UsersPage.obj_SaveChanges_Btn, "Clickable", "", minWaitTime);
			Assert.assertTrue(appInd.clickObject(oBrowser, UsersPage.obj_DeleteUser_Btn));
			appDep.waitForElement(oBrowser, null, "Alert", "", minWaitTime);
			oBrowser.switchTo().alert().accept();
			appInd.threadSleep(2000);
			Assert.assertTrue(appInd.verifyElementNotPresent(oBrowser, By.xpath(UsersPage.obj_Created_UserName_Link.replace("%", userName))));
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'deleteUser()' method. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'deleteUser()' method. " + e);
			return false;
		}
	}
	
	
	
	
	/*************************************
	 * Method Name	: editUser()
	 * Purpose		: to edit the user in actiTime
	 * 
	 *************************************/
	public String editUser(WebDriver oBrowser, String userName, Map<String, String> objData) {
		String firstName = null;
		String lastName = null;
		String updateUserName = null;
		try {
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath(UsersPage.obj_Created_UserName_Link.replace("%", userName))));
			appDep.waitForElement(oBrowser, UsersPage.obj_SaveChanges_Btn, "Clickable", "", minWaitTime);
			
			if(objData.get("obj_Users_FirstName_Edit")!=null) {
				firstName = objData.get("obj_Users_FirstName_Edit")+appInd.getDateTime("hhmmss");
			}else firstName = (userName.split(", "))[1];
			if(objData.get("obj_Users_LastName_Edit")!=null) {
				lastName = objData.get("obj_Users_LastName_Edit");
			}else lastName = (userName.split(", "))[0];
			
			if(objData.get("User_MiddleInitial")!=null) {
				updateUserName = lastName + ", " + firstName + " " + objData.get("User_MiddleInitial") + ".";
			}else {
				updateUserName = lastName + ", " + firstName;
			}
			Assert.assertTrue(appInd.setObjectBasedOnData(oBrowser, UsersPage.obj_Users_FirstName_Edit, firstName));
			Assert.assertTrue(appInd.setObjectBasedOnData(oBrowser, UsersPage.obj_Users_MiddleInitial_Edit, objData.get("User_MiddleInitial")));
			Assert.assertTrue(appInd.setObjectBasedOnData(oBrowser, UsersPage.obj_Users_LastName_Edit, objData.get("User_LastName")));
			Assert.assertTrue(appInd.setObjectBasedOnData(oBrowser, UsersPage.obj_Users_Email_Edit, objData.get("User_Email")));
			Assert.assertTrue(appInd.setObjectBasedOnData(oBrowser, UsersPage.obj_Users_UserName_Edit, objData.get("User_UserName")));
			Assert.assertTrue(appInd.setObjectBasedOnData(oBrowser, UsersPage.obj_Users_Password_Edit, objData.get("User_Password")));
			Assert.assertTrue(appInd.setObjectBasedOnData(oBrowser, UsersPage.obj_Users_RetypePassword_Edit, objData.get("User_RetypePassword")));
			
			Assert.assertTrue(appInd.clickObject(oBrowser, UsersPage.obj_SaveChanges_Btn));
			appDep.waitForElement(oBrowser, By.xpath(UsersPage.obj_Created_UserName_Link.replace("%", updateUserName)), "Clickable", "", maxWaitTime);
			Assert.assertTrue(appInd.verifyElementPresent(oBrowser, By.xpath(UsersPage.obj_Created_UserName_Link.replace("%", updateUserName))));
			reports.writeReport(oBrowser, "Screenshot", "The user '"+userName+"' was updated successful");
			return updateUserName;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'editUser()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'editUser()' method. " + e);
			return null;
		}finally {updateUserName = null;}
	}
}
