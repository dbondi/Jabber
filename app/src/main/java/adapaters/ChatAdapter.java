package adapaters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.jab.GlideApp;
import com.example.jab.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

import controllers.ChatController;
import custom_class.Chat;
import custom_class.UserProfile;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    ArrayList<Chat> chats = new ArrayList<>();
    Context context;
    int widthScreen;
    int heightScreen;
    int optionScreenSize;
    ChatController controller;
    boolean load = true;
    boolean mostPopular;
    int ydy = 0;

    private UserProfile user;

    public ChatAdapter(UserProfile user, Context context, int widthScreen, int heightScreen,int optionScreenSize, ChatController controller, boolean mostPopular){
        this.user = user;
        this.context = context;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        this.optionScreenSize = optionScreenSize;
        this.controller = controller;
        this.mostPopular = mostPopular;
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

        Chat currentChat = chats.get(position);

        System.out.println("StartBind");

        double factor = ((double)currentChat.getImageHeight()+20)/((double)currentChat.getImageWidth()+80.0);

        if(currentChat.getBoolGif()){
            ImageView postImageRef = holder.post_image;

            holder.poll.setVisibility(View.GONE);

            GlideApp.with(context)
                    .load(currentChat.getGifURL())
                    .into(postImageRef);
        }
        else if(currentChat.getBoolPoll()){

            Boolean voteInDatabase = false;

            holder.option1Check.setVisibility(View.INVISIBLE);
            holder.option2Check.setVisibility(View.INVISIBLE);
            holder.option3Check.setVisibility(View.INVISIBLE);
            holder.option4Check.setVisibility(View.INVISIBLE);

            holder.option1Vote.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));
            holder.option2Vote.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));
            holder.option3Vote.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));
            holder.option4Vote.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));

            holder.poll.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));

            holder.castVote.setClickable(false);

            holder.poleNum.setVisibility(View.INVISIBLE);

            Map<String,Integer> pollVotes = currentChat.getPollVoteList();

            if (currentChat.getPollVoteList().size()==1){
                holder.poleNum.setText(currentChat.getPollVoteList().size()+" vote");
            }
            else{
                holder.poleNum.setText(currentChat.getPollVoteList().size()+" votes");
            }
            ArrayList<String> pollOptions = currentChat.getPollOptions();
            if(pollOptions.size()==2){
                holder.option1Text.setText(pollOptions.get(0));
                holder.option2Text.setText(pollOptions.get(1));
                holder.option3Box.setVisibility(View.GONE);
                holder.option4Box.setVisibility(View.GONE);
            }
            else if(pollOptions.size()==3){
                holder.option1Text.setText(pollOptions.get(0));
                holder.option2Text.setText(pollOptions.get(1));
                holder.option3Text.setText(pollOptions.get(2));
                holder.option4Box.setVisibility(View.GONE);
            }
            else if(pollOptions.size()==4){
                holder.option1Text.setText(pollOptions.get(0));
                holder.option2Text.setText(pollOptions.get(1));
                holder.option3Text.setText(pollOptions.get(2));
                holder.option4Text.setText(pollOptions.get(3));
            }

            for(String key: pollVotes.keySet()){

                if(key.equals(user.getUID())) {

                    voteInDatabase = true;

                    holder.option1Box.setClickable(false);
                    holder.option2Box.setClickable(false);
                    holder.option3Box.setClickable(false);
                    holder.option4Box.setClickable(false);

                    Integer pollNum = pollVotes.get(key);


                    holder.option1Vote.setVisibility(View.VISIBLE);
                    holder.option2Vote.setVisibility(View.VISIBLE);
                    holder.option3Vote.setVisibility(View.VISIBLE);
                    holder.option4Vote.setVisibility(View.VISIBLE);
                    holder.option1Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(0) / pollVotes.size()));
                    holder.option2Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(1) / pollVotes.size()));
                    holder.option3Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(2) / pollVotes.size()));
                    holder.option4Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(3) / pollVotes.size()));
                    holder.poleNum.setVisibility(View.VISIBLE);

                    if (pollNum == 1) {
                        holder.option1Check.setVisibility(View.VISIBLE);
                    }
                    else if (pollNum == 2) {
                        holder.option2Check.setVisibility(View.VISIBLE);
                    }
                    else if (pollNum == 3) {
                        holder.option3Check.setVisibility(View.VISIBLE);
                    }
                    else if (pollNum == 4) {
                        holder.option4Check.setVisibility(View.VISIBLE);
                    }
                }

            }
            if(currentChat.getPoleVoted()){
                currentChat.setPoleVoted(true);
                holder.option1Vote.setVisibility(View.VISIBLE);
                holder.option2Vote.setVisibility(View.VISIBLE);
                holder.option3Vote.setVisibility(View.VISIBLE);
                holder.option4Vote.setVisibility(View.VISIBLE);

                holder.option1Box.setClickable(false);
                holder.option2Box.setClickable(false);
                holder.option3Box.setClickable(false);
                holder.option4Box.setClickable(false);
                holder.poleNum.setVisibility(View.VISIBLE);

                int newPoleVotes = currentChat.getPollVoteList().size()+1;

                if ((newPoleVotes)==1){
                    holder.poleNum.setText(newPoleVotes+" vote");
                }
                else{
                    holder.poleNum.setText(newPoleVotes+" votes");
                }

                if (currentChat.getValueVoted() == 1) {
                    holder.option1Check.setVisibility(View.VISIBLE);
                    holder.option1Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                    holder.option1Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - (currentChat.getPollVotes().get(0)+1) / (currentChat.getPollVoteList().size()+1)));
                    holder.option2Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(1) / (currentChat.getPollVoteList().size()+1)));
                    holder.option3Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(2) / (currentChat.getPollVoteList().size()+1)));
                    holder.option4Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(3) / (currentChat.getPollVoteList().size()+1)));

                } else if (currentChat.getValueVoted() == 2) {
                    holder.option2Check.setVisibility(View.VISIBLE);
                    holder.option2Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                    holder.option1Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(0) / (currentChat.getPollVoteList().size()+1)));
                    holder.option2Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - (currentChat.getPollVotes().get(1)+1) / (currentChat.getPollVoteList().size()+1)));
                    holder.option3Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(2) / (currentChat.getPollVoteList().size()+1)));
                    holder.option4Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(3) / (currentChat.getPollVoteList().size()+1)));


                } else if (currentChat.getValueVoted() == 3) {
                    holder.option3Check.setVisibility(View.VISIBLE);
                    holder.option3Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                    holder.option1Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(0) / (currentChat.getPollVoteList().size()+1)));
                    holder.option2Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(1) / (currentChat.getPollVoteList().size()+1)));
                    holder.option3Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - (currentChat.getPollVotes().get(2)+1) / (currentChat.getPollVoteList().size()+1)));
                    holder.option4Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(3) / (currentChat.getPollVoteList().size()+1)));

                } else if (currentChat.getValueVoted() == 4) {
                    holder.option4Check.setVisibility(View.VISIBLE);
                    holder.option4Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                    holder.option1Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(0) / (currentChat.getPollVoteList().size()+1)));
                    holder.option2Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(1) / (currentChat.getPollVoteList().size()+1)));
                    holder.option3Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - currentChat.getPollVotes().get(2) / (currentChat.getPollVoteList().size()+1)));
                    holder.option4Vote.getLayoutParams().width = Math.round(optionScreenSize * (1 - (currentChat.getPollVotes().get(3)+1) / (currentChat.getPollVoteList().size()+1)));

                }
            }

            if(!voteInDatabase && !currentChat.getPoleVoted()){
                holder.option1Vote.setVisibility(View.INVISIBLE);
                holder.option2Vote.setVisibility(View.INVISIBLE);
                holder.option3Vote.setVisibility(View.INVISIBLE);
                holder.option4Vote.setVisibility(View.INVISIBLE);

                holder.option1Box.setClickable(true);
                holder.option2Box.setClickable(true);
                holder.option3Box.setClickable(true);
                holder.option4Box.setClickable(true);

                holder.option1Box.setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){
                        holder.option1Box.setBackground(context.getResources().getDrawable(R.drawable.clear_border));

                        holder.option2Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option3Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option4Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));

                        holder.valueSelected = 1;
                        holder.castVote.setClickable(true);
                    }
                });
                holder.option2Box.setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){
                        holder.option2Box.setBackground(context.getResources().getDrawable(R.drawable.clear_border));
                        holder.option1Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option3Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option4Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));

                        holder.valueSelected = 2;
                        holder.castVote.setClickable(true);
                    }
                });
                holder.option3Box.setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){
                        holder.option3Box.setBackground(context.getResources().getDrawable(R.drawable.clear_border));
                        holder.option1Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option2Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option4Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));

                        holder.valueSelected = 3;
                        holder.castVote.setClickable(true);
                    }
                });
                holder.option4Box.setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){
                        holder.option4Box.setBackground(context.getResources().getDrawable(R.drawable.clear_border));
                        holder.option1Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option2Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));
                        holder.option3Box.setBackground(context.getResources().getDrawable(R.drawable.inside_clear_border));

                        holder.valueSelected = 4;
                        holder.castVote.setClickable(true);
                    }
                });
                holder.castVote.setOnClickListener(new View.OnClickListener(){
                    @Override public void onClick(View v){
                        currentChat.setPoleVoted(true);
                        if (holder.valueSelected == 1) {
                            currentChat.setValueVoted(1);
                            notifyItemChanged(position);

                        } else if (holder.valueSelected == 2) {
                            currentChat.setValueVoted(2);
                            notifyItemChanged(position);

                        } else if (holder.valueSelected == 3) {
                            currentChat.setValueVoted(3);
                            notifyItemChanged(position);
                        } else if (holder.valueSelected == 4) {
                            currentChat.setValueVoted(4);
                            notifyItemChanged(position);
                        }

                    }
                });
            }



        }
        else if(currentChat.getBoolImage()) {


            ImageView postImageRef = holder.post_image;

            holder.poll.setVisibility(View.GONE);


            //holder.post_image.setImageBitmap(currentChat.getImageBitmap());

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

            GlideApp.with(context)
                    .load(currentChat.getGsReference())
                    .apply(requestOptions)
                    .into(postImageRef);
        }
        else if(currentChat.getBoolText()){

            holder.poll.setVisibility(View.GONE);

            holder.post_image.setVisibility(View.GONE);

        }


        if (currentChat.getContent().isEmpty()){
            holder.message.setVisibility(View.GONE);

        }
        else{
            String first = "<font color='" + currentChat.getColor().get(1) + "'>\" </font>";
            String next = "<font color='#FFFFFF'>" + currentChat.getContent().toUpperCase() + "</font>";
            String last = "<font color='" + currentChat.getColor().get(1) + "'> \"</font>";

            holder.message.setText(Html.fromHtml(first + next + last));
        }

        holder.username.setText(currentChat.getUserName());


        ImageView profPicRef = holder.profilePic;

        holder.profilePic.getLayoutParams().height = ((int) (heightScreen*.03));
        holder.bottomBox.getLayoutParams().height = ((int) (heightScreen*.06));

        holder.cardBackground.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));
        holder.photoBox.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));
        holder.bottomBox.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));
        holder.post_image.setBackgroundColor(Color.parseColor(currentChat.getColor().get(0)));

        if(!mostPopular){
            holder.placeText.setVisibility(View.INVISIBLE);
        }


        holder.likeNumber.setText(Integer.toString(currentChat.getLikeNumber()));
        holder.commentNumber.setText(Integer.toString(currentChat.getCommentNumber()));

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

        GlideApp.with(context)
                .load(currentChat.getProfPicReference())
                .apply(requestOptions)
                .into(profPicRef);


        if(calculateLike(currentChat)) {
            if(!holder.cbLike.isChecked()) {
                holder.cbLike.toggle();
            }
        }

        holder.cbComment.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                controller.commentSection(currentChat.getPlace(), currentChat.getMessageID(), user);
            }
        });

        holder.cbLike.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                //like

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
        private LinearLayout cardBackground;
        FirebaseStorage storage = FirebaseStorage.getInstance();

        private TextView option1Text;
        private TextView option2Text;
        private TextView option3Text;
        private TextView option4Text;

        private RelativeLayout option1Box;
        private RelativeLayout option2Box;
        private RelativeLayout option3Box;
        private RelativeLayout option4Box;

        private LinearLayout option1Vote;
        private LinearLayout option2Vote;
        private LinearLayout option3Vote;
        private LinearLayout option4Vote;

        private LinearLayout option1Check;
        private LinearLayout option2Check;
        private LinearLayout option3Check;
        private LinearLayout option4Check;

        private LinearLayout poll;

        private LinearLayout votePreCast;
        private LinearLayout castVote;

        private TextView poleNum;
        private TextView placeText;

        private int valueSelected;


        public ViewHolder(View itemView) {
            super(itemView);

            System.out.println("ViewHolder");
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
            cardBackground = itemView.findViewById(R.id.cardBackground);
            poll = itemView.findViewById(R.id.poll);

            option1Text = itemView.findViewById(R.id.option_1);
            option1Box = itemView.findViewById(R.id.option_1_box);
            option2Text = itemView.findViewById(R.id.option_2);
            option2Box = itemView.findViewById(R.id.option_2_box);
            option3Text = itemView.findViewById(R.id.option_3);
            option3Box = itemView.findViewById(R.id.option_3_box);
            option4Text = itemView.findViewById(R.id.option_4);
            option4Box = itemView.findViewById(R.id.option_4_box);

            castVote = itemView.findViewById(R.id.castVote);
            votePreCast = itemView.findViewById(R.id.votePreCast);
            poleNum = itemView.findViewById(R.id.poleNum);

            option1Vote = itemView.findViewById(R.id.option_1_vote);
            option2Vote = itemView.findViewById(R.id.option_2_vote);
            option3Vote = itemView.findViewById(R.id.option_3_vote);
            option4Vote = itemView.findViewById(R.id.option_4_vote);

            option1Check = itemView.findViewById(R.id.option1Check);
            option2Check = itemView.findViewById(R.id.option2Check);
            option3Check = itemView.findViewById(R.id.option3Check);
            option4Check = itemView.findViewById(R.id.option4Check);

            placeText = itemView.findViewById(R.id.place);


        }
    }

}
