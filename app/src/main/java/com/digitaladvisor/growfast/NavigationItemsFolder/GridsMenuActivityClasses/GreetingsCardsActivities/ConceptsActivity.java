package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaladvisor.growfast.HelperMethods.ProductEntry;
import com.digitaladvisor.growfast.InterfacesUsed.LoadMyConcepts;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter.ConceptsCardRecyclerViewAdapter;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.ProductGridItemDecoration;
import com.digitaladvisor.growfast.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConceptsActivity extends AppCompatActivity implements LoadMyConcepts {
    private RecyclerView recyclerView;
    LoadMyConcepts loadMyConcepts;

    CollectionReference loadMyConceptRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concepts);
        initializeCards();
        setRecyclerView();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.concepts_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initializeCards() {
        loadMyConceptRef = FirebaseFirestore.getInstance().collection("Concepts Greetings");
        loadMyConcepts = this;
        setUpToolbar();
        loadConceptGreets();
    }

    private void loadConceptGreets() {
        loadMyConceptRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<ProductEntry> products = new ArrayList<>();
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                        ProductEntry product = bannerSnapshot.toObject(ProductEntry.class);
                        products.add(product);
                    }
                    loadMyConcepts.onConceptsLoadSuccess(products);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadMyConcepts.onConceptsLoadFailure(e.getMessage());
            }
        });
    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.greetconceptsRecyclerView);
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
    public void onConceptsLoadSuccess(List<ProductEntry> templates) {
        ConceptsCardRecyclerViewAdapter adapter = new ConceptsCardRecyclerViewAdapter(this, templates);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    @Override
    public void onConceptsLoadFailure(String message) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refreshiconclick) {
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