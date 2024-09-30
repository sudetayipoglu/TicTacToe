package com.mobilprogramlar.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    private final Context context;
    private final String[] categories;

    public MainActivity_Adapter(Context context, String[] categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mainactivity_adapter_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.title.setText(categories[position]);

        itemViewHolder.title.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.nurullah_cardTextColor));
        itemViewHolder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.nurullah_cardBackgroundColor));
        itemViewHolder.layoutContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.nurullah_cardBackgroundColor));


        switch (position) {
            case 0:
                itemViewHolder.image.setImageResource(R.drawable.logom);
                break;
            case 1:
                itemViewHolder.image.setImageResource(R.drawable.uygulamalarimiz);
                break;
            default:
                itemViewHolder.image.setImageResource(R.drawable.ic_launcher_background);
                break;
        }



        itemViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = null;

            switch (position) {
                case 0:
                    intent = new Intent(context, TicTacToeActivity.class);
                    break;
                case 1:
                    NtHelper.openDeveloperPage(context, "mobilprogramlar.com");
                    break;
                default:
                    intent = new Intent(context, TicTacToeActivity.class);
                    break;
            }

            if (intent != null) {
                intent.putExtra("category", categories[position]);
                if (!(context instanceof Activity)) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
            }

            v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction(() ->
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
            ).start();
        });
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout layoutContainer;
        ImageView image;
        TextView title;

        ItemViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            layoutContainer = itemView.findViewById(R.id.layout_container_1);
            image = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_title);
        }
    }
}
