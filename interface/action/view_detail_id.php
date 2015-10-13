<?php


/*

var data = {
    "viewId": 100
};

$.post('action/view_detail_id.php', data, function (res) {
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


        $view_id = $get_data['viewId'];

        $sql = "SELECT * FROM ft_view WHERE view_id=:view_id";

        $stmt = $this->db->prepare($sql);
        $stmt->bindValue(':view_id', $view_id, PDO::PARAM_STR);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $v = $stmt->fetch();

        var_dump($v);

        $this->res['bannerList'] = explode(',', $v['picture']);
        $this->res['info']['name'] = $v['name'];
        $this->res['info']['describe'] = $v['description'];
        $this->res['info']['commentNum'] = $v['comment_number'];
        $this->res['info']['goneNum'] = $v['went_number'];
        $this->res['info']['wantNum'] = $v['want_number'];
        $this->res['info']['address'] = $v['location'];
        $this->res['info']['openTime'] = $v['open_time'];
        $this->res['info']['playTime'] = $v['play_time'];
        $this->res['info']['phone'] = $v['phone'];
        $this->res['info']['price'] = $v['price'];


        if ($v) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
        } else {
            $this->res['code'] = 1075;
            $this->res['message'] = '查询数据库失败';
        }
    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$view = new View();
$view->index();












