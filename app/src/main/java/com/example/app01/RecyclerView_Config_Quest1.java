package com.example.app01;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecyclerView_Config_Quest1 {
    private int count=0;
    private Context mContext;
    private History2sAdapter mHistory2sAdapter;
    private List<String> Quests= new ArrayList<>();
    public void setConfig(RecyclerView recyclerView, Context context, List<History2> history2s, List<String> keys){
        mContext = context;
        mHistory2sAdapter = new History2sAdapter(history2s, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mHistory2sAdapter);
        GlobalVariable gv = (GlobalVariable) mContext.getApplicationContext();
        gv.setrQcount(0);
    }

    class History2ItemView extends RecyclerView.ViewHolder{
        private TextView m危險項目;
        private TextView m時間點;
        private ImageView imageView_危險項目;
        private int isClick=0;


        private String key;

        public History2ItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.history2_list_item, parent, false));

            m危險項目 = (TextView)itemView.findViewById(R.id.危險項目_textView);
            m時間點 = (TextView)itemView.findViewById(R.id.時間點_textView);
            imageView_危險項目 = itemView.findViewById(R.id.imageView_危險項目);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(isClick==0){
                        isClick=1;
                        itemView.setBackgroundColor(Color.RED);
                        count=count+1;
                        Quests.add(key);
                    }else {
                        isClick=0;
                        itemView.setBackgroundColor(Color.parseColor("#1B00BCD4"));
                        count=count-1;
                        Iterator<String> iterator = Quests.iterator();
                        while (iterator.hasNext()) {
                            String value = iterator.next();
                            if (value.equals(key)) {
                                iterator.remove();
                            }
                        }
                    }
                   //Toast.makeText(mContext.getApplicationContext(),Quests+"",Toast.LENGTH_SHORT).show();
                    GlobalVariable gv = (GlobalVariable) mContext.getApplicationContext();
                    gv.setrQcount(count);
                    gv.setQuests(Quests);

//                    Intent intent = new Intent(mContext, rHistoryPage2.class);
//                    intent.putExtra("key",key);
//                    intent.putExtra("開始時間",t_start_time_data.getText().toString());
//                    intent.putExtra("年月日",m結束時間.getText().toString());
//                    intent.putExtra("category",m開始時間.getText().toString());
//                    intent.putExtra("isbn",m駕駛紀錄id.getText().toString());
//
//                    mContext.startActivity(intent);
                }
            });

        }
        public void bind(History2 history2, String key){
            m危險項目.setText(history2.get危險項目());
            m時間點.setText(history2.get時間點());
            if(history2.get危險項目()=="闖紅燈") {
                imageView_危險項目.setImageResource(R.drawable.traffic);
            }else if(history2.get危險項目()=="重油") {
                imageView_危險項目.setImageResource(R.drawable.hand);
            }else if(history2.get危險項目()=="急煞") {
                imageView_危險項目.setImageResource(R.drawable.f2);
            }else if(history2.get危險項目()=="夜騎") {
                imageView_危險項目.setImageResource(R.drawable.moon);
            }else if(history2.get危險項目()=="雨騎") {
                imageView_危險項目.setImageResource(R.drawable.rain);
            }else {
                imageView_危險項目.setImageResource(R.drawable.dangericn);
            }
            this.key = key;
        }
    }
    class History2sAdapter extends RecyclerView.Adapter<History2ItemView>{
        private List<History2> mHistory2List;
        private List<String> mKeys;

        public History2sAdapter(List<History2> mHistory2List, List<String> mKeys) {
            this.mHistory2List = mHistory2List;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public History2ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new History2ItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull History2ItemView holder, int position) {
            holder.bind(mHistory2List.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mHistory2List.size();
        }
    }
}