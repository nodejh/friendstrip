<?php

// 注册

/*
URL:localhost:3001/sign_in
    {
      "phone":"123553",                // string
      "pwd":"32452",                   // string
      "code":"55422",                  // string
      //"type":"0210",                   // string
      "info":
         {                             // string
           "name":"张三",
           "province":"四川",
           "city":"成都",
           "school":"四川大学",
           "campus":"江安校区",
           "grade":"大二",
           "address":"学校地址：四川省成都市双流县",
           "sex":"男",
         }
    }


$.post('http://localhost/friendstrip/interface/action/sign_in',data, function (res) {
    console.log(res);
});


*/


//include '../function/common.php';
//include '../database/dbConfigure.php';

include 'common.class.php';


$tool = new Common();


$post_data = file_get_contents("php://input");
//$get_data = $_POST;
$get_data = json_decode($post_data, true);

//echo gettype($post_data);

var_dump($get_data['phone']);

if ($tool->all_not_null($get_data)) {

  
    if (isset($get_data['phone']) &&
        isset($get_data['pwd']) &&
        isset($get_data['code'])) {

        if (! $tool->reg_exp_phone($get_data['phone'])) {
            $tool->res['code'] = 1000;
            $tool->res['message'] = '手机号格式错误';
            $tool->die_json($tool->res);
        }

        if (! $tool->reg_exp_password($get_data['pwd'])) {
            $tool->res['code'] = 1001;
            $tool->res['message'] = '密码格式错误';
            $tool->die_json($tool->res);
        }


        // TODO 验证码。 由于没有短信验证码，所以这里一直返回真
        if (! $tool->reg_exp_msg_code($get_data['code'])) {
            $tool->res['code'] = 1002;
            $tool->res['message'] = '短信验证码错误';
            $tool->die_json($tool->res);
        }


        $stmt = $tool->db->prepare("select * from ft_user where phone=:phone");
        $stmt->execute(array(':phone'=> $get_data['phone']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);  //设置获取模式

        while($row = $stmt->fetch()){
            // 如果手机号已存在
            $tool->res['code'] = 1003;
            $tool->res['message'] = '电话号码已存在';
            $tool->die_json($tool->res);
        }

        $stmt = $tool->db->prepare("insert into ft_user(phone, password, date,name,country,city,school,campus,grade,address,gender) values (:phone, :password, :date, :name,:country,:city,:school,:campus,:grade,:address,:gender)");

        $stmt->bindparam(':phone', $phone);
        $stmt->bindparam(':password', $password);
        $stmt->bindparam(":date",$date);
        $stmt->bindparam(":name",$name);
        $stmt->bindparam(":country",$country);
        $stmt->bindparam(":city",$city);
        $stmt->bindparam(":school",$school);
        $stmt->bindparam(":campus",$campus);
        $stmt->bindparam(":grade",$grade);
        $stmt->bindparam(":address",$address);
        $stmt->bindparam(":gender",$gender);

        $phone = $get_data['phone'];
        $password = $tool->encrypt($get_data['pwd']);
        $date = time();
        $name = $get_data['info']['name'];
        $country = $get_data['info']['province'];
        $city = $get_data['info']['city'];
        $school = $get_data['info']['school'];
        $campus = $get_data['info']['campus'];
        $grade = $get_data['info']['grade'];
        $address = $get_data['info']['address'];
        $gender = $get_data['info']['sex'];

        if($stmt->execute()){
            $tool->res['code'] = 0;
            $tool->res['message'] = 'success';
            $tool->res['uid'] = $tool->db->lastInsertId();
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

