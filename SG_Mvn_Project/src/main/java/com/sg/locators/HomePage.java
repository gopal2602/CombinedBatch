package com.sg.locators;

import org.openqa.selenium.By;

public class HomePage {
	public static By obj_UserName_Edit = By.xpath("//input[@id='username']");
	public static By obj_Password_Edit = By.xpath("//input[@name='pwd']");
	public static By obj_Login_Btn = By.xpath("//a[@id='loginButton']");
	public static By obj_LoginLogo_Img = By.xpath("//img[contains(@src, 'timer.png')]");
	public static By obj_TimeTrack_PageHeader = By.xpath("//td[@class='pagetitle']");
	public static By obj_LoginHeader_Text = By.cssSelector("td[id='headerContainer']");
	public static By obj_Shortcut_Window = By.xpath("//div[@style='display: block;']");
	public static By obj_Shortcut_Window_Close_Btn = By.id("gettingStartedShortcutsMenuCloseId");
	public static By obj_Logout_Link = By.xpath("//a[@id='logoutLink']");
}
