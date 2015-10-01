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


/*
 *
 * //准备一条语句,并放到服务器端,而且编译
  $stmt=$pdo->prepare("insert into shop(name,price)values(?,?)");
//  $stmt=$pdo->prepare("insert into shop(name,price)values(:na,:pr)");

  //绑定参数(变量和参数绑定)
  $stmt->bindparam(1,$name);
  $stmt->bindparam(2,$price);

//  $stmt->bindparam(":na",$name);
//  $stmt->bindparam(":pr",$price);

  $name="liwu11";
  $price=234.4311;

  if($stmt->execute()){
    echo "执行成功";
    echo "最后插入的ID:".$pdo->lastInsertId();
}else{
    echo "执行失败";
  }
 */