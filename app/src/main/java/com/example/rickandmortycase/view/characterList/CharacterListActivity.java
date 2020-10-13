package com.example.rickandmortycase.view.characterList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rickandmortycase.R;
import com.example.rickandmortycase.adapter.CharacterListAdapter;
import com.example.rickandmortycase.helper.EndlessScrollListener;
import com.example.rickandmortycase.model.Character;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListActivity extends AppCompatActivity implements CharacterListContract.View {

    private final static String TAG = "CharacterListAct";

    private CharacterListPresenter presenter;

    private List<Character> characterList;
    private CharacterListAdapter characterListAdapter;

    @BindView(R.id.rvCharacters)
    RecyclerView rvCharacters;

    @BindView(R.id.cardProgressContainer)
    CardView cardProgressContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_character_list);
        ButterKnife.bind(this);

        presenter = new CharacterListPresenter(this);
        presenter.getDataByPage(1);

    }


    @Override
    public void showProgress() {
        cardProgressContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        cardProgressContainer.setVisibility(View.GONE);
    }

    @Override
    public void fillCharacterData(List<Character> data) {

        characterList.addAll(data);
        characterListAdapter.notifyDataSetChanged();
        rvCharacters.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Bir hata oluştu", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onResponseFailure: ", throwable);
    }

    @Override
    public void setupUI() {

        characterList = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        characterListAdapter = new CharacterListAdapter(CharacterListActivity.this, characterList);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.vertical_recyclerview_anim);

        rvCharacters.setLayoutAnimation(animation);
        rvCharacters.setLayoutManager(layoutManager);
        rvCharacters.setAdapter(characterListAdapter);

        EndlessScrollListener scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.getDataByPage(page);
            }
        };

        rvCharacters.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}