package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.DateUtils;
import utilities.ExtentReportManager;

public class Base {
	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static WebDriverWait wait;
	public static List<WebElement> cities = new ArrayList<WebElement>();
	public static List<String> topCities = new ArrayList<String>();
	public static  ExtentReports report = ExtentReportManager.getReportInstance();
	public static ExtentTest logger;
	static String[] names = new String[137];
	public static String fileName = System.getProperty("user.dir") + "\\test-output\\ExcelOutput\\output.xlsx";
	public static XSSFWorkbook wb = new XSSFWorkbook();


	/****************** Reporting Functions ***********************/
	public static void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	public static void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		// takeScreenShot();
		// Assert.fail(reportString);
	}

	public static void takeScreenShot() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\snapshots"
				+ DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\src\\test\\resources\\snapshots"
					+ DateUtils.getTimeStamp() + ".png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void setPropertiesFile() {
		try {
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
			prop.load(file);
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}
	}

	/****************** Invoke Browser ***********************/
	public static void invokeBrowser(String browserName) {
		try {
			if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("Mozila")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new OperaDriver();
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 10);
	}

	/****************** Open URL ***********************/
	public static void openURL(String websiteURLKey) {
		try {
			driver.get(prop.getProperty(websiteURLKey));
			reportPass(websiteURLKey + " Identified Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/****************** Identify Element ***********************/
	public static WebElement getElement(String locatorKey) {
		WebElement element = null;

		try {
			if (locatorKey.endsWith("_id")) {
				element = driver.findElement(By.id(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.startsWith("xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_className")) {
				element = driver.findElement(By.className(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_CSS")) {
				element = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_LinkText")) {
				element = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_PartialLinkText")) {
				element = driver.findElement(By.partialLinkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_Name")) {
				element = driver.findElement(By.name(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else {
				reportFail("Failing the Testcase, Invalid Locator " + locatorKey);
			}
		} catch (Exception e) {

			// Fail the TestCase and Report the error
			reportFail(e.getMessage());
			e.printStackTrace();
		}

		return element;
	}

	/****************** Identify Elements ***********************/
	public static List<WebElement> getElements(String locatorKey) {
		List<WebElement> list = new ArrayList<WebElement>();

		try {
			if (locatorKey.endsWith("_id")) {
				list = driver.findElements(By.id(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.startsWith("xpath")) {
				list = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_ClassName")) {
				list = driver.findElements(By.className(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_CSS")) {
				list = driver.findElements(By.cssSelector(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_LinkText")) {
				list = driver.findElements(By.linkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_PartialLinkText")) {
				list = driver.findElements(By.partialLinkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else if (locatorKey.endsWith("_Name")) {
				list = driver.findElements(By.name(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);
			} else {
				reportFail("Failing the Testcase, Invalid Locator " + locatorKey);
			}
		} catch (Exception e) {

			// Fail the TestCase and Report the error
			reportFail(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	/****************** Enter Text ***********************/
	public static void enterText(WebElement Key, String data) {
		try {
			Key.sendKeys(prop.getProperty(data));
			reportPass(data + " - Entered successfully in locator Element : " + Key);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Select List Drop Down ******************/
	public void SelectElementInList(WebElement locator, String Value) {
		try {
			Select select = new Select(locator);
			select.selectByVisibleText(prop.getProperty(Value));
			logger.log(Status.INFO, "Selected the Defined Value : " + prop.getProperty(Value));
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void scrollBy() {
		try {
			JavascriptExecutor jse6 = (JavascriptExecutor) driver;
			jse6.executeScript("window.scrollBy(0, 1950)");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	public void switchToOtherWindow() {
		try {
			Set<String> list;
			list = driver.getWindowHandles();
			Iterator<String> iterator = list.iterator();
			String b = iterator.next();
			String b1 = iterator.next();
			driver.switchTo().window(b1);
			logger.log(Status.INFO, "Switched to other window");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	public void getAlertText() {
		try {
			String av = driver.switchTo().alert().getText();
			System.out.println(av);
			logger.log(Status.INFO, "Alert captured");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeExcel(String SheetName) throws IOException {
		int x;

		if (SheetName == "HospitalNames") {
			XSSFSheet sheet1 = wb.createSheet(SheetName);
			int row = 0;

			for (x = 0; x < names.length; x++) {
				try {
					if (names[x].length() > 2) {
						sheet1.createRow(row).createCell(0).setCellValue(names[x]);
						row++;
					}
				} catch (Exception e) {

				}
			}
		} else if (SheetName == "TopCities") {
			XSSFSheet sheet2 = wb.createSheet(SheetName);
			for (x = 0; x < topCities.size(); x++) {
				sheet2.createRow(x).createCell(0).setCellValue(topCities.get(x));
			}

			FileOutputStream writeFile = new FileOutputStream(new File(fileName));
			wb.write(writeFile);
			writeFile.close();
			wb.close();
		}
	}

	/**************** Core Application Functions ******************/
	public void sortHospitalsByRating(double Ratings) {
		String ratings;
		Float rating;
		String Hname;
		int j = 1;

		for (int i = 2; i < 137; i++) {

			try {
				ratings = driver.findElement(By.xpath("//*[@id='container']/div[3]/div/div[2]/div[1]/div/div[3]/div["
						+ i + "]/div/div[1]/div[2]/div/div[1]/div/div/span[1]")).getText();
				rating = Float.parseFloat(ratings);

				if (rating > Ratings) {
					Hname = driver.findElement(By.xpath("//*[@id='container']/div[3]/div/div[2]/div[1]/div/div[3]/div["
							+ i + "]/div/div[1]/div[2]/div/div[1]/div/div/span[1]/ancestor::div[6]//h2")).getText();
					// System.out.println(j + ") " + Hname + " Rating=>" + rating);
					names[i] = Hname;
					j++;
				}
			} catch (Exception e) {

			}
		}
		logger.log(Status.INFO, "Sorted by Ratings");
	}

	/****************** Close Browser ***********************/
	public void closeBrowser() {
		driver.close();
		logger.log(Status.INFO, "Browser closed");
	}

	public void quitBrowser() {
		driver.quit();
		logger.log(Status.INFO, "Browser closed");
	}

	public void endReport() {
		report.flush();
	}

}
