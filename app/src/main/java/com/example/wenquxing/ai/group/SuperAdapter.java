package com.example.wenquxing.ai.group;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wenquxing.R;
import com.example.wenquxing.ai.db.DBAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SuperAdapter extends GroupRecyclerAdapter<String,Exsuper>{

    private RequestManager mLoader;

    public SuperAdapter(Context context,int data) {
        super(context);
        mLoader = Glide.with(context.getApplicationContext());
        LinkedHashMap<String, List<Exsuper>> map = new LinkedHashMap<>();
        List<String> titles = new ArrayList<>();
        map.clear();
        map.put("今日事务", create(0,context,data));
        map.put("今日课程", create(1,context,data));
        titles.add("今日事务");
        titles.add("今日课程");
        resetGroups(map,titles);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SViewHolder(mInflater.inflate(R.layout.item_list_article, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Exsuper item, int position) {
        SViewHolder h = (SViewHolder) holder;
        h.mTextNa.setText(item.getSname());
        h.mTextContent.setText(item.getScontext());
        h.mTextstar.setText(String.valueOf(item.getSstar()));
        h.mTextfinish.setText(String.valueOf(item.getSfinish()));
        h.mTextSl.setText(item.getSloty());
        h.CH.setChecked(item.Sgetcomplite());
    }

    private static class SViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextNa;
        private TextView mTextContent;
        private TextView mTextSl;
        private TextView mTextstar;
        private TextView mTextfinish;

        private CheckBox CH;

        private SViewHolder(View itemView) {
            super(itemView);
            mTextNa = itemView.findViewById(R.id.tv_name);
            mTextContent = itemView.findViewById(R.id.tv_content);
            mTextstar = itemView.findViewById(R.id.tv_star);
            mTextfinish = itemView.findViewById(R.id.tv_finish);
            mTextSl = itemView.findViewById(R.id.tv_Sl);
            CH = itemView.findViewById(R.id.Finish);
        }
    }

    /**
     * 事务创建
     *
     */

    private static Exsuper create(int type, String Sname, String Sloty, String Scontext,
                                  boolean complite,int start,int finish) {
        Exsuper es = new Exsuper();
        es.setSname(Sname);
        es.setSloty(Sloty);
        es.setSstar(start);
        es.setSfinish(finish);
        es.setScontext(Scontext);
        es.setcomplite(complite);
        return es;
    }

    private List<Exsuper> create(int p, Context context,int data) {
        DBAdapter db = new DBAdapter(context);
        db.open();
        Exsuper pexsupers = new Exsuper();
        for (int i = 0; i < 3; i++) {
            pexsupers.setSid(i+1);
            pexsupers.setType(p);
            pexsupers.setSstar(i+1 + 5 % 3);
            pexsupers.setSfinish(i+1 + 7 % 3);
            pexsupers.setTimedata(data+(i%3));
            pexsupers.setSname(i+1 + "?");
            pexsupers.setScontext(i+1 + "OPOP");
            pexsupers.setcomplite1(1);
            pexsupers.setSloty(i+1 + "pppppppp");
            db.insert(pexsupers);
        }

        Exsuper exsuper[] = db.queryOneDate(data, p);
        List<Exsuper> list = new ArrayList<>();

        if (p == 0) {
            list.add(create(p, "新高区开发", "新闻", "新高区开展一系列xxx计划", false, 4, 6));
            if (exsuper.length != 0) {
                for (int i = 0; i < exsuper.length; i++) {
                    list.add(create(p, exsuper[i].getSname(), exsuper[i].getSloty(), exsuper[i].getScontext(),
                            exsuper[i].Sgetcomplite(), exsuper[i].getSstar(), exsuper[i].getSfinish()));
                }
            }

        } else if (p == 1) {
            list.add(create(p, "Math", "1111", "LIhua", false, 8, 9));
            if (exsuper.length != 0) {
                for (int i = 0; i < exsuper.length; i++) {
                    list.add(create(p, exsuper[i].getSname(), exsuper[i].getSloty(), exsuper[i].getScontext(),
                            exsuper[i].Sgetcomplite(), exsuper[i].getSstar(), exsuper[i].getSfinish()));
                }
            }
        }
        db.close();
        return list;
    }

}
