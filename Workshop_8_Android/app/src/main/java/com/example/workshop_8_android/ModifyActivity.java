package com.example.workshop_8_android;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyActivity extends AppCompatActivity {

    EditText etModFName, etModLName, etModEmail, etModAddress,
            etModCity, etModProvince, etModPostal, etModCountry,
            etModHPhone, etModBPhone, etModPassword;
    Button btnSave;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        etModFName = findViewById(R.id.etModFName);
        etModLName = findViewById(R.id.etModLName);
        etModEmail = findViewById(R.id.etModEmail);
        etModAddress = findViewById(R.id.etModAddress);
        etModCity = findViewById(R.id.etModCity);
        etModProvince = findViewById(R.id.etModProvince);
        etModPostal = findViewById(R.id.etModPostal);
        etModHPhone = findViewById(R.id.etModHPhone);
        etModBPhone = findViewById(R.id.etModBPhone);
        etModPassword = findViewById(R.id.etModPassword);
        btnSave = findViewById(R.id.btnModSave);

        requestQueue = Volley.newRequestQueue(this);

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

//    class PutCustomer implements Runnable {
//        private Customer customer;
//        public PutCustomer(Customer customer) {this.customer = customer;}
//
//        @Override
//        public void run() {
//            String url = "http://10.243.5.15:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/postcustomer";
//            JSONObject jsonBody = new JSONObject();
//            try {
//                jsonBody.put("customerId", customer.getCustomerId());
//                jsonBody.put("custFirstName", customer.getCustFirstName());
//                jsonBody.put("custLastName", customer.getCustLastName());
//                jsonBody.put("custAddress", customer.getCustAddress());
//                jsonBody.put("custCity", customer.getCustCity());
//                jsonBody.put("custProv", customer.getCustProv());
//                jsonBody.put("custPostal", customer.getCustPostal());
//                jsonBody.put("custCountry", customer.getCustCountry());
//                jsonBody.put("custHomePhone", customer.getCustHomePhone());
//                jsonBody.put("custBusPhone", customer.getCustBusPhone());
//                jsonBody.put("custEmail", customer.getCustEmail());
//                jsonBody.put("custPassword", customer.getCustPassword());
//                jsonBody.put("agentId", customer.getAgentId());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//                            Log.d("admin", "response=" + jsonObject);
//                            VolleyLog.wtf(response.toString(), "utf-8");
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                        }
//                    }


    class PostAgent implements Runnable {
        private Customer customer;

        public PostAgent(Customer customer) {
            this.customer = customer;
        }

        @Override
        public void run() {
            //send JSON data to REST service
            String url = "http://10.243.5.15:8080/Workshop_7_REST-1.0-SNAPSHOT/api/customer/postcustomer";
            JSONObject obj = new JSONObject();
            try {
                obj.put("customerId", customer.getCustomerId());
                obj.put("custFirstName", customer.getCustFirstName());
                obj.put("custLastName", customer.getCustLastName());
                obj.put("custAddress", customer.getCustAddress());
                obj.put("custCity", customer.getCustCity());
                obj.put("custProv", customer.getCustProv());
                obj.put("custPostal", customer.getCustPostal());
                obj.put("custCountry", customer.getCustCountry());
                obj.put("custHomePhone", customer.getCustHomePhone());
                obj.put("custBusPhone", customer.getCustBusPhone());
                obj.put("custEmail", customer.getCustEmail());
                obj.put("custPassword", customer.getCustPassword());
                obj.put("agentId", customer.getAgentId());
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
}