
<?php


// 通过 trip_id 获取发布记录
// get_trip_by_trip_id.php



/*

var data = {
    "trip_id": 1
};

$.post('action/get_trip_by_id.php', data, function (res) {
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

        //$get_data = $_POST;
        $post_data = file_get_contents("php://input");
        $get_data = json_decode($post_data, true);

        $sql = "SELECT * FROM ft_trip WHERE trip_id = :trip_id ";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':trip_id' => $get_data['trip_id']));
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












