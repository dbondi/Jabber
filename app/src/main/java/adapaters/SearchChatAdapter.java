package adapaters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jab.ChatActivity;
import com.example.jab.R;
import com.example.jab.ViewPager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import controllers.SearchChatController;
import controllers.SearchController;
import controllers.SearchTabController;
import custom_class.Place;
import custom_class.SearchTab;
import custom_class.TabInfo;
import custom_class.UserProfile;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class SearchChatAdapter extends RecyclerView.Adapter<SearchChatAdapter.ViewHolder> {

    private FirebaseAuth auth;
    private static Bundle savedInstanceState;
    private Context context;
    private SearchChatController controller;


    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();
    private UserProfile user = null;

    private List<SearchTab> elements = new ArrayList<>();

    public SearchChatAdapter(List<SearchTab> items, Bundle savedInstanceState, Context context) {
        this.elements.addAll(items);
        this.savedInstanceState = savedInstanceState;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameOfTab;
        private CheckBox checkBox;
        private LinearLayout chatBackground;

        public ViewHolder(View itemView, SearchChatController controller) {
            super(itemView);
            // prepare your ViewHolder
            nameOfTab = itemView.findViewById(R.id.chat_name);
            checkBox = itemView.findViewById(R.id.add_button);
            chatBackground = itemView.findViewById(R.id.chat_search_gradient);

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
        controller = new SearchChatController(auth,context);

        View view = layoutInflater.inflate(R.layout.layout_chat_search, parent, false);
        return new ViewHolder(view,controller);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchTab item = elements.get(position);

        if(item.getType().equals("UniversityTab")){
            holder.nameOfTab.setText("\uD83C\uDF93 "+ item.getName());
            holder.chatBackground.setBackground(context.getResources().getDrawable(R.drawable.chat_school_search));
        }
        else if(item.getType().equals("CityTab")){
            holder.nameOfTab.setText("\uD83C\uDF03 "+ item.getName());
            holder.chatBackground.setBackground(context.getResources().getDrawable(R.drawable.chat_city_search));
        }


        user = savedInstanceState.getParcelable("User");
        localUniversityPlaces = savedInstanceState.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = savedInstanceState.getParcelableArrayList("LocalCityPlaces");


        holder.chatBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.goToTab(item,savedInstanceState,item.getPlace());
            }
        });
    }

}
