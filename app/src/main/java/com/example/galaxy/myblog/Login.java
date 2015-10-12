package com.example.galaxy.myblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {


    EditText username,password;
    Button login,signup,reset;

    String usertxt,passtxt;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);
        reset = (Button) findViewById(R.id.passwordButton);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);


                usertxt = username.getText().toString();
                passtxt = password.getText().toString();

                if (usertxt.equals("") || passtxt.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter valid Credentials",Toast.LENGTH_LONG).show();
                }
                else {
                    if (connectivityManager.getNetworkInfo(0).isConnected() || connectivityManager.getNetworkInfo(1).isConnected())
                    {
//                        Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
//                        Log.i("Network", "Connected");

                        dialog = ProgressDialog.show(Login.this,"Loggin In","Please Wait....",true,false);

                        ParseUser.logInInBackground(usertxt, passtxt, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {

                                 if (user != null) {
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), MyBlogActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {

                                    Toast.makeText(getApplicationContext(), "Please Enter Valid Credentials", Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();

                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Not Connencted", Toast.LENGTH_SHORT).show();
                        Log.i("Network","Not Connected");
                    }


                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
//                dialog = ProgressDialog.show(Login.this, "Searching...", "Searching for matches", true, false);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,PasswordReset.class);
                startActivity(i);


            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
