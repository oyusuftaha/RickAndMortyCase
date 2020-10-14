package com.example.rickandmortycase.view.characterDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rickandmortycase.R;
import com.example.rickandmortycase.adapter.EpisodesAdapter;
import com.example.rickandmortycase.model.Character;
import com.example.rickandmortycase.util.AppUtils;
import com.example.rickandmortycase.view.characterList.CharacterListActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterDetailActivity extends AppCompatActivity {

    @BindView(R.id.ivCharacterImage)
    ImageView ivCharacterImage;
    @BindView(R.id.ivStatusCircle)
    ImageView ivAliveCircle;
    @BindView(R.id.tvStatusAndSpecies)
    TextView tvStatusAndSpecies;
    @BindView(R.id.tvCharacterName)
    TextView tvCharacterName;
    @BindView(R.id.tvLastKnownLoc)
    TextView tvLastKnownLoc;
    @BindView(R.id.rvEpisodes)
    RecyclerView rvEpisodes;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_character_detail);
        context = getApplicationContext();

        supportPostponeEnterTransition();
        ButterKnife.bind(this);

        Character character = (Character) getIntent().getSerializableExtra(CharacterListActivity.EXTRA_CHARACTER_ITEM);

        setCharacterImage(character);
        fillContent(character);
        fillEpisodesRecyclerView(character.getEpisodeNumbers());
    }

    private void setCharacterImage(Character character) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = getIntent().getExtras().getString(CharacterListActivity.EXTRA_CHARACTER_IMAGE_TRANSITION);
            ivCharacterImage.setTransitionName(imageTransitionName);
        }

        Picasso.get().load(character.getImage()).noFade().into(ivCharacterImage, new Callback() {
            @Override
            public void onSuccess() {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onError(Exception e) {
                supportStartPostponedEnterTransition();
            }
        });
    }

    private void fillContent(Character character) {
        ivAliveCircle.setColorFilter(ContextCompat.getColor(context, AppUtils.getStatusColor(character.getStatus())),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        tvStatusAndSpecies.setText(character.getStatus() + " - " + character.getSpecies());
        tvCharacterName.setText(character.getName());
        tvLastKnownLoc.setText(character.getLocation().getName());
    }

    private void fillEpisodesRecyclerView(List<Integer> episodes) {
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvEpisodes.setLayoutManager(horizontalLayout);

        rvEpisodes.setAdapter(new EpisodesAdapter(CharacterDetailActivity.this, episodes));
    }
}