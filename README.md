# repotallercurso
Taller del curso Everis Java + MS

Autor: Daniel Alcón Martín
Fecha: 30/11/2020

Repositorio con los microservicios y la estrutura de servicios auxiliares.

Puertos de conexión servicios auxiliares
- eureka-server: 8761
- cloud-server: 8080
- admin-server: 8081
- config-server: 8888
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

En la carpeta "Archivos de properties" se encuentran los properties de las aplicaciones del github, pero no se utilizan para nada.

El servidor Hystrix tiene configurado en su properties que la lista de direcciones permitidas está 172.24.224.1 la de WSL.

Todos los Microservicios utilizan server-config para buscar su fichero properties en el repositorio de github (https://github.com/DAlconM/repotallercursoconfig.git). 

Todos las aplicaciones tienen un archivo Dockerfile

Para generar el container de mysql utilizar 
docker run --name mysqlcontainer -e MYSQL_ROOT_PASSWORD=Myzisql1 -p 3307:3306 -d mysql:latest

Los archivos properties del repositorio "repotallercursoconfig" están configurados con la IP WSL.
Los archivos del resto de apps (eureka, config, adming, cloud) están configurados con ips de docker

En las url la dirección IP está puesta 172.24.224.1, la cual es la IP de WSL de mi propio Docker. 
En los Dockerfile y en los containers se utilizan los mismos puertos exceptuando el de Mysql, el cual utiliza externamente 3307 pero internamente 3306.

Tanto Hystrix-server como Turbine-server tienen configuradas las ip de 172.24.224.1 en allow
hystrix.dashboard.proxyStreamAllowList=172.24.224.1

Las bases de datos tienen los datos que se encuentran en el archivo "Datos para importar en las bases de datos".

En la carpeta "Archivos Docker-Compose" se encuentran los ficheros .yml para hacer el docker-compose de los containers. Levantarlos todos a la vez puede dar errores de conexión en los microservicios porque no podemos controlar el orden en que se activan. 
La solución pasa por crear dos subcarpetas: "servidores" y "servicios". De esta forma, si hacemos el docker-compose primero de los servidores, nos creará un cluster de containers con los servicios. Cuando estén arrancados, haremos el docker-compose de los servicios los cuales ya tendrán disponibles el resto de servidores.
- Anotación: los servicios cliente y visita pueden dar fallos sobre la conexión con mysql por el SSL. En sus urls tienen indicado SSL=false, pero aún así se debe acceder antes almenos una vez a la configuración de la conexión de la base de datos con mysql workbench para indicar que no utilice SSL.