package com.example.workshop_8_android;

import android.icu.util.Output;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    List<Customer> customers;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void asyncJson(View view) throws IOException {
        Executors.newSingleThreadExecutor().execute(new myRunnable());
    }

    public void Login(View view) throws IOException {
        // if(checkLogin())
    }

    public boolean checkLogin(String email, String password) {

        return false;
    }

    class myRunnable implements Runnable {
        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            // StringBuffer buffer = new StringBuffer();
//            String url = "https://" + serverIPAddress + ":8080/JSPFall2023Day4JPAExample2-1.0-SNAPSHOT/api/agent/getallagents";
            String url = "http://10.243.5.15:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/getallcustomers";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf("*************RESPONSE: " + response, "utf-8");
                    Log.d("harv", "response=" + response);

                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    // insert all customers from JSON into List
                    try {
                        JSONArray customerArray = new JSONArray(response);
                        for (int i=0; i<customerArray.length(); i++)
                        {
                            JSONObject cust = customerArray.getJSONObject(i);
                            customers.add(new Customer(
                                    cust.getInt("customerId"), cust.getString("custFirstName"),
                                    cust.getString("custLastName"), cust.getString("custAddress"),
                                    cust.getString("custCity"), cust.getString("custProv"),
                                    cust.getString("custPostal"), cust.getString("custCountry"),
                                    cust.getString("custHomePhone"), cust.getString("custBusPhone"),
                                    cust.getString("custEmail"), cust.getString("custPassword"),
                                    cust.getInt("agentId")));

                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                }
            });

            requestQueue.add(stringRequest);
        }
    }
}
