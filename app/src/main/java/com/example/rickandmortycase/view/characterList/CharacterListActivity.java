package com.example.rickandmortycase.view.characterList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rickandmortycase.R;
import com.example.rickandmortycase.model.Character;

import java.util.List;

public class CharacterListActivity extends AppCompatActivity implements CharacterListContract.View {

    private CharacterListPresenter presenter;
    private int pageNumber = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_character_list);

        presenter = new CharacterListPresenter(this);
        presenter.getDataByPage(pageNumber);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void fillContent(List<Character> characterList) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}