package com.example.app01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config_History1 {
    private Context mContext;
    private History1sAdapter mHistory1sAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<History1> history1s, List<String> keys){
        mContext = context;
        mHistory1sAdapter = new History1sAdapter(history1s, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mHistory1sAdapter);
    }

    class History1ItemView extends RecyclerView.ViewHolder{
        private TextView m開始結束時間;
        private TextView m本次危險次數;

        private String key;

        public History1ItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.history1_list_item, parent, false));

            m開始結束時間 = (TextView)itemView.findViewById(R.id.開始結束時間_textView);
            m本次危險次數 = (TextView)itemView.findViewById(R.id.本次危險次數_textView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, rHistoryPage2.class);
                    intent.putExtra("key",key);
                    //intent.putExtra("開始時間",t_start_time_data.getText().toString());
                    //intent.putExtra("年月日",m結束時間.getText().toString());
                    //intent.putExtra("category",m開始時間.getText().toString());
                    //intent.putExtra("isbn",m駕駛紀錄id.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }
        public void bind(History1 history1, String key){
            m開始結束時間.setText(history1.get開始時間() + " - " + history1.get結束時間());
            m本次危險次數.setText("危險次數: "+history1.get本次危險次數());
            this.key = key;
        }
    }
    class History1sAdapter extends RecyclerView.Adapter<History1ItemView>{
        private List<History1> mHistory1List;
        private List<String> mKeys;

        public History1sAdapter(List<History1> mHistory1List, List<String> mKeys) {
            this.mHistory1List = mHistory1List;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public History1ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new History1ItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull History1ItemView holder, int position) {
            holder.bind(mHistory1List.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mHistory1List.size();
        }
    }
}
