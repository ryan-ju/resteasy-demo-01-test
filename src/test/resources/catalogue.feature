Feature: catalogue

  Browsing catalogue

  Scenario: get catalogue
    When a user gets catalogue
    Then she should get some items
    And the items should be:
      | id    | name                              | description           |
      | 00001 | Scala for the Impatient           | Awesome Scala book    |
      | 00002 | Java Concurrent in Practice       | Great threading book  |
      | 00003 | C Programming: A Modern Approach  | The C book            |
