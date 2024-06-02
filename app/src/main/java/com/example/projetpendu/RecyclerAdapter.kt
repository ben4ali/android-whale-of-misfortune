package com.example.demorecylcerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetpendu.Partie
import com.example.projetpendu.R

class RecyclerAdapter(var partieList : ArrayList<Partie>):
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){
    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var statusImage : ImageView = itemView.findViewById(R.id.statusOutput)
        var tempsText : TextView = itemView.findViewById(R.id.tempsOutput)
        var motText : TextView = itemView.findViewById(R.id.motDictio)
        var errorText : TextView = itemView.findViewById(R.id.langueDictio)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_historique,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return partieList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var status : String = partieList[position].status
        var temps : Int = partieList[position].temps
        var mot : String = partieList[position].mot
        var nbErreur : Int = partieList[position].nbErreur

        if (status == "win"){
            holder.statusImage.setImageResource(R.drawable.win)
        }else{
            holder.statusImage.setImageResource(R.drawable.erreur)
        }
        holder.tempsText.text = holder.tempsText.context.resources.getString(R.string.temps) + " " + temps + "s"
        holder.motText.text = holder.motText.context.resources.getString(R.string.mot) + " " + mot
        holder.errorText.text = holder.errorText.context.resources.getString(R.string.nbErreur) + " " + nbErreur
    }

}