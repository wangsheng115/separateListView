
package com.chiemy.pullseparate;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private String[] arr = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
            "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56"
    };
    private SeparateBottomListView mListView;
    private LayoutInflater mLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (SeparateBottomListView) findViewById(R.id.pullExpandListView1);
        mListView.setAdapter(new MyBaseAdapter());
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(MainActivity.this, arr[position],
                        Toast.LENGTH_SHORT).show();
            }
        });

        mLater = getLayoutInflater();
    }

    private class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            RelativeLayout rl;
            if (convertView == null) {
                rl = (RelativeLayout) mLater.inflate(R.layout.item, null);
            } else {
                rl = (RelativeLayout) convertView;
            }

            TextView tv = (TextView) rl.findViewById(R.id.text);
            tv.setText(arr[position]);

            return rl;
        }

    }

}
