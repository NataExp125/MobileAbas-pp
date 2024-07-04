package com.example.bcagamma;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText etCode;
    private Button btnLogin;
    private static final String CORRECT_CODE = "123456";

    TextToSpeech tts;
    boolean isSpeaking = false; // Flag untuk menandai apakah sedang dalam proses pembacaan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCode = findViewById(R.id.etCode);
        btnLogin = findViewById(R.id.btnLogin);

        // Inisialisasi TextToSpeech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(1.f);
                } else {
                    Toast.makeText(LoginActivity.this, "Text to speech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Menambahkan TextWatcher untuk EditText
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Menggunakan TextToSpeech untuk membacakan setiap angka secara terpisah
                if (!isSpeaking && !s.toString().isEmpty()) {
                    String lastChar = s.toString().substring(s.length() - 1);
                    tts.speak(lastChar, TextToSpeech.QUEUE_FLUSH, null);
                    isSpeaking = false;
                }
            }
        });

        // Menambahkan OnClickListener untuk tombol login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etCode.getText().toString();
                if (CORRECT_CODE.equals(code)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Kode salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Menambahkan listener untuk menandai selesai pembacaan teks
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {}

            @Override
            public void onDone(String utteranceId) {
                isSpeaking = false;
            }

            @Override
            public void onError(String utteranceId) {}
        });
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
