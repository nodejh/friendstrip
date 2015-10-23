<?php

/*
 *
 *


      {
      "uid":"1",                    // string
      "type":"1",                     // string 1:景点； 2： 景区
       "spotId":"235",              // string
      //"gps":"143.2244/21.24412",         // string 前面是经度，后面是纬度
      "gps_x":  "143.2222", //经度
      "gps_y": "21.24412" // 纬度
    }


 *
 *
 *
 */


include 'common.class.php';

/**
 * 想去
 */
class Want extends Common {

    public $data;


    function __construct() {
        parent::__construct();
    }


    public function index() {
        //$post_data = file_get_contents("php://input");
        //$get_data = json_decode($post_data, true);
        $get_data = $_POST;

        $sql = "INSERT INTO ft_wantgo(user_id, type, spotview_id, time, gps) VALUES (:user_id, :type, :spotview_id, :time, GeomFromText(POINT(:gps_x :gps_y)))";

        //$sql = "INSERT INTO ft_wantgo(user_id, type, spotview_id, time, gps) VALUES (:user_id, :type, :spotview_id, :time, GeomFromText(POINT(:gps_x :gps_y)))";

        //$sql = "insert into ft_wantgo(user_id, type, spotview_id, time, gps) values(?,?,?,?,GeomFromText(POINT(? ?)))";
        //$stmt = $this->db->prepare($sql);



        $user_id = $get_data['uid'];
        $type = $get_data['type'];
        $spotview_id = $get_data['spotId'];
        $time = time();
        $gps_x = $get_data['gps_x'];
        $gps_y = $get_data['gps_y'];

        //$stmt->$this->blinparam(1,$user_id);
        //$stmt->$this->blinparam(2,$type);
        //$stmt->$this->blinparam(3,$spotview_id);
        //$stmt->$this->blinparam(4,$time);
        //$stmt->$this->blinparam(5,$gps_x);
        //$stmt->$this->blinparam(6,$gps_y);



        $user_id = $get_data['uid'];
        $type = $get_data['type'];
        $spotview_id = $get_data['spotId'];
        $time = time();
        $gps_x = $get_data['gps_x'];
        $gps_y = $get_data['gps_y'];

//var_dump($get_data);

        $stmt = $this->db->prepare($sql);
        $stmt->bindparam(':user_id', $user_id);
        $stmt->bindparam(':type', $type);
        $stmt->bindparam(':spotview_id', $spotview_id);
        $stmt->bindparam(':time',$time);
        $stmt->bindparam(':gps_x',$gps_x);
        $stmt->bindparam(':gps_y',$gps_y);

        if($stmt->execute()){
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['tripID'] = $this->db->lastInsertId();
        }else{
            $this->db->errorInfo();
            $this->res['code'] = 1075;
            $this->res['message'] = '写入数据库失败';
        }


    }



    function __destruct() {
        $this->die_json($this->res);
    }

}


$comment = new Want();
$comment->index();