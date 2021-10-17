@PokemonFeature
Feature: Compare Pokemon Data

  Scenario Outline: Compare pokemon data from some pokemon website
    Given open bulbapedia home page
    And   bulbapedia home page should be opened
    When  at bulbapedia home page search for '<pokemon>'
    Then  at bulbapedia pokemon page get following '<pokemon>' data
      | name      |
      | number    |
      | types     |
      | baseStats |

    Given open pokemondb home page
    And   pokemondb home page should be opened
    When  at pokemondb home page search for '<pokemon>'
    And   at pokemondb result page click pokemon pokedex
    Then  at pokemondb pokemon page get following data
      | name      |
      | number    |
      | types     |
      | baseStats |

    When  send api request for '<pokemon>'
    Then  api response code should be 200
    And   convert the response into model class

    Then compare pokemon name from three websites
    Then compare pokemon number from three websites
    Then compare pokemon type from three websites
    Then compare pokemon baseStats from three websites

    Examples:
      | pokemon   |
      | pikachu   |
#      | charizard |






