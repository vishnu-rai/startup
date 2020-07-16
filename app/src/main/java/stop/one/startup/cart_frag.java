package stop.one.startup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class cart_frag extends Fragment {

    TextView total_price, check_out;
    RecyclerView cart_recycler;

    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;

    String userid;
    int ttl_price;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cart, container, false);

        total_price = view.findViewById(R.id.total_price);
        check_out = view.findViewById(R.id.check_out);
        cart_recycler = view.findViewById(R.id.cart_recycler);

        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        calculate_price();

        Query query = rootRef.collection("Users").document(userid).collection("Cart");

        FirestoreRecyclerOptions<cart_holder> options = new FirestoreRecyclerOptions.Builder<cart_holder>()
                .setQuery(query, cart_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<cart_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final cart_holder model) {

                holder.item_name.setText(model.getProd_name());
                holder.brand_name.setText(model.getProd_brand());
                holder.price.setText("Price : " + model.getProd_price());

                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String docId = getSnapshots().getSnapshot(position).getId();
                        rootRef.collection("Users").document(userid)
                                .collection("Cart").document(docId)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                notifyItemRemoved(position);
                                calculate_price();

                            }
                        });


                    }
                });

            }

            @NonNull
            @Override
            public RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.card_cart_layout, parent, false);
                return new RecyclerAdapter(view);
            }
        };

        cart_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        cart_recycler.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        {
            adapter.stopListening();

        }
    }

    public class RecyclerAdapter extends RecyclerView.ViewHolder {
        TextView item_name, brand_name, price, remove;

        public RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            brand_name = itemView.findViewById(R.id.brand_name);
            remove = itemView.findViewById(R.id.remove);
            price = itemView.findViewById(R.id.price);

        }
    }

    public void calculate_price()
    {

        final int[] price = {0};



        rootRef.collection("Users").document(userid).collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("tag", document.getId() + " => " + document.getData());
                                price[0] = price[0] + Integer.parseInt(document.getString("prod_price"));
                            }
                            total_price.setText("Rs. "+price[0]);
                        } else {
                            Log.w("tag", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
