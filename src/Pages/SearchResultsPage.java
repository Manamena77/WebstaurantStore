/**
 * 
 */
package Pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class SearchResultsPage extends BaseClass {

	// Locators and fields used across methods/Classes
	String searchFieldLocator = "//input[@id='searchval']";
	String searchButtonLocator = "//button[contains(text(),'Search')]";
	String itemName = "stainless work table";
	String resultsLocator = "//a[contains(@class,'block font-semibold text-sm')]";
	String expectedWord = "Table";
	String nextButtonLocator = "//li[@class='rc-pagination-next']/a[contains(@aria-label,'Go to page')]";
	String accessoriesPromptLocator = "//div[@role='dialog']";
	String prompAddToCartLocator = "//button[@aria-label='Submit Feedback'][text()='Add To Cart'][@type='submit']";
	String cartButtonLocator = "//span[text()='Cart']";
	String viewCartLocator = "//div[1]/p[1]/div[2]/div[3]/a[contains(text(),'View Cart')]";
	String countertopEdgeLocator = "//select[@title='Countertop Edge']";
	String finishUpgradeLocator = "//select[@title='Finish Upgrade']";
	String sinkBowlLocator = "//select[@title='Sink Bowl']";
	String overshelfLocator = "//select[@title='Overshelf']";
	public static String lastItemName;

	// this method will verify that all results contain the task word "Table"
	public void verifySearchFunctionality() {
		try {

			// Call the searchItems methods that will return a list of all items
			// names/summaries
			List<String> itemsDiscreptions = new SearchResultsPage().searchItems();

			// Create a counter for the results that won't contain the expected word
			int wrongItemsCount = 0;

			// This will log all the results that won't contain the expected word
			for (String discreption : itemsDiscreptions) {
				if (!discreption.contains(expectedWord)) {
					System.out
							.println("*** This product: " + discreption + " Doesn't contain the word :" + expectedWord);
					wrongItemsCount++;
				}
			}

			// After logging all the unexpected results it will mark the test as failed
			if (wrongItemsCount > 0) {
				Assert.assertTrue(false);

				// Otherwise will pass the test with verification message for the total number
				// of results
			} else {
				System.out.println(
						"Passed, All " + itemsDiscreptions.size() + " results contains the word " + expectedWord);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	// This method will search for any product, and return a list of the items names
	// in all results pages
	public List<String> searchItems() {
		List<String> itemsDiscreption = new ArrayList<String>();
		try {

			// Sent the search item (from the field (itemName))
			WebElement searchField = driver.findElement(By.xpath(searchFieldLocator));
			WebElement searchbuton = driver.findElement(By.xpath(searchButtonLocator));
			searchField.sendKeys(itemName);
			searchbuton.click();

			WebElement nextButton = driver.findElement(By.xpath(nextButtonLocator));
			List<WebElement> resultsList = driver.findElements(By.xpath(resultsLocator));
			List<WebElement> isNextButtonActive = driver.findElements(By.xpath(nextButtonLocator));

			// This will keep on clicking next results page (dynamically regardless of the
			// number of pages)
			while (isNextButtonActive.size() > 0) {
				isNextButtonActive = driver.findElements(By.xpath(nextButtonLocator));
				resultsList = driver.findElements(By.xpath(resultsLocator));

				// This will grap the items names/summary and store it to the returned List.
				for (WebElement element : resultsList) {
					String itemDescription = element.getText();
					itemsDiscreption.add(itemDescription);
				}
				// This logic to handle the items in the last page
				if (isNextButtonActive.size() > 0) {
					lib.scrollToElement(By.xpath(nextButtonLocator));
					lib.waitForElementClickable(By.xpath(nextButtonLocator));
					nextButton = driver.findElement(By.xpath(nextButtonLocator));
					nextButton.click();
				}
			}

			// This will store the name/summary of he last item in the last page so we can
			// verify it is correctly added to the cart.
			SearchResultsPage.lastItemName = resultsList.get(resultsList.size() - 1).getText();
			} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		return itemsDiscreption;

	}

	// This method is a work-around since not all results contain the Task word
	// "Table"
	public void addLastItemToCart() {
		try {

			// Search for the item and itorate through the results pages
			new SearchResultsPage().searchItems();
			List<WebElement> results = driver.findElements(By.xpath("//div/div[4]/form[1]/div[1]/div[1]/input[2]"));

			// Add the last product in the last page dynamically to the cart
			WebElement lastProductInLastPage = results.get(results.size() - 1);
			lastProductInLastPage.click();

			// If accessories prompt dynamically handle up to 4 types of accessories "Can
			// expand based on test needs"
			if (lib.isElementPresent(By.xpath(accessoriesPromptLocator))) {
				Thread.sleep(2000);
				if (lib.isElementPresent(By.xpath(overshelfLocator))) {
					lib.selectFirstItem(By.xpath(overshelfLocator));
				}
				if (lib.isElementPresent(By.xpath(countertopEdgeLocator))) {
					lib.selectFirstItem(By.xpath(countertopEdgeLocator));
				}
				if (lib.isElementPresent(By.xpath(finishUpgradeLocator))) {
					lib.selectFirstItem(By.xpath(finishUpgradeLocator));
				}
				if (lib.isElementPresent(By.xpath(sinkBowlLocator))) {
					lib.selectFirstItem(By.xpath(sinkBowlLocator));
				}

				// After handling the accessories click on the add to cart button within the
				// prompt
				WebElement prompt = driver.findElement(By.xpath(accessoriesPromptLocator));
				WebElement prompAddToCart = prompt.findElement(By.xpath(prompAddToCartLocator));
				prompAddToCart.click();

			}

			// Otherwise will straight-away just wait for the add to card floating element
			// to disappear to click on the cart
			WebElement viewCArt = driver.findElement(By.xpath(viewCartLocator));
			lib.waitForElementNotVisible(viewCArt);
			Thread.sleep(3000);
			WebElement cartButton = driver.findElement(By.xpath(cartButtonLocator));
			lib.waitForElementClickable(cartButton);
			lib.scrollToElement(cartButton);
			Thread.sleep(2000);
			
			//Open Cart page
			cartButton.click();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	/**
	 * @return the lastItemName so it can be used in the cart page
	 */
	public static String getLastItemName() {
		return lastItemName;
	}

}
