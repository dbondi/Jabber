package adapaters;

import android.content.Context;
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
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import controllers.NotificationController;
import custom_class.Comment;
import custom_class.Notification;
import custom_class.Place;
import custom_class.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    ArrayList<Notification> notifications = new ArrayList<>();
    Bundle bundle;
    Context context;
    boolean boolPhoto;
    NotificationController controller;
    int width;

    private UserProfile user;
    private Comment comment;
    private Place place;
    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();

    public NotificationAdapter(Context context, Bundle bundle, NotificationController controller, int width) {
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

    public void update(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_likes, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Notification notification = notifications.get(position);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);


        if(notification.getChatLike()){
            holder.extraNotifications.setText("");
            ArrayList<StorageReference> notificationPics = notification.getNotificationPics();
            if(notificationPics.size() >= 1){
                GlideApp.with(context)
                        .load(notificationPics.get(0))
                        .apply(requestOptions)
                        .into(holder.profilePic1);

            }
            else{
                holder.profilePic1.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() >= 2){
                GlideApp.with(context)
                        .load(notificationPics.get(1))
                        .apply(requestOptions)
                        .into(holder.profilePic2);
            }
            else{
                holder.profilePic2.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() >= 3){
                GlideApp.with(context)
                        .load(notificationPics.get(2))
                        .apply(requestOptions)
                        .into(holder.profilePic3);
            }
            else{
                holder.profilePic3.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() >= 4){
                GlideApp.with(context)
                        .load(notificationPics.get(3))
                        .apply(requestOptions)
                        .into(holder.profilePic4);
            }
            else{
                holder.profilePic4.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() >= 5){
                GlideApp.with(context)
                        .load(notificationPics.get(4))
                        .apply(requestOptions)
                        .into(holder.profilePic5);
            }
            else{
                holder.profilePic5.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() >= 6){
                GlideApp.with(context)
                        .load(notificationPics.get(5))
                        .apply(requestOptions)
                        .into(holder.profilePic6);
            }
            else{
                holder.profilePic6.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() >= 7){
                GlideApp.with(context)
                        .load(notificationPics.get(6))
                        .apply(requestOptions)
                        .into(holder.profilePic7);
            }
            else{
                holder.profilePic7.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() >= 8){
                GlideApp.with(context)
                        .load(notificationPics.get(7))
                        .apply(requestOptions)
                        .into(holder.profilePic8);
            }
            else{
                holder.profilePic8.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            if(notificationPics.size() == 9){
                GlideApp.with(context)
                        .load(notificationPics.get(7))
                        .apply(requestOptions)
                        .into(holder.profilePic8);
            }
            else if(notificationPics.size() > 9){
                int extra = notificationPics.size() - 8;
                holder.profilePic9.setImageDrawable(context.getResources().getDrawable(R.color.colorDarkBlue));
                if(extra<10){
                    holder.extraNotifications.setText("+"+extra);
                    holder.extraNotifications.setTextSize(COMPLEX_UNIT_SP,16);
                    holder.extraNotifications.setPadding(0,0,3,3);
                }
                else if(extra<100){
                    holder.extraNotifications.setText("+"+extra);
                    holder.extraNotifications.setTextSize(COMPLEX_UNIT_SP,13);
                    holder.extraNotifications.setPadding(0,0,3,2);
                }
                else if(extra<1000) {
                    holder.extraNotifications.setText("+" + extra);
                    holder.extraNotifications.setTextSize(COMPLEX_UNIT_SP, 11);
                    holder.extraNotifications.setPadding(0, 0, 3, 1);
                }
                else if(extra<10000) {
                    holder.extraNotifications.setText("+" + extra);
                    holder.extraNotifications.setTextSize(COMPLEX_UNIT_SP, 9);
                    holder.extraNotifications.setPadding(0, 0, 3, 1);
                }
                else{
                    holder.extraNotifications.setText("+" + extra);
                    holder.extraNotifications.setTextSize(COMPLEX_UNIT_SP, 7);
                    holder.extraNotifications.setPadding(0, 0, 3, 1);
                }
            }
            else{
                holder.profilePic9.setImageDrawable(context.getResources().getDrawable(R.color.black));
            }
            holder.notificationChat.setText(notification.getContent());
            holder.notificationType.setBackground(context.getResources().getDrawable(R.drawable.red_heart));
        }



    }



    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilePic1;
        private CircleImageView profilePic2;
        private CircleImageView profilePic3;
        private CircleImageView profilePic4;
        private CircleImageView profilePic5;
        private CircleImageView profilePic6;
        private CircleImageView profilePic7;
        private CircleImageView profilePic8;
        private CircleImageView profilePic9;
        private LinearLayout notificationType;
        private TextView notificationMessage;
        private TextView notificationChat;
        private TextView extraNotifications;

        //FirebaseStorage storage = FirebaseStorage.getInstance();

        public ViewHolder(View itemView) {
            super(itemView);
            profilePic1 = itemView.findViewById(R.id.image1);
            profilePic2 = itemView.findViewById(R.id.image2);
            profilePic3 = itemView.findViewById(R.id.image3);
            profilePic4 = itemView.findViewById(R.id.image4);
            profilePic5 = itemView.findViewById(R.id.image5);
            profilePic6 = itemView.findViewById(R.id.image6);
            profilePic7 = itemView.findViewById(R.id.image7);
            profilePic8 = itemView.findViewById(R.id.image8);
            profilePic9 = itemView.findViewById(R.id.image9);
            notificationType = itemView.findViewById(R.id.notification_type);
            notificationMessage = itemView.findViewById(R.id.notification_message);
            notificationChat = itemView.findViewById(R.id.notification_chat);
            extraNotifications = itemView.findViewById(R.id.extra_notifications);
        }
    }

}
