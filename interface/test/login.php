<?php

include 'db.php';


if ($_POST) {
    $data = $_POST;
} elseif ($_GET) {
    $data = $_GET;
}

if (isset($data['phone']) && isset($data['password'])) {

    if ($userinfo && $userinfo['password'] == $data['password']) {

        $result['userinfo']['name'] = $userinfo['name'];
        $result['userinfo']['phone'] = $userinfo['phone'];
        $result['userinfo']['country'] = $userinfo['country'];
        $result['userinfo']['city'] = $userinfo['city'];
        $result['userinfo']['school'] = $userinfo['school'];
        $result['userinfo']['campus'] = $userinfo['campus'];
        $result['userinfo']['grade'] = $userinfo['grade'];
        $result['userinfo']['avatar'] = $userinfo['avatar'];
        $result['userinfo']['gender'] = $userinfo['gender'];
        $result['status'] = 0;
        $result['message'] = '登录成功';

    } else {
        $result['status'] = 2001;
        $result['message'] = '用户名或密码错误';
    }

} else {
    $result['status'] = 2000;
    $result['message'] = '用户名或密码为空';
}

echo (json_encode($result));


?>