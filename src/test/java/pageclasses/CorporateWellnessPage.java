package pageclasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseClassPage;

public class CorporateWellnessPage extends BaseClassPage {

	public CorporateWellnessPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	@FindBy(id = "name")
	public WebElement name;

	@FindBy(id = "organization_name")
	public WebElement orgName;

	@FindBy(id = "official_email_id")
	public WebElement emailId;

	@FindBy(id = "official_phone_no")
	public WebElement phoneno;

	@FindBy(xpath = "//*[@id=\"button-style\"]")
	public WebElement submit;

	public void enterName() {
		try {
			enterText(name, "name");
			logger.log(Status.PASS, "name entered succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void enterorgName() {
		try {
			enterText(orgName, "orgName");
			logger.log(Status.PASS, "orgsnization name entered succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void enterEmailId() {
		try {
			enterText(emailId, "emailId");
			logger.log(Status.PASS, "emailId entered succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void enterPhoneno() {
		try {
			enterText(phoneno, "phoneno");
			logger.log(Status.PASS, "phoneno entered succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void selectOrgSize() {
		try {
			SelectElementInList(getElement("xpathDropdown"), "organization_size");
			waitLoad(2);
			logger.log(Status.PASS, "Organization Size Selected succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void submit() {
		try {
			submit.click();
			waitLoad(2);
			logger.log(Status.PASS, "Submitted succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}
