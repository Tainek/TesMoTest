package com.ahorr.tesmotest

import com.ahorr.tesmotest.data.model.PokeDetail
import com.ahorr.tesmotest.data.model.PokemonListResponse
import com.ahorr.tesmotest.data.remote.PokemonApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: PokemonApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getPokemonList returns parsed data`() = runBlocking {
        val mockJson = """
            {
              "results": [
                {"name": "bulbasaur", "url": "https://pokeapi.co/api/v2/pokemon/1/"},
                {"name": "ivysaur", "url": "https://pokeapi.co/api/v2/pokemon/2/"}
              ]
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val response: PokemonListResponse = api.getPokemonList()
        assertEquals(2, response.results.size)
        assertEquals("bulbasaur", response.results[0].name)
        assertEquals("ivysaur", response.results[1].name)
    }

    @Test
    fun `getPokemonDetail returns parsed data`() = runBlocking {
        val mockJson = """
            {
              "abilities": [],
              "baseExperience": 64,
              "cries": {"latest": "cry1", "legacy": "cry2"},
              "forms": [],
              "gameIndices": [],
              "height": 7,
              "heldItems": [],
              "id": 1,
              "isDefault": true,
              "locationAreaEncounters": "",
              "moves": [],
              "name": "bulbasaur",
              "order": 1,
              "pastAbilities": [],
              "pastTypes": [],
              "species": {"name": "bulbasaur", "url": "https://pokeapi.co/api/v2/pokemon-species/1/"},
              "sprites": {
                "back_default": "",
                "back_female": "",
                "back_shiny": "",
                "back_shiny_female": "",
                "front_default": "",
                "front_female": "",
                "front_shiny": "",
                "front_shiny_female": "",
                "other": {"dreamWorld": {"frontDefault": "", "frontFemale": null}, "home": {"frontDefault": "", "frontFemale": null, "frontShiny": "", "frontShinyFemale": null}, "officialArtwork": {"frontDefault": "", "frontShiny": ""}, "showdown": {"back_default": "", "back_female": "", "back_shiny": "", "back_shiny_female": "", "front_default": "", "front_female": "", "front_shiny": "", "front_shiny_female": "", "other": null, "versions": null}},
                "versions": {"generationI": {"redBlue": {"backDefault": "", "backGray": "", "backTransparent": "", "frontDefault": "", "frontGray": "", "frontTransparent": ""}, "yellow": {"backDefault": "", "backGray": "", "backTransparent": "", "frontDefault": "", "frontGray": "", "frontTransparent": ""}}, "generationIi": {"crystal": {"backDefault": "", "backShiny": "", "backShinyTransparent": "", "backTransparent": "", "frontDefault": "", "frontShiny": "", "frontShinyTransparent": "", "frontTransparent": ""}, "gold": {"backDefault": "", "backShiny": "", "frontDefault": "", "frontShiny": "", "frontTransparent": null}, "silver": {"backDefault": "", "backShiny": "", "frontDefault": "", "frontShiny": "", "frontTransparent": null}}, "generationIii": {"emerald": {"frontDefault": "", "frontShiny": ""}, "fireredLeafgreen": {"backDefault": "", "backShiny": "", "frontDefault": "", "frontShiny": "", "frontTransparent": null}, "rubySapphire": {"backDefault": "", "backShiny": "", "frontDefault": "", "frontShiny": "", "frontTransparent": null}}, "generationIv": {"diamondPearl": {"back_default": "", "back_female": "", "back_shiny": "", "back_shiny_female": "", "front_default": "", "front_female": "", "front_shiny": "", "front_shiny_female": "", "other": null, "versions": null}, "heartgoldSoulsilver": {"back_default": "", "back_female": "", "back_shiny": "", "back_shiny_female": "", "front_default": "", "front_female": "", "front_shiny": "", "front_shiny_female": "", "other": null, "versions": null}, "platinum": {"back_default": "", "back_female": "", "back_shiny": "", "back_shiny_female": "", "front_default": "", "front_female": "", "front_shiny": "", "front_shiny_female": "", "other": null, "versions": null}}, "generationV": {"blackWhite": {"back_default": "", "back_female": "", "back_shiny": "", "back_shiny_female": "", "front_default": "", "front_female": "", "front_shiny": "", "front_shiny_female": "", "other": null, "versions": null}}, "generationVi": {}, "generationVii": {"icons": {"frontDefault": "", "frontFemale": null}, "ultraSunUltraMoon": {"frontDefault": "", "frontFemale": null, "frontShiny": "", "frontShinyFemale": null}}, "generationViii": {"icons": {"frontDefault": "", "frontFemale": null}}}
              },
              "stats": [],
              "types": [],
              "weight": 69
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val response: PokeDetail = api.getPokemonDetail("bulbasaur")
        assertEquals("bulbasaur", response.name)
        assertEquals(1, response.id)
        assertEquals(64, response.baseExperience)
    }
} 