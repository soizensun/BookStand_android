package com.example.zozen.bookstore.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zozen.bookstore.Adapter.AdapterMyBasket;
import com.example.zozen.bookstore.Modal.Book;
import com.example.zozen.bookstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FragmentMyBasket extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private AdapterMyBasket adapterMyBasket;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Button buyBTN;
    private Double allPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_basket, container, false);
        getActivity().setTitle("My basket");

        allPrice = 0.0;
        buyBTN = view.findViewById(R.id.buyBTN);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        final String[] disPlayName = firebaseUser.getEmail().split("@");

        final List<Book> tmpBookList = new ArrayList();


        buyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(view.getContext(), "buy", Toast.LENGTH_SHORT).show();
                db.collection("myBasket").whereEqualTo("user", disPlayName[0]).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d("fireLig", "all book firestore fail" + e.getMessage());
                        }
                        for (final DocumentSnapshot doc : documentSnapshots){
                            String id = doc.getString("id");
                            Log.i("iddd", "id : " + id);
                            db.collection("myBook").document(id).delete();

                            Intent intent = new Intent(view.getContext(), PaymentPage.class);
                            Log.i("--pp--", String.valueOf(allPrice));
                            intent.putExtra("price", allPrice + "");
                            startActivity(intent);
                        }
                    }
                });
            }
        });


        recyclerView = view.findViewById(R.id.myBasketLV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db.collection("myBasket").whereEqualTo("user", disPlayName[0]).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(final QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("fireLig", "all book firestore fail" + e.getMessage());
                }
                for (final DocumentSnapshot doc : documentSnapshots){
                    String id = doc.getString("id");

                    db.collection("myBook").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("333", "DocumentSnapshot data: " + document.getData());
                                    String name = document.getString("name");
                                    Double price = document.getDouble("price");
                                    String detail = document.getString("detail");
                                    String url = document.getString("url");
                                    String author = document.getString("author");
                                    String id = document.getId();

                                    allPrice += price;
                                    tmpBookList.add(new Book(url, name, price, detail, author, id));
                                    adapterMyBasket = new AdapterMyBasket(getActivity(), tmpBookList);
                                    recyclerView.setAdapter(adapterMyBasket);
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            }
        });


        return view;
    }
}
