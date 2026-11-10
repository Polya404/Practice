package org.application;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
    }

    private static void actions(WebDriver driver) {
        Actions actions = new Actions(driver);
        actions
//                .moveToElement()
//                .keyDown()
//                .keyUp()
                .clickAndHold()
                .release()
                .build()
                .perform();
    }

    private static WebElement getWebElement(WebDriver driver) {
        driver.get("https://dev.ua/");
        WebElement element = driver.findElements(By.cssSelector(".navbar__nav-item")).get(10);
        System.out.println(element.getCssValue("display"));
        return element;
    }

    private static void rozetka(WebDriver driver) {
        driver.get("https://rozetka.com.ua/");

        WebElement element = driver.findElements(By.cssSelector("div[class='tile__prices ng-star-inserted']")).get(3);

        System.out.println(element.getAttribute("scrollWidth"));
        System.out.println(element.getText());
    }

    private static void waiters(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement element = (new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("1"))));
    }

    private static void locators(WebDriver driver) {
        WebElement inputXpath = driver.findElement(By.xpath("//input[@aria-label='Пошук Google']"));
        WebElement inputCssSelector = driver.findElement(By.cssSelector("body"));
        WebElement inputClassName = driver.findElement(By.className(""));
        WebElement inputId = driver.findElement(By.id(""));
        WebElement inputLinkText = driver.findElement(By.linkText(""));
        WebElement inputName = driver.findElement(By.name(""));
        WebElement inputTagName = driver.findElement(By.tagName(""));
    }
}
