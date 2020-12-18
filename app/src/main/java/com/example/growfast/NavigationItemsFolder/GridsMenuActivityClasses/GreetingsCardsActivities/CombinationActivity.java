package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.ProductEntry;
import com.example.growfast.InterfacesUsed.LoadMyCombination;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter.CombinationsCardRecyclerViewAdapter;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.ProductGridItemDecoration;
import com.example.growfast.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CombinationActivity extends AppCompatActivity implements LoadMyCombination {
    private RecyclerView recyclerView;
    LoadMyCombination loadMyCombination;

    CollectionReference loadMyCombinationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);
        initializeCards();
        setRecyclerView();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.combination_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeCards() {
        loadMyCombinationsRef = FirebaseFirestore.getInstance().collection("Combinations Greetings");
        loadMyCombination = this;
        setUpToolbar();
        loadLifeGreets();
    }

    private void loadLifeGreets() {
        loadMyCombinationsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<ProductEntry> products = new ArrayList<>();
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                        ProductEntry product = bannerSnapshot.toObject(ProductEntry.class);
                        products.add(product);
                    }
                    loadMyCombination.onCombinationsLoadSuccess(products);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadMyCombination.onCombinationsLoadFailure(e.getMessage());
            }
        });
    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.greetcombinationsRecyclerView);
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

    @Override
    public void onCombinationsLoadSuccess(List<ProductEntry> templates) {
        CombinationsCardRecyclerViewAdapter adapter = new CombinationsCardRecyclerViewAdapter(this, templates);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    @Override
    public void onCombinationsLoadFailure(String message) {
        Toast.makeText(this, "Something went wrong: " + message, Toast.LENGTH_SHORT).show();
    }
}