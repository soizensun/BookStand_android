package com.example.zozen.bookstore.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zozen.bookstore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity {
    private EditText bookNameEDT, priceBookEDT, bookDetailEDT, bookAuthorEDT;
    private Button uploadBookBTN;
    private ImageView picBookIMV;
    private ProgressBar addBookPG;
    private Uri downloadUrl;

    private String bookName;
    private String userName;

    private Uri uri;

    private StorageReference storageReference;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setTitle("Add book page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        downloadUrl = null;
        userName = getIntent().getStringExtra("userName");
        Log.d("NNN", userName);

        bookNameEDT = findViewById(R.id.bookNameEDT);
        priceBookEDT = findViewById(R.id.priceBookEDT);
        bookAuthorEDT = findViewById(R.id.bookAuthorEDT);
        bookDetailEDT = findViewById(R.id.bookDetailEDT);
        uploadBookBTN = findViewById(R.id.uploadBookBTN);
        picBookIMV = findViewById(R.id.picBookIMV);
        addBookPG = findViewById(R.id.addBookPG);

        uploadBookBTN.setVisibility(View.INVISIBLE);
        addBookPG.setVisibility(View.INVISIBLE);

        picBookIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookName = bookNameEDT.getText().toString().trim();
                String bookPrice = priceBookEDT.getText().toString().trim();
                String bookDetail = bookDetailEDT.getText().toString().trim();
                String bookAuthor = bookAuthorEDT.getText().toString().trim();
                if(bookName.isEmpty() || bookPrice.isEmpty() || bookDetail.isEmpty() || downloadUrl == null){
                    if(bookName.isEmpty()) bookNameEDT.setError("please enter your book's name");
                    if(bookPrice.isEmpty()) priceBookEDT.setError("please set your book's price");
                    if(bookDetail.isEmpty()) bookDetailEDT.setError("please enter your book's detail");
                    if(bookAuthor.isEmpty()) bookAuthorEDT.setError("please enter your book's author");
                }
                else{

                }
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        uploadBookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookName = bookNameEDT.getText().toString().trim();
                String bookPrice = priceBookEDT.getText().toString().trim();
                String bookAuthor = bookAuthorEDT.getText().toString().trim();
                String bookDetail = bookDetailEDT.getText().toString().trim();
                if(bookName.isEmpty() || bookPrice.isEmpty() || bookDetail.isEmpty() || downloadUrl == null){
                    if(bookName.isEmpty()) bookNameEDT.setError("please enter your book's name");
                    if(bookPrice.isEmpty()) priceBookEDT.setError("please set your book's price");
                    if(bookAuthor.isEmpty()) bookAuthorEDT.setError("please set you book's author");
                    if(bookDetail.isEmpty()) bookDetailEDT.setError("please enter your book's detail");
                    if(downloadUrl == null) Toast.makeText(AddBook.this, "please choose your picture", Toast.LENGTH_SHORT).show();
                }
                else {
                    addBookPG.setVisibility(View.VISIBLE);
                    Double Dprice = Double.parseDouble(bookPrice);
                    Map tempMap = new HashMap<String, Object>();

                    tempMap.put("url", downloadUrl.toString());
                    tempMap.put("user", userName);
                    tempMap.put("name", bookName);
                    tempMap.put("price", Dprice);
                    tempMap.put("detail", bookDetail);
                    tempMap.put("author", bookAuthor);

                    db.collection("myBook")
                            .add(tempMap)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    addBookPG.setVisibility(View.GONE);
                                    startActivity(new Intent(AddBook.this, MainActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    addBookPG.setVisibility(View.GONE);
                                    Log.w("fail", "Error adding document", e);
                                }
                            });
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0){
            uri = data.getData();
            addBookPG.setVisibility(View.VISIBLE);
            if(uri != null){
                StorageReference riversRef = storageReference.child("images/" + bookName + ".jpg");
                riversRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                addBookPG.setVisibility(View.INVISIBLE);
                                uploadBookBTN.setVisibility(View.VISIBLE);
                                downloadUrl = taskSnapshot.getDownloadUrl();
                                Bitmap b = null;
                                try {
                                    b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                picBookIMV.setImageBitmap(b);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                            }
                        });
            }
            else{
                Toast.makeText(AddBook.this, "please choose your book's picture", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
