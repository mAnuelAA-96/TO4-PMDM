EJERCICIO 1

El layout del ejercicio 1 se compone de un botón y dos ViewFlipper para la visualización del texto a mostrar y las imágenes. El ViewFlipper que muestra el texto se compone de un TextView vacío y otro con la frase a mostrar cuando se pulse el botón. Cuando se pulsa el botón, comienza la animación de este ViewFlipper que muestra el texto y, mediante un MediaPlayer, se establece el sonido de entrada. Una vez acaba la animación, se reproduce otro sonido que indica el fin de esta animación. Además, para cambiar el texto por las imágenes, se cambia la visibilidad de los dos ViewFlipper para que se muestre uno u otro.

En el ViewFlipper que mostrará las imágenes, se establecen 4 vistas iguales, compuestas por un ImageView, un TextView con el nombre del lugar de la imagen mostrada y un EditText para valorar la imagen. Estas 4 vistas irán rotando deslizando el dedo por la pantalla. Esto se hace mediante una clase interna CustomGestureDetector.

En el método onCreate() se cargan las imágenes en el ViewFlipper y se establece la variable mGestureDetector para cambiar de imagen. El cambio de imagen se realiza mediante los métodos previousView() y nextView(), que tendrán una animación de entrada y salida (contrarias en ambos métodos) y se establece un sonido para el cambio entre vistas.

Para los degradados del background y de los botones se han creado dos archivos diferentes en el directorio res/drawable, llamados degradado_background.xml y degradado_botones.xml. Estos estilos se referencian en el layout de la actividad en el botón y en el ContraintLayout.


EJERCICIO 2

El layout del ejercicio 2 se compone de un botón y de un ViewFlipper (VF) para la rotación de las imágenes descargadas. Para el botón he utilizado el mismo estilo cargado en la actividad anterior y en todo el proyecto.

En el método onCreate() he inicializado el archivo de errores. Si no está creado este se crea y se añade un separador para cada inicio de la aplicación con la fecha y la hora del inicio para separar los errores en diferentes ejecuciones.

Mediante el botón se procede a descargar el fichero txt alojado en el servidor. La descarga se hace mediante la clase MyAsyncTask, utilizando la clase Result y Connection para realizarla. En la clase Result he cambiado una de las variables, adaptándola al contenido del fichero. Se trata de una lista de Strings para almacenar los enlaces contenidos en el fichero txt. Para realizar este cambio, he tenido que adaptar en la clase Connection el método getUrl(), que lee el fichero y almacena las URLs para posteriormente devolver una lista con todas ellas. En esta clase Connection también he añadido el método addError() para registrar en el fichero de error los errores producidos.

En la clase principal del ejercicio, he realizado la carga de imágenes del fichero en el ViewFlipper. Esto se hace mediante el método cargarImagenes(), al cual se le pasa la lista de imágenes obtenidas mediante la clase Connection. Para cada imagen, se crean los parámetros de visualización (para que queden centradas en la pantalla) y posteriormente se carga mediante Glide. Una vez finalizada la carga de todas las imágenes, se ajusta el intervalo de cambio y se lanza la animación.

Al usar Glide he encontrado una dificultad que la he solucionado de una forma que no era la que yo quería en un principio. He usado el listener de esta biblioteca. Mi idea era que, en caso de producirse un error con el enlace, la "imagen" de dicho enlace quedara excluida del ViewFlipper, mostrándose únicamente las imágenes correctas. Esto no he conseguido hacerlo, ya que se cargaba una imagen por defecto en el ViewFlipper. Lo que he hecho es añadir una imagen en caso de error.