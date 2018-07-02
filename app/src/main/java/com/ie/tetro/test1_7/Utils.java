package com.ie.tetro.test1_7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import async.Async;
import async.MyRun;

public class Utils {
    static Bitmap bmp;

    public static void endDl() {
        try {
            q.ma.img.setImageBitmap(bmp);

            String user = (String) ((JSONObject) ((JSONObject) ((JSONArray) q.ma.jsonObj.get("data")).get(0)).get("user")).get("username");
            int likes = (int) ((JSONObject) ((JSONObject) ((JSONArray) q.ma.jsonObj.get("data")).get(0)).get("likes")).get("count");
            q.ma.r0.setText("Connected as:" + user);
            q.ma.r1.setText("Likes:" + String.valueOf(likes));


            JSONArray posts = (JSONArray) q.ma.jsonObj.get("data");
            String onePost = "...";
            q.ma.r2.setText(onePost);
            q.ma.r3.setText(onePost);
            q.ma.r4.setText(onePost);
            q.ma.r5.setText(onePost);
            q.ma.r6.setText(onePost);
            q.ma.r7.setText(onePost);
            for (int i = 0; i < posts.length() && i < 6; i += 1) {
                onePost = (String) ((JSONObject) ((JSONObject) posts.get(i)).get("caption")).get("text");
                switch (i) {
                    case 0:
                        q.ma.r2.setText(onePost);
                        break;
                    case 1:
                        q.ma.r3.setText(onePost);
                        break;
                    case 2:
                        q.ma.r4.setText(onePost);
                        break;
                    case 3:
                        q.ma.r5.setText(onePost);
                        break;
                    case 4:
                        q.ma.r6.setText(onePost);
                        break;
                    case 5:
                        q.ma.r7.setText(onePost);
                        break;
                }
            }
        } catch (Exception ex) {
            q.mex(ex);
        }
    }

    public static void doDl(String link) {
        try {
            URL url = new URL(link);
            Bitmap bmp2 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Bitmap res2 = Bitmap.createScaledBitmap(bmp2, (int) 200, (int) 200, false);
            bmp = res2;
        } catch (Exception ex) {
            q.mex(ex);
        }
    }

    public static void downloadImage(final String link) {
        try {
            MyRun doit = new MyRun() {
                @Override
                public void run() {
                    try {

                        doDl(link);
                    } catch (Exception ex) {
                        q.mex(ex);
                    }
                }
            };
            MyRun endit = new MyRun() {
                @Override
                public void run() {
                    endDl();
                }
            };
            new Async(doit, endit).execute();
        } catch (Exception ex) {
            q.mex(ex);
        }
    }

    public static JSONObject getMedia(String link) {
        JSONObject res = null;
        try {
            URL url = new URL(link);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String content = in.readLine();
            in.close();
            res = new JSONObject(content);
            q.m(content);
        } catch (Exception e) {
            q.mex(e);
        }
        return res;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }
}