package com.example.bcagamma;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TfAntarRekening extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etCode3;
    private Button btnLogi;
    private ImageView voice;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_antar_rekening);

        // Initialize the views
        btnBack = findViewById(R.id.btnBack);
        etCode3 = findViewById(R.id.etCode3);
        btnLogi = findViewById(R.id.btnLogi);
        voice = findViewById(R.id.voice);

        // Initialize TextToSpeech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                } else {
                    Toast.makeText(TfAntarRekening.this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for the back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the previous activity
                finish();
            }
        });

        // Set click listener for the send button
        btnLogi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Speak hint text
                speakHintText();

                // Handle send button click
                String rekeningTujuan = etCode3.getText().toString().trim();
                if (!rekeningTujuan.isEmpty() && rekeningTujuan.length() == 6) {
                    // Add your logic here to handle the transfer
                    performTransfer(rekeningTujuan);
                } else {
                    etCode3.setError("No.Rekening Tujuan is required and must be 6 digits");
                }
            }
        });

        // Set click listener for the voice button
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TfAntarRekening.this, "Voice input feature coming soon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown TextToSpeech when the activity is destroyed
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    private void speakHintText() {
        String hintText = etCode3.getHint().toString();
        if (!hintText.isEmpty()) {
            tts.speak(hintText, TextToSpeech.QUEUE_FLUSH, null, "HintText");
        }
    }

    private void performTransfer(String rekeningTujuan) {
        // Simulate a transfer operation
        String successMessage = "Transfer ke Nomer Rekening  " + rekeningTujuan + " ,Berhasil";
        Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();
        tts.speak(successMessage, TextToSpeech.QUEUE_FLUSH, null, "TransferSuccess");
        // Add your actual transfer logic here
    }
}
