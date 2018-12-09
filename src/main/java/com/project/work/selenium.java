package com.project.work;

import com.gargoylesoftware.htmlunit.html.DomElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Service
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
    @Value("${saveFilePath}")
    private String savePath;

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    private String click;

    public void ConnectToWeb()throws Throwable{
        System.setProperty(webDriverName, webDriverPath);
        webDriver = null;
        webDriver = new HtmlUnitDriver();
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
        webDriver.findElement(By.partialLinkText(click)).click();
    }
    public void ClickByXPath() throws Throwable
    {
        webDriver.findElement(By.xpath(click)).click();
    }

    public void ClickByCSSPath() throws Throwable
    {
        webDriver.findElement(By.cssSelector(click)).click();
    }

    public void CloseAll() throws Throwable
    {
        webDriver.quit();
    }
    
    public void pickFromMenu(String link) throws Throwable{
        String menu = getClick();
        WebElement menu = webDriver.findElement(By.xpath(menu));
        Actions action = new Actions(webDriver);
        action.moveToElement(menu).perform();
        webDriver.findElement(By.linkText(link)).click();
    }

    public void NavigateToReports() throws Throwable{
        try {
            setClick("");
            Click();
            setClick("");
            Click();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void DownloadReports()throws Exception{
        DomElement file = (DomElement) webDriver.findElement(By.name(""));//.getElementByName("File");
        InputStream inputStream = file.click().getWebResponse().getContentAsStream();

        OutputStream outputStream = new FileOutputStream(new File(savePath));
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    }

    public void wdRun()throws Throwable{
        ConnectToWeb();
        InsertCredentials();
        NavigateToReports();
        DownloadReports();
        CloseAll();
    }
}
