package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.WebInfoHelper;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.ReferenceImagesCardRecyclerViewAdapter;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.ProductGridItemDecoration;
import com.example.growfast.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class WebsiteActivity extends AppCompatActivity {
    private WebView webView;
    private EditText editName;
    DatabaseReference websiteData;
    RecyclerView recyclerView;
    private ReferenceImagesCardRecyclerViewAdapter adapter;
    private String TAG;
    StorageReference storageReference;

    public static List<Uri> imagesSelected;
    public List<String> imagesSelectedURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        imagesSelected = new ArrayList<>();
        imagesSelectedURL = new ArrayList<>();
//        webView = (WebView) findViewById(R.id.webview);
////        ProgressBar pb=findViewById(R.id.progressPage);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://bestfashionpoint.com/");
///*//        webView.setWebChromeClient(new WebChromeClient() {
////            public void onProgressChanged(WebView view, int progress) {
////                Log.e("progress", ""+progress);
////                if (progress == 100) { //...page is fully loaded.
////                    // TODO - Add whatever code you need here based on web page load completion...
////                    pb.setVisibility(View.GONE);
////                }
////            }
////        });*/
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        editName = findViewById(R.id.mweb_name);
        websiteData = FirebaseDatabase.getInstance().getReference("Users");
        setRecyclerView();
        storageReference = FirebaseStorage.getInstance().getReference();


    }

    private void updateUI(FirebaseUser user, List<Uri> image) {

        for (Uri i : image) {
            StorageReference spaceRef = storageReference.child("USERS/" + user.getUid() + "/MyProducts" + System.currentTimeMillis() + ".jpg");
            spaceRef.putFile(i).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                spaceRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.e(TAG, "updateUI: PHOTO URL TO BE SAVED: " + uri);
                    imagesSelectedURL.add(uri.toString());
                });
            }).addOnCompleteListener(task -> {
                Toast.makeText(this, "Done with uploading...", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> Toast.makeText(this, "Failed.", Toast.LENGTH_SHORT).show());
        }

    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.webPicRefrecycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        /*
         * Pass parameter as list of type ProductEntry
         * Must be retrieved from database to here only
         * ProductEntry contains three fields:
         * ImageView productImage
         * TextView productName, productCost;
         * */
        loadAllcards();
    }

    private void loadAllcards() {
        List<String> values = new ArrayList<>();
        values.add("www.google.com");
        values.add("www.google.com");
        values.add("www.google.com");

        values.add("www.google.com");
        values.add("www.google.com");

        adapter = new ReferenceImagesCardRecyclerViewAdapter(this, values);


        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    public void addtoRealtimeDatabase(View view) {
        imagesSelectedURL = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(user, imagesSelected);
        String name = editName.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            String id = websiteData.push().getKey();
            String imageLink = "https://firebasestorage.googleapis.com/v0/b/growfast-be85f.appspot.com/o/Profile%20Pic%2FnNil0rhFBnfmfxXFfuNTC8Syke43.jpg?alt=media&token=8b55eb19-31c6-45a6-bc19-a5051ccdbd15";
            WebInfoHelper mywebsite = new WebInfoHelper(id, name, imageLink, "+913214567890", "", imagesSelectedURL);
//            Map<String,String> valuesDB=new HashMap<>();
//            valuesDB.put("name",name);

            websiteData.child(id).setValue(mywebsite);
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TAG = "GALLERY_TAG";
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        adapter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
//    @Override
//    public void onBackPressed() {
////        if (webView.canGoBack()) {
////            webView.goBack();
////        } else {
////            super.onBackPressed();
////        }
//    }
}