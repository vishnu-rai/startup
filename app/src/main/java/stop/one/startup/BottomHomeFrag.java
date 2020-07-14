package stop.one.startup;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BottomHomeFrag extends Fragment {

    RecyclerView category_recyclerview, add_recyclerview, shop_recyclerview;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    Category_adapter adapter;
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

        rec();


        return rootView;
    }

    public void rec() {
        Query query = rootRef.collection("Category").orderBy("type", Query.Direction.ASCENDING);


//        Toast.makeText(getActivity(),"toast", Toast.LENGTH_LONG).show();
        FirestoreRecyclerOptions<Category_holder> options = new FirestoreRecyclerOptions.Builder<Category_holder>()
                .setQuery(query, Category_holder.class)
                .build();

        adapter = new Category_adapter(options);
        category_recyclerview.setHasFixedSize(true);
        category_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        category_recyclerview.setAdapter(adapter);

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


}
