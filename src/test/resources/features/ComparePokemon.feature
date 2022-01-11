@PokemonFeature
Feature: Feature - Compare Pokemon Data

  @Website
  Scenario Outline: Scenario - Get pokemon data from some pokemon website
    Given open bulbapedia home page
    And   prepare start recording for pokemon '<pokemon>'
    When  at bulbapedia home page search for '<pokemon>'
    Then  at bulbapedia pokemon page get following '<pokemon>' data
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
#      | charizard |
#      | eevee     |
#      | mewtwo    |
#      | garurumon |
#      | koffing   |
#      | meowth    |
#      | blastoise |
#      | ivysaur   |
#      | squirtle  |
#      | mew       |
#      | pidgey    |
#      | rattata   |
#      | solrock   |

  @Api
  Scenario Outline: Scenario - Get pokemon data from api
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
      | garurumon |
      | koffing   |
      | meowth    |
      | blastoise |
      | ivysaur   |
      | squirtle  |
      | mew       |
      | pidgey    |
      | rattata   |
      | solrock   |

  @Mobile
  Scenario Outline: Scenario - Get pokemon data from mobile pokemon app
    When  at pokedex app home page search for '<pokemon>'
    Then  at pokedex app pokemon page get following data
      | name      |
      | number    |
      | baseStats |

    Examples:
      | pokemon   |
      | piKacHU   |
      | charizard |
      | eevee     |
      | mewtwo    |
      | garurumon |
      | koffing   |
      | meowth    |
      | blastoise |
      | ivysaur   |
      | squirtle  |
      | mew       |
      | pidgey    |
      | rattata   |
      | solrock   |

  @Compare
  Scenario: Scenario - Compare pokemon data
    Given prepare pokemon parameter for following pokemon
      | pikachu   |
      | charizard |
      | eevee     |
      | mewtwo    |
      | garurumon |
      | koffing   |
      | meowth    |
      | blastoise |
      | ivysaur   |
      | squirtle  |
      | mew       |
      | pidgey    |
      | rattata   |
      | solrock   |
    Then  compare all pokemon data






