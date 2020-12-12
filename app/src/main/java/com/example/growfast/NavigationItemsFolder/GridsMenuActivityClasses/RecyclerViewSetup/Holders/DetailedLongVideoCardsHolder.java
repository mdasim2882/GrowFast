package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.R;

public class DetailedLongVideoCardsHolder extends RecyclerView.ViewHolder {
    public CardView productCard;
    public ImageView imgCard;
    public TextView productTitle;
    public TextView productPrice;

    public DetailedLongVideoCardsHolder(@NonNull View itemView) {
        super(itemView);
        imgCard = itemView.findViewById(R.id.videocardproduct_image);
        productTitle = itemView.findViewById(R.id.videocardproduct_title);
        productPrice = itemView.findViewById(R.id.videocardproduct_price);
        productCard = itemView.findViewById(R.id.videocardofproducts);
        // addtoCart=itemView.findViewById(R.id.addtoCartButton);
        // TODO: Find and store views from itemView
    }
}
