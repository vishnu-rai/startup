package stop.one.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editprofilepage extends AppCompatActivity {

    TextView edit_profile_tv,add_idea_tv,add_project_tv;
    EditText edit_profile_et,new_phoneno_et,idea_topic_et,idea_desc_et_,project_name_et,project_detail_et;

    Button update_btn;

    int choosen_option=1;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    String editprofileet,newphonenoet,ideadescet,ideatopicet,projectnameet,projectdetailet;

    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilepage);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Updating in");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);


        edit_profile_tv=findViewById(R.id.add_profile_tv);
        add_idea_tv=findViewById(R.id.add_idea_tv);
        add_project_tv=findViewById(R.id.add_project_tv);
        new_phoneno_et=findViewById(R.id.new_phoneno_et);
        edit_profile_et=findViewById(R.id.edit_profile_et);
        idea_topic_et=findViewById(R.id.idea_topic_et);
        idea_desc_et_=findViewById(R.id.idea_desc_et_);
        project_name_et=findViewById(R.id.project_name_et);
        project_detail_et=findViewById(R.id.project_detail_et);
        update_btn=findViewById(R.id.update_btn);


        idea_topic_et.setVisibility(View.GONE);
        idea_desc_et_.setVisibility(View.GONE);
        project_detail_et.setVisibility(View.GONE);
        project_name_et.setVisibility(View.GONE);


        edit_profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new_phoneno_et.setVisibility(View.VISIBLE);
                edit_profile_et.setVisibility(View.VISIBLE);
                idea_topic_et.setVisibility(View.GONE);
                idea_desc_et_.setVisibility(View.GONE);
                project_detail_et.setVisibility(View.GONE);
                project_name_et.setVisibility(View.GONE);

                idea_topic_et.setText("");
                idea_desc_et_.setText("");
                project_name_et.setText("");
                project_detail_et.setText("");

                choosen_option=1;

                editprofileet="";
                newphonenoet="";
                ideadescet="";
                ideatopicet="";
                projectnameet="";
                projectdetailet="";

            }
        });

        add_idea_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                idea_topic_et.setVisibility(View.VISIBLE);
                idea_desc_et_.setVisibility(View.VISIBLE);
                new_phoneno_et.setVisibility(View.GONE);
                edit_profile_et.setVisibility(View.GONE);
                project_name_et.setVisibility(View.GONE);
                project_detail_et.setVisibility(View.GONE);

                edit_profile_et.setText("");
                new_phoneno_et.setText("");
                project_name_et.setText("");
                project_detail_et.setText("");
                idea_topic_et.setText("");
                idea_desc_et_.setText("");

                choosen_option=2;

                editprofileet="";
                newphonenoet="";
                ideadescet="";
                ideatopicet="";
                projectnameet="";
                projectdetailet="";

            }
        });

        add_project_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                project_name_et.setVisibility(View.VISIBLE);
                project_detail_et.setVisibility(View.VISIBLE);
                edit_profile_et.setVisibility(View.GONE);
                new_phoneno_et.setVisibility(View.GONE);
                idea_desc_et_.setVisibility(View.GONE);
                idea_topic_et.setVisibility(View.GONE);

                new_phoneno_et.setText("");
                edit_profile_et.setText("");
                project_name_et.setText("");
                project_detail_et.setText("");
                idea_desc_et_.setText("");
                idea_topic_et.setText("");

                choosen_option=3;

                editprofileet="";
                newphonenoet="";
                ideadescet="";
                ideatopicet="";
                projectnameet="";
                projectdetailet="";

            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                updatecontent();
            }
        });
    }

    private void updatecontent() {

        if(choosen_option==1)
        {
            editprofiledetail();
        }
        else  if(choosen_option==2)
        {
            addidea();
        }
        else  if(choosen_option==3)
        {
            addproject();
        }
    }

    private void editprofiledetail() {

        editprofileet=edit_profile_et.getText().toString();
        newphonenoet=new_phoneno_et.getText().toString();

        Map<String, Object> user = new HashMap<>();

        if(editprofileet.isEmpty()&&newphonenoet.isEmpty())
        {

            dialog.dismiss();
            if(editprofileet.isEmpty()) {
                edit_profile_et.setError("Empty");
                edit_profile_et.setText("");
            }
            if(newphonenoet.isEmpty()) {
                edit_profile_et.setError("Empty");
                edit_profile_et.setText("");
            }
        }

        else
        {

            if(!editprofileet.isEmpty())
            user.put("Age", editprofileet);
            if(!newphonenoet.isEmpty())
            user.put("Phone number", newphonenoet);

            FirebaseUser cuser = mAuth.getCurrentUser();

            FirebaseFirestore dbl = FirebaseFirestore.getInstance();

            dbl.collection("Users").document(cuser.getUid())
                    .update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dialog.dismiss();;
                    Intent intent=new Intent(editprofilepage.this,userprofile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void addidea() {


        ideatopicet=idea_topic_et.getText().toString();
        ideadescet=idea_desc_et_.getText().toString();

        Map<String, Object> user = new HashMap<>();

        if(ideatopicet.isEmpty()||ideadescet.isEmpty())
        {

            dialog.dismiss();
            if(ideatopicet.isEmpty()) {
                idea_topic_et.setError("Empty");
                idea_topic_et.setText("");
            }
            if(ideadescet.isEmpty()) {
                idea_desc_et_.setError("Empty");
                idea_desc_et_.setText("");
            }
        }

        else
        {

            user.put("Idea_topic", ideatopicet);
            user.put("Idea_desc", ideadescet);

            FirebaseUser cuser = mAuth.getCurrentUser();

            FirebaseFirestore dbl = FirebaseFirestore.getInstance();

            dbl.collection("Users").document(cuser.getUid()).collection("Ideas").document(ideatopicet)
                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dialog.dismiss();;
                    Intent intent=new Intent(editprofilepage.this,userprofile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void addproject() {


        projectnameet=project_name_et.getText().toString();
        projectdetailet=project_detail_et.getText().toString();

        Map<String, Object> user = new HashMap<>();

        if(projectnameet.isEmpty()||projectdetailet.isEmpty())
        {

            dialog.dismiss();
            if(projectnameet.isEmpty()) {
                project_name_et.setError("Empty");
                project_name_et.setText("");
            }
            if(projectdetailet.isEmpty()) {
                project_detail_et.setError("Empty");
                project_detail_et.setText("");
            }
        }

        else
        {

            user.put("Project_topic", projectnameet);
            user.put("Project_desc", projectdetailet);

            FirebaseUser cuser = mAuth.getCurrentUser();

            FirebaseFirestore dbl = FirebaseFirestore.getInstance();

            dbl.collection("Users").document(cuser.getUid()).collection("Projects").document(projectnameet)
                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dialog.dismiss();
                    Intent intent=new Intent(editprofilepage.this,userprofile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        }

    }
}
