package stop.one.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addpost extends Activity {

    EditText post_title_et,post_desc_et;
    Button add_post_btn;
    String posttitleet="",postdescet="";

    public ProgressDialog dialog;
    public Map<String, Object> user = new HashMap<>();

    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser cuser = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Updating in");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        post_title_et=findViewById(R.id.post_title_et);
        post_desc_et=findViewById(R.id.post_desc_et);
        add_post_btn=findViewById(R.id.add_post_btn);

        add_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                addposts();
            }
        });

    }

    private void addposts() {

        posttitleet= post_title_et.getText().toString();
        postdescet= post_desc_et.getText().toString();

        if(posttitleet.isEmpty()||postdescet.isEmpty())
        {
            if(posttitleet.isEmpty()){
            post_title_et.setError("Empty");
            post_title_et.setText("");}

            if(postdescet.isEmpty()){
            post_title_et.setError("Empty");
            post_title_et.setText("");}
        }
        else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            final Long time=System.currentTimeMillis();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("Users").document(cuser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            user.put("Post_title", posttitleet);
                            user.put("Post_desc", postdescet);
                            user.put("User_id", cuser.getUid());
                            user.put("Post_time", String.valueOf(time));
                            user.put("User_name", document.getString("Name"));

                            FirebaseFirestore dbl = FirebaseFirestore.getInstance();

                            dbl.collection("Users").document(cuser.getUid()).collection("Posts").document()
                                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                    Intent intent=new Intent(addpost.this,userprofile.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Log.d("LOGGER", "No such document");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });


        }


    }
}
