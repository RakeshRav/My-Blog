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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class IndexActivity extends AppCompatActivity {


    TextView name;
    Button post;
    EditText title,content;
    String titletxt,contenttxt,username;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);



        name = (TextView) findViewById(R.id.name);
        post = (Button) findViewById(R.id.post);

        title = (EditText) findViewById(R.id.titleContent);
        content = (EditText) findViewById(R.id.content);

        final ParseUser user = ParseUser.getCurrentUser();

        name.setText(user.getUsername().toString());

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titletxt = title.getText().toString();
                contenttxt = content.getText().toString();
                username = user.getUsername();
                int blogs = user.getInt("blogs")+1;

                Toast.makeText(IndexActivity.this, "no of Blogs "+user.getInt("blogs"), Toast.LENGTH_SHORT).show();

                if (titletxt.equals("") || contenttxt.equals(""))
                {
                    Toast.makeText(IndexActivity.this, "Please add some text in title or Blog Content", Toast.LENGTH_SHORT).show();
                }
                else {

                    ParseObject parseObject = new ParseObject("Blog");

                    parseObject.put("title", titletxt);
                    parseObject.put("content",contenttxt);
                    parseObject.put("user", username);
                    user.put("blogs", blogs);


                    dialog = ProgressDialog.show(getApplicationContext(),"Posting your blog","Please wait....",true,false);
                    user.saveInBackground();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(IndexActivity.this, "Successfully Posted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(IndexActivity.this, MyBlogActivity.class);
                            startActivity(i);
                            finish();
                            dialog.dismiss();
                        }
                    });



                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId())
        {
            case R.id.action_my_blogs:

//                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(),MyBlogActivity.class);
                startActivity(i);
                break;

            case R.id.action_search_user:

                Intent intent = new Intent(getApplicationContext(), SearchUsersActivity.class);
                startActivity(intent);
                break;

            case R.id.action_logout:

                dialog = ProgressDialog.show(getApplicationContext(), "Logout", "Please wait....", true, false);

                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {

                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        finish();
                        dialog.dismiss();
                    }
                });
                break;


        }

        return super.onOptionsItemSelected(item);
    }
}
