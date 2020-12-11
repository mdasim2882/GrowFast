package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.ProductEntry;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.EditLifeCard;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.GreetingCardsHolder.HealthCardItemsViewHolder;
import com.example.growfast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HealthCardRecyclerViewAdapter extends RecyclerView.Adapter<HealthCardItemsViewHolder> {

    public final String TAG = getClass().getSimpleName();
    Context context;
    private List<ProductEntry> productList;
    Activity activity;

    public HealthCardRecyclerViewAdapter(Context context, List<ProductEntry> actualCards) {
        this.productList = actualCards;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public HealthCardItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_life_card, parent, false);

        return new HealthCardItemsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthCardItemsViewHolder holder, int position) {
        // TODO: Put Recycler ViewHolder Cards binding code here in MDC-102
        String uricards = productList.get(position).getProductImage();

        Picasso.get().load(uricards).into(holder.imgCard);
        holder.productPrice.setText(productList.get(position).getProductCost());
        holder.productTitle.setText(productList.get(position).getProductName());


        holder.productCard.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Material Card clicked " + productList.get(position).getProductName() + " : " +
                    "\nCost: " + productList.get(position).getProductCost() + "!!!" + context.getClass());
            //TODO: Perform card clicked working
            Context c = v.getContext();
            Intent i = new Intent(v.getContext(), EditLifeCard.class);
            i.putExtra("cardUri", uricards);
            Toast.makeText(c, "Working :" + position, Toast.LENGTH_SHORT).show();
            v.getContext().startActivity(i);

        });


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
