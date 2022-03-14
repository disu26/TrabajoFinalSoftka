# TrabajoFinalSoftka

Trabajo final para la cantera nivel 2 de softka university.

------ Backend -------

Para el backend se utilizó MySQL, controlado gracias a java con spring boot. La creación de la base de datos se encuentra en la carpeta resources, este código tiene que ser ejecutado para poder crear la tabla de contactos donde se almacenará toda la información.

También se utilizó NodeJs para manejar la base de datos mongo, en la cual se almacena la información de los usuarios. esta también se debe inicializar por consola para correr correctamente el programa.

------ Frontend ------

Para el Frontend se utilizó Angular, el cual consume el API rest en java como servicios.

Para poder correr el programa se necesita tener Angular CLI, si no se tiene previamente instalado se puede instalar con el comando "npm install -g @angular/cli", una vez instalado se debe ir a la raíz del frontend por medio de la consola y ejecutar allí el comando "ng serve", el cuál pondrá en un servidor el frontend y este estará listo para ser utilizado.

La página se abre desde el servidor en el que se configuró el Angular, por defecto el 4200.

---- Funcionamiento del programa ----

Inicio de sesión

No se pudo crear un registro, estos datos son quemados en la bd.
![image](https://user-images.githubusercontent.com/80411541/158114520-561ff90b-06ff-4eef-9d45-604481c1f806.png)
![image](https://user-images.githubusercontent.com/80411541/158114560-147fdcbb-3036-4eb5-aa60-1ddcdbff6584.png)

Se ingresan las creedenciales, estas se validan en el backend y se deja ingresar a la persona.
![image](https://user-images.githubusercontent.com/80411541/158114626-236d7af7-9afb-4dd6-bb63-5fe1c7a4c761.png)

Cuando entra la primera persona, no se muestra nada. Hay que actualizar para poder ver el temporizador y la tarjeta que se generó.
![image](https://user-images.githubusercontent.com/80411541/158114645-57694627-d166-47a5-9c96-687ba1ec3e6e.png)

La tarjeta es generada aleatoriamente y se asigna a la persona. El temporizador se maneja con una variable de tiempo que se crea cuando entra la primera persona y después se realizan operaciones con esta en el frontend.
![image](https://user-images.githubusercontent.com/80411541/158114676-2631173f-95f7-4834-97b5-db6f0c7decef.png)

Cuando pasan los 5 min, se lleva a esta pantalla donde el administrador de la partida puede generar la siguiente balota y al darle al botón se muestran las balotas que han salido.
![image](https://user-images.githubusercontent.com/80411541/158114709-2787de1b-9583-4bd6-96fc-5cabcd3b5169.png)

En la tarjeta se puede dar click a los campos del bingo y estos se marcan en la base de datos si se confirma que ya salieron.
Al darle al botón de bingo se verifica si la persona ganó o no y se muestra un letrero en base a esto. 

![image](https://user-images.githubusercontent.com/80411541/158114742-e1bc9bce-52d4-4d93-879d-f226b68a88f8.png)


---Anotaciones---

Se presentaron problemas de tiepo y por esto no se pudo completar como se debería este trabajo, sin embargo se puso mucho empeño en el backend y se realizaron bastantes validaciones.

Se presentaron problemas para hacer que varias personas ingresaran a la vez a la aplicación, esto puede generar errores.

También se pueden generar errores si una persona entra dos veces en dos juegos distintos.

Y a veces los ids de mongo se envían como undefined, esto también puede generar errores.






