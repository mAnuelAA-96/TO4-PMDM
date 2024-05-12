package com.example.to4_pmdm.ejercicio2

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.to4_pmdm.R
import com.example.to4_pmdm.databinding.ActivityEjercicio2Binding
import com.example.to4_pmdm.ejercicio2.asynctask.Connection
import java.net.URL
import com.example.to4_pmdm.ejercicio2.asynctask.Result
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Ejercicio2 : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityEjercicio2Binding
    lateinit var myAsyncTask: MyAsyncTask

    private val urlFichero = "https://manuelflo.com/images/to4-pmdm-ej2/imagenes.txt"
    private lateinit var file: File
    private val pathErrores = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val fechaHora = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHoraFormateada = fechaHora.format(formato)

        file = File(pathErrores, "errores.txt")
        file.appendText("----------Nuevo inicio: $fechaHoraFormateada----------\n")

        binding.btnDescargar.setOnClickListener(this)

    }

    private fun showError(imagen: String, error: String){
        Log.e("Error al cargar la imagen",
            "No se ha cargado la imagen de la URL: $imagen\n" +
                    "Error: $error)")
    }

    private fun cargarImagenes(listaImagenes: List<String>){

        for (image in listaImagenes){

            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.CENTER_IN_PARENT)

            val imageView = ImageView(this)
            Glide.with(this).load(image)
                .error(R.drawable.error_imagen)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        showError(image, e!!.message.toString())
                        addError(image, e.message.toString())
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("info", "imagen cargada $image")
                        return false
                    }
                })
                .into(imageView)

            binding.viewFlipperImagenes.addView(imageView)
        }

        binding.viewFlipperImagenes.isAutoStart = true
        binding.viewFlipperImagenes.flipInterval = 1500
        binding.viewFlipperImagenes.setInAnimation(this, R.anim.slide_in_right)
        binding.viewFlipperImagenes.setOutAnimation(this, R.anim.slide_out_left)
        binding.viewFlipperImagenes.startFlipping()

    }

    override fun onClick(v: View?) {
        if(v == binding.btnDescargar){
            descargarFichero()
        }
    }

    private fun descargarFichero(){
        myAsyncTask = MyAsyncTask(this)
        myAsyncTask.execute()
    }

    inner class MyAsyncTask(private val context: Context) : AsyncTask<URL?, Void?, Result>(){

        private lateinit var progress: ProgressDialog

        override fun onPreExecute() {
            progress = ProgressDialog(context)
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progress.setMessage("Conectando...")
            progress.setCancelable(true)
            progress.setOnCancelListener{cancel(true)}
            progress.show()
        }

        override fun doInBackground(vararg url: URL?): Result {
            lateinit var result: Result

            try{
                result = Connection.connect(URL(urlFichero))
            } catch (e: IOException){
                Log.e("HTTP", e.message, e)
                result = Result()
                result.code = 500
                result.message = e.message.toString()
            }

            return result
        }

        override fun onPostExecute(result: Result){
            cargarImagenes(result.listaURLs)
            progress.dismiss()
        }

        override fun onCancelled(){
            progress.dismiss()
        }
    }

    private fun addError(enlace: String, error: String){

        val fechaHora = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHoraFormateada = fechaHora.format(formato)

        file.appendText("$enlace; $fechaHoraFormateada; $error\n")

    }

}