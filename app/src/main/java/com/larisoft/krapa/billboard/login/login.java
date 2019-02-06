package com.larisoft.krapa.billboard.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.larisoft.krapa.billboard.MainActivity;
import com.larisoft.krapa.billboard.R;
import com.larisoft.krapa.billboard.admins.admins;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements View.OnClickListener {
    public static final String dEmail = "email";
    public static final String dPassword = "password";
    // UI references.
    EditText mEmailView;
    EditText mPasswordView;
    Button mEmailSignInButton;
    TextView txt_forgot;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    String URL = "https://app.tedxafariwaa.com/LoginandRegistration/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.editText_Password);

        mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        txt_forgot = findViewById(R.id.txt_forgot);
        txt_forgot = findViewById(R.id.txt_forgot);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(login.this);
        mEmailSignInButton.setOnClickListener(login.this);
        txt_forgot.setOnClickListener(login.this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.email_sign_in_button) {


            final String mail = mEmailView.getText().toString().trim();
            final String password = mPasswordView.getText().toString().trim();

            if (password.equals("") || mail.equals("")) {


                Toast.makeText(login.this, "All Fields are Compulsory", Toast.LENGTH_SHORT).show();
            } else {

                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Logging In....please Wait");
                progressDialog.show();

                //get string response using url
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onResponse(String response) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        switch (response.trim()) {
                            case "1":
                                openProfile();
                                break;
                            case "2":
                                openProfile2();
                                break;
                            default:
                                Toast.makeText(login.this, response, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    private void openProfile() {
                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.putExtra(dEmail, mail);
                        startActivity(intent);
                        finish();
                    }

                    private void openProfile2() {
                        Intent intent = new Intent(login.this, admins.class);
                        intent.putExtra(dEmail, mail);
                        startActivity(intent);
                        finish();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Show timeout error message
                                Toast.makeText(login.this,
                                        "Check Internet Connection",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else if (error instanceof NetworkError) {
                            // Show timeout error message
                            Toast.makeText(login.this,
                                    "Oops. Network error!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            // Show timeout error message
                            Toast.makeText(login.this,
                                    "Oops. No Internet!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            // Show timeout error message
                            Toast.makeText(login.this,
                                    "Oops. Authentication error!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            // Show timeout error message
                            Toast.makeText(login.this,
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            // Show timeout error message
                            Toast.makeText(login.this,
                                    "Oops. Timeout error!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                })

                {
                    @Override
                    protected Map<String, String> getParams() {
                        //first parameter variable names, second parameter database column names
                        Map<String, String> params = new HashMap<>();
                        params.put(dEmail, mail);
                        params.put(dPassword, password);
                        return params;
                    }
                };
                //instantiate request queue
                RequestQueue requestQueue = Volley.newRequestQueue(login.this);

                //add request to request queue
                requestQueue.add(stringRequest);
            }


        } else if (v.getId() == R.id.txt_forgot) {
            Intent sign_up_Intent = new Intent(login.this, ForgotPassActivity.class);
            sign_up_Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(sign_up_Intent);
            finish();
        }
    }
}