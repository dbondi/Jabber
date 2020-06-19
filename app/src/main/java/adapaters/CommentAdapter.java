package adapaters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.jab.GlideApp;
//import com.example.jab.GlideApp;
import com.bumptech.glide.request.RequestOptions;
import com.example.jab.GlideApp;
import com.example.jab.R;

import java.util.ArrayList;

import custom_class.Comment;
import custom_class.UserProfile;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    ArrayList<Comment> comments = new ArrayList<>();
    UserProfile user;
    Context context;
    boolean boolPhoto;

    public CommentAdapter(UserProfile user, Context context){
        this.user = user;
        this.context = context;
    }

    private Boolean calculateLike(Comment currentChat) {

        String UID = user.getUID();
        return currentChat.getLikeList().contains(UID);
    }

    public void update(ArrayList<Comment> comments){
        this.comments = comments;
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
        Comment currentChat = comments.get(position);
         double factor = ((double) currentChat.getImageHeight()) / ((double) currentChat.getImageWidth());

        holder.username.setText(currentChat.getUserName());

        ImageView postImageRef = holder.post_image;

        ImageView profPicRef = holder.profilePic;

        //Integer widthScreen = currentChat.getImageWidth();
        Integer heightScreen = currentChat.getImageHeight();

        holder.backgroundColor.setCardBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));

        if (currentChat.getBoolImage()) {
            //postImageRef.getLayoutParams().height = ((int) (factor*widthScreen));
            //((ViewGroup) holder.messageView.getParent()).removeView(holder.message);
            holder.message.setHeight(0);

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            GlideApp.with(context)
                    .load(currentChat.getGsReference())
                    .apply(requestOptions)
                    .into(postImageRef);


        } else if (currentChat.getGifBoolean()) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

            holder.message.setHeight(0);

            GlideApp.with(context)
                    .load(currentChat.getGifUrl())
                    .apply(requestOptions)
                    .into(postImageRef);

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



        }
    }

}
