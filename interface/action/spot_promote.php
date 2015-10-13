<?php


/*

var data = {
      "type":"1010",                    // string 1010:省份，1020：城市
      "token":"4213",                   // string
      "name": "四川"        // string
   }

$.post('action/spot_promote.php', data, function (res) {
    console.log(res);
});



 */

include 'common.class.php';


class Promote extends Common {

    public $data;



    function __construct() {

        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;

        if ($get_data['type'] == '1010') {
            $this->get_country($get_data['name']);
        } else {
            $this->get_city($get_data['name']);
        }

    }


    public function get_country($name) {

        $sql = "SELECT * FROM ft_spot WHERE country=:country ORDER BY want_number DESC LIMIT 10";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':country' => $name));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetchAll();

        foreach ($res as $k => $v) {
            $pictures = explode(',', $v['picture']);
            $res[$k]['imageUrl'] = $pictures[0];
        }

        if ($res) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['spotList'] = $res;
        } else {
            $this->res['code'] = 1045;
            $this->res['message'] = '结果为空';
        }

    }


    public function get_city($name) {

        $sql = "SELECT * FROM ft_spot WHERE city=:city ORDER BY want_number DESC LIMIT 10";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':city' => $name));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetchAll();

        foreach ($res as $k => $v) {
            $pictures = explode(',', $v['picture']);
            $res[$k]['imageUrl'] = $pictures[0];
        }

        if ($res) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['spotList'] = $res;
        } else {
            $this->res['code'] = 1045;
            $this->res['message'] = '结果为空';
        }
    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$promote = new Promote();
$promote->index();












