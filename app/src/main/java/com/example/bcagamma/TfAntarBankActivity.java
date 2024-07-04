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

public class TfAntarBankActivity extends AppCompatActivity {

    private EditText etBankTujuan;
    private EditText etNoRekeningTujuan;
    private Button btnSend;
    private ImageView btnBack;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_antar_bank);

        etBankTujuan = findViewById(R.id.etCode);
        etNoRekeningTujuan = findViewById(R.id.etCode2);
        btnSend = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                } else {
                    Toast.makeText(TfAntarBankActivity.this, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etBankTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakHintText("Bank Tujuan");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String bankTujuan = etBankTujuan.getText().toString();
                String noRekeningTujuan = etNoRekeningTujuan.getText().toString();

                // Navigate to next page
                Intent intent = new Intent(TfAntarBankActivity.this, SuksesActivity.class);
                startActivity(intent);

                // Speak transaction success
                speakSuccessMessage();
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

    private void speakHintText(String hintText) {
        if (!hintText.isEmpty()) {
            tts.speak(hintText, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void speakSuccessMessage() {
        String successMessage = "Transaksi berhasil";
        tts.speak(successMessage, TextToSpeech.QUEUE_FLUSH, null);
    }
}
