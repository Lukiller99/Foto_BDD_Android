<?php
$hostname="localhost";
$database="BDAndroid";
$username="root";
$password="";

$conexion = mysqli_connect($hostname,$username,$password,$database);

$id=$_POST['id'];
$titulo=$_POST['titulo'];
$descripcion=$_POST['descripcion'];
$foto=$_POST['foto'];
$lat=$_POST['lan'];
$lon=$_POST['lon'];


$path='imagenes/'.$titulo.'.jpg';
$url="http://$hostname/BDAndroid/$path";

file_put_contents($path,base64_decode($foto));
$bytesArchivo=file_get_contents($path);

$sql="INSERT INTO fotos VALUES (?,?,?,?,?,?,?)";
$stm=$conexion->prepare($sql);
$stm->bind_param('issssdd',$id,$titulo,$descripcion,$bytesArchivo,$path,$lon,$lat);

if($stm->execute()){
    echo "registrado";
}else{
    echo "noRegistra";
}
