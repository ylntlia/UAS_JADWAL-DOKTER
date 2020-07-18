package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.uas.data.model.Dokter;

public class DocDetail extends AppCompatActivity {

    public static final String EXTRA_DOKTER ="extra_dokter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_detail);

        /**
        TextView tvNama = findViewById(R.id.tv_nama_dokter);
        Dokter dokter = getIntent().getParcelableExtra(EXTRA_DOKTER);
        String nama = dokter.getNama();
        //TextView tvAhli = findViewById(R.id.tv_ahli_dokter);
        //String ahli = dokter.getAhli();
        //TextView tvKeterangan = findViewById(R.id.tv_keterangan);
        //String keterangan = dokter.getKeterangan();
        tvNama.setText(nama);
        //tvAhli.setText(ahli);
        //tvKeterangan.setText(keterangan);
         */
    }
}
