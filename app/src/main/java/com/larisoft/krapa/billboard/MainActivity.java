package com.larisoft.krapa.billboard;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String sName = "name_company";
    public static final String sTelephone = "telephone_company";
    public static final String sLat = "lat_company";
    public static final String sLon = "lon_company";
    public static final String sInputPerson = "input_person";
    public static final String sBillboard_owner = "billboard_owner";


    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    final int CODE_GALLERy_REQUEST = 999;
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    EditText name, telephone, owner;
    TextView latDisplay, lonDisplay, location;
    // Google client to interact with Google API
    Button save;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    String URL = "https://app.tedxafariwaa.com/LoginandRegistration/companies.php";
    double latitude;
    double longitude;
    String TA1 = "TAG";
    String mLatitudeLabel = "Latitude";
    String mLongitudeLabel = "Longitude";
    Button btnChoose;
    ImageView imageUpload;
    Bitmap bitmap;
    String image;
    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.name);
        telephone = findViewById(R.id.editText_Phone);
        owner = findViewById(R.id.owner_name);
        latDisplay = findViewById(R.id.btn_latitude);
        lonDisplay = findViewById(R.id.btn_longitude);


        save = findViewById(R.id.save_billboard_loc);
        location = findViewById(R.id.get_location);
        // Assigning Activity this to progress dialog.

        btnChoose = findViewById(R.id.chooseImage);
        imageUpload = findViewById(R.id.imageViewDisplay);


        progressDialog = new ProgressDialog(MainActivity.this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        save.setOnClickListener(this);
        location.setOnClickListener(this);
        btnChoose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.save_billboard_loc) {

            // Showing progress dialog at user registration time.
            progressDialog.setMessage("Save in progress....please Wait");
            progressDialog.show();

            //here should correspond with database columns below
            final String name_company = name.getText().toString().trim();
            final String telephone_company = telephone.getText().toString().trim();
            final String billboard_owner = owner.getText().toString().trim();
            final String lat_company = String.valueOf(latitude);
            final String lon_company = String.valueOf(longitude);
            final String uploadedImage = image = getStringImage(bitmap);
            ;
            Log.i(TA1, "onClick: Got here");

            Intent getMyIntent = getIntent();
            final String Email = getMyIntent.getStringExtra("email");
            Log.i(TA1, "onClick: Got email" + Email);

            if (billboard_owner.equals("") || name_company.equals("") || telephone_company.equals("") || lat_company.equals("") || lon_company.equals("")) {
                Toast.makeText(MainActivity.this, "All Fields are Compulsory", Toast.LENGTH_SHORT).show();
            } else {
                //instantiate request queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                //get string response using url
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TA1, "onClick: Got here" + response);
                        if (response.equals("2")) {
                            openProfile();
                        } else if (response.equals("1")) {
                            Toast.makeText(MainActivity.this, "Location Already Exist, Please move to Another Location", Toast.LENGTH_SHORT).show();

                        } else if (response.equals("3")) {
                            Toast.makeText(MainActivity.this, "Could not insert data", Toast.LENGTH_SHORT).show();

                        } else if (response.equals("4")) {
                            Toast.makeText(MainActivity.this, "Check Database connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                    private void openProfile() {
                        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                )

                {
                    @Override
                    protected Map<String, String> getParams() {

                        //first parameter variable names, second parameter database column names
                        Map<String, String> params = new HashMap<>();
                        params.put(sBillboard_owner, billboard_owner);
                        params.put(sName, name_company);
                        params.put(sTelephone, telephone_company);
                        params.put(sLat, lat_company);
                        params.put(sLon, lon_company);
                        params.put(sInputPerson, Email);
                        params.put("image", uploadedImage);

                        return params;
                    }
                };

                //add request to request queue
                requestQueue.add(stringRequest);
            }

        } else if (v.getId() == R.id.get_location) {


            if (!checkPermissions()) {
                requestPermissions();
            } else {
                getLastLocation();
            }

        } else if (v.getId() == R.id.chooseImage) {

            //selectImage();
           /* ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODE_GALLERy_REQUEST);*/

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 999);


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

/*
    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }*/

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            latDisplay.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLatitudeLabel,
                                    mLastLocation.getLatitude()));

                            latitude = mLastLocation.getLatitude();
                            lonDisplay.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLongitudeLabel,
                                    mLastLocation.getLongitude()));
                            longitude = mLastLocation.getLongitude();
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.main_activity_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.imageViewDisplay);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


        }

    }

    public String getStringImage(Bitmap bm) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }

}
