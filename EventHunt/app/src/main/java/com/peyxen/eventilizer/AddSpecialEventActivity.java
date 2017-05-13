package com.peyxen.eventilizer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.clans.fab.FloatingActionButton;
import com.peyxen.eventilizer.Model.Shops;
import com.peyxen.eventilizer.util.Config;
import com.peyxen.eventilizer.util.ShopsData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Pci on 1/9/2016.
 */
public class AddSpecialEventActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddSpecialEventActivity";
    private static final String insertUrl = "http://" + Config.IP_ADDRESS + "/eventilizer/addEvent.php";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_JOINED = "joined";
    private static final String KEY_TYPE = "type";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_RATING = "rating";

    private DatePickerDialog datePickerDialog;
    private ProgressDialog progressDialog;
    private LinearLayout mainLayout;
    private FloatingActionButton addBtn;
    private ImageView calender;
    private CardView cardView;
    private EditText title, desc;
    private TextView date,sum;
    private Spinner sp_locations;
    private Toolbar mToolbar;
    private SimpleDateFormat dateFormatter;
    private Calendar cal;
    private int total;
    private String[] locations = {"Banepa", "Kathmandu", "Panauti", "Pokhara", "Dhulikhel", "Bhaktapur", "Ilam", "Biratnagar", "Birgunj", "Hetauda"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_specialevent);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_special);
        mToolbar.setTitle("Create Special Events");
        setSupportActionBar(mToolbar);

        calender = (ImageView) findViewById(R.id.imgCalender3);
        cardView = (CardView) findViewById(R.id.cv_special);
        title = (EditText) findViewById(R.id.title_special);
        desc = (EditText) findViewById(R.id.desc_special);
        date = (TextView) findViewById(R.id.date_special);
        sum = (TextView)findViewById(R.id.total);

        mainLayout = (LinearLayout) findViewById(R.id.event_main_layout);
        addBtn = (FloatingActionButton) findViewById(R.id.addItem);

        sp_locations = (Spinner) findViewById(R.id.sp_locations_special);
        sp_locations.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, locations));

        total = 0;
        desc.setMinHeight(300);
        cardView.setRadius(15);
        cardView.setCardElevation(0.9f);
        cardView.setCardBackgroundColor(R.color.my_accent);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View curChild = getLayoutInflater().inflate(R.layout.selected_items, null);
                AutoCompleteTextView autoTextView = (AutoCompleteTextView) curChild.findViewById(R.id.autoCompleteTextView);
                final TextView price = (TextView) curChild.findViewById(R.id.item_price);
                final EditText Qnt = (EditText) curChild.findViewById(R.id.qnt);
                autoTextView.setThreshold(0);

                final ArrayList<String> allItems = getItemList();
                autoTextView.setAdapter(new ArrayAdapter<String>(AddSpecialEventActivity.this, android.R.layout.simple_dropdown_item_1line, allItems));
                autoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = (String) adapterView.getItemAtPosition(i);
                        total += getPrice(item)*4;
                        price.setText(getPrice(item) + "");
                    }
                });
                mainLayout.addView(curChild);
                sum.setText(getTotal() + "");
                sum.setVisibility(View.VISIBLE);
            }
        });
        initializeDatePickerDialog();
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.setYearRange(2016, 2028);
                datePickerDialog.show(getSupportFragmentManager(), "datepicker");
            }
        });
    }

    public int getTotal() {
        return total;
    }

    public void initializeDatePickerDialog() {

        cal = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("MMM dd,yyyy", Locale.US);
        int curYear = cal.get(Calendar.YEAR);
        int curMon = cal.get(Calendar.MONTH);
        int curDay = cal.get(Calendar.DAY_OF_MONTH);
        date.setText(dateFormatter.format(cal.getTime()));
        datePickerDialog = DatePickerDialog.newInstance(this, curYear, curMon, curDay, false);
    }

    public void createSpecial(View view) {

        addEvent();

    }

    public void addEvent() {
        final String eTitle = title.getText().toString().trim();
        final String eDescription = desc.getText().toString().trim();
        final String eDate = date.getText().toString().trim();
        final String eJoined = "0";
        final String eType = "Special";
        final String eAmount = "0";
        final int eRating = 0;
        final String eLocation = sp_locations.getSelectedItem().toString();
        Intent intent = new Intent(this, MainActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("New Event: " + eTitle)
                .setStyle(new Notification.BigTextStyle().bigText(eDescription + "\n" + eDate + "\t" + eAmount + " Nrs"))
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        cal.set(year, month, day);
        date.setText(dateFormatter.format(cal.getTime()));
    }

    public ArrayList<String> getItemList() {
        ArrayList<String> mItems = new ArrayList<String>();
        ArrayList<Shops> allShops = ShopsData.shops;
        for (int i = 0; i < allShops.size(); i++) {
            mItems.add(allShops.get(i).getItem());
        }
        return mItems;
    }

    public int getPrice(String item) {
        int price = 0;
        for (int i = 0; i < ShopsData.shops.size(); i++) {
            if (item.equals(ShopsData.shops.get(i).getItem())) {
                price = ShopsData.shops.get(i).getPrice();
                break;
            }
        }
        return price;
    }
}
