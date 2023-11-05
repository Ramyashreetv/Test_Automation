package com.test.automation;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class GUI_Automation {


	WebDriver driver;
    Properties properties = new Properties();
    FileInputStream input = null;

	@BeforeClass
	public void testSetup() throws IOException {

		input = new FileInputStream("automationtest.properties");
		properties.load(input);
		if(properties.getProperty("browser_name").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Ramya\\Selenium\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(properties.getProperty("browser_name").equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver", "C:\\Ramya\\Selenium\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else{
			System.setProperty("webdriver.gecko.driver", "C:\\Ramya\\Selenium\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

	}

	@BeforeMethod
	public void openBrowser() {
		driver.get(properties.getProperty("url"));
		driver.findElement(By.cssSelector("a[title*='Sign in']")).click();
		driver.findElement(By.xpath("//*[contains(text(), 'Accept All')]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class*='toggle-normal-form'] a[href='/users/sign_up'][class*='sign-up-link']")));
		driver.findElement(By.cssSelector("div[class*='toggle-normal-form'] a[href='/users/sign_up'][class*='sign-up-link']")).click();
		System.out.println("We are currently on the following URL" + driver.getCurrentUrl());
	}

	@Test(priority=1,groups = { "functionaltest", "regressiontest" },description = "This method validates the sign up functionality")
	public void signUp() throws InterruptedException {
		driver.findElement(By.id("user_full_name")).sendKeys("someone");
		driver.findElement(By.id("user_email_login")).sendKeys("someone@gmail.com");
		driver.findElement(By.id("user_password")).sendKeys("somepassword@123");
		driver.findElement(By.xpath("//input[@name='terms_and_conditions']")).click();
		driver.findElement(By.id("user_submit")).click();

	}
	
	/*@Test(priority=2,groups = { "functionaltest", "regressiontest" },description = "This method validates the sign in functionality")
	public void signIn() {
		driver.findElement(By.id("user_email_login")).sendKeys("someone@gmail.com");
		driver.findElement(By.id("user_password")).sendKeys("somepassword@123");
		driver.findElement(By.xpath("//input[@name='terms_and_conditions']")).click();
		driver.findElement(By.id("user_submit")).click();

	}*/

	@AfterMethod
	public void postSignUp() {
		System.out.println(driver.getCurrentUrl());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}