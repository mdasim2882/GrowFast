package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.WhatsappVideoEntry;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.ExoVideosWpActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.WhatsappVideoCardsHolder;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.WhatsappStatusVideoActivity;
import com.example.growfast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.DigitalCardRecyclerViewAdapter.COME_FROM;
import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.DigitalCardRecyclerViewAdapter.ITEM_TYPE;
import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.DigitalCardRecyclerViewAdapter.PRODUCT_NAME;
import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.DigitalCardRecyclerViewAdapter.PRODUCT_PRICE;
import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.DigitalCardRecyclerViewAdapter.UNIQUE_ID;

public class WhatsappVideoRecyclerViewAdapter extends RecyclerView.Adapter<WhatsappVideoCardsHolder> {


    public final String TAG = getClass().getSimpleName();
    Context context;
    private List<WhatsappVideoEntry> productList;
    Activity activity;

    public WhatsappVideoRecyclerViewAdapter(Context context, List<WhatsappVideoEntry> actualCards) {
        this.productList = actualCards;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public WhatsappVideoCardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_videos, parent, false);

        return new WhatsappVideoCardsHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull WhatsappVideoCardsHolder holder, int position) {

        String productImage = productList.get(position).getProductImage();
        String productCost = productList.get(position).getProductCost();
        String productName = productList.get(position).getProductName();
        String videoProductLink = productList.get(position).getVideoProductLink();

        if (productCost != null && productImage != null && productCost != null && videoProductLink != null
                && productCost != "" && productImage != "" && productCost != "" && videoProductLink != "") {
            Picasso.get().load(productList.get(position).getProductImage()).into(holder.videocardimgCard);
            holder.videocardproductPrice.setText(productCost);


            //TODO: Put Recycler ViewHolder Cards binding video links in intents to fetch videos
            Log.d(TAG, "onBindViewHolder: ViDEO LinK --> " + productList.get(position).getVideoProductLink());

            String extPurchasd;
            try {
                extPurchasd = productCost.substring(0, productCost.indexOf(' '));
            } catch (Exception e) {
                extPurchasd = productCost;
            }
            if (extPurchasd.equals("Purchased")) {
                holder.videocardproductPrice.setText(extPurchasd);
            } else {
                holder.videocardproductPrice.setText(productCost);
            }
            holder.videocardproductTitle.setText(productName);

            holder.videocardproductCard.setOnClickListener(v -> {
                Log.d(TAG, "onClick: Material Card clicked " + productName + " : " +
                        "\nCost: " + productCost + "!!!" + context.getClass());

                //TODO: Perform card clicked working


                DialogInterface.OnClickListener dialogClickListener = performDialogOperations(productName, productCost);
                //TODO: Perform card clicked working
                Context c = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage("Do you really want to add to Cart?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).setCancelable(false);


                if (productCost.equals("Free") || productCost.charAt(0) == 'P') {

                    Intent i = new Intent(v.getContext(), ExoVideosWpActivity.class);
                    i.putExtra("statusVideo", true);
                    i.putExtra("videoUrl", videoProductLink);
                    v.getContext().startActivity(i);
                    // NOTE: Remember the important feature of Activity typecasting in constructor of Adapter
                    // in order to use overridePendingTransition() method
                    activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                } else {
                    builder.show();
                }

            });
        }


    }

    private DialogInterface.OnClickListener performDialogOperations(String productName, String productCost) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
//                        startMyItemAddedBroadCast();
                        Intent i = new Intent(context, CartItemsActivity.class);
                        i.putExtra(COME_FROM, "wpstats");
                        i.putExtra(PRODUCT_NAME, productName);
                        i.putExtra(UNIQUE_ID, productName + System.currentTimeMillis());
                        i.putExtra(ITEM_TYPE, "Whatsapp Status Videos");

                        i.putExtra(PRODUCT_PRICE, Integer.parseInt(productCost.substring(3)));

                        context.startActivity(i);
                        ((WhatsappStatusVideoActivity) context).finish();


                        //  Toast.makeText(context, "Yes Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        //  Toast.makeText(context, "No Clicked", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                }
            }
        };
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
