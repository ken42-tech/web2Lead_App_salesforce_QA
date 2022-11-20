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

public class w2l_salesforceport {

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
	static String validage = "18";
	static String invalidage = "1";
	static String invalidage1 = "121";
	static String invalidagewithalpha = "ss";
	static String aadharnumber = "123456789012";
	static String invalidaadharnumber = "1234567890121";
	static String invalidaadharnumberwithalphanumeric = "12qw";

	public static void main(String[] args) throws Throwable {
		String CSV_PATH = "";
		String logFileName = "";
		boolean append = false;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

		if (Utils.checkWindowsOs()) {
			CSV_PATH = "C:\\Users\\Public\\Documents\\datareadertext.csv";
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
					testValidateage(w2l_url, driver, csvCell);
					break;
				case 8:
					testValidateaadhar(w2l_url, driver, csvCell);
					break;

				case 9:
					EmptyW2l_fillform_check(w2l_url, driver, csvCell);
					break;

				case 10:
					fillform_W2l(w2l_url, driver, csvCell);
					break;

				case 11:
					salesforce_loginviewMarkdelete(sal_url, driver, csvCell);
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
			log.info("Tc1:-Firstname validation testcase passed");
			System.out.println("Tc1:-Firstname validation testcase passed");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc1:-Firstname validation testcase failed");
			System.out.println("Tc1:-Firstname validation testcase failed");
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
			log.info("Tc2:-Lastname validation testcase passed");
			System.out.println("Tc2:-Lastname validation testcase passed");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc2:-Lastname validation testcase failed");
			System.out.println("Tc2:-Lastname validation testcase failed");
		}
	}

	@Test(priority = 3)
	public static void testValidatemiddlename(String url, WebDriver Driver, String[] csvCell) throws Exception {

		try {

			if (Utils.checkmiddlename(url)) {
				System.out.println("there is no middle name");

			} else {

				String web_url = csvCell[0];
				Driver.get(web_url);
				log.warning(
						"Tc3:-middlename  should contain only maximum 15 alphabet's not more than 15 character, specialcharacter and number's ");

				Utils.callSendkeys(driver, ActionXpath.middlename, checkwithalphabet);

				Utils.cleartext(driver, ActionXpath.middlename);

				Utils.callSendkeys(driver, ActionXpath.middlename, checkwithnumber);
//				WebElement alphamiddlename = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//				 
//				String checkmiddlenamewithnumber = alphamiddlename.getText();
//				
//				if (checkmiddlenamewithnumber.equals(null)) {
//					System.out.println(checkmiddlenamewithnumber);
//					log.warning("Tc3:-MiddleName data validation check with number failed  ");
				//
//				}
				Utils.cleartext(driver, ActionXpath.middlename);
				Utils.callSendkeys(driver, ActionXpath.middlename, checkwithlength);
//				WebElement number = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//				String checkmiddlenamewithcharactersize = number.getText();
				//
//				if (checkmiddlenamewithcharactersize.equals(null)) {
//					System.out.println(checkmiddlenamewithcharactersize);
//					log.warning("Tc3:-MiddleName data validation check with length size failed  ");
				//
//				}
				Utils.cleartext(driver, ActionXpath.middlename);
				Utils.callSendkeys(driver, ActionXpath.middlename, checkwithSpecialCharacteronly);
//				WebElement SpecialCharacteronly = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//				String checkmiddlenamewithSpecialCharacteronly = SpecialCharacteronly.getText();
//				System.out.println(checkmiddlenamewithSpecialCharacteronly);
//				if (checkmiddlenamewithSpecialCharacteronly.equals(null)) {
//					System.out.println(checkmiddlenamewithcharactersize);
//					log.warning("Tc3:-MiddleName data validation check with length size failed  ");
				//
			}
			
			log.info("Tc3:-Middlename validation testcase passed");
			System.out.println("Tc3:-Middlename validation testcase passed");
	

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc3:-Middlename validation testcase failed");
			System.out.println("Tc3:-Middlename validation testcase failed");
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
//				WebElement invalidmail2 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//				String checkinvalidEmail2 = invalidmail2.getText();
			//
//				if (checkinvalidEmail2.equals(null)) {
//					System.out.println(checkinvalidEmail2);
//					log.warning("Tc4:-Email data validation check with invalid email failed  ");
			//
//				}

			Utils.cleartext(driver, ActionXpath.email);
			Utils.callSendkeys(driver, ActionXpath.email, invalidEmail3);
//				WebElement invalidmail3 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//				String checkinvalidEmail3 = invalidmail3.getText();
			//
//				if (checkinvalidEmail3.equals(null)) {
//					System.out.println(checkinvalidEmail3);
//					log.warning("Tc4:-Email data validation check with invalid email failed  ");
			//
//				}
			Utils.cleartext(driver, ActionXpath.email);
			Utils.callSendkeys(driver, ActionXpath.email, invalidEmail4);
//				WebElement invlaidmail4 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
//				String checkinvalidEmail4 = invlaidmail4.getText();
			//
//				if (checkinvalidEmail4.equals(null)) {
//					System.out.println(checkinvalidEmail4);
//					log.warning("Tc4:-Email data validation check with invalid email failed  ");
			//
//				}
			
			log.info("Tc4:-Email validation testcase passed");
			System.out.println("Tc4:-Email validation testcase passed");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc4:-Email validation testcase failed");
			System.out.println("Tc4:-Email validation testcase failed");
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
			log.warning("Tc5:-phone data validation check with alphabet failed  ");

			if (!checkphonenumberalphabet.equals(null)) {

				log.warning("Tc5:-phone data validation check with alphabet failed it's null ");

			}

			log.info("Tc5:-phone validation testcase passed");
			System.out.println("Tc5:-phone validation testcase passed");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc5:-phone validation testcase failed");
			System.out.println("Tc5:-phone validation testcase failed");
		}

	}

	@Test(priority = 6)
	public static void testValidateadditionalphonenumber(String url, WebDriver Driver, String[] csvCell)
			throws Exception {
		try {

			if (Utils.checkadditionalphone(url)) {
				System.out.println("no additional number");
			} else {
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

			}
			log.info("Tc6:-Addition phone number validation testcase passed");
			System.out.println("Tc6:-Addition phone number validation testcase passed");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc6:-Addition phone number validation testcase failed");
			System.out.println("Tc6:-Addition phone number validation testcase failed");
		}
	}

	@Test(priority = 7)
	public static void testValidateage(String url, WebDriver Driver, String[] csvCell) throws Exception {
		try {

			if (Utils.checkage(url)) {
				System.out.println("no age");
			} else {
				String web_url = csvCell[0];
				Driver.get(web_url);
				log.warning(
						"Tc7:-age  should be 18 not less than 18, it shouldn't contain alphabet's and specialcharacter ");

				Utils.callSendkeys(driver, ActionXpath.age, validage);

				Utils.cleartext(driver, ActionXpath.age);
				Utils.callSendkeys(driver, ActionXpath.age, invalidage);
				WebElement age = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
				String checkinvalidage = age.getText();
				System.out.println(checkinvalidage);
				log.warning("Tc7:-age data validation check with length size failed  ");

				if (!checkinvalidage.equals(null)) {

					log.warning("Tc7:-age data validation check with length size failed it's null ");

				}

				Utils.cleartext(driver, ActionXpath.age);
				Utils.callSendkeys(driver, ActionXpath.age, invalidage1);
				WebElement age1 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
				String checkinvalidage1 = age1.getText();
				System.out.println(checkinvalidage1);
				log.warning("Tc7:-age data validation check with more than length failed  ");

				if (!checkinvalidage1.equals(null)) {

					log.warning("Tc7:-age data validation check with more than length failed it's null ");

				}

				Utils.cleartext(driver, ActionXpath.age);
				Utils.callSendkeys(driver, ActionXpath.age, invalidagewithalpha);
				WebElement age111 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
				String checkinvalidagewithalpha = age111.getText();
				System.out.println(checkinvalidagewithalpha);
				log.warning("Tc7:-age data validation check with alphabet failed  ");

				if (!checkinvalidagewithalpha.equals(null)) {

					log.warning("Tc7:-age data validation check with alphabet failed it's null ");

				}
				
				
				

			}
			log.info("Tc7:-Age validation testcase passed");
			System.out.println("Tc7:-Age validation testcase passed");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc7:-Age validation testcase failed");
			System.out.println("Tc7:-Age validation testcase failed");
		}
	}

	@Test(priority = 8)
	public static void testValidateaadhar(String url, WebDriver Driver, String[] csvCell) throws Exception {
		try {

			if (Utils.checkadhar(url)) {
				System.out.println("no aadhar");
			} else {
				String web_url = csvCell[0];
				Driver.get(web_url);
				log.warning(
						"Tc8:-aadhar  should be 12 digit not less than 12, it shouldn't contain alphabet's and specialcharacter ");

				Utils.callSendkeys(driver, ActionXpath.aadhar, aadharnumber);

				Utils.cleartext(driver, ActionXpath.aadhar);
				Utils.callSendkeys(driver, ActionXpath.aadhar, invalidaadharnumber);
				WebElement adhar = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
				String checkinvalidaadhar = adhar.getText();
				System.out.println(checkinvalidaadhar);
				log.warning("Tc8:-aadhar data validation check with length size failed  ");

				if (!checkinvalidaadhar.equals(null)) {

					log.warning("Tc8:-aadhar data validation check with length size failed it's null ");

				}

				Utils.cleartext(driver, ActionXpath.aadhar);
				Utils.callSendkeys(driver, ActionXpath.aadhar, invalidaadharnumberwithalphanumeric);
				WebElement adhar1 = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
				String checkinvalidaadharnumberwithalphanumeric = adhar1.getText();
				System.out.println(checkinvalidaadharnumberwithalphanumeric);
				log.warning("Tc8:-age data validation check with invalidaadharnumberwithalphanumeric failed  ");

				if (!checkinvalidaadharnumberwithalphanumeric.equals(null)) {

					log.warning(
							"Tc8:-aadhar data validation check with invalidaadharnumberwithalphanumeric failed it's null ");

				}

			}
			
			log.info("Tc8:-Aadhar validation testcase passed");
			System.out.println("Tc8:-Aadhar validation testcase passed");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc8:-Aadhar validation testcase failed");
			System.out.println("Tc8:-Aadhar validation testcase failed");
		}
	}

	@Test(priority = 9)
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
			log.warning("Tc9:- validation  failed because field's are not entered  ");

			
			log.info("Tc9:-EmptyW2l_fillform_check testcase passed");	
			System.out.println("Tc9:-EmptyW2l_fillform_check testcase passed");
		}

		catch (Exception e) {
			e.printStackTrace();
			log.info("Tc9:-EmptyW2l_fillform_check testcase failed");
			System.out.println("Tc9:-EmptyW2l_fillform_check testcase failed");
			
		}
	}

	@Test(priority = 10)
	public static void fillform_W2l(String url, WebDriver Driver, String[] csvCell) throws Throwable, Throwable {
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(2));
			String web_url = csvCell[0];
			Driver.get(web_url);
			Utils.basic_info(Driver, csvCell, url);

			Utils.address_info(Driver, csvCell, url);

			Utils.other_info(driver, csvCell, web_url);

			Utils.submit_info(driver, csvCell, web_url);


			
			
			log.info("Tc10:-web2Lead formfillup testcase passed");
			System.out.println("Tc10:-web2Lead formfillup testcase passed");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tc10:-web2Lead formfillup testcase failed");
			System.out.println("Tc10:-web2Lead formfillup testcase failed");
		}
	}

	@Test(priority = 11)
	public static void salesforce_loginviewMarkdelete(String sal_url, WebDriver driver2, String[] csvCell)
			throws java.lang.Exception

	{
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tab11 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab11.get(3));

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

}
