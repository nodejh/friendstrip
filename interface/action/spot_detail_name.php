<?php


/*

var data = {
    "name":"峨眉山"
};

$.post('action/spot_detail_name.php', data, function (res) {
console.log(res);
});


 */

include 'common.class.php';


class Spot extends Common {

    public $data;


    function __construct() {

        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;


        $name = $get_data['name'];

        $sql = "SELECT * FROM ft_spot WHERE name LIKE :name";

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

            $sql_views = "SELECT * FROM ft_view WHERE spot_id=:spot_id";
            $stmt_views = $this->db->prepare($sql_views);
            $stmt_views->execute(array(':spot_id' => $v['spot_id']));
            $stmt->setFetchMode(PDO::FETCH_ASSOC);
            $views = $stmt_views->fetchAll();
            $this->res[$k]['info']['viewList'] = $views;
        }


        if ($res) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
        } else {
            $this->res['code'] = 1055;
            $this->res['message'] = '查询数据库失败';
        }
    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$spot = new Spot();
$spot->index();












