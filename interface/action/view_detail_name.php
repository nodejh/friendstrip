<?php


/*

var data = {
    "name":"锦里"
};

$.post('action/view_detail_name.php', data, function (res) {
console.log(res);
});


 */

include 'common.class.php';


class View extends Common {

    public $data;


    function __construct() {

        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;


        $name = $get_data['name'];

        $sql = "SELECT * FROM ft_view WHERE name LIKE :name";

        $stmt = $this->db->prepare($sql);
        $stmt->bindValue(':name', '%'.$name.'%', PDO::PARAM_STR);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetchAll();

        foreach($res as $k => $v) {
            $this->res[$k]['bannerList'] = explode(',', $v['picture']);
            $this->res[$k]['info']['name'] = $v['name'];
            $this->res[$k]['info']['describe'] = $v['description'];
            $this->res[$k]['info']['commentNum'] = $v['comment_number'];
            $this->res[$k]['info']['goneNum'] = $v['went_number'];
            $this->res[$k]['info']['wantNum'] = $v['want_number'];
            $this->res[$k]['info']['address'] = $v['location'];
            $this->res[$k]['info']['openTime'] = $v['open_time'];
            $this->res[$k]['info']['playTime'] = $v['play_time'];
            $this->res[$k]['info']['phone'] = $v['phone'];
            $this->res[$k]['info']['price'] = $v['price'];

        }


        if ($res) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
        } else {
            $this->res['code'] = 1065;
            $this->res['message'] = '查询数据库失败';
        }
    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$view = new View();
$view->index();












