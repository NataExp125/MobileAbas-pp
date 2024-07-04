package com.example.bcagamma;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CheckBalanceActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Mengakhiri activity ini dan kembali ke HomeActivity
            }
        });

        String textToRead = "M-info\n 07/05 03:40:15\n No rek . 234535534\n Saldo Anda Berjumlah : \n Rp.80 ribu rupiah";

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Bahasa tidak didukung, tampilkan pesan kesalahan
                        textToSpeech.speak("Language not supported", TextToSpeech.QUEUE_FLUSH, null, null);
                    } else {
                        textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    // TTS tidak berhasil diinisialisasi, tampilkan pesan kesalahan
                    textToSpeech.speak("Text to speech initialization failed", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
