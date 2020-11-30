# repotallercurso
Taller del curso Everis Java + MS

Repositorio con los microservicios y la estrutura de servicios auxiliares.

Puertos de conexión servicios auxiliares
- eureka-server: 8761
- cloud-server: 8080
- admin-server: 8081
- server-config: 8888
- hystrix-server: 8082
- turbine-server: 8083

Eureka-> user: usuario | password: usuario

Puertos de conexión microservicios
- cliente-service: 8090
- factura-service: 8091
- pago-service: 8092
- visita-service: 8093

Proyectos de entidades del dominio
- proyecto-entidades-dto (Data Transfer Object)
- proyecto-entidades-mongo
- proyecto-entidades-sql

En la carpeta "Llamadas Postman" se encuentran dos archivos de Postman con las llamadas a los microservicios exportadas. Las que utilizan Gateway en el puerto 8080 y el nombre del ms, requieren que esté inciado el programa cloud-server.

Todos los Microservicios utilizan server-config para buscar su fichero properties en el repositorio de github (https://github.com/DAlconM/repotallercursoconfig.git). 

En la carpeta "Archivos de properties" se encuentran los properties de las aplicaciones del github, pero no se utilizan para nada.