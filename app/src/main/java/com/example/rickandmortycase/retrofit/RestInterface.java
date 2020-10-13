package com.example.rickandmortycase.retrofit;

import com.example.rickandmortycase.model.CharacterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("character")
    Call<CharacterResponse> getCharacters(@Query("page") int pageNumber);

}
