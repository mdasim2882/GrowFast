package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;
import com.digitaladvisor.growfast.InterfacesUsed.LoadMyTemplates;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.DigitalCardRecyclerViewAdapter;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.ProductGridItemDecoration;
import com.digitaladvisor.growfast.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DigitalCardsActivity extends AppCompatActivity implements LoadMyTemplates {

    private RecyclerView recyclerView;
    LoadMyTemplates loadMyTemplates;
    List<ProductEntry> refreshList;

    CollectionReference templatesRef;
    private DigitalCardRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_cards);
        initializeCards();
        setRecyclerView();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.digicards_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeCards() {
        templatesRef = FirebaseFirestore.getInstance().collection("Templates");
        loadMyTemplates = this;
        setUpToolbar();
        loadTemplates();
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadMyTemplates.onTemplatedLoadFailure(e.getMessage());
            }
        });
    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
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

    @Override
    public void onTemplateLoadSuccess(List<ProductEntry> templates) {
        refreshList = templates;
        adapter = new DigitalCardRecyclerViewAdapter(this, templates);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    @Override
    public void onTemplatedLoadFailure(String message) {
        Toast.makeText(this, "No response:", Toast.LENGTH_SHORT).show();

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
}