package com.example.bcagamma;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TfBankActivity extends AppCompatActivity {

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_bank);

        // Initialize UI elements
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvMtf = findViewById(R.id.tvMtf);
        Button btnAntarBank = findViewById(R.id.btnAntarBank);
        Button btnAntarRekening = findViewById(R.id.btnAntarRekening);

        // Set up TextToSpeech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                } else {
                    Toast.makeText(TfBankActivity.this, "Text to speech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up click listeners for buttons
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAntarBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakButton(btnAntarBank.getText().toString());
                Intent intent = new Intent(TfBankActivity.this, TfAntarBankActivity.class);
                startActivity(intent);
            }
        });

        btnAntarRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakButton(btnAntarRekening.getText().toString());
                Intent intent = new Intent(TfBankActivity.this, TfAntarRekening.class);
                startActivity(intent);
            }
        });
    }

    private void speakButton(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ButtonSpeak");
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
