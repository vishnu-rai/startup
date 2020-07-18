package stop.one.startup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class order_detail extends AppCompatActivity {

    TextView name,city,state,phone,address,order_date,delivery_status,payment_type,
            cancel_order,prod_name,prod_brand,prod_price,order_Id;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    String userid;
    FirebaseFirestore firebaseFirestore;
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        name=findViewById(R.id.name);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        order_date=findViewById(R.id.order_date);
        delivery_status=findViewById(R.id.delivery_status);
        payment_type=findViewById(R.id.payment_type);
        cancel_order=findViewById(R.id.cancel_order);
        prod_name=findViewById(R.id.prod_name);
        prod_brand=findViewById(R.id.prod_brand);
        prod_price=findViewById(R.id.prod_price);
        order_Id=findViewById(R.id.order_Id);

        firebaseFirestore=FirebaseFirestore.getInstance();
        userid=auth.getCurrentUser().getUid();

        Intent i=getIntent();
        order_id = i.getStringExtra("order_id");

        order_Id.setText(order_id);

        final DocumentReference db = firebaseFirestore.collection("Users").document(userid)
                .collection("Order").document(order_id);

        db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("tag", "DocumentSnapshot data: " + document.getData());
                        prod_name.setText(document.getString("prod_name"));
                        prod_brand.setText(document.getString("prod_brand"));
                        prod_price.setText(document.getString("prod_price"));
                        order_date.setText(document.getString("Order_date"));
                        name.setText(document.getString("name"));
                        phone.setText(document.getString("phone"));
                        state.setText(document.getString("state"));
                        address.setText(document.getString("address"));
                        city.setText(document.getString("city"));
                        if(document.getString("Delivered")=="0") {
                            delivery_status.setText("On it's way");
                        }
                        else{
                            delivery_status.setText("Delivered");
                        }
                        if(document.getString("Payment")=="pending")
                            payment_type.setText("COD");
                        else
                            payment_type.setText("Online payment");


                    } else {
                        Log.d("tag", "No such document");
                    }
                } else {
                    Log.d("tag", "get failed with ", task.getException());
                }
            }
        });


    }
}
