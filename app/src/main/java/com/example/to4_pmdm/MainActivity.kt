package com.example.to4_pmdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.to4_pmdm.databinding.ActivityMainBinding
import com.example.to4_pmdm.ejercicio1.Ejercicio1
import com.example.to4_pmdm.ejercicio2.Ejercicio2

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEjercicio1.setOnClickListener(this)
        binding.btnEjercicio2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == binding.btnEjercicio1){
            val intent = Intent(this, Ejercicio1::class.java)
            startActivity(intent)
        } else if (v == binding.btnEjercicio2){
            val intent = Intent(this, Ejercicio2::class.java)
            startActivity(intent)
        }
    }
}