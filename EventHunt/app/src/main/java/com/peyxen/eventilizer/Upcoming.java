package com.peyxen.eventilizer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.peyxen.eventilizer.Adapter.RVAdapter;
import com.peyxen.eventilizer.Model.Events;
import com.peyxen.eventilizer.util.EventsData;
import com.peyxen.eventilizer.util.Filter;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Upcoming extends Fragment {
    private RecyclerView rv;
    private PullRefreshLayout layout;
    Filter filter;
    private ArrayList<Events> selectedEvent;
    private RVAdapter mAdapter;

    public Upcoming() {
        // Required empty icon_pub constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View row = inflater.inflate(R.layout.fragment_upcoming, container, false);
        rv = (RecyclerView) row.findViewById(R.id.rv_upcoming);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(MainActivity.isPublic)
            selectedEvent = EventsData.publicEvents;
        else if(MainActivity.isPaid)
            selectedEvent = EventsData.paidEvents;
        else if(MainActivity.isSpecial)
            selectedEvent = EventsData.specialEvents;

        filter = new Filter(selectedEvent,getActivity());
        mAdapter =new RVAdapter(getActivity(), filter.getUpcomingEvents());
        rv.setAdapter(mAdapter);
      layout = (PullRefreshLayout) row.findViewById(R.id.refresh_upcoming);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new EventsData(getActivity());
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 5000);
            }
        });
        return row;
    }


}