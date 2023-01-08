package com.example.a3leveltesla

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.jvm.internal.MutablePropertyReference2


class level1 : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseReference2: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        var loadingimage: ProgressBar= findViewById(R.id.progressBar);
        loadingimage.setVisibility(View.VISIBLE);


        imageView=findViewById(R.id.level1pic)
        databaseReference=FirebaseDatabase.getInstance().getReference("photos")
        databaseReference2=FirebaseDatabase.getInstance().getReference("answers")

        databaseReference.child("level1").get()
            .addOnSuccessListener {
                val url= it.child("1").value.toString()

                Glide.with(this).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
                loadingimage.setVisibility(View.INVISIBLE);
            }

        var urlans=""
        databaseReference2.child("level1").get()
            .addOnSuccessListener {
                 urlans= it.child("1").value.toString()
            }

        val ans:EditText=findViewById(R.id.level1ans)
        val ansbtn: Button =findViewById(R.id.level1submit)

        ansbtn.setOnClickListener{
            val answer=ans.text.toString()
            if(answer.isEmpty() || answer.trim { it<=' ' }==""){
                Toast.makeText(this, "Please enter your answer", Toast.LENGTH_LONG).show()
            }
            else if(answer==urlans){
                startActivity(Intent(this, level2::class.java))
                Toast.makeText(this, "Congrats...You advance to the next level", Toast.LENGTH_LONG).show()
                finish()
            }
            else{
                Toast.makeText(this, "Wrong Answer", Toast.LENGTH_LONG).show()
            }
        }
    }
}