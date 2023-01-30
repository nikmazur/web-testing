# Web UI Testing
This project is a group of functional web UI tests using Selenide, TestNG and Cucumber as the testing framework. Allure is used for creating reports. Tests can be run either locally or remotely.

In this project I used the Fluent Page Object design pattern, where each page has a separate class which lists all elements, methods and checks related to that page.

## Run
Java and Gradle need to be installed. Command to build and execute all tests:
```bash
gradle clean test -Dgroups=SelEasy,TheInternet downloadAllure allureServe
```
This will automatically set up the browser (Firefox by WebDriverManager), run all tests in 2 parallel processes, and present an Allure report with the results.

## Cucumber
Tests can also be executed through Cucumber:
```bash
gradle clean test -Dgroups=Cucumber
```
After execution a Cucumber report (instead of Allure) will be automatically opened in the default browser. Report will contain results, steps and attached screenshots for each test.

## Allure Report Example
![alt text](https://github.com/nikmazur/Web-Testing/raw/master/bin/allure_screen.png "Allure Report")

The report contains data on each test and parameters which were used for it. At the end of each test there are also attachments of a screenshot and HTML code of the web page.

## Changing Launch Parameters
By default the tests and launched locally in Firefox. By editing **application.properties** in root project directory you can change the following parameters:

* **remote** = true (Launch remotely in Selenoid) / false (Launch in browser locally)
* **selenoidUrl** = (If previous is set to "true", specify the Selenoid URL here)
* **headless** = true (If remote=false, launch browser locally in headless mode) / false (Launch browser regularly)

## Website Info
For testing I chose 2 different websites. Tests access local mirrors of these websites downloaded to the project directory.
<details>
<summary>TheInternet</summary>
https://the-internet.herokuapp.com/

![alt text](https://github.com/nikmazur/Web-Testing/blob/master/bin/theinternet.png "Dave Haeffner’s Practice Site")

This website consists of separate pages with various web elements designed for running simple tests (e.g. working with logins, drop-down lists, downloading files). It’s useful for demonstrating basic manipulations with content.
</details>

<details>
<summary>Selenium Easy</summary>
https://demo.seleniumeasy.com/

![alt text](https://github.com/nikmazur/Web-Testing/blob/master/bin/seleasy.png "Selenium Easy")

Selenium Easy is a website with various automation tutorials. It has a seperate section with diffrent widgets (calendar, intervactive tables, pop-up messages and JavaScript windows) for practicing selenium tests. Provides a nice addition to the previous site for testing various scenarios. 
</details>