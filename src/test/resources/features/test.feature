Feature:  Testing out the Sports Tak application

  @Test @ChangeLanguage
  Scenario: Change language and verify the action
  Given the user is on SportsTak
  When the user clicks on language selection button
  Then the language gets changed

  @Test @BrokenLinkCheck
  Scenario: Check that thumbnails (editors choice) does not have a broken link
    Given the user is on SportsTak
    When the user clicks on each thumbnail mimicked by sending HTTP request
    Then ensure that the urls are not broken

  @Test @ScrollAndCheckLinks
  Scenario: Click on Cricket and scroll to third page and check that the links are working
    Given the user is on SportsTak
    When the user clicks on the Cricket button
    And scrolls down to third page
    Then click and verify that links are not broken

  @Test @ChangeTheme
  Scenario: Change theme to light to dark and vice versa
    Given the user is on SportsTak
    When the user clicks on the theme button
    Then the website turns on that theme

  @Test @ScrapStoryText
  Scenario: Click on any web story and scrap through the story text
    Given the user is on SportsTak
    When the user clicks on a web story
    Then the web story opens in a new tab and story texts are captured in console