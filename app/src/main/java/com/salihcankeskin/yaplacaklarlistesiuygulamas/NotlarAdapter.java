package com.salihcankeskin.yaplacaklarlistesiuygulamas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotlarAdapter extends RecyclerView.Adapter<NotlarAdapter.NotlarviewHolder> {


    private List<Not> notlar;

    private OnNotClickListener listener;

    public NotlarAdapter(List<Not> notlar, OnNotClickListener listener) {
        this.notlar = notlar;
        this.listener = listener;
    }

    public void setNotlar(List<Not> notlar) {
        this.notlar = notlar;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotlarAdapter.NotlarviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notlar_satiri,parent,false);
        return new NotlarviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotlarAdapter.NotlarviewHolder holder, int position) {
        Not not = notlar.get(position);
        holder.baslikTextView.setText(not.getBaslik());
        holder.icerikTextView.setText(not.getIcerik());

        holder.silButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSilmeClick(not);
            }
            });

        holder.guncelleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGuncellemeClick(not);
            }
            });




    }

    @Override
    public int getItemCount() {
        return notlar.size();
    }

    public class NotlarviewHolder extends RecyclerView.ViewHolder {
        private TextView baslikTextView;
        private TextView icerikTextView;
        private Button silButton;
        private Button guncelleButton;

        public NotlarviewHolder(@NonNull View itemView) {
            super(itemView);
            baslikTextView=itemView.findViewById(R.id.not_basligi);
            icerikTextView=itemView.findViewById(R.id.not_içerigi);

            silButton=itemView.findViewById(R.id.not_sil_button);
            guncelleButton=itemView.findViewById(R.id.not_guncelle_button);

        }
    }

    public interface OnNotClickListener
    {
        void onSilmeClick(Not not);
        void onGuncellemeClick(Not not);


    }


}

