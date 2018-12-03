package com.project.work;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class selenium {

    private WebDriver webDriver;

    private String link;

    @Value("${webDriverName}")
    private String webDriverPath;
    @Value("${webDriverPath}")
    private String webDriverName;
    @Value("${LoginName}")
    private String account;
    @Value("${LoginPass}")
    private String password;

    public String click;

    public void ConnectToWeb()throws Throwable{
        System.setProperty(webDriverName, webDriverPath);
        webDriver = null;




        webDriver = new HtmlUnitDriver();
        /*DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        webDriver.navigate().to(link);
        webDriver.navigate().to("javascript:document.getElementById('overridelink').click()");*/

        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
        capabilities.setCapability("browserName","htmlunit");
        webDriver.get("");
    }

    public void InsertCredentials()throws Throwable{
        webDriver.findElement(By.name("j_username")).sendKeys(account);
        webDriver.findElement(By.name("j_password")).sendKeys(password);
        webDriver.findElement(By.name("submit")).click();
    }
    public void Click() throws Throwable
    {
        Thread.sleep(5000);
        webDriver.findElement(By.partialLinkText(click)).click();

    }
    public void ClickByXPath() throws Throwable
    {
        Thread.sleep(5000);
        webDriver.findElement(By.xpath(click)).click();


    }

    public void ClickByCSSPath() throws Throwable
    {
        Thread.sleep(5000);
        webDriver.findElement(By.cssSelector(click)).click();
    }

    public void CloseAll() throws Throwable
    {
        webDriver.quit();
    }

    public void NavigateToReports(){

    }

    public void DownloadReports(){


    }

    public void wdRun()throws Throwable{
        ConnectToWeb();
        InsertCredentials();
        NavigateToReports();

    }
}
