package adapaters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import custom_class.MapTab;
import custom_class.Notification;
import custom_class.Place;
import custom_class.UserProfile;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int ITEM_TYPE_FRIEND_REQUEST = 0;
    private static final int ITEM_NOTIFICATION = 1;
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

    public NotificationAdapter(Context context, Bundle bundle, NotificationController controller) {
        user = bundle.getParcelable("User");
        this.bundle = bundle;
        this.context = context;
        this.controller = controller;
    }

    private Boolean calculateLike(Comment currentChat) {

        String UID = user.getUID();
        return currentChat.getLikeList().contains(UID);
    }

    public void update(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public int getItemViewType(int position) {
        if (notifications.get(position).getFriendRequest()) {
            return ITEM_TYPE_FRIEND_REQUEST;
        } else {
            return ITEM_NOTIFICATION;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == ITEM_TYPE_FRIEND_REQUEST){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_friend_requests, parent, false);
            final FriendRequestHolder holder = new FriendRequestHolder(view);

            return holder;
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_likes, parent, false);
            final NotificationHolder holder = new NotificationHolder(view);

            return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Notification notification = notifications.get(position);

        if(notification.getFriendRequest()){
            ((FriendRequestHolder)holder).friendRequest.setText("You have no friends");
            ((FriendRequestHolder)holder).friendRequestPhoto.setCircleBackgroundColor(Color.parseColor("#338833"));
        }
        else {


            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);


            if (notification.getChatLike()) {
                ((NotificationHolder)holder).extraNotifications.setText("");
                ((NotificationHolder)holder).circleImages.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).nameOfUserComment.setVisibility(View.GONE);
                ((NotificationHolder)holder).notificationType.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).notificationCommentPic.setVisibility(View.GONE);

                ArrayList<StorageReference> notificationPics = notification.getUserPics();
                if (notificationPics.size() >= 1) {
                    GlideApp.with(context)
                            .load(notificationPics.get(0))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic1);

                } else {
                    ((NotificationHolder)holder).profilePic1.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 2) {
                    GlideApp.with(context)
                            .load(notificationPics.get(1))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic2);
                } else {
                    ((NotificationHolder)holder).profilePic2.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 3) {
                    GlideApp.with(context)
                            .load(notificationPics.get(2))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic3);
                } else {
                    ((NotificationHolder)holder).profilePic3.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 4) {
                    GlideApp.with(context)
                            .load(notificationPics.get(3))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic4);
                } else {
                    ((NotificationHolder)holder).profilePic4.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 5) {
                    GlideApp.with(context)
                            .load(notificationPics.get(4))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic5);
                } else {
                    ((NotificationHolder)holder).profilePic5.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 6) {
                    GlideApp.with(context)
                            .load(notificationPics.get(5))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic6);
                } else {
                    ((NotificationHolder)holder).profilePic6.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 7) {
                    GlideApp.with(context)
                            .load(notificationPics.get(6))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic7);
                } else {
                    ((NotificationHolder)holder).profilePic7.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 8) {
                    GlideApp.with(context)
                            .load(notificationPics.get(7))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic8);
                } else {
                    ((NotificationHolder)holder).profilePic8.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() == 9) {
                    GlideApp.with(context)
                            .load(notificationPics.get(7))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic8);
                } else if (notificationPics.size() > 9) {
                    int extra = notificationPics.size() - 8;
                    ((NotificationHolder)holder).profilePic9.setImageDrawable(context.getResources().getDrawable(R.color.colorDarkBlue));
                    if (extra < 10) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 16);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 3);
                    } else if (extra < 100) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 13);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 2);
                    } else if (extra < 1000) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 11);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 1);
                    } else if (extra < 10000) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 9);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 1);
                    } else {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 7);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 1);
                    }
                } else {
                    ((NotificationHolder)holder).profilePic9.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                ArrayList<String> names = notification.getUserNames();
                if (notification.getContent().equals("")) {
                    ((NotificationHolder)holder).notificationChat.setVisibility(View.GONE);
                } else {
                    ((NotificationHolder)holder).notificationChat.setText(notification.getContent());
                }
                ((NotificationHolder)holder).extraNotifications.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).notificationMessage.setTextColor(Color.parseColor("#FFFFFF"));
                ((NotificationHolder)holder).notificationChat.setTextColor(Color.parseColor("#DDDDDD"));
                ((NotificationHolder)holder).postComment.setVisibility(View.GONE);
                ((NotificationHolder)holder).notificationType.setBackground(context.getResources().getDrawable(R.drawable.red_heart));
                if (names.size() == 1) {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + " liked your post");
                } else if (names.size() == 2) {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + " and " + names.get(1) + " liked your post");
                } else if (names.size() == 3) {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + ", " + names.get(1) + ", and " + names.get(2) + " liked your post");
                } else {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + " and " + (names.size() - 1) + " others liked your post");
                }
            } else if (notification.getCommentLike()) {
                ((NotificationHolder)holder).extraNotifications.setText("");
                ((NotificationHolder)holder).circleImages.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).nameOfUserComment.setVisibility(View.GONE);
                ((NotificationHolder)holder).notificationType.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).notificationCommentPic.setVisibility(View.GONE);

                ArrayList<StorageReference> notificationPics = notification.getUserPics();
                if (notificationPics.size() >= 1) {
                    GlideApp.with(context)
                            .load(notificationPics.get(0))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic1);

                } else {
                    ((NotificationHolder)holder).profilePic1.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 2) {
                    GlideApp.with(context)
                            .load(notificationPics.get(1))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic2);
                } else {
                    ((NotificationHolder)holder).profilePic2.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 3) {
                    GlideApp.with(context)
                            .load(notificationPics.get(2))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic3);
                } else {
                    ((NotificationHolder)holder).profilePic3.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 4) {
                    GlideApp.with(context)
                            .load(notificationPics.get(3))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic4);
                } else {
                    ((NotificationHolder)holder).profilePic4.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 5) {
                    GlideApp.with(context)
                            .load(notificationPics.get(4))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic5);
                } else {
                    ((NotificationHolder)holder).profilePic5.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 6) {
                    GlideApp.with(context)
                            .load(notificationPics.get(5))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic6);
                } else {
                    ((NotificationHolder)holder).profilePic6.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 7) {
                    GlideApp.with(context)
                            .load(notificationPics.get(6))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic7);
                } else {
                    ((NotificationHolder)holder).profilePic7.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() >= 8) {
                    GlideApp.with(context)
                            .load(notificationPics.get(7))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic8);
                } else {
                    ((NotificationHolder)holder).profilePic8.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                if (notificationPics.size() == 9) {
                    GlideApp.with(context)
                            .load(notificationPics.get(7))
                            .apply(requestOptions)
                            .into(((NotificationHolder)holder).profilePic8);
                } else if (notificationPics.size() > 9) {
                    int extra = notificationPics.size() - 8;
                    ((NotificationHolder)holder).profilePic9.setImageDrawable(context.getResources().getDrawable(R.color.colorDarkBlue));
                    if (extra < 10) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 16);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 3);
                    } else if (extra < 100) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 13);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 2);
                    } else if (extra < 1000) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 11);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 1);
                    } else if (extra < 10000) {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 9);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 1);
                    } else {
                        ((NotificationHolder)holder).extraNotifications.setText("+" + extra);
                        ((NotificationHolder)holder).extraNotifications.setTextSize(COMPLEX_UNIT_SP, 7);
                        ((NotificationHolder)holder).extraNotifications.setPadding(0, 0, 3, 1);
                    }
                } else {
                    ((NotificationHolder)holder).profilePic9.setImageDrawable(context.getResources().getDrawable(R.color.black));
                }
                ArrayList<String> names = notification.getUserNames();
                if (notification.getContent().equals("")) {
                    ((NotificationHolder)holder).notificationChat.setVisibility(View.GONE);
                } else {
                    ((NotificationHolder)holder).notificationChat.setText(notification.getContent());
                }
                ((NotificationHolder)holder).notificationMessage.setTextColor(Color.parseColor("#FFFFFF"));
                ((NotificationHolder)holder).notificationChat.setTextColor(Color.parseColor("#DDDDDD"));
                ((NotificationHolder)holder).notificationType.setBackground(context.getResources().getDrawable(R.drawable.red_heart));

                if (names.size() == 1) {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + " liked your post");
                } else if (names.size() == 2) {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + " and " + names.get(1) + " liked your post");
                } else if (names.size() == 3) {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + ", " + names.get(1) + ", and " + names.get(2) + " liked your post");
                } else {
                    ((NotificationHolder)holder).notificationMessage.setText(names.get(0) + " and " + (names.size() - 1) + " others liked your post");
                }

                ((NotificationHolder)holder).extraNotifications.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).postComment.setVisibility(View.GONE);
            } else if (notification.getCommentMessage()) {


                ArrayList<StorageReference> notificationPics = notification.getUserPics();
                ArrayList<String> names = notification.getUserNames();

                ((NotificationHolder)holder).circleImages.setVisibility(View.INVISIBLE);
                ((NotificationHolder)holder).nameOfUserComment.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).postComment.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).postComment.setText(" commented on your post");


                ((NotificationHolder)holder).nameOfUserComment.setText(names.get(0));

                GlideApp.with(context)
                        .load(notificationPics.get(0))
                        .apply(requestOptions)
                        .into(((NotificationHolder)holder).notificationCommentPic);

                ((NotificationHolder)holder).notificationMessage.setText(notification.getContent());

                ((NotificationHolder)holder).notificationType.setVisibility(View.GONE);
                ((NotificationHolder)holder).notificationCommentPic.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).notificationChat.setVisibility(View.GONE);
                ((NotificationHolder)holder).extraNotifications.setVisibility(View.GONE);
            } else if (notification.getResponseMessage()) {

                ArrayList<StorageReference> notificationPics = notification.getUserPics();
                ArrayList<String> names = notification.getUserNames();

                ((NotificationHolder)holder).circleImages.setVisibility(View.INVISIBLE);
                ((NotificationHolder)holder).nameOfUserComment.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).postComment.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).postComment.setText(" responded to your comment");


                ((NotificationHolder)holder).nameOfUserComment.setText(names.get(0));

                GlideApp.with(context)
                        .load(notificationPics.get(0))
                        .apply(requestOptions)
                        .into(((NotificationHolder)holder).notificationCommentPic);

                ((NotificationHolder)holder).notificationMessage.setText(notification.getContent());

                ((NotificationHolder)holder).notificationType.setVisibility(View.GONE);
                ((NotificationHolder)holder).notificationCommentPic.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).notificationChat.setVisibility(View.GONE);
                ((NotificationHolder)holder).extraNotifications.setVisibility(View.GONE);

            } else if (notification.getFriendRequest()) {

                ArrayList<StorageReference> notificationPics = notification.getUserPics();
                ArrayList<String> names = notification.getUserNames();

                ((NotificationHolder)holder).circleImages.setVisibility(View.INVISIBLE);
                ((NotificationHolder)holder).nameOfUserComment.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).postComment.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).postComment.setText(" responded to your comment");


                ((NotificationHolder)holder).nameOfUserComment.setText(names.get(0));

                GlideApp.with(context)
                        .load(notificationPics.get(0))
                        .apply(requestOptions)
                        .into(((NotificationHolder)holder).notificationCommentPic);

                ((NotificationHolder)holder).notificationMessage.setText(notification.getContent());

                ((NotificationHolder)holder).notificationType.setVisibility(View.GONE);
                ((NotificationHolder)holder).notificationCommentPic.setVisibility(View.VISIBLE);
                ((NotificationHolder)holder).notificationChat.setVisibility(View.GONE);
                ((NotificationHolder)holder).extraNotifications.setVisibility(View.GONE);

            }


        }
    }



    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilePic1;
        private CircleImageView profilePic2;
        private CircleImageView profilePic3;
        private CircleImageView profilePic4;
        private CircleImageView profilePic5;
        private CircleImageView profilePic6;
        private CircleImageView profilePic7;
        private CircleImageView profilePic8;
        private CircleImageView profilePic9;
        private CircleImageView notificationCommentPic;
        private RelativeLayout notificationType;
        private TextView notificationMessage;
        private TextView notificationChat;
        private TextView extraNotifications;
        private TextView nameOfUserComment;
        private TextView postComment;
        private LinearLayout circleImages;

        //FirebaseStorage storage = FirebaseStorage.getInstance();

        public NotificationHolder(View itemView) {
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
            notificationCommentPic = itemView.findViewById(R.id.notification_comment_pic);
            notificationType = itemView.findViewById(R.id.notification_type);
            notificationMessage = itemView.findViewById(R.id.notification_message);
            notificationChat = itemView.findViewById(R.id.notification_chat);
            extraNotifications = itemView.findViewById(R.id.extra_notifications);
            circleImages = itemView.findViewById(R.id.circle_images);
            nameOfUserComment = itemView.findViewById(R.id.name_of_user_comment);
            postComment = itemView.findViewById(R.id.post_comment);
        }
    }
    public class FriendRequestHolder extends RecyclerView.ViewHolder {
        private CircleImageView friendRequestPhoto;
        private TextView friendRequest;

        public FriendRequestHolder(View itemView) {
            super(itemView);
            friendRequestPhoto = itemView.findViewById(R.id.friend_requests_photo);
            friendRequest = itemView.findViewById(R.id.friend_requests_text);
        }
    }

}
