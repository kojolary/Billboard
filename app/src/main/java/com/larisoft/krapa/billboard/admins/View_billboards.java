package com.larisoft.krapa.billboard.admins;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.larisoft.krapa.billboard.R;
import com.larisoft.krapa.billboard.mData.Product2;
import com.larisoft.krapa.billboard.mListView.ProductAdapter;
import com.larisoft.krapa.billboard.mListView.ProductAdapter2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class View_billboards extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String URL = "https://app.tedxafariwaa.com/LoginandRegistration/companiesView.php";

    //a list to store all the products
// Creating Progress dialog.
    ProgressDialog progressDialog;
    //a list to store all the products
    List<Product2> productList;
    //the recyclerview
    RecyclerView rV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_billboards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //getting the recyclerview from xml
        rV = findViewById(R.id.listRecycleView);
        rV.setHasFixedSize(true);
        rV.setLayoutManager(new LinearLayoutManager(this));

        //add ItemDecoration
        rV.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));

        //initializing the productlist
        productList = new ArrayList<>();



        //this method will fetch and parse json
        //to display it in recyclerview
        load_data_from_server();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void load_data_from_server() {

        //get string response using url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @SuppressLint("ShowToast")
            @Override
            public void onResponse(String response) {

                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);

                        //adding the product to product list
                        productList.add(new Product2(
                                product.getString("billboard_owner"),
                                product.getString("name_company"),
                                product.getString("telephone_company"),
                                product.getDouble("lat_company"),
                                product.getDouble("lon_company"),
                                product.getString("input_person"),
                                product.getString("image")
                        ));
                    }

                    //creating adapter object and setting it to recyclerview
                    ProductAdapter2 adapter = new ProductAdapter2(getApplication(), productList);

                    rV.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Hiding the progress dialog after all task complete.
                        // Show timeout error message
                        Toast.makeText(getApplication(),
                                error + "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();
                    }
                } else if (error instanceof NetworkError) {

                    // Show timeout error message
                    Toast.makeText(getApplication(),
                            "Oops. Network error!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    // Hiding the progress dialog after all task complete.
                    // Show timeout error message
                    Toast.makeText(getApplication(),
                            "Oops. Server error!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    // Show timeout error message
                    Toast.makeText(getApplication(),
                            "Oops. Authentication error!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    // Show timeout error message
                    Toast.makeText(getApplication(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    // Show timeout error message
                    Toast.makeText(getApplication(),
                            "Oops. No Network error!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    // Show timeout error message
                    Toast.makeText(getApplication(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //instantiate request queue
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplication()));

        //add request to request queue
        requestQueue.add(stringRequest);


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent a = new Intent(View_billboards.this,Add_user.class);
            startActivity(a);

        } else if (id == R.id.nav_view_user) {
            Intent v = new Intent(View_billboards.this,View_users.class);
            startActivity(v);
        } else if (id == R.id.nav_view_billboard) {
            Intent v = new Intent(View_billboards.this,View_billboards.class);
            startActivity(v);
        } else if (id == R.id.nav_share) {
            /* Intent s = new Intent(admins.this,admins.class);
             startActivity(s);*/
        } else if (id == R.id.nav_send) {

            /* Intent sd = new Intent(admins.this,admins.class);
             startActivity(sd);*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
