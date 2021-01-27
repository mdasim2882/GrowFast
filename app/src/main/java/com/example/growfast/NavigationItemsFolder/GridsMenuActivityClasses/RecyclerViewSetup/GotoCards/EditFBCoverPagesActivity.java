package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.growfast.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditFBCoverPagesActivity extends AppCompatActivity {


    public static final String MAGGY = "MAGGY";
    public static final String AUTHENTICATION_CHECKUP = "AUTHENTICATION_CHECKUP";
    private int _xDelta;
    private int _yDelta;
    Uri picUri;


    String image_name;

    private String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";
    Button sharePoster, addLogo;
    RelativeLayout convertEditLifetoPdf;
    TextView dpeditLife, mobileditLife, usernameWatermark, mobileWatermark;
    LinearLayout mWatermark;
    FirebaseFirestore database;

    private File sdCard = Environment.getExternalStorageDirectory();
    private ImageView cardActual;
    private String imageSetter;
    private PdfDocument pdfDocument;
    private PdfDocument.PageInfo myPageInfo;
    private PdfDocument.Page page;
    private Bitmap bitmap, scaledBitmap, scaledBitmapper;
    private Bitmap iconbitmap;
    private Bitmap iconscaledBitmap;
    FirebaseAuth firebaseAuth;

    String cardUsername = "Unknown", cardPhoneNumber, cardWatermarkName = "Unknown", cardWatermarkPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_f_b_cover_pages);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {
            cardPhoneNumber = firebaseAuth.getCurrentUser().getPhoneNumber();
            cardWatermarkPhoneNumber = cardPhoneNumber;

            Log.d(AUTHENTICATION_CHECKUP, "onCreate() called with: savedInstanceState = [" + firebaseAuth.getCurrentUser().getPhoneNumber() + "]");
        }
        //Initialize view here
        initializeViews();
        setUpToolbar();
        loadNamefromDB();

    }

    private void loadNamefromDB() {
        database.collection("User").document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    String n = task.getResult().get("name").toString();
                    if (n != null && !n.equals("")) {
                        dpeditLife.setText(n);
                        usernameWatermark.setText(n);
                    } else {
                        dpeditLife.setText(cardUsername);
                        usernameWatermark.setText(cardWatermarkName);
                    }
                }).addOnFailureListener(e -> {
            String message = e.getMessage().toString();
            String error = "Update your profile to continue.\nError: " + message;
            showToaster(error);
            dpeditLife.setText(cardUsername);
            usernameWatermark.setText(cardUsername);
        });
    }


    private void initializeViews() {
        imageSetter = getIntent().getStringExtra("fbcardsUri");
        Log.d("TAG", "initializeViews() returned: imageSetter " + imageSetter);


        convertEditLifetoPdf = findViewById(R.id.editFbCovertoPDF);
        dpeditLife = findViewById(R.id.editFBCoverDPUsername);
        mobileditLife = findViewById(R.id.editFBCoverMobile);
        usernameWatermark = findViewById(R.id.usernameWatermark);
        mobileWatermark = findViewById(R.id.mobileWatermark);
        sharePoster = findViewById(R.id.FbCoversharePosterButton);
        addLogo = findViewById(R.id.FbCoveraddLogoButton);


        cardActual = findViewById(R.id.editFbCoverImage);
        mWatermark = findViewById(R.id.Watermark);

        mobileditLife.setText(cardPhoneNumber);
        mobileWatermark.setText(cardWatermarkPhoneNumber);

        Picasso.get().load(imageSetter).into(cardActual);


    }


    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.editFbCover_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.hideOverflowMenu();
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(MAGGY, "Permission is granted");
                return true;
            } else {

                Log.v(MAGGY, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(MAGGY, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(MAGGY, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public void saveAsFbCoverPNG(View view) throws IOException {


        if (isStoragePermissionGranted()) {
            File dir = new File(sdCard.getAbsolutePath() + "/Digital Advisor/FBCover");
// create this directory if not already created
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Bitmap bitmap = Bitmap.createBitmap(convertEditLifetoPdf.getWidth(), convertEditLifetoPdf.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bitmap);
            convertEditLifetoPdf.draw(c);
            File outputFile; // Where to save it
            outputFile = new File(dir, "FbCoverGreeting" + System.currentTimeMillis() + ".png");
            try {

                FileOutputStream out = new FileOutputStream(outputFile);
                boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                viewInGallery(outputFile);
                out.close();
            } catch (IOException e) {
                showToaster(e.getMessage());
            }
        }


    }

    private void viewInGallery(File outputFile) {
        final Intent intent = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                                FileProvider.getUriForFile(this, getPackageName() + ".provider", outputFile) : Uri.fromFile(outputFile),
                        "image/*")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivity(intent);
    }

    public void convertPNGButton(View view) {

        int isShown = mWatermark.getVisibility();
        if (isShown == View.GONE) {
            mWatermark.setVisibility(View.VISIBLE);
            addLogo.setText("DELETE BOOKMARK");
        } else {
            mWatermark.setVisibility(View.GONE);
            addLogo.setText("ADD BOOKMARK");

        }
    }


    private void showToaster(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}