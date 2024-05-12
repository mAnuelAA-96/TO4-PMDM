package com.example.to4_pmdm.ejercicio2.asynctask

import android.os.Environment
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Connection {

    fun connect(url: URL): Result{
        var response = 500
        var result = Result()
        var urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

        response = urlConnection.responseCode
        result.code = response
        result.message = urlConnection.responseMessage

        if(response == HttpURLConnection.HTTP_OK){
            result.listaURLs = getUrl(urlConnection.inputStream)
        } else {
            //result.message = "Error en el acceso a la web: $response"
            addError(url.toString(), result.code.toString(), result.message)
        }

        urlConnection.disconnect()
        return result
    }

    private fun getUrl(inputStream: InputStream?): List<String> {
        val reader = BufferedReader(inputStream!!.reader())
        var listaURLs = mutableListOf<String>()

        try{
            var line = reader.readLine()
            while (line != null){
                listaURLs.add(line)
                line = reader.readLine()
            }
        } finally {
            reader.close()
            return listaURLs
        }
    }

    private fun addError(enlace: String, errorCode: String, error: String){

        val pathError = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(pathError, "errores.txt")

        val fechaHora = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHoraFormateada = fechaHora.format(formato)

        file.appendText("$enlace; $fechaHoraFormateada; $errorCode $error\n")
    }

}