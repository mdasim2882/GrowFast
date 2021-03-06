package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities;

import android.os.Bundle;
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
import com.digitaladvisor.growfast.InterfacesUsed.LoadMyHealth;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.GreetingCardsRecyclerViewAdapter.HealthCardRecyclerViewAdapter;
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

public class HealthActivity extends AppCompatActivity implements LoadMyHealth {
    private RecyclerView recyclerView;
    LoadMyHealth loadMyHealth;

    CollectionReference loadMyHealthRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        initializeCards();
        setRecyclerView();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.health_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeCards() {
        loadMyHealthRef = FirebaseFirestore.getInstance().collection("Health Greetings");
        loadMyHealth = this;
        setUpToolbar();
        loadHealthGreets();
    }

    private void loadHealthGreets() {
        loadMyHealthRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<ProductEntry> products = new ArrayList<>();
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                        ProductEntry product = bannerSnapshot.toObject(ProductEntry.class);
                        products.add(product);
                    }
                    loadMyHealth.onHealthLoadSuccess(products);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadMyHealth.onHealthLoadFailure(e.getMessage());
            }
        });
    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.health_greets_recycler_view);
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
    public void onHealthLoadSuccess(List<ProductEntry> templates) {
        HealthCardRecyclerViewAdapter adapter = new HealthCardRecyclerViewAdapter(this, templates);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    @Override
    public void onHealthLoadFailure(String message) {
        Toast.makeText(this, "No response:", Toast.LENGTH_SHORT).show();

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