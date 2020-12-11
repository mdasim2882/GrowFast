package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.WhatsappVideoEntry;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.ProductOverview;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.WhatsappVideos;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.WhatsappVideoCardsHolder;
import com.example.growfast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new WhatsappVideoCardsHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull WhatsappVideoCardsHolder holder, int position) {


        Picasso.get().load(productList.get(position).getProductImage()).into(holder.imgCard);
        holder.productPrice.setText(productList.get(position).getProductCost());
        holder.productTitle.setText(productList.get(position).getProductName());

        //TODO: Put Recycler ViewHolder Cards binding video links in intents to fetch videos
        Log.d(TAG, "onBindViewHolder: ViDEO LinK --> " + productList.get(position).getVideoProductLink());


        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Material Card clicked " + productList.get(position).getProductName() + " : " +
                        "\nCost: " + productList.get(position).getProductCost() + "!!!" + context.getClass());

                //TODO: Perform card clicked working
                Context c = v.getContext();

                Intent i = new Intent(v.getContext(), WhatsappVideos.class);
                i.putExtra("statusVideo", true);
                i.putExtra("videoUrl", productList.get(position).getVideoProductLink());
                v.getContext().startActivity(i);
                // NOTE: Remember the important feature of Activity typecasting in constructor of Adapter
                // in order to use overridePendingTransition() method
                activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });


    }

    private void goToProductdetailsActivity(View v, String title, Integer imageID, Integer priceIDs) {
        Intent i = new Intent(v.getContext(), ProductOverview.class);
        i.putExtra("Title", title);
        i.putExtra("ImageID", imageID);
        i.putExtra("PriceBar", priceIDs);
        v.getContext().startActivity(i);
        // NOTE: Remember the important feature of Activity typecasting in constructor of Adapter
        // in order to use overridePendingTransition() method
        activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
