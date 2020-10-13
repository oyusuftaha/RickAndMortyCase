package com.example.rickandmortycase.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickandmortycase.R;
import com.example.rickandmortycase.model.Character;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    List<Character> itemList;
    Context context;

    public CharacterListAdapter(Context context, List<Character> itemList) {
        this.itemList = itemList;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_character,
                        parent,
                        false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Glide.with(context).load(itemList.get(position).getImage()).placeholder(R.drawable.ic_character_placeholder).into(holder.ivCharacterImage);

        if (itemList.get(position).getStatus().equals("Alive")) {
            holder.ivAliveCircle.setColorFilter(ContextCompat.getColor(context, R.color.green), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else if (itemList.get(position).getStatus().equals("Dead")) {
            holder.ivAliveCircle.setColorFilter(ContextCompat.getColor(context, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            holder.ivAliveCircle.setColorFilter(ContextCompat.getColor(context, R.color.gray500), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        holder.tvAlive.setText(itemList.get(position).getStatus());
        holder.tvCharacterName.setText(itemList.get(position).getName());
        holder.tvSpecies.setText(itemList.get(position).getSpecies());

    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivAliveCircle)
        ImageView ivAliveCircle;

        @BindView(R.id.ivCharacterImage)
        ImageView ivCharacterImage;

        @BindView(R.id.tvAlive)
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
