package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses;
//Try it with multiple Access to Cards

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.HelperMethods.WhatsappVideoEntry;
import com.example.growfast.InterfacesUsed.LoadMyVideos;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.VideosRecyclerViewAdapter;
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

public class VideosCardsActivity extends AppCompatActivity implements LoadMyVideos {
    private RecyclerView recyclerView;
    LoadMyVideos loadMyVideoImages;

    String variety;

    CollectionReference templatesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_paste_videos);
        initializeCards();

        setRecyclerView();
    }

    private void setUpToolbar(String title) {


        Toolbar toolbar = findViewById(R.id.copypaste_details_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        if (title != null) {
            toolbar.setTitle(title);
        }
    }

    private void initializeCards() {
        setUpToolbar(null);
        String whereTo = getIntent().getStringExtra("from");
        String putMydataFrom = getIntent().getStringExtra("collectionName");


        if (whereTo != null) {
            peformWhereToWork(whereTo);
        }

        templatesRef = FirebaseFirestore.getInstance().collection(putMydataFrom);
        loadMyVideoImages = this;

        loadVideoImagesAndLinks();
    }

    private void peformWhereToWork(String whereTo) {
        switch (whereTo) {
            case "agentIntroVideos": {
                variety = "Agent Intro Videos";
                setUpToolbar(variety);
                break;
            }
            case "copyPaste": {
                variety = "Copy Paste Videos";
                setUpToolbar(variety);
                break;
            }
            case "detailedVideos": {
                variety = "In-Detailed Long Videos";
                setUpToolbar(variety);
                break;
            }

            case "uiPro": {
                variety = "UiPro Creativity Videos";
                setUpToolbar(variety);
                break;
            }
            case "greetingsVideos": {
                variety = "Greetings Videos";
                setUpToolbar(variety);
                break;
            }
            case "ownVideos": {
                variety = "Own Videos";
                setUpToolbar(variety);
                break;
            }


        }
    }

    private void loadVideoImagesAndLinks() {
        templatesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<WhatsappVideoEntry> products = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                        WhatsappVideoEntry product = bannerSnapshot.toObject(WhatsappVideoEntry.class);
                        products.add(product);
                    }
                    loadMyVideoImages.onVideosImageLoadSuccess(products);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadMyVideoImages.onVideosImageLoadFailure(e.getMessage());
            }
        });
    }

    private void setRecyclerView() {

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.copypasteRecyclerView);
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
    public void onVideosImageLoadSuccess(List<WhatsappVideoEntry> templates) {
        VideosRecyclerViewAdapter adapter = new VideosRecyclerViewAdapter(this, templates, variety);
        recyclerView.setAdapter(adapter);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        int largePadding = getResources().getDimensionPixelSize(R.dimen.updown_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.side_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
    }

    @Override
    public void onVideosImageLoadFailure(String message) {
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