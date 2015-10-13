<?php

//TODO 私信

include 'common.class.php';


class Release extends Common
{

    public $data;


    function __construct() {
        parent::__construct();
    }


    //当前景点
    public function index() {

        $get_data = $_POST;


    }


    function __destruct()
    {
        $this->die_json($this->res);
    }
}


$recommend = new Release();
$recommend->release();
