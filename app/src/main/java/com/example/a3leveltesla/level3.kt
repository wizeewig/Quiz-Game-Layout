package com.example.a3leveltesla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class level3 : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseReference2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level3)

        var loadingimage: ProgressBar = findViewById(R.id.progressBar);
        loadingimage.setVisibility(View.VISIBLE);

        imageView=findViewById(R.id.level3pic)
        databaseReference= FirebaseDatabase.getInstance().getReference("photos")
        databaseReference2=FirebaseDatabase.getInstance().getReference("answers")

        databaseReference.child("level3").get()
            .addOnSuccessListener {
                val url= it.child("3").value.toString()

                Glide.with(this).load(url).into(imageView)
                loadingimage.setVisibility(View.INVISIBLE);
            }

        var urlans=""
        databaseReference2.child("level3").get()
            .addOnSuccessListener {
                urlans= it.child("3").value.toString()
            }

        val ans:EditText=findViewById(R.id.level3ans)
        val ansbtn: Button =findViewById(R.id.level3submit)

        ansbtn.setOnClickListener{
            val answer=ans.text.toString()
            if(answer.isEmpty() || answer.trim { it<=' ' }==""){
                Toast.makeText(this, "Please enter your answer", Toast.LENGTH_LONG).show()
            }
            else if(answer==urlans){
                startActivity(Intent(this, congrats::class.java))
                Toast.makeText(this, "Hurray!!! You have completed TESLA", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_LONG).show()
            }
        }
    }
}