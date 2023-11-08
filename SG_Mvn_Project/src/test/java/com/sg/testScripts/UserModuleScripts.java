package com.sg.testScripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.sg.driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/*************************************
	 * Method Name	: TC_LoginAndLogout()
	 * Purpose		: 
	 * test case ID	: TC_101
	 *************************************/
	public boolean TC_LoginAndLogout() {
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		try {
			test = extent.startTest("TC_LoginAndLogout");
			objData = datatable.getExcelTestData(moduleName, "Users", "TC_101");
			
			oBrowser = appInd.launchBrowser(propData.get("browserName"));
			Assert.assertTrue(appDep.navigateURL(oBrowser, propData.get("appURL")));
			Assert.assertTrue(appDep.loginToApplication(oBrowser, objData.get("UserName"), objData.get("Password")));
			Assert.assertTrue(appDep.logoutFromActiTime(oBrowser));
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'TC_LoginAndLogout()' testScript. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'TC_LoginAndLogout()' testScript. " + e);
			return false;
		}finally {
			oBrowser.close();
			oBrowser = null;
			reports.endExtentReport(test);
		}
	}
	
	
	
	/*************************************
	 * Method Name	: TC_createAndDeleteUser()
	 * Purpose		: 
	 * test case ID	: TC_102
	 *************************************/
	public boolean TC_createAndDeleteUser() {
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String userName = null;
		try {
			test = extent.startTest("TC_createAndDeleteUser");
			objData = datatable.getExcelTestData(moduleName, "Users", "TC_102");
			
			oBrowser = appInd.launchBrowser(propData.get("browserName"));
			Assert.assertTrue(appDep.navigateURL(oBrowser, propData.get("appURL")));
			Assert.assertTrue(appDep.loginToApplication(oBrowser, objData.get("UserName"), objData.get("Password")));
			userName = userMethods.createUser(oBrowser, objData);
			Assert.assertTrue(userMethods.deleteUser(oBrowser, userName));
			Assert.assertTrue(appDep.logoutFromActiTime(oBrowser));
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'TC_createAndDeleteUser()' testScript. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'TC_createAndDeleteUser()' testScript. " + e);
			return false;
		}finally {
			oBrowser.close();
			oBrowser = null;
			reports.endExtentReport(test);
		}
	}
	
	
	
	/*************************************
	 * Method Name	: TC_createEditAndDeleteUser()
	 * Purpose		: 
	 * test case ID	: TC_103
	 *************************************/
	public boolean TC_createEditAndDeleteUser() {
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String userName = null;
		try {
			test = extent.startTest("TC_createEditAndDeleteUser");
			objData = datatable.getExcelTestData(moduleName, "Users", "TC_103_1");
			
			oBrowser = appInd.launchBrowser(propData.get("browserName"));
			Assert.assertTrue(appDep.navigateURL(oBrowser, propData.get("appURL")));
			Assert.assertTrue(appDep.loginToApplication(oBrowser, objData.get("UserName"), objData.get("Password")));
			userName = userMethods.createUser(oBrowser, objData);
			
			objData = datatable.getExcelTestData(moduleName, "Users", "TC_103_2");
			userName = userMethods.editUser(oBrowser, userName, objData);
			Assert.assertTrue(userMethods.deleteUser(oBrowser, userName));
			Assert.assertTrue(appDep.logoutFromActiTime(oBrowser));
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception in 'TC_createEditAndDeleteUser()' testScript. " + e);
			return false;
		}catch(AssertionError e) {
			reports.writeReport(oBrowser, "Exception", "Assertion Exception in 'TC_createEditAndDeleteUser()' testScript. " + e);
			return false;
		}finally {
			oBrowser.close();
			oBrowser = null;
			reports.endExtentReport(test);
		}
	}
}
