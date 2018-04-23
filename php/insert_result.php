<?php
include "connect.php";

$std_id = $_POST["std_id"];
$std_name = $_POST["studentName"];
$teeth_11 = $_POST["teeth_11"];
$teeth_12 = $_POST["teeth_12"];
$teeth_13 = $_POST["teeth_13"];
$teeth_14 = $_POST["teeth_14"];
$teeth_15 = $_POST["teeth_15"];
$teeth_16 = $_POST["teeth_16"];
$teeth_17 = $_POST["teeth_17"];
$teeth_18 = $_POST["teeth_18"];
$teeth_21 = $_POST["teeth_21"];
$teeth_22 = $_POST["teeth_22"];
$teeth_23 = $_POST["teeth_23"];
$teeth_24 = $_POST["teeth_24"];
$teeth_25 = $_POST["teeth_25"];
$teeth_26 = $_POST["teeth_26"];
$teeth_27 = $_POST["teeth_27"];
$teeth_28 = $_POST["teeth_28"];
$teeth_31 = $_POST["teeth_31"];
$teeth_32 = $_POST["teeth_32"];
$teeth_33 = $_POST["teeth_33"];
$teeth_34 = $_POST["teeth_34"];
$teeth_35 = $_POST["teeth_35"];
$teeth_36 = $_POST["teeth_36"];
$teeth_37 = $_POST["teeth_37"];
$teeth_38 = $_POST["teeth_38"];
$teeth_41 = $_POST["teeth_41"];
$teeth_42 = $_POST["teeth_42"];
$teeth_43 = $_POST["teeth_43"];
$teeth_44 = $_POST["teeth_44"];
$teeth_45 = $_POST["teeth_45"];
$teeth_46 = $_POST["teeth_46"];
$teeth_47 = $_POST["teeth_47"];
$teeth_48 = $_POST["teeth_48"];
$record_date = $_POST["record_date"];
$dentist_name = $_POST["dentist_name"];

$conn->set_charset("utf8");
$sql = "SELECT id FROM result_status WHERE id='$std_id'";
$update = "update result_status set 
teeth_11='$teeth_11', teeth_12='$teeth_12', teeth_13='$teeth_13', teeth_14='$teeth_14',
teeth_15='$teeth_15', teeth_16='$teeth_16', teeth_17='$teeth_17', teeth_18='$teeth_18',
teeth_21='$teeth_21', teeth_22='$teeth_22', teeth_23='$teeth_23', teeth_24='$teeth_24',
teeth_25='$teeth_25', teeth_26='$teeth_26', teeth_27='$teeth_27', teeth_28='$teeth_28',
teeth_31='$teeth_31', teeth_32='$teeth_32', teeth_33='$teeth_33', teeth_34='$teeth_34',
teeth_35='$teeth_35', teeth_36='$teeth_36', teeth_37='$teeth_37', teeth_38='$teeth_38',
teeth_41='$teeth_41', teeth_42='$teeth_42', teeth_43='$teeth_43', teeth_44='$teeth_44',
teeth_45='$teeth_45', teeth_46='$teeth_46', teeth_47='$teeth_47', teeth_48='$teeth_48' , record_date='$record_date' , dentist_name='$dentist_name' where id='$std_id'";

$Sql_Query = "insert into result_status(`id`, `name`, 
`teeth_11`, `teeth_12`, `teeth_13`, `teeth_14`, 
`teeth_15`, `teeth_16`, `teeth_17`, `teeth_18`,
`teeth_21`, `teeth_22`, `teeth_23`, `teeth_24`, 
`teeth_25`, `teeth_26`, `teeth_27`, `teeth_28`,
`teeth_31`, `teeth_32`, `teeth_33`, `teeth_34`, 
`teeth_35`, `teeth_36`, `teeth_37`, `teeth_38`,
`teeth_41`, `teeth_42`, `teeth_43`, `teeth_44`, 
`teeth_45`, `teeth_46`, `teeth_47`, `teeth_48`, `record_date`, `dentist_name`)  
        values ('".$std_id."','".$std_name."',
        '".$teeth_11."','".$teeth_12."','".$teeth_13."','".$teeth_14."',
        '".$teeth_15."','".$teeth_16."','".$teeth_17."','".$teeth_18."',
        '".$teeth_21."','".$teeth_22."','".$teeth_23."','".$teeth_24."',
        '".$teeth_25."','".$teeth_26."','".$teeth_27."','".$teeth_28."',
        '".$teeth_31."','".$teeth_32."','".$teeth_33."','".$teeth_34."',
        '".$teeth_35."','".$teeth_36."','".$teeth_37."','".$teeth_38."',
        '".$teeth_41."','".$teeth_42."','".$teeth_43."','".$teeth_44."',
        '".$teeth_45."','".$teeth_46."','".$teeth_47."','".$teeth_48."','".$record_date."','".$dentist_name."')";

        $query = "SELECT * FROM result_status WHERE id='$std_id'";
        $result = mysqli_query($conn,$query);

        if($result->num_rows > 0) {
            if(mysqli_query($conn,$update)){
 
                echo 'Data update Successfully';
      
                }
                else{
                
                echo 'Try Again';
                
                }
        }else{
            if(mysqli_query($conn,$Sql_Query)){
 
                echo 'Data insert Successfully';
      
                }
                else{
                
                echo 'Try Again';
                
                }
        }
        
    
        
        mysqli_set_charset($conn,"utf8");
        mysqli_close($conn);
?>