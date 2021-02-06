package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaladvisor.growfast.R;

public class WebsiteCardItemsViewHolder extends RecyclerView.ViewHolder {

    public CardView productCard;
    public ImageView imgCard;
    public TextView productTitle;
    public TextView productValidity;
    public TextView productPrice;

    public WebsiteCardItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        imgCard = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.product_title);
        productValidity = itemView.findViewById(R.id.product_validity);
        productPrice = itemView.findViewById(R.id.product_price);
        productCard = itemView.findViewById(R.id.cardofproducts);
        // addtoCart=itemView.findViewById(R.id.addtoCartButton);
        // TODO: Find and store views from itemView
    }
}
