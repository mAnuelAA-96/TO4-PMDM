package com.example.to4_pmdm.ejercicio2.asynctask

class Result {
    var code = 0 //indica el código de estado devuelto por el servidor web
    lateinit var message: String //información del error
    lateinit var listaURLs: List<String> //lista de URLs leidas
}