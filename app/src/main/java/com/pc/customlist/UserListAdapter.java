package com.pc.customlist;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class UserListAdapter extends BaseAdapter {

    private static final String TAG = UserListAdapter.class.getName();
    private Vector<Book> items;

    public UserListAdapter(Vector<Book> items) {
        Log.i(TAG, TAG);
        this.items = items;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_user, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.nameTV);
            holder.headingLL = (LinearLayout) convertView.findViewById(R.id.headingLL);
            holder.headingTV = (TextView) convertView.findViewById(R.id.headingTV);
            holder.nameLL = (LinearLayout) convertView.findViewById(R.id.nameLL);
            convertView.setTag(holder);
        }
        else {
			holder = (ViewHolder) convertView.getTag();
		}

        if (position < items.size()) {
            final Book subsidies = items.get(position);
            if (subsidies != null && (subsidies.getTitle().length() == 1)) {

                global.arrayforTitle.add(position);
                holder.nameLL.setVisibility(View.GONE);
                holder.headingLL.setBackgroundColor(Color.parseColor("#f3f3f3"));
                holder.headingLL.setVisibility(View.VISIBLE);
                holder.headingTV.setText(subsidies.getTitle());
            } else {
                holder.nameLL.setVisibility(View.VISIBLE);
                holder.headingLL.setVisibility(View.GONE);
                holder.name.setText(subsidies.getTitle());

            }
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView name, headingTV;
        LinearLayout nameLL, headingLL;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
