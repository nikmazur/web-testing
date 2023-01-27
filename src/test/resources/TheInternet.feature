Feature: The Internet Tests

  Background: User opens The Internet home page

  Scenario: Basic Login
    Given I am on the Basic Auth page
    When I enter login tomsmith
    And I enter password SuperSecretPassword!
    And I press Submit
    Then I am logged in

  Scenario: Basic Logout
    Given I am on the Basic Auth page
    When I enter login tomsmith
    And I enter password SuperSecretPassword!
    And I press Submit
    And I press Logout
    Then I am logged out

  Scenario: Click Checkboxes
    Given I am on the Checkboxes page
    When I click on Checkbox 1
    And I click on Checkbox 2
    Then Checkbox 1 is checked
    And Checkbox 2 is unchecked

  Scenario: Select Dropdown Option 1
    Given I am on the Dropdown page
    When I select Option 1
    Then Dropdown text is "Option 1"

  Scenario: Select Dropdown Option 2
    Given I am on the Dropdown page
    When I select Option 2
    Then Dropdown text is "Option 2"

  Scenario: Press PAUSE key
    Given I am on the Key Presses page
    When I press PAUSE on the keyboard
    Then Page displays "You entered: PAUSE"

  Scenario: Press NUMPAD6 key
    Given I am on the Key Presses page
    When I press NUMPAD6 on the keyboard
    Then Page displays "You entered: NUMPAD6"

  Scenario: Opening & Switching Tabs
    Given I am on the Multiple Windows page
    When I click link to open New Window
    And I switch to tab "New Window"
    Then Page title should be "New Window"
    When I switch to tab "The Internet"
    Then Page title should be "The Internet"

  Scenario: Moving Slider
    Given I am on the Horizontal Slider page
    When I move the slider 5 times to the right
    Then Slider value is 2.5
    When I move the slider 2 times to the left
    Then Slider value is 1.5

  Scenario: Large table with data
    Given I am on the Large Table page
    Then Row 14 column 37 should have text 14.37
    Then Row 28 column 3 should have text 28.3
    Then Row 7 column 44 should have text 7.44

  Scenario: Failing test
    Given I am on the Dropdown page
    When I select Option 2
    Then Dropdown text is "Option 47"