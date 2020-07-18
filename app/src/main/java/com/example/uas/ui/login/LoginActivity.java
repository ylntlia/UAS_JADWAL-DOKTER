package com.example.uas.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uas.MainActivity;
import com.example.uas.R;
import com.example.uas.RegisterActivity;
import com.example.uas.data.LoginDataSource;


public class LoginActivity extends AppCompatActivity {
    protected Cursor cursor;
    EditText user, pass;
    Button btnRegistrasi, btnLogin;
    LoginDataSource dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new LoginDataSource(this);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);

        btnLogin = findViewById(R.id.login);
        btnRegistrasi = findViewById(R.id.daftar);

        SessionManager sessionManager = new SessionManager(this.getApplication());

        if(sessionManager.isLoggedIn()) {
            goToActivity();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CekLogin();
            }
        });

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(daftar);
            }
        });
    }

    public void CekLogin() {
        SessionManager sessionManager = new SessionManager(this.getApplication());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_user WHERE username = " +
                "'" +user.getText().toString()+ "' AND password = '"+pass.getText().toString()+"'", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            final SessionManager sm = new SessionManager(getApplicationContext());
            sessionManager.createLoginSession(cursor.getString(2).toString(), cursor.getString(1).toString());

            Toast.makeText(getApplicationContext(), "Selamat Datang " + sm.getNama(), Toast.LENGTH_SHORT).show();

            goToActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Salah", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToActivity(){
        Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mIntent);
    }
}
