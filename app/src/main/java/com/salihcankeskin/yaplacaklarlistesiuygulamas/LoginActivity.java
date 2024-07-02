package com.salihcankeskin.yaplacaklarlistesiuygulamas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, sifreEditText;

    private Button loginButton, registerButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        sifreEditText = findViewById(R.id.sifre);
        loginButton = findViewById(R.id.giris_button);
        registerButton = findViewById(R.id.kayit_button);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(view -> kullaniciGiris());
        registerButton.setOnClickListener(view -> kullaniciKayit());
    }

    private void kullaniciKayit() {
        String email = emailEditText.getText().toString();
        String sifre = sifreEditText.getText().toString();

        if (!email.isEmpty() && !sifre.isEmpty()) {
            //giriş işlemi
            auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Kayıt işlemi başarılı.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Kayıt işlemi başarısız.", Toast.LENGTH_SHORT).show();
                }

                ;
            });


        } else {
            Toast.makeText(this, "Lütfen gerekli alanları doldurunuz.", Toast.LENGTH_SHORT).show();
        }
    }


    private void kullaniciGiris() {
        String email = emailEditText.getText().toString();
        String sifre = sifreEditText.getText().toString();

        if (!email.isEmpty() && !sifre.isEmpty()) {
            //giriş işlemi
            auth.signInWithEmailAndPassword(email, sifre).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Giriş işlemi başarılı.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Giriş işlemi başarısız.", Toast.LENGTH_SHORT).show();
                }

                ;
            });


        } else {
            Toast.makeText(this, "Lütfen gerekli alanları doldurunuz.", Toast.LENGTH_SHORT).show();

        }


    }

    }