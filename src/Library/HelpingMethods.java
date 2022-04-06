package Library;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import io.github.bonigarcia.wdm.managers.SafariDriverManager;

/**
 * @author mrizkallah
 *
 */
public class HelpingMethods {

	private WebDriver driver;

	public HelpingMethods(WebDriver _driver) {
		driver = _driver;
	}

	public HelpingMethods(WebDriver _driver, String browserType) {
		driver = _driver;
	}

	/**
	 * @return the driver
	 */
	public WebDriver initiateTheDriver(String browserName, String headless) {
		try {

			switch (browserName) {

			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions chOptions = new ChromeOptions();
				if (headless.equalsIgnoreCase("true")) {
					chOptions.addArguments("--headless");
				}
				driver = new ChromeDriver(chOptions);
				break;

			case "firefox":
				FirefoxDriverManager.firefoxdriver().setup();
				FirefoxOptions ffOptions = new FirefoxOptions();
				if (headless.equalsIgnoreCase("true")) {
					ffOptions.addArguments("--headless");
				}

				driver = new FirefoxDriver(ffOptions);
				break;

			case "safari":
				SafariDriverManager.safaridriver().setup();
				driver = new SafariDriver();
				break;

			case "edge":
				EdgeDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;

			default:
				System.out.println("This Browser is not configured. Launching Chrome Browser...");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();

			// Will launch Chrome in case of browser exception
			System.out.println("This Browser is not configured. Launching Chrome Browser...");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		return driver;

	}

	public void selectFirstItem(By by) {
		Select select = new Select(driver.findElement(by));
		select.selectByIndex(1);
	}

	public boolean isElementPresent(By by) {

		List<WebElement> elements = driver.findElements(by);
		if (elements.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void clickElement(By by) {
		WebElement element = driver.findElement(by);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void waitForElementVisible(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitForElementVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementPresence(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void waitForElementClickable(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public void waitForElementClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementNotVisible(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void scrollToElement(By by) {
		try {
			WebElement element = driver.findElement(by);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void scrollToElement(WebElement element) {
		try {

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
