package com.example.zozen.bookstore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zozen.bookstore.Controller.BookDetailPage;
import com.example.zozen.bookstore.Modal.Book;
import com.example.zozen.bookstore.R;

import java.util.List;

public class AdapterAllbook extends RecyclerView.Adapter<AdapterAllbook.BookViewHolder>{
    private Context context;
    private List<Book> bookList;

    public AdapterAllbook(Context context, List<Book> bookList) {
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
    public void onBindViewHolder(@NonNull final BookViewHolder bookViewHolder, int i) {
        Book book = bookList.get(i);

        bookViewHolder.nameTVC.setText(book.getNameBook());
        bookViewHolder.priceTVC.setText(book.getPriceBook() + " B.");
        bookViewHolder.detailTVC.setText(book.getDetailBook());
        bookViewHolder.urlTVC.setText(book.getPicUrlBook());
        bookViewHolder.authorTVC.setText(book.getAuthorBook());
        bookViewHolder.idTVC.setText(book.getIdBook());
//        Picasso.get().load(book.getPicUrlBook()).into(bookViewHolder.bookIMV);

        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookDetailPage.class);
                intent.putExtra("name", bookViewHolder.nameTVC.getText());
                intent.putExtra("price", bookViewHolder.priceTVC.getText());
                intent.putExtra("detail", bookViewHolder.detailTVC.getText());
                intent.putExtra("url", bookViewHolder.urlTVC.getText());
                intent.putExtra("author", bookViewHolder.authorTVC.getText());
                intent.putExtra("id", bookViewHolder.idTVC.getText());
                context.startActivity(intent);
            }
        });

//        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Long click to delete : " + bookViewHolder.nameTVC.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        bookViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        TextView nameTVC, priceTVC, detailTVC, urlTVC, authorTVC, idTVC;
//        ImageView bookIMV;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTVC = itemView.findViewById(R.id.nameTVC);
            priceTVC = itemView.findViewById(R.id.priceTVC);
            detailTVC = itemView.findViewById(R.id.detailTVC);
            urlTVC = itemView.findViewById(R.id.urlTVC);
            authorTVC = itemView.findViewById(R.id.authorTVC);
            idTVC = itemView.findViewById(R.id.idTVC);
//            bookIMV = itemView.findViewById(R.id.bookIMV);
        }
    }
}
