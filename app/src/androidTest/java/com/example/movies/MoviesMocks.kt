package com.example.movies

import com.example.movies.mock.MockInterceptor

val MoviesMocks = listOf(
    MockInterceptor.Mock(
        predicate = { request ->
            request.url.queryParameter("s") != null && request.url.queryParameter("page") == "1"
        },
        response = MockInterceptor.Mock.Response(
            """
                {
                  "Search": [
                    {
                      "Title": "The Fast and the Furious",
                      "Year": "1954",
                      "imdbID": "tt0046969",
                      "Type": "movie",
                      "Poster": ""
                    }
                  ],
                  "totalResults": "1",
                  "Response": "True"
                }
            """.trimIndent()
        )
    ),
    MockInterceptor.Mock(
        predicate = { request ->
            request.url.queryParameter("s") != null && request.url.queryParameter("page") == "2"
        },
        response = MockInterceptor.Mock.Response(
            """
                {
                  "totalResults": "1",
                  "Response": "False"
                }
            """.trimIndent()
        )
    ),
    MockInterceptor.Mock(
        predicate = { request -> request.url.queryParameter("i") != null },
        response = MockInterceptor.Mock.Response(
            """
                {
                  "Title": "The Fast and the Furious",
                  "Year": "2001",
                  "Rated": "PG-13",
                  "Released": "22 Jun 2001",
                  "Runtime": "106 min",
                  "Genre": "Action, Crime, Thriller",
                  "Director": "Rob Cohen",
                  "Writer": "Ken Li, Gary Scott Thompson, Erik Bergquist",
                  "Actors": "Vin Diesel, Paul Walker, Michelle Rodriguez",
                  "Plot": "Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to end it.",
                  "Language": "English, Spanish",
                  "Country": "United States, Germany",
                  "Awards": "11 wins & 18 nominations total",
                  "Poster": "",
                  "Ratings": [
                    {
                      "Source": "Internet Movie Database",
                      "Value": "6.8/10"
                    },
                    {
                      "Source": "Rotten Tomatoes",
                      "Value": "55%"
                    },
                    {
                      "Source": "Metacritic",
                      "Value": "58/100"
                    }
                  ],
                  "Metascore": "58",
                  "imdbRating": "6.8",
                  "imdbVotes": "436,588",
                  "imdbID": "tt0232500",
                  "Type": "movie",
                  "DVD": "N/A",
                  "BoxOffice": "$144,745,925",
                  "Production": "N/A",
                  "Website": "N/A",
                  "Response": "True"
                }
            """.trimIndent()
        )
    )
)