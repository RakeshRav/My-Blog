package com.example.galaxy.myblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.galaxy.myblog.fragments.BlogListFragment;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MyBlogActivity extends AppCompatActivity {

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_list);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new BlogListFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
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
           case R.id.action_logout:

               dialog = ProgressDialog.show(MyBlogActivity.this,"Logout","Please wait....",true,false);

               ParseUser.logOutInBackground(new LogOutCallback() {
                   @Override
                   public void done(ParseException e) {

                       Intent i = new Intent(MyBlogActivity.this, Login.class);
                       startActivity(i);
                       finish();
                       dialog.dismiss();
                   }
               });

       }

        return super.onOptionsItemSelected(item);
    }
}
