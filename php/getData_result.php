<?php

include "connect.php";

// if(!$con)
// {
//  die("Error ".mysqli_connect_error());
 
// }
// else
// {
//  echo "<h3>connection success</h3>";
 
// }

$con->set_charset("utf8");
$sql = "select * from result_status;";

$res = mysqli_query($con,$sql);
$response = array();
while($row=mysqli_fetch_array($res))
{

 array_push($response,array('ID'=>$row[1],'studentName'=>$row[2]));
}

echo json_encode(array('result'=>$response), JSON_UNESCAPED_UNICODE);
mysqli_close($con);
//echo "Hello world...";



?>﻿