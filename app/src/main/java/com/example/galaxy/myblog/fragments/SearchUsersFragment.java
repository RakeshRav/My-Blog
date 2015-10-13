package com.example.galaxy.myblog.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.galaxy.myblog.R;
import com.example.galaxy.myblog.UserDetailsActivity;
import com.example.galaxy.myblog.constants.UserConastants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by galaxy on 08/10/15.
 */

public class SearchUsersFragment extends Fragment
{

    List<ParseUser> parseUsers,parseSearch;
    FrameLayout msgLayout;
    ListView listView;
    SearchView searchView;
    ParseQuery<ParseUser> query,querySearch;
    ParseUser currentUser,listParseUser;
    ProgressDialog dialog;
    Bitmap imageBitmap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View userView = inflater.inflate(R.layout.search_fragment_list, container, false);

        msgLayout = (FrameLayout) userView.findViewById(R.id.msg);

        listView = (ListView) userView.findViewById(R.id.user_list);

        searchView = (SearchView) userView.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);

        currentUser = ParseUser.getCurrentUser();

        query = ParseUser.getQuery();

        query.whereNotEqualTo("username",currentUser.getUsername());
        query.addDescendingOrder("blogs");


        dialog = ProgressDialog.show(getActivity(),"Users","Loading Users....",true,false);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                parseUsers = objects;
                listUsers();
                dialog.dismiss();
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String querytxt) {

                String regex = "%" + querytxt + "%";
//                Toast.makeText(getActivity(), ""+regex, Toast.LENGTH_SHORT).show();

                if (!querytxt.equals("")) {
                    querySearch = ParseUser.getQuery();
                    querySearch.whereNotEqualTo("username", currentUser.getUsername());
                    querySearch.whereContains("username", querytxt);

                    try {

                        parseSearch = querySearch.find();

                        if (parseSearch.size() > 0) {
                            msgLayout.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
//                            Toast.makeText(getActivity(), "Users : "+parseSearch.size()+" Found", Toast.LENGTH_SHORT).show();

                            SearchUserAdapter adapter = new SearchUserAdapter(getActivity(), parseSearch);
                            listView.setAdapter(adapter);
                        } else {
                            listView.setVisibility(View.GONE);
                            msgLayout.setVisibility(View.VISIBLE);
//                            Toast.makeText(getActivity(), "No Users Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    listUsers();
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name,email;
                listParseUser = parseUsers.get(position);
                name = listParseUser.get("username").toString();
                email = listParseUser.get("email").toString();

                ParseFile file = (ParseFile)listParseUser.get("photo_thumb");
                try {
                    byte[] image = file.getData();
                    imageBitmap = BitmapFactory.decodeByteArray(image,0,image.length);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                UserConastants.shared().setName(name);
                UserConastants.shared().setEmail(email);
                UserConastants.shared().setImageBitmap(imageBitmap);

                Intent i = new Intent(getActivity(), UserDetailsActivity.class);
                startActivity(i);
            }
        });

        return userView;
    }

    public void listUsers()
    {
        if (parseUsers.size()>0)
            {
                msgLayout.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Users : "+parseUsers.size()+" Found", Toast.LENGTH_SHORT).show();

                SearchUserAdapter adapter = new SearchUserAdapter(getActivity(),parseUsers);
                listView.setAdapter(adapter);

            }
            else {
                listView.setVisibility(View.GONE);
                msgLayout.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "No Users Found", Toast.LENGTH_SHORT).show();
            }


    }
}
