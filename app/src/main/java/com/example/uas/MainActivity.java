package com.example.uas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uas.api.ClientAsyncTask;
import com.example.uas.data.model.Dokter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ListDokterAdapter mAdapter;
    private List<Dokter> listDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formIntent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(formIntent);
            }
        });

        listView = findViewById(R.id.list_data);
        listDokter = new ArrayList<Dokter>();
        mAdapter = new ListDokterAdapter(this, listDokter);

        listView.setAdapter(mAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                showActionDialog(position);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent formIntent = new Intent(MainActivity.this, DocDetail.class);
                startActivity(formIntent);
            }
        });

        loadData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        try {
            ClientAsyncTask task = new ClientAsyncTask(this, new ClientAsyncTask.OnPostExecuteListener() {
                @Override
                public void onPostExecute(String result) {
                    if (result.equals("error")) {
                        Toast.makeText(getBaseContext(), "Tidak Dapat Terkoneksi Dengan Server", Toast.LENGTH_SHORT).show();
                    } else {
                        processResponse(result);
                    }
                }
            });
            task.request_type = "get";
            task.api_url = "list_dokter.php";
            task.showDialog = true;
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processResponse(String response) {
        Log.d("JSON_DATA", response);
        try {
            JSONObject jsonobj = new JSONObject(response);
            JSONArray jsonArray = jsonobj.getJSONArray("dokter");

            Dokter dokter = null;
            for ( int i = 0 ; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                dokter = new Dokter();
                dokter.setId(obj.getInt( "id"));
                dokter.setNama(obj.getString( "nama"));
                dokter.setAhli(obj.getString( "ahli"));
                listDokter.add(dokter);
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showActionDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Choose Option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent formIntent = new Intent(MainActivity. this, FormActivity. class);
                    Dokter dokter = listDokter.get( position );
                    formIntent.putExtra("id", dokter.getId().toString());
                    formIntent.putExtra("nama", dokter.getNama());
                    formIntent.putExtra("ahli", dokter.getAhli());
                    formIntent.putExtra("keterangan", dokter.getKeterangan());
                    startActivity(formIntent);
                } else {
                    deleteData(position);
                }
            }
        });
        builder.show();
    }

    private void deleteData(int position) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id",String.valueOf(listDokter.get(position).getId())));
        try {
            ClientAsyncTask task = new ClientAsyncTask(this, new ClientAsyncTask.OnPostExecuteListener() {
                @Override
                public void onPostExecute(String result) {
                    Log.d("TAG", "delete:" + result);
                    if (result.contains("Error description")) {
                        Toast.makeText(getBaseContext(), "Tidak Dapat Terkoneksi Dengan Server", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                    }
                }
            });
            task.request_type = "post";
            task.api_url = "delete_Data.php";
            task.showDialog = true;
            task.setParams(params);
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
