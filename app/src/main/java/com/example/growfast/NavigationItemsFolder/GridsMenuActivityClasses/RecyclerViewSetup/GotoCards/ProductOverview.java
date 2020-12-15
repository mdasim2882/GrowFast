package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.net.MalformedURLException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductOverview extends AppCompatActivity {
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    Uri picUri;
    RelativeLayout convertDigitalCardLayout;
    CircleImageView userCenteredImage;

    private String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";
    Button moveNextbutton;
    private String imageSetter;
    ImageView cardActual;
    private Bitmap scaledBitmapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);

        initializeViews();
        setUpToolbar();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.digitalCards_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {

        imageSetter = getIntent().getStringExtra("digiCardUri");

        rootLayout = (ViewGroup) findViewById(R.id.digitalCards_view_root);
        convertDigitalCardLayout = findViewById(R.id.digitalCardsframetoPDF);
        moveNextbutton = findViewById(R.id.moveNextbtn);
        userCenteredImage = findViewById(R.id.digitalCardCenteredDP);


        cardActual = findViewById(R.id.actualdigitalCardsImage);


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


        Picasso.get().load(imageSetter).into(cardActual);


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
            writer.setSpaceCharRatio(8);
            document.open();


            // Find images in Drawable like whatsapp,facebook,instagram,etc
            Image image = getImageFromDrawable();
            image.scalePercent(15, 15);


            // Convert image for background
            Image back = getImageforBackGround();
            back.setAbsolutePosition(0, 0);


            PdfPTable table = getPdfPTableProperties(writer, document);
            PdfPCell cell;


            // Horizontal images in table
            Paragraph paragraph = new Paragraph();


            PdfPCell cell1 = new PdfPCell();
            PdfPCell cell2 = new PdfPCell();
            PdfPCell cell3 = new PdfPCell();
            PdfPCell cell4 = new PdfPCell();
            PdfPCell cell5 = new PdfPCell();
            PdfPCell cell6 = new PdfPCell();
            PdfPCell cell7 = new PdfPCell();
//            cell1.setBorderColor(BaseColor.WHITE);
//            cell2.setBorderColor(BaseColor.WHITE);
//            cell3.setBorderColor(BaseColor.WHITE);

//            cell1.setBackgroundColor(BaseColor.WHITE);
//            cell2.setBackgroundColor(BaseColor.WHITE);
//            cell3.setBackgroundColor(BaseColor.WHITE);

            cell1.setBorder(PdfPCell.NO_BORDER);
            cell2.setBorder(PdfPCell.NO_BORDER);
            cell3.setBorder(PdfPCell.NO_BORDER);
            cell4.setBorder(PdfPCell.NO_BORDER);
            cell5.setBorder(PdfPCell.NO_BORDER);
            cell6.setBorder(PdfPCell.NO_BORDER);
            cell7.setBorder(PdfPCell.NO_BORDER);


            cell1.addElement(new Chunk(image, 0, 0, true).setAnchor("http://www.google.com"));
            cell2.addElement(new Chunk(image, 0, 0, true).setAnchor("https://voterportal.eci.gov.in"));
            cell3.addElement(new Chunk(image, 0, 0, true).setAnchor("https://voterportal.eci.gov.in"));
            cell4.addElement(new Chunk(image, 0, 0, true).setAnchor("https://voterportal.eci.gov.in"));
            cell5.addElement(new Chunk(image, 0, 0, true).setAnchor("https://voterportal.eci.gov.in"));
            cell6.addElement(new Chunk(image, 0, 0, true).setAnchor("https://voterportal.eci.gov.in"));
            cell7.addElement(new Chunk(image, 0, 0, true).setAnchor("https://voterportal.eci.gov.in"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);


            paragraph.add(table);

            Paragraph p = new Paragraph(" ");
            int position = (int) (document.getPageSize().getHeight() - 125);             //Document best fit size 140 for scaling of image=20;
            paragraph.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(position);

            document.add(p);


            document.add(paragraph);


            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(back);
            // Add a background canvas

            document.close();

            if (!document.isOpen()) {
                showToaster("Done: " + directory);
            }

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

    private PdfPTable getPdfPTableProperties(PdfWriter writer, Document document) {


        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(90);
        table.setTotalWidth(document.getPageSize().getWidth());
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        return table;
    }

    private Image getImageFromDrawable() throws BadElementException, IOException {
        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.growfast, null);
        d.setAlpha(0);
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

    public void saveAsPDF(View view) {
        createPDFDocument();
    }
}
