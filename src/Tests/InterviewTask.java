/**
 * 
 */
package Tests;

import javax.sql.rowset.serial.SerialArray;

import org.testng.annotations.Test;

import Library.BaseClass;
import Pages.CartPage;
import Pages.LandingPage;
import Pages.SearchResultsPage;

/**
 * @author mrizkallah
 *
 */
public class InterviewTask extends BaseClass {

	
	//This one fails since there are results that doesn't contain the word Table
	@Test
	public void verifySearchResultsContainCertainWord() {
		new LandingPage().verifyHomePageLoadedSuccessfully();
		new SearchResultsPage().verifySearchFunctionality();
		new SearchResultsPage().addLastItemToCart();
		new CartPage().verifyItemAddedToCart();
		new CartPage().emptyCart();
	}

	//This one passes as it ignores the "table" word verification
	@Test
	public void workflowForSearchAddItemToCart() {
		LandingPage landing = new LandingPage();
		SearchResultsPage search = new SearchResultsPage();
		CartPage cart = new CartPage();
		landing.verifyHomePageLoadedSuccessfully();
		search.addLastItemToCart();
		cart.verifyItemAddedToCart();
		cart.emptyCart();
	}

}
