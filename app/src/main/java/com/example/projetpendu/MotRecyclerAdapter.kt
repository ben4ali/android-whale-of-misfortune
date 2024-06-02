package com.example.projetpendu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demorecylcerview.RecyclerAdapter

class MotRecyclerAdapter (var motList: List<Mot>):
    RecyclerView.Adapter<MotRecyclerAdapter.MyViewHolder>(){
        var onItemClick : ((Mot) -> Unit)? = null
        class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
            var motText : TextView = itemView.findViewById(R.id.motDictio)
            var langueText : TextView = itemView.findViewById(R.id.langueDictio)
            var difficulteText : TextView = itemView.findViewById(R.id.difficultyDictio)
            var deleteButton : ImageView = itemView.findViewById(R.id.deleteBtn)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_mot,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var mot : String = motList[position].mot
        var langue : String = motList[position].langue
        var difficulte : String = motList[position].difficulte

        holder.motText.text = mot
        holder.langueText.text = langue
        if (difficulte == "FACILE"){
            holder.difficulteText.text = holder.difficulteText.context.resources.getString(R.string.facile)
        } else if (difficulte == "NORMAL"){
            holder.difficulteText.text = holder.difficulteText.context.resources.getString(R.string.normal)
        } else {
            holder.difficulteText.text = holder.difficulteText.context.resources.getString(R.string.dur)
        }
        holder.deleteButton.setOnClickListener {
            onItemClick?.invoke(motList[position])
        }
    }
    override fun getItemCount(): Int {
        return motList.size
    }
}