package com.example.currencyconvertapp

import ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiResponse {
    @GET("{currencyCode1}/{currencyCode2}.min.json")
    fun getResponse(
        @Path("currencyCode1") curr1: String,
        @Path("currencyCode2") curr2: String
    ): Call<ResponseData> }