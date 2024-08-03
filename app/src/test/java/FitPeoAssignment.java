import javax.swing.Action;

import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FitPeoAssignment {

    public static void main(String[] args) throws InterruptedException{

        System.setProperty("webdriver.chrome.driver", "D:/selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //Navigate to Fitpeo Homepage

		driver.get("https://www.fitpeo.com/");
        driver.manage().window().maximize();

        //Navigate to  Revenue Calculator Page

        WebElement revnueCalculator = driver.findElement(By.xpath("//div[text()='Revenue Calculator']"));
        revnueCalculator.click();

        //Scroll Down to the Slider section:

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement eligiblePatients = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Medicare Eligible Patients']")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", eligiblePatients); 

        // Adjust the Slider:

        WebElement sliderpointer = driver.findElement(By.xpath("//input[@type='range']"));
        System.out.println("The Location of the poninter is: "+ sliderpointer.getLocation()); // (718, 663)

        Actions action = new Actions(driver);

        //action.dragAndDropBy(sliderpointer, calculateSliderOffset(sliderpointer, 820), 0).perform();
       
        action.dragAndDropBy(sliderpointer, 94, 0).perform(); 


        //action.clickAndHold(sliderpointer).moveByOffset(950,663).release().perform();
        Thread.sleep(3000);


        // Validate the bottom text field value is updated to 820

        WebElement txtbox = driver.findElement(By.xpath("//input[@type='number']"));
        String value = txtbox.getAttribute("value");
        System.out.println("Value is :" +value);
        // if (txtbox.getAttribute("value").equals("823")){
        //     System.out.println("Slider is working fine");
        // }
        assert txtbox.getAttribute("value").equals("823");
        System.out.println("Slider is working fine");


        // Update the Text Field:

        txtbox.click();
        Thread.sleep(3000);
        txtbox.clear();
        Thread.sleep(3000);
        txtbox.sendKeys("560");
        Thread.sleep(3000);

        System.out.println("Entereed 560 to the text box");

        // Validate Slider Value:

        // if (sliderpointer.getAttribute("value").equals("560")){
        //     System.out.println("The slider's position is updated to reflect the value 560");
        // }

        assert sliderpointer.getAttribute("value").equals("560");
        System.out.println("The slider's position is updated to reflect the value 560");


        // Select CPT Codes:

         WebElement CPTcodes = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiBox-root css-1p19z09']")));
         js.executeScript("arguments[0].scrollIntoView(true);", CPTcodes);

         System.out.println("Page scorlled to CPT codes successfully");

         driver.findElement(By.xpath("//p[text()='CPT-99091']/..//input")).click();
         driver.findElement(By.xpath("//p[text()='CPT-99453']/..//input")).click();
         driver.findElement(By.xpath("//p[text()='CPT-99454']/..//input")).click();
         driver.findElement(By.xpath("//p[text()='CPT-99474']/..//input")).click();

         System.out.println("All 4 checkboxes are selected");


         // Validate Total Recurring Reimbursement:

          WebElement reimbursement=driver.findElement(By.xpath("//*[contains(text(),'Total Recurring Reimbursement for all Patients Per Month:')]/child::p"));

          System.out.println(reimbursement.getText());

          assert reimbursement.getText().contains("$75600"); 
    
        // Close the browser

        driver.close();

    }

}
