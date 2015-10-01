<?php

// 完善信息

include '../function/common.php';
include '../database/dbConfigure.php';


$tool = new Tool();


if ($tool->all_not_null($_POST)) {

    if (!isset($_POST['uid'])) {
        $tool->res['code'] = 1010;
        $tool->res['message'] = 'user id 丢失！非法操作';
        $tool->die_json($tool->res);
    }

    if (isset($_POST['name']) ||
        isset($_POST['gender']) ||
        isset($_POST['school']) ||
        isset($_POST['campus']) ||
        isset($_POST['grade']) ||
        isset($_POST['address'])) {

        $sql = 'UPDATE ft_user SET name=:name, gender=:gender, school=:school, campus=:campus, grade=:grade, address=:address WHERE user_id=:user_id';

        $stmt = $db->prepare($sql);

        $name = isset($_POST['name']) ? $_POST['name'] : '';
        $gender = isset($_POST['gender']) ? $_POST['gender'] : '';
        $school = isset($_POST['school']) ? $_POST['school'] : '';
        $campus = isset($_POST['campus']) ? $_POST['campus'] : '';
        $grade = isset($_POST['grade']) ? $_POST['grade'] : '';
        $address = isset($_POST['address']) ? $_POST['address'] : '';
        $user_id = $_POST['uid'];

        $stmt->bindparam(':name', $name);
        $stmt->bindparam(':gender', $gender);
        $stmt->bindparam(':school', $school);
        $stmt->bindparam(':campus', $campus);
        $stmt->bindparam(':grade', $grade);
        $stmt->bindparam(':address', $address);
        $stmt->bindParam(':user_id', $user_id);

        if($stmt->execute()){
            $tool->res['code'] = 0;
            $tool->res['message'] = 'update user info success';
            $tool->die_json($tool->res);
        }else{
            $tool->res['code'] = 0;
            $tool->res['message'] = 'update user info fail';
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
  'uid': '7',
  'name': 'jianghang',
  'gender': 1,
  'school': 'sichuan university',
  'campus': 'jiangan',
  'grade': '2013',
  'address': 'chengdu'
}

$.post('http://localhost/friendstrip/interface/action/complete_userinfo',data, function (res) {
    console.log(res);
});

*/
