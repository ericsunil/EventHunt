package com.peyxen.eventilizer.util;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.peyxen.eventilizer.Model.KeyShops;
import com.peyxen.eventilizer.Model.Shops;
import com.peyxen.eventilizer.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pci on 1/26/2016.
 */
public class ShopsData {

    private static final String TAG = "ShopsData";
    public static ArrayList<Shops> shops;
    private static final String getUrl = "http://"+Config.IP_ADDRESS+"/eventilizer/JSON/shops.php";
    RequestQueue requestQueue;
    Context context;
    public ShopsData(final Context context) {
        this.context = context;
        requestQueue = Singleton.getInstance().getmRequestQueue();
        shops = new ArrayList<Shops>();
        doInBackground();
    }
    public void doInBackground()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getUrl, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONArray datas = response.getJSONArray(KeyShops.KEY_SHOPS);

                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject data = datas.getJSONObject(i);
                        int id = data.getInt(KeyShops.KEY_ID);
                        String name = data.getString(KeyShops.KEY_NAME);
                        String item = data.getString(KeyShops.KEY_ITEM);
                        int price = data.getInt(KeyShops.KEY_PRICE);
                        shops.add(new Shops(id,name,item,price));
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, "ERROR JSON" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
