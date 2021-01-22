package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import com.example.growfast.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.EditFBCoverPagesActivity.MAGGY;

public class ProductOverview extends AppCompatActivity {
    // Pdf Card Details
    public static final String CARD_WIDTH = "cardWidth";
    public static final String CARD_HEIGHT = "cardHeight";
    public static final int CARD_DETAILS = 1;

    // Activity result material
    ProgressDialog progressDialog;
    public static final String PHONE_NO = "PHONE_NO";
    public static final String YOUTUBE_CHANNEL = "YOUTUBE_CHANNEL";
    public static final String EMAIL_ID = "EMAIL_ID";
    public static final String WEBSITE_URL = "WEBSITE_URL";
    public static final String WHATSAPP_NO = "WHATSAPP_NO";
    public static final String FACEBOOK_ID = "FACEBOOK_ID";
    public static final String LINKEDIN_ID = "LINKEDIN_ID";


    //Counter for valid inputs
    int counter = 3;
    //Inputs received strings
    private String dataFacebookId, dataLinkedIn, dataWhatsappNo, dataWebsiteUrl;
    private String dataMobile;
    private String dataEmailId;
    private String dataYoutube;


    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    Uri picUri;
    RelativeLayout convertDigitalCardLayout;
    CircleImageView userCenteredImage;
    TextView textViewAboutUs;


    private String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";
    private File sdCard = Environment.getExternalStorageDirectory();
    Button moveNextbutton;
    private String imageSetter;
    ImageView cardActual, mWhatsappLogo, mFacebookLogo, mLinkedLogo, mWebsiteLogo, mcallLogo, mYoutubeLogo, mMessagingLogo;
    private Bitmap scaledBitmapper;
    private PdfPCell cell7;
    private PdfPCell cell6;
    private PdfPCell cell5;
    private PdfPCell cell4;
    private PdfPCell cell3;
    private PdfPCell cell2;
    private PdfPCell cell1;
    private Image back;
    private Image whatsapp;
    private Image facebook;
    private Image linkedIn;
    private Image website;
    private Image contactno;
    private Image youtube;
    private Image messaging;
    private PdfPTable table;
    private LinearLayout ll;
    private File myPDFFile;
    private Document document;
    private String fname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);
        counter = 3;
        try {
            initializeViews();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }
        setUpToolbar();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.digitalCards_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_24));
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initializeViews() throws IOException, BadElementException {
        ll = findViewById(R.id.linksharesbox);
        imageSetter = getIntent().getStringExtra("digiCardUri");

        rootLayout = (ViewGroup) findViewById(R.id.digitalCards_view_root);
        convertDigitalCardLayout = findViewById(R.id.digitalCardsframetoPDF);
        moveNextbutton = findViewById(R.id.moveNextbtn);
        userCenteredImage = findViewById(R.id.digitalCardCenteredDP);
        textViewAboutUs = findViewById(R.id.digitalCardAboutusText);


        cardActual = findViewById(R.id.actualdigitalCardsImage);
        mWhatsappLogo = findViewById(R.id.whatsappLogo);
        mFacebookLogo = findViewById(R.id.facebookLogo);
        mLinkedLogo = findViewById(R.id.linkedInLogo);
        mWebsiteLogo = findViewById(R.id.websiteLogo);
        mcallLogo = findViewById(R.id.contactLogo);
        mYoutubeLogo = findViewById(R.id.youtubeLogo);
        mMessagingLogo = findViewById(R.id.messagingLogo);


        // For drag and Drop movement between images
        setProfileMiddleLogo();


        Picasso.get().load(imageSetter).into(cardActual);
        setAboutUsTextMiddle();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // Obtain table cells
                obtainsTableCells();
            }
        };
        Handler h = new Handler();
        h.post(r);


    }

    private void setAboutUsTextMiddle() {
        int mywidth = RelativeLayout.LayoutParams.MATCH_PARENT;
        Log.d("About Us", "setAboutUsTextMiddle() called with width: " + mywidth);
        RelativeLayout.LayoutParams textlayoutParams = new RelativeLayout.LayoutParams(500, 225);
        textViewAboutUs.setLayoutParams(textlayoutParams);
        textViewAboutUs.setWidth(400);
        textViewAboutUs.requestLayout();
//      textViewAboutUs.setWidth(textlayoutParams.width-20);

        textViewAboutUs.setOnTouchListener((view, event) -> {
            if (view.getTag() != null && view.getTag().equals("cardsTP")) {
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
        });
//        userCenteredImage.setX(250);
//        userCenteredImage.setY(500);
    }

    private void obtainsTableCells() {
        // Get logos
        try {
            getAllimagesfromDrawable();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cell1 = new PdfPCell();
        cell2 = new PdfPCell();
        cell3 = new PdfPCell();
        cell4 = new PdfPCell();
        cell5 = new PdfPCell();
        cell6 = new PdfPCell();
        cell7 = new PdfPCell();


        cell1.setBorder(PdfPCell.NO_BORDER);
        cell2.setBorder(PdfPCell.NO_BORDER);
        cell3.setBorder(PdfPCell.NO_BORDER);
        cell4.setBorder(PdfPCell.NO_BORDER);
        cell5.setBorder(PdfPCell.NO_BORDER);
        cell6.setBorder(PdfPCell.NO_BORDER);
        cell7.setBorder(PdfPCell.NO_BORDER);
    }

    private void setProfileMiddleLogo() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(225, 225);
        userCenteredImage.setLayoutParams(layoutParams);

        userCenteredImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getTag() != null && view.getTag().equals("cardsDP")) {
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
        userCenteredImage.setX(250);
        userCenteredImage.setY(500);
    }

    private void getAllimagesfromDrawable() throws BadElementException, IOException {

        Log.d("checkStatus", "createPDFDocument: Called for NewPDF Creation...");
        // Find images in Drawable like whatsapp,facebook,instagram,etc
        whatsapp = getImageFromDrawable(R.drawable.newwp, 0);
        whatsapp.scalePercent(15, 15);

        facebook = getImageFromDrawable(R.drawable.fbtransparent, 0);
        facebook.scalePercent(15, 15);

        linkedIn = getImageFromDrawable(R.drawable.lkndinlogotransparent, 0);
        linkedIn.scalePercent(15, 15);

        website = getImageFromDrawable(R.drawable.growfast, 0);
        website.scalePercent(15, 15);

        contactno = getImageFromDrawable(R.drawable.bgtransparentphone, 0);
        contactno.scalePercent(15, 15);

        youtube = getImageFromDrawable(R.drawable.logo_digitaladvisor, 1);
        youtube.scalePercent(15, 15);

        messaging = getImageFromDrawable(R.drawable.messagetransparent, 0);
        messaging.scalePercent(15, 15);


    }


    private void createPDFDocument(int count) throws IOException, BadElementException {

        // Convert image for background
        back = getImageforBackGround();
        back.setAbsolutePosition(0, 0);
//        ll.setVisibility(View.VISIBLE);


        Log.d("checkStatus", "createPDFDocument: Called for PDF Creation...");
        // Create and set default margins in PDF
        document = new Document();
        document.setMargins(0, 10, 0, 0);
        Rectangle size = new Rectangle(cardActual.getWidth(), cardActual.getHeight());
        document.setPageSize(size);

        // Assign some name to PDF
        File dir = new File(sdCard.getAbsolutePath() + "/Digital Advisor/PDFs");
// create this directory if not already created
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        fname = "Digital Card-" + n + ".pdf";
        String pdfFile = directory + "/NewPDF.pdf";
//        File myPDFFile = new File(pdfFile);
        myPDFFile = new File(dir, fname);
        try {

            FileOutputStream outputStream = new FileOutputStream(myPDFFile);
            PdfWriter writer = PdfWriter.getInstance(document,
                    outputStream);

            document.open();
            document.setPageCount(1);

            //Initialize table and set its properties
            getPdfPTableProperties(document, count);


            // Horizontal images in table
            Paragraph paragraph = new Paragraph();


            cell2.addElement(new Chunk(facebook, 0, 0, true).setAnchor(dataFacebookId));
            cell3.addElement(new Chunk(linkedIn, 0, 0, true).setAnchor(dataLinkedIn));
            cell4.addElement(new Chunk(website, 0, 0, true).setAnchor(dataWebsiteUrl));
            cell6.addElement(new Chunk(youtube, 0, 0, true).setAnchor(dataYoutube));

            table.addCell(cell1);
            table.addCell(cell5);
            table.addCell(cell7);
            if (counter > 3) {
                Log.e("TAG", "createPDFDocument: " + counter);
                if (dataWebsiteUrl != null) {
                    table.addCell(cell4);
                }
                if (dataLinkedIn != null) {
                    table.addCell(cell3);
                }
                if (dataYoutube != null) {
                    table.addCell(cell6);
                }
                if (dataFacebookId != null) {
                    table.addCell(cell2);
                }

            }

            paragraph.add(table);

            Paragraph p = new Paragraph(" ");
            int position = (int) (document.getPageSize().getHeight() - 175); //Document best fit size 140 for scaling of image=20;
            paragraph.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(position);

            document.add(p);


            document.add(paragraph);


            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(back);
            // Add a background canvas

            myPDFFile.setWritable(false);

            document.close();
            outputStream.flush();
            outputStream.close();
            writer.close();


        } catch (DocumentException e) {
            e.printStackTrace();
            Log.d("checkStatus", "createPDFDocument: Document Exception -----> " + e.getMessage());
        } catch (FileNotFoundException e) {
            Log.d("checkStatus", "createPDFDocument: FileNotFound Exception -----> " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("checkStatus", "createPDFDocument: IO Exception -----> " + e.getMessage());

            e.printStackTrace();
        }

    }

    private void getPdfPTableProperties(Document document, int count) {

        Log.d("TAG", "getPdfPTableProperties: Count: ---> " + count);
        if (count <= 3) {
            table = new PdfPTable(3);
            table.setWidthPercentage(50);
        } else {
            table = new PdfPTable(count);
            table.setWidthPercentage(90);
        }
        table.setTotalWidth(document.getPageSize().getWidth());
        table.setHorizontalAlignment(Element.ALIGN_CENTER);


    }

    private Image getImageFromDrawable(int imageID, int colorLogo) throws BadElementException, IOException {
        Drawable d = ResourcesCompat.getDrawable(getResources(), imageID, null);
        if (colorLogo == 1) {
            d.setTint(Color.RED);
        }
        Bitmap drawableBitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream drawableStream = new ByteArrayOutputStream();
        drawableBitmap.compress(Bitmap.CompressFormat.PNG, 100, drawableStream);
        byte[] bitmapData = drawableStream.toByteArray();
        return Image.getInstance(bitmapData);
    }

    private Image getImageforBackGround() throws BadElementException, IOException {


        convertDigitalCardLayout.setDrawingCacheEnabled(true);
        Bitmap bitmapper = Bitmap.createBitmap(convertDigitalCardLayout.getDrawingCache());
        convertDigitalCardLayout.setDrawingCacheEnabled(false);

        scaledBitmapper = Bitmap.createScaledBitmap(bitmapper, cardActual.getWidth(), cardActual.getHeight(), false);
        ByteArrayOutputStream background = new ByteArrayOutputStream();
        scaledBitmapper.compress(Bitmap.CompressFormat.PNG, 100, background);


        return Image.getInstance(background.toByteArray());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                picUri = result.getUri();
                userCenteredImage.setImageURI(picUri);

            }
        }

        if (requestCode == CARD_DETAILS && resultCode == RESULT_OK && data != null) {
            //Retrieving dataInputs

            try {
                retrieveDataInputs(data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadElementException e) {
                e.printStackTrace();
            }


        }

    }

    private void retrieveDataInputs(Intent data) throws IOException, BadElementException {
        dataMobile = data.getStringExtra(PHONE_NO);
        dataEmailId = data.getStringExtra(EMAIL_ID);
        dataWebsiteUrl = data.getStringExtra(WEBSITE_URL);
        dataWhatsappNo = data.getStringExtra(WHATSAPP_NO);
        dataFacebookId = data.getStringExtra(FACEBOOK_ID);
        dataLinkedIn = data.getStringExtra(LINKEDIN_ID);
        dataYoutube = data.getStringExtra(YOUTUBE_CHANNEL);


        if (dataMobile != null) {
//            counter++;
            cell5.addElement(new Chunk(contactno, 0, 0, true).setAnchor("tel:" + dataMobile));
        } else {
            cell5.addElement(new Chunk(contactno, 0, 0, true));
        }
        if (dataEmailId != null) {
//            counter++;

            cell7.addElement(new Chunk(messaging, 0, 0, true).setAnchor("mailto:" + dataEmailId));
        } else {
            cell7.addElement(new Chunk(messaging, 0, 0, true));
        }
        if (dataWebsiteUrl != null) {
            counter++;

        }
        if (dataWhatsappNo != null) {
            cell1.addElement(new Chunk(whatsapp, 0, 0, true).setAnchor("https://api.whatsapp.com/send?phone=+91" + dataWhatsappNo));
        } else {
            cell1.addElement(new Chunk(whatsapp, 0, 0, true));
        }
        if (dataFacebookId != null) {
            counter++;

        }
        if (dataLinkedIn != null) {
            counter++;

        }
        if (dataYoutube != null) {
            counter++;
        }

//createPDFDocument(counter);

        InitiatePDF runner = new InitiatePDF();


        runner.execute(counter);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.frameDPCentered) {
            CropImage.activity().setAspectRatio(1, 1).start(ProductOverview.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.digital_cardsmenu, menu);
        return true;
    }

    private void showToaster(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    public void movetonext(View view) {
        if (isStoragePermissionGranted()) {
            Intent addLinksCard = new Intent(this, FillDigitalCardDetailsActivity.class);
            startActivityForResult(addLinksCard, CARD_DETAILS);
        }

    }

    private class InitiatePDF extends AsyncTask<Integer, String, Integer> {

        private String resp;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = new ProgressDialog(ProductOverview.this);
            progressDialog.setTitle("Creating PDF");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            ll.setVisibility(View.GONE);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {

                createPDFDocument(integers[0]);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadElementException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //showToaster("Done: " + directory);
            counter = 3;
            table = null;
            ll.setVisibility(View.VISIBLE);
            //showToaster("Done: " + directory);


            showToaster("Created Successfully");
            viewPdf(fname);
            finish();
        }
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
            Log.v(MAGGY, "Permission: " + permissions[0] + " was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    private void viewPdf(String mfname) {
        String url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Digital Advisor/PDFs/" + mfname;
        File file = new File(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        intent.setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ? FileProvider.getUriForFile(this, getPackageName() + ".provider", file) : Uri.fromFile(file), "application/pdf");

        Intent start = Intent.createChooser(intent, "Open With");

        startActivity(intent);

        Log.d("MAGGY", "viewPdf: \nURI : " + Uri.fromFile(file).toString() +
                "\n Folder Path: " + file.getPath() +
                "\n Url String: " + url
                + "\nLESS THAN NOUGAT: " + FileProvider.getUriForFile(this, getPackageName() + ".provider", file));


    }

}
