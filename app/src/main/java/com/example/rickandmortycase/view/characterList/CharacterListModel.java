package com.example.rickandmortycase.view.characterList;

import com.example.rickandmortycase.model.CharacterResponse;
import com.example.rickandmortycase.retrofit.ApiClient;
import com.example.rickandmortycase.retrofit.RestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterListModel implements CharacterListContract.Model{
    @Override
    public void getCharacterList(final OnFinishedListener onFinishedListener, int pageNo) {

        RestInterface restInterface = ApiClient.getClient().create(RestInterface.class);

        Call<CharacterResponse> call = restInterface.getCharacters(pageNo);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                CharacterResponse characterResponse = response.body();
                onFinishedListener.onFinished(characterResponse);
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
