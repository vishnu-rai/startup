package stop.one.startup;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class signuppage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    private static final String[] paths = {"choose", "start up", "technician"};

    final Calendar myCalendar = Calendar.getInstance();

    public int choosen_option, date, year, month;

    Button signup_btn;

    public final static int TAG_PERMISSION_CODE = 1;

    Boolean signupready = true;

    private FirebaseAuth mAuth;

    String passwordet, mainskillet, startupideafeildet, companynameet, emailet, phonenumberet, dobet, nameet, user_type, age;

    EditText password_et, mainskill_et, startupideafeild_et, companyname_et, email_et, phonenumber_et, dob_et, name_et;
    private String TAG = "signuppage";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog dialog;

    private double latitude = 0.0d, longitude = 0.0d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);

        signuppage.CheckPermission.requestPermission(this, TAG_PERMISSION_CODE);

        mAuth = FirebaseAuth.getInstance();

        password_et = findViewById(R.id.password_et);
        mainskill_et = findViewById(R.id.mainskill_et);
        startupideafeild_et = findViewById(R.id.startupideafeild_et);
        companyname_et = findViewById(R.id.companyname_et);
        email_et = findViewById(R.id.email_et);
        phonenumber_et = findViewById(R.id.phonenumber_et);
        dob_et = findViewById(R.id.dob_et);
        name_et = findViewById(R.id.name_et);
        signup_btn = findViewById(R.id.signup_btn);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loging in");
        dialog.setMessage("Loging. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);


        mainskill_et.setVisibility(View.GONE);
        startupideafeild_et.setVisibility(View.GONE);
        companyname_et.setVisibility(View.GONE);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob_et.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(signuppage.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        spinner = findViewById(R.id.dropdown_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(signuppage.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupready = true;
                user_type = "";
                onsignupclicked();
            }
        });
    }

    private void onsignupclicked() {

        Boolean loc = checklocation();

        nameet = name_et.getText().toString();
        passwordet = password_et.getText().toString();
        emailet = email_et.getText().toString();
        dobet = dob_et.getText().toString();
        phonenumberet = phonenumber_et.getText().toString();

        if (choosen_option == 1) {
            startupideafeildet = startupideafeild_et.getText().toString();
            companynameet = companyname_et.getText().toString();

        } else if (choosen_option == 2) {
            mainskillet = mainskill_et.getText().toString();
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            signupready = false;
            Toast.makeText(this, latitude + "Give this app location permission." + longitude, Toast.LENGTH_SHORT).show();
        } else if (!loc) {
            dialog.show();
            signupready = false;
            dialog.dismiss();
            buildAlertMessageNoGps();
            Toast.makeText(this, " hey ", Toast.LENGTH_LONG).show();
        } else if (loc) {
            dialog.show();
            LocationManager _manager_ = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location _location_ = _manager_.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = _location_.getLongitude();
            latitude = _location_.getLatitude();
            Toast.makeText(this, latitude + " hey " + longitude, Toast.LENGTH_LONG).show();
        }


        if (!isValidMail(emailet)) {
            signupready = false;
            email_et.setError("Not valid email");
            email_et.setText("");
        } else if (!isValidMobile(phonenumberet)) {
            signupready = false;
            phonenumber_et.setError("Not valid phone");
            phonenumber_et.setText("");
        } else if (nameet.isEmpty()) {
            signupready = false;
            name_et.setError("Empty");
        } else if (nameet.contains("")) {

        } else if (dobet.isEmpty()) {
            signupready = false;
            dob_et.setError("Empty");

        } else if (passwordet.isEmpty()) {
            signupready = false;
            password_et.setError("Empty");

        }

        switch (choosen_option) {
            case 0:

                user_type = "";

                mainskill_et.setVisibility(View.GONE);
                startupideafeild_et.setVisibility(View.GONE);
                companyname_et.setVisibility(View.GONE);
                mainskill_et.setText("");
                startupideafeild_et.setText("");
                companyname_et.setText("");

                signupready = false;
                ((TextView) spinner.getSelectedView()).setError("Choose one");
                break;

            case 1:

                if (companynameet.isEmpty()) {
                    signupready = false;
                    companyname_et.setError("Empty");
                    companyname_et.setText("");
                } else if (startupideafeildet.isEmpty()) {
                    signupready = false;
                    startupideafeild_et.setError("Empty");
                    startupideafeild_et.setText("");
                }

                user_type = "Entrepreneur";

                break;

            case 2:


                if (mainskillet.isEmpty()) {
                    signupready = false;
                    mainskill_et.setError("Empty");
                    mainskill_et.setText("");
                }

                user_type = "Technician";

                break;
        }

        // sigining up

        if (signupready == true) {
            mAuth.createUserWithEmailAndPassword(emailet, passwordet)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                age = getAge(year, month, date);

                                Map<String, Object> user = new HashMap<>();
                                user.put("Name", nameet);
                                user.put("Phone number", phonenumberet);
                                user.put("Profile Type", user_type);
                                user.put("Email id", emailet);
                                user.put("Age", age);
                                user.put("Latitude", String.valueOf(latitude));
                                user.put("Longitude", String.valueOf(longitude));


                                if (choosen_option == 1) {
                                    user.put("Company Name", companynameet);
                                    user.put("Startup Idea", startupideafeildet);
                                }
                                if (choosen_option == 2) {
                                    user.put("Main skill", mainskillet);
                                }


                                FirebaseUser cuser = mAuth.getCurrentUser();

                                FirebaseFirestore dbl = FirebaseFirestore.getInstance();

                                dbl.collection("Users").document(cuser.getUid())
                                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(signuppage.this, userprofile.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                        dialog.dismiss();
                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });
        } else {
            dialog.dismiss();
//            Toast.makeText(getApplicationContext(), "Not signed up", Toast.LENGTH_LONG).show();

        }

    }

    // validation of email
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    // validate phone number
    private boolean isValidMobile(String phone) {
        if (android.util.Patterns.PHONE.matcher(phone).matches() && phone.length() == 10)
            return true;
        else
            return false;
    }

    // Checking phone gps
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    // choosing date
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob_et.setText(sdf.format(myCalendar.getTime()));

        date = Integer.parseInt((sdf.format(myCalendar.getTime())).substring(0, 2));
        month = Integer.parseInt((sdf.format(myCalendar.getTime())).substring(3, 5));
        year = Integer.parseInt((sdf.format(myCalendar.getTime())).substring(6));

    }

    //first drop down
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i) {

            case 0:
                mainskill_et.setVisibility(View.GONE);
                startupideafeild_et.setVisibility(View.GONE);
                companyname_et.setVisibility(View.GONE);
                choosen_option = i;
                break;
            case 1:
                choosen_option = i;
                mainskill_et.setVisibility(View.GONE);
                startupideafeild_et.setVisibility(View.VISIBLE);
                companyname_et.setVisibility(View.VISIBLE);
                // Whatever you want to happen when the first item gets selected
                break;
            case 2:
                choosen_option = i;
                companyname_et.setVisibility(View.GONE);
                startupideafeild_et.setVisibility(View.GONE);
                mainskill_et.setVisibility(View.VISIBLE);

                // Whatever you want to happen when the second item gets selected
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        mainskill_et.setVisibility(View.GONE);
        companyname_et.setVisibility(View.GONE);
        startupideafeild_et.setVisibility(View.GONE);
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }




    public boolean checklocation() {

        LocationManager lm1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            Toast.makeText(this, latitude + "Give this app location permission." + longitude, Toast.LENGTH_LONG).show();
            return false;
        }
        for(int i=0;i<=1000000;i++);
        Location location_ = lm1.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location_ == null) {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            }
            return false;
        } else
            return true;
    }

    public static class CheckPermission {
        public static boolean checkPermission(Activity activity) {
            int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
            if (result == PackageManager.PERMISSION_GRANTED) {

                return true;

            } else {

                return false;

            }
        }

        public static void requestPermission(Activity activity, final int code) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, code);
            }
        }
    }

}
