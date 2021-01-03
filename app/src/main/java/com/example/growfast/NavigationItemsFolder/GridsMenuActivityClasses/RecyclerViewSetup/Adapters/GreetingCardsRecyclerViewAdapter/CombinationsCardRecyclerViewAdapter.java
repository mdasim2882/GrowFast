package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter;

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

import com.example.growfast.HelperMethods.ProductEntry;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.CombinationActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.EditFBCoverPagesActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.GreetingCardsHolder.CombinationsCardItemsViewHolder;
import com.example.growfast.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.VideosRecyclerViewAdapter.CATEGORY;
import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity.UID;

public class CombinationsCardRecyclerViewAdapter extends RecyclerView.Adapter<CombinationsCardItemsViewHolder> {
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

    public CombinationsCardRecyclerViewAdapter(Context context, List<ProductEntry> actualCards) {
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

                        Intent i = new Intent(context, CartItemsActivity.class);
                        i.putExtra(COME_FROM, "combicards");
                        i.putExtra(PRODUCT_NAME, productName);
                        i.putExtra(UNIQUE_ID, productID);
                        i.putExtra(ITEM_TYPE, "Combinations Greetings");
                        i.putExtra(CATEGORY, "Combinations Greetings");

                        i.putExtra(PRODUCT_PRICE, Integer.parseInt(productCost.substring(3)));

                        context.startActivity(i);
                        ((CombinationActivity) context).finish();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.dismiss();
                        break;
                }
            }
        };
    }

    @NonNull
    @Override
    public CombinationsCardItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_combinations_card, parent, false);

        return new CombinationsCardItemsViewHolder(layoutView);
    }

    static long findDifference(String start_date, String end_date) {

        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            // Calucalte time difference in
            // seconds, minutes, hours, years,
            // and days
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;

            // Print the date difference in
            // years, in days, in hours, in
            // minutes, and in seconds

            System.out.print(
                    "Difference "
                            + "between two dates is: ");

            Log.e("DigitalCard", "findDifference: +" + difference_In_Years
                    + " years, "
                    + difference_In_Days
                    + " days, "
                    + difference_In_Hours
                    + " hours, "
                    + difference_In_Minutes
                    + " minutes, "
                    + difference_In_Seconds
                    + " seconds");
            return difference_In_Days;
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public void onBindViewHolder(@NonNull CombinationsCardItemsViewHolder holder, int position) {
        // TODO: Put Recycler ViewHolder Cards binding code here in MDC-102

        String productImage = productList.get(position).getProductImage();
        String getCardsUri = productImage;
        String productName = productList.get(position).getProductName();
        String productCost = productList.get(position).getProductCost();
        String expiryDaysLimit = productList.get(position).getExpiryDaysLimit();
        String extPurchase = "None";
        String validity = "Validity: Lifetime";
        if (expiryDaysLimit != null && !expiryDaysLimit.equals("")) {
            validity = "Validity: " + expiryDaysLimit + " Days";
        }

        if (productList.get(position).getBoughtBy() != null) {
            List<String> boughtBy = productList.get(position).getBoughtBy();
            boolean members = boughtBy.contains(FirebaseAuth.getInstance().getCurrentUser().getUid());

            //Converting timeStamp to Date and then format using Simple date format object
            Map<String, Object> purchaseTime = productList.get(position).getPurchaseTime();
            if (purchaseTime != null) {
                Timestamp timestamp = (Timestamp) purchaseTime.get(UID);
                if (timestamp != null) {
                    Date creationDate = timestamp.toDate();

                    SimpleDateFormat simpleDateFormat
                            = new SimpleDateFormat(
                            "dd-MM-yyyy HH:mm:ss");
                    Date d1 = new Date();
                    String todaysDate = simpleDateFormat.format(d1);
                    String buyingDate = simpleDateFormat.format(creationDate);

                    Log.e(TAG, "onBindViewHolder: \n+" +
                            "DATE of Purchase: " + creationDate.toString()
                            + "\n Timestamp: " + timestamp
                            + "\n Todays Date: " + todaysDate
                            + "\n Purchase Date: " + buyingDate

                    );
                    long differenceInDays = findDifference(buyingDate, todaysDate);

                    if (differenceInDays > Long.parseLong(expiryDaysLimit)) {
                        validity = "Validity: " + expiryDaysLimit + " Days";
                    } else {
                        long val = Math.abs(differenceInDays - Long.parseLong(expiryDaysLimit));
                        validity = "Validity: " + val + " Days Left";
                        if (members) {
                            extPurchase = "Purchased";
                        }
                    }
                }
            }

            Log.e("Credentials", "onBindViewHolder: Card Name: " + productName + " \nPurchased by: \n" + members + "\nLIST: " + boughtBy + "\n");
        }

        if (productCost != null && productImage != null && productCost != null && productName != null
                && !productCost.equals("") && !productImage.equals("") && !productCost.equals("") && !productName.equals("")) {
            Picasso.get().load(productImage).into(holder.imgCard);

            if (extPurchase.equals("Purchased")) {
                holder.productPrice.setText(extPurchase);
            } else {
                holder.productPrice.setText(productCost);
            }
            holder.productTitle.setText(productName);
            holder.productValidity.setText(validity);


            // Alert Dialog to confirm

            String finalExtPurchase = extPurchase;

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
                    Intent i = new Intent(v.getContext(), EditFBCoverPagesActivity.class);
                    i.putExtra("fbcardsUri", getCardsUri);
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
