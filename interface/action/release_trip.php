<?php


// 5、发布出行（拼车）
//
// release.php


/*



var data = {
  "user_id": 1, // 发布者ID
  "type": 1, // 1 表示景区（城市）;2 表示景点{因为景点、景区都有出行记录和发布出行的功能，12统一表现为游玩};3表示拼车
  "spotview_id": 1, // 景点、景区ID；从出行、个人中心发布的出行没有该ID
  "start_city": "成都", // 出发城市，后台获取
  "start_school": "四川大学",// 始发学校；如：四川大学
  "destination": "锦里", //目的地名
  "start_time": "2015-10-04", //出发日期
  "more_info": "从四川大学出发去峨眉山", // 出行说明
  "people_number": 5 //出行（拼车）人数
};


$.post('action/release_trip.php', data, function (res) {
console.log(res);
});


 */

include 'common.class.php';


class Recommon extends Common {

    public $data;


    function __construct() {
        parent::__construct();
    }


    //当前景点
    public function release() {
        //var_dump($_POST);

        $sql = "INSERT INTO ft_trip(user_id, type, spotview_id, start_school, destination, start_time, date, more_info, people_number, people_hadnum) VALUES (:user_id, :type, :spotview_id, :start_school, :destination, :start_time, :date, :more_info, :people_number, :people_hadnum)";

        $people_hadnum = 0;
        $time = time();
        $date = strtotime($_POST['start_time']);
var_dump($date);
        var_dump($time);

        $stmt = $this->db->prepare($sql);
        $stmt->bindparam(':user_id', $_POST['user_id']);
        $stmt->bindparam(':type', $_POST['type']);
        $stmt->bindparam(':spotview_id', $_POST['spotview_id']);
        $stmt->bindparam(':start_school', $_POST['start_school']);
        $stmt->bindparam(':destination', $_POST['destination']);
        $stmt->bindparam(':people_number', $_POST['people_number']);
        $stmt->bindparam(':start_time', $date);
        $stmt->bindparam(":date",$time);
        $stmt->bindparam(':more_info', $_POST['more_info']);
        $stmt->bindparam(':people_hadnum', $people_hadnum);

        if($stmt->execute()){
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['trip_id'] = $this->db->lastInsertId();
        }else{
            $this->db->errorInfo();
            $this->res['code'] = -1;
            $this->res['message'] = 'fail to insert to database';
        }


    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$recommend = new Recommon();
$recommend->release();



