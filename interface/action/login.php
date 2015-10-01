<?php

// 登录

include '../function/common.php';
include '../database/dbConfigure.php';


$tool = new Tool();


if ($tool->all_not_null($_POST)) {

    if (isset($_POST['phone']) &&
        isset($_POST['password'])) {

        if (! $tool->reg_exp_phone($_POST['phone'])) {
            $tool->res['code'] = 1007;
            $tool->res['message'] = '手机号格式错误';
            $tool->die_json($tool->res);
        }

        $stmt = $db->prepare("select * from ft_user where phone=:phone");
        $stmt->execute(array(':phone'=> $_POST['phone']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);  //设置获取模式

        if ($row = $stmt->fetch()){

            if ($tool->encrypt($_POST['password']) == $row['password']) {
                $tool->res['code'] = 0;
                $tool->res['message'] = '电话号码已存在';
                $tool->die_json($tool->res);
            } else {
                $tool->res['code'] = 1009;
                $tool->res['message'] = 'wrong password';
                $tool->die_json($tool->res);
            }

        } else {
            $tool->res['code'] = 1008;
            $tool->res['message'] = '手机号错误';
            $tool->die_json($tool->res);
        }

    }

} else {
    $tool->res['code'] = 1006;
    $tool->res['message'] = '非法操作';
    $tool->die_json($tool->res);
}


// example:
/*
var data = {
  'phone': '18333330000',
  'password': '111111'
}

$.post('http://localhost/friendstrip/interface/action/login',data, function (res) {
    console.log(res);
});

*/
