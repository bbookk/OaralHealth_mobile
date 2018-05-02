<?php
include "connect.php";

$conn->set_charset("utf8");
$sql = "select * from analysis_result;";

$res = mysqli_query($conn,$sql);
$response = array();
while($row=mysqli_fetch_array($res))
{

 array_push($response,array('date'=>$row[0],'schoolName'=>$row[1],'classroom'=>$row[2],'studentID'=>$row[3],
 'dentName'=>$row[4],'dmft'=>$row[5], 'studentName'=>$row[6], 'gender'=>$row[7]));
}

echo json_encode(array('analysis_result'=>$response), JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>