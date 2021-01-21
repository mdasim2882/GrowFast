package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Holders.RefImagesCardItemsViewHolder;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.WebsiteActivity;
import com.example.growfast.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReferenceImagesCardRecyclerViewAdapter extends RecyclerView.Adapter<RefImagesCardItemsViewHolder> {

    public static final int GALLERY_CODE = 1001;

    public final String TAG = getClass().getSimpleName();
    ImageView profilePic;
    StorageReference storageReference;
    private Uri imageUri;


    Context context;
    private List<String> productList;
    Activity activity;
    public boolean paymentDone = false;
    public RefImagesCardItemsViewHolder abc;
    Stack<Integer> pos;
    List<Integer> locations;

    public void setAbc(RefImagesCardItemsViewHolder abc) {
        this.abc = abc;
    }

    public ReferenceImagesCardRecyclerViewAdapter(Context context, List<String> uploadImages) {
        this.productList = uploadImages;
        this.context = context;
        activity = (Activity) context;
        pos = new Stack<>();
        locations = new ArrayList<>();

    }

    @NonNull
    @Override
    public RefImagesCardItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_addphotocard, parent, false);

        return new RefImagesCardItemsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RefImagesCardItemsViewHolder holder, int position) {

        holder.imgCard.setOnClickListener(v -> {
            context = v.getContext();
//            if(!pos.contains(position)){
//                pos.add(position);
//                }
            pos.push(position);

            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(openGallery, GALLERY_CODE);
            setAbc(holder);


        });


    }

    public RefImagesCardItemsViewHolder getAbc() {
        return abc;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MyAdapter", "onActivityResult");
        if (requestCode == GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data.getData();
                //  profilePic.setImageURI(imageUri);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Log.e(TAG, "onActivityResult: UID after authentication" + user.getUid());
                    try {
                        RefImagesCardItemsViewHolder refImagesCardItemsViewHolder = getAbc();
                        refImagesCardItemsViewHolder.imgCard.setImageURI(imageUri);
                        Log.d(TAG, "onActivityResult() called with: URI CODE = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + imageUri.toString() + "]");
                        if (!locations.contains(pos.peek())) {
                            locations.add(pos.peek());

//                            if (!WebsiteActivity.imagesSelected.contains(imageUri))
                            WebsiteActivity.imagesSelected.add(imageUri);
                        } else {
                            WebsiteActivity.imagesSelected.set(pos.peek(), imageUri);
                        }


//                        updateUI(user, imageUri);
                    } catch (Exception e) {
                        showToaster("Size is too big to upload");
                    }
//                    updateUI(user, imageUri);
                }
            }
        }
    }


    private void showToaster(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
