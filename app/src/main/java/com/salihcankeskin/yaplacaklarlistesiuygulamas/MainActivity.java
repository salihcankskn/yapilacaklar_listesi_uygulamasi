package com.salihcankeskin.yaplacaklarlistesiuygulamas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotlarAdapter.OnNotClickListener {

    private EditText baslikEditText, icerikEditText;
    private Button ekleButton;
    private RecyclerView recyclerView;
    private NotlarAdapter notlarAdapter;
    private DatabaseReference notlarRef;
    private FirebaseAuth auth;
    private Not not;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baslikEditText = findViewById(R.id.not_basligi);
        icerikEditText = findViewById(R.id.not_icerigi);
        ekleButton = findViewById(R.id.not_ekle_btn);
        recyclerView = findViewById(R.id.notlar_recyler_view);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        notlarRef = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notlarAdapter = new NotlarAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(notlarAdapter);

        ekleButton.setOnClickListener(v -> notEkleGuncellet());

        gorevleriYukle();
    }

    private void gorevleriYukle() {
        String kullaniciId = auth.getCurrentUser().getUid();
        Query query = notlarRef.child("notlar").orderByChild("kullaniciId").equalTo(kullaniciId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Not> notlar = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Not not = dataSnapshot.getValue(Not.class);
                    if (not != null) {
                        not.setId(dataSnapshot.getKey());
                        notlar.add(not);
                    }
                }
                notlarAdapter.setNotlar(notlar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Okuma Başarısız", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notEkleGuncellet() {
        String baslik = baslikEditText.getText().toString().trim();
        String icerik = icerikEditText.getText().toString().trim();

        String kullaniciId = auth.getCurrentUser().getUid();
        if (baslik.isEmpty() || icerik.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
        } else {
            if (not == null) {
                String notId = notlarRef.child("notlar").push().getKey();
                not = new Not(notId, baslik, icerik, kullaniciId);
                notlarRef.child("notlar").child(notId).setValue(not).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "İşlem Başarılı", Toast.LENGTH_SHORT).show();
                    kontrolleriTemizle();
                    gorevleriYukle();
                }).addOnFailureListener(e -> Toast.makeText(this, "İşlem başarısız", Toast.LENGTH_SHORT).show());
            } else {
                not.setBaslik(baslik);
                not.setIcerik(icerik);
                notlarRef.child("notlar").child(not.getId()).setValue(not).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                    kontrolleriTemizle();
                    gorevleriYukle();
                }).addOnFailureListener(e -> Toast.makeText(this, "Güncelleme Başarısız", Toast.LENGTH_SHORT).show());
            }
        }
    }

    private void kontrolleriTemizle() {
        baslikEditText.setText("");
        icerikEditText.setText("");
        not = null;
    }

    @Override
    public void onGuncellemeClick(Not not) {
        this.not = not;
        baslikEditText.setText(not.getBaslik());
        icerikEditText.setText(not.getIcerik());
    }

    @Override
    public void onSilmeClick(Not not) {
        notlarRef.child("notlar").child(not.getId()).removeValue().addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Silme Başarılı", Toast.LENGTH_SHORT).show();
            gorevleriYukle();
        }).addOnFailureListener(e -> Toast.makeText(this, "Silme Başarısız", Toast.LENGTH_SHORT).show());
    }
}
