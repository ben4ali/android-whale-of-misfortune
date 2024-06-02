package com.example.projetpendu

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demorecylcerview.DatabaseHelper
import com.example.demorecylcerview.RecyclerAdapter

class Historique : AppCompatActivity(), OnClickListener {
    lateinit var partieList: ArrayList<Partie>
    lateinit var recycler : RecyclerView
    lateinit var adapter : RecyclerAdapter
    lateinit var retourButton: ConstraintLayout
    lateinit var partieDAO: PartieDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historique)

        recycler = findViewById(R.id.recycler)
        retourButton = findViewById(R.id.retourBtn)
        retourButton.setOnClickListener(this)
        partieList = ArrayList()

        val helper = DatabaseHelper(this)
        partieDAO = PartieDAO(helper)
        //partieDAO.deleteAll()
        setInfoAdapter()
    }
    fun setInfoAdapter(){
        adapter = RecyclerAdapter(partieList)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter
    }
    override fun onResume() {
        super.onResume()
        partieList.clear()
        partieList.addAll(partieDAO.findAll())
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.retourBtn -> {
                finish()
            }
        }
    }
}