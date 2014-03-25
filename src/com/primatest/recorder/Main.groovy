package com.primatest.recorder

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.ie.InternetExplorerDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

import java.util.concurrent.TimeUnit

/**
 * Created with IntelliJ IDEA.
 * User: Dmitri
 * Date: 11/4/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
class Main {

    def static initScript =
        '''
function getPathTo(element) {
    if(element.tagName == "A"){
        return "//a[text()='"+element.textContent+"']";
    }
    if (element.id!=='')
        return "//*[@id='"+element.id+"']";
    if (element===document.body)
        return element.tagName;

    var ix= 0;
    var siblings= element.parentNode.childNodes;
    for (var i= 0; i<siblings.length; i++) {
        var sibling= siblings[i];
        if (sibling===element)
            return getPathTo(element.parentNode)+'/'+element.tagName+'['+(ix+1)+']';
        if (sibling.nodeType===1 && sibling.tagName===element.tagName)
            ix++;
    }
}

document.redwoodRecording = [];

document.onkeydown = function(ev){
    if(ev.keyCode == 13){
        var path = getPathTo(ev.target);
        //document.redwoodRecording.push('Browser.Driver.findElement(By.xpath("'+ path +'")).sendKeys("'+ ev.target.value +'");Browser.Driver.findElement(By.xpath("'+ path +'")).sendKeys(org.openqa.selenium.Keys.ENTER)');
    }
}

document.onchange = function(ev){
    //console.log(ev);
    if(ev.target.tagName === "INPUT"){
        if((ev.target.type === "checkbox") ||(ev.target.type === "radio")){
            document.redwoodRecording.push(JSON.stringify({operation:"click",idType:"xpath",id:getPathTo(ev.target)}));
            //document.redwoodRecording.push('Browser.Driver.findElement(By.xpath("'+ getPathTo(ev.target) +'")).click()');
        }
        else if(ev.target.value === ""){
            document.redwoodRecording.push(JSON.stringify({operation:"clear",idType:"xpath",id:getPathTo(ev.target)}));
            //document.redwoodRecording.push('Browser.Driver.findElement(By.xpath("'+ getPathTo(ev.target) +'")).clear()');
        }
        else{
            document.redwoodRecording.push(JSON.stringify({operation:"sendKeys",idType:"xpath",id:getPathTo(ev.target),data:ev.target.value}));
            //document.redwoodRecording.push('Browser.Driver.findElement(By.xpath("'+ getPathTo(ev.target) +'")).sendKeys("'+ ev.target.value +'")');
        }
    }
    else if(ev.target.tagName == "SELECT"){
        var selectedText = ev.target.options[ev.target.selectedIndex].text;
        document.redwoodRecording.push(JSON.stringify({operation:"select",idType:"xpath",id:getPathTo(ev.target),data:selectedText}));
        //document.redwoodRecording.push('new Select(Browser.Driver.findElement(By.xpath("'+ getPathTo(ev.target) +'"))).selectByVisibleText("'+ selectedText +'")');
    }
};

document.onmouseup = function(ev){
    if(ev.target.tagName === "OPTION"){
        return;
    }
    if((ev.target.tagName != "INPUT") && (ev.target.tagName != "SELECT")){
        var path = getPathTo(ev.target);
        document.redwoodRecording.push(JSON.stringify({operation:"click",idType:"xpath",id:getPathTo(ev.target)}));
        //document.redwoodRecording.push('Browser.Driver.findElement(By.xpath("'+ path +'")).click()');
    }
};

'''

    def static collectScript =
        '''
   var callback = arguments[arguments.length - 1];
   window.onbeforeunload   = function(){
        var delay = function (ms) {
            var start = +new Date;
            while ((+new Date - start) < ms);
            //document.redwoodRecording.push("unloaded");
            callback(document.redwoodRecording);
        }
        delay(500);
    };
   var waitForActions = function(){

       if((document.redwoodRecording) && (document.redwoodRecording.length > 0)){
        callback(document.redwoodRecording);
        document.redwoodRecording = [];
       }
       else{
        setTimeout(waitForActions, 50);
       }
   }
   waitForActions();
'''

    public static void main(String[] args)
    {

        WebDriver RecDriver

        if(args[0] == "Chrome"){
            def service = new ChromeDriverService.Builder().usingPort(9515).usingDriverExecutable(new File("chromedriver.exe")).build()
            service.start()
            RecDriver = new RemoteWebDriver(service.getUrl(),DesiredCapabilities.chrome())

        }
        else if(args[0] == "Firefox"){
            RecDriver = new FirefoxDriver()
        }
        else{
            def serviceIE = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File("IEDriverServer.exe")).build()
            serviceIE.start()
            DesiredCapabilities d = DesiredCapabilities.internetExplorer()
            d.setCapability("nativeEvents", false)
            d.setCapability("forceCreateProcessApi", true)
            d.setCapability("browserCommandLineSwitches", "-private")
            RecDriver = new RemoteWebDriver(serviceIE.getUrl(),d)
        }

        //def service2 = new ChromeDriverService.Builder().usingPort(9516).usingDriverExecutable(new File("chromedriver.exe")).build()
        //service2.start()
        //WebDriver Driver = new RemoteWebDriver(service2.getUrl(),DesiredCapabilities.chrome())
        //WebDriver RecDriver = new FirefoxDriver();

        //def serviceIE = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File("IEDriverServer.exe")).build()
        //serviceIE.start()
        //DesiredCapabilities d = DesiredCapabilities.internetExplorer()
        //d.setCapability("nativeEvents", false)
        //d.setCapability("forceCreateProcessApi", true)
        //d.setCapability("browserCommandLineSwitches", "-private")
        //d.setCapability(InternetExplorerDriver.
        //        INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

        //WebDriver RecDriver = new RemoteWebDriver(serviceIE.getUrl(),d)
        //RecDriver.get("https://github.com/marijnh/codemirror")
        //Driver.get("http://127.0.0.1:3000")
        //RecDriver.get("http://angieslist.com")
        RecDriver.get(args[1])
        //Driver.get("http://reddit.com")
        //RecDriver.get("http://reddit.com")
        //RecDriver.findElement(org.openqa.selenium.By.xpath("//*[text()='This dog had to spend the night at the emergency clinic last night. His owner stayed with him all night.']")).click()
        //sleep(15000)
        RecDriver.manage().timeouts().setScriptTimeout(9999999, TimeUnit.SECONDS);
        //def wins = RecDriver.getWindowHandles()
        //RecDriver.switchTo().window("Angie's List | Find a Local Business, Ratings, Reviews, Deals |")
        JavascriptExecutor js = (JavascriptExecutor) RecDriver


        js.executeScript(initScript)
        //Binding binding = new Binding();
        //binding.setVariable("Driver",Driver)
        //binding.getProperty(By.xpath())
        //GroovyShell shell = new GroovyShell(binding);

        //js.executeAsyncScript(initScript)
        def recording = []
        while (true){


            try{

                def response = js.executeAsyncScript(collectScript)
                //def response = clickResponse.toString().replaceAll(";",";\r\n")
                //def runresponse = response[0].replaceAll("Browser.","")
                //runresponse = runresponse.replaceAll("By.","org.openqa.selenium.By.")
                response.each{
                    recording << it
                    //println groovy.json.JsonOutput.toJson(response)
                }
                //println (response)
                //if(response.endsWith("unloaded]")){
                    js.executeScript(initScript)
                //}
                /*
                try{
                    shell.evaluate(runresponse)
                }
                catch (Exception ex){
                    shell.evaluate(runresponse)
                }
                */


            }
            catch (Exception ex){
                //Unexpected modal dialog
                if(ex.message.contains("unload")){
                    js.executeScript(initScript)
                    //def clickResponse = js.executeAsyncScript(initScript)
                    //println (clickResponse)
                }
                else{
                    println recording
                    if((recording.size() == 0)&&(!ex.message.contains("disconnected"))){
                        println "ERROR:"+ex.message
                    }
                    System.exit(0)
                    //throw ex
                }
                //println ("unloaded")
            }
            //sleep(10)//*[@id="gridview-1052-record-523f296161789a5587f8a2a6"]/td[2]/div/a
        }


    }
}
