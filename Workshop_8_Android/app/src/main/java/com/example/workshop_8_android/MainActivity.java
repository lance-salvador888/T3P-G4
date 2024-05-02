package com.example.workshop_8_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidquery.AQuery; // I figured out how to load dependencies from a .jar file !  !  !!  !!! -Lance
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void asyncJson() {

//perform a Google search in just a few lines of code
        AQuery aq = new AQuery(getApplicationContext());

        String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {


                if (json != null) {

                    //successful ajax call, show status code and json content
                    Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();

                } else {

                    //ajax error, show error code
                    Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void async_post(){

//do a twiiter search with a http post
        AQuery aq = new AQuery(getApplicationContext());
        String url = "http://search.twitter.com/search.json";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("q", "androidquery");

        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                System.out.println(json);

            }
        });
    }

    public void asyncJson(View view) {
        AQuery aq = new AQuery(getApplicationContext());

        String url = "http://localhost:8080/Day7RESTJPA-1.0-SNAPSHOT/api/gog/CAprovinces";

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {


                if (json != null) {

                    //successful ajax call, show status code and json content
                    Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();

                } else {

                    //ajax error, show error code
                    Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
