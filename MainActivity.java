package com.mobilprogramlar.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainactivity);
        updateUIComponents();

        setupButtons(this);
        NtHelper.setOnBackPressed(this, MainActivity.class);
        setupRecyclerView(this);

    }


    private void setupRecyclerView(Context context) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                holder.itemView.animate().alpha(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.itemView.setAlpha(1);
                    }
                }).start();
                return super.animateRemove(holder);
            }

            @Override
            public boolean animateAdd(RecyclerView.ViewHolder holder) {
                holder.itemView.setAlpha(0);
                holder.itemView.animate().alpha(1).setDuration(500).start();
                return super.animateAdd(holder);
            }
        });

        String[] topics_main = context.getResources().getStringArray(R.array.main_topics);
        MainActivity_Adapter adapter = new MainActivity_Adapter(context, topics_main);
        recyclerView.setAdapter(adapter);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.animation_fall_down1);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }

    private void updateUIComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvToolbarTitle = findViewById(R.id.toolbar_title);
        TextView tvToolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        ConstraintLayout constraintLayout3 = findViewById(R.id.constraintlayout_2);

        toolbar.setBackgroundColor(getColor(R.color.nurullah_toolbar_rengi));
        tvToolbarTitle.setTextColor(getColor(R.color.nurullah_metin_rengi_01));
        constraintLayout3.setBackgroundColor(getColor(R.color.nurullah_activityBackgroundColor));

        tvToolbarSubtitle.setText(R.string.subtitle_MainActivity);
        tvToolbarSubtitle.setTextColor(getColor(R.color.nurullah_metin_rengi_05));
    }



    private void setupButtons(Context context) {
        ImageButton btnHome = findViewById(R.id.btn_home);
        ImageButton btnShare = findViewById(R.id.btn_share);

        setButtonClickListener(btnHome, context, MainActivity.class);

        if (btnShare != null) {
            btnShare.setOnClickListener(v -> NtHelper.shareText(this));
        }
    }


    private void setButtonClickListener(ImageButton button, Context context, Class<?> targetActivity) {
        if (button != null) {
            button.setOnClickListener(v -> NtHelper.startActivity(context, targetActivity));
        }
    }


}