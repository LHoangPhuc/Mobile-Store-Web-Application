package com.example.testandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testandroid.Activity.CartActivity;
import com.example.testandroid.Entities.Product;
import com.example.testandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mContext;
    private List<Product> mProductList;
    private OnRemoveButtonClickListener mListener;

    public CartAdapter(Context context, List<Product> productList, OnRemoveButtonClickListener listener) {
        mContext = context;
        mProductList = productList;
        mListener = listener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final Product currentProduct = mProductList.get(position);
        holder.productNameTextView.setText(currentProduct.getName());
        holder.productPriceTextView.setText(String.valueOf(currentProduct.getPrice()));
        holder.productQuantityTextView.setText(String.valueOf(currentProduct.getQuantity()));

        // Calculate total price for this product and set it to productTotalPriceTextView
        double totalPrice = currentProduct.getPrice() * currentProduct.getQuantity();
        holder.productTotalPriceTextView.setText(String.valueOf(totalPrice));

        // Load image using Picasso
        Picasso.get().load(currentProduct.getImage()).into(holder.productImageView);

        // Set up click listener for remove button
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRemoveButtonClicked(currentProduct);
            }
        });

        // Set up click listener for decrease quantity button
        holder.decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProduct.getQuantity() > 1) {
                    currentProduct.setQuantity(currentProduct.getQuantity() - 1);
                    ((CartActivity)mContext).updateTotalCost(); // Update total cost
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Số lượng sản phẩm phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up click listener for increase quantity button
        holder.increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProduct.setQuantity(currentProduct.getQuantity() + 1);
                notifyDataSetChanged();
                ((CartActivity)mContext).updateTotalCost(); // Update total cost
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public interface OnRemoveButtonClickListener {
        void onRemoveButtonClicked(Product product);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productQuantityTextView;
        TextView productTotalPriceTextView;
        ImageView productImageView;
        Button removeButton;
        ImageView decreaseQuantityButton;
        ImageView increaseQuantityButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name);
            productPriceTextView = itemView.findViewById(R.id.product_price);
            productQuantityTextView = itemView.findViewById(R.id.item_giohang_soluong);
            productTotalPriceTextView = itemView.findViewById(R.id.item_giohang_giasp2);
            productImageView = itemView.findViewById(R.id.product_image);
            removeButton = itemView.findViewById(R.id.delete_cart);
            decreaseQuantityButton = itemView.findViewById(R.id.item_giohang_tru);
            increaseQuantityButton = itemView.findViewById(R.id.item_giohang_cong);
        }
    }
}