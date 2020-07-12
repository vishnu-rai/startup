package stop.one.startup;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerholderpost extends RecyclerView.ViewHolder {

    public TextView ideaDesc_tv,ideaData_tv,user_name_tv;

    public recyclerholderpost(@NonNull View itemView) {
        super(itemView);

        ideaData_tv=itemView.findViewById(R.id.ideaData_tv);
        ideaDesc_tv=itemView.findViewById(R.id.ideaDesc_tv);
        user_name_tv=itemView.findViewById(R.id.user_name_tv);
    }
}