package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.CartHelp;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.GreetingCardsHolder.CartItemsCardViewHolder;
import com.example.growfast.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartitemCardRecyclerViewAdapter extends RecyclerView.Adapter<CartItemsCardViewHolder> {

    double actualSumprice = 0;
    double actualTransactionFee = 0;

    public static final String COME_FROM = "comeFrom";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_PRICE = "productPrice";
    public static final String CART_BADGE = "CART_BADGE";
    public static final String UNIQUE_ID = "uniqueID";
    public static final String ITEM_TYPE = "itemType";

    FirebaseFirestore database;
    public final String TAG = getClass().getSimpleName();
    Context context;
    private List<CartHelp> productList;
    Activity activity;
    private boolean press = false;

    public CartitemCardRecyclerViewAdapter(Context context, List<CartHelp> actualCards) {
        this.productList = actualCards;
        this.context = context;
        activity = (Activity) context;
        database = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CartItemsCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cart_card, parent, false);

        return new CartItemsCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsCardViewHolder holder, int position) {
        // TODO: Put Recycler ViewHolder Cards binding code here in MDC-102

//actualSumprice=0;
        String itemprice = productList.get(position).getItemprice();
        holder.productPrice.setText(itemprice);
        holder.productTitle.setText(productList.get(position).getItemName());
        holder.productType.setText(productList.get(position).getItemType());

        actualSumprice = actualSumprice + Integer.parseInt(itemprice.substring(3));
        actualTransactionFee = actualTransactionFee + setFeePercent(Integer.parseInt(itemprice.substring(3))) + setFeePercent(0);

        actualSumprice = roundOFF(actualSumprice);
        actualTransactionFee = roundOFF(actualTransactionFee);
//        holder.productCard.setOnClickListener(v -> {
//            Log.d(TAG, "onClick: Material Card clicked " + productList.get(position).getItemName() + " : " +
//                    "\nCost: " + productList.get(position).getItemprice() + "!!!" + context.getClass());
//            //TODO: Perform card clicked working
//            Context c = v.getContext();
//            Intent i = new Intent(v.getContext(), ProductOverview.class);
//            Toast.makeText(c, "Working :" + position, Toast.LENGTH_SHORT).show();
//            v.getContext().startActivity(i);
//
//        });
        holder.removeButton.setOnClickListener(v -> {
            //Remove this card
            notifyItemRemoved(position);
            String selectedPrice = productList.get(position).getItemprice();
            removeProductDocumentFromFirestore(position);
            notifyItemRangeChanged(position, productList.size());
            productList.remove(position);
            String a = "Before: " + actualSumprice;
            actualSumprice = actualSumprice - (Integer.parseInt(selectedPrice.substring(3)));
            a += "\nAfter: " + actualSumprice;
            Toast.makeText(context, "Value sum= " + a, Toast.LENGTH_LONG).show();

            Log.d("MAGGY", "onBindViewHolder: SUM OF RUPEES===>" + a);
            actualSumprice = roundOFF(actualSumprice);
            actualTransactionFee = actualTransactionFee - setFeePercent(Integer.parseInt(selectedPrice.substring(3))) + setFeePercent(0);
            actualTransactionFee = roundOFF(actualTransactionFee);
            press = true;
            CartItemsActivity.setupBadge((int) actualSumprice, actualTransactionFee);
            actualSumprice = 0;
//            super.onCreateViewHolder(holder,position);

            //TODO: Error occurs due to late binding of view holder o.e.; from bottom to top
        });
        if (!press) {
            CartItemsActivity.setupBadge((int) actualSumprice, actualTransactionFee);
        }
        Log.d("MAGGY", "onBindViewHolder: WITHOUT REMOVe PRESS===>" + actualSumprice);


    }


    private void removeProductDocumentFromFirestore(int position) {
        database.collection("Cart Products")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("My Cart")
                .document(productList.get(position).getItemID())
                .delete();


    }

    private double roundOFF(double a) {
        return Math.round(a * 100.0) / 100.0;
    }

    private double setFeePercent(int pricetobeLoaded) {
        return (double) Math.round((0.02) * pricetobeLoaded * 100) / 100;

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
