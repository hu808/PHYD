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

public class RecyclerView_Config_Com{

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
        private TextView muid;

        private String key;

        public ComItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.com_list_item, parent, false));

            musername = (TextView)itemView.findViewById(R.id.username_textView);
            memail = (TextView)itemView.findViewById(R.id.email_textView);
            muid = (TextView)itemView.findViewById(R.id.uid_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, cCustomerScore.class);
                    intent.putExtra("key",key);
                    intent.putExtra("uid",muid.getText().toString());
                    //intent.putExtra("年月日",m結束時間.getText().toString());
                    //intent.putExtra("category",m開始時間.getText().toString());
                    //intent.putExtra("isbn",m駕駛紀錄id.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }
        public void bind(Com com, String key){
            musername.setText(com.getUsername());
            memail.setText(com.getEmail());
            muid.setText(com.getUid());
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

        @NonNull
        @Override
        public ComItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
