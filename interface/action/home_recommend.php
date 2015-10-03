<?php

// 首页推荐
// home_recommend.php


include 'common.class.php';


class Recommon extends Common {

    public $data;


    function __construct() {

        parent::__construct();
        $this->get_banner();
        $this->get_spot();
    }

    // 图片轮播，返回4个广告function
    public function get_banner() {
        $stmt = $this->db->query("SELECT * FROM ft_ad LIMIT 0,4");
        $stmt->setFetchMode(PDO::FETCH_ASSOC);  //设置获取模式
        $res = $stmt->fetchAll();
        if ($res) {
            $this->data['bannerList'] = $res;
        } else {
            $this->data['bannerList'] = 0;
        }
    }


    //推荐景点，返回7个
    public function get_spot() {
        $stmt = $this->db->query("SELECT * FROM ft_spot ORDER BY want_number LIMIT 7");
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $res = $stmt->fetchAll();
        if ($res) {
            $this->data['spotList'] = $res;
        } else {
            $this->data['spotList'] = 0;
        }
    }


    function __destruct() {
        if ($this->data['bannerList'] && $this->data['spotList']) {
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


/*

var data = {};

$.post('action/home_recommend.php', data, function (res) {
  console.log(res);
});

 */

