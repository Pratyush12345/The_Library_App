package com.example.eng_hardik.svnit_library;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import  android.app.NotificationManager;
import  android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class UserActivity extends AppCompatActivity{


    private ListView listView;

    private DatabaseReference mDatabase, mName;

    private ArrayList<String> mUsernames= new ArrayList<>( );
    private TextView name;
    FirebaseUser user;
    private static final int  notificationid=1223;

    String uid;
    NotificationCompat.Builder notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );
        getSupportActionBar().hide();

        notification= new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        name = (TextView)findViewById( R.id.name );

        user = FirebaseAuth.getInstance().getCurrentUser();

        uid =user.getUid();

        name.setText( "Welcome" );

        mName = FirebaseDatabase.getInstance().getReference().child( "Name" ).child( uid );

        mName.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue( String.class );
                name.setText( "Welcome "+value );
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                name.setText( "Error Occured!" );
            }
        } );

        mDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( uid );

        listView = findViewById( R.id.listview );

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.activity_list_item, android.R.id.text1 , mUsernames );
        listView.setAdapter( arrayAdapter );
        mDatabase.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue( String.class );
                mUsernames.add(value);
                arrayAdapter.notifyDataSetChanged();

                //build notification
                notification.setSmallIcon(R.drawable.ic_launcher_background);
                notification.setTicker("You have issued a book");
                notification.setWhen(System.currentTimeMillis());
                notification.setContentTitle("SVNIT-Library");
                notification.setContentText("You have issued the book: "+value);
                Intent intent=new Intent(UserActivity.this,UserActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(UserActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);
                NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(notificationid,notification.build());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue( String.class );
                mUsernames.remove(value);
                arrayAdapter.notifyDataSetChanged();


                notification.setSmallIcon(R.drawable.ic_launcher_background);
                notification.setTicker("You have returned a book");
                notification.setWhen(System.currentTimeMillis());
                notification.setContentTitle("SVNIT-Library");
                notification.setContentText("You have returned the book: "+value);
                Intent intent=new Intent(UserActivity.this,UserActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(UserActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);
                NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(notificationid,notification.build());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );


        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    Intent intent = new Intent( UserActivity.this,Book.class );
                    startActivityForResult( intent , 0 );
                }

            }
        } );





        }
}
