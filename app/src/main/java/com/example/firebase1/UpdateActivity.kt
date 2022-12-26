package com.example.firebase1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase1.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference : DatabaseReference = database.reference.child("MyUsers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Update User"
        getsetData()


        binding.btnclick.setOnClickListener {
            update()
        }








    }

    private fun getsetData() {
        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")
        val email = intent.getStringExtra("email")

        binding.edName.setText(name)
        binding.edAge.setText(age)
        binding.edEmail.setText(email)


    }


    private fun update() {
        val updateName = binding.edName.text.toString()
        val updateAge = binding.edAge.text.toString()
        val updateEmail = binding.edEmail.text.toString()

        val userId = intent.getStringExtra("id").toString()

        val userMap = mutableMapOf<String, Any>()

        userMap["id"] = userId
        userMap["name"] = updateName
        userMap["aga"] = updateAge
        userMap["email"] = updateEmail

        reference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->

        }
    }


}