package adapaters;

import com.example.jab.R;

import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import custom_class.SearchRow;
import custom_class.SearchTab;

public class SearchColumnAdapter extends RecyclerView.Adapter<SearchColumnAdapter.ViewHolder> {
    private ArrayList<SearchRow> columnTabs;

    public SearchColumnAdapter(ArrayList<SearchRow> columnTabs){
        this.columnTabs = columnTabs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_search_tab, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void update(ArrayList<SearchRow> columnTabs){
        this.columnTabs = columnTabs;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchColumnAdapter.ViewHolder holder, int position) {
        SearchRow columnTab = columnTabs.get(position);
        SearchTab leftSearchTab = columnTab.getLeftTab();
        SearchTab rightSearchTab = columnTab.getRightTab();
        if (leftSearchTab != null){
            holder.leftText.setText(leftSearchTab.getName());
        }
        if (rightSearchTab != null){
            holder.rightText.setText(leftSearchTab.getName());
        }


    }

    @Override
    public int getItemCount() {
        return columnTabs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText leftText;
        private EditText rightText;
        private CardView leftCard;
        private CardView rightCard;
        public ViewHolder(View itemView) {
            super(itemView);
            leftText = itemView.findViewById(R.id.leftSearchTabText);
            rightText = itemView.findViewById(R.id.rightSearchTabText);

            leftCard = itemView.findViewById(R.id.leftSearchTab);
            rightCard = itemView.findViewById(R.id.rightSearchTab);

            leftCard.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){

                }
            });
            rightCard.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){

                }
            });

        }
    }
}
