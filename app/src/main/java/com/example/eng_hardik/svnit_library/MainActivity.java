package com.example.eng_hardik.svnit_library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private Button login;
    private int counter =5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();

        name = findViewById( R.id.user );
        password = (EditText)findViewById( R.id.pass );
        info = (TextView)findViewById( R.id.text );
        login = (Button)findViewById( R.id.button );
        info.setText("No. of Correct Attempts is 5");
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog( this );

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name.getText().toString() , password.getText().toString());
            }
        } );
    }



private void validate(String username, String password){

        progressDialog.setMessage( "Wait till the door openes ..." );
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword( username,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                progressDialog.dismiss();
                Toast.makeText( MainActivity.this,"Login Successful",Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent( MainActivity.this,UserActivity.class );
                startActivity(intent);
                MainActivity.this.finish();
            }
            else{
                Toast.makeText( MainActivity.this,"Login Failed",Toast.LENGTH_SHORT ).show();
                counter--;
                info.setText( "No of attempts remaining : "+counter );
                progressDialog.dismiss();
                if(counter==0)
                {login.setEnabled( false );}
            }
            }
        } );
    }
}
