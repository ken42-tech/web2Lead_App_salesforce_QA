package com.ken42.Appw2l;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import java.util.logging.*;
import java.util.regex.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Function;

import org.openqa.selenium.Alert;

public class Utils {
	static Logger log = Logger.getLogger(Utils.class.getName());
	static int time = 1000;
	// public static Logger log = Logger.getLogger("Pfs_portal");
	static String aadharnumber = "123456789012";
	static String Age = "18";

	public static void clickXpath(WebDriver driver, String xpath, String msg) throws Exception {
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		int count = 0;
		int maxTries = 7;
		final String XPATH = xpath;
		while (true) {
			try {
				Thread.sleep(1000);
				log.info("Click on the:" + msg);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
						.pollingEvery(Duration.ofSeconds(6)).ignoring(NoSuchElementException.class);
				WebElement WE = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.xpath(XPATH));
					}
				});
				WE.click();
				// new WebDriverWait(driver,
				// 10).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
				break;
			} catch (Exception e) {
				Thread.sleep(3000);
				log.warning("Failed to Click on the :" + msg);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static void callSendkeys(WebDriver driver, String Xpath, String Value) throws Exception {
		int count = 0;
		int maxTries = 7;
		final String XPATH = Xpath;
		while (true) {
			try {
				log.info("***********************Entering value   " + Value);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(20))
						.pollingEvery(Duration.ofSeconds(4)).ignoring(NoSuchElementException.class);
				WebElement WE = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.xpath(XPATH));
					}
				});
				WE.sendKeys(Value);
				Thread.sleep(1000);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				log.warning("Failed to send value  " + Value);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static void clickXpathWithJavascript(WebDriver driver, String xpath, int time, String msg) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int count = 0;
		int maxTries = 7;
		final String XPATH = xpath;
		while (true) {
			try {
				// Thread.sleep(1000);
				log.info("Click on the:" + msg);
				WebElement webElement = driver.findElement(By.xpath(xpath));
				JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
				javaScriptExecutor.executeScript("arguments[0].click()", webElement);
				// new WebDriverWait(driver,
				// 10).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
				break;
			} catch (Exception e) {
				Thread.sleep(3000);
				log.warning("Failed to Click on the :" + msg);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static void cleartext(WebDriver driver, String Xpath) throws Exception {
		int count = 0;
		int maxTries = 4;
		while (true) {
			try {
				log.info("clear value");

				Thread.sleep(4000);
				WebElement elee = driver.findElement(By.xpath(Xpath));
				elee.sendKeys(Keys.CONTROL, "a", Keys.DELETE);

				break;
			} catch (Exception e) {
				Thread.sleep(250);
				log.warning("Failed to clear value  ");
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static boolean checkWindowsOs() {
		String OS = "";
		OS = System.getProperty("os.name");
		System.out.println(OS);

		if (OS.contains("Windows")) {
			return true;
		}
		return false;

	}

	public static void clickXpathWithScroll(WebDriver driver, String xpath, int time, String msg) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int count = 0;
		int maxTries = 7;
		while (true) {
			try {
				Thread.sleep(1000);
				log.info("Click on the:" + msg);
				WebElement el = driver.findElement(By.xpath(xpath));
				js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'start' });", el);
				el.click();
				break;
			} catch (Exception e) {
				Thread.sleep(3000);
				Utils.printException(e);
				log.warning("Failed to Click on the :" + msg);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public int getDecimalRandomNumber() {

		Random r = new Random();
		int low = 50;
		int high = 99;
		int result = r.nextInt(high - low) + low;
		System.out.println(result);
		return result;
	}

	@Test
	public static void goBackToHome(WebDriver driver, String url) throws InterruptedException {
		driver.navigate().to(url);
		smallSleepBetweenClicks(1);
	}

	@Test
	public static void smallSleepBetweenClicks(int loop) throws InterruptedException {
		int total_time = 2000 * loop;
		System.out.println("Sleeping for " + total_time);
		Thread.sleep(2000 * loop);
	}

	@Test
	public static void bigSleepBetweenClicks(int loop) throws InterruptedException {
		int total_time = 7000 * loop;
		System.out.println("Sleeping for " + total_time);
		Thread.sleep(7000 * loop);
	}

	@Test
	public static void printException(Exception e) {
		log.warning("Exception is  " + e);
	}

	@Test
	public static int generateRandom() {
		double num = (int) Math.round(Math.random() * 10000);
		int num1 = (int) num;
		return num1;
	}

	public static void executeLongWait(String url) throws InterruptedException {
		String urlToMatch = "ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			bigSleepBetweenClicks(2);
		}
	}

	@Test
	public static String convertContent(String input) {

		// delete a last char
		if ("Karnataka".equals(input)) {
			return ("Karnataka");
		}

		StringBuffer sb = new StringBuffer(input);
		// sb.deleteCharAt(sb.length() - 1);

		String tri = sb.toString();
		String tr = tri.trim();
		String msg = tr.toLowerCase();
//           System.out.println(msg);

		char[] charArray = msg.toCharArray();
		boolean foundSpace = true;
		for (int i = 0; i < charArray.length; i++) {
			if (Character.isLetter(charArray[i])) {
				if (foundSpace) {
					charArray[i] = Character.toUpperCase(charArray[i]);
					foundSpace = false;
				}
			} else {
				foundSpace = true;
			}
		}
		return tr = String.valueOf(charArray);

	}

	@Test
	public static void validate_middlename(WebDriver driver, String[] csvCell, String url) throws Throwable {
		String middlename = csvCell[6];
		Utils.cleartext(driver, middlename);
		Utils.callSendkeys(driver, ActionXpath.middlename, middlename);
	}

	@Test
	public static void basic_info(WebDriver driver, String[] csvCell, String url) throws Throwable {
		String firstname = csvCell[5];

		String middlename = csvCell[6];
		String lastname = csvCell[7];
		String email = csvCell[8];
		String mobile = csvCell[9];
		String mobilephone = csvCell[10];
		String dob = csvCell[11];

		System.out.println(firstname);
		System.out.println(firstname);
		System.out.println(email);

		String exp = csvCell[48];

		// basic_info(csvCell, driver);
		// Addres_info(csvCell, driver);
		Thread.sleep(4000);
		Utils.callSendkeys(driver, ActionXpath.firstname, firstname);

		if (checkmiddlename(url)) {
			System.out.println("no middel name");
		} else {
			Utils.callSendkeys(driver, ActionXpath.middlename, middlename);
		}

		Utils.callSendkeys(driver, ActionXpath.lastName, lastname);
		Utils.callSendkeys(driver, ActionXpath.email, email);
		Utils.callSendkeys(driver, ActionXpath.mobile, mobile);

		if (checkadditionalphone(url)) {
			System.out.println("no additional name");
		} else {
			Utils.callSendkeys(driver, ActionXpath.mobilePhone, mobilephone);
		}

		if (checkToskipdob(url)) {
			System.out.println("no dob");

		} else {
			Utils.callSendkeys(driver, ActionXpath.dob, dob);
		}

		if (checkToskipcourse(url)) {
			System.out.println("no course");
			System.out.println(url);
		} else {
			Thread.sleep(2000);

			Utils.callSendkeys(driver, ActionXpath.ageselect, "18");

			Utils.callSendkeys(driver, ActionXpath.aadhar, "123456789012");

			Utils.callSendkeys(driver, ActionXpath.enquiry, "12");

			Utils.clickXpath(driver, ActionXpath.qualif, "qualif");

			driver.findElement(By.xpath("//li[.='12th']")).click();

			Utils.clickXpath(driver, ActionXpath.trainingcenter, "trainingcenter");

			driver.findElement(By.xpath("//li[.='NSIC, Delhi']")).click();

			Utils.clickXpath(driver, ActionXpath.course1, "course1");

			driver.findElement(By.xpath("//li[.='Big Data']")).click();

			Utils.clickXpath(driver, ActionXpath.clickoncheckox, "clickoncheckox");

		}
	}

	@Test
	public static void firstbasic_info(WebDriver driver, String[] csvCell, String url) throws Throwable {
		String firstname = csvCell[5];

		String middlename = csvCell[6];
		String lastname = csvCell[7];
		String email = csvCell[8];
		String mobile = csvCell[9];
		String mobilephone = csvCell[10];
		String dob = csvCell[11];
		String exp = csvCell[48];

		String aadhar = csvCell[47];
		System.out.println(firstname);
		System.out.println(firstname);
		System.out.println(email);

		// basic_info(csvCell, driver);
		// Addres_info(csvCell, driver);
		Thread.sleep(4000);
		// Utils.callSendkeys(driver, ActionXpath.firstname, firstname);

		if (checkmiddlename(url)) {
			System.out.println("no middel name");
		} else {
			Utils.callSendkeys(driver, ActionXpath.middlename, middlename);
		}

		Utils.callSendkeys(driver, ActionXpath.lastName, lastname);
		Utils.callSendkeys(driver, ActionXpath.email, email);
		Utils.callSendkeys(driver, ActionXpath.mobile, mobile);

		if (checkadditionalphone(url)) {
			System.out.println("no additional name");
		} else {
			Utils.callSendkeys(driver, ActionXpath.mobilePhone, mobilephone);
		}

		if (checkToskipdob(url)) {
			System.out.println("no dob");

		} else {
			Utils.callSendkeys(driver, ActionXpath.dob, dob);
		}

		if (checkToskipcourse(url)) {
			System.out.println("no course");
			System.out.println(url);
		} else {
			Thread.sleep(2000);

			Utils.callSendkeys(driver, ActionXpath.ageselect, "18");

			Utils.callSendkeys(driver, ActionXpath.aadhar, aadhar);

			Utils.callSendkeys(driver, ActionXpath.enquiry, exp);

			Utils.clickXpath(driver, ActionXpath.qualif, "qualif");

			driver.findElement(By.xpath("//li[.='12th']")).click();

			Utils.clickXpath(driver, ActionXpath.trainingcenter, "trainingcenter");

			driver.findElement(By.xpath("//li[.='NSIC, Delhi']")).click();

			Utils.clickXpath(driver, ActionXpath.course1, "course1");

			driver.findElement(By.xpath("//li[.='Big Data']")).click();

			Utils.clickXpath(driver, ActionXpath.clickoncheckox, "clickoncheckox");

		}
	}

	@Test
	public static void address_info(WebDriver driver, String[] csvCell, String url) throws Throwable {

		if (checkToskip(url)) {
			System.out.println("not matching");
		} else {

			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.clickonstate, "click on state");

			List<WebElement> values = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String statesel = csvCell[50];
			for (int i = 0; i < values.size(); i++) {

				if (values.get(i).getText().contains(statesel)) {
					values.get(i).click();
					break;
				}

			}

			Utils.clickXpath(driver, ActionXpath.clickoncity, "click on city");
			List<WebElement> city = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String citysel = csvCell[51];

			for (int i = 0; i < city.size(); i++) {

				if (city.get(i).getText().contains(citysel)) {

					city.get(i).click();
					break;
				}

			}

		}

		if (checkToskipcours(url)) {
			System.out.println("no course");
		} else {
			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.course, "course");

		}

		if (checkToskipnitte(url)) {
			System.out.println("no other");
		} else {
			Utils.clickXpath(driver, ActionXpath.taluku, "taluku");

			List<WebElement> taluku = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String talkusel = csvCell[52];
			for (int i = 0; i < taluku.size(); i++) {

				if (taluku.get(i).getText().contains(talkusel)) {
					taluku.get(i).click();
					break;
				}

			}
			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.gramPanchayat, "gramPanchayat");

			List<WebElement> gram = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String gramsel = csvCell[53];
			for (int i = 0; i < gram.size(); i++) {

				if (gram.get(i).getText().contains(gramsel)) {
					gram.get(i).click();
				}

			}
//        String talaku = csvCell[12];
//        String grampanchayat = csvCell[13];
			String village = csvCell[14];
			String hamlet = csvCell[15];

			// Utils.callSendkeys(driver, ActionXpath.taluku, talaku);
//        Utils.callSendkeys(driver, ActionXpath.gramPanchayat, grampanchayat);
//
			Utils.callSendkeys(driver, ActionXpath.village, village);
//
			Utils.callSendkeys(driver, ActionXpath.hamlet, hamlet);
		}
	}

	@Test
	public static void submit_info(WebDriver driver, String[] csvCell, String url) throws Throwable {

		Thread.sleep(3000);
		Actions ac1 = new Actions(driver);
		WebElement ele1 = driver.findElement(By.xpath("//span[.='Submit']|//span[.='SUBMIT']"));
		ac1.doubleClick(ele1).perform();
		
	}

	@Test
	public static void other_info(WebDriver driver, String[] csvCell, String url) throws Throwable {

		if (checkToskip(url))

		{
			System.out.println("no other details");
		} else {
			Utils.clickXpath(driver, ActionXpath.clickonqualification, "click on qualification");

			List<WebElement> Qualification = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String qualisel = csvCell[54];
			for (int i = 0; i < Qualification.size(); i++) {

				if (Qualification.get(i).getText().contains(qualisel)) {
					Qualification.get(i).click();
					break;
				}

			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)");
			Utils.clickXpath(driver, ActionXpath.clickonApplyto, "click on Applyto");

			List<WebElement> apply = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String Applysel = csvCell[55];
			for (int i = 0; i < apply.size(); i++) {

				if (apply.get(i).getText().contains(Applysel)) {
					apply.get(i).click();
					break;
				}

			}

			Utils.clickXpath(driver, ActionXpath.clickonreferral, "click on referral");

			List<WebElement> referral = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String referalsel = csvCell[56];
			for (int i = 0; i < referral.size(); i++) {

				if (referral.get(i).getText().contains(referalsel)) {
					referral.get(i).click();
					break;
				}

			}

//			WebElement ref = driver.findElement(By.xpath("(//li[@role='option'])[5]"));
//			String referral = ref.getText();
//			System.out.println(referral);
//			Thread.sleep(2000);
//			driver.findElement(By.xpath("//li[@data-value='" + referral + "']")).click();

			// Utils.callSendkeys(driver, ActionXpath.referalname, "surya");

			Utils.clickXpath(driver, ActionXpath.clickonmobiliser, "click on mobilizer");

			List<WebElement> mobiliser = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String mobilizersel = csvCell[57];
			for (int i = 0; i < mobiliser.size(); i++) {

				if (mobiliser.get(i).getText().contains(mobilizersel)) {
					mobiliser.get(i).click();
					break;
				}

			}

//			WebElement mobi = driver.findElement(By.xpath("(//li[@role='option'])[5]"));
//			String mobilizer = mobi.getText();
//			System.out.println(mobilizer);
//			Thread.sleep(2000);
//			driver.findElement(By.xpath("//li[@data-value='" + mobilizer + "']")).click();

			Utils.clickXpath(driver, ActionXpath.clickonreligion, "click on religion");
			List<WebElement> religion = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String religionsel = csvCell[58];
			for (int i = 0; i < religion.size(); i++) {

				if (religion.get(i).getText().contains(religionsel)) {
					religion.get(i).click();
					break;
				}

			}

//			WebElement reli = driver.findElement(By.xpath("(//li[@role='option'])[5]"));
//			String religion = reli.getText();
//			System.out.println(religion);
//			Thread.sleep(2000);
//			driver.findElement(By.xpath("//li[@data-value='" + religion + "']")).click();

			Utils.clickXpath(driver, ActionXpath.clickoncaste, "click on caste");

			List<WebElement> caste = driver
					.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
			String castesel = csvCell[59];
			for (int i = 0; i < caste.size(); i++) {

				if (caste.get(i).getText().contains(castesel)) {
					caste.get(i).click();
					break;
				}

			}

//			WebElement cas = driver.findElement(By.xpath("(//li[@role='option'])[3]"));
//			String caste = cas.getText();
//			System.out.println(caste);
//			Thread.sleep(2000);
//			driver.findElement(By.xpath("//li[@data-value='" + caste + "']")).click();

			if (checkboxSkip(url)) {
				System.out.println("no checkbox");
			} else {
				WebDriverWait wait = new WebDriverWait(driver, 20);
				WebElement e = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-disabled='false']")));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
			}
		}
	}

	@Test
	public static void login(String app_url, WebDriver driver, String[] csvCell) throws Exception

	{

		try {

			String regex = "Null";
			String mobile = csvCell[68];
			Utils.callSendkeys(driver, ActionXpath.addphone, mobile);
			Utils.clickXpath(driver, ActionXpath.getotp, "Request OTP");
			Thread.sleep(5000);
			Alert alert = driver.switchTo().alert(); // switch to alert
			String alertMessage = driver.switchTo().alert().getText(); // capture alert message
			System.out.println(alertMessage); // Print Alert Message
			Pattern pt = Pattern.compile("-?\\d+");
			Matcher m = pt.matcher(alertMessage);
			while (m.find()) {
				regex = m.group();
			}
			// Thread.sleep(2000);
			alert.accept();
			Utils.callSendkeys(driver, ActionXpath.enterotp, regex);

			Utils.clickXpath(driver, ActionXpath.clickongo, "clickongo");
			System.out.println(
					"Sleeping after login for 7 seconds so that goBacktoHome function does not automatically logout user");

		} catch (Exception e) {

			System.err.println("login failed");
			// driver.quit();
			throw (e);
		}

	}

	@Test
	public static void Appbasic_info(String app_url, WebDriver driver, String[] csvCell) throws Throwable {
		String vocation = csvCell[66];
		String firstname = csvCell[5];
		String surename = csvCell[16];
		String fathername = csvCell[17];

		String dob = csvCell[11];

		String occupation = csvCell[18];

		String grampanchayat = csvCell[13];
		String village = csvCell[14];
		String hamlet = csvCell[15];

		String religion = csvCell[19];
		String age = csvCell[20];

		String mothername = csvCell[21];

		String guardianname = csvCell[22];
		String hobbies = csvCell[23];
		Thread.sleep(4000);
		Utils.clickXpath(driver, ActionXpath.clickonnewapplication, "clickonnewapplication");
		Thread.sleep(4000);
		driver.findElement(By.xpath("//div[.='" + vocation + "']/../..//button")).click();
		// Utils.clickXpath(driver, ActionXpath.clickonapply, "clickonapply");
		Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickonyes, "clickonyes");
		Thread.sleep(4000);
		Utils.cleartext(driver, ActionXpath.firstname);
		Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.firstname, firstname);
		// Thread.sleep(1000);
		Utils.cleartext(driver, ActionXpath.surename);
		// Thread.sleep(1000);
		Utils.callSendkeys(driver, ActionXpath.surename, surename);
		// Thread.sleep(1000);
		Utils.cleartext(driver, ActionXpath.enterfathername);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterfathername, fathername);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.enterdob);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterdob, dob);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.presentoccupation);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.presentoccupation, occupation);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.gramPanchayat1);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.gramPanchayat1, grampanchayat);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.Hamlet);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.Hamlet, hamlet);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.Village);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.Village, village);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.religion);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.religion, religion);

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.age);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.age, age);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.entermothername);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.entermothername, mothername);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.enterguardianname);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterguardianname, guardianname);

		// Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.enterhobbies);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterhobbies, hobbies);

		// Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickonbloodgroup, "clickonbloodgroup");
		// Thread.sleep(2000);

		List<WebElement> Bloodgroup = driver
				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
		String bloodsel = csvCell[60];
		for (int i = 0; i < Bloodgroup.size(); i++) {

			if (Bloodgroup.get(i).getText().contains(bloodsel)) {
				Bloodgroup.get(i).click();
				break;
			}

		}

//		WebElement blo = driver.findElement(By.xpath("(//li[@role='option'])[5]"));
//		String bloodgroup = blo.getText();
//		System.out.println(bloodgroup);
//		// Thread.sleep(5000);
		// driver.findElement(By.xpath("//li[@data-value='" + bloodgroup +
		// "']")).click();

		// Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickoncaste1, "clickoncaste1");
		// Thread.sleep(2000);
		List<WebElement> caste = driver
				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
		String castesel = csvCell[59];
		for (int i = 0; i < caste.size(); i++) {

			if (caste.get(i).getText().contains(castesel)) {
				caste.get(i).click();
				break;
			}

		}

//		
//
//		WebElement cast = driver.findElement(By.xpath("(//li[@role='option'])[5]"));
//		String castism = cast.getText();
//		System.out.println(castism);
//		// Thread.sleep(5000);
//		driver.findElement(By.xpath("//li[@data-value='" + castism + "']")).click();

		Thread.sleep(2000);
	driver.findElement(By.xpath("(//button[@name='gender'])[3]")).click();

//if(el.isSelected())
//{
//	System.out.println("true");
//	
//}
//else
//{
//	el.click();
//}


Utils.clickXpath(driver, ActionXpath.clickonnext, "clickonnext");

	}

	@Test
	public static void family_info(String app_url, WebDriver driver, String[] csvCell) throws Exception {
		String workingMembers = csvCell[24];
		String nonWorkingMembers = csvCell[25];
		String familyIncome = csvCell[26];

		String noOfFamilymembers = csvCell[27];
		String noOfFamilymembersAdults = csvCell[28];
		String noOfFamilymembersChildren = csvCell[29];

		Thread.sleep(15000);
		Utils.callSendkeys(driver, ActionXpath.noOfFamilymembers, noOfFamilymembers);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.noOfFamilymembersAdults, noOfFamilymembersAdults);
		Utils.callSendkeys(driver, ActionXpath.noOfFamilymembersChildren, noOfFamilymembersChildren);

		Utils.callSendkeys(driver, ActionXpath.workingMembers, workingMembers);
		Utils.callSendkeys(driver, ActionXpath.nonWorkingMembers, nonWorkingMembers);

		Utils.clickXpath(driver, ActionXpath.clickonmaritalstatus, "clickonmaritalstatus");
		Thread.sleep(2000);
		List<WebElement> maritalstatus = driver
				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
		String martialsel = csvCell[61];
		for (int i = 0; i < maritalstatus.size(); i++) {

			if (maritalstatus.get(i).getText().contains(martialsel)) {
				maritalstatus.get(i).click();
				break;
			}

		}

//		WebElement mar = driver.findElement(By.xpath("(//li[@role='option'])[1]"));
//		String marital = mar.getText();
//		System.out.println(marital);
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//li[@data-value='" + marital + "']")).click();

		Utils.callSendkeys(driver, ActionXpath.familyIncome, familyIncome);

		Utils.clickXpath(driver, ActionXpath.clickonanyImpInformation, "clickonanyImpInformation");

		List<WebElement> ImpInformation = driver
				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
		String imponfosel = csvCell[62];
		for (int i = 0; i < ImpInformation.size(); i++) {

			if (ImpInformation.get(i).getText().contains(imponfosel)) {
				ImpInformation.get(i).click();
				break;
			}

		}
//		WebElement imp = driver.findElement(By.xpath("(//li[@role='option'])[1]"));
//		String Information = imp.getText();
//		System.out.println(Information);
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//li[@data-value='" + Information + "']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");

		Utils.callSendkeys(driver, ActionXpath.bankholder, "suri");
		Utils.callSendkeys(driver, ActionXpath.bankname, "sbi");
		Utils.callSendkeys(driver, ActionXpath.branchname, "bangaluru");
		Utils.callSendkeys(driver, ActionXpath.ifsccode, "C00678");

		Utils.clickXpath(driver, ActionXpath.clickon2ndnext, "clickon2ndnext");

		Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickon2ndnext, "clickon2ndnext");

	}

	@Test
	public static void Address_info(String app_url, WebDriver driver, String[] csvCell) throws Throwable

	{
		String address = csvCell[30];
		String city = csvCell[31];
		String pincode = csvCell[32];

		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-2000)");
		Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.address, address);

		Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickoncountry, "clickoncountry");

		List<WebElement> country = driver
				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
		String contrysel = csvCell[63];
		for (int i = 0; i < country.size(); i++) {

			if (country.get(i).getText().contains(contrysel)) {
				country.get(i).click();
				break;
			}

		}

//		WebElement coun = driver.findElement(By.xpath("(//li[@role='option'])[27]"));
//		String country = coun.getText();
//		System.out.println(country);
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//li[@data-value='" + country + "']")).click();

		Utils.clickXpath(driver, ActionXpath.selectstate, "clickonstate1");

		List<WebElement> clickstate = driver
				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
		String stateselc = csvCell[64];
		for (int i = 0; i < clickstate.size(); i++) {

			if (clickstate.get(i).getText().contains(stateselc)) {
				clickstate.get(i).click();
				break;
			}
		}

//		WebElement stat = driver.findElement(By.xpath("(//li[@role='option'])[27]"));
//		String state = stat.getText();
//		System.out.println(state);
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//li[@data-value='" + state + "']")).click();
////
		Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.addcity);
		Utils.callSendkeys(driver, ActionXpath.addcity, city);
		Utils.callSendkeys(driver, ActionXpath.pincode, pincode);
		Thread.sleep(4000);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-disabled='false']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
		Thread.sleep(5000);

		//JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
//		Thread.sleep(2000);
//		Utils.clickXpath(driver, ActionXpath.clickoncountry1, "clickoncountry");
//		Thread.sleep(2000);
//		
//		List<WebElement> country1 = driver
//				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
//		String contrysel1=csvCell[63];
//		for (int i = 0; i < country1.size(); i++) {
//
//			if (country1.get(i).getText().contains(contrysel1)) {
//				country1.get(i).click();
//				break;
//			}
//
//		}

//		WebElement coun1 = driver.findElement(By.xpath("(//li[@role='option'])[27]"));
//		String country1 = coun1.getText();
//		System.out.println(country1);
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//li[@data-value='" + country1 + "']")).click();
//
//		// Thread.sleep(2000);

		Utils.clickXpath(driver, ActionXpath.clickon3rdnext, "clickon3rdnext");

	}

	@Test
	public static void qualification_info(String app_url, WebDriver driver, String[] csvCell) throws Exception

	{
		String education = csvCell[33];
		String finic = csvCell[34];

		String startingdate = csvCell[35];
		String joiningdate = csvCell[36];
		String enddate = csvCell[37];

		String iwanttojoin = csvCell[38];
		String aftercompletion = csvCell[39];

		Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.qualification);
		Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.qualification, education);
		Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickonbatch, "clickonbatch");

		List<WebElement> batch = driver
				.findElements(By.xpath("//ul[contains(@class,'MuiList-root MuiMenu-list MuiList-padding')]//li"));
		String batchsel1 = csvCell[65];
		for (int i = 0; i < batch.size(); i++) {

			if (batch.get(i).getText().contains(batchsel1)) {
				batch.get(i).click();
				break;
			}

		}

//		WebElement bat = driver.findElement(By.xpath("(//li[@role='option'])[2]"));
//		String batch = bat.getText();
//		System.out.println(batch);
//		Thread.sleep(5000);
//		driver.findElement(By.xpath("//li[@data-value='" + batch + "']")).click();

//     Thread.sleep(2000);
//     Utils.cleartext(driver, ActionXpath.finicial);
//     Thread.sleep(2000);
//     Utils.callSendkeys(driver, ActionXpath.finicial, finic);

		Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.startingdate);
		Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.startingdate, startingdate);

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.joiningdate);
		Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.joiningdate, joiningdate);

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.enddate);

		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enddate, enddate);

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.iwanttojoin);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.iwanttojoin, iwanttojoin);

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.aftercompletion);

		Utils.callSendkeys(driver, ActionXpath.aftercompletion, aftercompletion);
		// Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickon4thnext, "clickonnext");

	}

	@Test
	public static void uploadfile_info(String app_url, WebDriver driver, String[] csvCell) throws Throwable

	{

		Thread.sleep(6000);

		Utils.callSendkeys(driver, ActionXpath.passport, "C:\\Users\\Public\\Documents\\samplepptx.pptx");

		WebElement getalert = driver.findElement(By.xpath("//div[@role='alert']"));
		String message = getalert.getText();
		System.out.println(message);
	}

	@Test
	public static void Multipleuploadfile_info(String app_url, WebDriver driver, String[] csvCell) throws Throwable

	{
		Thread.sleep(5000);

		Utils.callSendkeys(driver, ActionXpath.passport, "C:\\Users\\Dell\\Desktop\\Screenshot (154).png");
		Thread.sleep(5000);
		Utils.callSendkeys(driver, ActionXpath.addhar, "C:\\Users\\Dell\\Desktop\\Screenshot (154).png");
		Thread.sleep(6000);
		Utils.callSendkeys(driver, ActionXpath.school, "C:\\Users\\Dell\\Desktop\\Screenshot (154).png");

		Thread.sleep(10000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		Utils.clickXpath(driver, ActionXpath.clickon5thnext, "clickonnext");

	}

	@Test
	public static void checkbox(String app_url, WebDriver driver, String[] csvCell) throws Throwable

	{
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-disabled='false']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e1);
		Utils.clickXpath(driver, ActionXpath.clickon6thnext, "clickonnext");

	}

	@Test
	public static void submit_app(String app_url, WebDriver driver, String[] csvCell) throws Throwable

	{
		Thread.sleep(10000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e11 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Submit']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e11);

	}

	@Test

	public static void salesforce_login(String sal_url, WebDriver driver, String[] csvCell) throws Exception {

		String username = csvCell[41];
		String password = csvCell[42];

		Utils.callSendkeys(driver, ActionXpath.username, username);

		Utils.callSendkeys(driver, ActionXpath.password, password);

		Utils.clickXpath(driver, ActionXpath.logintosalesforce, "login to salesforce");

	}

	@Test

	public static void clickonleadandselect(String sal_url, WebDriver driver, String[] csvCell) throws Exception {

		Thread.sleep(10000);

		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Leads']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
		System.out.println("click on leads");
		Thread.sleep(5000);

		Utils.clickXpath(driver, ActionXpath.clickondropdown, "clickondropdown");

		Thread.sleep(5000);

		// WebDriverWait wait = new WebDriverWait(driver, 20);

		WebElement e1 = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[.='w2l automation']|(//span[.='w2l automation'])[1]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e1);
		Thread.sleep(5000);

		WebElement e11 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-refid='recordId']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e11);

	}

	@Test

	public static void clickonApplicationandselect(String sal_url, WebDriver driver, String[] csvCell)
			throws Exception {

		Thread.sleep(10000);

		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Applications']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
		System.out.println("click on Application");
		Thread.sleep(5000);

//		Utils.clickXpath(driver, ActionXpath.clickondropdown, "clickondropdown");
//
//		Thread.sleep(5000);
//
//		// WebDriverWait wait = new WebDriverWait(driver, 20);
//		WebElement e1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='All'])[1]")));
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e1);
//

//		driver.findElement(By.xpath("//a[@title='"+Applicant+"']"));
//	Thread.sleep(5000);

		List<WebElement> clickapplicant = driver.findElements(By.xpath("//table//tbody//td[3]//a"));
		String Applicant = csvCell[67];
		for (int i = 0; i < clickapplicant.size(); i++) {

			if (clickapplicant.get(i).getText().contains(Applicant)) {
				clickapplicant.get(i).click();

				break;
			}

		}

		Thread.sleep(3000);
		// WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e11 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='slds-button slds-button_icon-border slds-button_icon-x-small'])[1]")));
	((JavascriptExecutor) driver).executeScript("arguments[0].click();", e11);

	//	driver.findElement(By.xpath("(//span[@class='view-all-label'])[1]")).click();
		
		
//		Thread.sleep(3000);
//		
//		WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//lightning-button-menu[@class=\"slds-dropdown-trigger slds-dropdown-trigger_click\"])[2]")));
//		((JavascriptExecutor)driver).executeScript("arguments[0].click();", elem);
//		//driver.findElement(By.xpath("(//*[name()='svg' and @class='slds-icon slds-icon-text-default slds-icon_xx-small'])[54]")).click();
	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@title='Delete']")).click();
	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[.='Delete']")).click();
		Thread.sleep(3000);
		
		Thread.sleep(10000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		Thread.sleep(3000);
//		
//		
//	
		
		Thread.sleep(3000);
		// WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e111 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='slds-button slds-button_icon-border slds-button_icon-x-small'])[7]")));
	((JavascriptExecutor) driver).executeScript("arguments[0].click();", e111);

//	driver.findElement(By.xpath("(//span[text()='View All'])[5]")).click();
//		
//		
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("(//a[@title='Show 2 more actions'])[2]")).click();
//	
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@title='Delete']")).click();
//	
		Thread.sleep(3000);
	driver.findElement(By.xpath("//span[.='Delete']")).click();
		Thread.sleep(3000);
//		driver.navigate().back();
	
		WebElement elqq = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class='slds-button slds-button_icon-border-filled']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", elqq);

		WebElement we1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@name='Delete']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", we1);

		///// a[@title='Delete']

		Thread.sleep(5000);
		WebElement le = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[2]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", le);

		Thread.sleep(5000);
//		WebElement eqeq = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Delete']")));
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", eqeq);

	}

	@Test

	public static void clickonApplicationview(String sal_url, WebDriver driver, String[] csvCell) throws Exception {

		Thread.sleep(10000);

		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Applications']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
		System.out.println("click on Application");
		Thread.sleep(5000);

		Utils.clickXpath(driver, ActionXpath.clickondropdown, "clickondropdown");

		Thread.sleep(5000);

		// WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='w2lead']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e1);

		Thread.sleep(5000);

		// WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e1a = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@data-refid='recordId'])[1]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e1a);

		Thread.sleep(5000);

		// WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e12 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@title='Application View']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e12);

//		Thread.sleep(2000);
//		List<WebElement> ele = driver.findElements(By.tagName("body"));
//         String p = ele.get
//        System.out.println(p);
//        
//		
//         if (p.contains("First Name") && p.contains("Father Name") 
//                 && p.contains("Date Of Birth") && p.contains("Present Occupation") && p.contains("Gender")){
//                     log.info("  application view  PASSED  \n\n");
//                 }else {
//                     log.warning("  application view FAILED \n\n");
//                 }
//             

//		 List<WebElement> elementsWithOwnText = new ArrayList<WebElement>();
//		    List<WebElement> allElements = driver.findElements(By.xpath("//*"));
//		    for (WebElement element: allElements) {
//		        List<WebElement> childElements = element.findElements(By.xpath(".//*"));
//		        String text = element.getText();
//		        //System.out.println(text);
//		        if (childElements.size() == 0 && text.length() > 0) {
//		            elementsWithOwnText.add(element);
//		            System.out.println(element);
//		        }
//		    }	
//		
//		
//		    WebElement webElement = driver.findElement(By.xpath("*******"));
//		    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;  
//		    String compareHolder = (String) jsExecutor.executeScript("return arguments[0].value", webElement);
//		    

		Thread.sleep(5000);

		// WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e1aq = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Edit']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e1aq);

		Thread.sleep(5000);
		// driver.findElement(By.xpath("/html/body/div[4]/div[1]/section/div[1]/div[2]/div[2]/div[1]/div/div/div/div/div/one-record-home-flexipage2/forcegenerated-adg-rollup_component___force-generated__flexipage_-record-page___-application_-record_-page___hed__-application__c___-v-i-e-w/forcegenerated-flexipage_application_record_page_hed__application__c__view_js/record_flexipage-record-page-decorator/div[1]/records-record-layout-event-broker/slot/slot/flexipage-record-home-template-desktop2/div/div[2]/div[1]/slot/flexipage-component2/slot/flexipage-tabset2/div/lightning-tabset/div/slot/slot/flexipage-tab2[3]/slot/flexipage-component2/slot/flexipage-aura-wrapper/div/div/div[2]/div[1]/button[3]")).click();
		WebElement ele = driver.findElement(By.xpath("//input[@name='FirstName']"));
		String se = ele.getText();
		System.out.println(se);

		driver.quit();
//		Thread.sleep(2000);
//		WebElement actual =driver.findElement(By.xpath("//input[@name='Date Of Birth']"));
//		String actualresult = actual.getText();
//		//expectedresult =actual.getText();
//		System.out.println(actualresult);

//		Thread.sleep(2000);
//		WebElement expected =driver.findElement(By.xpath("//input[@name='Date Of Birth']"));
//		String expectedresult = expected.getText();
//System.out.println(expectedresult);
//		
//	
//	
//
//		Thread.sleep(5000);
//		
//		if (expectedresult.equals(csvCell[11])) {
//			System.out.println("matching");
//		} else {
//			System.out.println("not matching");
//		}
		Thread.sleep(2000);
		driver.navigate().back();
//

		Thread.sleep(5000);
		WebElement elqq = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//a[@class='rowActionsPlaceHolder slds-button slds-button--icon-x-small slds-button--icon-border-filled keyboardMode--trigger']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", elqq);

		Thread.sleep(5000);
		WebElement le = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Delete']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", le);

		Thread.sleep(5000);
		WebElement eqeq = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Delete']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", eqeq);

	}

	@Test

	public static void markasStatus(String sal_url, WebDriver driver, String[] csvCell) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		Thread.sleep(5000);
		WebElement e111 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Mark Status as Complete']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e111);

		Thread.sleep(5000);

		WebElement e1111 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Mark Status as Complete']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e1111);

	}

	@Test

	public static void selectsaveanddelete(String sal_url, WebDriver driver, String[] csvCell) throws Exception {
		Thread.sleep(5000);

		Select s = new Select(driver.findElement(By.xpath("//select[@class='stepAction required-field select']")));
		s.selectByValue("converted");

		Thread.sleep(3000);

		s.selectByValue("Registration Complete");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e5 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Save'])[2]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e5);
		Thread.sleep(4000);

		WebElement e51 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Delete']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e51);
		Thread.sleep(3000);

		WebElement e511 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[.='Delete'])[2]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e511);

	}

	@Test

	public static void profile_logout(String sal_url, WebDriver driver, String[] csvCell) throws Exception {
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement e6 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@class='uiImage'])[1]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e6);
		Thread.sleep(3000);
		WebElement e61 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Log Out']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e61);

	}

	@Test
	public static void signout_app(String app_url, WebDriver driver, String[] csvCell) throws Throwable

	{
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement e111 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Sign out']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e111);

	}

	@Test
	public static void salesforce_applicationviewanddelete(String sal_url, WebDriver driver, String[] csvCell)
			throws java.lang.Exception

	{
		String url = csvCell[40];
		driver.get(url);
		Utils.salesforce_login(sal_url, driver, csvCell);
		Utils.clickonApplicationandselect(sal_url, driver, csvCell);

		Utils.profile_logout(sal_url, driver, csvCell);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(4));

	}

	@Test
	public static void w2lform_validationcheck(WebDriver driver, String[] csvCell, String url) throws Throwable {
		String firstnamevalidate = csvCell[43];

		String lastnamevalidate = csvCell[44];
		String emailvalidate = csvCell[45];
		String mobilevalidate = csvCell[46];

		Utils.callSendkeys(driver, ActionXpath.firstname, firstnamevalidate);
		WebElement fi = driver.findElement(
				By.xpath("(//p[@class='MuiTypography-root jss21 MuiTypography-body1 MuiTypography-alignLeft'])[1]"));
		String firstname = fi.getText();
		System.out.println(
				"first name should contain only alphabets with 40 character should not contain alphanumeric \n");

		System.out.println(firstname);

		Utils.callSendkeys(driver, ActionXpath.lastName, lastnamevalidate);
		WebElement last = driver.findElement(
				By.xpath("(//p[@class='MuiTypography-root jss21 MuiTypography-body1 MuiTypography-alignLeft'])[3]"));
		String lastName = last.getText();
		System.out.println(
				"last name should contain only alphabets with 40 character should not contain alphanumeric \n");

		System.out.println(lastName);

		Utils.callSendkeys(driver, ActionXpath.email, emailvalidate);
		WebElement emai = driver.findElement(
				By.xpath("(//p[@class='MuiTypography-root jss21 MuiTypography-body1 MuiTypography-alignLeft'])[4]"));
		String email = emai.getText();
		System.out.println(
				"email should contain 1 specialcharcter,1uppercase and lowercase should not exceed morthan 320 characters \n");

		System.out.println(email);

		Utils.callSendkeys(driver, ActionXpath.mobile, mobilevalidate);
		WebElement mobil = driver.findElement(
				By.xpath("(//p[@class='MuiTypography-root jss21 MuiTypography-body1 MuiTypography-alignLeft'])[5]"));
		String mobile = mobil.getText();
		System.out.println("mobile phone should contain only 10digit valid number shouldn't contain alphanumeric \n");
		System.out.println(mobile);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");

//		if (firstname.contains(firstname) && lastName.contains(lastName) && email.contains(email)
//				&& mobile.contains(mobile)) {
//			log.info(" firstname and lastname should contain only alphabets with 40 character. email should contain 1 @special charcter with one uppercase  ,phone number  should contain only 10 digit number should not contain alphanumeric than  PASSED \n\n");
//		} else {
//			log.warning(" firstname and lastname should contain only alphabets with 40 character. email should contain 1 @special charcter with one uppercase  ,phone number  should contain only 10 digit number should not contain alphanumeric if it doesn't contain than Failed\n");
//		}

	}

	@Test
	public static void firstname_validationalphabet(WebDriver driver, String[] csvCell, String url) throws Throwable {
		String firstnamevalidate = csvCell[43];
		Utils.callSendkeys(driver, ActionXpath.firstname, firstnamevalidate);
		WebElement fi = driver.findElement(
				By.xpath("(//p[@class='MuiTypography-root jss21 MuiTypography-body1 MuiTypography-alignLeft'])[1]"));
		String firstname = fi.getText();
		System.out.println(
				"first name should contain only alphabets with 40 character should not contain alphanumeric \n");

		System.out.println(firstname);
	}

	@Test
	public static void validateAppbasic_info(String app_url, WebDriver driver, String[] csvCell) throws Throwable {

		String firstname = csvCell[5];
		String surename = csvCell[16];
		String fathername = csvCell[17];

		String dob = csvCell[11];

		String occupation = csvCell[18];

		String grampanchayat = csvCell[13];
		String village = csvCell[14];
		String hamlet = csvCell[15];

		String religion = csvCell[19];
		String age = csvCell[20];

		String mothername = csvCell[21];

		String guardianname = csvCell[22];
		String hobbies = csvCell[23];

		Utils.clickXpath(driver, ActionXpath.clickonnewapplication, "clickonnewapplication");

		Utils.clickXpath(driver, ActionXpath.clickonapply, "clickonapply");

		Utils.clickXpath(driver, ActionXpath.clickonyes, "clickonyes");
		Utils.cleartext(driver, ActionXpath.firstname);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.firstname, firstname);
		// Thread.sleep(1000);
		Utils.cleartext(driver, ActionXpath.surename);
		// Thread.sleep(1000);
		Utils.callSendkeys(driver, ActionXpath.surename, surename);
		// Thread.sleep(1000);
		Utils.cleartext(driver, ActionXpath.enterfathername);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterfathername, fathername);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.enterdob);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterdob, dob);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.presentoccupation);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.presentoccupation, occupation);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.gramPanchayat1);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.gramPanchayat1, grampanchayat);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.Hamlet);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.Hamlet, hamlet);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.Village);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.Village, village);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.religion);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.religion, religion);

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.age);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.age, age);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.entermothername);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.entermothername, mothername);
		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.enterguardianname);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterguardianname, guardianname);

		// Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");

		// Thread.sleep(2000);
		Utils.cleartext(driver, ActionXpath.enterhobbies);
		// Thread.sleep(2000);
		Utils.callSendkeys(driver, ActionXpath.enterhobbies, hobbies);

		// Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickonbloodgroup, "clickonbloodgroup");
		// Thread.sleep(2000);

		WebElement blo = driver.findElement(By.xpath("(//li[@role='option'])[5]"));
		String bloodgroup = blo.getText();
		System.out.println(bloodgroup);
		// Thread.sleep(5000);
		driver.findElement(By.xpath("//li[@data-value='" + bloodgroup + "']")).click();

		// Thread.sleep(2000);
		Utils.clickXpath(driver, ActionXpath.clickoncaste1, "clickoncaste1");
		// Thread.sleep(2000);

		WebElement cast = driver.findElement(By.xpath("(//li[@role='option'])[5]"));
		String castism = cast.getText();
		System.out.println(castism);
		// Thread.sleep(5000);
		driver.findElement(By.xpath("//li[@data-value='" + castism + "']")).click();

		// Thread.sleep(2000);

		Utils.clickXpath(driver, ActionXpath.clickonnext, "clickonnext");

	}

	@Test
	public static Boolean checkmiddlename(String url) {
		String urlToMatch = "nitte|enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkadditionalphone(String url) {
		String urlToMatch = "nitte|enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkToskipdob(String url) {
		String urlToMatch = "nitte|enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkToskipcourse(String url) {
		String urlToMatch = "ltpct|nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkToskipcours(String url) {
		String urlToMatch = "ltpct|essci";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkToskip(String url) {
		String urlToMatch = "enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkToskipnitte(String url) {
		String urlToMatch = "enquire-samsung|nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkTodropdown(String url) {
		String urlToMatch = "nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkage(String url) {
		String urlToMatch = "ltpct|nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkToskipurlnotmatching(String url) {
		String urlToMatch = "enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checktraining(String url) {
		String urlToMatch = "enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkadhar(String url) {
		String urlToMatch = "ltpct|nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checcourse(String url) {
		String urlToMatch = "ltpct|nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkaadhar(String url) {
		String urlToMatch = "enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkenquire(String url) {
		String urlToMatch = "enquire-samsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkboxSkip(String url) {
		String urlToMatch = "enquire-samsung|nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkmarkasstatus(String url) {
		String urlToMatch = "ltpct|nitte";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static String getTEXT(WebDriver driver, String xpath) throws Exception {
		int count = 0;
		int maxTries = 7;
		String HtmlText = "";
		while (true) {
			try {
				WebElement p = driver.findElement(By.xpath(xpath));
				HtmlText = p.getText();
				return HtmlText;
			} catch (Exception e) {
				Utils.smallSleepBetweenClicks(1);
				if (++count > maxTries) {
					throw (e);
				}
			}
		}
	}

}
