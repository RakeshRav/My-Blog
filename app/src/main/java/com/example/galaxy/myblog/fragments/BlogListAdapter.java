package com.example.galaxy.myblog.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.galaxy.myblog.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by galaxy on 08/10/15.
 */
public class BlogListAdapter extends BaseAdapter
{

    Context context;
    List<ParseObject> parseObjects;
    LayoutInflater inflater;

    public BlogListAdapter(Context context,List<ParseObject> parseObjects)
    {
        this.context = context;
        inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ParseQuery<ParseObject> blogQuery = new ParseQuery<ParseObject>("Blog");

        this.parseObjects = parseObjects;

    }

    @Override
    public int getCount() {
        return parseObjects.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        ViewHolder viewHolder;

        view = convertView;

        if (view == null)
        {
            view = inflater.inflate(R.layout.blog_item_layout,parent,false);

            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) view.findViewById(R.id.title_name);
            viewHolder.content = (TextView) view.findViewById(R.id.blog_content);

            view.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        String title = parseObjects.get(position).get("title").toString();
        String content = parseObjects.get(position).get("content").toString();

        viewHolder.title.setText(title);
        viewHolder.content.setText(content);

        return view;
    }

    public class ViewHolder
    {
        public TextView title,content;
    }
}
