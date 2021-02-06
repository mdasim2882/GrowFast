package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.WebInfoHelper;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.WebsitePreviewActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.ReferenceImagesCardRecyclerViewAdapter;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.ProductGridItemDecoration;
import com.example.growfast.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class WebsiteActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private WebView webView;
    private final int GALLERY_CODE_WEBSITE = 1111;
    private EditText editName, editDesignation, editBusinessAdd;
    private EditText editReferenceWebsite, editMobileNo, editEmailId;
    private EditText editLinkedInId, editFacebookId, editWhatsappNo;
    private EditText editTwitterId, editTelegramId, editInstagramId;
    private EditText editGoogleMapLocationLink, editAboutUs;
    private Uri imageUri;

    private String reditName, reditDesignation, reditBusinessAdd;
    private String reditReferenceWebsite, reditMobileNo, reditEmailId;
    private String reditLinkedInId, reditFacebookId, reditWhatsappNo;
    private String reditTwitterId, reditTelegramId, reditInstagramId;
    private String reditGoogleMapLocationLink, reditAboutUs, imageLinkUser;
    private ReferenceImagesCardRecyclerViewAdapter adapter;

    public List<String> imagesSelectedURL;
    public static List<Uri> imagesSelected;

    DatabaseReference websiteData;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    StorageReference storageReference;
    private String id, typeWebsite;

    ImageView webprofile_image;
    private FirebaseUser user;
    private WebInfoHelper mywebsite;
    ProgressDialog progressDialog;
    public static final String TYPE_WEBSITE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        setUpToolbar();
        imagesSelected = new ArrayList<>();
        imagesSelectedURL = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Creating Website...");

        typeWebsite = getIntent().getStringExtra(TYPE_WEBSITE);
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
        initializeEditTextFields();
        websiteData = FirebaseDatabase.getInstance().getReference("Users");
        setRecyclerView();
        //Todo: Update 4
//        id = websiteData.push().getKey();
        Log.e(TAG, "onCreate: TYPE WEBSITE" + typeWebsite);
        id = FirebaseAuth.getInstance().getCurrentUser().getUid() + typeWebsite;

        storageReference = FirebaseStorage.getInstance().getReference();
        database = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.mycreatedwebsite_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initializeEditTextFields() {
        imageLinkUser = "";
        editName = findViewById(R.id.mweb_name);
        webprofile_image = findViewById(R.id.mwebprofile_image);
        editDesignation = findViewById(R.id.mweb_designation_name);
        editBusinessAdd = findViewById(R.id.mweb_businessAdd);
        editReferenceWebsite = findViewById(R.id.mweb_refwebAdd);
        editMobileNo = findViewById(R.id.mweb_moblie);
        editEmailId = findViewById(R.id.mweb_emailID);

        editFacebookId = findViewById(R.id.ms_facebookId);
        editLinkedInId = findViewById(R.id.ms_linkedId);
        editWhatsappNo = findViewById(R.id.ms_whatsappNo);
        editTwitterId = findViewById(R.id.ms_twitterID);
        editTelegramId = findViewById(R.id.ms_telegramID);
        editInstagramId = findViewById(R.id.ms_instagramID);

        editGoogleMapLocationLink = findViewById(R.id.mb_googleMap);
        editAboutUs = findViewById(R.id.mabout_edt);

        webprofile_image.setOnClickListener(v -> {
            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, GALLERY_CODE_WEBSITE);
        });


    }

    private void retriveEditTextFields() {
        reditName = editName.getText().toString();
        reditDesignation = editDesignation.getText().toString();
        reditBusinessAdd = editBusinessAdd.getText().toString();
        reditReferenceWebsite = editReferenceWebsite.getText().toString();
        reditMobileNo = editMobileNo.getText().toString();
        reditEmailId = editEmailId.getText().toString();

        reditFacebookId = editFacebookId.getText().toString();
        reditLinkedInId = editLinkedInId.getText().toString();
        reditWhatsappNo = editWhatsappNo.getText().toString();
        reditTwitterId = editTwitterId.getText().toString();
        reditTelegramId = editTelegramId.getText().toString();
        reditInstagramId = editInstagramId.getText().toString();

        reditGoogleMapLocationLink = editGoogleMapLocationLink.getText().toString();
        reditAboutUs = editAboutUs.getText().toString();
    }

    private void updateUI(FirebaseUser user, List<Uri> image, boolean status) {
        AtomicInteger a = new AtomicInteger();
        a.set(0);

        Log.d(TAG, "updateUI() called with:  image = [" + image + "]");


        for (Uri i : image) {

            StorageReference spaceRef = storageReference.child("USERS/" + user.getUid() + "/MyProducts" + System.currentTimeMillis() + ".jpg");
            spaceRef.putFile(i).addOnSuccessListener(taskSnapshot -> {
//                Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                spaceRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.e(TAG, "updateUI: PHOTO URL TO BE SAVED: " + uri);

                    if (a.get() == image.size() - 1) {
                        Log.e(TAG, "ATOMIC VALUE after update = [" + a.get() + "]");
                    }
//                 imagesSelectedURL.add(uri.toString());
                    Map<String, Object> map = new HashMap<>();
                    map.put("product_" + a, uri.toString());
                    //TODO: Update 1
                    websiteData.child(id).updateChildren(map);
//                    websiteData.updateChildren(map);
                    a.set(a.get() + 1);
                });
            })
                    .addOnCompleteListener(task -> {
//                Toast.makeText(this, "Done with uploading...", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed.", Toast.LENGTH_SHORT).show());
        }

        if (status) {
            uploadProfilePicture();
        }
    }

    private void uploadProfilePicture() {
        StorageReference spaceRef = storageReference.child("USERS/" + user.getUid() + "/WebProfilePicture" + System.currentTimeMillis() + ".jpg");
        spaceRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
//                    Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            spaceRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Log.e(TAG, "updateUI: PHOTO URL TO BE SAVED: " + uri);
//                 imagesSelectedURL.add(uri.toString());
                Map<String, Object> map = new HashMap<>();
                map.put("imageProfileLink", uri.toString());
                imageLinkUser = uri.toString();
                //TODO: Update 2;
                websiteData.child(id).updateChildren(map);
//                websiteData.updateChildren(map);
            });
        });
    }

    private void showInRealtimeDatabase() {
        String name = editName.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            mywebsite = new WebInfoHelper(id, name, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");

            retriveEditTextFields();
            mywebsite.setDesignationName(reditDesignation);
            mywebsite.setBusinessAddress(reditBusinessAdd);
            mywebsite.setReferenceWebsite(reditReferenceWebsite);

            mywebsite.setPhoneNo(reditMobileNo);
            mywebsite.setEmailId(reditEmailId);
            mywebsite.setWebsiteType(typeWebsite);

            mywebsite.setFacebookPageLink(reditFacebookId);
            mywebsite.setLinkedInPageLink(reditLinkedInId);
            mywebsite.setWhatsappNumber(reditWhatsappNo);
            mywebsite.setTwitterIDLink(reditTwitterId);
            mywebsite.setTelegramIDLink(reditTelegramId);
            mywebsite.setInstagramIDLink(reditInstagramId);

            mywebsite.setGoogleMapLocationlink(reditGoogleMapLocationLink);
            mywebsite.setAboutUs(reditAboutUs);
            mywebsite.setImageProfileLink(imageLinkUser);

            AtomicReference<String> imageLink = new AtomicReference<>("");
            Log.e(TAG, "showInRealtimeDatabase: RealTime UID: " + id
                    + "\nAuthentication UID: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

            /*database.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.getResult().get("image") != null) {
//                    imageLink.set(task.getResult().get("image").toString());
                    mywebsite.setImageLink(task.getResult().get("image").toString());

                }
                Log.e(TAG, "onComplete() called with: task = [" + task.getResult().getData() + "]");
            }).addOnFailureListener(e -> Log.e(TAG, "onFailure: " + e.getMessage()));*/


//            Map<String,String> valuesDB=new HashMap<>();
//            valuesDB.put("name",name);

            //TODO: Update 3
            websiteData.child(id).setValue(mywebsite);
//            websiteData.setValue(mywebsite);
//            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Name can't be empty", Toast.LENGTH_SHORT).show();
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
        String name = editName.getText().toString();
        try {
            imagesSelectedURL = new ArrayList<>();

            if (!TextUtils.isEmpty(name)) {
                progressDialog.show();
                showInRealtimeDatabase();

                updateUI(user, imagesSelected, true);
            } else {
                Toast.makeText(this, "Name can't be empty", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            updateUI(user, imagesSelected, false);
//            StorageReference spaceRef = storageReference.child("USERS/" + user.getUid() + "/WebProfilePicture" + System.currentTimeMillis() + ".jpg");
//            spaceRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
////                    Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();
//                spaceRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                    Log.e(TAG, "updateUI: PHOTO URL TO BE SAVED: " + uri);
////                 imagesSelectedURL.add(uri.toString());
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("imageLink", uri.toString());
//                    imageLinkUser = uri.toString();
////                        websiteData.child(id).updateChildren(map);
//                });
//            });
        }
        if (!TextUtils.isEmpty(name)) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {
                //Do something after 8000ms
                progressDialog.dismiss();
                Intent intent = new Intent(this, WebsitePreviewActivity.class);
                intent.putExtra(TYPE_WEBSITE, typeWebsite);
                startActivity(intent);
                Toast.makeText(WebsiteActivity.this, "Created.", Toast.LENGTH_SHORT).show();
                finish();
            }, 20000);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        adapter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE_WEBSITE) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data.getData();
                webprofile_image.setImageURI(imageUri);
//                StorageReference spaceRef = storageReference.child("USERS/" + user.getUid() + "/WebProfilePicture" + System.currentTimeMillis() + ".jpg");
//                spaceRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
////                    Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show();
//                    spaceRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                        Log.e(TAG, "updateUI: PHOTO URL TO BE SAVED: " + uri);
////                 imagesSelectedURL.add(uri.toString());
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("imageLink", uri.toString());
//                        imageLinkUser = uri.toString();
////                        websiteData.child(id).updateChildren(map);
//                    });

//                }).addOnCompleteListener(task -> {
//                    Toast.makeText(this, "Done with uploading...", Toast.LENGTH_SHORT).show();
//                }).addOnFailureListener(e -> Toast.makeText(this, "Failed.", Toast.LENGTH_SHORT).show());
            }
        }
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