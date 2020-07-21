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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BottomOrderFrag extends Fragment {

    RecyclerView order_recycler;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.bottom_order_frag,container, false);

        order_recycler=view.findViewById(R.id.order_recycler);

        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = rootRef.collection("Users").document(userId).collection("Order");

        FirestoreRecyclerOptions<order_holder> options = new FirestoreRecyclerOptions.Builder<order_holder>()
                .setQuery(query, order_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<order_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final order_holder model) {

                holder.item_name.setText(model.getProd_name());
                holder.brand_name.setText(model.getProd_brand());
                holder.price.setText("Price : "+model.getProd_price());
                if(model.getDelivered()=="0")
                {
                    holder.status.setText("On it's way");
                }
                else if(model.getDelivered()=="1")
                    holder.status.setText("Delivered");

                holder.product_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String order_id= getSnapshots().getSnapshot(position).getId();;
                        Intent intent = new Intent(getContext(), order_detail.class);
                        intent.putExtra("order_id", order_id);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.card_order_layout, parent, false);
                return new RecyclerAdapter(view);
            }
        };

        order_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        order_recycler.setAdapter(adapter);

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
        TextView item_name,brand_name,price,status;
        LinearLayout product_layout;


        public RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            brand_name = itemView.findViewById(R.id.brand_name);
            price = itemView.findViewById(R.id.price);
            status = itemView.findViewById(R.id.status);
            product_layout = itemView.findViewById(R.id.product_layout);

        }
    }
}
