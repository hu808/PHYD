package com.example.app01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config_History2 {
    private Context mContext;
    private RecyclerView_Config_History2.History2sAdapter mHistory2sAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<History2> history2s, List<String> keys){
        mContext = context;
        mHistory2sAdapter = new RecyclerView_Config_History2.History2sAdapter(history2s, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mHistory2sAdapter);
    }

    class History2ItemView extends RecyclerView.ViewHolder{
        private TextView m危險項目;
        private TextView m時間點;
        private ImageView imageView_危險項目;

        private String key;

        public History2ItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.history2_list_item, parent, false));

            m危險項目 = (TextView)itemView.findViewById(R.id.危險項目_textView);
            m時間點 = (TextView)itemView.findViewById(R.id.時間點_textView);
            imageView_危險項目 = itemView.findViewById(R.id.imageView_危險項目);

        }
        public void bind(History2 history2, String key){
            m危險項目.setText(history2.get危險項目());
            m時間點.setText(history2.get時間點());
            if(m危險項目.getText()+""=="闖紅燈") {
                imageView_危險項目.setImageResource(R.drawable.traffic);
            }else if(m危險項目.getText()+""=="重油") {
                imageView_危險項目.setImageResource(R.drawable.f2);
            }else if(m危險項目.getText()+""=="急煞") {
                imageView_危險項目.setImageResource(R.drawable.hand);
            }else if(m危險項目.getText()+""=="夜騎") {
                imageView_危險項目.setImageResource(R.drawable.moon);
            }else if(m危險項目.getText()+""=="雨騎") {
                imageView_危險項目.setImageResource(R.drawable.rain);
            }else {
                imageView_危險項目.setImageResource(R.drawable.dangericn);
            }
            //Toast.makeText(mContext.getApplicationContext(),m危險項目.getText()+"",Toast.LENGTH_SHORT).show();
            this.key = key;
        }
    }
    class History2sAdapter extends RecyclerView.Adapter<RecyclerView_Config_History2.History2ItemView>{
        private List<History2> mHistory2List;
        private List<String> mKeys;

        public History2sAdapter(List<History2> mHistory2List, List<String> mKeys) {
            this.mHistory2List = mHistory2List;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public RecyclerView_Config_History2.History2ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView_Config_History2.History2ItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_Config_History2.History2ItemView holder, int position) {
            holder.bind(mHistory2List.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mHistory2List.size();
        }
    }
}
