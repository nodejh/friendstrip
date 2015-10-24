<?php

/*
 *
 *


      {
      "uid":"1",                    // string
      "type":"1",                     // string 1:景点； 2： 景区
       "spotId":"235",              // string
      "gps":"143.2244,21.24412",         // string 前面是经度，后面是纬度

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

        $sql = "INSERT INTO ft_wantgo(user_id, type, spotview_id, time, gps) VALUES (:user_id, :type, :spotview_id, :time, :gps)";

        $user_id = $get_data['uid'];
        $type = $get_data['type'];
        $spotview_id = $get_data['spotId'];
        $time = time();
        $gps = $get_data['gps'];

        $stmt = $this->db->prepare($sql);
        $stmt->bindparam(':user_id', $user_id);
        $stmt->bindparam(':type', $type);
        $stmt->bindparam(':spotview_id', $spotview_id);
        $stmt->bindparam(':time',$time);
        $stmt->bindparam(':gps',$gps);

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


$want = new Want();
$want->index();