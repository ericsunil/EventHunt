package com.peyxen.eventilizer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.peyxen.eventilizer.Model.Events;
import com.peyxen.eventilizer.util.EventsData;

import java.util.ArrayList;

/**
 * Created by Pci on 1/15/2016.
 */
public class EventDetails extends AppCompatActivity {
    private static final String TAG = "EventDetails";

    private String insertUrl = "";
    private TextView _title, _location, _desc;
    private Button _joined, _rating, _category, btnJoin;
    private RatingBar _ratingBar;
    private ImageView _img;
    private static final String KEY_JOINED = "joined";
    private int joined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _title = (TextView) findViewById(R.id.eTitle);
        _desc = (TextView) findViewById(R.id.eDetails);
        _location = (TextView) findViewById(R.id.eLocation);

        _joined = (Button) findViewById(R.id.eJoined);
        _rating = (Button) findViewById(R.id.actual_rating);
        _category = (Button) findViewById(R.id.eCategory);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        _ratingBar = (RatingBar) findViewById(R.id.eRating);

        _img = (ImageView) findViewById(R.id.img_event_details);
        int _id = getIntent().getExtras().getInt("_id");

        if (MainActivity.isPublic)
            btnJoin.setText("Join");
        else if (MainActivity.isPaid)
            btnJoin.setText("200$");

        setUpData(_id);


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.isPublic) {
                    btnJoin.setClickable(false);
                    btnJoin.setText("Joined ✓✓");
                    joined++;
                    _joined.setText(joined + "");
                } else if (MainActivity.isPaid) {
                    final ProgressDialog progressDialog = new ProgressDialog(EventDetails.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    progressDialog.setMessage("Making payment via eSewa..");

                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            btnJoin.setText("Joined");

                        }
                    }, 3000);
                }
                //   update();
            }
        });
        //Snackbar.make(view, "Added to MyFavourite", Snackbar.LENGTH_LONG)
        //      .setAction("Action", null).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void setUpData(int _id) {
        ArrayList<Events> mAllEvents = null;
        if (MainActivity.isPublic)
            mAllEvents = EventsData.publicEvents;
        else if (MainActivity.isPaid)
            mAllEvents = EventsData.paidEvents;
        for (int i = 0; i < mAllEvents.size(); i++) {
            if (mAllEvents.get(i).getId() == _id) {
                String title = mAllEvents.get(i).getTitle();
                String desc = mAllEvents.get(i).getDescription();
                String date = mAllEvents.get(i).getDate();
                String location = mAllEvents.get(i).getLocation();
                String amount = mAllEvents.get(i).getAmount() + "";
                joined = mAllEvents.get(i).getJoined();
                int rating = mAllEvents.get(i).getRating();
                if(MainActivity.isPaid)
                btnJoin.setText(amount);
                _title.setText(title.toString());
                _desc.setText(desc.toString());
                _location.setText(location.toString());
                _joined.setText(joined + "");
                _rating.setText(rating + "");
                if (title.equals("COBWEB LIVE IN BANEPA"))
                    _img.setImageResource(R.drawable.cobweb);
                _ratingBar.setRating(rating);
                _ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        _ratingBar.setRating(v);
                        _ratingBar.setIsIndicator(true);
                    }
                });
                break;
            }
        }

    }

}


