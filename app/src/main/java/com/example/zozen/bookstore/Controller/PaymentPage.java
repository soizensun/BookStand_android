package com.example.zozen.bookstore.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.zozen.bookstore.R;

public class PaymentPage extends AppCompatActivity {
    private TextView showPriceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        getSupportActionBar().setTitle("book detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showPriceTV = findViewById(R.id.showPriceTV);

        String price = getIntent().getStringExtra("price");
        Log.d("++p++", price);
        showPriceTV.setText(price);

    }
}
