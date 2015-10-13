
<?php


//7、获取发布记录------景区、景点、个人中心
// get_trip_by_id.php


// TODO

include 'common.class.php';


class Release extends Common {

    public $data;


    function __construct() {

        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;

        $sql_trip = "SELECT * FROM ft_trip WHERE trip_id = :trip_id ";
        $stmt_trip = $this->db->prepare($sql_trip);
        $stmt_trip->execute(array(':trip_id' => $get_data['id']));
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


$release = new Release();
$release->index();












