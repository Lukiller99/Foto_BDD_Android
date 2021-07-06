<?php
$hostname="localhost";
$database="fotos";
$username="root";
$password="";

$conexion = mysqli_connect($hostname,$username,$password,$database);

$id=$_POST['id'];
$titulo=$_POST['titulo'];
$descripcion=$_POST['descripcion'];
$foto=$_POST['foto'];


$path='imagenes/'.$titulo.'.jpg';
$url="http://$hostname/BDAndroid/$path";

file_put_contents($path,base64_decode($foto));
$bytesArchivo=file_get_contents($path);

$sql="INSERT INTO fotos VALUES (?,?,?,?,?)";
$stm=$conexion->prepare($sql);
$stm->bind_param('issss',$id,$titulo,$descripcion,$bytesArchivo,$path);

if($stm->execute()){
    echo "registrado";
}else{
    echo "noRegistra";
}

