package com.example.library_2023

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.library_2023.databinding.ActivityLoginBinding
import com.example.library_2023.databinding.ActivityRegisterBinding
import com.example.library_2023.ui.theme.Library_2023Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : ComponentActivity() {

    //view binding
    private lateinit var binding: ActivityLoginBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog, will show while creating account | regiser user
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, not have account, goto register screen
        binding.noAccountTv.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        //handle click, begin login
        binding.loginBtn.setOnClickListener{
            /*Steps:
            1)input Data
            2)validate data
            3)Login - firebase auth
            4)Check user type - firebase auth
                if user - move to user dashboard
                if admin - move to user dashboard
             */
            validateData()
        }
    }

    private var email = ""
    private var password = ""

    private fun validateData(){
        //1)Input Data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //2) Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid Email format...", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            //invalid email pattern
            Toast.makeText(this,"Enter password...", Toast.LENGTH_SHORT).show()
        }
        else if (email.isEmpty()){
            //empty password
            Toast.makeText(this,"Enter your name...", Toast.LENGTH_SHORT).show()
        }
        else{
            loginUser()
        }
    }

    private fun loginUser(){
        //3)Login - firebase auth

        //show progress
        progressDialog.setMessage("Logging In")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                checkUser()
            }
            .addOnFailureListener{ e->
                //failed login
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}...", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser(){
        /*
        * 4)Check user type - firebase auth
                if user - move to user dashboard
                if admin - move to user dashboard
        * */

        progressDialog.setMessage("Checking User")

        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()
                    //get user type e.g. user or admin
                    val userType = snapshot.child("userType").value
                    if(userType == "user"){
                        //it's simple user, open user dashboard
                        startActivity(Intent(this@LoginActivity,DashboardUserActivity::class.java))
                        finish()
                    }
                    else if(userType == "admin"){
                        //it's admin, open admin dashboard
                        startActivity(Intent(this@LoginActivity,DashboardAdminActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}
