
<?php


//15、获取景点详情(by ID)

// get_view_by_id.php



/*

var data = {
    "view_id": 1
};

$.post('action/get_view_by_id.php', data, function (res) {
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

        $sql = "SELECT * FROM ft_view WHERE view_id = :view_id ";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':view_id' => $_POST['view_id']));
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












