package com.peyxen.eventilizer.util;

import android.content.Context;
import android.util.Log;

import com.peyxen.eventilizer.Model.Events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Pci on 1/14/2016.
 */
public class Filter {
    private static final String TAG = "Filter";
    private ArrayList<Events> allEvents;
    private Context context;


    public Filter(ArrayList<Events> allEvents, Context context) {
        this.allEvents = allEvents;
        this.context = context;
    }

    public ArrayList<Events> getLatestEvents() {
        ArrayList<Events> reversed = new ArrayList<Events>();
        for (int i = allEvents.size() - 1; i >= 0; i--)
            reversed.add(allEvents.get(i));
        return reversed;
    }

    public ArrayList<Events> getNearbyEvents() {
        ArrayList<Events> nearby = new ArrayList<Events>();
        ArrayList<Events> latestEvents = getLatestEvents();
        String curLoc = "";
//        TraceLocation traceLocation = new TraceLocation(context);
     //   traceLocation.traceLocation();
  //      if (traceLocation.mLocation.equals(""))
            curLoc = "Banepa";
    //    else
     //       curLoc = traceLocation.mLocation;
        for (int i = 0; i < latestEvents.size(); i++) {
            if (latestEvents.get(i).getLocation().equals(curLoc))
                nearby.add(latestEvents.get(i));
        }
        return nearby;
    }

    public ArrayList<Events> getPopularEvents() {
        ArrayList<Events> popularEvents = new ArrayList<Events>();
        ArrayList<Events> latestEvents = getLatestEvents();
        final int MAX_RATING = 5;
        final int MIN_RATING = 0;
        int curRating = MAX_RATING;
        while (curRating > MIN_RATING - 1) {
            for (int i = 0; i < latestEvents.size(); i++) {
                if (latestEvents.get(i).getRating() == curRating)
                    popularEvents.add(latestEvents.get(i));
            }
            curRating--;
        }
        return popularEvents;
    }

    public ArrayList<Events> getUpcomingEvents() {
        ArrayList<Events> latestEvents = getLatestEvents();
        ArrayList<Events> upcomingEvents = new ArrayList<Events>();
        ArrayList<Integer> diffCollections = new ArrayList<Integer>();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy");

        for (int i = 0; i < latestEvents.size(); i++) {
            Date today = null, curDay = null;
            try {
                today = format.parse(format.format(cal.getTime()));
                curDay = format.parse(latestEvents.get(i).getDate());
                int diff = dateDifference(today, curDay);
                if (diff >= 0)
                    diffCollections.add(diff);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //HashSet to remove duplicates
        HashSet<Integer> set = new HashSet<Integer>(diffCollections);
        diffCollections.clear();
        diffCollections.addAll(set);
        //sort in ascending order for upcoming events
        Collections.sort(diffCollections);

        Log.d(TAG, diffCollections.toString());

        for (int x = 0; x < diffCollections.size(); x++) {
            for (int i = 0; i < latestEvents.size(); i++) {
                Date today = null, curDay = null;
                try {
                    today = format.parse(format.format(cal.getTime()));
                    curDay = format.parse(latestEvents.get(i).getDate());
                    int diff = dateDifference(today, curDay);
                    if (diff == diffCollections.get(x)) {
                        upcomingEvents.add(latestEvents.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();

                }
            }
        }
        return upcomingEvents;
    }

    public int dateDifference(Date date1, Date date2) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String sDate1 = formater.format(date1);
        String sDate2 = formater.format(date2);
        String[] pDate1 = sDate1.split("-");
        int year1 = Integer.parseInt(pDate1[0]);
        int mon1 = Integer.parseInt(pDate1[1]);
        int day1 = Integer.parseInt(pDate1[2]);
        String[] pDate2 = sDate2.split("-");
        int year2 = Integer.parseInt(pDate2[0]);
        int mon2 = Integer.parseInt(pDate2[1]);
        int day2 = Integer.parseInt(pDate2[2]);
        int tDays1 = year1 * 365 + mon1 * 30 + day1;
        int tDays2 = year2 * 365 + mon2 * 30 + day2;
        return (tDays2 - tDays1);
    }

}
