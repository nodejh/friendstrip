<?php
/*
 * 注册-手机号密码
 */

include 'db.php';

if ($_POST) {
    $data = $_POST;
} elseif ($_GET) {
    $data = $_GET;
}

if (isset($data['phone']) && isset($data['password'])) {

    $stmt = $db->prepare("INSERT INTO ft_user (phone, password, date) VALUES (:phone, :password, :date)");
    $stmt->bindParam(':phone', $phone);
    $stmt->bindParam(':password', $password);
    $stmt->bindParam(':date', $date);

    $phone = $data['phone'];
    $password = $data['password'];
    $date = time();

    $user = $stmt->execute();

    if ($user) {
        $result['user_id'] = $db->lastInsertId();
        $result['status'] = 0;
        $result['message'] = '手机号密码注册成功';
    } else {
        $result['status'] = 3002;
        $result['message'] = '手机号密码写入数据库失败';
    }

} else {
    if (!isset($data['phone'])) {
        $result['status'] = 3003;
        $result['message'] = '手机号不能为空';
    } elseif (!isset($data['password'])) {
        $result['status'] = 3004;
        $result['message'] = '密码不能为空';
    } else {
        $result['status'] = 3005;
        $result['message'] = '手机号或密码不能为空';
    }
}


echo (json_encode($result));
