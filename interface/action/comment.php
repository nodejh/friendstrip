<?php

/*
 *
 *


  {
      "uid":"1",                    // string
      "type":"1",                   // string 1910:景区； 1920：景点
      "spotID":"12341",                // string -景点、景区的ID
      "content":"评论内容"            // string
    };


 *
 *
 *
 */


include 'common.class.php';

/**
 * 评论
 */
class Comment extends Common {

    public $data;


    function __construct() {
        parent::__construct();
    }


    public function index() {
        //$post_data = file_get_contents("php://input");
        //$get_data = json_decode($post_data, true);
        $get_data = $_POST;

        $sql = "INSERT INTO ft_comment(user_id, type, spotview_id, content, date) VALUES (:user_id, :type, :spotview_id, :content, :date)";

        $user_id = $get_data['uid'];
        $content = $get_data['content'];
        $type = $get_data['type'];
        $spotview_id = $get_data['spotID'];
        $date = time();


        $stmt = $this->db->prepare($sql);
        $stmt->bindparam(':user_id', $user_id);
        $stmt->bindparam(':type', $type);
        $stmt->bindparam(':spotview_id', $spotview_id);
        $stmt->bindparam(':content', $content);
        $stmt->bindparam(':date',$date);

        if($stmt->execute()){
            $this->res['code'] = 0;
            $this->res['message'] = 'success';
            $this->res['commentID'] = $this->db->lastInsertId();
        }else{
            $this->db->errorInfo();
            $this->res['code'] = 1065;
            $this->res['message'] = '写入数据库失败';
        }


    }



    function __destruct() {
        $this->die_json($this->res);
    }

}


$comment = new Comment();
$comment->index();