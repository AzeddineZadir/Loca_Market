package com.example.loca_market.ui.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ViewHolder>{
    private Context context;
    private List<Category> categories;
    private List<Category> categoriesSelected;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;

    public HobbiesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        mStore =FirebaseFirestore.getInstance();
        mAuth =FirebaseAuth.getInstance();
        categoriesSelected=new ArrayList<>();
       getCategoriesSelected();
    }

    private void getCategoriesSelected() {
        mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
                        for(DocumentChange doc :task.getResult().getDocumentChanges()){
                            Category c = doc.getDocument().toObject(Category.class);
                            categoriesSelected.add(c);
                        }
                    }

                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_single_hobbie_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item.setText(categories.get(position).getName());
        if(categoriesSelected!=null){
            for(Category c : categoriesSelected){
                if (c.getName().trim().toLowerCase().equals(categories.get(position).getName().trim().toLowerCase()))
                    holder.item.setChecked(true);
            }

        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.item.isChecked()){
                    mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                            .collection("categories").add(categories.get(position)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                            }
                        });
                    }else{
                    mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                            .collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult()!=null){
                                    for(DocumentChange doc :task.getResult().getDocumentChanges()){
                                        String documentId = doc.getDocument().getId();
                                        Category c = doc.getDocument().toObject(Category.class);
                                        if(c.getName().toLowerCase().trim().equals(categories.get(position).getName().toLowerCase().trim()))
                                        mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                                                .collection("categories").document(documentId).delete();
                                    }
                                }

                            }
                        }
                    });
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.checkbox_hobbie);
        }
    }
}
