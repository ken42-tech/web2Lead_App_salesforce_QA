package com.ken42.Appw2l;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.reporters.jq.Main;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.opencsv.CSVReader;

import java.util.logging.*;
import java.util.logging.FileHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class w2l_portal {

	private static final Exception Exception = null;
	private static WebDriver driver;
	static int time = 1000;
	public static Logger log = Logger.getLogger("w2l_portal");
	static String checkwithalphabet = "sachin";
	static String checkwithnumber = "123";
	static String checkwithlength = "tyuiopkljhbngferdcvvvv";
	static String checkwithSpecialCharacteronly = "%^%&@^&@^&@^&@^&@&";
	static String checkwithinvalidemail = "%^%&@^&@^&@^&@^&@&";
	static String validemail = "sachin@ken42.com";
	static String invalidEmail1 = "a@ggg.c";
	static String invalidEmail2 = "aa.c";
	static String invalidEmail3 = "aa@c";
	static String invalidEmail4 = "@cc.in";
	static String phonenumbervalid10digit = "9898981234";
	static String phonenumberdigitcheck = "989898";
	static String phonenumberalphabetcheck = "qwer";
	static String phonenumberchecklength = "98989812341";

	public static void main(String[] args) throws Throwable {
		String CSV_PATH = "";
		String logFileName = "";
		boolean append = false;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

		if (Utils.checkWindowsOs()) {
			CSV_PATH = "C:\\Users\\Public\\Documents\\alldatatest.csv";
			logFileName = String.format("C:\\Users\\Public\\Documents\\Testresult_%s.HTML", timeStamp);
		} else {
			CSV_PATH = "/Users/Shared/pfs.csv";
			logFileName = String.format("/users/Shared/Testresult_%s.HTML", timeStamp);
		}

		log.info("Found CSV file");
		FileHandler logFile = new FileHandler(logFileName, append);
		logFile.setFormatter(new MyHtmlFormatter());
		log.addHandler(logFile);

		CSVReader csvReader;
		int count = 0;
		csvReader = new CSVReader(new FileReader(CSV_PATH));

		String[] csvCell;

		while ((csvCell = csvReader.readNext()) != null) {

			if (count == 0) {
				count = count + 1;
				continue;
			}

			String w2l_url = csvCell[0];
			String app_url = csvCell[1];
			String Browser = csvCell[2];
			String From = csvCell[3];
			String To = csvCell[4];
			String sal_url = csvCell[40];

			log.info("URL is " + w2l_url);

			int from = Integer.parseInt(From);
			int to = Integer.parseInt(To);

			initDriver1(Browser, w2l_url);

			log.info("**********************Testing for  Portal  " + w2l_url);
			// Below If will execute all Student related test cases
			for (int i = from; i <= to; i++) {
				switch (i) {
				case 1:
					testValidatefirstname(w2l_url, driver, csvCell); // TC-1
					break;
				case 2:
					testValidatelastname(w2l_url, driver, csvCell);
					break;
				case 3:
					testValidatemiddlename(w2l_url, driver, csvCell);
					break;
				case 4:
					testValidateEmail(w2l_url, driver, csvCell);
					break;
				case 5:
					testValidatephonenumber(w2l_url, driver, csvCell);
					break;
				case 6:
					testValidateadditionalphonenumber(w2l_url, driver, csvCell);
					break;
				case 7:
					EmptyW2l_fillform_check(w2l_url, driver, csvCell);
					break;

				case 8:
					fillform_W2l(w2l_url, driver, csvCell);
					break;
				case 9:
					Apploginuploadandsubmit(app_url, driver, csvCell);
					break;
				case 10:
					salesforce_applicationviewanddelete(sal_url, driver, csvCell);
					break;
				case 11:
					Apploginwithinvalidnumber(app_url, driver, csvCell);
					break;

				case 12:
					Application_fillandfilesizecheck(app_url, driver, csvCell);
					break;

				case 13:
					salesforce_loginviewMarkdelete(sal_url, driver, csvCell);
					break;
				case 14:
					validatewithfnfillform_W2l(w2l_url, driver, csvCell);
					break;

				default:

					throw Exception;
				}
			}
			// quitDriver(PFSurl);
			//
			log.info("***************** COMPLETED TESTTING OF PORTAL" + w2l_url);
		}
		SendMail.sendEmail(logFileName);
	}

	@BeforeMethod
	public static void initDriver1(String Browser, String url) throws Exception {
		String ChromeDriver = "";
		String EdgeDriver = "";
		String FirefoxDriver = "";
		if (Utils.checkWindowsOs()) {
			ChromeDriver = "C:\\Users\\Public\\Documents\\chromedriver.exe";
			EdgeDriver = "C:\\Users\\Public\\Documents\\msedgedriver.exe";
			FirefoxDriver = "C:\\Users\\Public\\Documents\\geckodriver.exe";
		} else {
			ChromeDriver = "Users/shared/chromedriver.exe";
			EdgeDriver = "Users/shared/msedgedriver.exe";
			FirefoxDriver = "Users/shared/geckodriver.exe";
			// url="https://ltpct-reg-stg-w2l.ken42.com/form";
		}

		System.out.println("Browser is " + Browser);
		System.out.println("URL is " + url);
		try {
			System.out.println("Browser is ****" + Browser);
			System.out.println("URL is " + url);
			if ("chrome".equals(Browser)) {
				System.setProperty("webdriver.chrome.driver", ChromeDriver);
				ChromeOptions op = new ChromeOptions();
				op.addArguments("--disable-notifications");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(op);
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				driver.manage().window().maximize();

			} else if ("edge".equals(Browser)) {
				System.setProperty("webdriver.edge.driver", EdgeDriver);
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if ("firefox".equals(Browser)) {
				System.setProperty("webdriver.edge.driver", FirefoxDriver);
				FirefoxOptions fx = new FirefoxOptions();
				fx.addArguments("--disable-notifications");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(fx);
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if ("safari".equals(Browser)) {
				driver = new SafariDriver();
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				driver.get(url);
				driver.manage().window().maximize();

			}
			driver.get(url);
		} catch (Exception e) {
			log.warning("UNABLE TO LAUNCH BROWSER \n\n\n");
			System.exit(01);
		}
	}

	@Test(priority = 1)
	public static void testValidatefirstname(String url, WebDriver Driver, String[] csvCell) throws Exception {
		try {
			String web_url = csvCell[0];
			Driver.get(web_url);
			log.warning(
					"Tc1:-Firstname  should contain only maximum 15 alphabet's not more than 15 character, specialcharacter and number's ");

			Utils.callSendkeys(driver, ActionXpath.firstname, checkwithalphabet);

			Utils.cleartext(driver, ActionXpath.firstname);
			Utils.callSendkeys(driver, ActionXpath.firstname, checkwithnumber);
			WebElement alphafirstname = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkfirstnamewithnumber = alphafirstname.getText();
			System.out.println(checkfirstnamewithnumber);
			log.warning("Tc1:-Firstname data validation check with number failed ");

			if (!checkfirstnamewithnumber.equals(null)) {

				log.warning("Tc1:-Firstname data validation check with number failed it's null ");

			}

			Utils.cleartext(driver, ActionXpath.firstname);
			Utils.callSendkeys(driver, ActionXpath.firstname, checkwithlength);
			WebElement number = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkfirstnamewithcharactersize = number.getText();
			System.out.println(checkfirstnamewithcharactersize);
			log.warning("Tc1:-Firstname data validation check with length size failed  ");

			if (!checkfirstnamewithcharactersize.equals(null)) {

				log.warning("Tc1:-Firstname data validation check with length size failed it's null ");

			}

			Utils.cleartext(driver, ActionXpath.firstname);
			Utils.callSendkeys(driver, ActionXpath.firstname, checkwithSpecialCharacteronly);
			WebElement SpecialCharacteronly = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkfirstnamewithSpecialCharacteronly = SpecialCharacteronly.getText();
			System.out.println(checkfirstnamewithSpecialCharacteronly);
			log.warning("Tc1:-Firstname data validation check with SpecialCharacter failed  ");

			if (!checkfirstnamewithSpecialCharacteronly.equals(null)) {

				log.warning("Tc1:-Firstname data validation check with SpecialCharacter failed  it's null");

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc1:-Firstname validation testcase completed");
		}
	}

	@Test(priority = 2)
	public static void testValidatelastname(String url, WebDriver Driver, String[] csvCell) throws Exception {
		try {
			String web_url = csvCell[0];
			Driver.get(web_url);
			log.warning(
					"Tc2:-lastname  should contain only maximum 15 alphabet's not more than 15 character, specialcharacter and number's ");

			Utils.callSendkeys(driver, ActionXpath.lastName, checkwithalphabet);

			Utils.cleartext(driver, ActionXpath.lastName);
			Utils.callSendkeys(driver, ActionXpath.lastName, checkwithnumber);
			WebElement alphalastName = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checklastNamewithnumber = alphalastName.getText();
			System.out.println(checklastNamewithnumber);
			log.warning("Tc2:-LastName data validation check with number failed  ");

			if (!checklastNamewithnumber.equals(null)) {

				log.warning("Tc2:-LastName data validation check with number failed it's null  ");

			}

			Utils.cleartext(driver, ActionXpath.lastName);
			Utils.callSendkeys(driver, ActionXpath.lastName, checkwithlength);
			WebElement number = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checklastNamewithcharactersize = number.getText();
			System.out.println(checklastNamewithcharactersize);
			log.warning("Tc2:-LastName data validation check with length size failed  ");

			if (!checklastNamewithcharactersize.equals(null)) {

				log.warning("Tc2:-LastName data validation check with length size failed it's null ");

			}

			Utils.cleartext(driver, ActionXpath.lastName);
			Utils.callSendkeys(driver, ActionXpath.lastName, checkwithSpecialCharacteronly);
			WebElement SpecialCharacteronly = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checklastNamewithSpecialCharacteronly = SpecialCharacteronly.getText();
			System.out.println(checklastNamewithSpecialCharacteronly);
			log.warning("Tc2:-LastName data validation check with specialCharacter failed  ");

			if (!checklastNamewithSpecialCharacteronly.equals(null)) {

				log.warning("Tc2:-LastName data validation check with specialCharacter failed it's null ");

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc2:-Lastname validation testcase completed");
		}
	}

	@Test(priority = 3)
	public static void testValidatemiddlename(String url, WebDriver Driver, String[] csvCell) throws Exception {

		try {

			String web_url = csvCell[0];
			Driver.get(web_url);
			log.warning(
					"Tc3:-middlename  should contain only maximum 15 alphabet's not more than 15 character, specialcharacter and number's ");

			Utils.callSendkeys(driver, ActionXpath.middlename, checkwithalphabet);

			Utils.cleartext(driver, ActionXpath.middlename);

			Utils.callSendkeys(driver, ActionXpath.middlename, checkwithnumber);
//			WebElement alphamiddlename = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//			 
//			String checkmiddlenamewithnumber = alphamiddlename.getText();
//			
//			if (checkmiddlenamewithnumber.equals(null)) {
//				System.out.println(checkmiddlenamewithnumber);
//				log.warning("Tc3:-MiddleName data validation check with number failed  ");
//
//			}
			Utils.cleartext(driver, ActionXpath.middlename);
			Utils.callSendkeys(driver, ActionXpath.middlename, checkwithlength);
//			WebElement number = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//			String checkmiddlenamewithcharactersize = number.getText();
//
//			if (checkmiddlenamewithcharactersize.equals(null)) {
//				System.out.println(checkmiddlenamewithcharactersize);
//				log.warning("Tc3:-MiddleName data validation check with length size failed  ");
//
//			}
			Utils.cleartext(driver, ActionXpath.middlename);
			Utils.callSendkeys(driver, ActionXpath.middlename, checkwithSpecialCharacteronly);
//			WebElement SpecialCharacteronly = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//			String checkmiddlenamewithSpecialCharacteronly = SpecialCharacteronly.getText();
//			System.out.println(checkmiddlenamewithSpecialCharacteronly);
//			if (checkmiddlenamewithSpecialCharacteronly.equals(null)) {
//				System.out.println(checkmiddlenamewithcharactersize);
//				log.warning("Tc3:-MiddleName data validation check with length size failed  ");
//
//			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc3:-Middlename validation testcase completed");
		}
	}

	@Test(priority = 4)
	public static void testValidateEmail(String url, WebDriver Driver, String[] csvCell) throws Exception {
		try {
			String web_url = csvCell[0];
			Driver.get(web_url);
			log.warning("Tc4:-email should contain only max 256  character, 1specialcharacter and number's ");

			Utils.callSendkeys(driver, ActionXpath.email, validemail);

			Utils.cleartext(driver, ActionXpath.email);
			Utils.callSendkeys(driver, ActionXpath.email, invalidEmail1);

			WebElement invalidmail = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkinvalidEmail = invalidmail.getText();

			if (checkinvalidEmail.equals(null)) {
				System.out.println(checkinvalidEmail);
				log.warning("Tc4:-Email data validation check with invalid email failed  ");

			}

			Utils.cleartext(driver, ActionXpath.email);
			Utils.callSendkeys(driver, ActionXpath.email, invalidEmail2);
//			WebElement invalidmail2 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//			String checkinvalidEmail2 = invalidmail2.getText();
//
//			if (checkinvalidEmail2.equals(null)) {
//				System.out.println(checkinvalidEmail2);
//				log.warning("Tc4:-Email data validation check with invalid email failed  ");
//
//			}

			Utils.cleartext(driver, ActionXpath.email);
			Utils.callSendkeys(driver, ActionXpath.email, invalidEmail3);
//			WebElement invalidmail3 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//			String checkinvalidEmail3 = invalidmail3.getText();
//
//			if (checkinvalidEmail3.equals(null)) {
//				System.out.println(checkinvalidEmail3);
//				log.warning("Tc4:-Email data validation check with invalid email failed  ");
//
//			}
			Utils.cleartext(driver, ActionXpath.email);
			Utils.callSendkeys(driver, ActionXpath.email, invalidEmail4);
//			WebElement invlaidmail4 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//			String checkinvalidEmail4 = invlaidmail4.getText();
//
//			if (checkinvalidEmail4.equals(null)) {
//				System.out.println(checkinvalidEmail4);
//				log.warning("Tc4:-Email data validation check with invalid email failed  ");
//
//			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc4:-Email validation testcase completed");
		}
	}

	@Test(priority = 5)
	public static void testValidatephonenumber(String url, WebDriver Driver, String[] csvCell) throws Exception {
		try {
			String web_url = csvCell[0];
			Driver.get(web_url);
			log.warning(
					"Tc5:-phonenumber  should contain only maximum 10 digit it shouldn't contain alphabet's and specialcharacter ");

			Utils.callSendkeys(driver, ActionXpath.mobile, phonenumbervalid10digit);

			Utils.cleartext(driver, ActionXpath.mobile);
			Utils.callSendkeys(driver, ActionXpath.mobile, phonenumberdigitcheck);
			WebElement phonenocheck = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkphonedigit = phonenocheck.getText();
			System.out.println(checkphonedigit);
			log.warning("Tc5:-phone data validation check with length size failed  ");

			if (!checkphonedigit.equals(null)) {

				log.warning("Tc5:-phone data validation check with length size failed it's null ");

			}

			Utils.cleartext(driver, ActionXpath.mobile);
			Utils.callSendkeys(driver, ActionXpath.mobile, phonenumberalphabetcheck);
			WebElement phonenoalpha = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkphonenumberalphabet = phonenoalpha.getText();
			System.out.println(checkphonenumberalphabet);

			Utils.cleartext(driver, ActionXpath.mobile);
			Utils.callSendkeys(driver, ActionXpath.mobile, phonenumberchecklength);
			WebElement phonenolength = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String phonenumberchecklength = phonenolength.getText();
			System.out.println(phonenumberchecklength);

			log.warning("Tc5:-phone data validation check with alphabet failed  ");

			if (!checkphonenumberalphabet.equals(null)) {

				log.warning("Tc5:-phone data validation check with alphabet failed it's null ");

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc5:-phone validation testcase completed");
		}
	}

	@Test(priority = 6)
	public static void testValidateadditionalphonenumber(String url, WebDriver Driver, String[] csvCell)
			throws Exception {
		try {
			String web_url = csvCell[0];
			Driver.get(web_url);
			log.warning(
					"Tc6:-Additional phonenumber  should contain only maximum 10 digit it shouldn't contain alphabet's and specialcharacter ");

			Utils.callSendkeys(driver, ActionXpath.mobilePhone, phonenumbervalid10digit);

			Utils.cleartext(driver, ActionXpath.mobilePhone);
			Utils.callSendkeys(driver, ActionXpath.mobilePhone, phonenumberdigitcheck);
			WebElement phonenocheck = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkphonedigit = phonenocheck.getText();
			System.out.println(checkphonedigit);
			log.warning("Tc6:-phone data validation check with length size failed  ");

			if (!checkphonedigit.equals(null)) {

				log.warning("Tc6:-phone data validation check with length size failed it's null ");

			}

			Utils.cleartext(driver, ActionXpath.mobilePhone);
			Utils.callSendkeys(driver, ActionXpath.mobilePhone, phonenumberalphabetcheck);
			WebElement phonenoalpha = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checkphonenumberalphabet = phonenoalpha.getText();
			System.out.println(checkphonenumberalphabet);
			log.warning("Tc6:-phone data validation check with alphabet failed  ");

			if (!checkphonenumberalphabet.equals(null)) {

				log.warning("Tc6:-phone data validation check with alphabet failed it's null ");

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc6:-Addition phone number validation testcase completed");
		}
	}

	@Test(priority = 7)
	public static void EmptyW2l_fillform_check(String url, WebDriver Driver, String[] csvCell)
			throws Throwable, Throwable {

		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(1));

			String web_url = csvCell[0];
			Driver.get(web_url);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)");

			Utils.submit_info(driver, csvCell, web_url);

			WebElement empty = driver.findElement(By.xpath("//span[@style='color: rgb(255, 153, 31);']"));
			String emptycheckform = empty.getText();
			System.out.println(emptycheckform);
			log.warning("Tc7:- validation  failed because field's are not entered  ");

		}

		catch (Exception e) {
			e.printStackTrace();
			log.info("Tc7:-EmptyW2l_fillform_check testcase completed");
		}
	}

	@Test(priority = 8)
	public static void fillform_W2l(String url, WebDriver Driver, String[] csvCell) throws Throwable, Throwable {
		try {
//			((JavascriptExecutor) driver).executeScript("window.open()");
//			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
//			driver.switchTo().window(tab.get(2));
			String web_url = csvCell[0];
			Driver.get(web_url);
			Utils.basic_info(Driver, csvCell, url);

			Utils.address_info(Driver, csvCell, url);

			Utils.other_info(driver, csvCell, web_url);

			Utils.submit_info(driver, csvCell, web_url);
			Thread.sleep(6000);
			log.info("***************** COMPLETED TESTTING OF PORTAL" + url);
			driver.quit();
		} catch (Exception e) {

			e.printStackTrace();
			log.info("Tc8:-web2Lead formfillup testcase completed");

		}
	}

	@Test(priority = 9)
	public static void Apploginuploadandsubmit(String app_url, WebDriver driver, String[] csvCell) throws Throwable {
		try {
//			((JavascriptExecutor) driver).executeScript("window.open()");
//			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
//			driver.switchTo().window(tab.get(3));
			String url = csvCell[1];
			driver.get(url);

			Utils.login(app_url, driver, csvCell);
			Utils.Appbasic_info(app_url, driver, csvCell);
			Utils.family_info(app_url, driver, csvCell);
			Utils.Address_info(app_url, driver, csvCell);
			Utils.qualification_info(app_url, driver, csvCell);
			Utils.Multipleuploadfile_info(app_url, driver, csvCell);
			Utils.checkbox(app_url, driver, csvCell);
			Utils.submit_app(app_url, driver, csvCell);

			Utils.signout_app(app_url, driver, csvCell);

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc9:-Application login,fileuploadandsubmit testcase completed");
		}
	}

	@Test(priority = 10)
	public static void salesforce_applicationviewanddelete(String sal_url, WebDriver driver, String[] csvCell)
			throws java.lang.Exception

	{
		try {

//			((JavascriptExecutor) driver).executeScript("window.open()");
//			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
//			driver.switchTo().window(tab.get(4));

			String url = csvCell[40];
			driver.get(url);
			Utils.salesforce_login(sal_url, driver, csvCell);
			Utils.clickonApplicationandselect(sal_url, driver, csvCell);

			Utils.profile_logout(sal_url, driver, csvCell);

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc10:-Salesforce Application view and delete testcase completed");
		}
	}

	@Test(priority = 11)
	public static void Apploginwithinvalidnumber(String app_url, WebDriver driver2, String[] csvCell) throws Throwable

	{
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(5));

			String url = csvCell[1];
			driver2.get(url);
			Utils.callSendkeys(driver, ActionXpath.addphone, "9080504030");
			Utils.clickXpath(driver, ActionXpath.getotp, "getotp");
			Thread.sleep(3000);
			WebElement getalert = driver.findElement(By.xpath("//div[@role='alert']"));
			String message = getalert.getText();

			System.out.println(message);

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc11:-Application with invalidnumber testcase completed");
		}
	}

	@Test(priority = 12)
	public static void Application_fillandfilesizecheck(String app_url, WebDriver driver, String[] csvCell)
			throws Throwable

	{
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(6));

			String url = csvCell[1];
			driver.get(url);

			Utils.login(app_url, driver, csvCell);
			Utils.Appbasic_info(app_url, driver, csvCell);
			Utils.family_info(app_url, driver, csvCell);
			Utils.Address_info(app_url, driver, csvCell);
			Utils.qualification_info(app_url, driver, csvCell);
			Utils.uploadfile_info(app_url, driver, csvCell);
			Utils.signout_app(app_url, driver, csvCell);

			Utils.salesforce_applicationviewanddelete(url, driver, csvCell);

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc12:-Application form_fill and file_size check testcase and application delete completed");
		}
	}

	@Test(priority = 13)
	public static void salesforce_loginviewMarkdelete(String sal_url, WebDriver driver2, String[] csvCell)
			throws java.lang.Exception

	{
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tab11 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab11.get(7));

			String url = csvCell[40];
			driver.get(url);
			Utils.salesforce_login(sal_url, driver2, csvCell);
			Utils.clickonleadandselect(sal_url, driver2, csvCell);
			Utils.markasStatus(sal_url, driver2, csvCell);
			Utils.selectsaveanddelete(sal_url, driver2, csvCell);
			Utils.profile_logout(sal_url, driver2, csvCell);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc13:-Salesforce login,view,mark and delete testcase completed");
		}
	}

	@Test(priority = 14)
	public static void validatewithfnfillform_W2l(String url, WebDriver Driver, String[] csvCell)
			throws Throwable, Throwable {
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(8));
			String web_url = csvCell[0];
			Driver.get(web_url);
			Utils.firstbasic_info(Driver, csvCell, url);

			Utils.address_info(Driver, csvCell, url);

			Utils.other_info(driver, csvCell, web_url);

			Utils.submit_info(driver, csvCell, web_url);

			WebElement fnamevalidatewithfullform = driver
					.findElement(By.xpath("//span[@style='color: rgb(255, 153, 31);']"));
			String fncheckform = fnamevalidatewithfullform.getText();
			System.out.println(fncheckform);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-2000)");

			log.info("***************** COMPLETED TESTTING OF PORTAL but failed due to firstname empty field" + url);

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc14:-firstname validate with complete form fill web2Lead  testcase completed");
		}
	}

	@AfterMethod
	public static void quitDriver(String Url) throws Exception {
		log.info("Close window  of portal" + Url);
		log.info("\n");
		driver.quit();
	}
}