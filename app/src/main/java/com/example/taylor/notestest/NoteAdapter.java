package com.example.taylor.notestest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylor on 2020/6/11.
 */

public class NoteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;

    private List<Note> backlist;//备份原始数据
    private List<Note> noteList;//数据会改变，所以要有变量来备份原始数据
    private MyFilter myFilter;

    public NoteAdapter(Context mContext, List<Note> noteList) {
        this.mContext = mContext;
        this.noteList = noteList;
        backlist = noteList;
    }


    public NoteAdapter() {
        super();
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    class MyFilter extends Filter {

            //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults result = new FilterResults();
                List<Note> list;
                if (TextUtils.isEmpty(charSequence)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                    list = backlist;
                } else {//否则把符合条件的数据对象添加到集合中
                    list = new ArrayList<>();
                    for (Note note : backlist) {
                        if (note.getContent().contains(charSequence)) {
                            list.add(note);
                        }

                    }
                }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到Filter Results的count变量中
            return result;
        }

        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            noteList = (List<Note>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mContext.setTheme(R.style.DayTheme);
        View v = View.inflate(mContext, R.layout.note_layout, null);
        TextView tv_content = (TextView)v.findViewById(R.id.tv_content);
        TextView tv_time = (TextView)v.findViewById(R.id.tv_time);

        //Set text for TextView
        String allText = noteList.get(position).getContent();
        /*if (sharedPreferences.getBoolean("noteTitle" ,true))
            tv_content.setText(allText.split("\n")[0]);*/
        tv_content.setText(allText);
        tv_time.setText(noteList.get(position).getTime());

        //Save note id to tag
        v.setTag(noteList.get(position).getId());

        return v;


    }
}
