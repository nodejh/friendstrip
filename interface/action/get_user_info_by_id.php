<?php


//23、获取用户信息-

// get_user_info_by_id.php



/*

var data = {
    "user_id": 1
};

$.post('action/get_user_info_by_id.php', data, function (res) {
console.log(res);
});



 */

include 'common.class.php';


class Recommon extends Common {

    public $data;


    function __construct() {

        parent::__construct();
    }


    public function get_trip() {

        $sql = "SELECT * FROM ft_user WHERE trip_id = :user_id ";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':user_id' => $_POST['user_id']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetch();

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


$recommend = new Recommon();
$recommend->get_trip();












