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

public class MTranfer extends AppCompatActivity {

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtranfer);

        // Initialize UI elements
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvMtranfer = findViewById(R.id.tvMtranfer);
        Button btnTranfer = findViewById(R.id.btnTranfer);
        Button btnDaftarTransfer = findViewById(R.id.btnDaftarTransfer);

        // Set up TextToSpeech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                } else {
                    Toast.makeText(MTranfer.this, "Text to speech initialization failed", Toast.LENGTH_SHORT).show();
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

        btnTranfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakButton(btnTranfer.getText().toString());
                Intent intent = new Intent(MTranfer.this, TfBankActivity.class);
                startActivity(intent);
            }
        });

        btnDaftarTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakButton(btnDaftarTransfer.getText().toString());
                Intent intent = new Intent(MTranfer.this, TfBank2Activity.class);
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
