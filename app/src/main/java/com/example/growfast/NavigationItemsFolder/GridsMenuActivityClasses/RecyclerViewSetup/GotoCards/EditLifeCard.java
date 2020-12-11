package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.growfast.R;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditLifeCard extends AppCompatActivity {


    Button sharePoster;
    FrameLayout convertEditLifetoPdf;
    CircleImageView dpeditLife;

    ImageButton sharepos;
    ImageView cardActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_life_card);

        initializeViews();
        setUpToolbar();

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void saveAsPDF(View view) {
        Integer ht_len = cardActual.getHeight();
        Integer width_len = cardActual.getWidth();
        int h = dpToPx(ht_len);
        int w = dpToPx(width_len);

        PdfGenerator.getBuilder()
                .setContext(this)
                .fromViewIDSource()
                .fromViewID(this, R.id.editLifeframetoPDF)
                /* "fromViewID()" takes array of view ids those MUST BE and MUST BE contained in the inserted "activity" .
                 * You can also invoke "fromViewIDList()" method here which takes list of view ids instead of array. */

                .setCustomPageSize(720, 959)
                /* Here I used ".setCustomPageSize(3000,3000)" to set custom page size.*/
                .setFileName("LifeFrame-PDF")
                .setFolderName("Test-PDF-folder/PDFs")
                .openPDFafterGeneration(true)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Toast.makeText(EditLifeCard.this, "Failed to convert", Toast.LENGTH_SHORT).show();
                        Log.d("STATUS:", "onFailure: \"Failed to convert\"");

                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);
                        Toast.makeText(EditLifeCard.this, "In Progress...", Toast.LENGTH_SHORT).show();
                        Log.d("STATUS:", "onFailure: \"In Progress...\"");

                    }

                    @Override
                    public void onSuccess(SuccessResponse response) {
                        super.onSuccess(response);
                        Toast.makeText(EditLifeCard.this, "Successfully Converted...", Toast.LENGTH_SHORT).show();

                        Log.d("STATUS:", "onFailure: \"Successfully Converted...\"");
                    }
                });
    }

    private void initializeViews() {
        String imageSetter = getIntent().getStringExtra("cardUri");


        convertEditLifetoPdf = findViewById(R.id.editLifeframetoPDF);
        sharePoster = findViewById(R.id.sharePosterButton);
        dpeditLife = findViewById(R.id.editGreetsLifeDP);


        cardActual = findViewById(R.id.editLifeCardImage);
        Picasso.get().load(imageSetter).into(cardActual);


    }

    public void gotoLink(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.google.com/"));
        startActivity(intent);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.editLife_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showMe(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.google.com/"));
        startActivity(intent);
    }

    public void addLogo(View view) {
    }
}