package stop.one.startup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class shop_products_list extends AppCompatActivity {

    public static String item_type_name,prodid;

    TextView text_view;
    RecyclerView product_recyclerview;

    public FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_products_list);

        Intent startingIntent = getIntent();
        item_type_name = startingIntent.getStringExtra("item_type_name");

        product_recyclerview=findViewById(R.id.product_recyclerview);
        text_view=findViewById(R.id.text_view);
        text_view.setText(item_type_name);


        product_recyclerview.setHasFixedSize(true);
        product_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        Query query = rootRef.collection("Category").document(Shops_name.category_name)
                .collection("Shops").document(Shop_items.shop_Id).collection("Items")
                .document(item_type_name).collection("Product");

        final FirestoreRecyclerOptions<product_list_holder> options = new FirestoreRecyclerOptions.Builder<product_list_holder>()
                .setQuery(query, product_list_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<product_list_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final product_list_holder model) {

                holder.item_name.setText(model.getName());
                holder.brand_name.setText(model.getBrand());
                holder.price.setText(model.getPrice());

                holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        String uiserId=user.getUid();
                        prodid=getSnapshots().getSnapshot(position).getId();

                        Map<String, Object> us = new HashMap<>();
                        us.put("prod_name", model.getName());
                        us.put("prod_brand", model.getBrand());
                        us.put("prod_price", model.getPrice());
                        us.put("Category", Shops_name.category_name);
                        us.put("Shop_Id",Shop_items.shop_Id);
                        us.put("Item_name",item_type_name);
                        us.put("Prod_Id",prodid);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        db.collection("Users").document(uiserId).collection("Cart")
                                .add(us)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("tag", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(getApplicationContext(),"Added to cart",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("tag", "Error adding document", e);
                                        Toast.makeText(getApplicationContext(),"Failed to add",Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                });




                holder.product_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String prod_id = getSnapshots().getSnapshot(position).getId();
                        Intent intent = new Intent(shop_products_list.this, product_detail.class);
                        intent.putExtra("prod_id", prod_id);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_product_list, parent, false);
                return new RecyclerAdapter(view);
            }
        };
        product_recyclerview.setAdapter(adapter);
    }

    class RecyclerAdapter extends RecyclerView.ViewHolder {

        TextView item_name,brand_name,price,add_to_cart;
        LinearLayout product_layout;


        public RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            product_layout = itemView.findViewById(R.id.product_layout);
            brand_name = itemView.findViewById(R.id.brand_name);
            price = itemView.findViewById(R.id.price);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);



        }
    }

    protected void onStart() {

        if (adapter != null)
            adapter.startListening();
        super.onStart();

    }

    @Override
    protected void onStop() {
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }


    @Override
    protected void onPostResume() {
        if (adapter != null)
            adapter.startListening();
        super.onPostResume();
    }
}


