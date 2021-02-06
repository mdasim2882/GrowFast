package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaladvisor.growfast.HelperMethods.CartHelp;
import com.digitaladvisor.growfast.HelperMethods.CartManager;
import com.digitaladvisor.growfast.InterfacesUsed.LoadCartItems;
import com.digitaladvisor.growfast.NavigationItemsFolder.BusinessManagement;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter.CartitemCardRecyclerViewAdapter;
import com.digitaladvisor.growfast.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.VideosRecyclerViewAdapter.CATEGORY;

public class CartItemsActivity extends AppCompatActivity implements LoadCartItems, PaymentResultListener {
    public static final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public final String TAG = getClass().getSimpleName();
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    private RecyclerView recyclerView;
    public static final int UPI_PAYMENT = 1234;
    public static final String UNIQUE_ID = "uniqueID";
    public static final String COME_FROM = "comeFrom";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_PRICE = "productPrice";
    public static final String ITEM_TYPE = "itemType";

    static TextView productname, prductprice, priceSubtotal, feeTransaction, priceTotalOrder;
    Button placeOrderButton;
    LinearLayout upper, lower;
    private String getFrom;
    private String productName, productID, productCollection, productType;
    private int price;

    private static int payableAmount;

    FirebaseFirestore database;
    CollectionReference loadMyCartItems;
    LoadCartItems loadCartItems;
    private CartManager doneAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);
        Checkout.preload(getApplicationContext());

        chekOwner();
        database = FirebaseFirestore.getInstance();
        initialTextFields();
        setUpToolbar();
        setRecyclerView();


    }

    private void loadCartItems() {
        loadMyCartItems.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<CartHelp> products = new ArrayList<>();
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                        CartHelp product = bannerSnapshot.toObject(CartHelp.class);
                        products.add(product);
                    }
                    loadCartItems.onCartItemsLoadSuccess(products);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadCartItems.onCartItemsLoadFailure(e.getMessage());
            }
        });
    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.cartItemsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        /*
         * Pass parameter as list of type ProductEntry
         * Must be retrieved from database to here only
         * ProductEntry contains three fields:
         * ImageView productImage
         * TextView productName, productCost;
         * */

    }

    private void initialTextFields() {
        productname = findViewById(R.id.pdname);
        prductprice = findViewById(R.id.pd_price);
        priceSubtotal = findViewById(R.id.subtotal_price);
        feeTransaction = findViewById(R.id.transaction_fee);
        priceTotalOrder = findViewById(R.id.totalPriceOrder);
        doneAdd = new CartManager();


        placeOrderButton = findViewById(R.id.placeorderbtn);
        loadMyCartItems = FirebaseFirestore.getInstance().collection("Cart Products").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("My Cart");

        upper = findViewById(R.id.order_details_box);
        lower = findViewById(R.id.cartTotal_details_box);
        if (getFrom != null) {
//            setupBadge();
            productID = productID.trim();
            productCollection = productCollection.trim();
            addToDatabaseCart();
        }
        // Load cart items
        else {
            loadCartItems();
        }
        placeOrderButton.setOnClickListener(v -> {
            startPayment();
            //paymentUri(null, null, null, null);

        });
        loadCartItems = this;
        Log.e(TAG + " DataCart", "initialTextFields: Linked List of Products: " + CartManager.managedProductId);
        Log.e(TAG + " DataCart", "initialTextFields: Linked List of Collections: " + CartManager.managedCollectionName);
    }

    private void addToDatabaseCart() {


        HashMap<String, String> itemData = new HashMap<>();
        itemData.put("itemName", productName);
        itemData.put("itemType", productType);
        itemData.put("itemID", productID);
        itemData.put("itemCollection", productCollection);
        itemData.put("itemprice", "Rs " + price);
        doneAdd.additemId(productID);


        database.collection("Cart Products").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("My Cart").document(productID).set(itemData, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CartItemsActivity.this, "Add to Cart Done", Toast.LENGTH_SHORT).show();
                loadCartItems();
            }

        });
    }

    private void paymentUri(String name, String upiId, String note, String amount) {
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", "mohdasim2808@oksbi")
                        .appendQueryParameter("pn", "Mohd Asim")
//                        .appendQueryParameter("mc", "your-merchant-code")
//                        .appendQueryParameter("tr", "your-transaction-ref-id")
                        .appendQueryParameter("tn", "Tada")
                        .appendQueryParameter("am", "" + 5)
                        .appendQueryParameter("cu", "INR")
//                        .appendQueryParameter("url", "your-transaction-url")
                        .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        Intent createChooser = Intent.createChooser(intent, "Pay with");
        if (createChooser.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(createChooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI Payment App found, install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == UPI_PAYMENT) {
//            // Process based on the data in response.
//            Log.d("result", data.getStringExtra("Status"));
        Log.e("main ", "response " + resultCode);


        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }


    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(CartItemsActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: " + approvalRefNo);

                database.collection("Templates").document(FirebaseAuth.getInstance().getUid()).update("productCost", "Purchased Reference ID: " + approvalRefNo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("QUERY EXECUTED_--------------------------------------->");
                        }
                    }
                });


            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Toast.makeText(this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: " + approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void chekOwner() {

        productID = getIntent().getStringExtra(UNIQUE_ID);
        productCollection = getIntent().getStringExtra(ITEM_TYPE);

        productType = getIntent().getStringExtra(CATEGORY);
        productName = getIntent().getStringExtra(PRODUCT_NAME);
        getFrom = getIntent().getStringExtra(COME_FROM);
        price = getIntent().getIntExtra(PRODUCT_PRICE, 0);
    }

    private double setFeePercent(int pricetobeLoaded) {
        return (double) Math.round((0.02) * pricetobeLoaded * 100) / 100;

    }

    public static void setupBadge(int price, double fee) {
        Log.d("bdcst", "setupBadge: Received");


        priceSubtotal.setText("Rs " + price);

        feeTransaction.setText("Rs " + fee);

        double totalSum = fee + price;
        payableAmount = (int) (totalSum * 100);
        priceTotalOrder.setText("Rs " + totalSum);

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.cart_management_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartItemsActivity.this, BusinessManagement.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    //-----------------------------------------------RAZOR PAY API METHODS----------------------------//
    public void startPayment() {
//    checkout.setKeyID("<YOUR_KEY_ID>");
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_t5DZKoybTA6nNW");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo_digitaladvisor);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            Log.e(TAG, "Payment: Amount " + String.valueOf(payableAmount));
            options.put("name", "Digital Advisor");
            options.put("description", "Purchasing Digital Cards/Videos");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//        options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(payableAmount));//pass amount in currency subunits


//        JSONObject prefill=new JSONObject();
//        prefill.put("email", "mohdasim2882@gmail.com");
//        prefill.put("contact","+919580130679");
//            options.put("prefill.email", "mohdasim2882@gmail.com");
            options.put("prefill.contact", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

//        options.put("prefill", prefill);
            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, BusinessManagement.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onCartItemsLoadSuccess(List<CartHelp> templates) {
        CartitemCardRecyclerViewAdapter adapter = new CartitemCardRecyclerViewAdapter(this, templates);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    @Override
    public void onCartItemsLoadFailure(String message) {
        Toast.makeText(this, "Something went wrong: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentSuccess(String s) {
        try {
            Log.e(TAG, " Payment successfull " + s + "\n UIDs: " + FirebaseAuth.getInstance().getUid() + "\n User Id: " + UID + "\n Product Id that is set: " + CartManager.managedProductId);

            Toast.makeText(this, "Payment ID: " + s, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Transaction successful", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Cart updates after 5 minutes", Toast.LENGTH_SHORT).show();
            Map<String, Object> purchseInfo = new HashMap<>();
            purchseInfo.put(UID, FieldValue.serverTimestamp());
//            purchseInfo.put("purcahseItems",
//                    CartManager.managedCollectionName);
//            purchseInfo.put("purchaseItemID",CartManager
//            .managedProductId);

            Map<String, Object> itemData = new HashMap<>();
            itemData.put("boughtBy", FieldValue.arrayUnion(FirebaseAuth.getInstance().getUid()));
            itemData.put("purchaseTime", purchseInfo);
            // TODO: Start Here...
            ProgressDialog p = new ProgressDialog(this);
            p.setMessage("Updating info...");
            p.setCanceledOnTouchOutside(false);
//        p.show();
            for (int i = 0; i < CartManager.managedProductId.size(); i++) {
                // Remove Particular Card
                database.collection(CartManager.managedCollectionName.get(i).trim())
                        .document(CartManager.managedProductId.get(i).trim())
                        .set(itemData, SetOptions.merge())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.e(TAG, "onComplete: QUERY EXECUTED_--------------> " + task.getResult());
                            }
                        })
                        .addOnFailureListener(e -> Log.e(TAG, "onFailure: ----------FAILED---------------> " + e.getMessage()));
                //Make user cart Empty here
                database.collection("Cart Products")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("My Cart")
                        .document(CartManager.managedProductId.get(i).trim())
                        .delete().addOnCompleteListener(task -> Log.e(TAG, "onPaymentSuccess: Cart Empty Success"))
                        .addOnFailureListener(e -> Log.e(TAG, "onPaymentSuccess: Cart Empty Failed " + e.getMessage()));
            }
            p.dismiss();
        } catch (Exception e) {
            Log.e(TAG, "onPaymentSuccess: ErrorMessage " + e.getMessage());
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG, "Error code " + i + " -- Payment failed " + s);
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError" + e.getMessage());
        }
    }
}