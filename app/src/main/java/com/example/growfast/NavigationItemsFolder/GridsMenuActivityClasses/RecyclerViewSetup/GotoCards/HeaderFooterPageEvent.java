package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.example.growfast.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    Activity activity;

    public HeaderFooterPageEvent(Activity activity) {
        this.activity = activity;
    }

    public void onStartPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), rect.getLeft(), rect.getTop(), 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), rect.getRight(), rect.getTop(), 0);
    }

    public void onEndPage(PdfWriter writer, Document document) {
//        Rectangle rect = writer.getBoxSize("art");
//        Image image = null;
//        try {
//            image = getImageFromDrawable();
//        } catch (BadElementException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        image.scalePercent(20,20);
//        Paragraph paragraph = new Paragraph();
//        paragraph.add(new Chunk(image, 0, 0, true).setAnchor("http://www.google.com"));
//        paragraph.add(new Chunk(image, 0, 0, true).setAnchor("https://voterportal.eci.gov.in"));
//        paragraph.add(new Chunk(image, 0, 0, true).setAnchor("https://aktu.ac.in/"));
//        paragraph.setSpacingBefore(rect.getBottom());
//
//        try {
//
//            document.add(paragraph);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        ColumnText ct = new ColumnText(writer.getDirectContent());
//        ct.setAlignment(Element.ALIGN_CENTER);
//        ct.setSimpleColumn(new Rectangle(0, 0, 550, 800));
//        ct.addElement(paragraph);
//        try {
//            ct.go();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
        Rectangle rect = writer.getBoxSize("art");

        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Bottom Left"), rect.getLeft(), rect.getBottom(), 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), rect.getRight(), rect.getBottom(), 0);
    }

    private Image getImageFromDrawable() throws BadElementException, IOException {
        Drawable d = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.growfast, null);
        Bitmap drawableBitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream drawableStream = new ByteArrayOutputStream();
        drawableBitmap.compress(Bitmap.CompressFormat.PNG, 100, drawableStream);
        byte[] bitmapData = drawableStream.toByteArray();
        return Image.getInstance(bitmapData);
    }
}