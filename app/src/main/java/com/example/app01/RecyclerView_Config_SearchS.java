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

public class RecyclerView_Config_SearchS {
    private Context mContext;
    private SearchSsAdapter mSearchSsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<SearchS1> searchS1s, List<String> keys){
        mContext = context;
        mSearchSsAdapter = new SearchSsAdapter(searchS1s, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mSearchSsAdapter);
    }

    class SearchSItemView extends RecyclerView.ViewHolder{
        private TextView m月份;
        private TextView m安全分數;

        private String key;

        public SearchSItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.searchscore_list_item, parent, false));

            m月份 = (TextView)itemView.findViewById(R.id.月份_textView);
            m安全分數 = (TextView)itemView.findViewById(R.id.安全分數_textView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, rScorePage.class);
                    intent.putExtra("key",key);
                    intent.putExtra("月份",m月份.getText().toString());
                    //intent.putExtra("年月日",m結束時間.getText().toString());
                    //intent.putExtra("category",m開始時間.getText().toString());
                    //intent.putExtra("isbn",m駕駛紀錄id.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }
        public void bind(SearchS1 searchS1s, String key){
            m月份.setText(searchS1s.get月份());
            m安全分數.setText(searchS1s.get安全分數()+"");
            this.key = key;
        }
    }
    class SearchSsAdapter extends RecyclerView.Adapter<SearchSItemView>{
        private List<SearchS1> mSearchSList;
        private List<String> mKeys;

        public SearchSsAdapter(List<SearchS1> searchS1s, List<String> mKeys) {
            this.mSearchSList = searchS1s;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public SearchSItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SearchSItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchSItemView holder, int position) {
            holder.bind(mSearchSList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mSearchSList.size();
        }
    }
}