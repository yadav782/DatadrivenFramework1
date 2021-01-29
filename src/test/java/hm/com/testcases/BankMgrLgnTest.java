package hm.com.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import hm.com.base.TestBase;

public class BankMgrLgnTest extends TestBase {

	@Test
	public void bankMgrLgnTest() throws InterruptedException {

		log.debug("testsiteURL : "+config.getProperty("testsiteURL")+"  Launched and Test started");
		driver.findElement(By.cssSelector(OR.getProperty("bnkMangrLgnBtn"))).click();
		Thread.sleep(5000);
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Login not successfull");
		System.out.println("Assert Passed");
	}

}
