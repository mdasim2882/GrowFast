package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.R;

public class RefImagesCardItemsViewHolder extends RecyclerView.ViewHolder {

    public CardView productCard;
    public ImageView imgCard;


    public RefImagesCardItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        imgCard = itemView.findViewById(R.id.productRef_image);
        productCard = itemView.findViewById(R.id.cardaddphotos);
        // addtoCart=itemView.findViewById(R.id.addtoCartButton);
        // TODO: Find and store views from itemView
    }
}
