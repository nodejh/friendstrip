<?php

set_time_limit(0);
date_default_timezone_set('Asia/Shanghai');
header('content-type:text/html;charset=utf-8');

//include '../../interface/database/dbConfigure.php';
include 'db.class.php';
include 'phpQuery.php';


class Spider extends DB {


    public function main() {
        $i = 0;
        $j = 0;
        $page_province = 'http://www.lvmama.com/lvyou/';
        $province = phpQuery::newDocumentFile($page_province);

        // 省份所在的节点组合
        $dom_province = $province->find('.wy_area_list:eq(0)')->find('a');

        // 遍历所有省节点
        foreach ($dom_province as $key => $v) {
            $i++;

            // 每个省的 url
            $url_province[$key] = $v->getAttribute('href');
            echo '省份' . $i .':' . $url_province[$key] . PHP_EOL;

            // 获取景区列表页面 url
            $province_details = phpQuery::newDocumentFile('http://www.lvmama.com/lvyou/'.$url_province[$key]);
            $page_spot = $province_details->find('.statenav')->find('a:eq(2)')->attr('href');
            echo '景区列表页面'. $i . ':' . $page_spot . PHP_EOL;

            // 景区列表页面
            $spot = phpQuery::newDocumentFile($page_spot);

            // 景区列表节点
            $dom_spot_div = $spot->find('.hot_id');
            // 遍历景区列表节点
            foreach ($dom_spot_div as $v_spot) {
                $j++;

                // 某个景区
                $url_spot = pq($v_spot)->find('dt a')->attr('href');
                echo '景区URL'. $j . ':' . $url_spot . PHP_EOL;

                // 抓取景区详情页，并将景区信息存入数据库
                $spot_id = $this->save_spot($url_spot);

                // 某个景区推荐景点列表
                $view_url_list = pq($v_spot)->find('dd .mt5 a');
                // 遍历某个景区的景点
                foreach ($view_url_list as $view_url_item) {
                    $view_url = pq($view_url_item)->attr('href');
                    echo '景点URL'. $j . ':' . $view_url . PHP_EOL;
                    $view_id = $this->save_view($url, $spot_id);
                }

            }


        }
    }


    /**
     * 保存景区详情
     * @param $url
     * @return bool
     */
    public function save_spot($url) {

        // 景区详情页
        $spot = phpQuery::newDocumentFile($url);

        $data['name'] = $this->remove_space($spot->find('.countryBox.fl h1')->text());
        $data['want_number'] = $this->remove_space($spot->find('.icon_wantGo')->next()->find('span')->text());
        $data['went_number'] = $this->remove_space($spot->find('.icon_Gone')->next()->find('span')->text());
        $data['comment_number'] = 0;
        $data['country'] = $this->remove_space($spot->find('.nav.clearfix .fl a:eq(3)')->text());
        $data['city'] = $this->remove_space($spot->find('.nav.clearfix .fl a:eq(4)')->text());
        $data['description'] = $this->remove_space($spot->find('.cityIntroduce')->text());
        $data['picture'] = '';
        $dom_img_urls = $spot->find('.foucsSmall li img');
        foreach($dom_img_urls as $img_k => $dom_img_url) {
            $img_url = pq($dom_img_url)->attr('to');
            $data['picture'] .= $this->download_img($img_url) . ',';
        }
        $data['picture'] = rtrim($data['picture'], ',');

        $data['name'] = '我';

        $sql = "INSERT INTO ft_spot(name,want_number,went_number,comment_number,country,city,description,picture) VALUES  (:name,:want_number,:went_number,:comment_number,:country,:city,:description,:picture)";

        // 存入数据库
        $stmt = $this->db->prepare($sql);
        $stmt->bindparam(':name', $data['name']);
        $stmt->bindparam(':want_number', $data['want_number']);
        $stmt->bindparam(':went_number', $data['went_number']);
        $stmt->bindparam(':comment_number', $data['comment_number']);
        $stmt->bindparam(':country', $data['country']);
        $stmt->bindparam(':city', $data['city']);
        $stmt->bindparam(':description', $data['description']);
        $stmt->bindparam(':picture', $data['picture']);

        if($stmt->execute()){
            echo "最后插入的ID:".$this->db->lastInsertId();
            return $this->db->lastInsertId();
        } else {
            var_dump('fail to insert to database');
            return false;
        }

    }


    /**
     * 保存景点详情
     * @param $url
     * @return bool|string
     */
    public function save_view($url, $spot_id) {
        // 景点详情页
        $spot = phpQuery::newDocumentFile($url);

        $data['name'] = $this->remove_space($spot->find('.countryBox.fl h1')->text());
        $data['want_number'] = $this->remove_space($spot->find('.icon_wantGo')->next()->find('span')->text());
        $data['went_number'] = $this->remove_space($spot->find('.icon_Gone')->next()->find('span')->text());
        $data['comment_number'] = 0;
        $data['location'] = $this->remove_space($spot->find('.city_mapListBox .map_contentTxt:eq(0)')->text());
        $data['open_time'] = $this->remove_space($spot->find('.city_mapListBox .map_contentTxt:eq(1)')->text());
        $data['play_time'] = $this->remove_space($spot->find('.city_mapListBox .map_contentTxt:eq(2)')->text());
        $data['phone'] = $this->remove_space($spot->find('.city_mapListBox .map_contentTxt:eq(3)')->text());
        $data['price'] = $this->remove_space($spot->find('.city_mapListBox .map_contentTxt:eq(5)')->text());
        $data['description'] = $this->remove_space($spot->find('.countryBox.fl p')->text());
        $data['picture'] = '';
        $dom_img_urls = $spot->find('.foucsSmall li img');
        foreach($dom_img_urls as $img_k => $dom_img_url) {
            $img_url = pq($dom_img_url)->attr('to');
            $data['picture'] .= $this->download_img($img_url) . ',';
        }
        $data['picture'] = rtrim($data['picture'], ',');

        var_dump($data);
        //$data['name'] = 'dadf';
        //$data['want_number'] = 200;
        //$data['went_number'] = 100;
        //$data['comment_number'] = 0;
        //$data['location'] = 'ewrqwrqwre';
        //$data['open_time'] = 'dsafaf';
        //$data['play_time'] = 'daf';
        //$data['phone'] = 'daaerw';
        //$data['price'] = 'dasfwe';
        //$data['description'] = 'dawefsd';
        //$data['picture'] = 'da';


        $sql = "INSERT INTO ft_view(spot_id,location,open_time,play_time,phone,price,picture,want_number,went_number,comment_number,name,description)".
                "VALUES (:spot_id,:location,:open_time,:play_time,:phone,:price,:picture,:want_number,:went_number,:comment_number,:name,:description)";

        // 存入数据库
        $stmt = $this->db->prepare($sql);
        $stmt->bindparam(':spot_id', $spot_id);
        $stmt->bindparam(':location', $data['location']);
        $stmt->bindparam(':open_time', $data['open_time']);
        $stmt->bindparam(':play_time', $data['play_time']);
        $stmt->bindparam(':phone', $data['phone']);
        $stmt->bindparam(':price', $data['price']);
        $stmt->bindparam(':picture', $data['picture']);
        $stmt->bindparam(':name', $data['name']);
        $stmt->bindparam(':want_number', $data['want_number']);
        $stmt->bindparam(':went_number', $data['went_number']);
        $stmt->bindparam(':comment_number', $data['comment_number']);
        $stmt->bindparam(':description', $data['description']);

        var_dump($data);
        if($stmt->execute()){
            echo "最后插入的ID:".$this->db->lastInsertId();
            return $this->db->lastInsertId();
        } else {
            echo 'fail to insert to database' . PHP_EOL;
            var_dump($this->db->errorInfo());
            return false;
        }
    }


    /**
     * 去掉 html 标签和空格
     * @param $string
     * @return string
     */
    public function remove_space($string) {
        $html = htmlspecialchars($string);
        $no_html = strip_tags($html);
        $res = mb_ereg_replace('(　| )+$', '', $no_html);
        //$res = preg_replace('/\s|　/','',$res);
        //$res = mb_convert_encoding($res, "UTF-8", "GBK");
        //$res = mb_convert_encoding($res,'utf-8','gbk');
        return $res;
    }

    //download_img('http://qlogo2.store.qq.com/qzone/393183837/393183837/50');

    /**
     * 下载图片到本地
     * @param $img_url
     * @return string
     */
    public function download_img($img_url) {
        $curl = curl_init($img_url);
        $filename = date('Ymdhis') . '_' . uniqid() . '.jpg';
        curl_setopt($curl,CURLOPT_RETURNTRANSFER,1);
        $imageData = curl_exec($curl);
        curl_close($curl);
        $tp = @fopen('img/'.$filename, 'a');
        fwrite($tp, $imageData);
        fclose($tp);
        return $filename;
    }

}

$spider = new Spider();
//save_spot('http://www.lvmama.com/lvyou/d-chengdu279.html');

//$spider->save_view('http://www.lvmama.com/lvyou/poi/sight-151780.html', 1);

$spider->save_spot('http://www.lvmama.com/lvyou/d-chengdu279.html');


/*
 *
 * 份1:d-sichuan278.html
景区页面1:http://www.lvmama.com/lvyou/scenery/d-sichuan278.html
景区URL1:http://www.lvmama.com/lvyou/d-chengdu279.html
景点URL1:http://www.lvmama.com/lvyou/poi/sight-151780.html
http://www.lvmama.com/lvyou/poi/sight-151780.html
 */