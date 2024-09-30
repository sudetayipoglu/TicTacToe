package com.mobilprogramlar.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 ### Dinamik zorluk: oyun sırasında oyuncunun performansına göre oyunun yapay zekasının (AI) zorluk seviyesini
 otomatik olarak değiştirmesi anlamına gelir. Oyun sırasında oyuncunun hamlelerine göre yapay zekanın stratejisini değiştirir.
 Eğer oyuncu AI'yi sürekli yenerse, oyun daha zor hale gelir, eğer AI sürekli kaybederse oyun daha kolaylaşır.
 Kolay, Orta, Zor modlarının arasında geçiş yapılır.

 */
public class TicTacToeActivity extends AppCompatActivity {

    // 0: X, 1: O, 2: boş (oynanmamış)
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // Hücrelerin durumu // 2: boş hücre
    int[][] winPositions = { // Kazanma kombinasyonları
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Yatay
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Dikey
            {0, 4, 8}, {2, 4, 6}  // Çapraz
    };
    boolean gameActive = true; // Oyunun aktif olup olmadığını kontrol etmek için
    String gameMode; // "single", "two", "dynamic" veya "tutorial"

    //Zorluk Seviyesi Değişkeni
    String difficultyLevel = "easy"; // Kolay, Orta, Zor

    // Skor değişkenleri
    int xScore = 0;
    int oScore = 0;
    int drawScore = 0;
    TextView gameStatus, xScoreText, oScoreText, drawScoreText;
    LottieAnimationView fireworksView;
    WinningLineView winningLineView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_main);

        // TextView'ları bağlama
        gameStatus = findViewById(R.id.gameStatus);
        xScoreText = findViewById(R.id.xScore);
        oScoreText = findViewById(R.id.oScore);
        drawScoreText = findViewById(R.id.drawScore);

        // Intent'ten oyun modunu ve zorluk seviyesini al
        Intent intent = getIntent();
        gameMode = intent.getStringExtra("mode");
        difficultyLevel = intent.getStringExtra("difficulty"); //Zorluk seviyesi bilgisi

        // Oyun başlatıldığında tutorial modunu kontrol et
        if (gameMode.equals("tutorial")) {
            gameStatus.setText(getString(R.string.tutorial_welcome));
        }

        fireworksView = findViewById(R.id.fireworksView);
        winningLineView = findViewById(R.id.winningLineView);

    }

    // Tüm butonları dizide toplama işlemini bir metotla halledebiliriz
    private Button[] initializeButtons() {
        int[] buttonIds = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
        Button[] buttons = new Button[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = findViewById(buttonIds[i]);
//            buttons[i].setBackgroundColor(ContextCompat.getColor(this, R.color.button));
        }
        return buttons;
    }


    // Kazanma kontrolü sırasında havai fişek animasyonunu tetikleyin
    public void triggerFireworks() {
        // Lottie animasyonunu görünür yap ve başlat
        fireworksView.setVisibility(View.VISIBLE);
        fireworksView.playAnimation();
        // Animasyonu 3 saniye sonra durdur
        new Handler().postDelayed(() -> fireworksView.setVisibility(View.GONE), 3000);
    }


//    // Havai fişek animasyonu
//    public void triggerFireworks() {
//        Button[] buttons = initializeButtons();  // Tüm butonları başlat
//        for (Button button : buttons) {
//            if (button.getText().equals("X") || button.getText().equals("O")) {
//                Animation fireworkAnimation = AnimationUtils.loadAnimation(this, R.anim.flash_animation);
//                button.startAnimation(fireworkAnimation);  // Havai fişek animasyonu
//            }
//        }
//    }

    // Oyuncu hamlesini yerleştirme metodu
    public void placeSymbol(View view) {
        if (gameMode.equals("tutorial")) {
            placeSymbolTutorial(view);  // Eğer eğitici moddaysak bu metot çalışacak
        } else {
            Button clickedButton = (Button) view;
            int tappedPosition = Integer.parseInt(clickedButton.getTag().toString());

//            // Circular Reveal animasyonunu ekle // Circular Reveal Animation (Dairesel Açılma Efekti)
//            int cx = (clickedButton.getLeft() + clickedButton.getRight()) / 2;
//            int cy = (clickedButton.getTop() + clickedButton.getBottom()) / 2;
//            float finalRadius = (float) Math.hypot(clickedButton.getWidth(), clickedButton.getHeight());
//            Animator circularReveal = ViewAnimationUtils.createCircularReveal(clickedButton, cx, cy, 0f, finalRadius);
//            circularReveal.setDuration(1500); // Animasyonun süresi
//            circularReveal.start();

//            // Döndürme animasyonunu ekle
            Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            clickedButton.startAnimation(rotateAnimation);
            clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red));

            if (gameState[tappedPosition] == 2 && gameActive) { // ==2 hücrenin henüz oynanmadığı (boş) anlamına gelir.
                gameState[tappedPosition] = activePlayer;

//                // Patlama animasyonunu başlat
//                LottieAnimationView bubblePop = new LottieAnimationView(this);
//                bubblePop.setAnimation(R.raw.fireworks);
//                bubblePop.playAnimation();

////                // Patlayan baloncuk animasyonunu uygula
//                Animation bubblePop2 = AnimationUtils.loadAnimation(this, R.anim.bubble_pop_animation);
//                clickedButton.startAnimation(bubblePop2);

                // 1. Oyuncu X olduğunda kırmızı, 2. Oyuncu O olduğunda mavi yap
                if (activePlayer == 0) {
                    clickedButton.setText("X");
                    clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red)); // Kırmızı renk
                } else {
                    clickedButton.setText("O");
                    clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.blue)); // Mavi renk
                }

                clickedButton.setEnabled(false); // Butonu devre dışı bırak
                activePlayer = (activePlayer == 0) ? 1 : 0; // Sıradaki oyuncuya geç
                gameStatus.setText(activePlayer == 0 ? getString(R.string.x_turn) : getString(R.string.o_turn));

                int result = checkForWinOrDraw();
                if (result == 0) {
                    xScore++;
                    //xScoreText.setText("X: " + xScore);
                    xScoreText.setText(getString(R.string.x_score, xScore));
                    gameStatus.setText(getString(R.string.x_wins));
                    triggerFireworks();  // Kazanma olduğunda havai fişek efekti

                } else if (result == 1) {
                    oScore++;
                    oScoreText.setText(getString(R.string.o_score, oScore));
                    gameStatus.setText(getString(R.string.o_wins));
                    triggerFireworks();  // Kazanma olduğunda havai fişek efekti

                } else if (result == 2) {
                    drawScore++;
                    drawScoreText.setText(getString(R.string.draw_score, drawScore));
                    gameStatus.setText(getString(R.string.draw));
                }

                // Eğer oyun tek oyunculu modda ise AI'nin hamlesi
                if (gameActive && gameMode.equals("single") && activePlayer == 1) {
                    aiMove(); // AI'nin hamlesini gerçekleştir
                }
            }
        }
    }



    // AI Hareketi, Zorluk Seviyesine Göre
    public void aiMove() {
        Log.e("AI_Move", getString(R.string.ai_move_called));
        Log.e("AI_Move", getString(R.string.difficulty_level, difficultyLevel));

        if (!gameActive || difficultyLevel == null || difficultyLevel.isEmpty()) {
            Log.e("AI_Move", getString(R.string.ai_move_failed));
            return;
        }

        // Dinamik zorluk modundaysa zorluk seviyesini ayarlayın
        if (gameMode.equals("dynamic")) {
            adjustDynamicDifficulty();
        }

        switch (difficultyLevel) {
            case "easy":    // Kolay seviye: Rastgele hamle
                Log.e("AI_Move", getString(R.string.easy_level));
                easyAIMove();
                break;
            case "medium":  // Orta seviye: Kazanma veya engelleme hamlesi
                mediumAIMove();
                break;
            case "hard":    // Zor seviye: Minimax algoritması
                hardAIMove();
                break;
            default:
                easyAIMove(); // Geçersiz veya boş durumda yine "easy" hamlesi yap
                Log.e("AI_Move", getString(R.string.default_to_easy));
        }
    }

    // Kolay seviye için AI
    public void easyAIMove() {
        List<Integer> availablePositions = new ArrayList<>();
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] == 2) availablePositions.add(i);
        }
        if (availablePositions.isEmpty()) return;

        // Rastgele bir pozisyon seç
        int randomPosition = availablePositions.get(new Random().nextInt(availablePositions.size()));
        gameState[randomPosition] = activePlayer;

        Button[] buttons = initializeButtons();
        Button aiButton = buttons[randomPosition];  // Butonu doğrudan diziden seçin ve güncelleyin
        aiButton.setText("O");
        aiButton.setEnabled(false);
        aiButton.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));


        activePlayer = 0;
        gameStatus.setText(getString(R.string.x_turn));
        checkForWinOrDraw();
    }



    // Orta seviye için AI
    public void mediumAIMove() {
        Button[] buttons = initializeButtons();
        // Kazanma veya engelleme hamlesi varsa oyun devam edecek
        int move = findWinningOrBlockingMove(); // Kazanma veya engelleme hamlesini bul
        if (move == -1) {   // Kazanma veya engelleme hamlesi yoksa rastgele bir hamle yap
            easyAIMove();   // Eğer kazanma hamlesi yoksa, rastgele hamle yap
        } else {
            gameState[move] = activePlayer;     // Kazanabilecek bir hamle varsa onu oyna
            Button aiButton = buttons[move];    // Doğrudan diziden butonu alın
            aiButton.setText("O");
            aiButton.setEnabled(false);
            aiButton.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
            activePlayer = 0;
            gameStatus.setText(getString(R.string.x_turn));
            checkForWinOrDraw();
        }
    }


    // Zor seviye için AI (Minimax)
    public void hardAIMove() {
        Button[] buttons = initializeButtons();
        int bestMove = minimax(gameState, activePlayer).move;
        gameState[bestMove] = activePlayer;
        Button aiButton = buttons[bestMove];
        aiButton.setText("O");
        aiButton.setEnabled(false);
        aiButton.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
        activePlayer = 0;
        gameStatus.setText(getString(R.string.x_turn));
        checkForWinOrDraw();
    }




    // Zor AI için Minimax algoritması
    public Move minimax(int[] board, int player) {
        // Kazananı kontrol edin (0, 1 veya berabere)
        int winner = checkWinner();
        if (winner != -1) {
            return new Move(-1, winner == 0 ? 10 : (winner == 1 ? -10 : 0)); // Skorları kazanan oyuncuya göre verin
        }

        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 2) { // Boş hücre
                board[i] = player;
                Move move = new Move(i, minimax(board, player == 0 ? 1 : 0).score);
                moves.add(move);
                board[i] = 2; // Hamleyi geri al
            }
        }

        Move bestMove = null;
        if (player == 0) { // Maximizer
            int bestScore = Integer.MIN_VALUE;
            for (Move move : moves) {
                if (move.score > bestScore) {
                    bestMove = move;
                    bestScore = move.score;
                }
            }
        } else { // Minimizer
            int bestScore = Integer.MAX_VALUE;
            for (Move move : moves) {
                if (move.score < bestScore) {
                    bestMove = move;
                    bestScore = move.score;
                }
            }
        }

        return bestMove;
    }









    // Kazanma veya engelleme hamlesi arama (MEDIUM seviyesi için)
    public int findWinningOrBlockingMove() {
        for (int[] winPosition : winPositions) {
            // Kazanabilecek bir pozisyonu kontrol et
            if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[0]] != 2
                    && gameState[winPosition[2]] == 2) {
                return winPosition[2];
            } else if (gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[1]] != 2
                    && gameState[winPosition[0]] == 2) {
                return winPosition[0];
            } else if (gameState[winPosition[0]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2
                    && gameState[winPosition[1]] == 2) {
                return winPosition[1];
            }
        }
        return -1; // Eğer böyle bir hamle yoksa
    }



    // Dinamik Zorluk Ayarlama Fonksiyonu (Dinamik zorluk seviyesi için)
    public void adjustDynamicDifficulty() {
        if (xScore - oScore >= 2) {
            difficultyLevel = "hard"; // X oyuncusu çok fazla kazanıyorsa zorluk artar
        } else if (oScore - xScore >= 2) {
            difficultyLevel = "easy"; // O oyuncusu çok fazla kazanıyorsa zorluk azalır
        } else {
            difficultyLevel = "medium"; // Denge durumunda orta zorluk olur
        }
    }





    // Eğitici Bölüm İçin Oyunu Başlatma Fonksiyonu
    public void placeSymbolTutorial(View view) {
        Button clickedButton = (Button) view;
        int tappedPosition = Integer.parseInt(clickedButton.getTag().toString());

        if (gameState[tappedPosition] == 2 && gameActive) {
            gameState[tappedPosition] = activePlayer;

            // X oyuncusunun hamlesi kırmızı, O oyuncusunun hamlesi mavi olacak şekilde ayarlıyoruz
            if (activePlayer == 0) {
                clickedButton.setText("X");
                clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red)); // Kırmızı renk
                gameStatus.setText(getString(R.string.sira_o));
            } else {
                clickedButton.setText("O");
                clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.blue)); // Mavi renk
                gameStatus.setText(getString(R.string.sira_x));
            }

            clickedButton.setEnabled(false); // Butonu devre dışı bırak
            activePlayer = (activePlayer == 0) ? 1 : 0;

            // Oyun durumu ve kazananı kontrol et
            int result = checkForWinOrDraw();
            if (result == 0) {
                gameStatus.setText(getString(R.string.x_kazandi_sen_sende));
            } else if (result == 1) {
                gameStatus.setText(getString(R.string.o_kazandi_sen_sende));
            } else if (result == 2) {
                gameStatus.setText(getString(R.string.berabere_sen_dene));
            }
            if (result == 0 || result == 1 || result == 2) {
                gameStatus.setText(getString(R.string.tebarikler_ai_ile_oyna));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startPlayerTry();   // Oyuncunun kendi başına denemesi için yeni bir bölüm başlat
                    }
                }, 1500);
            }
        } else {
            gameStatus.setText(getString(R.string.yanlis_hamle));
        }
    }


    public void startPlayerTry() {
        resetGame(null);  // Oyunu sıfırla ve kullanıcıya bağımsız bir oyun deneyimi sun
        gameStatus.setText(getString(R.string.sen_dene));
        // Yapay zeka müdahalesi olmadan normal bir oyun başlar // Oyun modunu single mod yaparak yapay zekayı aktif hale getiriyoruz

        gameMode = "single";

        // Eğer difficultyLevel null ise varsayılanı ayarla
        if (difficultyLevel == null) {
            difficultyLevel = "easy";  // Varsayılan zorluk seviyesi
        }

        // Eğer AI'nın sırasıysa hamleyi yapması için çağrı yap
        if (activePlayer == 1) {
            aiMove();  // AI'nin hamlesini gerçekleştir
        }
    }



    public int checkWinner() {
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]]
                    && gameState[winPosition[1]] == gameState[winPosition[2]]
                    && gameState[winPosition[0]] != 2) {
                return gameState[winPosition[0]];  // X kazanırsa 0, O kazanırsa 1 döndür
            }
        }
        // Eğer tüm hücreler doluysa ve kazanan yoksa, beraberlik durumu kontrol edelim
        boolean isDraw = true;
        for (int state : gameState) {
            if (state == 2) {
                isDraw = false;
                break;
            }
        }
        if (isDraw) {
            return 2;  // Beraberlik durumu
        }
        return -1;  // Henüz kazanan yok
    }

    // Kazanma veya beraberlik durumunu kontrol eden metot
    public int checkForWinOrDraw() {
        Button[] buttons = initializeButtons(); // Tüm butonları başlat
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]]
                    && gameState[winPosition[1]] == gameState[winPosition[2]]
                    && gameState[winPosition[0]] != 2) {
                gameActive = false;

                drawWinningLine(winPosition); // Çizgi çiz

                // Kazanan dizilimdeki butonların arka planını yeşile çevir ve animasyon ekle
                for (int pos : winPosition) {
                    runOnUiThread(() -> {
                        //buttons[pos].setBackgroundResource(R.drawable.button_background_gradient);
                        //buttons[pos].setBackgroundColor(ContextCompat.getColor(this, R.color.green)); // Yeşil renge çevir
                        //buttons[pos].invalidate();  // Değişikliğin görünmesi için yeniden çiz

                        Animation flashAnimation = AnimationUtils.loadAnimation(this, R.anim.flash_animation);
                        buttons[pos].startAnimation(flashAnimation);

//                        // Yanıp sönme animasyonu ekle
//                        Animation flashAnimation = AnimationUtils.loadAnimation(this, R.anim.flash);
//                        buttons[pos].startAnimation(flashAnimation);

                        // Döndürme animasyonunu ekle
                        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                        buttons[pos].startAnimation(rotateAnimation);
                        buttons[pos].setBackgroundColor(ContextCompat.getColor(this, R.color.green));
                        //buttons[pos].setBackgroundResource(R.drawable.button_background_gradient);
                        //buttons[pos].setBackground(ContextCompat.getDrawable(this, R.drawable.tictacoh));
                        //buttons[pos].setBackground(ContextCompat.getDrawable(this, R.drawable.button_background_gradient));
                        //buttons[pos].setBackground(ContextCompat.getDrawable(this, R.drawable.button_background_gradient));
                        //buttons[pos].setBackground(ContextCompat.getDrawable(this, R.drawable.button_rounded));
                    });
                    //buttons[pos].post(() -> {
                    //    Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce); // Bounce animasyonu ekle
                    //    buttons[pos].startAnimation(animation);
                    //});
                }


                return gameState[winPosition[0]]; // Kazananı döndür (0: X, 1: O)
            }
        }
        // Eğer tüm hücreler doluysa ve kazanan yoksa, beraberlik durumu
        boolean isDraw = true;
        for (int state : gameState) {
            if (state == 2) {
                isDraw = false;
                break;
            }
        }
        if (isDraw) {
            gameActive = false;
            return 2; // Beraberlik
        }
        return -1; // Henüz kazanan yok
    }


    public void drawWinningLine(final int[] winPosition) {
        final Button[] buttons = initializeButtons();

        // İlk butonun ekran üzerindeki global konumunu al
        int[] firstButtonLocation = new int[2];
        buttons[winPosition[0]].getLocationOnScreen(firstButtonLocation);
        int startX = firstButtonLocation[0] + (buttons[winPosition[0]].getWidth() / 2);
        int startY = firstButtonLocation[1] + (buttons[winPosition[0]].getHeight() / 2);

        // Son butonun ekran üzerindeki global konumunu al
        int[] lastButtonLocation = new int[2];
        buttons[winPosition[2]].getLocationOnScreen(lastButtonLocation);
        int stopX = lastButtonLocation[0] + (buttons[winPosition[2]].getWidth() / 2);
        int stopY = lastButtonLocation[1] + (buttons[winPosition[2]].getHeight() / 2);

        // Çizgiyi belirlenen koordinatlar ile çiz
        winningLineView.setLine(startX, startY-50, stopX, stopY-50);
        winningLineView.setVisibility(View.VISIBLE);

        // Çizgi 2 saniye boyunca görünür olsun ve sonra gizlensin
        new Handler().postDelayed(() -> winningLineView.setVisibility(View.GONE), 4000);


    }







    // Oyunu sıfırlama metodu
    public void resetGame(View view) {
        activePlayer = 0;  // X oyuncusuyla başla
        gameActive = true;
        Arrays.fill(gameState, 2); // Tüm hücreleri boş hale getir

        GridLayout gridLayout = findViewById(R.id.gridLayout);  // Oyun tahtasını Getir
        // Tüm butonların metnini temizle (boş yap) ve tekrar etkin yap
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
            button.setEnabled(true);
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.button));
            //button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rounded)); // Yuvarlak buton arka planı

            button.clearAnimation();
        }
        // Oyun durumunu güncelle
        gameStatus.setText(getString(R.string.x_turn));
        winningLineView.resetLine();  // Çizgiyi sıfırla

        // Eğer AI'nin sırasıysa hamleyi yapması için tetikleyin
        if (gameMode.equals("single") && activePlayer == 1) {
            aiMove();
        }
    }

    // Hareket sınıfı (Minimax için)
    public static class Move {
        int move;
        int score;

        public Move(int move, int score) {
            this.move = move;
            this.score = score;
        }
    }















}
