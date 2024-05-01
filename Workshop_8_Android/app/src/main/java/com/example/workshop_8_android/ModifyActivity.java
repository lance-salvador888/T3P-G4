package com.example.workshop_8_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ModifyActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etAddress, etCity, etProvince, etPostal, etHomePhone, etBusinessPhone, etPassword;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        // Initialize EditText fields
        etFirstName = findViewById(R.id.etModFName);
        etLastName = findViewById(R.id.etModLName);
        etEmail = findViewById(R.id.etModEmail);
        etAddress = findViewById(R.id.etModAddress);
        etCity = findViewById(R.id.etModCity);
        etProvince = findViewById(R.id.etModProvince);
        etPostal = findViewById(R.id.etModPostal);
        etHomePhone = findViewById(R.id.etModHPhone);
        etBusinessPhone = findViewById(R.id.etModBPhone);
        etPassword = findViewById(R.id.etModPassword);

        // Initialize Save button
        btnSave = findViewById(R.id.btnModSave);

        // TODO: Retrieve current user information and populate EditText fields

        // Set onClickListener for Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve information from EditText fields
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String address = etAddress.getText().toString();
                String city = etCity.getText().toString();
                String province = etProvince.getText().toString();
                String postal = etPostal.getText().toString();
                String homePhone = etHomePhone.getText().toString();
                String businessPhone = etBusinessPhone.getText().toString();
                String password = etPassword.getText().toString();

                // TODO: Update user information in your data storage

                // Show success message
                Toast.makeText(ModifyActivity.this, "User information updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}