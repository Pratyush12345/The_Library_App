package com.example.eng_hardik.svnit_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private Button login;
    private int counter =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();

        name = (EditText)findViewById( R.id.user );
        password = (EditText)findViewById( R.id.pass );
        info = (TextView)findViewById( R.id.text );
        login = (Button)findViewById( R.id.button );
        info.setText("No. of Correct Attempts is 5");

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name.getText().toString() , password.getText().toString());
            }
        } );
    }



private void validate(String username, String password){
    if ((username.equals( "admin" ))&&(password.equals( "123456" ))){
        Intent intent = new Intent( MainActivity.this, UserActivity.class );
        startActivity(intent);}

        else{
        counter--;
        info.setText( "No. of Attempts remaining : "+String.valueOf( counter ) );
        if(counter == 0)
            login.setEnabled(false);
    }

}
}
