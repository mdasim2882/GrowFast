package com.digitaladvisor.growfast.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MyDownloadingService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    private File sdCard = Environment.getExternalStorageDirectory();
    private static final String downloader = "DOWNLOADER";

    public MyDownloadingService() {
        super("MyDownloadingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(downloader, "onHandleIntent: SERVICE IS RUNNING");
        String urlToDownload = intent.getStringExtra("url");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {

            //create url and connect
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();

            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();
//            Log.d("DOWNLOADER", "onHandleIntent: fileLength: "+fileLength);
            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());


            File dir = new File(sdCard.getAbsolutePath() + "/Digital Advisor/Videos");
// create this directory if not already created
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filename = "DigiVideo-" + System.currentTimeMillis() + ".mp4";
            String path = sdCard.getAbsolutePath() + "/Digital Advisor/Videos/" + filename;
            OutputStream output = new FileOutputStream(path);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                // publishing the progress....
                Bundle resultData = new Bundle();
//                Log.d(downloader, "onHandleIntent:Total:  "+total+"Counter: "+(total * 100 / fileLength));
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);

                output.write(data, 0, count);

            }

            // close streams
            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            Log.d(downloader, "onHandleIntent: error:" + e.getMessage());
            Toast.makeText(this, "Error:-1", Toast.LENGTH_SHORT).show();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);

        receiver.send(UPDATE_PROGRESS, resultData);
    }

}