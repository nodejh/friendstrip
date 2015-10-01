<?php

$dsn = "mysql:host=localhost;dbname=friendstrip";
$db_user = 'jh';
$db_password = '123456';

try {

    $db = new PDO($dsn, $db_user, $db_password);
    $db->setAttribute(PDO::ATTR_CASE, PDO::CASE_NATURAL);

} catch (PDOException $error){

    echo '数据库连接失败：'.$error->getMessage();
    exit();
}