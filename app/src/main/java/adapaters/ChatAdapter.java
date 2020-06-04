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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jab.GlideApp;
import com.example.jab.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import controllers.ChatController;
import custom_class.Chat;
import custom_class.Place;
import custom_class.PointMap;
import custom_class.User;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    ArrayList<Chat> chats = new ArrayList<>();
    Context context;
    int widthScreen;
    int heightScreen;
    ChatController controller;
    boolean load = true;
    int ydy = 0;



    private User user;

    public ChatAdapter(User user, Context context, int widthScreen, int heightScreen, ChatController controller){
        this.user = user;
        this.context = context;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        this.controller = controller;
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
        System.out.println("onCreateViewHolder:"+viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_after_school_chat, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        System.out.println("onBindViewHolder:"+position);

        Chat currentChat = chats.get(position);

        double factor = ((double)currentChat.getImageHeight()+20)/((double)currentChat.getImageWidth()+80.0);

        String first = "<font color='#04c5e3'>\" </font>";
        String next = "<font color='#FFFFFF'>"+currentChat.getContent().toUpperCase()+"</font>";
        String last = "<font color='#04c5e3'> \"</font>";

        holder.message.setText(Html.fromHtml(first+next+last));

        //holder.profileBox.getLayoutParams().height = ((int) (heightScreen*.06));

        //holder.bottomBox.getLayoutParams().height = ((int) (heightScreen*.06));
        holder.username.setText(currentChat.getProfileName());

        ImageView postImageRef = holder.post_image;

        ImageView profPicRef = holder.profilePic;

        postImageRef.getLayoutParams().height = ((int) (factor*widthScreen));

        holder.profilePic.getLayoutParams().height = ((int) (heightScreen*.03));
        holder.bottomBox.getLayoutParams().height = ((int) (heightScreen*.06));


        //holder.post_image.setImageBitmap(currentChat.getImageBitmap());

        GlideApp.with(context)
                .load(currentChat.getGsReference())
                .centerCrop()
                .into(postImageRef);


        GlideApp.with(context)
                .load(currentChat.getProfPicReference())
                .centerCrop()
                .into(profPicRef);




        //holder.bottomBox.set


        if(calculateLike(currentChat)) {
            if(!holder.cbLike.isChecked()) {
                holder.cbLike.toggle();
            }
        }
        holder.likeNumber.setText(Integer.toString(currentChat.getLikeNumber()));
        holder.commentNumber.setText(Integer.toString(currentChat.getCommentNumber()));

        holder.cbComment.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                controller.commentSection(currentChat.getPlace(), currentChat.getMessageID(), user);
            }
        });


    }


    @Override
    public int getItemCount() {
        return chats.size();
    }

    //TODO
    public void connectView(RecyclerView  chatView) {
        if (chatView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) chatView
                    .getLayoutManager();


            chatView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    ydy = ydy + dy;
                    //System.out.println(ydy);

                    Integer height = recyclerView.getHeight();
                    Integer left_over = ydy % height;
                    Integer opposite = height - left_over;
                    linearLayoutManager.getItemCount();

                    if(opposite.equals(height)){
                        Integer lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                        Chat currentChat = chats.get(lastPosition);


                        double factor = ((double)currentChat.getImageHeight())/((double)currentChat.getImageWidth());

                        int heightImage = ((int) (factor*widthScreen));

                        Integer hidden_height = recyclerView.getHeight()/2;
                        Integer extra_space = hidden_height - heightImage;

                        //update
                        chatView.post(new Runnable() {
                            public void run() {
                                ((ChatAdapter.ViewHolder) (chatView.findViewHolderForAdapterPosition(lastPosition))).expansion.getLayoutParams().height = extra_space;
                                notifyItemChanged(lastPosition);
                            }
                        });
                    }
                    else {
                        System.out.println(height);
                        System.out.println(opposite);
                        //Subtract
                        Integer lastPosition = linearLayoutManager.findLastVisibleItemPosition() - 1;
                        Chat currentChat = chats.get(lastPosition);


                        double factor = ((double) currentChat.getImageHeight()) / ((double) currentChat.getImageWidth());

                        int heightImage = ((int) (factor * widthScreen));

                        Integer hidden_height = recyclerView.getHeight() / 2;
                        Integer extra_space = hidden_height - heightImage;

                        if (height > opposite && (height - 200) < opposite) {

                            //update
                            chatView.post(new Runnable() {
                                public void run() {
                                    ((ViewHolder) (chatView.findViewHolderForAdapterPosition(lastPosition))).expansion.getLayoutParams().height = extra_space;
                                    notifyItemChanged(lastPosition);
                                }
                            });
                        }
                        if ((height - 200) >= opposite && (height - 200 - heightImage) < opposite) {
                            chatView.post(new Runnable() {
                                public void run() {
                                    ((ViewHolder) (chatView.findViewHolderForAdapterPosition(lastPosition))).expansion.getLayoutParams().height = extra_space + ((height - 200)-opposite);
                                    notifyItemChanged(lastPosition);
                                }
                            });
                        }
                        if ((height - 200 - heightImage) >= opposite) {
                        }
                    }

                }
            });
        }
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
        private LinearLayout expansion;
        private int x;
        private int y;

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
            expansion = itemView.findViewById(R.id.expansion);

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
