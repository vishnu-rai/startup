package stop.one.startup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class product_detail extends AppCompatActivity {

    TextView prod_name, prod_price, prod_brand, prod_desc, addtocard, ordernow;

    public FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    String prodname, prodprice, prodbrand, proddesc;
    String city, address, phone, state;
    String prod_id;


    Map<String, Object> u = new HashMap<String, Object>();
    Map<String, Object> us = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        prod_name = findViewById(R.id.prod_brand);
        prod_price = findViewById(R.id.prod_price);
        prod_brand = findViewById(R.id.prod_brand);
        prod_desc = findViewById(R.id.prod_desc);
        addtocard = findViewById(R.id.addtocard);
        ordernow = findViewById(R.id.ordernow);

        Intent startingIntent = getIntent();
        prod_id = startingIntent.getStringExtra("prod_id");


        final CollectionReference db = rootRef.collection("Category").document(Shops_name.category_name)
                .collection("Shops").document(Shop_items.shop_Id).collection("Items")
                .document(shop_products_list.item_type_name).collection("Product");

        final DocumentReference docRef = db.document(prod_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("tag", "DocumentSnapshot data: " + document.getData());
                        proddesc = (document.getString("Description"));
                        prodbrand = (document.getString("Brand"));
                        prodname = (document.getString("Name"));
                        prodprice = document.getString("Price");
                        prod_name.setText(prodname);
                        prod_brand.setText(prodbrand);
                        prod_desc.setText(proddesc);
                        prod_price.setText(prodprice);

                    } else {
                        Log.d("tag", "No such document");
                    }
                } else {
                    Log.d("tag", "get failed with ", task.getException());
                }
            }
        });
        addtocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uiserId = user.getUid();

                Map<String, Object> usa = new HashMap<>();

                usa.put("prod_name", prodname);
                usa.put("prod_brand", prodbrand);
                usa.put("prod_price", prodprice);
                usa.put("Category", Shops_name.category_name);
                usa.put("Shop_Id", Shop_items.shop_Id);
                usa.put("Item_name", shop_products_list.item_type_name);
                usa.put("Prod _Id", prod_id);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("Users").document(uiserId).collection("Cart")
                        .add(usa)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("tag", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("tag", "Error adding document", e);
                                Toast.makeText(getApplicationContext(), "Failed to add", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uiserId = user.getUid();

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String format = sdf.format(new Date());

                Random rand = new Random();
                int n = rand.nextInt(50);

                final String order_id = format + String.valueOf(n);


                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(new Date());


                us.put("prod_name", prodname);
                us.put("prod_brand", prodbrand);
                us.put("prod_price", prodprice);
                us.put("Category", Shops_name.category_name);
                us.put("Shop_Id", Shop_items.shop_Id);
                us.put("Item_name", shop_products_list.item_type_name);
                us.put("Prod_Id", prod_id);
                us.put("Order_date", formattedDate);
                us.put("Return", "0");
                us.put("Canceled", "0");
                us.put("Payment", "pending");
                us.put("Delivered", "0");
                final String[] city_ = new String[1];
                final String[] address_ = new String[1];
                final String[] phone_ = new String[1];
                final String[] state_ = new String[1];

                final FirebaseFirestore db = FirebaseFirestore.getInstance();

                final DocumentReference docref = db.collection("Users").document(uiserId);
                docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("tagging", "DocumentSnapshot data: " + document.getData());
                                city = document.getString("city");
                                address = (document.getString("address"));
                                phone = (document.getString("phone"));
                                state = document.getString("state");
                                city_[0] =city;
                                address_[0]=address;
                                state_[0]=state;
                                phone_[0]=phone;

                                u.put("city", city);
                                u.put("state", state);
                                u.put("phone", phone);
                                u.put("address", address);

                            } else {
                                Log.d("tag", "No such document");
                            }
                        } else {
                            Log.d("tag", "get failed with ", task.getException());
                        }
                    }
                });


                us.put("city", city_[0]);
                us.put("state", state_[0]);
                us.put("phone", phone_[0]);
                us.put("address", address_[0]);

                u.put("Return", "0");
                u.put("Cancelled", "0");
                u.put("Payment", "Pending");
                u.put("Order_date", formattedDate);
                u.put("Product_id", prod_id);
                u.put("Delivered", "0");


                db.collection("Users").document(uiserId)
                        .collection("Order").document(order_id)
                        .set(us).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Order not placed", Toast.LENGTH_LONG).show();


                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        db.collection("Shop").document(Shop_items.shop_Id)
                                .collection("Orders").document(order_id)
                                .set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Order placed", Toast.LENGTH_LONG).show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Order not placed", Toast.LENGTH_LONG).show();


                            }
                        });

                    }
                });

            }
        });

    }

}
