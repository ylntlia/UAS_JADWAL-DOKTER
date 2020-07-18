package com.example.uas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uas.api.ClientAsyncTask;
import com.example.uas.data.model.Dokter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class FormActivity extends Activity {
    EditText edtAhli, edtNama, edtKeterangan;
    Button btnSImpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtAhli = findViewById(R.id.edt_ahli);
        edtNama = findViewById(R.id.edt_nama);
        edtKeterangan = findViewById(R.id.edt_keterangan);
        final Dokter dokter = new Dokter();
        if (getIntent().hasExtra( "id")) {
            String id = getIntent().getStringExtra( "id");
            String nama = getIntent().getStringExtra( "nama");
            String ahli = getIntent().getStringExtra( "ahli");
            String keterangan = getIntent().getStringExtra("keterangan");
            edtNama.setText(nama);
            edtAhli.setText(ahli);
            edtKeterangan.setText(keterangan);
            dokter.setId(Integer. valueOf (id));
        } else {
            dokter.setId( 0 );
        }

        btnSImpan = findViewById(R.id.btn_simpan);
        btnSImpan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = edtNama.getText().toString();
                String ahli = edtAhli.getText().toString();
                String keterangan = edtKeterangan.getText().toString();
                simpanData(dokter.getId(), nama, ahli, keterangan);
            }
        });
    }

    private void simpanData(int id, String nama, String ahli, String keterangan) {
        ArrayList<NameValuePair> params = new ArrayList<>();
        if (id !=0) {
            params.add(new BasicNameValuePair("id", String.valueOf(id)));
        }
        params.add(new BasicNameValuePair("nama", nama));
        params.add(new BasicNameValuePair("ahli", ahli));
        params.add(new BasicNameValuePair("keterangan", keterangan));

        try {
            ClientAsyncTask task = new ClientAsyncTask(this, new ClientAsyncTask.OnPostExecuteListener() {
                @Override
                public void onPostExecute(String result) {
                    Log.d("TAG", "savedata:" + result);

                    if (result.contains("Error description")) {
                        Toast.makeText(getBaseContext(), "Tidak Dapat terkoneksi Dengan Server", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                    }
                }
            });
            task.request_type = "post";
            task.api_url = "save_data.php";
            task.showDialog = true;
            task.setParams(params);
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
