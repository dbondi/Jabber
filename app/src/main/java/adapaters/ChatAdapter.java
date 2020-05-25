package adapaters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.jab.GlideApp;
import com.example.jab.GlideApp;
import com.example.jab.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import controllers.ChatController;
import custom_class.Chat;
import custom_class.PointMap;
import custom_class.User;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    ArrayList<Chat> chats = new ArrayList<>();
    Context context;
    int widthScreen;
    int heightScreen;
    ChatController controller;

    //locationData
    private String cityLocation = null;
    private ArrayList<PointMap> cityCoordinates = null;
    private String cityLocationKey = null;

    //localData
    private String localLocation = null;
    private ArrayList<PointMap> localCoordinates = null;
    private String localLocationKey = null;


    private User user;

    public ChatAdapter(User user, Context context, int widthScreen, int heightScreen, ChatController controller, String cityLocation, ArrayList<PointMap> cityCoordinates, String cityLocationKey, String localLocation, ArrayList<PointMap> localCoordinates, String localLocationKey){
        this.user = user;
        this.context = context;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        this.controller = controller;
        this.cityLocation = cityLocation;
        this.cityCoordinates = cityCoordinates;
        this.cityLocationKey = cityLocationKey;
        this.localLocation = localLocation;
        this.localCoordinates = localCoordinates;
        this.localLocationKey = localLocationKey;
    }

    private Boolean calculateLike(Chat currentChat) {

        String UID = user.getUID();
        return currentChat.getLikeList().contains(UID);
    }

    public void update(ArrayList<Chat> chats){
        this.chats = chats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_after_school_chat, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat currentChat = chats.get(position);

        double factor = ((double)currentChat.getImageHeight())/((double)currentChat.getImageWidth());

        String first = "<font color='#AAE91E63'>\" </font>";
        String next = "<font color='#000000'>"+currentChat.getContent().toUpperCase()+"</font>";
        String last = "<font color='#AAE91E63'> \"</font>";

        holder.message.setText(Html.fromHtml(first+next+last));

        //holder.profileBox.getLayoutParams().height = ((int) (heightScreen*.06));

        //holder.bottomBox.getLayoutParams().height = ((int) (heightScreen*.06));
        holder.username.setText(currentChat.getProfileName());

        ImageView postImageRef = holder.post_image;

        ImageView profPicRef = holder.profilePic;

        postImageRef.getLayoutParams().height = ((int) (factor*widthScreen));

        //holder.photoBox.getLayoutParams().height = ((int) (factor*widthScreen));
        //holder.photoBox.setBackground(context.getResources().getDrawable(R.drawable.background_top));
        holder.profilePic.getLayoutParams().height = ((int) (heightScreen*.03));
        holder.bottomBox.getLayoutParams().height = ((int) (heightScreen*.06));


        //holder.post_image.setImageBitmap(currentChat.getImageBitmap());
        GlideApp.with(context)
                .load(currentChat.getGsReference())
                .centerCrop()
                .into(postImageRef);


        GlideApp.with(context)
                .load(currentChat.getProfPicReference())
                .into(profPicRef);


        //holder.bottomBox.set


        if(calculateLike(currentChat)) {
            holder.cbLike.toggle();
        }
        holder.likeNumber.setText(Integer.toString(currentChat.getLikeNumber()));
        holder.commentNumber.setText(Integer.toString(currentChat.getCommentNumber()));

        holder.cbComment.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                controller.commentSection(cityLocation, cityCoordinates, cityLocationKey, localLocation, localCoordinates, localLocationKey, currentChat.getMessageID(), user);
            }
        });


    }



    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private de.hdodenhof.circleimageview.CircleImageView profilePic;
        private TextView username;
        private ImageView post_image;
        private CheckBox cbLike;
        private CheckBox cbComment;
        private TextView likeNumber;
        private TextView commentNumber;

        private LinearLayout profileBox;
        private LinearLayout bottomBox;
        private LinearLayout photoBox;
        FirebaseStorage storage = FirebaseStorage.getInstance();

        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            post_image = itemView.findViewById(R.id.post_image);
            cbLike = itemView.findViewById(R.id.like_button);
            cbComment = itemView.findViewById(R.id.comment_button);
            likeNumber = itemView.findViewById(R.id.like_number);
            commentNumber = itemView.findViewById(R.id.comment_number);

            bottomBox = itemView.findViewById(R.id.bottomBox);
            bottomBox = itemView.findViewById(R.id.bottomBox);
            photoBox = itemView.findViewById(R.id.box_image);


            cbLike.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    //like

                }
            });
        }
    }

}
