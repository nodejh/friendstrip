
<?php


//7、获取发布记录------景区、景点、个人中心
// get_trip_by_id.php

/*

{
    "type":"2210",                   // string  2210：景区； 2220：景点
      "token":"10101",                 // string
      "spotId":"124415",               // string
    }


*/


include 'common.class.php';


class Gpslist extends Common {

    public $data;


    function __construct() {

        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;

        $sql = "SELECT * FROM ft_wantgo WHERE spotview_id = :spotview_id ";
        $stmt_trip = $this->db->prepare($sql);
        $stmt_trip->execute(array(':spotview_id' => $get_data['id']));
        $stmt_trip->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt_trip->fetch();
        var_dump($res);


        if ($res) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['trip'] = $res;
        } else {
            $this->res['code'] = 1;
            $this->res['message'] = 'fail! No trip';
        }

    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$release = new Gpslist();
$release->index();












