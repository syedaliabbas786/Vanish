package com.example.vanish.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vanish.Login;
import com.example.vanish.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    TextView FullName,Email,Phone,Username;
    DatabaseReference reference;
    Animation anim;
    LinearLayout layout;
    Button signout;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Username = view.findViewById(R.id.username_profile);
        FullName = view.findViewById(R.id.fullName);
        Email = view.findViewById(R.id.email);
        Phone = view.findViewById(R.id.phoneNo);
        layout = view.findViewById(R.id.inner_layout);
        signout = view.findViewById(R.id.signOut);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();

        anim = AnimationUtils.loadAnimation(getContext(),R.anim.profile_bottom_top);
        layout.setAnimation(anim);

        String Id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(Id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("username").getValue(String.class);
                String name = snapshot.child("fullName").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String phone = snapshot.child("phoneNo").getValue(String.class);

                Username.setText(username);
                FullName.setText(name);
                Email.setText(email);
                Phone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });
        return view;
    }
}