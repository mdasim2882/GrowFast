package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters;

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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.ProductEntry;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.DigitalCardsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.ProductOverview;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.DigitalCardItemsViewHolder;
import com.example.growfast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DigitalCardRecyclerViewAdapter extends RecyclerView.Adapter<DigitalCardItemsViewHolder> {

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
    public boolean paymentDone = false;
    LocalBroadcastManager localBroadcastManager;

    public DigitalCardRecyclerViewAdapter(Context context, List<ProductEntry> actualCards) {
        this.productList = actualCards;
        this.context = context;
        activity = (Activity) context;

        localBroadcastManager = LocalBroadcastManager.getInstance(activity);
    }

    @NonNull
    @Override
    public DigitalCardItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new DigitalCardItemsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull DigitalCardItemsViewHolder holder, int position) {
        // TODO: Put Recycler ViewHolder Cards binding code here in MDC-102

        String productImage = productList.get(position).getProductImage();
        String getCardsUri = productImage;
        String productName = productList.get(position).getProductName();
        String productCost = productList.get(position).getProductCost();

        if (productList.get(position).getBoughtBy() != null) {
            String firstPurchasedUsers = productList.get(position).getBoughtBy().get(0);
            String secondPurchasedUser = productList.get(position).getBoughtBy().get(1);
            Log.e(TAG, "onBindViewHolder: Purchased by: \n" + firstPurchasedUsers + "\n" + secondPurchasedUser);
        }


        if (productImage != null && productCost != null && productName != null
                && productCost != "" && productImage != "" && productName != "") {

            Picasso.get().load(getCardsUri).into(holder.imgCard);
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
            holder.productTitle.setText(productName);


            // Alert Dialog to confirm

            holder.productCard.setOnClickListener(v -> {

                Log.d(TAG, "onClick: Material Card clicked " + productName + " : " +
                        "\nCost: " + productCost + "!!!--> " + productCost.substring(3));
                String productID = productName + System.currentTimeMillis();
                if (productList.get(position).getProductID() != null && !productList.get(position).getProductID().equals("")) {
                    productID = productList.get(position).getProductID();
                }
                DialogInterface.OnClickListener dialogClickListener = performDialogOperations(productID, productName, productCost);
                //TODO: Perform card clicked working
                Context c = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage("Do you really want to add to Cart?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).setCancelable(false);


                if (paymentDone == true || productCost.equals("Free") || productCost.charAt(0) == 'P') {
                    Intent i = new Intent(v.getContext(), ProductOverview.class);
                    i.putExtra("digiCardUri", getCardsUri);
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

    private DialogInterface.OnClickListener performDialogOperations(String productID, String productName, String productCost) {
        return (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
//                        startMyItemAddedBroadCast();
                    Intent i = new Intent(context, CartItemsActivity.class);
                    i.putExtra(COME_FROM, "digiRec");
                    i.putExtra(PRODUCT_NAME, productName);
                    i.putExtra(UNIQUE_ID, productID);
                    i.putExtra(ITEM_TYPE, "Digital Card");

                    i.putExtra(PRODUCT_PRICE, Integer.parseInt(productCost.substring(3)));

                    context.startActivity(i);
                    ((DigitalCardsActivity) context).finish();


                    Toast.makeText(context, "Yes Clicked", Toast.LENGTH_SHORT).show();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    Toast.makeText(context, "No Clicked", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
            }
        };
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

    private void startMyItemAddedBroadCast() {
        System.out.println("SEND");
        Log.d("bdcst", "setupBadge: SENT");
        Intent intent = new Intent(CART_BADGE);
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
