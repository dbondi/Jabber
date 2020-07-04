package adapaters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.jab.GlideApp;
import com.example.jab.R;
import com.google.firebase.Timestamp;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import controllers.DirectMessageController;
import controllers.DirectMessageSearchController;
import controllers.NotificationController;
import custom_class.DirectMessage;
import custom_class.Notification;
import custom_class.UserDirectMessages;
import custom_class.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static custom_class.HelperFunctions.spToPx;

public class DirectMessageSearchAdapter extends RecyclerView.Adapter<DirectMessageSearchAdapter.ViewHolder>{

    private static final int ITEM_SENDER = 0;
    private static final int ITEM_RECIPIENT = 1;
    ArrayList<UserDirectMessages> userDirectMessages = new ArrayList<>();
    Bundle bundle;
    Context context;
    DirectMessageSearchController controller;
    FirebaseStorage storage;

    public DirectMessageSearchAdapter(Context context, Bundle bundle, DirectMessageSearchController controller) {

        this.bundle = bundle;
        this.context = context;
        this.controller = controller;
        storage = FirebaseStorage.getInstance();
    }

    public void update(ArrayList<UserDirectMessages> userDirectMessages) {
        this.userDirectMessages = userDirectMessages;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_direct_messages, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DirectMessageSearchAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        UserDirectMessages userDirectMessage = userDirectMessages.get(position);

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);


        GlideApp.with(context)
                .load(storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+userDirectMessage.getUserUID()+"/PhotoReferences/pic1"))
                .apply(requestOptions)
                .into(holder.profilePic);

        holder.friendName.setText(userDirectMessage.getName());

        if(userDirectMessage.getTypeMessage().equals("ReadSent")){
            holder.receiptIcon.setBackground(context.getResources().getDrawable(R.drawable.received_opened_arrow));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(spToPx(16,context), spToPx(16,context));
            layoutParams.setMargins(0,0,25,0);
            holder.receiptIcon.setLayoutParams(layoutParams);
            holder.receiptDescription.setText("Opened • " + simplestTime(userDirectMessage.getLastMessage()));
            holder.addPhoto.setBackground(context.getResources().getDrawable(R.drawable.camera));
            LinearLayout.LayoutParams cameraParams = new LinearLayout.LayoutParams(spToPx(24,context), spToPx(24,context));
            holder.addPhoto.setLayoutParams(cameraParams);
        }
        else if(userDirectMessage.getTypeMessage().equals("UnreadSent")){
            holder.receiptIcon.setBackground(context.getResources().getDrawable(R.drawable.received_unopened_arrow));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(spToPx(16,context), spToPx(16,context));
            layoutParams.setMargins(0,0,25,0);
            holder.receiptIcon.setLayoutParams(layoutParams);
            holder.receiptDescription.setText("Sent • " + simplestTime(userDirectMessage.getLastMessage()));
            holder.addPhoto.setBackground(context.getResources().getDrawable(R.drawable.camera));
            LinearLayout.LayoutParams cameraParams = new LinearLayout.LayoutParams(spToPx(24,context), spToPx(24,context));
            holder.addPhoto.setLayoutParams(cameraParams);
        }
        else if(userDirectMessage.getTypeMessage().equals("ReadReceived")){
            holder.receiptIcon.setBackground(context.getResources().getDrawable(R.drawable.received_opened_box));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(spToPx(24,context), spToPx(24,context));
            layoutParams.setMargins(0,0,20,0);
            holder.receiptIcon.setLayoutParams(layoutParams);
            holder.receiptDescription.setText("Received • " + simplestTime(userDirectMessage.getLastMessage()));
            holder.addPhoto.setBackground(context.getResources().getDrawable(R.drawable.camera));
            LinearLayout.LayoutParams cameraParams = new LinearLayout.LayoutParams(spToPx(24,context), spToPx(24,context));
            holder.addPhoto.setLayoutParams(cameraParams);
        }
        else if(userDirectMessage.getTypeMessage().equals("UnreadReceived")){
            holder.receiptIcon.setVisibility(View.GONE);
            holder.addPhoto.setBackground(context.getResources().getDrawable(R.drawable.received_unopened_box));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(spToPx(30,context), spToPx(30,context));
            holder.addPhoto.setLayoutParams(layoutParams);
            holder.receiptDescription.setText("Open new message");
        }

        holder.directMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return userDirectMessages.size();
    }

    public String simplestTime(Timestamp setTime){
        int secondsNow = (int) Timestamp.now().getSeconds();
        int secondsSent = (int) setTime.getSeconds();
        int secondsDif = secondsNow - secondsSent;
        int weeks = secondsDif / 604800;
        int days = (secondsDif % 604800)/86400;
        int hours = (secondsDif % 86400)/3600;
        int minutes = (secondsDif % 3600)/60;
        int seconds = (secondsDif % 60);
        if (weeks != 0){
            return weeks +"w ago";
        }
        if (days != 0){
            return days +"d ago";
        }
        if (hours != 0){
            return hours +"h ago";
        }
        if (minutes != 0){
            return minutes +"m ago";
        }
        return seconds +"s ago";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView friendName;
        private CircleImageView profilePic;
        private LinearLayout receiptIcon;
        private TextView receiptDescription;
        private LinearLayout addPhoto;
        private LinearLayout directMessage;


        public ViewHolder(View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friend_name);
            profilePic = itemView.findViewById(R.id.friend_photo);
            receiptIcon = itemView.findViewById(R.id.receipt_icon);
            receiptDescription = itemView.findViewById(R.id.receipt_description);
            addPhoto = itemView.findViewById(R.id.add_photo);
            directMessage = itemView.findViewById(R.id.direct_message_row);
        }
    }

}
