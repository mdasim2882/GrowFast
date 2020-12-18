package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.ProductEntry;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.ComboPostersActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.EditsDifferentGreetingsCards;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.GreetingCardsHolder.ComboOffersCardItemsViewHolder;
import com.example.growfast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComboOffersCardRecyclerViewAdapter extends RecyclerView.Adapter<ComboOffersCardItemsViewHolder> {
    public static final String COME_FROM = "comeFrom";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_PRICE = "productPrice";
    public static final String CART_BADGE = "CART_BADGE";
    public static final String UNIQUE_ID = "uniqueID";
    public static final String ITEM_TYPE = "itemType";

    public final String TAG = getClass().getSimpleName();
    Context context;
    private List<ProductEntry> productList;
    Activity activity;

    public ComboOffersCardRecyclerViewAdapter(Context context, List<ProductEntry> actualCards) {
        this.productList = actualCards;
        this.context = context;
        activity = (Activity) context;
    }

    private DialogInterface.OnClickListener performDialogOperations(String productName, String productCost) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Intent i = new Intent(context, CartItemsActivity.class);
                        i.putExtra(COME_FROM, "comboOffers");
                        i.putExtra(PRODUCT_NAME, productName);
                        i.putExtra(UNIQUE_ID, productName + System.currentTimeMillis());
                        i.putExtra(ITEM_TYPE, "Combo Offers Card");

                        i.putExtra(PRODUCT_PRICE, Integer.parseInt(productCost.substring(3)));

                        context.startActivity(i);
                        ((ComboPostersActivity) context).finish();


                        Toast.makeText(context, "Yes Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Toast.makeText(context, "No Clicked", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                }
            }
        };
    }
    @NonNull
    @Override
    public ComboOffersCardItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_life_card, parent, false);

        return new ComboOffersCardItemsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboOffersCardItemsViewHolder holder, int position) {
        // TODO: Put Recycler ViewHolder Cards binding code here in MDC-102

        String uricards = productList.get(position).getProductImage();
        String getCardsUri = productList.get(position).getProductImage();
        Picasso.get().load(uricards).into(holder.imgCard);
        String productCost = productList.get(position).getProductCost();
        String extPurchasd;
        try {
            extPurchasd = productCost.substring(0, productCost.indexOf(' '));
        } catch (Exception e) {
            extPurchasd = productCost;
        }
        if (extPurchasd.equals("Purchased")) {
            holder.productPrice.setText(extPurchasd);
        } else {
            holder.productPrice.setText(productCost);
        }
        holder.productTitle.setText(productList.get(position).getProductName());


        holder.productCard.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Material Card clicked " + productList.get(position).getProductName() + " : " +
                    "\nCost: " + productList.get(position).getProductCost() + "!!!" + context.getClass());
            String productName = productList.get(position).getProductName();

            DialogInterface.OnClickListener dialogClickListener = performDialogOperations(productName, productCost);
            //TODO: Perform card clicked working
            Context c = v.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setMessage("Do you want to really add to Cart?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).setCancelable(false);


            if (productCost.equals("Free") || productCost.charAt(0) == 'P') {
                Intent i = new Intent(v.getContext(), EditsDifferentGreetingsCards.class);
                i.putExtra("cardUri", uricards);
                v.getContext().startActivity(i);
            } else {
                builder.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
