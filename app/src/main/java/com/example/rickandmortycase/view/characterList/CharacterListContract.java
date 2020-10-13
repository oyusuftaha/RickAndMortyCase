package com.example.rickandmortycase.view.characterList;


import com.example.rickandmortycase.model.Character;
import com.example.rickandmortycase.model.CharacterResponse;

import java.util.List;

public interface CharacterListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(CharacterResponse characterResponse);
            void onFailure(Throwable t);
        }

        void getCharacterList(OnFinishedListener onFinishedListener, int pageNumber);
    }

    interface View {

        void showProgress();
        void hideProgress();

        void fillCharacterData(List<Character> data);
        void onResponseFailure(Throwable throwable);

        void setupUI();
    }

    interface Presenter {

        void getDataByPage(int pageNumber);
        void onDestroy();
    }
}