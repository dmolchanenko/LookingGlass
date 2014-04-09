Looking Glass 2.0 - Tool for Selenium Automation Development
============

A cross-browser open-source tool to record, develop, debug Selenium scripts as well as identify and validate HTML objects by XPath, CSS, etc.


**Video Overview**:
https://www.youtube.com/watch?v=_P_sD8pPYfs

**Forum**:
https://groups.google.com/forum/#!forum/primatest-automation

**Compatible**:

OS: Windows, Linux, Mac OS X

Browsers: Chrome, Internet Explorer, Safari, Firefox

*Please note that this is a Selenium based solution.*  
*This means as your browser versions change you need to get latest Selenium jars. See FAQ for more info.*

**Install**:

1. Install Java 1.7.
2. Download zip file from:  https://bitbucket.org/primatestinc/looking-glass/downloads/LookingGlass.zip (select 'Keep' when prompted by browser).
3. Unzip contents in to your folder (eg: c:\Looking Glass).
4. Double click on 'StartLookingGlass.vbs' to start.
5. If 'Looking Glass' doesn't start, it could be that Java path is not set on your computer, please follow these instructions to set it: https://forums.bukkit.org/threads/how-to-fix-java-is-not-recognized-as-an-internal-command-or-external-command.139263/
 
**Upgrade From Previous To Latest Version**:

1. Delete all contents from your existing Looking Glass folder.
2. If you get error message on Delete that driver is in use, kill it through Task Manager or just restart your computer and delete Looking Glass folder again.
3. Download latest version of Looking Glass.
4. Unzip all contents of the Looking Glass zip file in to folder.

**Inspector** 

Inspector will identify HTML elements, suggest, verify xpath, css selectors and where you can even test whether Selenium will interact with the object or not.

1. Select browser type you want to work with and click on Open button.
2. Navigate to needed URL.
3. Click on the looking glass icon and click on element you want to inspect.
4. Select "Click" operation from drop down next to "Validate" button.
5. Click on the "Click" button to see if Selenium can click on the selected HTML element.
6. Click on 'XPath' and select any other property you want to see for this object (eg: CSS Selector).
7. Modify the text value (eg: XPath property value) to another one and click Validate to see if it works or not.

**Code**

This is where you can record or specify your own code and do a playback against any browser. Support any *Java/Groovy* code only.

1. Click on Code tab.
2. From menu select Browsers -> and select the type you want.
3. Click on Record (red) button to begin recording and navigate to URL you will use.
4. Do various clicking and typing actions you want and then click on Record (red) button again to stop.
5. Click on Green arrow to playback recorded script.
6. You can modify the code (ANY Java/Groovy code with asserts, loops, etc.) and do a playback against any browser as well.
7. User can also copy/paste their own code in to Code section for quick validation/modification. Use 'Driver variable name' field to make sure it matches the one in your own code.

**F.A.Q.**

Q: How is future support of the browsers going to work? How can I add later/earlier version of Selenium?

A: You can replace any Selenium jar that Looking Glass comes with to whatever latest one or the one you want under: \<Looking Glass Installed Location>\lib

Q: Can I use my own jar files and have that code be available in code portion of the Looking Glass?

A: Yes you can.
 1. Place your jars into \<Looking Glass Installed Location>\lib directory.  
 2. Restart Looking Glass and navigate to "Code" tab.
 3. Click on "imports" icon.
 4. Add any imports you like either from your jar or from selenium.  Now your your jar code is available to be used in Code tab.

**Known Limitations** (I'm currently working on both):

1. Currently does not work on multiple tabs/windows, just the first one that is opened.
2. Currently does not track elements inside iframes.

