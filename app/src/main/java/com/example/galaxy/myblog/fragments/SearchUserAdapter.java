package com.example.galaxy.myblog.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galaxy.myblog.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by galaxy on 08/10/15.
 */
public class SearchUserAdapter extends BaseAdapter
{

    Context context;
    List<ParseUser> parseUsers;
    LayoutInflater inflater;
    Bitmap imageBitmap;
    ViewHolder viewHolder;

    public SearchUserAdapter(Context context, List<ParseUser> parseUsers)
    {
        this.context = context;
        this.parseUsers = parseUsers;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return parseUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;


        view = convertView;

        if (view == null)
        {
            view = inflater.inflate(R.layout.search_single_user,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.list_username);
            viewHolder.email = (TextView) view.findViewById(R.id.list_email);
            viewHolder.blogCount = (TextView) view.findViewById(R.id.blog_count);
            viewHolder.image = (ImageView) view.findViewById(R.id.user_pic);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        String name = parseUsers.get(position).get("username").toString();
        String email = parseUsers.get(position).get("email").toString();
        int count = parseUsers.get(position).getInt("blogs");


        ParseFile imageFile = (ParseFile) parseUsers.get(position).get("photo_thumb");
        try {
            byte[] data = imageFile.getData();
            imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            viewHolder.image.setImageBitmap(imageBitmap);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.name.setText(name);
        viewHolder.email.setText(email);
        viewHolder.blogCount.setText(count+"");

        return view;
    }

    public class ViewHolder
    {
        public TextView name,email,blogCount;
        public ImageView image;
    }
}
