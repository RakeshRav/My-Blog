package com.example.galaxy.myblog.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.galaxy.myblog.BlogDetailsActivity;
import com.example.galaxy.myblog.R;
import com.example.galaxy.myblog.constants.UserConastants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by galaxy on 12/10/15.
 */
public class UserDetailsFragment extends Fragment
{

    ListView listView;
    TextView name,email;
    Button follow;
    List<ParseObject> parseObjects;
    String nameConstant,emailConstant;
    ProgressDialog dialog;
    TextView no_blogs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_details_fragment, container, false);


        name = (TextView) view.findViewById(R.id.detail_name);
        email = (TextView) view.findViewById(R.id.detail_email);

        nameConstant = UserConastants.shared().getName();
        emailConstant = UserConastants.shared().getEmail();

        name.setText(nameConstant);
        email.setText(emailConstant);

        follow = (Button) view.findViewById(R.id.follow);


        listView = (ListView) view.findViewById(R.id.list_view);
        no_blogs = (TextView) view.findViewById(R.id.no_blogs);

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Blog");

        parseQuery.whereEqualTo("user", nameConstant);


        dialog = ProgressDialog.show(getActivity(),"Loading User", "Please Wait....",true,false);

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                parseObjects = objects;

                if (parseObjects.size()>0) {
                    no_blogs.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    BlogListAdapter adapter = new BlogListAdapter(getActivity(), parseObjects);
                    listView.setAdapter(adapter);
                }
                else {
                    no_blogs.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                dialog.cancel();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String title = parseObjects.get(position).get("title").toString();

//                parseObjects.get(position).deleteInBackground();
//                    refresh();
                Intent i = new Intent(getActivity(), BlogDetailsActivity.class);
                i.putExtra("title", title);
                startActivity(i);
            }
        });


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow.getText().toString().equals("Follow")) {
                    follow.setText("Unfollow");
                }

                else if(follow.getText().toString().equals("Unfollow"))
                {
                    follow.setText("Follow");
                }
            }
        });
        return view;
    }
}
