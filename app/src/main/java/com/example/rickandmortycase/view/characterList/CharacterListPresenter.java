package com.example.rickandmortycase.view.characterList;

import com.example.rickandmortycase.model.CharacterResponse;

public class CharacterListPresenter implements CharacterListContract.Presenter, CharacterListContract.Model.OnFinishedListener {

    private CharacterListContract.View characterListView;
    private CharacterListContract.Model characterListModel;

    public CharacterListPresenter(CharacterListContract.View characterListView) {
        this.characterListView = characterListView;
        characterListModel = new CharacterListModel();

        this.characterListView.setupUI();
    }

    @Override
    public void getDataByPage(int pageNumber) {

        if (characterListView != null) {
            characterListView.showProgress();
        }
        characterListModel.getCharacterList(this, pageNumber);
    }

    @Override
    public void onFinished(CharacterResponse characterResponse) {

        if (characterResponse != null) {
            characterListView.fillCharacterData(characterResponse.getCharacters());
        }
        if (characterListView != null) {
            characterListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {

        characterListView.onResponseFailure(t);
        if (characterListView != null) {
            characterListView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.characterListView = null;
    }

}
