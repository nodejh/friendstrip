<?php

/*
 3、登录

功能简介：

    用户登录，服务器收到请求，判断账号密码是否正确。返回相应结果

客户端（POST）：

    URL：localhost：3001/log_in
    {
      "type":0301,                  // number
      "phone":"15709345512",         // string
      "pwd":"xxfwe234",              // string
      "token":"",                    // string--首次登录token为空
    }
服务器端：

    Success:
    {
      "status":"0" ,             // string --成功
      "token":"23535323"，       // string --返回token值
      "info":                // string --个人信息
         {
           "name":"张三",
           "phone":"15700873459"，
           "school":"四川大学",
           "campus":"江安校区",
           "grade":"大二",
           "sex":"性别",
           "imageUrl":"头像地址",
         }
       "joinNum": 3 ,              // number -加入数
       "releaseNum": 6 ,           // number -发布数        "commentNum": 10 ,          // number -评论数
       "letterNum": 3,             // number -私信数
    }
    //Failure:
    //{
    //  "status": "0311" ,           // string --表示登录失败
    //  "message":"用户名不存在"，     // string --信息
    //}



$.post('http://localhost/friendstrip/interface/action/log_in',data, function (res) {
    console.log(res);
});

*/


include 'common.class.php';

class Login extends Common {


    function __construct() {
        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;

        $sql = "SELECT * FROM ft_user WHERE phone = :phone ";
        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':phone' => $get_data['phone']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetch();

        if ($res) {
            if ($res['password'] == $this->encrypt($get_data['pwd'])) {
                $this->res['code'] = 0;
                $this->res['message'] = '登录成功';
                $this->res['token'] = $this->token();

                //个人基本信息
                $this->res['info']['name'] = $res['name'];
                $this->res['info']['phone'] = $res['phone'];
                $this->res['info']['school'] = $res['school'];
                $this->res['info']['campus'] = $res['campus'];
                $this->res['info']['sex'] = $res['gender'];
                $this->res['info']['imageUrl'] = $res['avatar'];
                $this->res['info']['name'] = $res['name'];

                // 加入数
                $sql_join = "SELECT * FROM ft_join WHERE user_id=" . $res['user_id'];
                $res_join = $this->db->prepare($sql_join);
                $res_join->execute();
                $this->res['info']['joinNum'] = $res_join->rowCount();

                // 评论数
                $sql_comment = "SELECT * FROM ft_comment WHERE user_id=" . $res['user_id'];
                $res_comment = $this->db->prepare($sql_comment);
                $res_comment->execute();
                $this->res['info']['commentNum'] = $res_comment->rowCount();

                //评论
                $sql_trip = "SELECT * FROM ft_trip WHERE user_id=" . $res['user_id'];
                $res_trip = $this->db->prepare($sql_trip);
                $res_trip->execute();
                $this->res['info']['releaseNum'] = $res_trip->rowCount();

                //私信
                $sql_message = "SELECT * FROM ft_message WHERE reciveuser_id=" . $res['user_id'];
                $res_message = $this->db->prepare($sql_message);
                $res_message->execute();
                $this->res['info']['letterNum'] = $res_message->rowCount();

                $this->res['code'] = 0;
                $this->res['message'] = 'success';


            } else {
                $this->res['code'] = 1007;
                $this->res['message'] = '密码错误';
            }

        } else {
            $this->res['code'] = 1006;
            $this->res['message'] = '用户名（手机号）错误';
        }
    }


    function __destruct() {
        $this->die_json($this->res);
    }

}


$login = new Login();
$login->index();






