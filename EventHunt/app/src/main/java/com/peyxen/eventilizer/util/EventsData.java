package com.peyxen.eventilizer.util;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.peyxen.eventilizer.Model.Events;
import com.peyxen.eventilizer.Model.KeyEvents;
import com.peyxen.eventilizer.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pci on 1/9/2016.
 */
public class EventsData {

    private static final String TAG = "EventsData";
    public static ArrayList<Events> publicEvents;
    public static ArrayList<Events> paidEvents;
    public static ArrayList<Events> specialEvents;
    Context context;
    public EventsData(final Context context) {
        this.context = context;
        publicEvents = new ArrayList<Events>();
        paidEvents = new ArrayList<Events>();
        specialEvents = new ArrayList<Events>();
        doInBackground();
    }
    public void doInBackground()
    {

        publicEvents.add(new Events(5,"Bhote Jatra","Nepali Festival Bote Jatra is a chariot procession honoring the Buddhist deity of compassion Avalokiteśvara held in Lalitpur, Nepal. It is one of the greatest religious events in the city and the longest chariot festival celebrated in the country.","Nov 31,2016","Public","Kathmandu",0,1,0));
        publicEvents.add(new Events(5,"Bisket Jatra","This festival is unique to Bhaktapur and is celebrated during the Nepali New Year, which falls in mid-April. The two striking features are the chariot procession and the tall pole known as lingo that is erected. In fact there are two chariots, one for the God Bhairav and the other for his consort, Bhadrakali also known as Bhairavi.","Aug 15,2016","Public","Kathmandu",0,5,0));
        publicEvents.add(new Events(5,"Deep Purple Nepal Concert","Deep Purple have explained the reason behind cancelling their appearance in Nepal planned for March 15. The band say they were keen to keep the date, but felt forced to call it off after the promoter breached their contract, leaving them “truly miserable.”","Mar 15,2016","Public","Kathmandu",0,5,0));


        paidEvents.add(new Events(5,"COBWEB LIVE IN BANEPA","Performing Bands\n\n1) Cobweb\n2) Ember Eyes\n\nVenue: Millium Campus(Godam chowk), Banepa\nDate: March 8,Saturday\nTime: 2pm Onwards\nEntry fee: free\n\nOrganizer:Zero Double One\nSupported By: Lets Support Nepali BANDS\n\nInvite your friends too\n\nShare\\/Support\\/Spread\\n\\nref:http:\\/\\/www.eventsinnepal.com\\/event\\/cobweb-live-in-banepa","Dec 31,2016","Paid","Banepa",0,3,0));
        paidEvents.add(new Events(5,"Atif Aslam Live In Nepal","Performing Exclusively in Kathmandu\n" +
                "Pahile nazar mein… Bakhuda tumhi ho… Aadat… Tere bin… Doorie… Hum kis galli… Who lamhe… O re piya and more…\n","Aug 31,2016","Paid","Kalimati",1000,4,0));
        paidEvents.add(new Events(5,"Sonakshi Sinha Performance","Nepali actresses Sushma Karki, Reecha Sharma are also performing their performance with Bollywood actress Sonakshi Sinha and Malaika Arora Khan. There are many Nepali celebrities as well for this even. This even is held in Kathmandu Nepal. This even venue is Tudikhel","Dec 31,2016","Paid","Banepa",5000,5,0));
    }
}
