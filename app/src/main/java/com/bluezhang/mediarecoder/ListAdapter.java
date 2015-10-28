package com.bluezhang.mediarecoder;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.jpardogo.android.flabbylistview.lib.FlabbyLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Random;

/**
 * Author: blueZhang
 * DATE:2015/10/28
 * Time: 13:17
 * email:bluezhang521@163.com
 */
public class ListAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mItems;
    private Random mRandomizer = new Random();

    public void setOnclic(View.OnClickListener onclic) {
        this.onclic = onclic;
    }

    private View.OnClickListener onclic;

    public ListAdapter(Context context, List<String> items) {
        super(context,0,items);
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int color = Color.argb(255, mRandomizer.nextInt(256), mRandomizer.nextInt(256), mRandomizer.nextInt(256));
        ((FlabbyLayout)convertView).setFlabbyColor(color);
        holder.text.setOnClickListener(onclic);
        holder.text.setTag(position);
        holder.text.setText(getItem(position));
        holder.text2.setText(getItem(position));
        holder.text3.setText(getItem(position));
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.text)
        TextView text;
        @ViewInject(R.id.text2)
        TextView text2;
        @ViewInject(R.id.text3)
        TextView text3;


        public ViewHolder(View view) {
            ViewUtils.inject(this, view);
        }
    }
}