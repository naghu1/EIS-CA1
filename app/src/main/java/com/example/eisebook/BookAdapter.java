package com.example.eisebook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {

    ArrayList<Book> books;
    Activity activity;
    LayoutInflater inflater;

    public BookAdapter(ArrayList<Book> books, Activity activity) {
        this.books = books;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        if(inflater == null)
        {
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView ==null)
        {
            convertView = inflater.inflate(R.layout.item_book, null);
        }

        TextView sl = (TextView)convertView.findViewById(R.id.sl);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView details = (TextView)convertView.findViewById(R.id.details);

        sl.setText((position+1)+".");
        name.setText(books.get(position).title);
        details.setText(books.get(position).details);

        return convertView;
    }
}
