package com.example.examen_01_moviles.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.examen_01_moviles.R;
import com.example.examen_01_moviles.models.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddProductFragment extends Fragment {
    View v;
    EditText nombre, precio, proveedor, categoria;
    private Button add, back;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public AddProductFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        initFirebase();
        v = inflater.inflate(R.layout.fragment_add_products, container, false);
        nombre = v.findViewById(R.id.add_nom_prod);
        precio = v.findViewById(R.id.add_precio_prod);
        proveedor = v.findViewById(R.id.add_prov_prod);
        categoria = v.findViewById(R.id.add_categ_prod);
        add = v.findViewById(R.id.btn_add_prod);
        back = v.findViewById(R.id.btn_return1);
        add.setOnClickListener(view -> {
            String nomProd = nombre.getText().toString().trim();
            String precioProd = precio.getText().toString().trim();
            String provProd = proveedor.getText().toString().trim();
            String categProd = categoria.getText().toString().trim();
            if(nomProd == null || nomProd.equals("")){
                nombre.setError("Campo Requerido");
            }else if(precioProd == null || precioProd.equals("")){
                precio.setError("Campo Requerido");
            }else if(provProd == null || provProd.equals("")){
                proveedor.setError("Campo Requerido");
            }else if(categProd == null || categProd.equals("")){
                categoria.setError("Campo Requerido");
            }else{
                Producto producto = new Producto();
                producto.setUid(UUID.randomUUID().toString());
                producto.setNombre(nomProd.trim());
                producto.setPrecio(Double.valueOf(precioProd.trim()));
                producto.setProveedor(provProd.trim());
                producto.setCategoria(categProd.trim());
                databaseReference.child("Producto").child(producto.getUid()).setValue(producto);
                Toast.makeText(getContext(), nomProd+" ha sido agregado!", Toast.LENGTH_SHORT).show();
                limpiarCajas();
            }
        });
        back.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment(), "LIST_FRAG").commit();
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiarCajas(){
        nombre.setText("");
        precio.setText("");
        proveedor.setText("");
        categoria.setText("");
    }

}
