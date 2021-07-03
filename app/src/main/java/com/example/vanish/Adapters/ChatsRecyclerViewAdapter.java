package com.example.vanish.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vanish.MessageActivity;
import com.example.vanish.Model.Chat;
import com.example.vanish.Model.Chats;
import com.example.vanish.Model.Model;
import com.example.vanish.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsRecyclerViewAdapter extends RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Chats> mUsers;
    private List<Model> users;
    private boolean ischat;

    String theLastMessage;
    public ChatsRecyclerViewAdapter(Context mContext, List<Chats> mUsers, boolean ischat)
    {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chatsample,parent,false);
        return new ChatsRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Chats user = mUsers.get(position);
        holder.UserName.setText(user.getFullName());

        //to check weather the user has a profile picture
        //if (user.getImageURL().equals("default"))
        //{
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        //}

        //when the us/*else
        //        {
        //            Glide.with(mContext).load(user.getImageURL()).into(holder.imageView);
        //        }*/er is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);
            }
        });

        if(ischat){
            lastmessage(user.getId(),holder.LastMessage);
        }else {
            holder.LastMessage.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView UserName,LastMessage,Time;


        public ViewHolder(View itemView)
        {
            super(itemView);
            UserName = itemView.findViewById(R.id.userName);
            LastMessage  = itemView.findViewById(R.id.lastMessage);
            Time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.profile_image_profile);
        }
    }

    //check for last message
    private void lastmessage(final String user_id, final TextView last_msg)
    {
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) &&chat.getSender().equals(user_id) ||
                            chat.getReceiver().equals(user_id)&& chat.getSender().equals(firebaseUser.getUid())){
                        theLastMessage = chat.getMessage();
                    }
                }
                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
