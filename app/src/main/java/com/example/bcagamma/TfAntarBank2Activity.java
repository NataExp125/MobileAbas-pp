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

import java.util.Locale;

public class TfAntarBank2Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Spinner spinnerBank;
    private Spinner spinnerRekening;
    private EditText etCode2;
    private EditText etCode3;
    private EditText etCode4;
    private Button btnSend;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tf_antar_bank2);

        tts = new TextToSpeech(this, this);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinnerBank = findViewById(R.id.spinnerBank);
        spinnerRekening = findViewById(R.id.spinnerRekening);
        etCode2 = findViewById(R.id.etCode2);
        etCode3 = findViewById(R.id.etCode3);
        etCode4 = findViewById(R.id.etCode4);
        btnSend = findViewById(R.id.btnLogin);

        String[] bankNames = {"Pilih Bank", "Bank BCA", "Bank MANDIRI", "Bank BRI", "Bank X"};
        ArrayAdapter<String> bankAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bankNames);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(bankAdapter);

        String[] rekeningList = {"Pilih Rekening", "Rekening 1", "Rekening 2", "Rekening 3", "Rekening 4"};
        ArrayAdapter<String> rekeningAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rekeningList);
        rekeningAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRekening.setAdapter(rekeningAdapter);

        spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedBank = bankNames[position];
                    speak("Bank Yang Dipilih: " + selectedBank);
                } else {
                    speak("Pilih Bank");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerRekening.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedRekening = rekeningList[position];
                    speak("Rekening Yang dipilih: " + selectedRekening);
                } else {
                    speak("Pilih Rekening");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etCode2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    speak("Jumlah Uang");
                } else {
                    String number = etCode2.getText().toString();
                    if (!number.isEmpty()) {
                        speak("Jumlah Uang: " + number);
                    }
                }
            }
        });

        etCode2.addTextChangedListener(new TextWatcher() {
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

        etCode3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    speak("Layanan");
                } else {
                    String layanan = etCode3.getText().toString();
                    if (!layanan.isEmpty()) {
                        speak("Layanan: " + layanan);
                    }
                }
            }
        });

        etCode4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    speak("Berita");
                } else {
                    String berita = etCode4.getText().toString();
                    if (!berita.isEmpty()) {
                        speak("Berita: " + berita);
                    }
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak("Sending...Transaksi Anda Berhasil");
                Intent intent = new Intent(TfAntarBank2Activity.this, Sukses3.class);
                startActivity(intent);
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
