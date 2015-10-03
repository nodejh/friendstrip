<?php


// 6、加入出行（拼车）
//
// release.php


/*



var data = {
  "user_id": 2, // 加入者ID
  "trip_id": 1, //trip id
  "total_people": 1, //当前总人数，即用户当前看到的总人数
};


$.post('action/join_trip.php', data, function (res) {
console.log(res);
});


{"join_id":"10","code":0,"message":"success"}


 */

include 'common.class.php';


class Recommon extends Common {

    public $data;


    function __construct() {
        parent::__construct();
    }


    //当前景点
    public function release() {

        // 记录出行
        $sql_insert = "INSERT INTO ft_join(user_id, trip_id, time) VALUES (:user_id, :trip_id, :time)";
        $stmt_insert = $this->db->prepare($sql_insert);
        $time = time();
        $stmt_insert->bindparam(':user_id', $_POST['user_id']);
        $stmt_insert->bindparam(':trip_id', $_POST['trip_id']);
        $stmt_insert->bindparam(':time', $time);

        //$join_id = $stmt_insert->execute();

        if ($stmt_insert->execute()) {

            $this->res['join_id'] = $this->db->lastInsertId();

            // 更新出行表信息
            $sql_update = "UPDATE ft_trip SET people_hadnum = :people_hadnum WHERE trip_id = :trip_id";

            $people_hadnum = $_POST['total_people'] + 1;

            $stmt_update = $this->db->prepare($sql_update);
            $stmt_update->bindparam(':trip_id', $_POST['trip_id']);
            $stmt_update->bindparam(':people_hadnum', $people_hadnum);

            if($stmt_update->execute()){
                $this->res['code'] = 0;
                $this->res['message'] = 'success';

            }else{
                $this->db->errorInfo();
                $this->res['code'] = -1;
                $this->res['message'] = 'fail to insert to database';
            }


        }  else {
            $this->db->errorInfo();
            $this->res['code'] = -1;
            $this->res['message'] = 'fail to insert to database';
        }







    }


    function __destruct() {
        $this->die_json($this->res);
    }
}


$recommend = new Recommon();
$recommend->release();



