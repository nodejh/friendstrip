<?php
/*
 * 应用程序初始化
 */

include 'db.php';


if ($_POST) {
    $data = $_POST;
} elseif ($_GET) {
    $data = $_GET;
}


if (isset($data['phone_id'])) {

    $token = 'token';

    //用名称传入参数
    $stmt = $db->prepare("INSERT INTO ft_token (phone_id, value) VALUES (:phone_id, :value)");
    $stmt->bindParam(':phone_id', $phone_id);
    $stmt->bindParam(':value', $value);

    $phone_id = $_POST['phone_id'];
    $value = $token;

    $insert_token = $stmt->execute();

    if ($insert_token) {
        $result['token'] = $token;
        $result['status'] = 0;
        $result['message'] = '应用程序初始化成功';

    } else {
        $result['status'] = 1001;
        $result['message'] = '存储token错误';
    }

} else {
    $result['status'] = 1000;
    $result['message'] = '传入的手机id为空';
}


echo (json_encode($result));

