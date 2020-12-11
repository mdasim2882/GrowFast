package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.CombinationActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.ComboPostersActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.ConceptsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.HealthActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities.LifeActivity;
import com.example.growfast.R;

public class GreetingsActivity extends AppCompatActivity {

    public static final String GREETINGS = "GREETINGS";
    public static final String FB_COVER_PAGES = "FB COVER PAGES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);
        intializeGridsItems();

    }

    private void intializeGridsItems() {
        setUpToolbar();
        GridLayout greetingsGrid = findViewById(R.id.greetingsGrid);
        GridLayout fbcoverGrid = findViewById(R.id.coverpagesGrid);
        setSingleEvent(greetingsGrid, GREETINGS);
        setSingleEvent(fbcoverGrid, FB_COVER_PAGES);
    }

    private void setSingleEvent(GridLayout mainGrid, String workedOn) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeMethodforCardIndex(finalI, workedOn);
                }
            });
        }
    }

    private void setUpToolbar() {

        Toolbar toolbar = findViewById(R.id.greetings_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void executeMethodforCardIndex(int finalI, String workedOn) {
        if (workedOn.equals(GREETINGS)) {
            switch (finalI) {
                case 0: {
                    Intent i = new Intent(this, LifeActivity.class);
                    //  Toast.makeText(this, "LifeActivity", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    break;
                }
                case 1: {
                    Intent i = new Intent(this, HealthActivity.class);
//                    Toast.makeText(this, "HealthActivity", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    break;
                }
                case 2: {
                    Intent i = new Intent(this, ComboPostersActivity.class);
//                    Toast.makeText(this, "ComboPostersActivity", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    break;
                }
            }
        } else if (workedOn.equals(FB_COVER_PAGES)) {
            switch (finalI) {
                case 0: {
                    Intent i = new Intent(this, CombinationActivity.class);
//                    Toast.makeText(this, "CombinationActivity", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    break;
                }
                case 1: {
                    Intent i = new Intent(this, ConceptsActivity.class);
//                    Toast.makeText(this, "ConceptsActivity", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    break;
                }

            }
        }

    }
}