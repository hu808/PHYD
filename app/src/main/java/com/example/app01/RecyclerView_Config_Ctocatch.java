package com.example.app01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config_Ctocatch {
    private Context mContext;
    private CToCatchsAdapter mCToCatchsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Stringtocatch> books, List<String> keys){
        mContext = context;
        mCToCatchsAdapter = new CToCatchsAdapter(books, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mCToCatchsAdapter);
    }

    class CToCatchItemView extends RecyclerView.ViewHolder{
        private TextView m因子;
        private TextView m門檻值;

        private String key;

        public CToCatchItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.stringctocatch, parent, false));

            m因子 = (TextView)itemView.findViewById(R.id.c因子_text);
            m門檻值 = (TextView)itemView.findViewById(R.id.c門檻值_text);

        }
        public void bind(Stringtocatch book, String key){
            m因子.setText(book.get因子());
            m門檻值.setText(book.get門檻值());
            this.key = key;
        }
    }
    class CToCatchsAdapter extends RecyclerView.Adapter<CToCatchItemView>{
        private List<Stringtocatch> mCToCatchList;
        private List<String> mKeys;

        public CToCatchsAdapter(List<Stringtocatch> mCToCatchList, List<String> mKeys) {
            this.mCToCatchList = mCToCatchList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public CToCatchItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CToCatchItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CToCatchItemView holder, int position) {
            holder.bind(mCToCatchList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mCToCatchList.size();
        }
    }
}
