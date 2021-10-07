package com.example.examen_01_moviles.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen_01_moviles.R;
import com.example.examen_01_moviles.models.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.UUID;

public class UpdateProductFragment extends Fragment {

    View v;
    private EditText nombre, precio, proveedor, categoria;
    private Button upd, back;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uuidProd;

    public UpdateProductFragment(){ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFirebase();
        v = inflater.inflate(R.layout.fragment_update_products, container, false);
        Bundle args = getArguments();
        uuidProd = args.getString("PRODUCTO_UUID");
        nombre = v.findViewById(R.id.upd_nom_prod);
        precio = v.findViewById(R.id.upd_precio_prod);
        proveedor = v.findViewById(R.id.upd_prov_prod);
        categoria = v.findViewById(R.id.upd_categ_prod);
        upd = v.findViewById(R.id.btn_upd_prod);
        back = v.findViewById(R.id.btn_return2);
        overwriteValues();
        upd.setOnClickListener(view -> {
            String nomProd = nombre.getText().toString().trim();
            String precioProd = precio.getText().toString().trim();
            String provProd = proveedor.getText().toString().trim();
            String categProd = categoria.getText().toString().trim();
            if(nomProd == null || nomProd.equals("")){
                nombre.setError("Campo Requerido");
            }else if(precioProd == null || precioProd.equals("")){
                nombre.setError("Campo Requerido");
            }else if(provProd == null || provProd.equals("")){
                proveedor.setError("Campo Requerido");
            }else if(categProd == null || categProd.equals("")){
                categoria.setError("Campo Requerido");
            }else{
                Producto producto = new Producto();
                producto.setUid(uuidProd);
                producto.setNombre(nomProd.trim());
                producto.setPrecio(Double.valueOf(precioProd.trim()));
                producto.setProveedor(provProd.trim());
                producto.setCategoria(categProd.trim());
                databaseReference.child("Producto").child(producto.getUid()).setValue(producto);
                Toast.makeText(getContext(), "Registro Actualizado!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment()).commit();
            }
        });
        back.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment(), "LIST_FRAG").commit();
        });
        return v;
    }

    private void overwriteValues(){
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Producto producto = dataSnapshot.getValue(Producto.class);
                    if (producto.getUid().equals(uuidProd)){
                        nombre.setText(producto.getNombre());
                        precio.setText(String.valueOf(producto.getPrecio()));
                        proveedor.setText(producto.getProveedor());
                        categoria.setText(producto.getCategoria());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference();
    }
    private void limpiarCajas(){
        nombre.setText("");
        precio.setText("");
        proveedor.setText("");
        categoria.setText("");
    }
}
