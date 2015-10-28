package com.bluezhang.mediarecoder;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends ListActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int NUM_LIST_ITEM = 500;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        List<String> items = getListItems();
        mAdapter = new com.bluezhang.mediarecoder.ListAdapter(this,items);
       mAdapter.setOnclic(this);
        setListAdapter(mAdapter);
        getListView().setSelection(items.size() / 2);
        getListView().setOnItemClickListener(this);
    }

    private ArrayList<String> getListItems() {
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<NUM_LIST_ITEM;i++){
            list.add("Item"+i);
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        TextView te = (TextView) view.findViewById(R.id.text);
//        int tag = (int) te.getTag();
//        if(tag == position){
//            te.setText("ddddddddddddd");
//        }

        Toast.makeText(this, "----000000---", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "点你妹", Toast.LENGTH_SHORT).show();
        TextView vi = (TextView)v;
        vi.setText("点你妹呀？？？？？？？");
    }
}
