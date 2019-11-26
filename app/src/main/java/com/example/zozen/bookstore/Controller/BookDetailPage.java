package com.example.zozen.bookstore.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zozen.bookstore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class BookDetailPage extends AppCompatActivity {
    private ImageView showPicIMV;
    private TextView nameTVsd, authorTVsd, detailTVsd;
    private Button buyBTN, addTobasketBTN;
    private FirebaseFirestore db;
    private ProgressBar waitAddToBasketPB;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_page);
        getSupportActionBar().setTitle("book detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showPicIMV = findViewById(R.id.showPicIMV);
        nameTVsd = findViewById(R.id.nameTVsd);
        authorTVsd = findViewById(R.id.authorTVsd);
        detailTVsd = findViewById(R.id.detailTVsd);
        buyBTN = findViewById(R.id.buyBTN);
        addTobasketBTN = findViewById(R.id.addTobasketBTN);
        waitAddToBasketPB = findViewById(R.id.waitAddToBasketPB);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        waitAddToBasketPB.setVisibility(View.INVISIBLE);

        final String name = getIntent().getStringExtra("name");
        final String price = getIntent().getStringExtra("price");
        final String detail = getIntent().getStringExtra("detail");
        final String url = getIntent().getStringExtra("url");
        final String author = getIntent().getStringExtra("author");
        final String id = getIntent().getStringExtra("id");

        nameTVsd.setText(name);
        detailTVsd.setText(detail);
        authorTVsd.setText(author);
        buyBTN.setText("b u y  " + price);
        Picasso.get().load(url).into(showPicIMV);

        buyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailPage.this, PaymentPage.class);
                intent.putExtra("price", price);
                db.collection("myBook").document(id).delete();
                startActivity(intent);
            }
        });

        addTobasketBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] onlyPrice = price.split(" ");
                String[] user = firebaseUser.getEmail().split("@");
                waitAddToBasketPB.setVisibility(View.VISIBLE);
                Map tempMap = new HashMap<String, Object>();
                tempMap.put("user", user[0]);
                tempMap.put("id", id);
//                tempMap.put("name", name);
//                tempMap.put("price", onlyPrice[0]);
//                tempMap.put("detail", detail);
//                tempMap.put("url", url);

                db.collection("myBasket")
                        .add(tempMap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                waitAddToBasketPB.setVisibility(View.GONE);
                                startActivity(new Intent(BookDetailPage.this, MainActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitAddToBasketPB.setVisibility(View.GONE);
                                Log.w("fail", "Error adding document", e);
                            }
                        });
            }
        });




    }
}
