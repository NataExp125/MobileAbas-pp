package com.example.bcagamma;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TfAntarRekening2Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Spinner spinnerRekening;
    private EditText etBiaya;
    private Button btnSend;
    private ImageView btnBack;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_antar_rekening2);

        tts = new TextToSpeech(this, this);

        spinnerRekening = findViewById(R.id.spinnerRekening);
        etBiaya = findViewById(R.id.etBiaya);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);

        // Data untuk diisi ke dalam Spinner Rekening
        List<String> rekeningList = new ArrayList<>();
        rekeningList.add("Pilih Rekening");
        rekeningList.add("Rekening 1");
        rekeningList.add("Rekening 2");
        rekeningList.add("Rekening 3");

        // Adapter untuk Spinner Rekening
        ArrayAdapter<String> rekeningAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rekeningList);
        rekeningAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRekening.setAdapter(rekeningAdapter);

        // Mengatur listener untuk Spinner
        spinnerRekening.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedRekening = rekeningList.get(position);
                    speak("Selected Rekening: " + selectedRekening);
                } else {
                    speak("Pilih Rekening");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Mengatur listener untuk EditText etBiaya
        etBiaya.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    speak("Biaya");
                } else {
                    String biaya = etBiaya.getText().toString();
                    if (!biaya.isEmpty()) {
                        speak("Biaya: " + biaya);
                    }
                }
            }
        });

        etBiaya.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count > 0) {
                    char lastChar = charSequence.charAt(start);
                    speak(String.valueOf(lastChar));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Handling when the Send button is clicked
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if both Spinner and EditText are filled
                if (!spinnerRekening.getSelectedItem().toString().isEmpty() && !etBiaya.getText().toString().isEmpty()) {
                    speak("Sending... Transaksi Anda Berhasil");
                    Intent intent = new Intent(TfAntarRekening2Activity.this, Sukses2.class);
                    startActivity(intent);
                } else {
                    // Show a toast message if either Spinner or EditText is empty
                    Toast.makeText(TfAntarRekening2Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back button pressed, finish the activity
                finish();
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Text to Speech not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Text to Speech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
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
