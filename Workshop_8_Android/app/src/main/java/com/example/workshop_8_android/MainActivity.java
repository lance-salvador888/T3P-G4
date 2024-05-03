package com.example.workshop_8_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    List<Customer> customers = new ArrayList<Customer>();
    EditText etEmail, etPassword;
    ExecutorService RefreshThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RefreshThread = Executors.newSingleThreadExecutor();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    public void asyncJson(View view) throws IOException {
        RefreshThread.execute(new GETCustomers());
    }

    public void SignIn(View view) throws IOException, InterruptedException {
        if (etEmail != null && etPassword != null) {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            Log.d("LOGINCHECKER", email + " " + password);
            if (LoginExists(email, password)) {
                Toast.makeText(getApplicationContext(), "Login successful.", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Invalid credentials. Please try again.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter credentials before trying to sign in.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean LoginExists(String email, String password) throws InterruptedException {
        String existingEmail, existingPass;

        RefreshAgents();

        RefreshThread.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        Log.d("LOGINCHECKER", "customers.size=" + customers.size());
        for(int i = 0; i < customers.size(); i++){
            existingEmail = customers.get(i).getCustEmail();
            existingPass = customers.get(i).getCustPassword();
            Log.d("LOGINCHECKER",
                    "checking email=" + existingEmail +
                         " password=" + existingPass);
            if(email.equals(existingEmail) && password.equals(existingPass)){
                return true;
            }
        }
        return false;
    }

    class GETCustomers implements Runnable {
        @Override
        public void run() {
            String url = "http://10.243.5.15:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/getallcustomers";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // VolleyLog.wtf("*************RESPONSE: " + response, "utf-8");
                    Log.d("CustomerGET", "response=" + response);
                    // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    try {
                        StoreJSONCustomers(response);
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

    // insert all current customers from JSON response into an ArrayList
    public void StoreJSONCustomers(String response) throws JSONException {
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
            Log.d("lanc :)", "added to customers: ID" + customers.get(i).getCustomerId());

        }
        Log.d("lanc :)", "all customers added successfully, size= " + customers.size());
    }
    // Rebuilds ArrayList from latest REST service data
    public void RefreshAgents(){
        Executors.newSingleThreadExecutor().execute(new GETCustomers());
    }
}
