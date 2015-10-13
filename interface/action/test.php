<?php

// $get_data = file_get_contents('php://input');
//   $data = json_decode($get_data, true);
  // echo $data;

  // $a = $_POST;

// $a['dsa'] = 'dsaf';
// $a['dasf'] = 'eqq';

// $data = $GLOBALS['HTTP_RAW_POST_DATA'];
//   // echo ($GLOBALS['HTTP_RAW_POST_DATA']);
// echo gettype(json_decode($data));

$a = file_get_contents("php://input");

$b = json_decode($a, true);
echo $b;

var_dump($b);



//       UPDATE `access_users`
//  SET `contact_first_name` = :firstname,
//      `contact_surname` = :surname,
//      `contact_email` = :email,
//      `telephone` = :telephone
//WHERE `user_id` = :user_id -- you probably have some sort of id
//       $statement = $conn->prepare($sql);
//       $statement->bindValue(":firstname", $firstname);
//       $statement->bindValue(":surname", $surname);
//       $statement->bindValue(":telephone", $telephone);
//       $statement->bindValue(":email", $email);
//       $count = $statement->execute();

//// new data
//$title = 'PHP Pattern';
//$author = 'Imanda';
//$id = 3;
//// query
//$sql = "UPDATE books SET title=?, author=? WHERE id=?";
//$update = $this->db->prepare($sql);
//$update->execute(array($title,$author,$id));




//
//$query = $database->prepare('SELECT * FROM table WHERE  name LIKE :name');
//$query->bindValue(':name', '%'.$name.'%', PDO::PARAM_STR);
//$query->execute();
//while ($results = $query->fetch())
//{
//    echo $results['name'];
//}