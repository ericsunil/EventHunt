package com.peyxen.eventilizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.peyxen.eventilizer.util.EventsData;

public class WelcomeActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance("Public Events", "Don't Miss any public events like Jatras,Concert happening in anywhere in Nepal.", R.drawable.public_event, Color.BLUE));
        addSlide(AppIntroFragment.newInstance("Paid Events", "Join club party,dance party by paying fix amount as per the organizer within eventhunt", R.drawable.paid, Color.BLACK));
        addSlide(AppIntroFragment.newInstance("Special Events", "It's your best friend Happy Birthday!!!! Join the awesome party!!", R.drawable.birthday, Color.GRAY));
        addSlide(AppIntroFragment.newInstance("Start Bussiness", "Advertise your shop and Sell your product by registering inside Eventhunt!!", R.drawable.business, Color.parseColor("#008000")));
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {
        new EventsData(WelcomeActivity.this);
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onDonePressed() {
        new EventsData(WelcomeActivity.this);
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
           }
}
