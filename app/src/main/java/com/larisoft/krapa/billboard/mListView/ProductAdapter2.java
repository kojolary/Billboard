package com.larisoft.krapa.billboard.mListView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.larisoft.krapa.billboard.R;
import com.larisoft.krapa.billboard.mData.Product;
import com.larisoft.krapa.billboard.mData.Product2;

import java.util.List;

/**
 * Created by emma on 10/18/2017.
 */

public class ProductAdapter2 extends RecyclerView.Adapter<ProductAdapter2.ProductViewHolder> {

    private Context mCtx;
    private List<Product2> productList;

    public ProductAdapter2(Context mCtx, List<Product2> productList) {
        this.mCtx =  mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_layout_billboard, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product2 product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.textViewID.setText(product.getId());
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewShortDesc.setText(product.getshort_description());
        holder.textViewRating.setText(String.valueOf(product.getRating()));
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewPerson.setText(String.valueOf(product.getinputPerson()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewID,textViewPrice ,textViewPerson;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.textViewId);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewPerson = itemView.findViewById(R.id.textViewInputPerson);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}