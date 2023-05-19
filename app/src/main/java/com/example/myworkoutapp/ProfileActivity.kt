package com.example.myworkoutapp

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.myworkoutapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var actionBar: ActionBar
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference:DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri:Uri
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "User Profile"

        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.btnSave.setOnClickListener {
            showProgressBar()

            val firstName = binding.mTvUserFirstName.text.toString()
            val lastName = binding.mTvUserLastName.text.toString()
            val email = binding.mTvUserEmail.text.toString()

            val user = User(firstName, lastName, email)
            if (uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener {

                    if (it.isSuccessful){

                        uploadProfilePic()

                    }else{
                        hideProgressBar()
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun uploadProfilePic(){
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile_pic}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+firebaseAuth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {

            hideProgressBar()
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{

            hideProgressBar()
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()

        }
    }

    private fun showProgressBar(){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

}
