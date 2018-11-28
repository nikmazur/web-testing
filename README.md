# Web-Testing
This project (built with Maven) is a group of web tests using Firefox Selenium Webdriver (geckodriver.exe, included with the project) and TestNG as the testing framework. It consists of 2 separate classes with tests, and another class with commonly used methods. Screenshots of the browser window are taken and stored in the project directory automatically at the end of each test.  

For testing I chose 2 different websites:

https://the-internet.herokuapp.com/

This website consists of separate pages with various web elements designed for running simple tests (e.g. working with logins, drop-down lists, downloading files). It’s useful for demonstrating basic manipulations with content.

https://www.phptravels.net/admin

(Login & pass: admin@phptravels.com, demoadmin)
A website emulating an administrator dashboard which allows to manipulate settings and content of a tourism services portal - phptravels.net. This a demo website, and all content & settings are automatically restored every 10 minutes, which allows us to freely edit any data. The tests are mostly aimed at checking basic functions (e.g. logging in, downloading files, changing settings) and manipulating content (adding, editing, deleting).
