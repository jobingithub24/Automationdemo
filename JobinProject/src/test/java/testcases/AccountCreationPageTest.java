package testcases;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import base.BaseClass;
import dataprovider.DataProviders;
import pageobjects.AccountCreationPage;
import pageobjects.HomePage;
import pageobjects.IndexPage;
import pageobjects.LoginPage;
import utility.Log;

public class AccountCreationPageTest extends BaseClass {
	private IndexPage indexPage;
	private LoginPage loginPage;
	private AccountCreationPage acountCreationPage;
	private HomePage homePage;
	
	@Parameters("browser")
	@BeforeMethod(groups = {"Smoke","Sanity","Regression"})
	public void setup(String browser) {
		launchApp(browser); 
	}
	
	@AfterMethod(groups = {"Smoke","Sanity","Regression"})
	public void tearDown() {
		getDriver().quit();
	}
	
	@Test(groups = "Sanity",dataProvider = "email", dataProviderClass = DataProviders.class)
	public void verifyCreateAccountPageTest(String email) throws Throwable {
		Log.startTestCase("verifyCreateAccountPageTest");
		indexPage= new IndexPage();
		loginPage=indexPage.clickOnSignIn();
		acountCreationPage=loginPage.createNewAccount(email);
		boolean result=acountCreationPage.validateAcountCreatePage();
		Assert.assertTrue(result);
		Log.endTestCase("verifyCreateAccountPageTest");
	}
	
	@Test(groups = "Regression",dataProvider = "newAcountDetailsData",dataProviderClass = DataProviders.class)
	public void createAccountTest(HashMap<String,String> hashMapValue) throws Throwable {
		Log.startTestCase("createAccountTest");
		indexPage= new IndexPage();
		loginPage=indexPage.clickOnSignIn();
		acountCreationPage=loginPage.createNewAccount(hashMapValue.get("Email"));
		acountCreationPage.createAccount(
				hashMapValue.get("Gender"),
				hashMapValue.get("FirstName"),
				hashMapValue.get("LastName"),
				hashMapValue.get("SetPassword"),
				hashMapValue.get("Day"),
				hashMapValue.get("Month"),
				hashMapValue.get("Year"),
				hashMapValue.get("Company"),
				hashMapValue.get("Address"),
				hashMapValue.get("City"),
				hashMapValue.get("State"),
				hashMapValue.get("Zipcode"),
				hashMapValue.get("Country"),
				hashMapValue.get("MobilePhone"));
		homePage=acountCreationPage.validateRegistration();
		Assert.assertEquals("http://automationpractice.com/index.php?controller=my-account", homePage.getCurrURL());
		Log.endTestCase("createAccountTest");
	}

}