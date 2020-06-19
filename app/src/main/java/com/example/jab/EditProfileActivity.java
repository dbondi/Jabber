package com.example.jab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import controllers.EditProfileController;
import custom_class.Place;
import custom_class.UserProfile;
import models.EditProfileModel;

public class EditProfileActivity extends AppCompatActivity {

    private EditProfileController controller;

    private FirebaseAuth auth;
    private Context context;

    private LinearLayout doneBtn;
    private FirebaseFirestore db;

    private Place place;

    private FirebaseStorage storage;

    private ArrayList<Place> localUniversityPlaces = new ArrayList<>();
    private ArrayList<Place> localCityPlaces = new ArrayList<>();

    private static Context instance;

    private UserProfile user;

    private int REQUEST_CODE = 100;
    private int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap image_bitmap_1;
    private Bitmap image_bitmap_2;
    private Bitmap image_bitmap_3;
    private Bitmap image_bitmap_4;
    private Bitmap image_bitmap_5;
    private Bitmap image_bitmap_6;
    private Bitmap image_bitmap_7;
    private Bitmap image_bitmap_8;
    private Bitmap image_bitmap_9;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;

    private EditText aboutMe;

    private int clickedImage = 0;
    private int highestImage = 0;

    String messageOption = "";

    private EditProfileModel model;

    ArrayList<Boolean> boolImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        controller = new EditProfileController(auth, this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("User");
        localUniversityPlaces = bundle.getParcelableArrayList("LocalUniversityPlaces");
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces");
        place = bundle.getParcelable("Place");

        db = FirebaseFirestore.getInstance();
        model = new EditProfileModel(auth, db);
        storage = FirebaseStorage.getInstance();

        image1 = findViewById(R.id.add_photo_1);
        image2 = findViewById(R.id.add_photo_2);
        image3 = findViewById(R.id.add_photo_3);
        image4 = findViewById(R.id.add_photo_4);
        image5 = findViewById(R.id.add_photo_5);
        image6 = findViewById(R.id.add_photo_6);
        image7 = findViewById(R.id.add_photo_7);
        image8 = findViewById(R.id.add_photo_8);
        image9 = findViewById(R.id.add_photo_9);
        context = getContext();

        System.out.println("BoolPcs");

        System.out.println(user.getBoolPictures());

        boolImages = user.getBoolPictures();
        System.out.println(boolImages);
        int i = 0;
        highestImage = 0;
        for(Boolean boolImage: boolImages){
            if (boolImage){
                StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (i + 1));
                getImage(i+1);
                highestImage++;
            }
            i++;
        }



        instance = this;

        doneBtn = findViewById(R.id.doneBtn);

        image1 = findViewById(R.id.add_photo_1);
        image2 = findViewById(R.id.add_photo_2);
        image3 = findViewById(R.id.add_photo_3);
        image4 = findViewById(R.id.add_photo_4);
        image5 = findViewById(R.id.add_photo_5);
        image6 = findViewById(R.id.add_photo_6);
        image7 = findViewById(R.id.add_photo_7);
        image8 = findViewById(R.id.add_photo_8);
        image9 = findViewById(R.id.add_photo_9);

        aboutMe = findViewById(R.id.add_bio);

        aboutMe.setText(user.getAbout());


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    model.saveInfo(aboutMe.getText().toString(),user);
                    controller.doneBtn(intent,localUniversityPlaces,localCityPlaces,user,place);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 1;
                openGalleryForImage();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 2;
                openGalleryForImage();
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 3;
                openGalleryForImage();
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 4;
                openGalleryForImage();
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 5;
                openGalleryForImage();
            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 6;
                openGalleryForImage();
            }
        });
        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 7;
                openGalleryForImage();
            }
        });
        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 8;
                openGalleryForImage();
            }
        });
        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickedImage = 9;
                openGalleryForImage();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void openGalleryForImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            Uri selectedPhotoUri = data.getData();
            try {
                    if(Build.VERSION.SDK_INT < 28) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(),
                                selectedPhotoUri
                        );
                        getImageLoad(clickedImage,bitmap);

                        messageOption = "Image";


                    } else {
                        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), selectedPhotoUri);
                        Bitmap bitmap = ImageDecoder.decodeBitmap(source);

                        getImageLoad(clickedImage,bitmap);


                        messageOption = "Image";

                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            messageOption = "Image";

            getImageLoad(clickedImage,bitmap);

        }
    }

    private int getImageBitmap(int clickedImage) {
        return 0;
    }

    private void getImage(int clickedImage) {

        if (clickedImage == 1){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image1);
        }
        else if (clickedImage == 2){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image2);
        }
        else if (clickedImage == 3){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image3);
        }
        else if (clickedImage == 4){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image4);
        }
        else if (clickedImage == 5){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image5);
        }
        else if (clickedImage == 6){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image6);
        }
        else if (clickedImage == 7){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image7);
        }
        else if (clickedImage == 8){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image8);
        }
        else if (clickedImage == 9){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));

            GlideApp.with(this)
                    .load(ref)
                    .into(image9);
        }
    }

    private void getImageLoad(int clickedImage,Bitmap bitmap) {
        System.out.println("STEM");
        System.out.println(clickedImage);
        System.out.println(highestImage);
        if (clickedImage > highestImage + 1){
            clickedImage = highestImage + 1;
        }
        if (clickedImage == 1){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(0,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image1);

        }
        else if (clickedImage == 2){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(1,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image2);
        }
        else if (clickedImage == 3){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(2,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image3);
        }
        else if (clickedImage == 4){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(3,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image4);
        }
        else if (clickedImage == 5){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(4,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image5);
        }
        else if (clickedImage == 6){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(5,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image6);
        }
        else if (clickedImage == 7){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(6,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image7);
        }
        else if (clickedImage == 8){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(7,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image8);
        }
        else if (clickedImage == 9){
            StorageReference ref = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/"+user.getUID()+"/PhotoReferences/pic" + (clickedImage));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            ref.putBytes(byteArray);
            boolImages.set(8,true);
            model.addBoolInfo(boolImages,user);
            GlideApp.with(this)
                    .load(ref)
                    .into(image9);
        }
        highestImage = highestImage + 1;

    }

    public static Context getContext(){
        return instance;
    }

    public interface FirestoreCallBack{
        void onCallback(UserProfile userEditInfo);
    }
}
