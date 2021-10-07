package com.example.examen_01_moviles.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.examen_01_moviles.R;
import com.example.examen_01_moviles.adapters.ProductoAdapter;
import com.example.examen_01_moviles.models.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    View v;
    private List<Producto> elements;
    private RecyclerView recView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Producto prodSelected;
    private ImageButton addProd;

    public ProductFragment(){ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_products, container, false);
        recView = v.findViewById(R.id.recview);
        addProd = v.findViewById(R.id.imageButton);
        addProd.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddProductFragment()).commit();
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFirebase();
        listarProductos();
    }

    private void listarProductos() {
        elements = new ArrayList<>();
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Producto producto = dataSnapshot.getValue(Producto.class);
                    elements.add(producto);
                }
                if(getActivity()!=null){
                    ProductoAdapter productoAdapter = new ProductoAdapter(elements, getContext());
                    recView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recView.setAdapter(productoAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


}
