package com.example.eng_hardik.svnit_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ListView listView;

    private DatabaseReference mDatabase;

    private ArrayList<String> mUsernames= new ArrayList<>( );

    FirebaseUser user;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );

        user = FirebaseAuth.getInstance().getCurrentUser();

        uid =user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( uid );

        listView = findViewById( R.id.listview );

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, mUsernames );
        listView.setAdapter( arrayAdapter );
        mDatabase.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue( String.class );
                mUsernames.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue( String.class );
                mUsernames.remove(value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );







    }
}
