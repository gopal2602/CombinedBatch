package com.sg.reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sg.driver.DriverScript;

public class ReportUtils extends DriverScript{
	/*************************************
	 * Method Name	: startExtentReport()
	 * Purpose		: creates the ExtentReport html file
	 * 
	 *************************************/
	public ExtentReports startExtentReport(String fileName) {
		String strReportPath = null;
		File objReportPath = null;
		File objScreenshot = null;
		try {
			strReportPath = System.getProperty("user.dir") + "//target//Reports";
			
			objReportPath = new File(strReportPath);
			if(!objReportPath.exists()) {
				objReportPath.mkdirs();
			}
			
			screenshotDir = strReportPath +"//screenshot";
			objScreenshot = new File(screenshotDir);
			if(!objScreenshot.exists()) {
				objScreenshot.mkdirs();
			}
			
			extent = new ExtentReports(strReportPath + "//" + fileName+".html", false);
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.addSystemInfo("OS Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment", propData.get("environment"));
			extent.loadConfig(new File(System.getProperty("user.dir") + "//extent-config.xml"));
			return extent;
		}catch(Exception e) {
			System.out.println("Exception in 'startExtentReport()' method. " + e);
			return null;
		}
		finally {
			strReportPath = null;
			objReportPath = null;
			objScreenshot = null;
		}
	}

	
	
	/*************************************
	 * Method Name	: endExtentReport()
	 * Purpose		: write to ExtentReport html file
	 * 
	 *************************************/
	public void endExtentReport(ExtentTest test) {
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e) {
			System.out.println("Exception in 'endExtentReport()' method. " + e);
		}
	}
	
	
	
	/*************************************
	 * Method Name	: writeReport()
	 * Purpose		: writes to reports to the extentReport html file
	 * 
	 *************************************/
	public void writeReport(WebDriver oBrowser, String status, String description) {
		try {
			switch(status.toLowerCase()) {
				case "pass":
					test.log(LogStatus.PASS, description);
					break;
				case "fail":
					test.log(LogStatus.FAIL, description+" : "
							+ test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				case "warning":
					test.log(LogStatus.WARNING, description);
					break;
				case "info":
					test.log(LogStatus.INFO, description);
					break;
				case "exception":
					test.log(LogStatus.FATAL, description+" : "
							+ test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				case "screenshot":
					test.log(LogStatus.PASS, description+" : "
							+ test.addScreenCapture(captureScreenshot(oBrowser)));
					break;
				default:	
			}
		}catch(Exception e) {
			System.out.println("Exception in 'writeReport()' method. " + e);
		}
	}
	
	
	/*************************************
	 * Method Name	: captureScreenshot()
	 * Purpose		: captures the screenshots
	 * 
	 *************************************/
	public String captureScreenshot(WebDriver oBrowser) {
		File objSrc = null;
		File objDes = null;
		String strDest = null;
		try {
			strDest = screenshotDir + "\\screenshots_" + appInd.getDateTime("hhmmss")+".png";
			TakesScreenshot ts = (TakesScreenshot) oBrowser;
			objSrc = ts.getScreenshotAs(OutputType.FILE);
			objDes = new File(strDest);
			FileHandler.copy(objSrc, objDes);
			return strDest;
		}catch(Exception e) {
			System.out.println("Exception in 'captureScreenshot()' method. " + e);
			return null;
		}
		finally
		{
			objSrc = null;
			objDes = null;
			strDest = null;
		}
	}
}
