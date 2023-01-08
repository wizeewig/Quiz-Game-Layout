package com.example.a3leveltesla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class level2 : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseReference2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level2)

        var loadingimage: ProgressBar = findViewById(R.id.progressBar);
        loadingimage.setVisibility(View.VISIBLE);

        imageView=findViewById(R.id.level2pic)
        databaseReference= FirebaseDatabase.getInstance().getReference("photos")
        databaseReference2=FirebaseDatabase.getInstance().getReference("answers")

        databaseReference.child("level2").get()
            .addOnSuccessListener {
                val url= it.child("2").value.toString()

                Glide.with(this).load(url).into(imageView)
                loadingimage.setVisibility(View.INVISIBLE);
            }

        var urlans=""
        databaseReference2.child("level2").get()
            .addOnSuccessListener {
                urlans= it.child("2").value.toString()
            }

        val ans:EditText=findViewById(R.id.level2ans)
        val ansbtn: Button =findViewById(R.id.level2submit)

        ansbtn.setOnClickListener{
            val answer=ans.text.toString()
            if(answer.isEmpty() || answer.trim { it<=' ' }==""){
                Toast.makeText(this, "Please enter your answer", Toast.LENGTH_LONG).show()
            }
            else if(answer==urlans){
                startActivity(Intent(this, level3::class.java))
                Toast.makeText(this, "Congrats...You advance to the next level", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_LONG).show()
            }
        }
    }
}