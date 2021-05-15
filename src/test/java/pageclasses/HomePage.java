package pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.Base;
import base.BaseClassPage;

public class HomePage extends BaseClassPage {
	
	public HomePage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/footer/div/div[1]/div[2]/div[2]/a[3]/span")
	public WebElement hospitalSearchURL;
	
	@FindBy(xpath="//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/input")
	public WebElement locationTextbox;
	
	@FindBy(xpath="//*[@id=\"c-omni-container\"]/div/div[1]/div[2]/div[1]/div[2]/span/div")
	public WebElement location;

	@FindBy(xpath="//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[3]")
	public WebElement sortByAvailability;
	
	@FindBy(xpath="//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[4]/span/span")
	public WebElement allFilters;
	
	@FindBy(xpath="//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[2]/div/div/div/label[1]")
	public WebElement sortByParking;

	@FindBy(xpath="//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div[2]/div[4]/a/div[1]")
	public WebElement diagnosticLink;
	
	@FindBy(xpath="//div[@class=\"u-margint--standard o-f-color--primary\"]")
	public WebElement topcities;
	
	public void searchForHospitals() {
		try {
			hospitalSearchURL.click();
			waitLoad(2);
			logger.log(Status.PASS, "Clicked the Hospital Search URL");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void enterLocation() {
		try {
			locationTextbox.clear();
			enterText(locationTextbox,"city");
			waitLoad(2);
			location.click();
			logger.log(Status.PASS, "Location entered succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void sortBy() {
		try {
			waitLoad(2);
			sortByAvailability.click();
			waitLoad(2);
			allFilters.click();
			waitLoad(2);
			sortByParking.click();
			logger.log(Status.PASS, "Sorting by Availability done successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public void scroll() {
		try {
			for (int i = 0; i < 16; i++) {
				scrollBy();
				waitLoad(3);
			}
			logger.log(Status.PASS, "Scroll done successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public DiagnosticsPage clickDiagnosticsLink() {
		logger.log(Status.INFO, "Clicking the Diagnostics Link");
		diagnosticLink.click();
		logger.log(Status.PASS, "Clicked the Diagnostics Link");
		DiagnosticsPage diagnosticspage = new DiagnosticsPage(driver, logger);
		PageFactory.initElements(driver, diagnosticspage);
		return diagnosticspage;
	}
	
}
