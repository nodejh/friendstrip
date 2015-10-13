<?php


/*

var data = {
    "spotId": 1
};

$.post('action/spot_detail_id.php', data, function (res) {
console.log(res);
});



 */

include 'common.class.php';


class Comment extends Common {

    public $data;


    function __construct() {

        parent::__construct();
    }


    public function index() {

        $get_data = $_POST;

        $sql = "SELECT * FROM ft_spot WHERE spot_id=:spot_id ";

        $stmt = $this->db->prepare($sql);
        $stmt->execute(array(':spot_id' => $get_data['spotId']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetch();

        var_dump($res);

        $spot['banner'] = explode(',', $res['picture']);
        //var_dump($spot);

        $spot['info']['name'] = $res['name'];
        $spot['info']['describe'] = $res['description'];
        $spot['info']['commentNum'] = $res['comment_number'];
        $spot['info']['goneNum'] = $res['went_number'];
        $spot['info']['wantNum'] = $res['want_number'];

        $sql_views = "SELECT * FROM ft_view WHERE spot_id=:spot_id";
        $stmt_views = $this->db->prepare($sql_views);
        $stmt_views->execute(array(':spot_id' => $get_data['spotId']));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $views = $stmt_views->fetchAll();


        if ($res) {
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['bannerList'] = $spot['banner'];
            $this->res['info'] = $spot['info'];
            $this->res['viewList'] = $views;
        } else {
            $this->res['code'] = 1035;
            $this->res['message'] = '查询数据库失败';
        }
    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$comment = new Comment();
$comment->index();












