package stop.one.startup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class shop_products_list extends AppCompatActivity {

    public static String product_name;

    TextView text_view;
    RecyclerView product_recyclerview;

    public FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_products_list);

        Intent startingIntent = getIntent();
        product_name = startingIntent.getStringExtra("itemname");

        product_recyclerview=findViewById(R.id.product_recyclerview);
        text_view=findViewById(R.id.text_view);
        text_view.setText(product_name);


        product_recyclerview.setHasFixedSize(true);
        product_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        Query query = rootRef.collection("Category").document("Electronic")
                .collection("Shops").document(Shop_items.docId).collection("Items")
                .document(product_name).collection("Product");

        final FirestoreRecyclerOptions<product_list_holder> options = new FirestoreRecyclerOptions.Builder<product_list_holder>()
                .setQuery(query, product_list_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<product_list_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final product_list_holder model) {

                holder.item_name.setText(model.getName());
                holder.brand_name.setText(model.getName());
                holder.price.setText(model.getName());

                holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        String uiserId=user.getUid();

                        Map<String, Object> us = new HashMap<>();
                        us.put("prod name", model.getName());
                        us.put("prod brand", model.getName());
                        us.put("prod price", model.getName());
                        us.put("prod name", model.getName());
                    }
                });




                holder.product_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String itemname = getSnapshots().getSnapshot(position).getId();
                        Intent intent = new Intent(shop_products_list.this, product_detail.class);
                        intent.putExtra("prodname", itemname);
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


