package com.entelgy.mediapro.spaininaday.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.entelgy.mediapro.spaininaday.R;
import com.entelgy.mediapro.spaininaday.sql.Video;

import java.util.List;

public class VideoArrayAdapter extends ArrayAdapter<Video> {
    private final Context context;
    private final List<Video> values;

    public VideoArrayAdapter(Context context, List<Video> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.video_row, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.videoFirstLine);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.videoSecondLine);
        ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.videoProgressBar);

        progressBar.setProgress((int) values.get(position).get_id() * 15 );

        titleView.setText(values.get(position).getTitulo());
        setIkarosTypeface(titleView);
        descriptionView.setText(values.get(position).getDescripcion());
        setLatoRegularTypeface(descriptionView);

        return rowView;
    }

    private void setIkarosTypeface(TextView textView) {
        Typeface face = Typeface.createFromAsset(context.getAssets(),"fonts/Ikaros.otf");
        textView.setTypeface(face);
    }

    private void setLatoRegularTypeface(TextView textView) {
        Typeface face = Typeface.createFromAsset(context.getAssets(),"fonts/Lato-Regular.ttf");
        textView.setTypeface(face);
    }
}
