package com.larisoft.krapa.billboard.admins;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.larisoft.krapa.billboard.MainActivity;
import com.larisoft.krapa.billboard.R;
import com.larisoft.krapa.billboard.login.login;

import java.util.HashMap;
import java.util.Map;

public class Add_user extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String sName = "name";
    public static final String sEmail = "email";
    public static final String sPassword = "password";
    public static final String sUserLevel = "user_level";
    public static final String sAssembly = "assembly";


    private static final String[] paths = {"Select Access Level", "Mobile Billboard Locator", "Admin"};
    Object UserAccessLevel;
    EditText name, email, password;
    Button register, login;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    String URL = "https://app.tedxafariwaa.com/LoginandRegistration/save.php";
    public String Spinner_Assembly_hold;
    Spinner spinner, spinner_assembly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.user_level);
        spinner_assembly = findViewById(R.id.user_assembly);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(Add_user.this,
                android.R.layout.simple_spinner_item, paths);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.assembly_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_assembly.setAdapter(staticAdapter);
        spinner.setAdapter(adapter);


        spinner_assembly.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);

        name = findViewById(R.id.editTextFullName);
        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);


        register = findViewById(R.id.btnRegister);
        login = findViewById(R.id.btnLogin);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(Add_user.this);

        register.setOnClickListener(Add_user.this);
        login.setOnClickListener(Add_user.this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admins, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_user) {
            Intent a = new Intent(Add_user.this, Add_user.class);
            startActivity(a);

        } else if (id == R.id.nav_view_user) {
            Intent v = new Intent(Add_user.this, View_users.class);
            startActivity(v);
        } else if (id == R.id.nav_view_billboard) {
            Intent v = new Intent(Add_user.this, View_billboards.class);
            startActivity(v);
        } else if (id == R.id.nav_share) {
            /* Intent s = new Intent(admins.this,admins.class);
             startActivity(s);*/
        } else if (id == R.id.nav_send) {

            /* Intent sd = new Intent(admins.this,admins.class);
             startActivity(sd);*/

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnRegister) {

            // Showing progress dialog at user registration time.
            progressDialog.setMessage("Registering....please Wait");
            progressDialog.show();

            final String username = name.getText().toString().trim();
            final String mail = email.getText().toString().trim();
            final String pswd = password.getText().toString().trim();
            final String spin_user_level = String.valueOf(UserAccessLevel);
            final String spin_assembly = Spinner_Assembly_hold.trim();

          //  Toast.makeText(this, spin_user_level, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, spin_assembly, Toast.LENGTH_SHORT).show();

            if (username.equals("") || pswd.equals("") || mail.equals("") || spin_user_level.equals("") || spin_assembly.equals("")) {
                Toast.makeText(Add_user.this, "All Fields are Compulsory", Toast.LENGTH_SHORT).show();
            } else {
                //instantiate request queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                //get string response using url
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Add_user.this, response, Toast.LENGTH_LONG).show();

                        if (response.equals("User Registration Successful")) {
                            openProfile();
                        }
                    }

                    private void openProfile() {
                       name.setText(null);
                       email.setText(null);
                       password.setText(null);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Add_user.this, error.toString(), Toast.LENGTH_LONG).show();
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
                        params.put(sUserLevel, spin_user_level);
                        params.put(sAssembly, spin_assembly);

                        return params;
                    }
                };

                //add request to request queue
                requestQueue.add(stringRequest);
            }

        } else if (v.getId() == R.id.btnLogin) {
            Intent mylogin = new Intent(Add_user.this, login.class);
            startActivity(mylogin);

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* Use the following switch-statement if you want to keep all spinner actions/callbacks together */
        /* The same can be done to the onNothingSelected callback */
        switch (parent.getId()) {
            case R.id.user_level:
               UserAccessLevel =  spinner.getItemAtPosition(position);

                break;
            case R.id.user_assembly:
                Spinner_Assembly_hold = spinner_assembly.getSelectedItem().toString();
                Log.d("user assembly", spinner.getSelectedItem().toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
