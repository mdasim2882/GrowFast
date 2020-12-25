package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;
// To use Crop Image Activity from Activity you can simply pass this as parameter


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.growfast.R;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditsDifferentGreetingsCards extends AppCompatActivity {
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    Uri picUri;


    String image_name;

    private String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";
    Button sharePoster, addLogo;
    RelativeLayout convertEditLifetoPdf;
    CircleImageView dpeditLife, mLogoiconEditLife;

    private File sdCard = Environment.getExternalStorageDirectory();
    ImageView cardActual;
    private String imageSetter;
    private PdfDocument pdfDocument;
    private PdfDocument.PageInfo myPageInfo;
    private PdfDocument.Page page;
    private Bitmap bitmap, scaledBitmap, scaledBitmapper;
    private Bitmap iconbitmap;
    private Bitmap iconscaledBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_life_card);

        //Initialize view here
        initializeViews();
        setUpToolbar();

    }


    private void initializeViews() {
        imageSetter = getIntent().getStringExtra("cardUri");

        rootLayout = (ViewGroup) findViewById(R.id.view_root);
        convertEditLifetoPdf = findViewById(R.id.editLifeframetoPDF);
        sharePoster = findViewById(R.id.sharePosterButton);
        addLogo = findViewById(R.id.addLogoButton);
        dpeditLife = findViewById(R.id.editGreetsLifeDP);
        mLogoiconEditLife = findViewById(R.id.editGreetsLifeLOGOICON);


        cardActual = findViewById(R.id.editLifeCardImage);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        RelativeLayout.LayoutParams newparams = new RelativeLayout.LayoutParams(125, 125);
        dpeditLife.setLayoutParams(layoutParams);


        dpeditLife.requestLayout();
        dpeditLife.setOnTouchListener(new ChoiceTouchListener());

        mLogoiconEditLife.setLayoutParams(newparams);
        mLogoiconEditLife.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getTag() != null && view.getTag().equals("icondp")) {
                    final int X = (int) event.getRawX();
                    final int Y = (int) event.getRawY();
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            RelativeLayout.LayoutParams viewLayoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                            _xDelta = X - viewLayoutParams.leftMargin;
                            _yDelta = Y - viewLayoutParams.topMargin;
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            RelativeLayout.LayoutParams moverlayoutParams = (RelativeLayout.LayoutParams) view
                                    .getLayoutParams();
                            moverlayoutParams.leftMargin = X - _xDelta;
                            moverlayoutParams.topMargin = Y - _yDelta;
                            moverlayoutParams.rightMargin = -250;
                            moverlayoutParams.bottomMargin = -250;
                            view.setLayoutParams(moverlayoutParams);
                            break;
                    }
                    rootLayout.invalidate();
                }
                return true;
            }
        });
        mLogoiconEditLife.setX(250);
        mLogoiconEditLife.setY(500);


        Picasso.get().load(imageSetter).into(cardActual);


    }


    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.editLife_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (image_name.equals("dp")) {
                    picUri = result.getUri();
                    dpeditLife.setImageURI(picUri);

                    try {
                        // Fill a null bitmap object and scaled to fit in PDF
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                        scaledBitmap = Bitmap.createScaledBitmap(bitmap, 90, 90, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (image_name.equals("icon")) {
                    picUri = result.getUri();
                    mLogoiconEditLife.setImageURI(picUri);

                    try {
                        // Fill a null bitmap object and scaled to fit in PDF
                        iconbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                        iconscaledBitmap = Bitmap.createScaledBitmap(iconbitmap, 90, 90, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public void saveAsPNG(View view) throws IOException {


        File dir = new File(sdCard.getAbsolutePath() + "/Digital Advisor/Images");
// create this directory if not already created
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Bitmap bitmap = Bitmap.createBitmap(convertEditLifetoPdf.getWidth(), convertEditLifetoPdf.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        convertEditLifetoPdf.draw(c);
        File outputFile; // Where to save it
        outputFile = new File(dir, "Greeting" + System.currentTimeMillis() + ".png");
        try {

            FileOutputStream out = new FileOutputStream(outputFile);
            boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (IOException e) {
            showToaster(e.getMessage());
        }


//        PdfGenerator.getBuilder()
//                .setContext(this)
//                .fromViewIDSource()
//                .fromViewID(this, R.id.editLifeframetoPDF)
//                /* "fromViewID()" takes array of view ids those MUST BE and MUST BE contained in the inserted "activity" .
//                 * You can also invoke "fromViewIDList()" method here which takes list of view ids instead of array. */
//                .setCustomPageSize(convertEditLifetoPdf.getWidth(), convertEditLifetoPdf.getHeight())
//                /* Here I used ".setCustomPageSize(3000,3000)" to set custom page size.*/
//                .setFileName("LifeFrame-PDF")
//                .setFolderName("Test-PDF-folder/PDFs")
//                .openPDFafterGeneration(true)
//                .build(new PdfGeneratorListener() {
//                    @Override
//                    public void onFailure(FailureResponse failureResponse) {
//                        super.onFailure(failureResponse);
//                        Toast.makeText(EditsDifferentGreetingsCards.this, "Failed to convert", Toast.LENGTH_SHORT).show();
//                        Log.d("STATUS:", "onFailure: \"Failed to convert\"");
//
//                    }
//
//                    @Override
//                    public void showLog(String log) {
//                        super.showLog(log);
//                        Toast.makeText(EditsDifferentGreetingsCards.this, "In Progress...", Toast.LENGTH_SHORT).show();
//                        Log.d("STATUS:", "onFailure: \"In Progress...\"");
//
//                    }
//
//                    @Override
//                    public void onSuccess(SuccessResponse response) {
//                        super.onSuccess(response);
//                        Toast.makeText(EditsDifferentGreetingsCards.this, "Created Successfully...", Toast.LENGTH_SHORT).show();
//
//                        Log.d("STATUS:", "onFailure: \"Successfully Converted...\"");
//                    }
//                });
    }

    public void convertButton(View view) {

        int isShown = mLogoiconEditLife.getVisibility();
        if (isShown == View.GONE) {
            mLogoiconEditLife.setVisibility(View.VISIBLE);
            addLogo.setText("DELETE LOGO");
        } else {
            mLogoiconEditLife.setVisibility(View.GONE);
            addLogo.setText("ADD LOGO");

        }
    }

    private void createPDFwithoutLibrary() {
        convertEditLifetoPdf.setDrawingCacheEnabled(true);
        Bitmap bitmapper = Bitmap.createBitmap(convertEditLifetoPdf.getDrawingCache());
        convertEditLifetoPdf.setDrawingCacheEnabled(false);

        pdfDocument = new PdfDocument();
        myPageInfo = new PdfDocument.PageInfo.Builder(cardActual.getWidth(), cardActual.getHeight(), 1).create();
        page = pdfDocument.startPage(myPageInfo);


        page.getCanvas().drawBitmap(bitmapper, 0, 0, null);


        pdfDocument.finishPage(page);


        String pdfFile = directory + "/PDFwithoutLibrary.pdf";
        File myPDFFile = new File(pdfFile);

        try {
            pdfDocument.writeTo(new FileOutputStream(myPDFFile));
            Toast.makeText(this, "Directory: " + directory, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
    }


    private void createPDFDocument() {
        // Create and set default margins in PDF
        Document document = new Document();
        document.setMargins(0, 10, 0, 0);
        Rectangle size = new Rectangle(cardActual.getWidth(), cardActual.getHeight());
        document.setPageSize(size);

        // Assign some name to PDF
        String pdfFile = directory + "/UsingIText.pdf";
        File myPDFFile = new File(pdfFile);
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(myPDFFile));

            document.open();

            Paragraph paragraph = new Paragraph();
            paragraph.add("You can find the IText tutorial at ");


            ByteArrayOutputStream stream = null;
            Image image = null;

            // bitmap is for selection of  Logo here only
            if (bitmap != null) {       // if addlogo=true i.e.,true and image has been selected in bitmap
                stream = new ByteArrayOutputStream();
                scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);      //
                image = Image.getInstance(stream.toByteArray());

            } else {
                // showToast here
                showToaster("Logo not selected yet");
                return;
            }


            convertEditLifetoPdf.setDrawingCacheEnabled(true);
            Bitmap bitmapper = Bitmap.createBitmap(convertEditLifetoPdf.getDrawingCache());
            convertEditLifetoPdf.setDrawingCacheEnabled(false);


            scaledBitmapper = Bitmap.createScaledBitmap(bitmapper, cardActual.getWidth(), cardActual.getHeight(), false);

            ByteArrayOutputStream background = new ByteArrayOutputStream();
            scaledBitmapper.compress(Bitmap.CompressFormat.PNG, 100, background);
            Image back = Image.getInstance(background.toByteArray());
            back.setAbsolutePosition(0, 0);

//            Chunk choker = new Chunk(back, 0, 0, true);
//            choker.setAnchor(new URL("http://tutorials.jenkov.com/java-itext/index.html"));

            Chunk chunk = new Chunk(image, 0, 0, true);         //offsetX and offsetY are logo image offsets
//            chunk.setAnchor(new URL("http://www.google.com"));


            Paragraph p = new Paragraph();
            p.add(chunk);
            document.add(p);


            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(back);
            // Add a background canvas

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showToaster(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dppictureiconclick) {
            image_name = "dp";
            CropImage.activity().setAspectRatio(1, 1).start(EditsDifferentGreetingsCards.this);
        }
        if (item.getItemId() == R.id.edilogoiconclick) {
            image_name = "icon";
            CropImage.activity().setAspectRatio(1, 1).start(EditsDifferentGreetingsCards.this);
            int isShown = mLogoiconEditLife.getVisibility();
            if (isShown == View.GONE) {
                mLogoiconEditLife.setVisibility(View.VISIBLE);
                addLogo.setText("DELETE LOGO");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_dp_logo_menu, menu);
        return true;
    }


    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            if (view.getTag() != null && view.getTag().equals("mdp")) {

                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        view.setLayoutParams(layoutParams);
                        break;
                }
            }
            rootLayout.invalidate();
            return true;
        }
    }
}