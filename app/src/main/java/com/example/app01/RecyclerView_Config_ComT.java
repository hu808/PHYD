package com.example.app01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config_ComT {

    private Context mContext;
    private ComsAdapter mComsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Com> coms, List<String> keys){
        mContext = context;
        mComsAdapter = new ComsAdapter(coms, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mComsAdapter);
    }

    class ComItemView extends RecyclerView.ViewHolder{
        private TextView musername;
        private TextView memail;

        private String key;

        public ComItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.comtitle_list_item, parent, false));

            musername = (TextView)itemView.findViewById(R.id.usernameT_textView);
            memail = (TextView)itemView.findViewById(R.id.emailT_textView);

        }
        public void bind(Com com, String key){
            musername.setText(com.getUsername());
            memail.setText(com.getEmail());
            this.key = key;
        }
    }
    class ComsAdapter extends RecyclerView.Adapter<ComItemView>{
        private List<Com> mComList;
        private List<String> mKeys;

        public ComsAdapter(List<Com> mComList, List<String> mKeys) {
            this.mComList = mComList;
            this.mKeys = mKeys;
        }

        @androidx.annotation.NonNull
        @Override
        public ComItemView onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
            return new ComItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ComItemView holder, int position) {
            holder.bind(mComList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mComList.size();
        }
    }
}
