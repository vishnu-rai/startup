package stop.one.startup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Shop_items extends AppCompatActivity {

    public static String docId;
    RecyclerView shopitems_recyclerview;
    public FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_items);

        Intent startingIntent = getIntent();
//        if (startingIntent != null)
        docId = startingIntent.getStringExtra("DocId");
        Toast.makeText(getApplicationContext(), docId, Toast.LENGTH_LONG).show();

        shopitems_recyclerview = findViewById(R.id.shopitems_recyclerview);
        shopitems_recyclerview.setHasFixedSize(true);
        shopitems_recyclerview.setLayoutManager(new GridLayoutManager(this, 4));

        Query query = rootRef.collection("Category").document("Electronic")
                .collection("Shops").document(docId).collection("Items");

        final FirestoreRecyclerOptions<shopitems_holder> options = new FirestoreRecyclerOptions.Builder<shopitems_holder>()
                .setQuery(query, shopitems_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<shopitems_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final shopitems_holder model) {

                holder.item_name.setText(model.getName());

                holder.shopitem_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String itemname = getSnapshots().getSnapshot(position).getId();
                        Intent intent = new Intent(Shop_items.this, shop_products_list.class);
                        intent.putExtra("itemname", itemname);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_items_layout, parent, false);
                return new RecyclerAdapter(view);
            }
        };
        shopitems_recyclerview.setAdapter(adapter);
    }

    class RecyclerAdapter extends RecyclerView.ViewHolder {

        TextView item_name;
        LinearLayout shopitem_layout;


        public RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            shopitem_layout = itemView.findViewById(R.id.shopitem_layout);


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

