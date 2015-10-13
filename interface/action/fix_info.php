<?php

// 注册

/*
URL:localhost:3001/fix_info
     {
      "uid":"123553",                  // string
      "type":"0410",                   // string
      "token":"34252",                 // string
      "info":                          // string --个人信息
         {
           "name":"张三",
           "phone":"15700873459",
           "school":"四川大学",
           "campus":"江安校区",
           "grade":"大二",
           "sex":"性别",
           "imageUrl":"头像地址",
         }
    }


$.post('http://localhost/friendstrip/interface/action/fix_info',data, function (res) {
    console.log(res);
});


*/


include 'common.class.php';

class Fixinfo extends Common {


    function __construct() {
        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;

        if (isset($get_data['uid'])) {


            // 满足有一个字段被修改即可
            if (isset($get_data['info']['name']) ||
                isset($get_data['info']['phone']) ||
                isset($get_data['info']['school']) ||
                isset($get_data['info']['campus']) ||
                isset($get_data['info']['grade']) ||
                isset($get_data['info']['sex']) ||
                isset($get_data['info']['imageUrl'])
            ) {
                // 拼接 sql 语句
                $sql = "UPDATE ft_user SET ";
                if (isset($get_data['info']['name'])) {
                    $sql .= "name=:name,";
                }
                if (isset($get_data['info']['phone'])) {
                    $sql .= "phone=:phone,";
                }
                if (isset($get_data['info']['school'])) {
                    $sql .= "school=:school,";
                }
                if (isset($get_data['info']['campus'])) {
                    $sql .= "campus=:campus,";
                }
                if (isset($get_data['info']['grade'])) {
                    $sql .= "grade=:grade,";
                }
                if (isset($get_data['info']['sex'])) {
                    $sql .= "gender=:gender,";
                }
                if (isset($get_data['info']['imageUrl'])) {
                    $sql .= "avatar=:avatar,";
                }
                $sql = rtrim($sql, ',');
                $sql .= " WHERE user_id=:user_id";
                //var_dump($sql);
                $statement = $this->db->prepare($sql);
                // 参数绑定
                if (isset($get_data['info']['name'])) {
                    $statement->bindValue(":name", $get_data['info']['name']);
                }
                if (isset($get_data['info']['phone'])) {
                    $statement->bindValue(":phone", $get_data['info']['phone']);
                }
                if (isset($get_data['info']['school'])) {
                    $statement->bindValue(":school", $get_data['info']['school']);
                }
                if (isset($get_data['info']['campus'])) {
                    $statement->bindValue(":campus", $get_data['info']['campus']);
                }
                if (isset($get_data['info']['grade'])) {
                    $statement->bindValue(":grade", $get_data['info']['grade']);
                }
                if (isset($get_data['info']['sex'])) {
                    $statement->bindValue(":gender", $get_data['info']['sex']);
                }
                if (isset($get_data['info']['imageUrl'])) {
                    $statement->bindValue(":avatar", $get_data['info']['imageUrl']);
                }
                $statement->bindValue(":user_id", $get_data['uid']);
                $count = $statement->execute();

                if ($count) {
                    $this->res['code'] = 0;
                    $this->res['message'] = 'success';
                    $this->res['uid'] = $get_data['uid'];
                } else {
                    $this->res['code'] = 1011;
                    $this->res['message'] = '更新数据库失败';
                }


            } else {
                $this->res['code'] = 1010;
                $this->res['message'] = '没有任何修改';
            }

        } else {
            $this->res['code'] = 1009;
            $this->res['message'] = '没有用户号，非法操作';
        }

    }


    function __destruct() {
        $this->die_json($this->res);
    }

}


$fixinfo = new Fixinfo();
$fixinfo->index();







