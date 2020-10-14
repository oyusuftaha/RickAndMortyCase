package com.example.rickandmortycase.view.characterList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rickandmortycase.R;
import com.example.rickandmortycase.adapter.CharacterListAdapter;
import com.example.rickandmortycase.helper.EndlessScrollListener;
import com.example.rickandmortycase.model.Character;
import com.example.rickandmortycase.view.characterDetail.CharacterDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListActivity extends AppCompatActivity implements CharacterListContract.View, CharacterListAdapter.CharacterListItemClickListener {

    private final static String TAG = "CharacterListAct";
    public final static String EXTRA_CHARACTER_ITEM = "character_item";
    public final static String EXTRA_CHARACTER_IMAGE_TRANSITION = "character_image_transition";

    private CharacterListPresenter presenter;

    private List<Character> characterList = new ArrayList<>();

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
    public void onCharacterItemClick(int adapterPosition, Character character, ImageView ivCharacterImage) {
        Intent intent = new Intent(this, CharacterDetailActivity.class);
        intent.putExtra(EXTRA_CHARACTER_ITEM, character);
        intent.putExtra(EXTRA_CHARACTER_IMAGE_TRANSITION, ViewCompat.getTransitionName(ivCharacterImage));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                ivCharacterImage,
                ViewCompat.getTransitionName(ivCharacterImage));

        startActivity(intent, options.toBundle());
    }

    @Override
    public void setupUI() {

        getSupportActionBar().setTitle(getResources().getString(R.string.app_bar_main_title));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        characterListAdapter = new CharacterListAdapter(CharacterListActivity.this, characterList, this);
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