package com.peyxen.eventilizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.clans.fab.FloatingActionButton;
import com.peyxen.eventilizer.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pci on 1/26/2016.
 */
public class AddShop extends AppCompatActivity {
    private static final String TAG ="AddShop" ;
    private static final String insertUrl = "http://" + Config.IP_ADDRESS + "/eventilizer/addShop.php";
    private static final String KEY_NAME = "shop_name";
    private static final String KEY_ITEM = "item";
    private static final String KEY_PRICE = "price";

    private LinearLayout mainLayout;
    private Toolbar mToolbar;
    private ArrayList<View> childs;
    private FloatingActionButton addShop;
    private EditText shopName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_shop);
        mToolbar.setTitle("Add Shop");
        setSupportActionBar(mToolbar);

        mainLayout = (LinearLayout)findViewById(R.id.main_layout);
        addShop = (FloatingActionButton)findViewById(R.id.addShop);
        shopName = (EditText)findViewById(R.id.shop_name);
        childs = new ArrayList<View>();
        addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View curChild = getLayoutInflater().inflate(R.layout.shop_item, null);
                childs.add(curChild);
                mainLayout.addView(curChild);
            }
        });
    }
    public void addShop(View view)
    {
        if(childs.size()!=0 && childs!=null)
        {
            addShop(childs);
        }
    }
    public void addShop(ArrayList<View> mChilds)
    {
        RequestQueue requestQueue = Singleton.getInstance().getmRequestQueue();
        for(int i=0;i<mChilds.size();i++) {
            EditText curItem = (EditText)mChilds.get(i).findViewById(R.id.items);
            EditText curPrice = (EditText)mChilds.get(i).findViewById(R.id.price);

            final String nShop = shopName.getText().toString();
            final String nItem = curItem.getText().toString();
            final String price = curPrice.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddShop.this, "Item added", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddShop.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NAME, nShop);
                params.put(KEY_ITEM, nItem);
                params.put(KEY_PRICE, price);
                return params;
            }

        };
        requestQueue.add(stringRequest);
        }
    }
}
