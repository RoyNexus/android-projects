package com.entelgy.mediapro.spaininaday;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.entelgy.mediapro.spaininaday.rest.Category;
import com.entelgy.mediapro.spaininaday.rest.Subcategory;
import com.entelgy.mediapro.spaininaday.sql.Video;
import com.entelgy.mediapro.spaininaday.sql.VideosDAO;
import com.entelgy.mediapro.spaininaday.util.CategoryUtil;
import com.entelgy.mediapro.spaininaday.util.SessionManager;
import com.entelgy.mediapro.spaininaday.util.SpainInADayActivity;
import com.entelgy.mediapro.spaininaday.util.ValidationException;

import java.util.Arrays;

public class UploadVideoActivity extends SpainInADayActivity {

    public static final String TAG = "UploadVideoActivity";

    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video);
        // Set title in Action Bar
        TextView txtView = (TextView) findViewById(R.id.actionBarText);
        txtView.setText(R.string.subir_video);
        // Set visible/invisible buttons in Action Bar
        LinearLayout actionbarLayout = (LinearLayout) findViewById(R.id.rightActionbar);
        actionbarLayout.setVisibility(View.INVISIBLE);

        // Populate Categories
        String firstCategoryName = populateCategories();
        populateSubcategories(firstCategoryName);
        // Set Typefaces
        setIkarosTypeface(R.id.actionBarText);
        setIkarosTypeface(R.id.uploadVideoBtn);
        setLatoRegularTypeface(R.id.tituloVideoTxt);
        setLatoRegularTypeface(R.id.descripcionTxt);
        setLatoRegularTypeface(R.id.textLegal);
        // Get the video preview
        final VideoView videoView = (VideoView) findViewById(R.id.videoPreview);
        Bundle extras = getIntent().getExtras();
        //mediaControls = new MediaController(UploadVideoActivity.this);
        //videoView.setMediaController(mediaControls);
        videoView.setZOrderOnTop(true);
        videoView.setVideoURI(Uri.parse(extras.get(HomeActivity.VIDEO_PARAM).toString()));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
                        mediaController = new MediaController(UploadVideoActivity.this);
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);
                        mediaController.setBackgroundColor(getResources().getColor(R.color.custom_transparent));
                    }
                });
            }
        });

        //videoView.requestFocus();
        videoView.start();
    }

    public void onSaveVideo(View view) {
        try {
            Video video = validateVideoForm();
            video.setCalidad("");
            video.setCategoria("");
            video.setFilePath("");
            video.setOffset(0);
            video.setUrl("");
            video.setTotalSize(100000000);
            video.setUserId(SessionManager.getInstance(getApplicationContext()).getLoggedUserId());
            video.setResolucion("");
            video.setSegundos("");
            video.setSubcategoria("");
            InsertVideoTask insertVideoTask = new InsertVideoTask();
            insertVideoTask.execute(video);

        } catch (ValidationException validationException) {
            // Mostramos error de validación
            showAlert(UploadVideoActivity.this, R.string.datos_validos_video, validationException.getCode(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    },
                    null
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    private class InsertVideoTask extends AsyncTask<Video, Void, Long> {

        @Override
        protected Long doInBackground(Video... params) {
            try {
                VideosDAO videosDAO = VideosDAO.getInstance(getApplicationContext());
                Long result = videosDAO.save(params[0]);
                return result;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Long videoId) {
            Log.d(TAG, "onPostExcecute result: " + videoId);
            if ((videoId == null) || (videoId <= 0)) {
                // Error al salvar el video
                showAlert(UploadVideoActivity.this, R.string.error, R.string.mensaje_error_red,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        },
                        null);

            } else {
                // Video guardado en BBDD
                showAlert(UploadVideoActivity.this, R.string.aviso, R.string.mensaje_video_registrado,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                goToHome();
                            }
                        },
                        null);
            }
        }
    }


    private String populateCategories() {
        String firstCategory = "";
        Spinner categoriasSpinner = (Spinner) findViewById(R.id.categoriaSpinner);
        CategoryUtil categoryUtil = CategoryUtil.getInstance();
        Category[] categories = categoryUtil.getCategories();

        MySpinnerAdapter<String> adapter = new MySpinnerAdapter(
                this,
                android.R.layout.simple_spinner_item,
                Arrays.asList(categories)
        );

        categoriasSpinner.setAdapter(adapter);
        if (categories.length > 0) {
            firstCategory = categories[0].getName();
        }

        categoriasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                populateSubcategories(((TextView) selectedItemView).getText().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // nothing to do
            }
        });

        return firstCategory;
    }

    private void populateSubcategories(String category) {
        Spinner subcategorySpinner = (Spinner) findViewById(R.id.subcategoriaSpinner);
        CategoryUtil categoryUtil = CategoryUtil.getInstance();
        Subcategory[] subcategories = categoryUtil.getSubcategories(category);

        MySpinnerAdapter<String> adapter = new MySpinnerAdapter(
                this,
                android.R.layout.simple_spinner_item,
                Arrays.asList(subcategories)
        );

        subcategorySpinner.setAdapter(adapter);
    }

}
