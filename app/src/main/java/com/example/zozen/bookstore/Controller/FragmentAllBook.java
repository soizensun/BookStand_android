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

import com.example.zozen.bookstore.Adapter.AdapterAllbook;
import com.example.zozen.bookstore.Modal.Book;
import com.example.zozen.bookstore.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllBook extends Fragment{
    RecyclerView recyclerView;
    private FirebaseFirestore db;
    private AdapterAllbook adapterAllbook;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_book, container, false);
        getActivity().setTitle("All book");
        final List<Book> tmpBookList = new ArrayList();
        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.allBookLV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db.collection("myBook").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("fireLig", "all book firestore fail" + e.getMessage());
                }
                for (DocumentSnapshot doc : documentSnapshots){
                    String name = doc.getString("name");
                    Double price = doc.getDouble("price");
                    String detail = doc.getString("detail");
                    String url = doc.getString("url");
                    String author = doc.getString("author");
                    String id = doc.getId();
                    Log.d("---id---", "onEvent: " + id);

                    tmpBookList.add(new Book(url, name, price, detail, author, id));
                    adapterAllbook = new AdapterAllbook(getActivity(), tmpBookList);
                    recyclerView.setAdapter(adapterAllbook);
                }
            }
        });

        return view;
    }
}
