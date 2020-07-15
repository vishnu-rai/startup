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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Shops_name extends AppCompatActivity {

    RecyclerView shopname_recyclerview;
    public FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_name);

        shopname_recyclerview = findViewById(R.id.shopname_recyclerview);
        shopname_recyclerview.setHasFixedSize(true);
        shopname_recyclerview.setLayoutManager(new GridLayoutManager(this, 2));

        Query query = rootRef.collection("Category").document("Electronic").collection("Shops");

        final FirestoreRecyclerOptions<shopname_holder> options = new FirestoreRecyclerOptions.Builder<shopname_holder>()
                .setQuery(query, shopname_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<shopname_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final shopname_holder model) {

                holder.shop_name.setText(model.getName());

                holder.shopname_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String docid=getSnapshots().getSnapshot(position).getId();
                        Intent intent = new Intent(Shops_name.this, Shop_items.class);
                        intent.putExtra("DocId", docid);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_shop_name, parent, false);
                return new RecyclerAdapter(view);
            }
        };
        shopname_recyclerview.setAdapter(adapter);
    }

    class RecyclerAdapter extends RecyclerView.ViewHolder
    {

        TextView shop_name;
        LinearLayout shopname_layout;


        public RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            shop_name=itemView.findViewById(R.id.shop_name);
            shopname_layout=itemView.findViewById(R.id.shopname_layout);



        }
    }
    protected void onStart() {

        if(adapter!=null)
            adapter.startListening();
        super.onStart();

    }

    @Override
    protected void onStop() {
        if(adapter!=null)
            adapter.stopListening();
        super.onStop();
    }


    @Override
    protected void onPostResume() {
        if(adapter!=null)
            adapter.startListening();
        super.onPostResume();
    }
}
