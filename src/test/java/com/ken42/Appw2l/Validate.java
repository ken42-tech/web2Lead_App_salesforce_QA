package com.ken42.Appw2l;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class Validate {
    static int time = 2000;
    static WebDriver driver;
     
    public static boolean  validate20CharLength(WebDriver driver, String xpath, String errorXpath) throws Exception {
        try{
            String Char41char = "sasadasasasasasasasa";
        Utils.callSendkeys(driver, xpath, Char41char);
        // WebElement alphafirstname = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
        WebElement errorMessage = driver.findElement(By.xpath(errorXpath));
            String checkwithlength = errorMessage.getText();
            
            WebElement number = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checklastNamewithcharactersize = number.getText();
			System.out.println(checklastNamewithcharactersize);
			

			if (!checklastNamewithcharactersize.equals(null)) {

				System.out.println("Tc:- data validation check with length size failed it's null ");

			}

            if (checkwithlength.contains("cannot")) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e){
        Utils.printException(e);
        throw (e);
        }
        
    }
    public static boolean  validateNumber(WebDriver driver, String xpath, String errorXpath) throws Exception {
        try{
            String Number = "202284989379393793783783";
        Utils.callSendkeys(driver, xpath, Number);
        // WebElement alphafirstname = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
        WebElement errorMessage = driver.findElement(By.xpath(errorXpath));
        
        WebElement alphalastName = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
		String checklastNamewithnumber = alphalastName.getText();
		System.out.println(checklastNamewithnumber);
	

		if (!checklastNamewithnumber.equals(null)) {

			System.out.println("Tc:- data validation check with number failed it's null  ");

		}

            String checkfirstnamewithnumber = errorMessage.getText();
            if (checkfirstnamewithnumber.contains("cannot")) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e){
        Utils.printException(e);
        throw (e);
        }
        
    }
    public static boolean  validateSpecialChar(WebDriver driver, String xpath, String errorXpath) throws Exception {
        try{
            String CharSpecial = "@%$^%*&!()%&&%&$$$";
        Utils.callSendkeys(driver, xpath, CharSpecial);
        // WebElement alphafirstname = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
        WebElement errorMessage = driver.findElement(By.xpath(errorXpath));
            String checkwithspecialcharacter = errorMessage.getText();
            
            WebElement SpecialCharacteronly = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
			String checklastNamewithSpecialCharacteronly = SpecialCharacteronly.getText();
			System.out.println(checklastNamewithSpecialCharacteronly);
			
			if (!checklastNamewithSpecialCharacteronly.equals(null)) {

				System.out.println("Tc:- data validation check with specialCharacter failed it's null ");

			}

            if (checkwithspecialcharacter.contains("cannot")) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e){
        Utils.printException(e);
        throw (e);
        }
        
    }
    public static boolean  validate80CharLength(WebDriver driver, String xpath, String errorXpath) throws Exception {
        try{
            String Char80char = "sddadsadadsadadadadadadadaddadasdadadadaddsdadsddadsaddasddadsadadsadadadadadadadaddadasdadadadaddsdadsddadsadda";
        Utils.callSendkeys(driver, xpath, Char80char);
        // WebElement alphafirstname = driver.findElement(By.xpath("//span[@style='color: rgb(185, 37, 0);']"));
        WebElement errorMessage = driver.findElement(By.xpath(errorXpath));
            String checkfirstnamewithnumber = errorMessage.getText();
            if (checkfirstnamewithnumber.contains("cannot")) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e){
        Utils.printException(e);
        throw (e);
        }
        
    }
}
