package com.sg.driver;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.sg.methods.AppDependentMethods;
import com.sg.methods.AppIndependentMethods;
import com.sg.methods.Datatable;
import com.sg.methods.TaskModuleMethods;
import com.sg.methods.UserModuleMethods;
import com.sg.reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static UserModuleMethods userMethods = null;
	public static TaskModuleMethods taskMethods = null;
	public static ReportUtils reports = null;
	public static Map<String, String> propData = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String screenshotDir = null;
	public static int minWaitTime = 60;
	public static int maxWaitTime = 120;
	public static String moduleName = null;
	

	@BeforeSuite
	public void loadClasses() {
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			datatable = new Datatable();
			userMethods = new UserModuleMethods();
			taskMethods = new TaskModuleMethods();
			reports = new ReportUtils();
			propData = appInd.getPropData("config");
			extent = reports.startExtentReport("TestAutomationReport");
		}catch(Exception e) {
			System.out.println("Exception in the 'loadClasses()' method. " + e);
		}
	}
	
	
	@DataProvider(name = "testScripts", parallel = false)
	public Object[][] dataProvider(){
		return datatable.createDataProvider("ExecutionController", "Controller");
	}
	
	
	@Test(dataProvider = "testScripts")
	public void executeScript(Map<String, String> data) {
		Class cls = null;
		Object obj = null;
		Method script = null;
		try {
			moduleName = data.get("ModuleName");
			cls = Class.forName(data.get("ClassName"));
			obj = cls.getDeclaredConstructor().newInstance();
			script = obj.getClass().getMethod(data.get("TestScriptName"));
			
			boolean blnRes = (boolean) script.invoke(obj);
			if(blnRes == true) {
				reports.writeReport(null, "Pass", "The Test scrip '"+data.get("TestScriptName")+"' was passed");
			}else {
				reports.writeReport(null, "Fail", "The Test scrip '"+data.get("TestScriptName")+"' was failed");
			}
		}catch(Exception e) {
			System.out.println("Exception in 'executeScript()' method");
		}
		finally {
			cls = null;
			obj = null;
			script = null;
		}
	}
	
//	@AfterSuite
//	public void tearDown1() {
//		reports.endExtentReport(test);
//	}
	
	@AfterSuite
	public void tearDown() {
		reports.endExtentReport(test);
	}
}
