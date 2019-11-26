package com.example.zozen.bookstore.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zozen.bookstore.Modal.Book;
import com.example.zozen.bookstore.R;

import java.util.List;

public class AdapterMyBasket extends RecyclerView.Adapter<AdapterMyBasket.BookViewHolder>{
    private Context context;
    private List<Book> bookList;

    public AdapterMyBasket(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_list, null);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        Book book = bookList.get(i);

        bookViewHolder.nameTVC.setText(book.getNameBook());
        bookViewHolder.priceTVC.setText(book.getPriceBook() + " B.");
        bookViewHolder.detailTVC.setText(book.getDetailBook());
        bookViewHolder.urlTVC.setText(book.getPicUrlBook());
        bookViewHolder.authorTVC.setText(book.getAuthorBook());
//        Picasso.get().load(book.getPicUrlBook()).into(bookViewHolder.bookIMV);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        TextView nameTVC, priceTVC, detailTVC, urlTVC, authorTVC;
//        ImageView bookIMV;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTVC = itemView.findViewById(R.id.nameTVC);
            priceTVC = itemView.findViewById(R.id.priceTVC);
            detailTVC = itemView.findViewById(R.id.detailTVC);
            urlTVC = itemView.findViewById(R.id.urlTVC);
            authorTVC = itemView.findViewById(R.id.authorTVC);
//            bookIMV = itemView.findViewById(R.id.bookIMV);

        }
    }
}
