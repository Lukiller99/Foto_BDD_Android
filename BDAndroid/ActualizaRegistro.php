<?php 
$hostname="localhost";
$database="fotos";
$username="root";
$password="";
$conexion = mysqli_connect($hostname,$username,$password,$database);

$consulta2 = "SELECT ruta,id from fotos where id = (select MAX(id) from fotos) ";
$resultado1=mysqli_query($conexion,$consulta2);

$registro1=mysqli_fetch_array($resultado1);

$resul["registro"]=$registro1["id"];
$resul["ruta"]=$registro1["ruta"];

$json["fotos"][]=$resul;

echo $resul["registro"];
echo $resul["ruta"];
