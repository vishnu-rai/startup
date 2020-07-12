package stop.one.startup;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerholder extends RecyclerView.ViewHolder {

    public TextView ideaDesc_tv,ideaData_tv;

    public recyclerholder(@NonNull View itemView) {
        super(itemView);

        ideaData_tv=itemView.findViewById(R.id.ideaData_tv);
        ideaDesc_tv=itemView.findViewById(R.id.ideaDesc_tv);
    }
}