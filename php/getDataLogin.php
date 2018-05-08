<?php
$host  = "localhost";
$user = "root";
$pass = "";
$db = "oralhealth";

$con = mysqli_connect($host,$user,$pass,$db);

$con->set_charset("utf8");
$sql = "select * from dentist;";

$res = mysqli_query($con,$sql);
$response = array();
while($row=mysqli_fetch_array($res))
{

 array_push($response,array('id'=>$row[0],'username'=>$row[1],'password'=>$row[2],'firstName'=>$row[3]
 ,'lastname'=>$row[4],'email'=>$row[5],'type'=>$row[6]));
}

echo json_encode(array('result'=>$response), JSON_UNESCAPED_UNICODE);
mysqli_close($con);
//echo "Hello world...";



?>﻿