package com.petscare.org.vista.adaptadores.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petscare.org.R;
import com.petscare.org.modelo.objetos.Noticia;

import java.util.ArrayList;

public class AdaptadorFeed extends RecyclerView.Adapter<AdaptadorFeed.MyViewHolder> {

    ArrayList<Noticia> listanoticias;

     public AdaptadorFeed(ArrayList<Noticia> listanoticias){
     this.listanoticias = listanoticias;
     }



     //SIRVE PARA INFLAR LA VISTA CREADA EN EL DISEÑADOR, PORQUE SE TIENE QUE ENCONTRAR SU ID QUE EN ESTE CASO ES lista_adatptador"
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adaptador,parent,false
        ));
    }

    //2..SE OBTIENEN LOS PARAMETROS DE LA PLANTILLA/CLASE NOTICIAS, Y CON EL GET SE OBTIENEN Y PONEN EN EL TXT
    //2..EN GENERAL EL METODO SIRVE PARA MOSTRAR EN CADA ITEM SUS DATOS CORRESPONDIENTES OBTENIDOS DESDE EL ARRAYLIST
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         holder.txtNoticia.setText(listanoticias.get(position).getTitulo());
         holder.txtAutores.setText(listanoticias.get(position).getFuenteNoticia());
    }



    //RETORNA LA CANTIDAD DE LOS ITEMS QUE SE LE PASARON, SI HAY 10 NOMBRES, RETORNARA 10 ESPACIOS
    @Override
    public int getItemCount() {
        return listanoticias.size();
    }


    //1...OBTIENE LA VISTA XML, Y SE ""CONVIERTE"" EN JAVA PARA TENER MAYOR MANIPULACION DE SUS COMPONENTES
    static class MyViewHolder extends RecyclerView.ViewHolder{
        //COMPONENTE CREADO
        TextView txtNoticia;
        TextView txtAutores;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNoticia = itemView.findViewById(R.id.txtNoticias);
            txtAutores = itemView.findViewById(R.id.txtAutores);
        }
    }
}
