package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.ProductEntry;
import com.example.growfast.InterfacesUsed.LoadMyTemplates;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.WebsiteCardRecyclerViewAdapter;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.ProductGridItemDecoration;
import com.example.growfast.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebCardsActivity extends AppCompatActivity implements LoadMyTemplates {
    public final String REF_WEBSITE_LINK = "websiteLink";
    public final String LOGIN_STATS = "loginStats";
    public static final String TYPE_WEBSITE = "type";
    SharedPreferences sharedPreferences;
    String getTypeWebsite;
    TextView createdWebsiteRecycler;

    LoadMyTemplates loadMyTemplates;
    private RecyclerView recyclerView;
    List<ProductEntry> refreshList;
    LinearLayout websiteRecycler;
    CollectionReference templatesRef;
    private WebsiteCardRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_cards);
        initializeCards();
        setRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refreshiconclick) {
            Log.d("products", "onOptionsItemSelected: List--> " + refreshList);
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_desk_menu, menu);
        return true;
    }

    private void initializeCards() {
        templatesRef = FirebaseFirestore.getInstance().collection("CreateWebsite");
        loadMyTemplates = this;
        websiteRecycler = findViewById(R.id.websiteDetailsRecycler);
        createdWebsiteRecycler = findViewById(R.id.created_websiteRecycler);
        sharedPreferences = getSharedPreferences(LOGIN_STATS, Context.MODE_PRIVATE);
        getTypeWebsite = sharedPreferences.getString(REF_WEBSITE_LINK, "");
        if (!getTypeWebsite.equals("")) {
            showLastCreatedWebsite();
        }
        setUpToolbar();
        loadTemplates();
    }

    private void showLastCreatedWebsite() {
        websiteRecycler.setVisibility(View.VISIBLE);
        createdWebsiteRecycler.setText(getTypeWebsite);
    }

    private void loadTemplates() {
        templatesRef.get().addOnCompleteListener(task -> {
            List<ProductEntry> products = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                    ProductEntry product = bannerSnapshot.toObject(ProductEntry.class);
                    products.add(product);
                }
                loadMyTemplates.onTemplateLoadSuccess(products);
            }
        }).addOnFailureListener(e -> loadMyTemplates.onTemplatedLoadFailure(e.getMessage()));
    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.webrecycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        /*
         * Pass parameter as list of type ProductEntry
         * Must be retrieved from database to here only
         * ProductEntry contains three fields:
         * ImageView productImage
         * TextView productName, productCost;
         * */

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.webcards_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    @Override
    public void onTemplateLoadSuccess(List<ProductEntry> templates) {
        refreshList = templates;
        adapter = new WebsiteCardRecyclerViewAdapter(this, templates);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    @Override
    public void onTemplatedLoadFailure(String message) {

    }

    public void copyWebsiteButtonRecycler(View view) {
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(createdWebsiteRecycler.getText().toString());
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
    }

    public void shareViaWhatsappLogo(View view) {
        shareViaWhatsApp();
    }

    public void shareViaWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, createdWebsiteRecycler.getText().toString());
        try {
            Objects.requireNonNull(this).startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
        }
    }

    public void sharetoOthers(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, createdWebsiteRecycler.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}