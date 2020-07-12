package stop.one.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class userprofile extends AppCompatActivity {

    Button edit_profile_btn,signout_btn;
    TextView user_name_tv,age_tv,email_tv,phoneno_tv,main_skill_tv,user_type_tv;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private String TAG="userprofile";

    public RecyclerView profilepage_project_recycler,profilepage_idea_recycler,profilepage_post_recycler;

    FirestoreRecyclerAdapter adapter,adapter1,adapter2;

    public FirebaseUser cuser;

    public FloatingActionButton fab_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        cuser = mAuth.getCurrentUser();


        user_name_tv=findViewById(R.id.user_name_tv);
        age_tv=findViewById(R.id.age_tv);
        email_tv=findViewById(R.id.email_tv);
        phoneno_tv=findViewById(R.id.phoneno_tv);
        main_skill_tv=findViewById(R.id.main_skill_tv);
        user_type_tv=findViewById(R.id.user_type_tv);

        signout_btn=findViewById(R.id.signout_btn);
        edit_profile_btn=findViewById(R.id.edit_profile_btn);

        fab_icon=findViewById(R.id.fab_icon);

        profilepage_project_recycler=findViewById(R.id.profilepage_project_recycler);
        profilepage_idea_recycler=findViewById(R.id.profilepage_idea_recycler);
        profilepage_post_recycler=findViewById(R.id.profilepage_post_recycler);

        profilepage_idea_recycler.setLayoutManager(new LinearLayoutManager(this));
        profilepage_project_recycler.setLayoutManager(new LinearLayoutManager(this));
        profilepage_post_recycler.setLayoutManager(new LinearLayoutManager(this));
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        FirebaseFirestore rootRef1 = FirebaseFirestore.getInstance();
        FirebaseFirestore rootRef2 = FirebaseFirestore.getInstance();

        Query query = rootRef.collection("Users").document(cuser.getUid()).collection("Ideas");

        Query query1 = rootRef1.collection("Users").document(cuser.getUid()).collection("Projects");

        Query query2 = rootRef2.collection("Users").document(cuser.getUid()).collection("Posts");


        final FirestoreRecyclerOptions<recycleritem> options = new FirestoreRecyclerOptions.Builder<recycleritem>()
                .setQuery(query, recycleritem.class)
                .build();

        final FirestoreRecyclerOptions<recycleritem> options1 = new FirestoreRecyclerOptions.Builder<recycleritem>()
                .setQuery(query1, recycleritem.class)
                .build();

        final FirestoreRecyclerOptions<recycleritem> options2 = new FirestoreRecyclerOptions.Builder<recycleritem>()
                .setQuery(query2, recycleritem.class)
                .build();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(cuser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        user_name_tv.setText(document.getString("Name"));
                        age_tv.setText(document.getString("Age"));
                        phoneno_tv.setText(document.getString("Phone number"));
                        user_type_tv.setText(document.getString("Profile Type"));
                        email_tv.setText(document.getString("Email id"));
                        main_skill_tv.setText(document.getString("Main skill"));

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Three adapter

        adapter= new FirestoreRecyclerAdapter<recycleritem,recyclerholder>(options) {

            @NonNull
            @Override
            public recyclerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(userprofile.this).inflate(R.layout.projectslayout, parent, false);
                return new recyclerholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final recyclerholder recyclerholder,final int i, @NonNull final recycleritem recycleritem)
            {

                recyclerholder.ideaData_tv.setText(recycleritem.getIdea_topic());
                recyclerholder.ideaDesc_tv.setText(recycleritem.getIdea_desc());

            }
        };

        profilepage_idea_recycler.setAdapter(adapter);

        adapter1= new FirestoreRecyclerAdapter<recycleritem,recyclerholder>(options1) {

            @NonNull
            @Override
            public recyclerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(userprofile.this).inflate(R.layout.projectslayout, parent, false);
                return new recyclerholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final recyclerholder recyclerholder, final int i, @NonNull final recycleritem recycleritem)
            {

                recyclerholder.ideaData_tv.setText(recycleritem.getProject_topic());
                recyclerholder.ideaDesc_tv.setText(recycleritem.getProject_desc());

            }
        };

        profilepage_project_recycler.setAdapter(adapter1);

        adapter2= new FirestoreRecyclerAdapter<recycleritem,recyclerholderpost>(options2) {

            @NonNull
            @Override
            public recyclerholderpost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(userprofile.this).inflate(R.layout.postlayout, parent, false);
                return new recyclerholderpost(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final recyclerholderpost recyclerholderpost,final int i, @NonNull final recycleritem recycleritem)
            {

                recyclerholderpost.ideaData_tv.setText(recycleritem.getPost_title());
                recyclerholderpost.ideaDesc_tv.setText(recycleritem.getPost_desc());
                recyclerholderpost.user_name_tv.setText(recycleritem.getUser_name());
            }
        };

        profilepage_post_recycler.setAdapter(adapter2);


        edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userprofile.this,editprofilepage.class));
            }
        });

        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(userprofile.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        fab_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(userprofile.this,addpost.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            onBackPressed();
        }
        if(adapter!=null)
            adapter.startListening();
        if(adapter1!=null)
            adapter1.startListening();
        if(adapter2!=null)
            adapter2.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(adapter!=null)
            adapter.stopListening();
        if(adapter1!=null)
            adapter1.stopListening();
        if(adapter2!=null)
            adapter2.stopListening();
        super.onStop();
    }


    @Override
    protected void onPostResume() {
        if(adapter!=null)
            adapter.startListening();
        if(adapter1!=null)
            adapter1.startListening();
        if(adapter2!=null)
            adapter2.startListening();
        super.onPostResume();
    }

    @Override
    public void onBackPressed() {

    }
}
