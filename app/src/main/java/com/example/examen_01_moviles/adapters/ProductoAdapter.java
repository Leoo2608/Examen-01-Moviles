package com.example.examen_01_moviles.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen_01_moviles.R;
import com.example.examen_01_moviles.fragments.AddProductFragment;
import com.example.examen_01_moviles.fragments.UpdateProductFragment;
import com.example.examen_01_moviles.models.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> data;
    private LayoutInflater inflater;
    private Context context;
    private Producto productoSelected;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public ProductoAdapter(List<Producto> itemList, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = itemList;
    }

    @Override
    public int getItemCount(){ return data.size(); }

    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        initFirebase();
        View view = inflater.inflate(R.layout.prod_element, null);
        ProductoAdapter.ViewHolder vHolder = new ProductoAdapter.ViewHolder(view);
        vHolder.item_prod.setOnClickListener(view1 -> {
            productoSelected = data.get(vHolder.getAdapterPosition());
            final CharSequence[] items = {
                    "Editar",
                    "Eliminar"
            };
            androidx.appcompat.app.AlertDialog.Builder menu = new AlertDialog.Builder(context);
            menu.setTitle("Opciones");
            menu.setItems(items, (dialogInterface, i) -> {
                switch (i){
                    case 0:
                        UpdateProductFragment updateProductFragment = new UpdateProductFragment();
                        Bundle args = new Bundle();
                        args.putString("PRODUCTO_UUID", productoSelected.getUid());
                        updateProductFragment.setArguments(args);
                        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.fragment_container, updateProductFragment).commit();
                        break;
                    case 1:
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setMessage("¿Desea eliminar este registro?");
                        alert.setPositiveButton("Si", (dialog, a)->{
                            Producto producto = new Producto();
                            producto.setUid(productoSelected.getUid());
                            databaseReference.child("Producto").child(producto.getUid()).removeValue();
                            Toast.makeText(context, "Regsitro Eliminado", Toast.LENGTH_SHORT).show();
                        });
                        alert.setNegativeButton("No", (dialog, a)-> dialog.dismiss());
                        alert.show();
                        break;
                }
            });
            menu.show();
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(final ProductoAdapter.ViewHolder holder, final int position){
        holder.bindData(data.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iconImage;
        TextView nombre, precio, proveedor, categoria;
        CardView item_prod;

        ViewHolder(View itemView) {
            super(itemView);
            item_prod = itemView.findViewById(R.id.cv_dc);
            iconImage = itemView.findViewById(R.id.iconImageView1);
            nombre = itemView.findViewById(R.id.txt_nombre);
            precio = itemView.findViewById(R.id.txt_precio);
            proveedor = itemView.findViewById(R.id.txt_prov);
            categoria = itemView.findViewById(R.id.txt_categ);
        }

        void bindData(final Producto item){
            nombre.setText(item.getNombre());
            precio.setText(String.valueOf(item.getPrecio()));
            proveedor.setText(item.getProveedor());
            categoria.setText(item.getCategoria());
        }
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference();
    }


}
