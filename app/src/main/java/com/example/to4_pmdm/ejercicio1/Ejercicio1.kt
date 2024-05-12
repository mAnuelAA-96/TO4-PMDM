package com.example.to4_pmdm.ejercicio1

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.to4_pmdm.R
import com.example.to4_pmdm.databinding.ActivityEjercicio1Binding

class Ejercicio1 : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityEjercicio1Binding
    lateinit var mGestureDetector: GestureDetector
    lateinit var mediaPlayer: MediaPlayer

    private val urlImagen1 = "https://manuelflo.com/images/to4-pmdm-ej1/imagen1.jpg"
    private val urlImagen2 = "https://manuelflo.com/images/to4-pmdm-ej1/imagen2.jpg"
    private val urlImagen3 = "https://manuelflo.com/images/to4-pmdm-ej1/imagen3.jpg"
    private val urlImagen4 = "https://manuelflo.com/images/to4-pmdm-ej1/imagen4.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPaisajes.setOnClickListener(this)

        Glide.with(this).load(urlImagen1).into(binding.iv1)
        Glide.with(this).load(urlImagen2).into(binding.iv2)
        Glide.with(this).load(urlImagen3).into(binding.iv3)
        Glide.with(this).load(urlImagen4).into(binding.iv4)

        val customGestureDetector = CustomGestureDetector()
        mGestureDetector = GestureDetector(this, customGestureDetector)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    //http://codetheory.us/android-viewflipper-and-viewswitcher/
    inner class CustomGestureDetector : GestureDetector.SimpleOnGestureListener(){
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if(e1?.getX()!! > e2.getX()){
                nextView()
            }

            if(e1.getX() < e2.getX()){
                previousView()
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    override fun onClick(v: View?) {
        if(v == binding.btnPaisajes){
            mostrarTexto()
        }
    }

    private fun previousView(){
        binding.vfImagenes.setInAnimation(this, android.R.anim.slide_in_left)
        binding.vfImagenes.setOutAnimation(this, android.R.anim.slide_out_right)

        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_camara)
        mediaPlayer.start()
        binding.vfImagenes.showPrevious()

    }

    private fun nextView(){
        binding.vfImagenes.setInAnimation(this, R.anim.slide_in_right)
        binding.vfImagenes.setOutAnimation(this, R.anim.slide_out_left)

        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_camara)
        mediaPlayer.start()
        binding.vfImagenes.showNext()
    }


    private fun mostrarTexto(){

        val inAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_out)
        binding.vfTexto.inAnimation = inAnimation
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_metal_entrada)
        mediaPlayer.start()
        binding.vfTexto.showNext()
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_metal_salida)

        inAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                mediaPlayer.start()
                binding.vfTexto.isVisible = false
                binding.vfImagenes.isVisible = true
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

    }

}