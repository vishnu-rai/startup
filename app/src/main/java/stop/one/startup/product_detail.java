package stop.one.startup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class product_detail extends AppCompatActivity {

    TextView prod_name,prod_price,prod_brand,prod_desc,addtocard,ordernow;

    public FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    String prodname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        prod_name=findViewById(R.id.prod_brand);
        prod_price=findViewById(R.id.prod_price);
        prod_brand=findViewById(R.id.prod_brand);
        prod_desc=findViewById(R.id.prod_desc);
        addtocard=findViewById(R.id.addtocard);
        ordernow=findViewById(R.id.ordernow);

        Intent startingIntent = getIntent();
        prodname = startingIntent.getStringExtra("prodname");


        CollectionReference db=rootRef.collection("Category").document("Electronic")
                .collection("Shops").document(Shop_items.docId).collection("Items")
                .document(shop_products_list.product_name).collection("Product");

        DocumentReference docRef = db.document(prodname);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("tag", "DocumentSnapshot data: " + document.getData());
                        prod_desc.setText(String.valueOf(document.getData()));
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
