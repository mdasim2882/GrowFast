package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.GreetingCardsHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.R;

public class CartItemsCardViewHolder extends RecyclerView.ViewHolder {

    public CardView productCard;
    public ImageView imgCard;
    public TextView productTitle;
    public TextView productPrice;
    public TextView productType;
    public Button removeButton;

    public CartItemsCardViewHolder(@NonNull View itemView) {
        super(itemView);
//        imgCard = itemView.findViewById(R.id.combinationImage);
        productTitle = itemView.findViewById(R.id.pdname);
        productPrice = itemView.findViewById(R.id.pd_price);
        productType = itemView.findViewById(R.id.pd_type);
        productCard = itemView.findViewById(R.id.productcartitems);
        removeButton = itemView.findViewById(R.id.removebtn);
        // TODO: Find and store views from itemView
    }
}
