package com.peyxen.eventilizer;

/**
 * Created by Pci on 1/1/2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.peyxen.eventilizer.util.Config;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private static final String insertUrl = "http://" + Config.IP_ADDRESS + "/eventilizer/signup.php";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        _signupButton.setAlpha((float) 0.7);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(SignUpActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                registerUser();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }


    private void registerUser() {
        final String names = _nameText.getText().toString().trim();

        final String passwords = _passwordText.getText().toString().trim();

        final String emails = _emailText.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NAME, names);
                params.put(KEY_PASSWORD, passwords);
                params.put(KEY_EMAIL, emails);
                return params;
            }

        };

        RequestQueue requestQueue = Singleton.getInstance().getmRequestQueue();
        requestQueue.add(stringRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
