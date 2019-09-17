package com.android.myliverpool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myliverpool.R;
import com.android.myliverpool.models.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> product = new ArrayList<>();
    private Context ctx;

    public ProductAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setProduct(List<Product> product) {
        this.product.clear();
        this.product.addAll(product);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecipeViewHolder) holder).setData(product.get(position));
    }

    @Override
    public int getItemCount() {
        return (!product.isEmpty()) ? product.size() : 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.tv_nameProduct)
        TextView tvNameProduct;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Product product) {
            Glide.with(ctx).load(product.getLgImage()).into(ivProduct);
            tvNameProduct.setText(product.getProductDisplayName());
            tvPrice.setText(ctx.getString(R.string.price, product.getListPrice().toString()));
        }
    }

}

