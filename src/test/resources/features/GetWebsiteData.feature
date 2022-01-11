@GetWebsitePokemonDataFeature
Feature: Feature - Get Pokemon Data from Website

  @Website @Positive
  Scenario Outline: Scenario - Get pokemon data from some pokemon website should be success
    Given open bulbapedia home page
    And   prepare start recording for pokemon '<pokemon>'
    When  at bulbapedia home page search for '<pokemon>'
    Then  at bulbapedia pokemon page get following data
      | name           |
      | number         |
      | types          |
      | baseStats      |
      | baseExperience |
      | species        |
      | growthRate     |

    Given open pokemondb home page
    When  at pokemondb home page search for '<pokemon>'
    Then  at pokemondb pokemon page get following data
      | name           |
      | number         |
      | types          |
      | baseStats      |
      | baseExperience |
      | species        |
      | growthRate     |
    Then stop and save recording for pokemon '<pokemon>'

    Examples:
      | pokemon   |
      | piKacHU   |
      | charizard |
      | eevee     |
      | mewtwo    |
      | koffing   |
      | meowth    |
      | blastoise |
      | ivysaur   |
      | squirtle  |
      | mew       |
      | pidgey    |
      | rattata   |
      | solrock   |

  @Website @Negative
  Scenario Outline: Scenario - Get pokemon data from some pokemon website should be failed
    Given open bulbapedia home page
    And   prepare start recording for pokemon '<pokemon>'
    When  at bulbapedia home page search for '<pokemon>'
    Then  at bulbapedia home page pokemon should not be found

    Given open pokemondb home page
    When  at pokemondb home page search for '<pokemon>'
    Then  at pokemondb home page pokemon should not be found
    Then stop and save recording for pokemon '<pokemon>'

    Examples:
      | pokemon   |
      | garurumon |
      | pimon     |
