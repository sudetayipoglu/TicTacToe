package com.mobilprogramlar.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class NURfonksiyonlar {
    Context cnn;

    public void progressBarclasim(Context context, int yuzde, TextView tv_yuzde, TextView tv_yuzde_mesaj_yazilacak_yer, String tv_yuzde_mesaj_icerik,  ProgressBar p1){
        final Handler[] handler = {new Handler()};
        final int[] progres_sayac = {0};
        new Thread(() -> {
            while (progres_sayac[0] < yuzde) {
                progres_sayac[0] += 1;
                handler[0].post(new Runnable() {
                    public void run() {
                        p1.setProgress(progres_sayac[0]);
                        tv_yuzde.setText(progres_sayac[0] + "/" + p1.getMax());
                        //tv_yuzde.setText(getString(R.string.progress_text, progres_sayac[0], p1.getMax()));

                    }

                    private int getString(int progressText, int i, int max) {
                        return progressText;
                    }
                });
                //try {
                //    Thread.sleep(70);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    // Bekleme süresi bittiğinde burası çalışacak
                }, 70);
            }
        }).start();
        if (yuzde == 100) {
            String Mesaj_ilerleme = tv_yuzde_mesaj_icerik + " Bitti TEBRİKLER";
            tv_yuzde_mesaj_yazilacak_yer.setText(Mesaj_ilerleme);
            p1.getProgressDrawable().setColorFilter(Color.parseColor("#FF1C6A1F"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(yuzde == 0){
            String Mesaj_ilerleme = tv_yuzde_mesaj_icerik + " Başlamadınız";
            tv_yuzde_mesaj_yazilacak_yer.setText(Mesaj_ilerleme);
            p1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }else{
            String Mesaj_ilerleme = tv_yuzde_mesaj_icerik + " Az Kaldı";
            tv_yuzde_mesaj_yazilacak_yer.setText(Mesaj_ilerleme);
            p1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }









    public void progressBarIcerik(Context context, int yuzde, TextView tv_yuzde, TextView tv_yuzde_mesaj_yazilacak_yer, String tv_yuzde_mesaj_icerik,  ProgressBar p1){
        final Handler[] handler = {new Handler()};
        final int[] progres_sayac = {0};
        new Thread(() -> {
            while (progres_sayac[0] < yuzde) {
                progres_sayac[0] += 1;
                handler[0].post(new Runnable() {
                    public void run() {
                        p1.setProgress(progres_sayac[0]);
                        tv_yuzde.setText(progres_sayac[0] + "/" + p1.getMax());
                        //tv_yuzde.setText(getString(R.string.progress_text, progres_sayac[0], p1.getMax()));
                    }

                    private int getString(int progressText, int i, int max) {
                        return progressText;
                    }
                });
                //try {
                //    Thread.sleep(70);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Bekleme süresi bittiğinde burası çalışacak
                    }
                }, 70);
            }
        }).start();
        if (yuzde == 100) {
            String Mesaj_ilerleme = tv_yuzde_mesaj_icerik + " Bitti TEBRİKLER";
            tv_yuzde_mesaj_yazilacak_yer.setText(Mesaj_ilerleme);
            p1.getProgressDrawable().setColorFilter(Color.parseColor("#FF1C6A1F"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(yuzde == 0){
            String Mesaj_ilerleme = tv_yuzde_mesaj_icerik + " Başlamadınız";
            tv_yuzde_mesaj_yazilacak_yer.setText(Mesaj_ilerleme);
            p1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }else{
            String Mesaj_ilerleme = tv_yuzde_mesaj_icerik + " Az Kaldı";
            tv_yuzde_mesaj_yazilacak_yer.setText(Mesaj_ilerleme);
            p1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }





    //KULLANIMI
    //NURfonksiyonlar.gosterTOAST(this, "Hatalı Giriş", "Lütfen sınav adını girin!", 2000);
    public static void gosterTOAST(Context context, String Baslik, String msg, int millisec)
    {
        Handler handler = null;
        final Toast[] toast_sureli = new Toast[1];
        for(int i = 0; i < millisec; i+=8000) {
            LayoutInflater inflater = LayoutInflater.from(context);

            //View layout = inflater.inflate(R.layout.toast_ekrani, null );
            // Doğru kullanım
            View layout = inflater.inflate(R.layout.nurfonksiyonlar_toast_ekrani, null, false);
            TextView baslikText = layout.findViewById(R.id.toast_baslik);
            TextView textMesaj = layout.findViewById(R.id.toast_mesaj);
            baslikText.setText(Baslik);
            textMesaj.setText(msg);
            toast_sureli[0] = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast_sureli[0].setGravity(Gravity.BOTTOM, 0, 0);
            toast_sureli[0].setDuration(Toast.LENGTH_LONG);
            toast_sureli[0].setView(layout);  //setView yani özel toast kullanımdan kaldırıldı. Bunun yerine SnackBar kullan
            toast_sureli[0].show();
            if(handler == null) {
                handler = new Handler();
                handler.postDelayed(() -> toast_sureli[0].cancel(), millisec);
            }
        }
    }




     /*
    // Örnek bir View referansı
        View view = findViewById(R.id.myView);

        // Snackbar mesajı
        String mesaj = "Bu, özelleştirilmiş bir Snackbar mesajıdır.";

        // Snackbar'ı gösterme
        new NURfonksiyonlar().ozelSnackBar(
            this,             // mevcut Activity veya Fragment context'i fonksiyona gönderiliyor.
            view,             // Snackbar'ın hangi View üzerinde gösterileceği belirleniyor.
            mesaj,            // Mesaj metni
            7000,             // Gösterim süresi (milisaniye cinsinden)
            5,                // Maksimum satır sayısı
            16,               // Metin boyutu (sp cinsinden)
            "#FFFFFF",        // Metin rengi (Beyaz)
            "#FF0000"         // Arka plan rengi (Kırmızı)
        );
     */

    public void ozelSnackBar(Context context, View v, String msg, int millisec, int satir_sayisi, int textSize, String textcolor, String backgroundcolor) {

        if (context == null || v == null || msg == null || msg.isEmpty()) {
            Log.e("SnackBarError", "Invalid input parameters");
            return;
        }
        // Renk geçerlilik kontrolü
        if (!isValidColorCode(textcolor) || !isValidColorCode(backgroundcolor)) {
            Log.e("SnackBarError", "Invalid color code");
            return;
        }
        // Snackbar oluşturma
        Snackbar snackbar_alt = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);

        // Snackbar View'ı özelleştirme
        View snackBarView_ALT = snackbar_alt.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView_ALT.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        snackBarView_ALT.setLayoutParams(params);

        // Arka plan ve metin rengini ayarlama
        snackBarView_ALT.setBackgroundColor(Color.parseColor(backgroundcolor));
        TextView mainTextView = snackBarView_ALT.findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setMaxLines(satir_sayisi);
        mainTextView.setTextColor(Color.parseColor(textcolor));
        mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        mainTextView.setTextSize(textSize);
        mainTextView.setPadding(0, 0, 0, 0);

        // Animasyon ve diğer ayarlar
        snackbar_alt.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        snackbar_alt.setDuration(millisec);

        // Ekstra özellik: Aksiyon butonu ekleme
        snackbar_alt.setActionTextColor(Color.BLUE);
        snackbar_alt.setAction("Tamam", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aksiyon butonuna basıldığında yapılacak işlemler buraya yazılabilir
            }
        });

        // Snackbar'ı gösterme
        snackbar_alt.show();
    }

    private boolean isValidColorCode(String colorStr) {
        try {
            Color.parseColor(colorStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }







//KULLANIMI
//new NURfonksiyonlar().ozelSnackBarIkonlu(TytFavDuzenle.this,editSinavAdi,alertIconResId,4000,16,5,"Eksik Giriş!","#000000","Lutfen sinav adini giriniz","#ffffff","#FF0000",true,true);
//new NURfonksiyonlar().ozelSnackBarIkonlu(this,xxxGroup6,alertIconResId2,4000,16,5,"Tebrikler!","#000000","Favori kaydedildi","#ffffff","#FF018786",false,false );
public void ozelSnackBarIkonlu(Context context, View v, int iconResId, int millisec, int textSize, int satir_sayisi, String baslik, String baslikRengi, String msg, String msgcolor, String backgroundcolor, boolean isIconEnabled, boolean isButtonEnabled) {
    // Snackbar oluştur
    Snackbar snackbar_alt = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
    View snackBarView_ALT = snackbar_alt.getView();

    // Snackbar ayarları
    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView_ALT.getLayoutParams();
    params.gravity = Gravity.BOTTOM;
    snackBarView_ALT.setLayoutParams(params);
    snackBarView_ALT.setBackgroundColor(Color.parseColor(backgroundcolor));   // Arka plan rengi

    // Mesaj ve başlık için SpannableStringBuilder oluşturma
    SpannableStringBuilder builder = new SpannableStringBuilder();

    // Başlık ekleme ve stil verme
    if (baslik != null && !baslik.isEmpty()) {
        builder.append(baslik).append("\n"); // Başlık ve boşluk ekleme
        builder.setSpan(new StyleSpan(Typeface.BOLD), 0, baslik.length(), 0); // Başlığı kalın yapma
        builder.setSpan(new ForegroundColorSpan(Color.parseColor(baslikRengi)), 0, baslik.length(), 0); // Başlık rengini ayarlama

        // Başlık için yazı boyutunu ayarlama (textSize değerinin 2 fazlası)
        float baslikSizeMultiplier = (textSize + 2.0f) / textSize;
        builder.setSpan(new android.text.style.RelativeSizeSpan(baslikSizeMultiplier), 0, baslik.length(), 0);
    }

    // İkon ekleme, eğer isIconEnabled true ise
    if (isIconEnabled && iconResId != 0) {
        builder.append("   ");
        builder.setSpan(new ImageSpan(context, iconResId, ImageSpan.ALIGN_BOTTOM), builder.length() - 1, builder.length(), 0);
        builder.append("   ");
    }

    // Mesaj ekleme
    builder.append(msg);

    // Mesajın metin özelliklerini ayarla
    TextView mainTextView = snackBarView_ALT.findViewById(com.google.android.material.R.id.snackbar_text);
    mainTextView.setMaxLines(satir_sayisi);
    mainTextView.setTextColor(Color.parseColor(msgcolor));
    mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
    mainTextView.setTextSize(textSize);
    mainTextView.setPadding(0, 0, 0, 0);
    mainTextView.setText(builder);  // Başlık, mesaj ve ikonu birlikte ayarla

    // Snackbar animasyonu ve gösterim süresi
    snackbar_alt.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
    snackbar_alt.setDuration(millisec);

    // Butonun eklenmesi, eğer isButtonEnabled true ise
    if (isButtonEnabled) {
        snackbar_alt.setActionTextColor(ContextCompat.getColor(context, R.color.nurullah_buttonBackgroundColor));
        snackbar_alt.setAction("Tamam", view -> {
            // Aksiyon butonuna basıldığında yapılacak işlemler buraya yazılabilir
        });

        // Aksiyon butonunun stilini değiştirme
        Button snackbarActionButton = snackbar_alt.getView().findViewById(com.google.android.material.R.id.snackbar_action);
        snackbarActionButton.setBackgroundColor(Color.parseColor("#0000FF")); // Arka plan rengini ayarlama
        snackbarActionButton.setTextColor(Color.WHITE); // Metin rengini beyaz yapma
        snackbarActionButton.setTextSize(16); // Metin boyutunu ayarlama
        snackbarActionButton.setTypeface(snackbarActionButton.getTypeface(), Typeface.BOLD); // Metni kalın yapma
        snackbarActionButton.setPadding(12, 12, 12, 12); // Butona padding ekleme
    }

    snackbar_alt.show();
}










// KULLANIMI
// String array'i XML'den çekmek
// String[] messages = getResources().getStringArray(R.array.main_topics);
// String[] messages = {"Satır 1: Tebrikler", "Satır 2: Başarıyla kaydedildi"};
// int alertIconResId = R.drawable.alert_icon; // Başlık ikonu
// int msgIconResId = R.drawable.msg_icon; // Mesaj ikonları
// new NURfonksiyonlar().ozelSnackBarIkonluArray(TytFavDuzenle.this, editSinavAdi, alertIconResId, msgResId, new String[]{"Satır 1", "Satır 2"}, 4000, 16, 5, "Eksik Giriş!", "#000000", "#ffffff", "#FF0000", true, true);
// new NURfonksiyonlar().ozelSnackBarIkonluArray(this, xxxGroup6, alertIconResId2, msgResId2, messages, 4000, 16, 5, "Tebrikler!", "#000000", "#ffffff", "#FF018786", false, false);

    public void ozelSnackBarIkonluArray(Context context, View v, int iconResId, int msgResId, String[] msg, String msgcolor, String msgBackgroundcolor, int millisec, int fontSize, int satir_sayisi, String baslik, String baslikRengi,  boolean isIconEnabled, boolean isButtonEnabled) {
        // Snackbar oluştur
        Snackbar snackbar_alt = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
        View snackBarView_ALT = snackbar_alt.getView();

        // Snackbar ayarları
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView_ALT.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        params.setMargins(20, 0, 20, 210); // Alttan 50dp margin ekleme
        snackBarView_ALT.setLayoutParams(params);
        snackBarView_ALT.setBackgroundColor(Color.parseColor(msgBackgroundcolor));   // Arka plan rengi

        // Mesaj ve başlık için SpannableStringBuilder oluşturma
        SpannableStringBuilder builder = new SpannableStringBuilder();

        // Başlık ekleme ve stil verme
        if (baslik != null && !baslik.isEmpty()) {
            if (isIconEnabled && iconResId != 0) {
                builder.append("   ");
                builder.setSpan(new ImageSpan(context, iconResId, ImageSpan.ALIGN_BOTTOM), builder.length() - 1, builder.length(), 0);
                builder.append("   ");
            }

            builder.append(baslik).append("\n"); // Başlık ve boşluk ekleme
            builder.setSpan(new StyleSpan(Typeface.BOLD), builder.length() - baslik.length(), builder.length(), 0); // Başlığı kalın yapma
            builder.setSpan(new ForegroundColorSpan(Color.parseColor(baslikRengi)), builder.length() - baslik.length(), builder.length(), 0); // Başlık rengini ayarlama

            // Başlık için yazı boyutunu ayarlama (textSize değerinin 2 fazlası)
            float baslikSizeMultiplier = (fontSize + 2.0f) / fontSize;
            builder.setSpan(new android.text.style.RelativeSizeSpan(baslikSizeMultiplier), builder.length() - baslik.length(), builder.length(), 0);
        }

        // Mesajları ekleme
        if (msg != null) {
            for (String message : msg) {
                builder.append("\n"); // Mesajlar arasında boşluk bırakma
                builder.append("   "); // İkon alanı
                builder.setSpan(new ImageSpan(context, msgResId, ImageSpan.ALIGN_BOTTOM), builder.length() - 1, builder.length(), 0);
                builder.append("   ").append(message);
            }
        }

        // Mesajın metin özelliklerini ayarla
        TextView mainTextView = snackBarView_ALT.findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setMaxLines(satir_sayisi);
        mainTextView.setTextColor(Color.parseColor(msgcolor));
        mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        mainTextView.setTextSize(fontSize);
        mainTextView.setPadding(0, 0, 0, 0);
        mainTextView.setText(builder);  // Başlık, mesaj ve ikonu birlikte ayarla

        // Snackbar animasyonu ve gösterim süresi
        snackbar_alt.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        snackbar_alt.setDuration(millisec);

        // Butonun eklenmesi, eğer isButtonEnabled true ise
        if (isButtonEnabled) {
            snackbar_alt.setActionTextColor(ContextCompat.getColor(context, R.color.nurullah_buttonBackgroundColor));
            snackbar_alt.setAction("Tamam", view -> {
                // Aksiyon butonuna basıldığında yapılacak işlemler buraya yazılabilir
            });

            // Aksiyon butonunun stilini değiştirme
            Button snackbarActionButton = snackbar_alt.getView().findViewById(com.google.android.material.R.id.snackbar_action);
            snackbarActionButton.setBackgroundColor(Color.parseColor("#0000FF")); // Arka plan rengini ayarlama
            snackbarActionButton.setTextColor(Color.WHITE); // Metin rengini beyaz yapma
            snackbarActionButton.setTextSize(16); // Metin boyutunu ayarlama
            snackbarActionButton.setTypeface(snackbarActionButton.getTypeface(), Typeface.BOLD); // Metni kalın yapma
            snackbarActionButton.setPadding(12, 12, 12, 12); // Butona padding ekleme
        }

        snackbar_alt.show();
    }



















    //KULLANIMI
    // new NURfonksiyonlar().ozelSnackBar(getContext(),view, cevap_Getir(gosterilen_soruID),7000,5,16,"#000000","#9979D3");
    public void ozelSnackBar1(Context context, View v, String msg, int millisec, int satir_sayisi, int textSize, String textcolor, String backgroundcolor) {
        Snackbar snackbar_alt = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
        View snackBarView_ALT = snackbar_alt.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackBarView_ALT.getLayoutParams();
        params.gravity = Gravity.BOTTOM;

        snackBarView_ALT.setLayoutParams(params);
        //snackBarView_ALT.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_background));   //snackbar_alt BACKGROUN COLOR
        snackBarView_ALT.setBackgroundColor(Color.parseColor(String.valueOf(backgroundcolor)));   //snackbar_alt BACKGROUN COLOR

        //TextView mainTextView =  (snackBarView_ALT).findViewById(R.id.snackbar_text);
        TextView mainTextView = (snackBarView_ALT).findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setMaxLines(satir_sayisi);       //10 satır maksimum

        //mainTextView.setTextColor(Color.WHITE);
        mainTextView.setTextColor(Color.parseColor(String.valueOf(textcolor)));
        mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);  //Mesajı ORTALAR
        mainTextView.setTextSize(textSize);
        mainTextView.setPadding(0,0,0,0);

        snackbar_alt.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);     //Animasyon veriyor
        snackbar_alt.setDuration(millisec);     //snackbar_alt 5 saniye bekliyor

        //SpannableStringBuilder builder = new SpannableStringBuilder();
        //builder.append("My message ").append(" ");
        //builder.setSpan(new ImageSpan(context, R.drawable.ic_paylas), builder.length() - 1, builder.length(), 0);

        snackbar_alt.setActionTextColor(Color.BLUE);
        /*
        snackbar_alt.setAction("Tamam", new View.OnClickListener() {    //snackbar_alt buttonuna basıldığında
            @Override
            public void onClick(View view) {
            }
        });
        */
        snackbar_alt.show();

        /*
                Snackbar snackbar;
                snackbar = Snackbar.make(view, cevap_Getir(gosterilen_soruID), Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
                snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.snackbar_background));
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.snackbar_text));
                snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.uyari_textColor_1));
                snackbar.setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.snackbar_background));
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setDuration(3000);
                snackbar.setAction("Tamam", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                snackbar.show();
                */
    }







}
