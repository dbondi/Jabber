package adapaters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.jab.GlideApp;
//import com.example.jab.GlideApp;
import com.airbnb.lottie.L;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.jab.CommentActivity;
import com.example.jab.CommentActivity;
import com.example.jab.GlideApp;
import com.example.jab.R;

import java.util.ArrayList;

import controllers.CommentController;
import custom_class.Comment;
import custom_class.Place;
import custom_class.UserProfile;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    ArrayList<Comment> comments = new ArrayList<>();
    Bundle bundle;
    Context context;
    boolean boolPhoto;
    CommentController controller;
    int width;

    private UserProfile user;
    private Place place;
    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();

    public CommentAdapter(Context context, Bundle bundle, CommentController controller,int width) {
        user = bundle.getParcelable("User");
        this.bundle = bundle;
        this.context = context;
        this.controller = controller;
        this.width = width;
    }

    private Boolean calculateLike(Comment currentChat) {

        String UID = user.getUID();
        return currentChat.getLikeList().contains(UID);
    }

    public void update(ArrayList<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }
    public void updateNew(ArrayList<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Comment currentChat = comments.get(position);
        double factor = ((double) currentChat.getImageHeight()) / ((double) currentChat.getImageWidth());

        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
        place = bundle.getParcelable("Place");

        holder.username.setText(currentChat.getUserName());

        ImageView postImageRef = holder.post_image;

        ImageView profPicRef = holder.profilePic;

        //holder.bottomComment.getLayoutParams().height = 0;

        //Integer widthScreen = currentChat.getImageWidth();
        Integer heightScreen = currentChat.getImageHeight();

        holder.backgroundColor.setCardBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));
        holder.card2.setCardBackgroundColor(Color.parseColor("#66"+currentChat.getColor().get(0).substring(1)));

        if (currentChat.getBoolImage()) {
            //postImageRef.getLayoutParams().height = ((int) (factor*widthScreen));
            //((ViewGroup) holder.messageView.getParent()).removeView(holder.message);
            holder.message.setVisibility(View.GONE);

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);


            GlideApp.with(context).asBitmap()
                    .load(currentChat.getGsReference())
                    .apply(requestOptions)
                    .into(postImageRef);


        } else if (currentChat.getGifBoolean()) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

            holder.message.setVisibility(View.GONE);

            GlideApp.with(context).asGif()
                    .load(currentChat.getGifUrl())
                    .apply(requestOptions)
                    .into(new ImageViewTarget<GifDrawable>(postImageRef) {


                        @Override
                        protected void setResource(@Nullable GifDrawable resource) {
                            //int widthImage = width/3;
                            //holder.backgroundColor.getLayoutParams().height = widthImage;

                            postImageRef.setImageDrawable(resource);
                            postImageRef.buildDrawingCache();
                        }
                    });

        } else if (currentChat.getStringBoolean()) {
            //holder.messageView.removeView(holder.post_image);
            String first = "<font color='" + currentChat.getColor().get(1) + "'>\" </font>";
            String next = "<font color='#FFFFFF'>" + currentChat.getContent() + "</font>";
            String last = "<font color='#AAFFFFFF'> \"</font>";

            holder.message.setText(Html.fromHtml(next));

        }

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);


        GlideApp.with(context)
                .load(currentChat.getProfPicReference())
                .apply(requestOptions)
                .into(profPicRef);

        //holder.bottomBox.set

        if (calculateLike(currentChat)) {
            holder.cbLike.toggle();
        }
        holder.likeNumber.setText(Integer.toString(currentChat.getLikeNumber()));

        holder.cbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //like

            }
        });

        holder.profilePic.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                if(currentChat.getUserUID().equals(user.getUID())){
                    controller.profileBtn(localUniversityPlaces,localCityPlaces,user,place);
                }
                else{
                    controller.userProfileBtn(localUniversityPlaces,localCityPlaces,user,place);
                }
            }
        });
        holder.username.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                if(currentChat.getUserUID().equals(user.getUID())){
                    controller.profileBtn(localUniversityPlaces,localCityPlaces,user,place);
                }
                else{
                    controller.userProfileBtn(localUniversityPlaces,localCityPlaces,user,place);
                }
            }
        });

        holder.messageView.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                holder.bottomComment.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,     LinearLayout.LayoutParams.WRAP_CONTENT));

            }
        });


    }



    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private de.hdodenhof.circleimageview.CircleImageView profilePic;
        private TextView username;
        private ImageView post_image;
        private CheckBox cbLike;
        private TextView likeNumber;
        private ConstraintLayout messageView;
        private CardView backgroundColor;
        private LinearLayout bottomComment;
        private CardView card2;

        //FirebaseStorage storage = FirebaseStorage.getInstance();

        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            post_image = itemView.findViewById(R.id.image);
            cbLike = itemView.findViewById(R.id.like_button);
            likeNumber = itemView.findViewById(R.id.like_number);
            messageView = itemView.findViewById(R.id.message_id);
            backgroundColor = itemView.findViewById(R.id.background_color);
            bottomComment = itemView.findViewById(R.id.bottomComment);
            card2 = itemView.findViewById(R.id.card2);
        }
    }

}
