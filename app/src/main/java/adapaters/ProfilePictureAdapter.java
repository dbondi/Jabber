package adapaters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.jab.GlideApp;
import com.example.jab.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import controllers.SearchController;
import custom_class.Comment;
import custom_class.SearchRow;

public class ProfilePictureAdapter extends RecyclerView.Adapter<ProfilePictureAdapter.ViewHolder>{

    private List<String> pic = new ArrayList<>();
    private Context context;
    private FirebaseStorage storage;

    public ProfilePictureAdapter(List<String> items, Context context) {
        pic = items;
        this.context = context;
        storage = FirebaseStorage.getInstance();
     }

    @NonNull
    @Override
    public ProfilePictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_picture, parent, false);
        final ProfilePictureAdapter.ViewHolder holder = new ProfilePictureAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public int getItemCount() {
        return pic.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePictureAdapter.ViewHolder holder, int position) {
        StorageReference picRef = storage.getReferenceFromUrl(pic.get(position));

        ImageView postImageRef = (ImageView) holder.imageView;


        GlideApp.with(context)
                .load(picRef)
                .into(postImageRef);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pic);
        }
    }
}
