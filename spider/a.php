
<?php
//链接数据库
//header("content-type:text/html; charset=GBK");
mysql_connect("localhost","root","root")or die("mysql链接失败");
mysql_select_db("friendstrip")or die("db链接失败");//度娘一下
mysql_query("set names 'utf8'");


include 'phpQuery.php'; 

set_time_limit(0);
error_reporting(E_ALL ^ E_NOTICE);

//逻辑
$provinceUrl=filter_provinceUrl();

foreach ($provinceUrl as $li) {

    $cityUrl=filter_cityUrl($li);

    foreach ($cityUrl as $a) {

       $viewUrl=filter_ft_spot($a);

     //  print_r($viewUrl);

       foreach ($viewUrl as $c) {

               // sleep(2);
                filter_ft_view($c);
                ob_flush();
           }
    }
}

// $b=filter_cityUrl();
// print_r($b);
// $a="http://www.lvmama.com/lvyou/d-chengdu279.html";
// $f=filter_ft_spot($a);
//        foreach($f as $li){ 
//                 sleep(2);
//                filter_ft_view($li);
//                ob_flush();

//             }




//获取景区信息,返回景点链接,ok
function filter_ft_spot($link_spot){

    phpQuery::newDocumentFile($link_spot); 

             //景点描述
            $description=pq(".wy_state_topNav")->find('p')->eq(1);

     

            //景区图片链接
            $link_picture="";
            $all=pq(".wy_state_focusBox .foucsSmall")->find('li');
            foreach($all as $li){ 
                 $f=pq($li)->find('img')->attr("to");
                 $link_picture=$link_picture.",".$f;
            }
            $city=pq(".wy_state_topNav")->find('p:first')->find('a')->eq(4)->text();
        



            //景区二级链接
            $l=pq(".wy_state_topNav")->find('div')->eq(10)->find('a')->eq(2)->attr("href");
            //$l=pq(".wy_state_topNav")->find('a')->eq(10)->attr("href");
         
	        phpQuery::newDocumentFile($l); 

	        //景区名称
            $name=pq(".wy_state_topNav")->find('h1')->text();

            //景区所属地
             $province=pq(".wy_state_topNav")->find('p:first')->find('a')->eq(3)->text();
             // $province= iconv("UTF-8","GBK",  $province);

             $name=strip_tags($name);
             $province=strip_tags($province);
             $city=strip_tags($city);
             $description=strip_tags($description);
             $link_picture=strip_tags($link_picture);
    


        $sql = "INSERT INTO `ft_spot` (spot_id, name,country,city,description,picture) VALUES (null, '$name','$province','$city', '$description','$link_picture')";
         $result = mysql_query($sql);
         $name=iconv("UTF-8","GBK",  $name);
         if($result){
            echo "----------------".$name."-------------------scuess".PHP_EOL;
            }else{
            die(mysql_error());
           }
            
            //下层景点链接
            $list=pq(".city_searchList")->find('dt');
                $a=array();
                foreach($list as $li){    

                 $link= pq($li)->find('a')->eq(0)->attr("href");
                    
                 $link = iconv("UTF-8","GBK",  $link);
                 array_push($a, $link);
                    
                  } 
                  return $a;

}

//获取景点信息
function filter_ft_view($link_view){
	

		phpQuery::newDocumentFile($link_view); 

		    //景点名称
            $name=pq(".wy_state_topNav")->find('h1')->text();
            
            //景点简介
            $description=pq(".mainList")->find('dl:first')->text();
           
            
            //景点基本信息
		   $ft_view=array();
		   $arr=pq(".city_mapListBox")->find('p:odd'); //pq类似于jquery的选择器$()，这里找到class为postTitle的元素
            foreach($arr as $li){ 
            	$f=pq($li)->text();
                // $f = iconv("UTF-8","GBK",  $f);
                array_push($ft_view, $f);
            }
            //图片链接
      
            $all=pq(".foucsSmall")->find('ul li')->find('img');
            $link_picture="";
            foreach($all as $li){ 
            	 $f=pq($li)->attr("to"); 
                 $link_picture=$link_picture.",".$f;
            }
          
           
            

	   
	   //echo($location);
     //   var_dump($ft_view);
        $link_picture=strip_tags($link_picture);
        $location=strip_tags($ft_view[0]);
	    $open_time=strip_tags($ft_view[1]);
	    $play_time=strip_tags($ft_view[2]);
	    $phone=strip_tags($ft_view[3]);
	    $price=strip_tags($ft_view[5]);

	  
        $sql = "INSERT INTO `ft_view` (view_id, location, open_time, play_time, phone, price, name, description,picture) VALUES (null, '$location', '$open_time', '$play_time', '$phone', '$price', '$name', '$description','$link_picture')";
         $result = mysql_query($sql);
         $name= iconv("UTF-8","GBK",  $name);
         if($result){
            echo "--------------------------------------".$name."-------------------success".PHP_EOL;
            }else{
            die(mysql_error());
           }

		
}

//获取省级链接
function filter_provinceUrl(){
	
	phpQuery::newDocumentFile('http://www.lvmama.com/lvyou/'); 
	$artlist = pq(".wy_area_list:first")->find('a'); 
     $a=array();

	foreach($artlist as $li){    

		$link= pq($li)->attr("href");
		
		$link = iconv("UTF-8","GBK",  $link);
		$link=  "http://www.lvmama.com/lvyou/".$link;
		array_push($a, $link);
		
		 } 
         echo "--------get provinceUrl success-----------".PHP_EOL;
		return $a;

		
}
//获取景区链接
function filter_cityUrl($link_provice){


            phpQuery::newDocumentFile($link_provice); 
            $l=pq(".wy_state_topNav")->find('div')->eq(9)->find('a')->eq(2)->attr("href");
                     
            phpQuery::newDocumentFile($l); 
                //下层景点链接
            $list=pq(".wy_state_model ")->find('dd');
            // echo $list."nihai";
                 $a=array();
                foreach($list as $li){    

                 $link= pq($li)->find('a:first')->attr("href");
                    
              
                 array_push($a, $link);
                    
                  } 
                 //  print_r($a) ;
                  echo "--------get cityUrl success-----------".PHP_EOL;

                  return $a;

}




?>