package hm.com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import hm.com.utilities.ExcelReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	/*
	 * WebDriver - Done
	 * Properties - Done
	 * Logs - Log4J jar file in POM
	 * ExtentReports
	 * DB
	 * Excel
	 * Mail
	 *
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log =Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"/src/test/resources/excel/testData.xlsx");
	public static WebDriverWait wait;


	@BeforeSuite
	public void setUp() {

		if(driver == null) {

			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/properties/Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config File Loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/properties/OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("Object Repository File Loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(config.getProperty("browser").equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(config.getProperty("browser").equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			log.debug("Chrome Launched ");
		}
		else if(config.getProperty("browser").equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}

		driver.get(config.getProperty("testsiteURL"));
		log.debug("Navigated to testsiteURL : "+config.getProperty("testsiteURL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
	}

	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			return true;

		} catch(NoSuchElementException e) {
			return false;

		}
	}

	@AfterSuite
	public void tearDown() {
		if(driver!=null) {
			driver.quit();
			log.debug("Execution completed");
		}

	}

}
