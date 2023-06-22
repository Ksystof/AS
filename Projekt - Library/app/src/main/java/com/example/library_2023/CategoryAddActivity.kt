package com.example.library_2023

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.produceState
import com.example.library_2023.databinding.ActivityCategoryAddBinding
import com.example.library_2023.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CategoryAddActivity : ComponentActivity() {

    //view binding
    private lateinit var binding: ActivityCategoryAddBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click,go back
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        //handle click, begin uupload category
        binding.submitBtn.setOnClickListener{
            validateDate()
        }
    }

    private var category = ""

    private fun validateDate(){
        //validate data

        //get data
        if (category.isEmpty()) {
            Toast.makeText(this, "Enter Category...", Toast.LENGTH_SHORT).show()
        }else{
            addCategoryFirebase()
        }
    }

    private fun addCategoryFirebase(){
        //show progress
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap = HashMap<String, Any?>()
        hashMap["id"] = "$timestamp"
        hashMap["category"] = category
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        //add to firebase db: Database Root > Categories > categoriyID > category info
        val ref = FirebaseDatabase.getInstance().getReference("Categories")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                //added succesfully
                progressDialog.dismiss()
                Toast.makeText(this,"Added successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e->
                //failure to add
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }
}
