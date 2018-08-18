package com.example.admin.EduPlanet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.EduPlanet.R;
import com.example.admin.EduPlanet.activity.ItemDetailActivity;
import com.example.admin.EduPlanet.bean.Item;
import com.example.admin.EduPlanet.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.MainViewHolder> {

    private Context mContext;
    private List<Item> mList;

    public ItemRecyclerAdapter(List<Item> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        holder.mName.setText(mList.get(position).getUsername() == null ? "" : mList.get(position).getUsername());
        holder.mContent.setText((CharSequence)mList.get(position).getTitle());
        holder.mInstitute.setText(mList.get(position).getInstituteType());
        holder.mType.setText(mList.get(position).getContentType());
        Picasso.with(mContext).load(mList.get(position).getAvatar()).transform(new RoundedTransformation(9, 0)).into(holder.mAvatar);
        Picasso.with(mContext).load(mList.get(position).getPic() == null ? "www" : mList.get(position).getPic()).transform(new RoundedTransformation(9, 0)).into(holder.mPicture);
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                intent.putExtra("itemObjectId", mList.get(position).getObjectId());
                intent.putExtra("title", mList.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mContent;
        private ImageView mAvatar;
        private ImageView mPicture;
        private TextView mInstitute;
        private TextView mType;
        private CardView mItem;

        public MainViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_item_name);
            mContent = itemView.findViewById(R.id.tv_item_content);
            mAvatar = itemView.findViewById(R.id.iv_item_avatar);
            mPicture = itemView.findViewById(R.id.iv_item_pic);
            mInstitute = itemView.findViewById(R.id.tv_item_institute);
            mType = itemView.findViewById(R.id.tv_item_type);
            mItem = itemView.findViewById(R.id.item_main);
        }
    }
}
