package adapaters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jab.R;
import com.example.jab.ViewPager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import controllers.SearchController;
import controllers.SearchTabController;
import custom_class.Place;
import custom_class.TabInfo;
import custom_class.UserProfile;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class SearchTabAdapter extends RecyclerView.Adapter<SearchTabAdapter.ViewHolder> {

    private FirebaseAuth auth;
    private static Bundle savedInstanceState;
    private Context context;
    private SearchTabController controller;


    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();
    private UserProfile user = null;

    private List<TabInfo> elements = new ArrayList<>();

    public SearchTabAdapter(List<TabInfo> items, Bundle savedInstanceState, Context context) {
        this.elements.addAll(items);
        this.savedInstanceState = savedInstanceState;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cardPicto;
        private LinearLayout cardMargins;
        private TextView nameOfTab;
        private ImageView cardBackground;
        private CardView cardView;

        public ViewHolder(View itemView, SearchTabController controller) {
            super(itemView);
            // prepare your ViewHolder
            cardBackground = itemView.findViewById(R.id.card_background);
            nameOfTab = itemView.findViewById(R.id.name_of_tab);
            cardMargins = itemView.findViewById(R.id.card_margins);
            cardPicto = itemView.findViewById(R.id.card_picto);
            cardView = itemView.findViewById(R.id.card_box);

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
        controller = new SearchTabController(auth,context);

        View view = layoutInflater.inflate(R.layout.layout_search_general_button, parent, false);
        return new ViewHolder(view,controller);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TabInfo item = elements.get(position);
        String id = item.getId();


        user = savedInstanceState.getParcelable("User");
        localUniversityPlaces = savedInstanceState.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = savedInstanceState.getParcelableArrayList("LocalCityPlaces");


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.goToTab(item,savedInstanceState,localUniversityPlaces,localCityPlaces,user);
            }
        });

        if(id.equals("100")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.purple_gradient));
            holder.nameOfTab.setText("Bars");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );

            params.setMargins(0, 0, (int) px, 0);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.bar_background));
            holder.cardPicto.getLayoutParams().width = (int) (px*10);
            holder.cardPicto.getLayoutParams().height = (int)(px*10);

        }
        else if(id.equals("101")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.pink_gradient));
            holder.nameOfTab.setText("Concerts");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, 0, 0);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.concert_people));
            holder.cardPicto.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.cardPicto.getLayoutParams().height = (int) (px*8);
        }
        else if(id.equals("102")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.teal_gradient));
            holder.nameOfTab.setText("Dance Clubs");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, (int) px, (int) px);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.concert));
            holder.cardPicto.getLayoutParams().width = (int) (px*7);
            holder.cardPicto.getLayoutParams().height = (int) (px*7);
        }
        else if(id.equals("103")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.blue_gradient));
            holder.nameOfTab.setText("Link");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, (int) px, (int) px);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.radar_1));
            holder.cardPicto.getLayoutParams().width = (int) (px*10);
            holder.cardPicto.getLayoutParams().height = (int) (px*10);

        }
        else if(id.equals("104")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.green_gradient));
            holder.nameOfTab.setText("Following");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, (int) px, (int) px);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.trophey));
            holder.cardPicto.getLayoutParams().width = (int) (px*6);
            holder.cardPicto.getLayoutParams().height = (int) (px*6);

        }
        else if(id.equals("105")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.red_gradient));
            holder.nameOfTab.setText("Happy Hour");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, (int) px, (int) px);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.beer));
            holder.cardPicto.getLayoutParams().width = (int) (px*5.5);
            holder.cardPicto.getLayoutParams().height = (int) (px*5.5);

        }
        else if(id.equals("106")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.orange_gradient));
            holder.nameOfTab.setText("Popular Chats");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, (int) px, (int) px);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.fire));
            holder.cardPicto.getLayoutParams().width = (int) (px*5.5);
            holder.cardPicto.getLayoutParams().height = (int) (px*6.5);

        }
        else if(id.equals("107")){
            holder.cardBackground.setBackground(context.getResources().getDrawable(R.drawable.brown_gradient));
            holder.nameOfTab.setText("Map");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)holder.cardMargins.getLayoutParams();
            float dip = 10f;
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, (int) px, (int) px);
            holder.cardMargins.setLayoutParams(params);
            holder.cardPicto.setBackground(context.getResources().getDrawable(R.drawable.maps));
            holder.cardPicto.getLayoutParams().width = (int) (px*8);
            holder.cardPicto.getLayoutParams().height = (int) (px*8);

        }

    }

}
