@GetMobilePokemonDataFeature
Feature: Feature - Get Pokemon Data from Mobile

  @Mobile @Positive
  Scenario Outline: Scenario - Get pokemon data from mobile pokemon app should be success
    When  at pokedex app home page search for '<pokemon>' exist is 'true'
    Then  at pokedex app pokemon page get following data
      | name      |
      | number    |
      | baseStats |
      | height    |
      | weight    |

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

  @Mobile @Negative
  Scenario Outline: Scenario - Get pokemon data from mobile pokemon app should be failed
    When  at pokedex app home page search for '<pokemon>' exist is 'false'

    Examples:
      | pokemon   |
      | garurumon |
      | pimon     |






