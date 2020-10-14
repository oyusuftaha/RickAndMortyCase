package com.example.rickandmortycase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmortycase.R;
import com.example.rickandmortycase.model.Character;
import com.example.rickandmortycase.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private final static String IMAGE_TRANSITION_KEY = "character_image_transition";
    private List<Character> itemList;
    private Context context;
    private CharacterListItemClickListener characterListItemClickListener;

    public interface CharacterListItemClickListener {
        void onCharacterItemClick(int adapterPosition, Character character, ImageView ivCharacterImage);
    }

    public CharacterListAdapter(Context context, List<Character> itemList, CharacterListItemClickListener characterListItemClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.characterListItemClickListener = characterListItemClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Character character = itemList.get(position);
        Picasso.get().load(character.getImage()).placeholder(R.drawable.ic_character_placeholder).into(holder.ivCharacterImage);

        holder.ivStatusCircle.setColorFilter(ContextCompat.getColor(context,
                AppUtils.getStatusColor(character.getStatus())), android.graphics.PorterDuff.Mode.MULTIPLY);


        holder.tvAlive.setText(character.getStatus());
        holder.tvCharacterName.setText(character.getName());
        holder.tvSpecies.setText(character.getSpecies());

        ViewCompat.setTransitionName(holder.ivCharacterImage, IMAGE_TRANSITION_KEY);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                characterListItemClickListener.onCharacterItemClick(holder.getAdapterPosition(), character, holder.ivCharacterImage);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivStatusCircle)
        ImageView ivStatusCircle;

        @BindView(R.id.ivCharacterImage)
        ImageView ivCharacterImage;

        @BindView(R.id.tvStatusAndSpecies)
        TextView tvAlive;

        @BindView(R.id.tvSpecies)
        TextView tvSpecies;

        @BindView(R.id.tvCharacterName)
        TextView tvCharacterName;


        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

    }
}
