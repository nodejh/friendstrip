
<?php

//14、获取搜索景点详情(by name)

// get_view_by_name.php



/*

var data = {
    "view_name": "成都"
};

$.post('action/get_view_by_name.php', data, function (res) {
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

        $sql = "SELECT * FROM ft_view WHERE view_name = :view_name ";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':view_name' => $_POST['view_name']));
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












