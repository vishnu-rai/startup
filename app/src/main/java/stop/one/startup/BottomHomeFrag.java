package stop.one.startup;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BottomHomeFrag extends Fragment {

    RecyclerView category_recyclerview, add_recyclerview, shop_recyclerview;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//    Category_adapter adapter;
    FirestoreRecyclerAdapter adapter;
    View rootView;


    public BottomHomeFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottom_home_frag, container, false);

        category_recyclerview = rootView.findViewById(R.id.category_recyclerview);

//        add_recyclerview = rootView.findViewById(R.id.add_recyclerview);
//        add_recyclerview.setHasFixedSize(true);
//        add_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
//
//        shop_recyclerview = rootView.findViewById(R.id.shop_recyclerview);
//        shop_recyclerview.setHasFixedSize(true);
//        shop_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        Query query = rootRef.collection("Category").orderBy("type", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Category_holder> options = new FirestoreRecyclerOptions.Builder<Category_holder>()
                .setQuery(query, Category_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Category_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final Category_holder model) {

                holder.category_textview.setText(model.getType());

                holder.card_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String docid=model.getType();
                        Intent intent = new Intent(getContext(), Shops_name.class);
                        intent.putExtra("Category name", docid);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getContext()).inflate(R.layout.card_layout_category, parent, false);
                return new RecyclerAdapter(view);
            }
        };

        Log.d("tagging", String.valueOf(adapter));
        category_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),4));
        category_recyclerview.setAdapter(adapter);

        return rootView;
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
        TextView category_textview;
        RelativeLayout card_layout;


        public RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            card_layout = itemView.findViewById(R.id.card_layout);
            category_textview = itemView.findViewById(R.id.category_textview);
        }
    }


}
