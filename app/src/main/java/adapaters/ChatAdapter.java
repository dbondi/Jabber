package adapaters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jab.R;

import java.util.ArrayList;

import custom_class.Chat;
import custom_class.SearchRow;
import custom_class.User;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    ArrayList<Chat> chats = new ArrayList<>();
    User user;
    ArrayList<String> likeUIDList;
    Boolean didYouLike;

    public ChatAdapter(User user){
        this.user = user;
        didYouLike = calculateLike();
    }

    private Boolean calculateLike() {
        String UID = user.getUID();
        return likeUIDList.contains(UID);
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

        holder.message.setText(currentChat.getContent());
        holder.profilePic.setImageBitmap(currentChat.getProfilePicBitmap());
        holder.username.setText(currentChat.getProfileName());
        holder.post_image.setImageBitmap(currentChat.getImageBitmap());
        if(didYouLike) {
            holder.cbLike.toggle();
        }
        holder.likeNumber.setText(currentChat.getLikeNumber());
        holder.commentNumber.setText(currentChat.getCommentNumber());

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

            cbLike.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){

                }
            });
            cbComment.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){

                }
            });


        }
    }

}
