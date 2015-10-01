<?php
/*
 * 注册-完善信息
 */

include 'db.php';

if ($_POST) {
    $data = $_POST;
} elseif ($_GET) {
    $data = $_GET;
}

if (isset($data['user_id'])) {

    if (isset($data['name'])) {

        if (isset($data['gender'])) {

            if (isset($data['school'])) {

                if (isset($data['campus'])) {

                    if (isset($data['grade'])) {

                        if (isset($data['address'])) {

                            $sql = "UPDATE ft_user SET name=:name,gender=:gender,school=:school,campus=:campus,grade=:grade,address=:address WHERE user_id=:user_id";
                            $stmt = $db->prepare($sql);
                            $stmt->bindParam(':name', $name);
                            $stmt->bindParam(':gender', $gender);
                            $stmt->bindParam(':school', $school);
                            $stmt->bindParam(':campus', $campus);
                            $stmt->bindParam(':grade', $grade);
                            $stmt->bindParam(':address', $address);
                            $stmt->bindParam(':user_id', $user_id);

                            $name = $data['name'];
                            $gender = $data['gender'];
                            $school = $data['school'];
                            $campus = $data['campus'];
                            $grade = $data['grade'];
                            $address = $data['address'];
                            $user_id = $data['user_id'];

                            $user = $stmt->execute();

                            if ($user) {
                                $result['status'] = 0;
                                $result['message'] = '完善信息成功';
                            } else {
                                $result['status'] = 3013;
                                $result['message'] = '用户信息写入数据库失败';
                            }

                        } else {
                            $result['status'] = 3012;
                            $result['message'] = '非法操作，完善信息时地址不能为空';
                        }

                    } else {

                        $result['status'] = 3011;
                        $result['message'] = '非法操作，完善信息时年级不能为空';
                    }

                } else {
                    $result['status'] = 3010;
                    $result['message'] = '非法操作，完善信息时校区不能为空';
                }

            } else {
                $result['status'] = 3009;
                $result['message'] = '非法操作，完善信息时学校不能为空';
            }

        } else {
            $result['status'] = 3008;
            $result['message'] = '非法操作，完善信息时性别不能为空';
        }

    } else {
        $result['status'] = 3007;
        $result['message'] = '非法操作，完善信息时姓名不能为空';
    }

} else {
    $result['status'] = 3006;
    $result['message'] = '非法操作，完善信息时 user_id 不能为空';
}

// http://localhost/friendstrip/interface/test/sign_info.php?user_id=1&name=name&gender=1&school=school&campus=campus&grade=grade&address=address

echo (json_encode($result));
