<?php

$dsn = "mysql:host=localhost;dbname=friendstrip";
$db_user = 'jh';
$db_password = '123456';
$db = new PDO($dsn, $db_user, $db_password);
$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_NATURAL);
