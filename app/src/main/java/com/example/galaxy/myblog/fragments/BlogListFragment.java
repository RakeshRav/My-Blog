package com.example.galaxy.myblog.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galaxy.myblog.BlogDetailsActivity;
import com.example.galaxy.myblog.IndexActivity;
import com.example.galaxy.myblog.R;
import com.example.galaxy.myblog.SearchUsersActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by galaxy on 07/10/15.
 */
public class BlogListFragment extends Fragment
{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    ListView listView;
    TextView msg;

    List<ParseObject> parseObjects;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



       View blogList = inflater.inflate(R.layout.blog_list_fragment, container,false);
        listView = (ListView) blogList.findViewById(R.id.blog_list_items);
        msg = (TextView) blogList.findViewById(R.id.connection);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(0).isConnected() || connectivityManager.getNetworkInfo(1).isConnected())
        {
            refresh();
        }
        else {
            msg.setText("No Internet Connection");
            msg.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);

            Toast.makeText(getActivity(), "Not Connencted", Toast.LENGTH_SHORT).show();
//            Log.i("Network","Not Connected");
        }
//        Toast.makeText(MyBlogActivity.this, "Timeline", Toast.LENGTH_SHORT).show();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                String title = parseObjects.get(position).get("title").toString();

//                parseObjects.get(position).deleteInBackground();
//                    refresh();
                  Intent i = new Intent(getActivity(), BlogDetailsActivity.class);
                i.putExtra("title",title);
                startActivity(i);
            }
        });



        return blogList;
    }

    public void refresh()
    {

//        Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
//            Log.i("Network","Connected");
        ParseQuery<ParseObject> blogQuery = new ParseQuery<ParseObject>("Blog");

        parseObjects = null;

        dialog = ProgressDialog.show(getActivity(),"My Blogs","Finding Your Blogs....",true,false);
            blogQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    parseObjects = objects;

                    if (parseObjects.size() > 0) {
                        BlogListAdapter adapter = new BlogListAdapter(getActivity(), parseObjects);
                        msg.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(adapter);

                    } else {
                        msg.setText("No Blogs Found");
                        msg.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }

                    dialog.dismiss();
                }
            });




    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.i("menu","success");
        inflater.inflate(R.menu.myblogs,menu);
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_add:

//                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(),IndexActivity.class);
                startActivity(i);
                break;

            case R.id.action_search_user:

                Intent intent = new Intent(getActivity(), SearchUsersActivity.class);
                startActivity(intent);
                break;

                
        }
        return super.onOptionsItemSelected(item);
    }
}
