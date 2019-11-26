package com.example.zozen.bookstore.Controller;

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

import com.example.zozen.bookstore.Adapter.AdapterMyBook;
import com.example.zozen.bookstore.Modal.Book;
import com.example.zozen.bookstore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyBook extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private AdapterMyBook adapterMyBook;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String[] disPlayName;

    final List<Book> tmpBookList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("My book");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        disPlayName = firebaseUser.getEmail().split("@");

        View view = inflater.inflate(R.layout.fragment_my_book, container, false);
//        final List<Book> tmpBookList = new ArrayList();

        recyclerView = view.findViewById(R.id.myBookLV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        getData();
        return view;
    }

    private void getData() {
        db.collection("myBook").whereEqualTo("user", disPlayName[0]).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("fireLig", "all book firestore fail" + e.getMessage());
                }
                tmpBookList.clear();
                for (DocumentSnapshot doc : documentSnapshots){
                    String name = doc.getString("name");
                    Double price = doc.getDouble("price");
                    String detail = doc.getString("detail");
                    String url = doc.getString("url");
                    String author = doc.getString("author");
                    String id = doc.getId();

                    tmpBookList.add(new Book(url, name, price, detail, author, id));
                    adapterMyBook = new AdapterMyBook(getActivity(), tmpBookList);
                    recyclerView.setAdapter(adapterMyBook);
                }
            }
        });
    }
}
