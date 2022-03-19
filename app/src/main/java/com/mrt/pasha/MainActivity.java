package com.mrt.pasha;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
/* burda android manifest e android: usesCleartextTraffic="true" yazmamız gerekiyor
application bölümüne. Eğer yazmazsak http connection ı kabul etmiyor.

 */
public class MainActivity extends AppCompatActivity {

    class myTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                InputStream is = con.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(is);

                return bm;

            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView)findViewById(R.id.imageView);

    }

    public void click(View v){
        myTask mt = new myTask();
        try {
            Bitmap bm = mt.execute("http://www.agaclar.net/forum/attachments/sukulent/7290d1171654782-pasa-008.jpg").get();
            if(bm != null){
                iv.setImageBitmap(bm);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}