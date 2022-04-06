/**
 * 
 */
package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Library.BaseClass;

/**
 * @author mrizkallah
 *
 */
public class CartPage extends BaseClass {

	String firstItemInCartLocator = "//div[2]/div[1]/div[2]/span[1]/a[1]";
	String emptyCartLocator = "//a[normalize-space()='Empty Cart']";
	String confirmEmptyCartLocator = "//button[contains(text(),'Empty Cart')]";

	// This method will verify that the last item was added to the cart
	public void verifyItemAddedToCart() {
		try {

			// Will get the item name in the cart
			WebElement firstItemInCart = driver.findElement(By.xpath(firstItemInCartLocator));
			lib.waitForElementClickable(firstItemInCart);
			String actualItemName = firstItemInCart.getText();

			// Will verify that it matches with the name from the last item added from the
			// results page
			String expectedLastItemName = SearchResultsPage.getLastItemName();
			Assert.assertTrue(actualItemName.contains(expectedLastItemName));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	// This method will empty the cart
	public void emptyCart() {
		try {
			lib.waitForElementClickable(By.xpath(emptyCartLocator));
			lib.clickElement(By.xpath(emptyCartLocator));
			lib.waitForElementClickable(By.xpath(confirmEmptyCartLocator));
			lib.clickElement(By.xpath(confirmEmptyCartLocator));
			Thread.sleep(2000);

			// This will verify that the item(s) where deleted successfully
			boolean exist = lib.isElementPresent(By.xpath(firstItemInCartLocator));
			if (exist) {
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}
