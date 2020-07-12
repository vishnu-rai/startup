package stop.one.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email_et,password_et;
    Button login_btn,signup_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_et=findViewById(R.id.email_et);
        password_et=findViewById(R.id.password_et);
        login_btn=findViewById(R.id.login_btn);
        signup_btn=findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,signuppage.class));

            }
        });


        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validate()) {

                    //Upload Data to the database

                    String user_email = email_et.getText().toString().trim();
                    String user_password = password_et.getText().toString().trim();


                    mAuth.signInWithEmailAndPassword(user_email, user_password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
//                                         Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        Intent intent=new Intent(Login.this,userprofile.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

//                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                        updateUI(null);
                                    }

                                }
                            });
                }

            }
        });
    }


    private Boolean validate(){

        Boolean result = false;

        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

        if (email.isEmpty() && password.isEmpty()){

            Toast.makeText(this,"Please enter all your details correctly", Toast.LENGTH_SHORT).show();

        }

        else {

            result = true;


        }

        return result;

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
//            Toast.makeText(Login.this, "Loged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login.this,userprofile.class));
        }

//        updateUI(currentUser);
    }

//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//if (user != null) {
//        // Name, email address, and profile photo Url
//        String name = user.getDisplayName();
//        String email = user.getEmail();
//        Uri photoUrl = user.getPhotoUrl();
//
//        // Check if user's email is verified
//        boolean emailVerified = user.isEmailVerified();
//
//        // The user's ID, unique to the Firebase project. Do NOT use this value to
//        // authenticate with your backend server, if you have one. Use
//        // FirebaseUser.getIdToken() instead.
//        String uid = user.getUid();
//    }
}
