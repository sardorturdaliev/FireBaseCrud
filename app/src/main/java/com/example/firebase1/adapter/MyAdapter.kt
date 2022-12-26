package com.example.firebase1.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase1.FloatingActivity
import com.example.firebase1.UpdateActivity
import com.example.firebase1.data.DataList
import com.example.firebase1.databinding.ItemCardBinding

class MyAdapter(var context: Context,var list : ArrayList<DataList>) : RecyclerView.Adapter<MyAdapter.Holder>() {

    inner class Holder(var binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataList) = with(binding) {
            tvName.text = data.name
            tvAge.text = data.aga
            tvEmail.text = data.email

            itemView.setOnClickListener {
                val intent = Intent(context,UpdateActivity ::class.java)
                intent.putExtra("id",list[adapterPosition].id)
                intent.putExtra("name",list[adapterPosition].name)
                intent.putExtra("age",list[adapterPosition].aga)
                intent.putExtra("email",list[adapterPosition].email)

              context.startActivity(intent)


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun getUserId(position : Int) : String{
        return  list[position].id
    }


}