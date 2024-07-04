package com.example.bcagamma;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TfBank2Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private ImageView btnBack;
    private Button btnAntarBank;
    private Button btnAntarRekening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_bank2);

        // Initialize TextToSpeech
        tts = new TextToSpeech(this, this);

        btnBack = findViewById(R.id.btnBack);
        btnAntarBank = findViewById(R.id.btnAntarBank);
        btnAntarRekening = findViewById(R.id.btnAntarRekening);

        // Setup back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back button pressed, finish the activity
                finish();
            }
        });

        // Setup button listeners
        btnAntarBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Antar Bank button clicked");
                Intent intent = new Intent(TfBank2Activity.this, TfAntarBank2Activity.class);
                startActivity(intent);
            }
        });

        btnAntarRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Antar Rekening button clicked");
                Intent intent = new Intent(TfBank2Activity.this, TfAntarRekening2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "TTS language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "TTS initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
