package stop.one.startup;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Category_adapter extends FirestoreRecyclerAdapter<Category_holder, Category_adapter.Viewholder> {


    public Category_adapter(@NonNull FirestoreRecyclerOptions<Category_holder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull Category_holder model) {
        holder.category_textview.setText(model.getType());
        Log.d("check_22",model.getType());
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_category, parent,false);
        return new Viewholder(v);
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView category_textview;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
//            textViewPedido = itemView.findViewById(R.id.txt_pedidoview);
            category_textview= itemView.findViewById(R.id.category_textview);
        }
    }
}