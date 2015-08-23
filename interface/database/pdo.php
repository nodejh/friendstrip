<?php
error_reporting(E_ALL);
$dsn = "mysql:host=localhost;dbname=friendstrip";
$db_user = 'jh';
$db_password = '123456';
$db = new PDO($dsn, $db_user, $db_password);
$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_NATURAL);

//var_dump($db);
//$count = $db->exec("INSERT INTO ft_test SET name='tttt'");
//echo $count;
//
//foreach($db->query("SELECT * FROM ft_test") as $row) {
//    print_r($row);
//    print_r('<br/>');
//}

$rs = $db->query("SELECT * FROM ft_test");
$rs->setFetchMode(PDO::FETCH_NUM);
$result_array = $rs->fetchAll();

var_dump($result_array);


$db = null;


?>