package com.example.testandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testandroid.Entities.Product;
import com.example.testandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private List<Product> mProductList;
    private Product mSelectedProduct;
    private OnDeleteButtonClickListener mListener;
    private OnAddToCartButtonClickListener mAddToCartListener;
    private OnDetailButtonClickListener mDetailListener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClicked(int productId);
    }

    public interface OnAddToCartButtonClickListener {
        void onAddToCartButtonClicked(Product product);
    }

    public interface OnDetailButtonClickListener {
        void onDetailButtonClicked(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnDeleteButtonClickListener deleteListener, OnAddToCartButtonClickListener addToCartListener, OnDetailButtonClickListener detailListener) {
        super(context, 0, productList);
        mContext = context;
        mProductList = productList;
        mListener = deleteListener;
        mAddToCartListener = addToCartListener;
        mDetailListener = detailListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        final Product currentProduct = mProductList.get(position);

        TextView idTextView = listItem.findViewById(R.id.product_id);
        idTextView.setText(String.valueOf(currentProduct.getId()));

        TextView nameTextView = listItem.findViewById(R.id.product_name);
        nameTextView.setText(String.valueOf(currentProduct.getName()));

        TextView priceTextView = listItem.findViewById(R.id.product_price);
        priceTextView.setText(String.valueOf(currentProduct.getPrice()));

        ImageView imageView = listItem.findViewById(R.id.product_image);
        Picasso.get().load(currentProduct.getImage()).into(imageView); // Load image using Picasso

        Button addToCartButton = listItem.findViewById(R.id.addtocart);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddToCartListener.onAddToCartButtonClicked(currentProduct);
            }
        });

        Button detailButton = listItem.findViewById(R.id.product_detail);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailListener.onDetailButtonClicked(currentProduct);
            }
        });

        // Highlight selected item
        if (currentProduct.equals(mSelectedProduct)) {
            listItem.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            listItem.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }

        // Set click listener for item selection
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedProduct = currentProduct;
                notifyDataSetChanged(); // Notify adapter that data set changed
            }
        });

        return listItem;
    }

    public void setSelectedProduct(Product product) {
        mSelectedProduct = product;
        notifyDataSetChanged(); // Notify adapter that data set changed
    }

    public Product getSelectedProduct() {
        return mSelectedProduct;
    }
}