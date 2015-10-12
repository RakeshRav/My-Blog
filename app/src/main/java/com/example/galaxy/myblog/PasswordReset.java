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
import com.parse.RequestPasswordResetCallback;

public class PasswordReset extends AppCompatActivity {

    EditText email;
    Button reset;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        email = (EditText) findViewById(R.id.email_id);

        reset = (Button) findViewById(R.id.resetButton);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailtxt = email.getText().toString();


                if (!emailtxt.equals("")) {

                    dialog = ProgressDialog.show(PasswordReset.this,"Password Reset","Sending you a reset link....",true,false);
                    ParseUser.requestPasswordResetInBackground(emailtxt, new RequestPasswordResetCallback() {
                        @Override
                        public void done(ParseException e) {
                            dialog.dismiss();
                            if (e == null) {
                                Toast.makeText(PasswordReset.this, "change Password link sent", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(PasswordReset.this, Login.class);
                                startActivity(i);
                                finish();
                            } else {
                                e.printStackTrace();
                                Toast.makeText(PasswordReset.this, "Please Enter A valid email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(PasswordReset.this, "Enter a valid Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password_reset, menu);
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
