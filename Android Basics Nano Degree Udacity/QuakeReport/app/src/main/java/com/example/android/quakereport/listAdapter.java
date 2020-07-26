package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class listAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";
    String locationOffset;
    String primaryLocation;

    public listAdapter(@NonNull Context context, List<Earthquake> earthquakeArrayList) {
        super(context, 0, earthquakeArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Earthquake currentEarthquake = getItem(position);

        //converting milliseconds to date instance.
        Date date = new Date(currentEarthquake.getTime());
        //formatting date in desired format using SimpleDateFormat Class.

        String formattedDate = formattedDate(date);
        String formattedTime = formattedTime(date);

        TextView magnitudeTv = (TextView) listItemView.findViewById(R.id.tvMagnitude);
        TextView locationOffsetTv = (TextView) listItemView.findViewById(R.id.location_offset);
        TextView primaryLocationTv = (TextView) listItemView.findViewById(R.id.primary_location);
        TextView dateTv = (TextView) listItemView.findViewById(R.id.tvTime);
        TextView timeTv = (TextView) listItemView.findViewById(R.id.tvTimeOccurred);

        String originalLocation = currentEarthquake.getCityName();

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] locationParts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = locationParts[0] + LOCATION_SEPARATOR;
            primaryLocation = locationParts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        DecimalFormat formatter = new DecimalFormat("0.0");
        String formattedMagnitude = formatter.format(currentEarthquake.getMagnitude());


        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTv.getBackground();

        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);


        magnitudeTv.setText(formattedMagnitude);
        primaryLocationTv.setText(primaryLocation);
        locationOffsetTv.setText(locationOffset);
        dateTv.setText(formattedDate);
        timeTv.setText(formattedTime);
        return listItemView;
    }

    private String formattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return simpleDateFormat.format(date);
    }

    private String formattedTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(date);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
