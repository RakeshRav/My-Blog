package com.example.galaxy.myblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {

    String objectId;
    List<ParseObject> parseObjects = null;

    TextView title,content;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        title = (TextView) findViewById(R.id.title_blog);
        content = (TextView) findViewById(R.id.content_blog);

        Intent i = getIntent();
        objectId = i.getStringExtra("objectId");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Blog");

        parseQuery.whereEqualTo("objectId", objectId);

        dialog = ProgressDialog.show(BlogDetailsActivity.this,"Blog","Loading Details",true,false);

           parseQuery.findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(List<ParseObject> objects, ParseException e) {

                   parseObjects = objects;

                   Toast.makeText(BlogDetailsActivity.this, ""+objects.size(), Toast.LENGTH_SHORT).show();
                   String titletxt = parseObjects.get(0).get("title").toString();
                   String contenttxt = parseObjects.get(0).get("content").toString();

                   title.setText(titletxt);
                   content.setText(contenttxt);
                   dialog.dismiss();
               }
           });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blog_details, menu);
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
