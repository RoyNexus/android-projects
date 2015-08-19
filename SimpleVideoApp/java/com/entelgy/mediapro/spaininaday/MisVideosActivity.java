package com.entelgy.mediapro.spaininaday;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.entelgy.mediapro.spaininaday.sql.Video;
import com.entelgy.mediapro.spaininaday.sql.VideosDAO;
import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;
import com.entelgy.mediapro.spaininaday.util.VideoArrayAdapter;

import java.util.List;

public class MisVideosActivity extends SpainInADayActivity {

    public static final String TAG = "UploadVideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_videos);

        // Set title in Action Bar
        TextView txtView = (TextView) findViewById(R.id.actionBarText);
        txtView.setText(R.string.mis_videos);
        // Set visible/invisible buttons in Action Bar
        LinearLayout actionbarLayout = (LinearLayout) findViewById(R.id.rightActionbar);
        actionbarLayout.setVisibility(View.INVISIBLE);
        // Set Typefaces
        setIkarosTypeface(R.id.actionBarText);
        //setIkarosTypeface(R.id.restablecerBtn);
        setLatoRegularTypeface(R.id.soloWifiTxt);

        SelectVideosTask task = new SelectVideosTask();
        task.execute();

    }

    private class SelectVideosTask extends AsyncTask<Void, Void, List<Video>> {

        @Override
        protected List<Video> doInBackground(Void... params) {
            try {
                VideosDAO videosDAO = VideosDAO.getInstance(getApplicationContext());
                return videosDAO.getAllVideos();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Video> videos) {

            Log.d(TAG, "onPostExcecute result: " + videos);
            if ((videos == null) || (videos.isEmpty())) {
                // Error al recuperar los videos
                showAlert(MisVideosActivity.this, R.string.error, R.string.mensaje_error_red,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        },
                        null);

            } else {
                // Se muestra listado de videos
                ListView listView = (ListView) findViewById(R.id.videoList);
                listView.setClickable(false);

                VideoArrayAdapter videoArrayAdapter = new VideoArrayAdapter(getApplicationContext(), videos);
                listView.setAdapter(videoArrayAdapter);
            }
        }
    }


}
