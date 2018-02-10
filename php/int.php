<?php
include "connect.php";
$datajson = file_get_contents("php://input");
$data = json_decode($datajson);

$school_name = $data->school_name;
$school_addr = $data->school_addr;
$std_name = $data->std_name;
$gender = $data->gender;
$dateOfBirth = $data->dateOfBirth;
$std_addr = $data->std_addr;
$nation = $data->nation;
$religion = $data->religion;
$dad_name = $data->dad_name;
$mom_name = $data->mom_name;
$dad_status = $data->dad_status;
$mom_status = $data->mom_status;
$dad_job = $data->dad_job;
$mom_job = $data->mom_job;
$parent_name = $data->parent_name;
$parent_phone = $data->parent_phone;
$parent_addr = $data->parent_addr;
$teacher = $data->teacher;
$master = $data->master;
$decay_num = $data->decay_num;


$conn->set_charset("utf8");
$sql = "INSERT INTO student(`schoolName`, `schoolAddr`, `studentName`, `gender`, `dateOfBirth`, `studentAddr`, `nationality`, `religion`, `dadName`, `momName`, `dadStatus`, `momStatus`, `dadJob`, `momJob`, `parentName`, `parentTel`, `parentAddr`, `teacherName`, `masterName`, `decayNum`)  
        VALUES ('".$school_name."','".$school_addr."','".$std_name."','".$gender."','".$dateOfBirth."','".$std_addr."','".$nation."','".$religion."','".$dad_name."','".$mom_name."','".$dad_status."','".$mom_status."','".$dad_job."','".$mom_job."','".$parent_name."','".$parent_phone."','".$parent_addr."','".$teacher."','".$master."','".$decay_num."')";

if ($conn->query($sql) === TRUE) {
    $newdata = array("Error"=>"false", "Message"=>"hi add member","data"=>$data);
} else {
    $newdata = array("Error"=>"true", "Message"=>"fail add","data"=>$data );
}

//$newdata = array("Error"=>"false", "Message"=>"Success","data"=>$id);
echo json_encode($newdata);
$conn->close();
//echo $datajson;


?>