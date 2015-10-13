<?php

// 注册

include '../function/common.php';
include '../database/dbConfigure.php';


$tool = new Tool();

if ($tool->all_not_null($_POST)) {

    if (isset($_POST['phone']) &&
        isset($_POST['password']) &&
        isset($_POST['confirm_password']) &&
        isset($_POST['code'])) {

        if (! $tool->reg_exp_phone($_POST['phone'])) {
            $tool->res['code'] = 1000;
            $tool->res['message'] = '手机号格式错误';
            $tool->die_json($tool->res);
        }

        if (! $tool->reg_exp_password($_POST['password'])) {
            $tool->res['code'] = 1001;
            $tool->res['message'] = '密码格式错误';
            $tool->die_json($tool->res);
        }

        if ($_POST['password'] != $_POST['confirm_password']) {
            $tool->res['code'] = 1002;
            $tool->res['message'] = '两次密码不一致';
            $tool->die_json($tool->res);
        }

        // TODO 验证码


        $stmt = $db->prepare("select * from ft_user where phone=:phone");
        $stmt->execute(array(':phone'=> $_POST['phone']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);  //设置获取模式

        while($row = $stmt->fetch()){
            // 如果手机号已存在
            $tool->res['code'] = 1003;
            $tool->res['message'] = '电话号码已存在';
            $tool->die_json($tool->res);
        }

        $stmt = $db->prepare("insert into ft_user(phone, password, date) values (:phone, :password, :date)");

        $stmt->bindparam(':phone', $phone);
        $stmt->bindparam('password', $password);
        $stmt->bindparam(":date",$date);

        $phone = $_POST['phone'];
        $password = $tool->encrypt($_POST['password']);
        $date = time();

        if($stmt->execute()){
            $tool->res['code'] = 0;
            $tool->res['message'] = 'success';
            $tool->res['uid'] = $pdo->lastInsertId();
            $tool->die_json($tool->res);
        }else{
            $tool->res['code'] = 1004;
            $tool->res['message'] = 'fail to insert to database';
            $tool->die_json($tool->res);
        }

    }
} else {
    $tool->res['code'] = 1005;
    $tool->res['message'] = '非法操作';
    $tool->die_json($tool->res);
}

// example:
/*
var data = {
    'phone': '18333330000',
  'password': '111111',
  'confirm_password': '111111',
  'code': '0'
}

$.post('http://localhost/friendstrip/interface/action/signup',data, function (res) {
    console.log(res);
});

*/