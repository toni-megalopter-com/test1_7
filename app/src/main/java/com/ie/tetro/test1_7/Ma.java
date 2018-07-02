package com.ie.tetro.test1_7;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyStore;

import InstAuth.ApplicationData;
import InstAuth.InstagramApp;

public class Ma extends AppCompatActivity {
    public JSONObject jsonObj;
    InstagramApp mApp;
    public TextView r0;
    public TextView r1;
    public TextView r2;
    public TextView r3;
    public TextView r4;
    public TextView r5;
    public TextView r6;
    public TextView r7;
    public Button btn;
    public ImageView img;
    private void loadAll() {
        try {

            r0 = (TextView) findViewById(R.id.r0);
            r1 = (TextView) findViewById(R.id.r1);
            r2 = (TextView) findViewById(R.id.r2);
            r3 = (TextView) findViewById(R.id.r3);
            r4 = (TextView) findViewById(R.id.r4);
            r5 = (TextView) findViewById(R.id.r5);
            r6 = (TextView) findViewById(R.id.r6);
            r7 = (TextView) findViewById(R.id.r7);
            img = (ImageView) findViewById(R.id.imageView);
            btn = (Button) findViewById(R.id.btn1);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    disconnectUser();
                    q.productionAlert();
                }
            });
            // q.ma.btn.setEnabled(false);

        } catch (Exception ex) {
            q.mex(ex);
        }

    }
    public void add2sp(int type, String name, Object val) {
        try {
            SharedPreferences.Editor editor = getSharedPreferences("general", MODE_PRIVATE).edit();
            switch (type) {
                case 3:
                    editor.putInt(name, (int) val);
                    break;
                case 6:
                    editor.putString(name, (String) val);
                    break;
            }
            editor.apply();
                /*
        apply() is asynchronous call to perform disk I/O where as commit() is synchronous.
         So avoid calling commit() from the UI thread. – Aniket Thakur Nov 10 '15 at 8:55

         */
        } catch (Exception ex) {
            q.mex(ex);
        }
    }
    public Object getsp(int type, String name) {

        Object obj = null;
        try {
            SharedPreferences prefs = getSharedPreferences("general", MODE_PRIVATE);
            switch (type) {
                case 3:
                    obj = prefs.getInt(name, 0);
                    break;
                case 6:
                    obj = prefs.getString(name, null);
                    break;
            }
        } catch (Exception ex) {
            q.mex(ex);
        }
        return obj;
    }
    public void posts() {
        new Thread() {
            @Override
            public void run() {
                try {
                    q.token = (String) q.ma.getsp(6, "instagramtoken");
                    jsonObj = Utils.getMedia("https://api.instagram.com/v1/users/self/media/recent/?access_token=" + q.token);
                    String link = (String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONArray) jsonObj.get("data")).get(0)).get("images")).get("thumbnail")).get("url");
                    Utils.downloadImage(link);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }
    public void showT(final String mess) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void connectUser() {
        try {
            mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
                    ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
            mApp.setListener(new InstagramApp.OAuthAuthenticationListener() {

                @Override
                public void onSuccess() {
                    try {
                        posts();
                    } catch (Exception ex) {
                        q.mex(ex);
                    }
                }
                @Override
                public void onFail(String error) {
                    Toast.makeText(Ma.this, error, Toast.LENGTH_SHORT).show();
                }
            });

            if (mApp.hasAccessToken()) {
                posts();
            } else {
                mApp.authorize();
            }
        } catch (Exception ex) {
            q.mex(ex);
        }
    }
    private void disconnectUser() {
        try {
            if (mApp.hasAccessToken()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(
                        Ma.this);
                builder.setMessage("Disconnect from Instagram?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        mApp.resetAccessToken();

                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception ex) {
            q.mex(ex);
        }
    }
    private void oncr() {
        try {
            loadAll();
            connectUser();
        } catch (Exception ex) {
            q.mex(ex);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma);
        q.ma = this;
        this.oncr();
        q.productionAlert();
    }
}
