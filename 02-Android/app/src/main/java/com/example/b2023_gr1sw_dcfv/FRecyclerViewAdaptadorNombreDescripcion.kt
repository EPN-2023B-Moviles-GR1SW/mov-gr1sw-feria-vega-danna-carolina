package com.example.b2023_gr1sw_dcfv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion(
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<
        FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder
        >() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(
        view
    ){
        val nombreTextView: TextView
        val descripcionTextView: TextView
        val likesTextView: TextView
        val accionButton: Button
        var numeroLikes = 0

        init {
            nombreTextView =view.findViewById(R.id.tv_nombre)
            descripcionTextView =view.findViewById(R.id.tv_descripcion)
            likesTextView =view.findViewById(R.id.tv_likes)
            accionButton =view.findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener { añadirLike() }
        }

        fun añadirLike(){
            numeroLikes = numeroLikes+1
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }

    }

    override fun getItemCount(): Int {
        return  this.lista.size
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recyler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder,
        position: Int
    ) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.descripcionTextView.text = entrenadorActual.descripcion
        holder.likesTextView.text = "0"
        holder.accionButton.text = "ID:${entrenadorActual.id}" +
                "Nombre:${entrenadorActual.nombre}"
    }

}