#!/bin/bash

echo "Introduzca la dirección del repositorio: "

#Dirección del repositorio almacenado en la memoria secundaria del ordenador.
read direccionRepositorioOrdenador

echo "Introduzca la dirección https del repositorio: "

#Dirección https del repositorio almacenado en GitLab,GitHub...
read direccionHttpsRepositorio

#Rama del repositorio en la que se encuentra el usuario.
rama=""

#Opcion del menú introducida por el usuario.
opcion=-1

#Inicia un repositorio en una rama especificada por el usuario.
iniciar_repositorio(){
	echo "Introduzca la rama inicial: "
	read rama
	cd $direccionRepositorioOrdenador
	git init  --initial-branch=$rama
}

#Clona el repositorio cuya dirección https ha sido especificada.
clonarRepositorio(){
	cd $direccionRepositorioOrdenador
	git clone $direccionHttpsRepositorio
}

#Descarga una rama específica de un repositorio específico.
bajarRepositorio(){
	cd $direccionRepositorioOrdenador
	git pull $direccionHttpsRepositorio $rama
}

#Permite subir un repositorio específico a una rama específica.
subirRepositorio(){
	cd $direccionRepositorioOrdenador
	git add .
	echo "Introduzca el mensaje de 'commit': "
	read mensaje
	git commit -m \"$mensaje\"
	git push $direccionHttpsRepositorio $rama
}

#Permite cambiar la dirección del repositorio donde se encuentra el .git
cambiarDireccionRepositorio(){
	echo "Introduzca la nueva dirección del repositorio: "
	read direccionRepositorioOrdenador
}

#Permite cambiar la dirección https del repositorio.
cambiarDireccionHttpsRepositorio(){
	echo "Introduzca la nueva dirección https del repositorio: "
	read direccionHttpsRepositorio
}

#Permite crear una nueva rama en el repositorio.
crearNuevaRama(){
	echo "Introduzca la nueva rama a crear: "
	read rama
	git checkout -b $rama
}

#Permite cambiar de rama del repositorio.
cambiarRama(){
	echo "Introduzca la rama a la que cambiar: "
	read rama
	git checkout $rama
}

#Muestra la rama en la que se encuentra el usuario.
mostrarRama(){
	echo $rama
}

#Imprime por pantalla la dirección del repositorio en la memoria secundaria
#del ordenador.
mostrarDireccionRepositorio(){
	echo $direccionRepositorioOrdenador
}

#Imprime por pantalla la dirección https del repositorio.
mostrarDireccionHttpsRepositorio(){
	echo $direccionHttpsRepositorio
}

#Imprime el menú principal del script.
menu(){
	echo "0. Salir"
	echo "1. Inicializar repositorio"
	echo "2. Clonar repositorio"
	echo "3. Bajar repositorio"
	echo "4. Subir repositorio"
	echo "5. Cambiar dirección repositorio"
	echo "6. Cambiar dirección https del repositorio"
	echo "7. Crear nueva rama"
	echo "8. Cambiar rama"
	echo "9. Mostrar rama"
	echo "10. Mostrar dirección repositorio"
	echo "11. Mostrar dirección https del repositorio"
}

while [ $opcion -ne 0 ]
do
	menu
	read opcion
	case $opcion in
		1)
			iniciar_repositorio
			;;
		2)
			clonarRepositorio
			;;
		3)
			bajarRepositorio
			;;
		4)
			subirRepositorio
			;;
		5)
			cambiarDireccionRepositorio
			;;
		6)
			cambiarDireccionHttpsRepositorio
			;;
		7)
			crearNuevaRama
			;;
		8)
			cambiarRama
			;;
		9)
			mostrarRama
			;;
		10)
			mostrarDireccionRepositorio
			;;
		11)
			mostrarDireccionHttpsRepositorio
			;;
	esac
done
