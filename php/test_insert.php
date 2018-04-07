<?php
include "connect.php";

IF EXISTS(select * from test where id=30122)
   update test set name='john' where id=3012
ELSE
   insert into test(name) values('john');

   ?>