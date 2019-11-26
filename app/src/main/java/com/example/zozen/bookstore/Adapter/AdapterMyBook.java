package com.example.zozen.bookstore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zozen.bookstore.Modal.Book;
import com.example.zozen.bookstore.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdapterMyBook extends RecyclerView.Adapter<AdapterMyBook.BookViewHolder>{
    private Context context;
    private List<Book> bookList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdapterMyBook(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_list, null);
        return new AdapterMyBook.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookViewHolder bookViewHolder, int i) {
        Book book = bookList.get(i);

        bookViewHolder.nameTVC.setText(book.getNameBook());
        bookViewHolder.priceTVC.setText(book.getPriceBook() + " B.");
        bookViewHolder.detailTVC.setText(book.getDetailBook());
        bookViewHolder.idTVC.setText(book.getIdBook());
        bookViewHolder.authorTVC.setText(book.getAuthorBook());
//        Picasso.get().load(book.getPicUrlBook()) .into(bookViewHolder.bookIMV);

        bookViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String name = (String) bookViewHolder.nameTVC.getText();
                String id = (String) bookViewHolder.idTVC.getText();
                int position = bookViewHolder.getLayoutPosition();
                openIsDeleteDialog(name, id, bookViewHolder, position);
                return true;
            }
        });
    }

    private void openIsDeleteDialog(String name, final String id, final BookViewHolder ingredientViewHolder, final int position) {
        AlertDialog.Builder isDeleteDialog = new AlertDialog.Builder(this.context)
                .setTitle("Are you sure to delete?")
                .setMessage(name)
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        Log.d("id---", id);
//                        adapter_suggest_menu.onBindViewHolder();
                        db.collection("myBook").document(id).delete();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "click cancel", Toast.LENGTH_SHORT).show();
                    }
                });
        isDeleteDialog.show();
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        TextView nameTVC, priceTVC, detailTVC, idTVC, authorTVC;
//        ImageView bookIMV;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTVC = itemView.findViewById(R.id.nameTVC);
            priceTVC = itemView.findViewById(R.id.priceTVC);
            detailTVC = itemView.findViewById(R.id.detailTVC);
            idTVC = itemView.findViewById(R.id.idTVC);
            authorTVC = itemView.findViewById(R.id.authorTVC);
//            bookIMV = itemView.findViewById(R.id.bookIMV);
        }
    }
}
