# Proyecto Final Backend I

## Documentación

* Para levantar el proyecto es necesario tener levantada una base de datos MySQL bajo el nombre "dh-db"
* El usuario debe ser "root" y la contraseña "" (vacia)
* El puerto donde se levanta el proyecto es le 8001
* Esta configuracion se puede cambiar en el archivo __application.properties__ en la carpeta __src/main/resources__

## Usuarios de prueba

### ADMIN

* __username__: pepito@gmail.com
* __password__: PepitoPass

### USER

* __username__: cuchuflito@gmail.com
* __password__: CuchuflitoPass

## Endpoints

* __GET__ /
####
* __GET__ /odontologo
* __GET__ /odontologo/{id}
* __POST__ /odontologo
* __PUT__ /odontologo/{id}
* __DELETE__ /odontologo/{id}
####
* __GET__ /paciente
* __GET__ /paciente/{id}
* __POST__ /paciente
* __PUT__ /paciente/{id}
* __DELETE__ /paciente/{id}
####
* __GET__ /turno
* __GET__ /turno/{id}
* __POST__ /turno
* __PUT__ /turno/{id}
* __DELETE__ /turno/{id}}
