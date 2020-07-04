package adapaters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.jab.GlideApp;
import com.example.jab.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import controllers.DirectMessageController;
import controllers.NotificationController;
import custom_class.DirectMessage;
import custom_class.Notification;
import custom_class.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

public class DirectMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_SENDER = 0;
    private static final int ITEM_RECIPIENT = 1;
    ArrayList<DirectMessage> directMessages = new ArrayList<>();
    Bundle bundle;
    Context context;
    private UserProfile myUser;
    private UserProfile friendUser;
    DirectMessageController controller;
    FirebaseStorage storage;

    public DirectMessageAdapter(Context context, DirectMessageController controller, UserProfile myUser, UserProfile friendUser) {
        this.bundle = bundle;
        this.context = context;
        this.controller = controller;
        this.myUser = myUser;
        this.friendUser = friendUser;
        storage = FirebaseStorage.getInstance();
    }

    public void update(ArrayList<DirectMessage> directMessages) {
        this.directMessages = directMessages;
    }

    @Override
    public int getItemViewType(int position) {
        if (directMessages.get(position).getSender().equals(myUser.getUID())) {
            return ITEM_SENDER;
        } else {
            return ITEM_RECIPIENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == ITEM_SENDER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sender_chat, parent, false);
            final DirectMessageAdapter.SenderHolder holder = new DirectMessageAdapter.SenderHolder(view);

            return holder;
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipient_chat, parent, false);
            final DirectMessageAdapter.RecipientHolder holder = new DirectMessageAdapter.RecipientHolder(view);

            return holder;
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        DirectMessage notification = directMessages.get(position);

        if (notification.getSender().equals(myUser.getUID())) {
            if(notification.getBoolContent()){
                ((SenderHolder) holder).chatMessage.setText(notification.getContent());
            }
        } else {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);


            GlideApp.with(context)
                    .load(storage.getReferenceFromUrl(friendUser.getStorageReferences().get(0)))
                    .apply(requestOptions)
                    .into(((RecipientHolder) holder).profilePic);


            if(notification.getBoolContent()) {
                ((RecipientHolder) holder).chatMessage.setText(notification.getContent());
            }
        }
    }

    @Override
    public int getItemCount() {
        return directMessages.size();
    }

    public class SenderHolder extends RecyclerView.ViewHolder {
        private TextView chatMessage;

        public SenderHolder(View itemView) {
            super(itemView);
            chatMessage = itemView.findViewById(R.id.chat_message);
        }
    }
    public class RecipientHolder extends RecyclerView.ViewHolder {
        private TextView chatMessage;
        private CircleImageView profilePic;

        public RecipientHolder(View itemView) {
            super(itemView);
            chatMessage = itemView.findViewById(R.id.chat_message);
            profilePic = itemView.findViewById(R.id.profile_pic);
        }
    }



}
