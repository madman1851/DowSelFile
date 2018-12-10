package com.project.work;

import com.gargoylesoftware.htmlunit.html.DomElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        String _menu = getClick();
        WebElement menu = webDriver.findElement(By.xpath(_menu));
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
        checkForDriver();
        ConnectToWeb();
        InsertCredentials();
        NavigateToReports();
        DownloadReports();
        CloseAll();
    }

    public void checkForDriver() {
        try {
            dirSearch();
            URL website = new URL("https://sourceforge.net/projects/htmlunit/files/latest/download");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("htmlDriver.zip");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            unzipFile("htmlDriver.zip",webDriverPath);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void unzipFile(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean dirSearch(){
        File f = new File(webDriverPath);
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(webDriverName);
            }
        });
        if(matchingFiles!=null) return true;
        return false;
    }

}
