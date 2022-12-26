package com.example.firebase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase1.adapter.MyAdapter
import com.example.firebase1.data.DataList
import com.example.firebase1.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myadapter: MyAdapter
    private var list = ArrayList<DataList>()
    private val lm = LinearLayoutManager(this)
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("MyUsers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.floating.setOnClickListener {
            val intent = Intent(this, FloatingActivity::class.java)
            startActivity(intent)
        }

        retriveDataFromDataBase()

        dragdrop()

    }


    private fun retriveDataFromDataBase() {

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (echUser in snapshot.children) {
                    val user = echUser.getValue(DataList::class.java)
                    if (user != null) {
                        list.add(user)
                    }
                }
                myadapter = MyAdapter(this@MainActivity, list)
                binding.recyler.apply {
                    layoutManager = lm
                    adapter = myadapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun dragdrop() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = myadapter.getUserId(viewHolder.adapterPosition)

                reference.child(id).removeValue()


            }

        }).attachToRecyclerView(binding.recyler)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menudelete) {
            showDialogMessage()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Dialog")
        dialogMessage.setMessage("Do you want to delete all ... ")
        dialogMessage.setPositiveButton("Yes") { _, _ ->
            reference.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    myadapter.notifyDataSetChanged()
                }
            }
        }

        dialogMessage.setNegativeButton("NO") { dialoginterface, _ ->
            dialoginterface.cancel()
        }
        dialogMessage.create().show()
    }


}