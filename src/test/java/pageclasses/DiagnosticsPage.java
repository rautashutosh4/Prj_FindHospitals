package pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseClassPage;

public class DiagnosticsPage extends BaseClassPage {

	public DiagnosticsPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span[1]")
	public WebElement ForProvidersLink;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/div/div[4]/a")
	public WebElement CorporateWellnessLink;

	public CorporateWellnessPage clickCorporateWellnessLink() {
		logger.log(Status.INFO, "Clicking the Corporate Wellness Link");
		driver.navigate().back();
		ForProvidersLink.click();
		CorporateWellnessLink.click();
		switchToOtherWindow();
		logger.log(Status.PASS, "Clicked the Corporate Wellness Link");
		CorporateWellnessPage corporatewellnesspage = new CorporateWellnessPage(driver, logger);
		PageFactory.initElements(driver, corporatewellnesspage);
		return corporatewellnesspage;
	}

	public void getTopCities() {
		try {
			cities = getElements("xpath_topcities");
			for (WebElement e : cities) {
				topCities.add(e.getText());
				logger.log(Status.PASS, "Top Cities got successfully");
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

}