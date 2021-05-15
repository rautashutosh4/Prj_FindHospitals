package testcases;

import java.io.IOException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.Base;
import base.BaseClassPage;
import pageclasses.CorporateWellnessPage;
import pageclasses.DiagnosticsPage;
import pageclasses.HomePage;

public class TS_FindHospitals extends Base {

	HomePage homepage;
	DiagnosticsPage diagnosticspage;
	CorporateWellnessPage corporatewellnesspage;

	@BeforeTest
	public void invokeBrowser() {
		logger = report.createTest("invokeBrowser");
		logger.log(Status.PASS, "Browser started succesfully");
		setPropertiesFile();
		invokeBrowser("Chrome");
	}

	@AfterTest
	public void closeBrowser() {
		quitBrowser();
		endReport();
	}

	@Test(priority = 1)
	public void hospitalSearch() throws InterruptedException, IOException {
		logger = report.createTest("HospitalSearch");

		BaseClassPage base = new BaseClassPage(driver, logger);
		PageFactory.initElements(driver, base);
		homepage = base.OpenApplication();
		homepage.searchForHospitals();
		homepage.enterLocation();
		homepage.sortBy();
		homepage.scroll();
		sortHospitalsByRating(3.5);
		writeExcel("HospitalNames");
	}

	@Test(priority = 2)
	public void topCities() throws InterruptedException, IOException {
		logger = report.createTest("getTopCities");
		diagnosticspage = homepage.clickDiagnosticsLink();
		diagnosticspage.getTopCities();
		writeExcel("TopCities");
	}

	@Test(priority = 3)
	public void getAlert() throws InterruptedException {
		logger = report.createTest("getAlert");
		corporatewellnesspage = diagnosticspage.clickCorporateWellnessLink();

		corporatewellnesspage.enterName();
		corporatewellnesspage.enterorgName();
		corporatewellnesspage.enterEmailId();
		corporatewellnesspage.enterPhoneno();
		corporatewellnesspage.selectOrgSize();
		corporatewellnesspage.submit();
		corporatewellnesspage.getAlertText();
	}
}
