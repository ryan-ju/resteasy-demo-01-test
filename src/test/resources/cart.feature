Feature: cart

  Editing shopping cart

  Scenario Outline: add item to cart then delete
    When a user adds the item with id "<id>"
    And she gets cart
    Then she should get back one item
    And the item have id = "<id>", name = "<name>" and description = "<description>"

    @CartModified
    Examples:
      | id    | name                              | description           |
      | 00001 | Scala for the Impatient           | Awesome Scala book    |
      | 00002 | Java Concurrent in Practice       | Great threading book  |
      | 00003 | C Programming: A Modern Approach  | The C book            |


  Scenario Outline: delete item from cart
    When a user adds the item with id "<id>"
    And she deletes the item
    And she gets cart
    Then she should get back empty cart

    @CartModified
    Examples:
      | id    |
      | 00001 |
      | 00002 |
      | 00003 |