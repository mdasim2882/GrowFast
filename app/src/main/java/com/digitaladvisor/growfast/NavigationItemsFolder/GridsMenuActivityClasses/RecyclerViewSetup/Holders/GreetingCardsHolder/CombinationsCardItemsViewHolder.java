package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.GreetingCardsHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaladvisor.growfast.R;

public class CombinationsCardItemsViewHolder extends RecyclerView.ViewHolder {

    public CardView productCard;
    public ImageView imgCard;
    public TextView productTitle;
    public TextView productValidity;
    public TextView productPrice;

    public CombinationsCardItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        imgCard = itemView.findViewById(R.id.combinationImage);
        productTitle = itemView.findViewById(R.id.combination_title);
        productPrice = itemView.findViewById(R.id.combinationPrice);
        productValidity = itemView.findViewById(R.id.product_validity);
        productCard = itemView.findViewById(R.id.productcombinations);
        // addtoCart=itemView.findViewById(R.id.addtoCartButton);
        // TODO: Find and store views from itemView
    }
}
