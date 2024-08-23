package com.example.workshop_8_android;

// ModifyActivity
// by: Amit Shalev

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

public class ModifyActivity extends AppCompatActivity {

    EditText etModFName, etModLName, etModEmail, etModAddress,
            etModCity, etModProvince, etModPostal, etModCountry,
            etModHPhone, etModBPhone, etModPassword;
    Button btnSave;

    RequestQueue requestQueue;

    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        customer = (Customer) getIntent().getSerializableExtra("customer");
        etModFName = findViewById(R.id.etModFName);
        etModLName = findViewById(R.id.etModLName);
        etModEmail = findViewById(R.id.etModEmail);
        etModAddress = findViewById(R.id.etModAddress);
        etModCity = findViewById(R.id.etModCity);
        etModProvince = findViewById(R.id.etModProvince);
        etModPostal = findViewById(R.id.etModPostal);
        etModCountry = findViewById(R.id.etModCountry);
        etModHPhone = findViewById(R.id.etModHPhone);
        etModBPhone = findViewById(R.id.etModBPhone);
        etModPassword = findViewById(R.id.etModPassword);
        btnSave = findViewById(R.id.btnModSave);

        setCustomer();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = new Customer(0,
                        etModFName.getText().toString(),
                        etModLName.getText().toString(),
                        etModAddress.getText().toString(),
                        etModCity.getText().toString(),
                        etModProvince.getText().toString(),
                        etModPostal.getText().toString(),
                        etModCountry.getText().toString(),
                        etModHPhone.getText().toString(),
                        etModBPhone.getText().toString(),
                        etModEmail.getText().toString(),
                        etModPassword.getText().toString(), 0);
                Toast.makeText(ModifyActivity.this, "User information updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCustomer() {
        Executors.newSingleThreadExecutor().execute(new GETCustomer(customer.getCustomerId()));
    }


    public void cancelMod(View view) {
        finish();
    }

    class PostCustomer implements Runnable {
        private final Customer customer;

        public PostCustomer(Customer customer) {
            this.customer = customer;
        }

        @Override
        public void run() {
            //send JSON data to REST service
            String url = "http://192.168.1.101:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/postcustomer";
            JSONObject obj = new JSONObject();
            try {
                obj.put("customerId", customer.getCustomerId());
                obj.put("custFirstName", etModFName.getText());
                obj.put("custLastName", etModLName.getText());
                obj.put("custAddress", etModAddress.getText());
                obj.put("custCity", etModCity.getText());
                obj.put("custProv", etModProvince.getText());
                obj.put("custPostal", etModPostal.getText());
                obj.put("custCountry", etModCountry.getText());
                obj.put("custHomePhone", etModHPhone.getText());
                obj.put("custBusPhone", etModBPhone.getText());
                obj.put("custEmail", etModEmail.getText());
                obj.put("custPassword", etModPassword.getText());
                obj.put("agentId", 2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d("admin", "response=" + response);
                            VolleyLog.wtf(response.toString(), "utf-8");

                            //display result message
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("admin", "error=" + error);
                            VolleyLog.wtf(error.getMessage(), "utf-8");
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        }
    }

    class GETCustomer implements Runnable {
        private final int customerId;

        public GETCustomer(int customerId) {
            this.customerId = customerId;
        }

        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            StringBuffer buffer = new StringBuffer();
            String url = "http://10.0.0.18:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/getcustomer/" + customerId;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");

                    //convert JSON data from response string into an Agent
                    JSONObject cust = null;
                    try {
                        cust = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        assert cust != null;
                        etModFName.setText(cust.getString("custFirstName"));
                        etModLName.setText(cust.getString("custLastName"));
                        etModAddress.setText(cust.getString("custAddress"));
                        etModCity.setText(cust.getString("custCity"));
                        etModProvince.setText(cust.getString("custProv"));
                        etModPostal.setText(cust.getString("custPostal"));
                        etModCountry.setText(cust.getString("custCountry"));
                        etModHPhone.setText(cust.getString("custHomePhone"));
                        etModBPhone.setText(cust.getString("custBusPhone"));
                        etModEmail.setText(cust.getString("custEmail"));
                        etModPassword.setText(cust.getString("custPassword"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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