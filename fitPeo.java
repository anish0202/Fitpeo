package Selniumpractice;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.webdriver.WebDriverBrowser;

public class fitPeo {
	public static void main(String[] args) throws Exception {
   	
    ScreenRecorderUtil.startRecord("fitPeo");	
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
  	ChromeOptions co = new ChromeOptions();
	co.addArguments("--remote-allow-origins=*");
	WebDriver driver = new ChromeDriver(co);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    driver.manage().window().maximize();
    try {
    driver.get("https://www.fitpeo.com/");
    takeScreenshot(driver, "Step1_Homepage");
    
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    WebElement revene = driver.findElement(By.xpath("(//*[@class='satoshi MuiBox-root css-5ty6tm'])[5]"));
    revene.click();
    takeScreenshot(driver, "Step2_RevenueCalculatorPage");

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, 350);");
    takeScreenshot(driver, "Step3_SliderSection");
    
    WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-valuenow='200']")));
    String value = slider.getAttribute("value");
    value = (String) js.executeScript("return arguments[0].value;", slider);

    int currentValue = Integer.parseInt(value);
    System.out.println(currentValue);
    Actions action = new Actions(driver);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
          
    action.dragAndDropBy(slider, 94, 0).build().perform();
    js.executeScript("window.scrollBy(0, 400);");
    takeScreenshot(driver, "Step4_SliderAdjusted");
  
    WebElement textfiled = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-formControl MuiInputBase-sizeSmall css-129j43u']//input")));  
    js.executeScript("window.scrollBy(0, 400);");
    boolean true1 =textfiled.isDisplayed();
    boolean true2 =textfiled.isEnabled();
    System.out.println(true1);
    System.out.println(true2);
    String get = textfiled.getText();
    System.out.println(get);
    textfiled.sendKeys(Keys.CONTROL, "a"); 
    textfiled.sendKeys(Keys.DELETE);
    textfiled.sendKeys("560");
    String value45 = slider.getAttribute("value");
    int currentValue1 = Integer.parseInt(value45);
    System.out.println(currentValue1);
    Assert.assertEquals(slider.getAttribute("value"), "560", "Slider value is correct!");
    takeScreenshot(driver, "Step5_SliderAdjusted");
    
    WebElement textfiled2 = driver.findElement(By.xpath("//*[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-formControl MuiInputBase-sizeSmall css-129j43u']//input"));  
    textfiled2.sendKeys(Keys.CONTROL, "a"); 
    textfiled2.sendKeys(Keys.DELETE);
    textfiled2.sendKeys("820");

    String[] cptCodes = {"CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474"};
    
    for (String cptCode : cptCodes) {
        try {
            WebElement checkbox = driver.findElement(By.xpath("//*[text()='" + cptCode + "']//parent::div//label/span/input"));
            checkbox.click();
            System.out.println(cptCode + " checkbox clicked successfully.");
        } catch (Exception e) {
            System.out.println("Failed to click checkbox for " + cptCode + ": " + e.getMessage());
        }
    }


      takeScreenshot(driver, "Step7_TotalReimbursementValidated");
      WebElement totalReimbursementHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='MuiTypography-root MuiTypography-body1 inter css-1bl0tdj'])[4]"))); 
      String totalReimbursementText = totalReimbursementHeader.getText();
      assert totalReimbursementText.contains("$110700");
      System.out.println(totalReimbursementText);
      ScreenRecorderUtil.stopRecord();	
    } catch (Exception e) {
        System.err.println("Test Failed: " + e.getMessage());
        e.printStackTrace();
    } finally {
    driver.quit();
    }
}

 	

private static void takeScreenshot(WebDriver driver, String stepName) {
    TakesScreenshot screenshot = (TakesScreenshot) driver;
    File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
    File destFile = new File("C:/TestResults/Screenshots/" + stepName + ".png"); 
    try {
        destFile.getParentFile().mkdirs();
        ImageIO.write(ImageIO.read(srcFile), "png", destFile);
    } catch (IOException e) {
        System.err.println("Failed to save screenshot for " + stepName + ": " + e.getMessage());
    }
}
}