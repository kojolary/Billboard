package com.larisoft.krapa.billboard.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.larisoft.krapa.billboard.MainActivity;
import com.larisoft.krapa.billboard.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String sName = "name";
    public static final String sEmail = "email";
    public static final String sPassword = "password";
    EditText name, email, password;
    Button register, login;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    String URL = "https://app.tedxafariwaa.com/LoginandRegistration/save.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editTextFullName);
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);

        register = findViewById(R.id.btnRegister);
        login = findViewById(R.id.btnLogin);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(RegisterActivity.this);

        register.setOnClickListener(RegisterActivity.this);
        login.setOnClickListener(RegisterActivity.this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {

            // Showing progress dialog at user registration time.
            progressDialog.setMessage("Registration in progress....please Wait");
            progressDialog.show();

            final String username = name.getText().toString().trim();
            final String mail = email.getText().toString().trim();
            final String pswd = password.getText().toString().trim();

            if (username.equals("") || pswd.equals("") || mail.equals("")) {
                Toast.makeText(RegisterActivity.this, "All Fields are Compulsory", Toast.LENGTH_SHORT).show();
            } else {
                //instantiate request queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                //get string response using url
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();

                        if (response.equals("User Registration Successfully")) {
                            openProfile();
                        }
                    }

                    private void openProfile() {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra(name.getText().toString().trim(), email.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                )

                {
                    @Override
                    protected Map<String, String> getParams() {

                        //first parameter variable names, second parameter database column names
                        Map<String, String> params = new HashMap<>();
                        params.put(sName, username);
                        params.put(sEmail, mail);
                        params.put(sPassword, pswd);

                        return params;
                    }
                };

                //add request to request queue
                requestQueue.add(stringRequest);
            }

        } else if (v.getId() == R.id.btnLogin) {
            Intent mylogin = new Intent(this, login.class);
            startActivity(mylogin);

        }

    }


}
