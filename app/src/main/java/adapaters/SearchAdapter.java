package adapaters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonElement;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.BubbleLayout;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.SearchController;
import custom_class.MapTab;
import custom_class.SearchRow;
import custom_class.SearchTab;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_MAP = 0;
    private static final int ITEM_TYPE_COLUMN = 1;
    private FirebaseAuth auth;
    private Bundle savedInstanceState;
    private Context context;
    private SearchController controller;

    private static final String GEOJSON_SOURCE_ID = "GEOJSON_SOURCE_ID";
    private static final String MARKER_IMAGE_ID = "MARKER_IMAGE_ID";
    private static final String CALLOUT_IMAGE_ID = "CALLOUT_IMAGE_ID";
    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";


    private List<Object> elements = new ArrayList<>();

    public SearchAdapter(List<Object> items, Bundle savedInstanceState, Context context) {
        this.elements.addAll(items);
        this.savedInstanceState = savedInstanceState;
        this.context = context;
    }

    private static class RowHolder extends RecyclerView.ViewHolder {
        private TextView leftText;
        private TextView rightText;
        private CardView leftCard;
        private CardView rightCard;
        private SearchRow searchRow;
        public RowHolder(View itemView, SearchController controller) {
            super(itemView);
            // prepare your ViewHolder
            leftText = itemView.findViewById(R.id.leftSearchTabText);
            rightText = itemView.findViewById(R.id.rightSearchTabText);

            leftCard = itemView.findViewById(R.id.leftSearchTab);
            rightCard = itemView.findViewById(R.id.rightSearchTab);

            leftCard.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    controller.goToTab(searchRow.getLeftTab());
                }
            });
            rightCard.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    controller.goToTab(searchRow.getRightTab());
                }
            });

        }

        public void bind(SearchRow searchRow) {
            // display your object
            this.searchRow = searchRow;
        }
    }

    private static class MapHolder extends RecyclerView.ViewHolder  {


        Button btn;

        public void bind(MapTab mapTab) {
            // display your object
        }




        public MapHolder(View itemView, SearchController controller) {
            super(itemView);
            btn = itemView.findViewById(R.id.mapView);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    controller.goToMap();
                }
            });
        }



    }

    @Override
    public int getItemViewType(int position) {
        if (elements.get(position) instanceof MapTab) {
            return ITEM_TYPE_MAP;
        } else {
            return ITEM_TYPE_COLUMN;
        }
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        controller = new SearchController(auth,context);
        if (viewType == ITEM_TYPE_MAP) {
            View view = layoutInflater.inflate(R.layout.activity_map_tabular, parent, false);

            return new MapHolder(view,controller);
        } else {
            View view = layoutInflater.inflate(R.layout.activity_single_search_tab, parent, false);

            return new RowHolder(view,controller);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Object item = elements.get(position);

        if (viewHolder instanceof MapHolder) {
            ((MapHolder) viewHolder).bind((MapTab) item);
        } else {
            ((RowHolder) viewHolder).bind((SearchRow) item);
            SearchRow rowTab = (SearchRow) item;
            SearchTab leftSearchTab = rowTab.getLeftTab();
            SearchTab rightSearchTab = rowTab.getRightTab();
            if (leftSearchTab != null){
                ((RowHolder)viewHolder).leftText.setText(leftSearchTab.getName());
            }
            if (rightSearchTab != null){
                ((RowHolder)viewHolder).rightText.setText(rightSearchTab.getName());
            }
        }
    }

}
