package com.fpuente.simple_ripley.component;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpuente.simple_ripley.ProductDetail;
import com.fpuente.simple_ripley.R;
import com.fpuente.simple_ripley.model.Product;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Product> Items = new ArrayList<>();
    OnItemClickListener mItemClickListener;
    Context mContext;

    public ItemAdapter(Context context) {
        Items = new ArrayList<>();
        mContext = context;
    }

    public ItemAdapter(List<Product> items) {
        this.Items = items;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtNormalPrice,txtInternetPrice, txtRipleyPrice,txtTrademark,txtDiscount;
        ImageView imgProduct;

        public ItemViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            txtName =  view.findViewById(R.id.txt_name);
            txtNormalPrice = view.findViewById(R.id.txt_normal_price);
            txtInternetPrice = view.findViewById(R.id.txt_internet_price);
            txtRipleyPrice = view.findViewById(R.id.txt_ripley_price);
            txtTrademark   = view.findViewById(R.id.txt_trademark);
            txtDiscount    = view.findViewById(R.id.txt_discount);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*Intent registerActivity = new Intent(mContext, ProductDetail.class);
            mContext.startActivity(registerActivity);*/

        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Product model = Items.get(position);

        Glide.with(mContext)
                .load(model.getUrl_img())
                .centerCrop()
                .into(holder.imgProduct);

        holder.txtName.setText( model.getName());
        holder.txtDiscount.setText("-"+model.getDisc()+"%");
        holder.txtTrademark.setText(model.getTrademark());
        holder.txtRipleyPrice.setText(model.getRipley_card_price());
        holder.txtInternetPrice.setText(model.getInternet_price());
        holder.txtNormalPrice.setText(model.getNormal_price());

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setItems(List<Product> items){
        Items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<Product> items){
        Items.addAll(items);
        notifyDataSetChanged();
    }
}
