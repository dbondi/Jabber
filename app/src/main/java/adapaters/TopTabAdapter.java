package adapaters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jab.R;
import com.example.jab.ViewPager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import controllers.SearchController;
import controllers.SearchTabController;
import controllers.TopTabController;
import custom_class.TabInfo;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class TopTabAdapter extends RecyclerView.Adapter<TopTabAdapter.ViewHolder> {

    private FirebaseAuth auth;
    private static Bundle savedInstanceState;
    private Context context;
    private TopTabController controller;

    private List<TabInfo> elements = new ArrayList<>();

    public TopTabAdapter(List<TabInfo> items, Bundle savedInstanceState, Context context) {
        this.elements.addAll(items);
        this.savedInstanceState = savedInstanceState;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameOfTab;
        private ImageView cardBackground;

        public ViewHolder(View itemView, TopTabController controller) {
            super(itemView);
            // prepare your ViewHolder
            cardBackground = itemView.findViewById(R.id.top_background);
            nameOfTab = itemView.findViewById(R.id.name_of_top_tab);
        }


    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        controller = new TopTabController(auth,context);

        View view = layoutInflater.inflate(R.layout.layout_search_top, parent, false);
        return new ViewHolder(view,controller);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TabInfo item = elements.get(position);
        String id = item.getId();

        if(id.equals("100")){
            holder.cardBackground.setImageDrawable(context.getResources().getDrawable(R.drawable.pink_map));
            holder.nameOfTab.setText("Map");
        }

    }

}
