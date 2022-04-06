package Library;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

/**
 * @author mrizkallah
 *
 */

public class BaseClass {
	public static WebDriver driver;
	String testName;
	public static HelpingMethods lib;
	String url;
	String headless;
	String browserName;
	public SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void beforeAll() {
		System.out.println("Starting all the tests...");
		JavaPropertiesManager prop = new JavaPropertiesManager("resources//config.properties");
		browserName = prop.readProperty("browser").toLowerCase();
		url = prop.readProperty("url");
		headless = prop.readProperty("headless");
		lib = new HelpingMethods(driver);

	}

	@BeforeMethod
	public void startTest(Method method) {
		try {

			this.testName = method.getName();
			System.out.println(testName + " Started...");
			driver = lib.initiateTheDriver(browserName, headless);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		try {
			
			if (result.getStatus() == ITestResult.SUCCESS) {
				System.out.println(testName + " Passed");
			} else if (result.getStatus() == ITestResult.FAILURE) {
				System.out.println(testName + " Failed");
			} else if (result.getStatus() == ITestResult.SKIP) {
				System.out.println(testName + " Skiped");
			}
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}

	}
	
	@AfterTest
	public void tearDown() {
		softAssert.assertAll();
	}

}
