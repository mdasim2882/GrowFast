package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaladvisor.growfast.HelperMethods.WhatsappVideoEntry;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.ProductOverview;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.WhatsappVideos;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.VideoCardsHolder;
import com.digitaladvisor.growfast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UiProRecyclerViewAdapter extends RecyclerView.Adapter<VideoCardsHolder> {


    public final String TAG = getClass().getSimpleName();
    Context context;
    private List<WhatsappVideoEntry> productList;
    Activity activity;

    public UiProRecyclerViewAdapter(Context context, List<WhatsappVideoEntry> actualCards) {
        this.productList = actualCards;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public VideoCardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_videos, parent, false);

        return new VideoCardsHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoCardsHolder holder, int position) {


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
