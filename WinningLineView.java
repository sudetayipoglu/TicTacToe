package com.mobilprogramlar.tictactoe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Handler;

import androidx.annotation.NonNull;


public class WinningLineView extends View {
    private Paint paint;
    private float startX, startY, stopX, stopY;
    private boolean shouldDrawLine = false;
    private float currentStopX, currentStopY;

    public WinningLineView(Context context) {
        super(context);
        init();
    }

    public WinningLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED); // Çizgi rengini ayarla
        paint.setStrokeWidth(70f); // Çizgi kalınlığını ayarla
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setBackgroundColor(Color.TRANSPARENT);
        setWillNotDraw(false);
    }

    public void setLine(float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        shouldDrawLine = true;
        animateLine();      // Çizginin animasyonla çizilmesi
        animateLineColor(); // Çizginin rengi değiştirilmesi
        //blinkLine(3, 500); // 3 defa çizgiyi görünür/görünmez yap
        animateLineThickness(); // 5 ile 20 arasında kalınlık değiştir
    }

    public void resetLine() {
        shouldDrawLine = false;
        invalidate(); // Yeniden çizerek çizgiyi sıfırla
    }



    // Çizgi Animasyonu (Yavaşça Çizilme)
    private void animateLine() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);  // Animasyon 0'dan 1'e kadar ilerleyecek
        animator.setDuration(4000); // 1 saniyelik animasyon süresi
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue(); // Animasyon ilerlemesi
                currentStopX = startX + progress * (stopX - startX);
                currentStopY = startY + progress * (stopY - startY);
                invalidate(); // Her animasyon adımında yeniden çiz
            }
        });
        animator.start(); // Animasyonu başlat
    }



    //Çizginin Renk Değiştirmesi (Gradient Efekti) (Örneğin, kırmızıdan yeşile dönüşen bir çizgi animasyonu)
    public void animateLineColor() {
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.RED, Color.GREEN);
        colorAnimator.setDuration(4000); // 1 saniyede renk değişimi
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                paint.setColor((int) animator.getAnimatedValue()); // Çizgi rengini ayarla
                invalidate(); // Yeniden çiz
            }
        });
        colorAnimator.start(); // Renk animasyonunu başlat
    }



    //Çizginin Yanıp Sönmesi (Blinking Effect)
    public void blinkLine(final int times, final int interval) {
        final Handler handler = new Handler();
        for (int i = 0; i < times; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shouldDrawLine = !shouldDrawLine; // Çizgiyi görünür/görünmez yap
                    invalidate(); // Yeniden çiz
                }
            }, (long) i * interval); // Her adımda çizgiyi tersine çevir
        }
    }



    //Çizgi Kalınlığını Dinamik Olarak Değiştirme
    public void animateLineThickness() {
        ValueAnimator thicknessAnimator = ValueAnimator.ofFloat(20f, 70f); // Çizgi kalınlığını değiştir
        thicknessAnimator.setDuration(6000); // 4 saniyelik animasyon
        thicknessAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                paint.setStrokeWidth((float) animation.getAnimatedValue()); // Çizgi kalınlığını ayarla
                invalidate(); // Yeniden çiz
            }
        });
        thicknessAnimator.start(); // Kalınlık animasyonunu başlat
    }









    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (shouldDrawLine) {
            canvas.drawLine(startX, startY, currentStopX, currentStopY, paint); // Animasyonlu çizgi
        }
    }
}

