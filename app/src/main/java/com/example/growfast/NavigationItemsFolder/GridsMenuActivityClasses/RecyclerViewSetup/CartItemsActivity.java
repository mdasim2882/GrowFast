package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup;

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

import com.example.growfast.NavigationItemsFolder.BusinessManagement;
import com.example.growfast.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartItemsActivity extends AppCompatActivity {
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    public static final int UPI_PAYMENT = 1234;
    public static final String COME_FROM = "comeFrom";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_PRICE = "productPrice";

    TextView productname, prductprice, priceSubtotal, feeTransaction, priceTotalOrder;
    Button placeOrderButton;
    LinearLayout upper, lower;
    private String getFrom;
    private String productName;
    private int price;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);
        chekOwner();
        initialTextFields();
        setUpToolbar();

        database = FirebaseFirestore.getInstance();

    }

    private void initialTextFields() {
        productname = findViewById(R.id.pdname);
        prductprice = findViewById(R.id.pd_price);
        priceSubtotal = findViewById(R.id.subtotal_price);
        feeTransaction = findViewById(R.id.transaction_fee);
        priceTotalOrder = findViewById(R.id.totalPriceOrder);

        placeOrderButton = findViewById(R.id.placeorderbtn);

        upper = findViewById(R.id.order_details_box);
        lower = findViewById(R.id.cartTotal_details_box);
        if (getFrom != null && getFrom.equals("digiRec")) {
            setupBadge();
        }
        placeOrderButton.setOnClickListener(v -> {

            paymentUri(null, null, null, null);

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

        productName = getIntent().getStringExtra(PRODUCT_NAME);
        getFrom = getIntent().getStringExtra(COME_FROM);
        price = getIntent().getIntExtra(PRODUCT_PRICE, 0);
    }

    private double setFeePercent(int pricetobeLoaded) {
        return (double) Math.round((0.02) * pricetobeLoaded * 100) / 100;

    }

    private void setupBadge() {
        Log.d("bdcst", "setupBadge: Received");

        lower.setVisibility(View.VISIBLE);
        upper.setVisibility(View.VISIBLE);

        if (productName != null) {
            productname.setText(" " + productName);
        } else {
            productname.setText("No items yet");
        }

        prductprice.setText("Rs " + price);
        priceSubtotal.setText("Rs " + price);

        feeTransaction.setText("Rs " + setFeePercent(price));

        double totalSum = setFeePercent(price) + price;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, BusinessManagement.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}