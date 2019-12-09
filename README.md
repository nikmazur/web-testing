# Web-Testing
This project (built with Gradle) is a group of web tests using Selenide, Webdriver and TestNG as the testing framework. Allure is used for creating run reports.

In this project I used the Page Object design pattern, where each page has a seperate class which lists all elements, methods and checks related to that page.

Tests can be ran either locally or remotely (in a Selenoid Docker container).

## Run
Java and Gradle need to be installed. Command to build and execute all tests:
```bash
gradle clean test downloadAllure allureServe
```
This will automatically set up the browser (Chrome by WebDriverManager), run all tests in 2 parallel processes, and will present an Allure report with the results.

## [Download Sample Report](https://github.com/nikmazur/Web-Testing/raw/master/bin/allure-report.zip)
![alt text](https://github.com/nikmazur/Web-Testing/raw/master/bin/allure_screen.png "Allure Report")

The report contains data on each test, parameters which were used for it, and execution history. At the end of each test there are also attachments of a screenshot and HTML code of the web page.

## Changing Launch Parameters
By default the tests and launched locally in Chrome. By editing **application.properties** in root project directory you can change the following parameters:

* **remote** = true (Launch remotely in Selenoid) / false (Launch in browser locally)
* **selenoidUrl** = (If previous is set to "true", specify the Selenoid URL here)
* **headless** = true (If remote=false, launch browser locally in headless mode) / false (Launch browser regularly)

## Website Info
For testing I chose 2 different websites:
<details>
<summary>TheInternet</summary>
https://the-internet.herokuapp.com/

![alt text](https://github.com/nikmazur/Web-Testing/blob/master/bin/theinternet.png "Dave Haeffner’s Practice Site")

This website consists of separate pages with various web elements designed for running simple tests (e.g. working with logins, drop-down lists, downloading files). It’s useful for demonstrating basic manipulations with content.
</details>

<details>
<summary>Selenium Easy</summary>
https://www.seleniumeasy.com/test/

![alt text](https://github.com/nikmazur/Web-Testing/blob/master/bin/seleasy.png "Selenium Easy")

Selenium Easy is a website with various automation tutorials. It has a seperate section with diffrent widgets (calendar, intervactive tables, pop-up messages and JavaScript windows) for practicing selenium tests. Provides a nice addition to the previous site for testing various scenarios. 
</details>