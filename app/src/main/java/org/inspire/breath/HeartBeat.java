package org.inspire.breath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

public class HeartBeat extends AppCompatActivity {
    TextView heartBeats;
    WawReader reader = new WawReader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_beat);

        InputStream in = getResources().openRawResource(R.raw.heartbeat02a);

        try {
            InputStream is = getResources().openRawResource(R.raw.heartbeat02a);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            byte [] arr = buffer.toByteArray();


            File tempFile = File.createTempFile("tmp", ".wav", null);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(arr);
            int cnt = reader.getResult(tempFile);
            heartBeats = (TextView)findViewById(R.id.res_field);
            heartBeats.setText(""+cnt);
        }
        catch (Exception e){
            System.out.println("no read");
        }

//        int cnt = reader.getResult(file);
//        System.out.println(cnt);

    }


}

