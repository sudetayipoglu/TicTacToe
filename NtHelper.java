package com.mobilprogramlar.tictactoe;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NtHelper {




    // Geri tuşuna basıldığında belirli bir aktiviteye dönmek için kullanılır.
    // OnBackPressedCallback kullanarak geri tuşu davranışını özelleştirir.
    // KULLANIMI: NtHelper.setOnBackPressed(this, MainActivity.class);
    public static void setOnBackPressed(AppCompatActivity activity, Class<?> targetActivity) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(activity.getApplicationContext(), targetActivity);
                // Intent'e FLAG_ACTIVITY_NEW_TASK ekleniyor.
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                NavUtils.navigateUpTo(activity, intent);
            }
        };
        activity.getOnBackPressedDispatcher().addCallback(activity, callback);
    }


    // Aktivite başlatma metodunu genel ve modüler hale getirmek için kullanılır.
    // Context'in bir Activity olup olmadığını kontrol eder ve eğer değilse FLAG_ACTIVITY_NEW_TASK bayrağını ekler.
    // KULLANIMI: NtHelper.startActivity(this, Ayarlar.class)
    public static void startActivity(Context context, Class<?> targetActivityClass) {
        Intent intent = new Intent(context, targetActivityClass);
        // Context'in bir Activity olup olmadığını kontrol ediyoruz.
        // Eğer Activity değilse FLAG_ACTIVITY_NEW_TASK bayrağını ekliyoruz.
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    // Metni paylaşmak için kullanılır.
    // Intent oluşturur ve paylaşım ekranını başlatır.
    // KULLANIMI: NtHelper.shareText(this)
    public static void shareText(Context context) {
        String title = context.getString(R.string.paylas_baslik);
        String message = context.getString(R.string.paylas_mesaj) + context.getString(R.string.paylas_link);
        String buttonText = context.getString(R.string.paylas_button_mesaj);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
        // Sharing Intent için FLAG_ACTIVITY_NEW_TASK kontrolü ekliyoruz.
        if (!(context instanceof Activity)) {
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(Intent.createChooser(sharingIntent, buttonText));
    }



    // Google Play geliştirici sayfasını açan metot
    // KULLANIMI: NtHelper.openDeveloperPage(context, "MobilUygulamalar.com");
    public static void openDeveloperPage(Context context, String developerName) {
        try {
            // Play Store uygulamasında açmayı dener
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + developerName));
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Eğer Play Store uygulaması yoksa, tarayıcıda açar
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + developerName));
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }




    //Kulanımı: private FloatingActionButton fab;
    // fab = findViewById(R.id.fab);
    // NtHelper.setupFab(this, fab, R.drawable.ic_star, R.color.fab_original_color, R.color.fab_clicked_color, R.animator.fab_animation, "Ekle");

    /**
     * FloatingActionButton için genel bir yapılandırma metodu.
     * Bu metod, FAB'e tıklama animasyonu, renk değişimi ve tıklama sonrası mesaj gösterme işlevlerini ekler.
     *
     * @param context          Uygulama bağlamı, genellikle `this` olarak geçilir.
     * @param fab              Özelleştirilecek FloatingActionButton nesnesi.
     * @param iconResId        FAB üzerinde görüntülenecek ikonun kaynak ID'si.
     * @param originalColorResId Orijinal arka plan rengi için kaynak ID'si.
     * @param clickedColorResId  Tıklama sonrası arka plan rengi için kaynak ID'si.
     * @param animationResId   FAB'e tıklandığında oynatılacak animasyonun kaynak ID'si.
     * @param fontSize         Metin boyutu.
     * @param toastSatir      Tıklama sonrası gösterilecek Toast mesajı Satır Sayısı.
     * @param toastBaslik      Tıklama sonrası gösterilecek Toast mesajı Başlığı.
     * @param toastMessage     Tıklama sonrası gösterilecek Toast mesajı.
     */
    public static void setupFabMsg(
            Context context,
            FloatingActionButton fab,
            int iconResId,
            int originalColorResId,
            int clickedColorResId,
            int animationResId,
            int fontSize,
            int toastSatir,
            String toastBaslik,
            String toastMessage) {

        // FAB ikonunu ayarla
        fab.setImageResource(iconResId);

        // Orijinal ve tıklama sonrası renkleri al
        int originalColor = ContextCompat.getColor(context, originalColorResId);
        int clickedColor = ContextCompat.getColor(context, clickedColorResId);

        // FAB'in arka plan rengini orijinal renk olarak ayarla
        fab.setBackgroundTintList(ColorStateList.valueOf(originalColor));

        // Tıklama olayını ayarla
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rotasyon animasyonunu yükle
                AnimatorSet rotateAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, animationResId);
                rotateAnimatorSet.setTarget(fab);

                // Renk animasyonunu oluştur
                ValueAnimator colorAnimator = ValueAnimator.ofArgb(originalColor, clickedColor, originalColor);
                colorAnimator.setDuration(500); // Animasyon süresi
                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                        fab.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
                    }
                });

                // Animasyonları birlikte oynat
                AnimatorSet combinedAnimatorSet = new AnimatorSet();
                combinedAnimatorSet.playTogether(rotateAnimatorSet, colorAnimator);
                combinedAnimatorSet.start();

                // Toast mesajını göster
                //Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                new NURfonksiyonlar().ozelSnackBarIkonlu(context, fab,iconResId,5000,fontSize,toastSatir,toastBaslik,"#000000",toastMessage,"#ffffff","#FF018786",true,false );
            }
        });
    }












    //Kulanımı: private FloatingActionButton fab;
    // fab = findViewById(R.id.fab);
    // NtHelper.setupFab(this, fab, R.drawable.ic_star, R.color.fab_original_color, R.color.fab_clicked_color, R.animator.fab_animation, "Ekle");

    /**
     * FloatingActionButton için genel bir yapılandırma metodu.
     * Bu metod, FAB'e tıklama animasyonu, renk değişimi ve tıklama sonrası mesaj gösterme işlevlerini ekler.
     *
     * @param context          Uygulama bağlamı, genellikle `this` olarak geçilir.
     * @param fab              Özelleştirilecek View (FloatingActionButton) nesnesi.
     * @param logoResId        FAB üzerinde görüntülenecek ikonun kaynak ID'si.
     * @param satirResId       FAB üzerinde görüntülenecek ikonun kaynak ID'si.
     * @param millisec           TOAST Mesajı Gösterilme Süresi.
     * @param fontSize           TOAST Mesajı Metin boyutu.
     * @param toastSatirSayisi   TOAST Mesajı Satır Sayısı.
     * @param toastBaslik        TOAST mesajı Başlığı.
     * @param baslikColor        TOAST mesajı Başlık Rengi için kaynak ID'si.
     * @param toastMessages      TOAST mesajlarının dizisi.
     * @param toastMessagesColor        TOAST mesajı Rengi için kaynak ID'si.
     * @param toastBackgroundColor      TOAST mesajı Background için kaynak ID'si.
     * @param isLogoEnabled             TOAST mesajı Logo Gösterimi.
     * @param isButtonEnabled           TOAST mesajı satır içi İkon Gösterimi.
     * @param originalColorResId        FAB Orijinal arka plan rengi için kaynak ID'si.
     * @param clickedColorResId         FAB Tıklama sonrası arka plan rengi için kaynak ID'si.
     * @param animationResId            FAB'e tıklandığında oynatılacak animasyonun kaynak ID'si.


     */
    public static void setupFabArrayMsg(
            Context context,
            FloatingActionButton fab,
            int logoResId,
            int satirResId,
            int millisec,
            int fontSize,
            int toastSatirSayisi,
            String toastBaslik,
            String baslikColor,
            String[] toastMessages,
            String toastMessagesColor,
            String toastBackgroundColor,
            boolean isLogoEnabled,
            boolean isButtonEnabled,
            int originalColorResId,
            int clickedColorResId,
            int animationResId) {
        // new NURfonksiyonlar().ozelSnackBarIkonluArray(this,xxxGroup6,R.drawable.star1,R.drawable.star2,millisec,fontSize,toastSatir,toastBaslik,baslikColor,toastMessages,toastMessagesColor,toastBackgroundColor,isLogoEnabled,isButtonEnabled,originalColorResId,clickedColorResId,animationResId);
        // FAB ikonunu ayarla
        fab.setImageResource(logoResId);

        // Orijinal ve tıklama sonrası renkleri al
        int originalColor = ContextCompat.getColor(context, originalColorResId);
        int clickedColor = ContextCompat.getColor(context, clickedColorResId);

        // FAB'in arka plan rengini orijinal renk olarak ayarla
        fab.setBackgroundTintList(ColorStateList.valueOf(originalColor));

        // Tıklama olayını ayarla
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rotasyon animasyonunu yükle
                AnimatorSet rotateAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, animationResId);
                rotateAnimatorSet.setTarget(fab);

                // Renk animasyonunu oluştur
                ValueAnimator colorAnimator = ValueAnimator.ofArgb(originalColor, clickedColor, originalColor);
                colorAnimator.setDuration(500); // Animasyon süresi
                colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                        fab.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
                    }
                });

                // Animasyonları birlikte oynat
                AnimatorSet combinedAnimatorSet = new AnimatorSet();
                combinedAnimatorSet.playTogether(rotateAnimatorSet, colorAnimator);
                combinedAnimatorSet.start();

                // Toast mesajını göster
//new NURfonksiyonlar().ozelSnackBarIkonluArray(this, xxxGroup6, alertIconResId2, msgResId2, messages, 4000, 16, 5, "Tebrikler!", "#000000", "#ffffff", "#FF018786", false, false);

                new NURfonksiyonlar().ozelSnackBarIkonluArray(context,fab,logoResId,satirResId,toastMessages,toastMessagesColor,toastBackgroundColor,millisec,fontSize,toastSatirSayisi,toastBaslik,baslikColor,isLogoEnabled,isButtonEnabled );
            }
        });
    }
















}