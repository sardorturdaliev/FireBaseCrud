package com.example.firebase1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebase1.data.DataList
import com.example.firebase1.databinding.ActivityFloatingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FloatingActivity : AppCompatActivity() {
    lateinit var binding: ActivityFloatingBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloatingBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.btnclick.setOnClickListener {
            addUserToDB()
            binding.edName.text = null
            binding.edAge.text = null
            binding.edEmail.text = null
        }


    }

    private fun addUserToDB() {
        var name = binding.edName.text.toString()
        var age = binding.edAge.text.toString()
        var email = binding.edEmail.text.toString()


        val id: String = reference.push().key.toString()

        val user = DataList(id, name, age, email)

        reference.child(id).setValue(user).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

        }

    }


}