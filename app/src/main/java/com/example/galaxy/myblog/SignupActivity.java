package com.example.galaxy.myblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {


    EditText username,email,password;
    Button signup;
    String usertxt,emailtxt,passtxt;
    ProgressDialog dialog;
    final int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = (Button) findViewById(R.id.signupButton);

        username = (EditText) findViewById(R.id.usernameSign);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.passwordSign);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                usertxt = username.getText().toString();
                emailtxt = email.getText().toString();
                passtxt = password.getText().toString();

                if (usertxt.equals("") || emailtxt.equals("") || passtxt.equals("")) {
                    Toast.makeText(SignupActivity.this, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();

                    Toast.makeText(SignupActivity.this, "valid hi h", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(usertxt);
                    user.setPassword(passtxt);
                    user.setEmail(emailtxt);

                    user.put("blogs", 0);

                    dialog = ProgressDialog.show(SignupActivity.this, "Signing Up", "Please Wait....", true, false);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            dialog.dismiss();

                            if (e == null) {
                                Toast.makeText(SignupActivity.this, "Successfully Signed up", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignupActivity.this, UploadPhotoActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                e.printStackTrace();
                                Toast.makeText(SignupActivity.this, "Please Use Different Username or Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
