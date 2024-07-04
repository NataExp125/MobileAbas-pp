package com.example.bcagamma;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMInfo, btnMTransfer, btnQRIS;
    private ImageView btnExit;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textToSpeech = new TextToSpeech(getApplicationContext(), new OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        textToSpeech.speak("Language not supported", TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    textToSpeech.speak("Initialization failed", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

        btnExit = findViewById(R.id.btnExit);
        btnMInfo = findViewById(R.id.btnMInfo);
        btnMTransfer = findViewById(R.id.btnMTransfer);
        btnQRIS = findViewById(R.id.btnQRIS);


        final int EXIT_BUTTON_ID = R.id.btnExit;
        final int M_INFO_BUTTON_ID = R.id.btnMInfo;
        final int M_TRANSFER_BUTTON_ID = R.id.btnMTransfer;
        final int QRIS_BUTTON_ID = R.id.btnQRIS;

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak("Cek Saldo", TextToSpeech.QUEUE_FLUSH, null, null);
                Intent intent = new Intent(HomeActivity.this, CheckBalanceActivity.class);
                startActivity(intent);
            }
        });

        btnMTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak("M - Transfer", TextToSpeech.QUEUE_FLUSH, null, null);
                Intent intent = new Intent(HomeActivity.this, MTranfer.class);
                startActivity(intent);
            }
        });
        btnQRIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        String textToRead = "Selamat Datang , di BCA Mobile";
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        textToSpeech.speak("Language not supported", TextToSpeech.QUEUE_FLUSH, null, null);
                    } else {
                        textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    textToSpeech.speak("Text to speech initialization failed", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onClick(View view) {
        //
    }
}
