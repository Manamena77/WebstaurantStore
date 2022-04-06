/**
 * 
 */
package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Library.BaseClass;
import Library.HelpingMethods;

/**
 * @author mrizkallah
 *
 */
public class LandingPage extends BaseClass {

	String expectedPageTitle = "WebstaurantStore: Restaurant Supplies & Foodservice Equipment";
	String searchFieldLocator = "//input[@id='searchval']";
	String productsCategoryLocator = "//li[@id='product-categories']";

	
	public void verifyHomePageLoadedSuccessfully() {
		//Verify Page title
		String actualPageTitle = driver.getTitle();
		Assert.assertEquals(expectedPageTitle, actualPageTitle);
		
		//Verify major functionalities displayed
		WebElement searchField = driver.findElement(By.xpath(searchFieldLocator));
		lib.waitForElementVisible(searchField);
		WebElement productsCategory = driver.findElement(By.xpath(productsCategoryLocator));
		Assert.assertTrue(searchField.isDisplayed() && productsCategory.isDisplayed());
	}
}

