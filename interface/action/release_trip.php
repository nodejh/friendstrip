<?php


// 5、发布出行（拼车）
//
// release.php


/*



var data = {
      "uid":"4353",              // string
      "token": "token",            // string
      //"type":"0510"                // string  [1,2,3] 0510、景区 ，0520、景点 ，0530、拼车
      "type": 1, // 1 表示景区（城市）;2 表示景点{因为景点、景区都有出行记录和发布出行的功能，12统一表现为游玩};3表示拼车
      "spotId":"32551",            // string 景点、景区ID；[--从出行、个人中心发布的出行没有该ID--]从出行、个人中心发布的出行该ID为0
      //"origin":"1";                // number 1：景点、景区 2：直接发布（个人中心，出行);去掉该字段，因为在数据库中如果 spotview_id为0，则表示从直接发布。
     "info":                       // string
       {
        // "uid":"发布者ID",  // 去掉该参数，因为已经有了
         "oriCity":"成都", //；如：成都",
         "oriSchool":"四川大学", //；如：四川大学"，
         "destination":"锦里。。",
         "goDate":"2015-10-14 20:00:00",    //年月日时,格式固定
         "phone":"18999999999",
         "content":"出行说明",
         "wantNumber":  "10",
       }
    }



$.post('action/release_trip.php', data, function (res) {
    console.log(res);
});


 */

include 'common.class.php';


class Release extends Common {

    public $data;


    function __construct() {
        parent::__construct();
    }


    //当前景点
    public function release() {

        $get_data = $_POST;

        $sql = "INSERT INTO ft_trip(user_id,phone, type, spotview_id, start_school, start_city, destination, start_time, date, more_info, people_number, people_hadnum) VALUES (:user_id, :phone,:type, :spotview_id, :start_school, :start_city, :destination, :start_time, :date, :more_info, :people_number, :people_hadnum)";

        $people_hadnum = 0; // 已有人数。因为是刚发布，所以初始化为0
        $time = time();
        $date = strtotime($get_data['info']['goDate']); // post 过来的时间格式为 2010-01-01


        $stmt = $this->db->prepare($sql);
        $stmt->bindparam(':user_id', $get_data['uid']);
        $stmt->bindparam(':phone', $get_data['info']['phone']);
        $stmt->bindparam(':type', $get_data['type']);
        $stmt->bindparam(':spotview_id', $get_data['spotId']);
        $stmt->bindparam(':start_school', $get_data['info']['oriSchool']);
        $stmt->bindparam(':start_city', $get_data['info']['oriCity']);
        $stmt->bindparam(':destination', $get_data['info']['destination']);
        $stmt->bindparam(':start_time', $date);
        $stmt->bindparam(':date',$time);
        $stmt->bindparam(':more_info', $get_data['info']['content']);
        $stmt->bindparam(':people_number', $get_data['info']['wantNumber']);
        $stmt->bindparam(':people_hadnum', $people_hadnum);

        if($stmt->execute()){
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['tripID'] = $this->db->lastInsertId();
        }else{
            $this->db->errorInfo();
            $this->res['code'] = 1012;
            $this->res['message'] = '写入数据库失败';
        }
    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$recommend = new Release();
$recommend->release();



