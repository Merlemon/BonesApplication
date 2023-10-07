package com.example.bonesapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageView
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TipBeforeCameraPage : AppCompatActivity() {
        // надо детально разобраться с тем как работать со сделанными фотками
    private val REQUEST_TAKE_PHOTO = 1

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_before_camera_page)

//        imageView = findViewById(R.id.imageView)

        val imageButton: ImageButton = findViewById(R.id.photo)

        imageButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }


        }


    val backButton: Button = findViewById(R.id.backButtonFromTip)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем миниатюру картинки
            val thumbnailBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(thumbnailBitmap)
        }
        when(requestCode){
            REQUEST_TAKE_PHOTO ->{
                if(resultCode == Activity.RESULT_OK && data !== null){
                    imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            else ->{
                Toast.makeText(this, "Wrong request code", Toast.LENGTH_SHORT).show()
            }

        }
    }
}