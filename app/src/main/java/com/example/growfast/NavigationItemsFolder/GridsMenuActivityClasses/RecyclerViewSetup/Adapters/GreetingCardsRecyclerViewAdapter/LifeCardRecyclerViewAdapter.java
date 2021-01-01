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
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.LifeActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.EditsDifferentGreetingsCards;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.GreetingCardsHolder.LifeCardItemsViewHolder;
import com.example.growfast.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.VideosRecyclerViewAdapter.CATEGORY;

public class LifeCardRecyclerViewAdapter extends RecyclerView.Adapter<LifeCardItemsViewHolder> {
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

    public LifeCardRecyclerViewAdapter(Context context, List<ProductEntry> actualCards) {
        this.productList = actualCards;
        this.context = context;
        activity = (Activity) context;
    }

    private DialogInterface.OnClickListener performDialogOperations(String productID, String productName, String productCost) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Intent i = new Intent(context, CartItemsActivity.class);
                        i.putExtra(COME_FROM, "lifeCard");
                        i.putExtra(PRODUCT_NAME, productName);
                        i.putExtra(UNIQUE_ID, productID);
                        i.putExtra(ITEM_TYPE, "Life Greetings");
                        i.putExtra(CATEGORY, "Life Greetings");

                        i.putExtra(PRODUCT_PRICE, Integer.parseInt(productCost.substring(3)));

                        context.startActivity(i);
                        ((LifeActivity) context).finish();


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
    public LifeCardItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_life_card, parent, false);

        return new LifeCardItemsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull LifeCardItemsViewHolder holder, int position) {
        // TODO: Put Recycler ViewHolder Cards binding code here in MDC-102
        String productImage = productList.get(position).getProductImage();
        String uricards = productImage;
        String productName = productList.get(position).getProductName();
        String productCost = productList.get(position).getProductCost();
        String extPurchase = "None";
        if (productList.get(position).getBoughtBy() != null) {
            List<String> boughtBy = productList.get(position).getBoughtBy();
            boolean members = boughtBy.contains(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            for (String bought:boughtBy) {
//
//                members+= bought+"\n";
//            }
            if (members) {
                extPurchase = "Purchased";
            }

            Log.e("Credentials", "onBindViewHolder: Card Name: " + productName + " \nPurchased by: \n" + members + "\nLIST: " + boughtBy + "\n");
        }
        if (productImage != null && productCost != null && productName != null
                && !productCost.equals("") && !productImage.equals("") && !productName.equals("")) {

            Picasso.get().load(uricards).into(holder.imgCard);
            if (extPurchase.equals("Purchased")) {
                holder.productPrice.setText(extPurchase);
            } else {
                holder.productPrice.setText(productCost);
            }
            holder.productTitle.setText(productName);


            // Alert Dialog to confirm

            String finalExtPurchase = extPurchase;
            Log.e(TAG, "onBindViewHolder: " + productImage);


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
                builder.setMessage("Do you want to really add to Cart?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).setCancelable(false);


                if (productCost.equals("Free") || finalExtPurchase.charAt(0) == 'P') {
                    Intent i = new Intent(v.getContext(), EditsDifferentGreetingsCards.class);
                    i.putExtra("cardUri", uricards);
                    v.getContext().startActivity(i);
                } else {
                    builder.show();
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
