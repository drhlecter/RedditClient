# RedditClient
por Edwin González para imaginamos.com


**Funcionamiento de la aplicación**

Al iniciar la app automáticamente se descargaran los subreddits desde la url 
del json dado. Estos quedarán guardados en una base de datos local que permite
volver a navegar por los subreddits de forma offline en un próxima corrida de
la aplicación.

El botón flotante permite volver a descargar en cualquier momento la lista de 
subreddits desde el servidor.

Al hacer click en un subreddit la interfaz se desliza hacia la sección de 
Detalles, donde se puede ver más información e imágenes del elemento 
seleccionado. En cualquier momento puede deslizarse desde la lista de subreddits
a la sección de detalles y viceversa.

La aplicación fuciona tanto en horizontal como vertical.


**Pruebas realizadas**

En los archivos fuente pueden verse las clases de pruebas unitarias y de interfaz
que fueron utilizadas. Específicamente en:

RedditClient\app\src\androidTest\java\com\edwin\redditclient\MyUiTests.java
RedditClient\app\src\test\java\com\edwin\redditclient\MyUnitTests.java
