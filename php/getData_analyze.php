<?php
include "connect.php";

$conn->set_charset("utf8");
$sql = "select * from analysis_result;";

$res = mysqli_query($conn,$sql);
$response = array();
while($row=mysqli_fetch_array($res))
{

 array_push($response,array('date'=>$row[1],'schoolName'=>$row[2],'classroom'=>$row[3],'studentID'=>$row[4],
 'dentName'=>$row[5],'dmft'=>$row[40], 'studentName'=>$row[42], 'gender'=>$row[43]));
}

echo json_encode(array('analysis_result'=>$response), JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>