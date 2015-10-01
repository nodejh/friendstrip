<?php
/*
 * 注册-验证短信验证码
 */


if ($_POST) {
    $data = $_POST;
} elseif ($_GET) {
    $data = $_GET;
}

if (isset($data['phone']) && isset($data['code'])) {
    $phone = $data['phone'];
    $code = $data['code'];

    if(isset($_SESSION[$phone]['code']) && $code == $_SESSION[$phone]['code']) {
        $result['code'] = $code;
        $result['status'] = 0;
        $result['message'] = '验证短信验证码成功';
        session_unset();
        session_destroy();
    }

} else {
    $result['status'] = 3001;
    $result['message'] = '短信验证码错误';
}

echo (json_encode($result));
