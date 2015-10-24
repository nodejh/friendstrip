<?php


//17、获取搜索推荐（所有景区、热门景点）
//
// search_recommend.php
// 根据当前地理位置来得到推荐的景点
// 返回十个推荐景点


/*

var data = {
    city: '成都'
};

$.post('action/search_recommend.php', data, function (res) {
console.log(res);
});


 */

include 'common.class.php';


class Recommon extends Common {

    public $data;
    public $num; // 推荐景点个数


    function __construct() {

        parent::__construct();
        $this->num = 10;
    }


    //当前景点
    public function get_spot() {

        $get_data = $_POST;

        $sql = 'SELECT spot_id FROM ft_spot WHERE name LIKE :name ORDER BY want_number LIMIT 1';
        $stmt = $this->db->prepare($sql);
        $stmt->bindValue(':name', '%'.$get_data['city'].'%', PDO::PARAM_STR);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetch();

        if ($res) {
             return $res;
        } else {
            return  '1';
        }
    }


    // 推荐景区
    public function get_views() {
        $spot_id = $this->get_spot();

        var_dump($spot_id);
        die();

        $stmt = $this->db->prepare("select * from ft_view where spot_id=:spot_id LIMIT ".$this->num);
        $stmt->execute(array(':spot_id'=> $spot_id));
        $stmt->setFetchMode(PDO::FETCH_ASSOC);  //设置获取模式
        $res = $stmt->fetchAll();

        if ($res) {
            $this->data['spotList'] = $res;
        } else {
            $this->data['spotList'] = 0;
        }
    }


    function __destruct() {
        if ($this->data['spotList']) {
            $this->data['code'] = 0;
            $this->data['message'] = 'success';
            $this->die_json($this->data);
        } else {
            $this->data['code'] = -1;
            $this->data['message'] = 'fail';
            $this->die_json($this->data);
        }
    }
}


$recommend = new Recommon();
$recommend->get_views();



