
<?php

//12、获取景区详情（by ID）
// get_spot_by_id.php



/*

var data = {
    "spot_id": 1
};

$.post('action/get_spot_by_id.php', data, function (res) {
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

        $sql = "SELECT * FROM ft_spot WHERE spot_id = :spot_id ";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':spot_id' => $_POST['spot_id']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetch();

        if ($res) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['spot'] = $res;
        } else {
            $this->res['code'] = 1;
            $this->res['message'] = 'fail! No spot_id';
        }

    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$recommend = new Recommon();
$recommend->get_trip();












