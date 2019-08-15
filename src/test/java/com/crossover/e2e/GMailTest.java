package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GMailTest {
	   private WebDriver driver;
	    private Properties properties = new Properties();

	    @BeforeMethod
	    public void setUp() throws Exception {
	        
	        properties.load(new FileReader(new File("./resources/test.properties")));
	        //Dont Change below line. Set this value in test.properties file incase you need to change it..
	        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	    }
	    
	    @AfterMethod
	    public void tearDown() throws Exception {
	        driver.quit();
	    }

	    /*
	     * To login to gmail and compose a new mail with subject and mail body from the properties file and send it.
	     * 
	     */
	    @Test
	    public void testSendEmail() throws Exception {
	        driver.get("https://mail.google.com/");
	           
	        WebElement userElement = driver.findElement(By.id("identifierId"));
	        userElement.sendKeys(properties.getProperty("username"));

	        driver.findElement(By.id("identifierNext")).click();

	        Thread.sleep(1000);

	        WebElement passwordElement = driver.findElement(By.name("password"));
	        passwordElement.sendKeys(properties.getProperty("password"));
	        driver.findElement(By.id("passwordNext")).click();

	        Thread.sleep(1000);

	        WebElement composeElement = driver.findElement(By.xpath("//*[@role='button' and contains(text(),'Compose')]"));
	        composeElement.click();
	        Thread.sleep(1000);
	        driver.findElement(By.name("to")).clear();
	        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
	        
	        // emailSubject and emailbody to be used in this unit test.
	        String emailSubject = properties.getProperty("email.subject");
	        String emailBody = properties.getProperty("email.body");
	        
	        driver.findElement(By.name("subjectbox")).sendKeys(emailSubject);
	        driver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(emailBody);
	        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
	        
	    }    
		
}
