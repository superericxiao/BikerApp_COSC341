package com.cosc341.heather.p04;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

class RecyclerViewAdapter <T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    //private List<Post> dataList;
    private List<RecyclerViewItem> dataList;

    // int added onto chatUser id to create unique button id
    // Also used in friends list - currently separate instance
    private static final int USER_PROFILE_ID_CODE = 10000;

    private LayoutInflater mInflater;


    RecyclerViewAdapter(Context context, List<RecyclerViewItem> dataList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);

        this.dataList = dataList;
    }


    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 1) { // text post
            itemView = mInflater.inflate(R.layout.recyclerview_textpost, parent, false);
            return new textPostViewHolder(itemView);
        } else if (viewType == 2) { // activity post
            itemView = mInflater.inflate(R.layout.recyclerview_activitypost, parent, false);
            return new activityPostViewHolder(itemView);
        } else if (viewType == 3) { // thisUserMessage
            itemView = mInflater.inflate(R.layout.recyclerview_thisuserchat, parent, false);
            return new thisUserMessageViewHolder(itemView);
        } else if (viewType == 4) { // otherUserMessage
            itemView = mInflater.inflate(R.layout.recyclerview_otheruserchat, parent, false);
            return new otherUserMessageViewHolder(itemView);
        } else {
            return null;
        }
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 1) {
            String username = ((Post) dataList.get(position)).getUser().getUsername();
            Integer avatar = ((Post) dataList.get(position)).getUser().getProfilePicture();
            int id = ((Post) dataList.get(position)).getUser().getId();

            ((textPostViewHolder)holder).tv_username.setText(username);
            ((textPostViewHolder)holder).ib_userProfile.setImageResource(avatar);

            ((textPostViewHolder)holder).ib_userProfile.setId(id + USER_PROFILE_ID_CODE);

            ((textPostViewHolder)holder).ib_userProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context instanceof NewsfeedActivity) {
                        ((NewsfeedActivity)context).goToUserProfile(view);
                    }
                }
            });

            String content = ((TextPost) dataList.get(position)).getContent();
            ((textPostViewHolder)holder).tv_content.setText(content);
        } else if (holder.getItemViewType() == 2) {
            String username = ((Post) dataList.get(position)).getUser().getUsername();
            Integer avatar = ((Post) dataList.get(position)).getUser().getProfilePicture();
            int id = ((Post) dataList.get(position)).getUser().getId();

            ((activityPostViewHolder)holder).tv_username.setText(username);
            ((activityPostViewHolder)holder).ib_userProfile.setImageResource(avatar);

            ((activityPostViewHolder)holder).ib_userProfile.setId(id + USER_PROFILE_ID_CODE);

            ((activityPostViewHolder)holder).ib_userProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context instanceof NewsfeedActivity) {
                        ((NewsfeedActivity)context).goToUserProfile(view);
                    }
                }
            });

            String routename = ((ActivityPost) dataList.get(position)).getRoute().getRouteName();
            ((activityPostViewHolder)holder).tv_routeName.setText(routename);
        } else if (holder.getItemViewType() == 3) {
             Integer profilePic = ((Message) dataList.get(position)).getUser().getProfilePicture();
             String username = ((Message)dataList.get(position)).getUser().getUsername();
             String message = ((Message)dataList.get(position)).getMessageContent();
             Date date = ((Message)dataList.get(position)).getDate();

             ((thisUserMessageViewHolder)holder).iv_userProfile.setImageResource(profilePic);
             ((thisUserMessageViewHolder)holder).tv_username.setText(username);
             ((thisUserMessageViewHolder)holder).tv_message.setText(message);
             ((thisUserMessageViewHolder)holder).tv_date.setText(date.toString());

        } else if (holder.getItemViewType() == 4) {
            Integer profilePic = ((Message) dataList.get(position)).getUser().getProfilePicture();
            String username = ((Message)dataList.get(position)).getUser().getUsername();
            String message = ((Message)dataList.get(position)).getMessageContent();
            Date date = ((Message)dataList.get(position)).getDate();

            ((otherUserMessageViewHolder)holder).iv_userProfile.setImageResource(profilePic);
            ((otherUserMessageViewHolder)holder).tv_username.setText(username);
            ((otherUserMessageViewHolder)holder).tv_message.setText(message);
            ((otherUserMessageViewHolder)holder).tv_date.setText(date.toString());
        } else {

        }

    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(dataList.get(position).getViewType());
    }

    // returns total number of rows
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class textPostViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username;
        ImageButton ib_userProfile;
        TextView tv_content;

        textPostViewHolder(View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_usernameTP);
            ib_userProfile = itemView.findViewById(R.id.iv_userProfileTP);
            tv_content = itemView.findViewById(R.id.tv_messageTP);
        }
    }

    public class activityPostViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username;
        ImageButton ib_userProfile;
        TextView tv_routeName;


        activityPostViewHolder(View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_usernameAP);
            ib_userProfile = itemView.findViewById(R.id.iv_userProfileAP);
            tv_routeName = itemView.findViewById(R.id.tv_routenameAP);
        }
    }

    public class thisUserMessageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_userProfile;
        TextView tv_username;
        TextView tv_message;
        TextView tv_date;

        thisUserMessageViewHolder(View itemView) {
            super(itemView);
            iv_userProfile = itemView.findViewById(R.id.iv_userProfileTC);
            tv_username = itemView.findViewById(R.id.tv_usernameTC);
            tv_message = itemView.findViewById(R.id.tv_messageTC);
            tv_date = itemView.findViewById(R.id.tv_dateTC);
        }
    }

    public class otherUserMessageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_userProfile;
        TextView tv_username;
        TextView tv_message;
        TextView tv_date;

        otherUserMessageViewHolder(View itemView) {
            super(itemView);
            iv_userProfile = itemView.findViewById(R.id.iv_userProfileOC);
            tv_username = itemView.findViewById(R.id.tv_usernameOC);
            tv_message = itemView.findViewById(R.id.tv_messageOC);
            tv_date = itemView.findViewById(R.id.tv_dateOC);
        }
    }

}
