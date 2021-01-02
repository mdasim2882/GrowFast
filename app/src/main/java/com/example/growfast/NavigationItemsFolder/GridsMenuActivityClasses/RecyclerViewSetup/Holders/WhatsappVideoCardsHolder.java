package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.R;

public class WhatsappVideoCardsHolder extends RecyclerView.ViewHolder {
    public CardView videocardproductCard;
    public ImageView videocardimgCard;
    public TextView videocardproductTitle;
    public TextView productvalidity;
    public TextView videocardproductPrice;

    public WhatsappVideoCardsHolder(@NonNull View itemView) {
        super(itemView);
        videocardimgCard = itemView.findViewById(R.id.videocardproduct_image);
        videocardproductTitle = itemView.findViewById(R.id.videocardproduct_title);
        productvalidity = itemView.findViewById(R.id.product_validity);
        videocardproductPrice = itemView.findViewById(R.id.videocardproduct_price);
        videocardproductCard = itemView.findViewById(R.id.videocardofproducts);
        // addtoCart=itemView.findViewById(R.id.addtoCartButton);
        // TODO: Find and store views from itemView
    }
}
