@GetApiPokemonDataFeature
Feature: Feature - Get Pokemon Data from Api

  @Api @Positive
  Scenario Outline: Scenario - Get pokemon data from api should be success
    When  send api request for '<pokemon>'
    Then  api response code should be 200
    Then  get following data from response
      | name           |
      | number         |
      | types          |
      | baseStats      |
      | baseExperience |

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

  @Api @Negative
  Scenario Outline: Scenario - Get pokemon data from api should be failed
    When  send api request for '<pokemon>'
    Then  api response code should be 400

    Examples:
      | pokemon   |
      | garurumon |
      | pimon     |
