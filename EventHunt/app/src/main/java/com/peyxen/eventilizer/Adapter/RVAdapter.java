package com.peyxen.eventilizer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.peyxen.eventilizer.EventDetails;
import com.peyxen.eventilizer.Model.Events;
import com.peyxen.eventilizer.R;

import java.util.ArrayList;

/**
 * Created by Pci on 1/14/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewholder> {

    private static final String TAG = "RVAdapter";
    public Context context;
    public ArrayList<Events> events;

    public RVAdapter(Context context,ArrayList<Events> events)
    {
        this.context = context;
        this.events = events;
    }
    @Override
    public RVViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.event_row, parent, false);
        RVViewholder holder = new RVViewholder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVViewholder holder, final int position) {
        if(events.get(position).getTitle().equals("Atif Aslam Live In Nepal"))
            holder.imageView.setBackgroundResource(R.drawable.paid1);
        if(events.get(position).getTitle().equals("COBWEB LIVE IN BANEPA"))
            holder.imageView.setBackgroundResource(R.drawable.cobweb);
        if(events.get(position).getTitle().equals("Sonakshi Sinha Performance"))
            holder.imageView.setBackgroundResource(R.drawable.paid2);
        if(events.get(position).getTitle().equals("Bhote Jatra"))
            holder.imageView.setBackgroundResource(R.drawable.public1);
        if(events.get(position).getTitle().equals("Bisket Jatra"))
            holder.imageView.setBackgroundResource(R.drawable.public2);
        if(events.get(position).getTitle().equals("Deep Purple Nepal Concert"))
            holder.imageView.setBackgroundResource(R.drawable.public3);

        holder.title.setText(events.get(position).getTitle());
        holder.title.setTextColor(Color.BLACK);
        holder.date.setText(events.get(position).getDate());
        holder.date.setTextColor(Color.BLACK);
        holder.location.setText(events.get(position).getLocation());
        holder.location.setTextColor(Color.BLACK);
        holder.ratingBar.setRating(events.get(position).getRating());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetails.class);
                intent.putExtra("_id",events.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class RVViewholder extends RecyclerView.ViewHolder {
        private CardView cv;
        private ImageView imageView;
        private TextView title, location, date;
        private RatingBar ratingBar;

        public RVViewholder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_event);
            imageView = (ImageView)itemView.findViewById(R.id.images);
            title = (TextView) itemView.findViewById(R.id.row_title);
            location = (TextView) itemView.findViewById(R.id.row_location);
            date = (TextView) itemView.findViewById(R.id.row_date);
            ratingBar = (RatingBar) itemView.findViewById(R.id.row_rating);
        }
    }
}
