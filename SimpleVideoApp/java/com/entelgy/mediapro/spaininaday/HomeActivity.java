package com.entelgy.mediapro.spaininaday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;

import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;

public class HomeActivity extends SpainInADayActivity {

    public static final String VIDEO_PARAM = "SELECTED_VIDEO";
    private static final long GET_DATA_INTERVAL = 6000;
    private static final int RECORD_VIDEO_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;
    private int images[] = {R.drawable.background_1, R.drawable.background_2, R.drawable.background_3, R.drawable.background_4};
    private int index = 0;
    private RelativeLayout layout = null;
    private Handler hand = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.home);

        // Set Typefaces
        setIkarosTypeface(R.id.enviarVideoPrincipalTxt);
        setLatoRegularTypeface(R.id.enviarVideoSecondaryTxt);

        setIkarosTypeface(R.id.grabarVideoPrincipalTxt);
        setLatoRegularTypeface(R.id.grabarVideoSecondaryTxt);

        setIkarosTypeface(R.id.misVideosPrincipalTxt);
        setLatoRegularTypeface(R.id.misVideosSecondaryTxt);

        setIkarosTypeface(R.id.miPerfilPrincipalTxt);
        setLatoRegularTypeface(R.id.miPerfilSecondaryTxt);

        setIkarosTypeface(R.id.homeTitle1);
        setIkarosTypeface(R.id.homeTitle2);
        setIkarosTypeface(R.id.homeTitle3);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            try {
                layout.setBackground(getResources().getDrawable(images[index++]));
                if (index == images.length) {
                    index = 0;
                }
                hand.postDelayed(run, GET_DATA_INTERVAL);
            } catch (Exception exception) {
                // Ignore exceptions
                exception.printStackTrace();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Background image carousel
        try {
            this.layout = (RelativeLayout) findViewById(R.id.homeLayout);
            hand.postDelayed(run, GET_DATA_INTERVAL);
        } catch (Exception exception) {
            // Ignore exceptions
            exception.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop Background image carousel
        hand.removeCallbacks(run);
    }



    public void goToGrabarVideo(View view) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // High quality
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 900); // 15 x 60
            startActivityForResult(takeVideoIntent, RECORD_VIDEO_REQUEST);
        }
    }

    public void goToEnviarVideo(View view) {
        Intent pickVideoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        if (pickVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pickVideoIntent, PICK_VIDEO_REQUEST);
        }
    }

    public void goToMisVideos(View view) {
        Intent intent = new Intent(this, MisVideosActivity.class);
        startActivity(intent);
    }

    public void goToMiPerfil(View view) {
        Intent intent = new Intent(this, MiPerfilActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (requestCode == RECORD_VIDEO_REQUEST && resultCode == RESULT_OK) {
            Uri videoUri = resultIntent.getData();
            Intent intent = new Intent(getBaseContext(), UploadVideoActivity.class);
            intent.putExtra(VIDEO_PARAM, videoUri);
            startActivity(intent);
        } else if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK) {
            Uri videoUri = resultIntent.getData();
            Intent intent = new Intent(getBaseContext(), UploadVideoActivity.class);
            intent.putExtra(VIDEO_PARAM, videoUri);
            startActivity(intent);
        }
    }
}

