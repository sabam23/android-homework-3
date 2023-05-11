package com.example.homework_3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    private val db = Firebase.database.getReference("students")

    private lateinit var imageView : ImageView
    private lateinit var firstName: TextView
    private lateinit var lastName : TextView
    private lateinit var id: TextView
    private lateinit var email : TextView
    private lateinit var img : TextView
    lateinit var backBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {
        backBtn = findViewById(R.id.backBtn)
        imageView = findViewById(R.id.imageView)
        firstName = findViewById(R.id.nameTextView)
        lastName = findViewById(R.id.surnameTextView)
        id = findViewById(R.id.personalId)
        email = findViewById(R.id.emailTextView)
        img = findViewById(R.id.pic)

        db.child("student1").addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val studentInfo = snapshot.getValue(Student::class.java) ?: return
                firstName.text = "First name: ${studentInfo.firstname}"
                lastName.text = "Last name: ${studentInfo.lastname}"
                id.text = "Id: ${studentInfo.id}"
                email.text = "Email: ${studentInfo.email}"

                Glide.with(this@ProfileActivity).load(studentInfo.img).into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}