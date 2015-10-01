<?php
/*
 * 注册-获取短信验证码
 */


if ($_POST) {
    $data = $_POST;
} elseif ($_GET) {
    $data = $_GET;
}

if (isset($data['phone'])) {
    $phone = $data['phone'];
    $code = 5680;

    $_SESSION[$phone]['code'] = $code;

    $result['code'] = $code;
    $result['status'] = 0;
    $result['message'] = '获取短信验证码成功';

} else {
    $result['status'] = 3000;
    $result['message'] = '获取短信验证码时，手机号不能为空';
}

echo (json_encode($result));
