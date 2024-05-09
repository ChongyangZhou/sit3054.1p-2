package com.example.task41.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.task41.Bean.RichengBean;
import com.example.task41.Database.DBUtils;
import com.example.task41.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public class MyAdapter extends BaseAdapter {
    private List<RichengBean> mList;
    private LayoutInflater layoutInflater;
    private DBUtils dbUtils;
    private Context context;
    private int month = Calendar.getInstance().get(Calendar.MONTH)+1;
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public MyAdapter(Context context, List<RichengBean> list){
        this.context = context;
        mList = list;
        layoutInflater = LayoutInflater.from(context);
        dbUtils = new DBUtils(context);
    }
    public void Refresh(List<RichengBean> list){
        mList = list;
        notifyDataSetChanged();

    }
    @Override
    public int getCount() {
        return mList.size() ;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(int j, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item, null);
            viewHolder.textView = (TextView)view.findViewById(R.id.beizhu);
             viewHolder.textView2 = (TextView)view.findViewById(R.id.leibie);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.date);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(mList.get(j).getShixiang());
        viewHolder.textView2.setText(mList.get(j).gettitle()+":");
        viewHolder.textView4.setText(dateToString(mList.get(j).getDate()));
        return view;
    }
    public String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public final class ViewHolder {
        public TextView textView, textView1, textView2, textView3,textView4;
        public ImageView imageView,imageView1;
        public CheckBox checkBox;
    }
}
