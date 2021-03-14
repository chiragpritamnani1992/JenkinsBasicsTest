package FreeCRMBasics;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FreeCrmJenkinsRunChrome {


	WebDriver driver;


	public void JSExecutorBtnClick(WebElement ele)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", ele);
	}

	@Test(priority=1)
	public void Login() throws InterruptedException
	{
		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.manage().window().maximize();

		driver.get("https://ui.cogmento.com/");

		Thread.sleep(2000);

		driver.findElement(By.name("email")).sendKeys("chirag.pritamnani@gmail.com");

		driver.findElement(By.name("password")).sendKeys("Chirya@1992");

		WebElement submitBtn = driver.findElement(By.xpath("//div[contains(@class,'submit button')]"));

		JSExecutorBtnClick(submitBtn);

		System.out.println(driver.getTitle());
	}

	@Test(priority=2)
	public void AddContactLog()
	{
		WebDriverWait w = new WebDriverWait(driver,30);

		WebElement contact = driver.findElement(By.xpath("//span[contains(text(),'Contacts')]"));

		Actions action = new Actions(driver);
		action.moveToElement(contact).perform();

		WebElement Addcontact = driver.findElement(By.xpath("//span[contains(text(),'Contacts')]/../..//button"));
		JSExecutorBtnClick(Addcontact);

		driver.findElement(By.name("first_name")).sendKeys("test2userFname");
		driver.findElement(By.name("last_name")).sendKeys("test3userLname");

		driver.findElement(By.xpath("//i[contains(@class,'save icon')]")).click();

		try {
			w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//i[@class='trash icon']"))));
			driver.findElement(By.xpath("//i[@class='trash icon']")).click();
		}

		catch(StaleElementReferenceException e) {
			w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//i[@class='trash icon']"))));
			driver.findElement(By.xpath("//i[@class='trash icon']")).click();
		}

		WebElement delete=driver.findElement(By.xpath("//button[@class='ui red button']"));
		w.until(ExpectedConditions.visibilityOf(delete));
		delete.click();

		List<WebElement> ls = driver.findElements(By.xpath("//div[@role='listbox']//i[@class='settings icon']"));
		ls.get(0).click();

		driver.findElement(By.xpath("//a[@role='option']//i[@class='power icon']")).click();

	}

}
