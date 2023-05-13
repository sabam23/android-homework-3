package com.example.homework_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastName : EditText
    private lateinit var submit : Button
    private lateinit var id: EditText
    private lateinit var email :EditText
    private lateinit var img :EditText

    private val db = Firebase.database.getReference("students")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        listeners()
    }

    private fun listeners() {
        submit.setOnClickListener {
            val name = firstName.text.toString()
            val surname = lastName.text.toString()
            val id = id.text.toString()
            val email = email.text.toString()
            val img = img.text.toString()

            if (name.isEmpty() || email.isEmpty() || surname.isEmpty()
                || id.isEmpty() || img.isEmpty()) {
                Toast.makeText(this, "Fill the fields", Toast.LENGTH_SHORT).show()
            } else if (id.length < 13) {
                Toast.makeText(this, "Id should be 13 digits", Toast.LENGTH_SHORT).show()
            } else if (!email.contains("@")) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            } else {
                val studentInfo = Student(
                    name,
                    surname,
                    id,
                    email,
                    img
                )
                db.child("student1").setValue(studentInfo)
                Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
        }
    }

    private fun init() {
        firstName = findViewById(R.id.nameEditText)
        lastName = findViewById(R.id.surnameEditText)
        id = findViewById(R.id.personalID)
        email = findViewById(R.id.emailEditText)
        submit = findViewById(R.id.sumbitBtn)
        img = findViewById(R.id.img)
    }

}