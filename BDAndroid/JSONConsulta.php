<?php
$hostname="localhost";
$database="BDAndroid";
$username="root";
$password="";

$json=array();

if(isset($_GET["id"])){
    $id=$_GET["id"];
    $conexion = mysqli_connect($hostname,$username,$password,$database);

    $consulta = "select * from fotos where id= '{$id}'";
   

    $resultado=mysqli_query($conexion,$consulta);
    

    if($registro=mysqli_fetch_array($resultado)){
        $resul["id"]=$registro["id"];
        $resul["titulo"]=$registro["titulo"];
        $resul["descripcion"]=$registro["descripcion"];
        $resul["ruta"]=$registro["ruta"];
        $json["fotos"][]=$resul;
    }else{
        $resul["id"]=0;
        $resul["titulo"]='no registra';
        $resul["descripcion"]='no registra';
        $resul["ruta"]='no registra';
        $json["fotos"][]=$resul;
    }
    mysqli_close($conexion);
    echo json_encode($json);
}else{
    $resultar["sucess"]=0;
    $resultar["message"]='WS no Retorna';
    $json['fotos'][]=$resultar;
    echo json_encode($json);

}