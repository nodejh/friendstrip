##接口格式设计

**Basic Regular**

1. 全部使用英文标点


1. 命名方式

    + 接口名称使用下划线命名方式
    + JSON 变量使用小驼峰命名方式

1. URL规则

    + 本地开发时: localhost:3001/interface_name
    
1. 状态码

    1). HTTP 状态码
    
     + 参考 <http://baike.baidu.com/link?url=EJwXUDxQkjZEqtQCA3bC6GES13s-jSyH-YbCWbmQiwJyNpIkLU2DJ9zEw_mnvTg0m_suHMlDxU9nSCprxqWcBa#3>
     
    2) 自定义状态码
    
     *规则:*
      
       *前两位表示 API 编号,后两位表示具体信息; 整数(如1000/2000）为公共状态*
        
       *0成功, 其他失败*
       
       + 1000 密码错误，非法操作
         
       + 1001 已经赞过该景点
       + 1002 已经赞过该游记
       + 1003 已经赞过该评论
        
       + 2001 已经收藏过该景点
       + 2002 已经收藏过该游记
       + 2003 已经收藏过该评论


###用户部分

**1. 点赞景点/游记/评论**

功能简介:
    
    用户对游记景点/游记/评论进行点赞操作，服务端纪录点赞的用户和时间；每个用户对每个景点/游记/评论只能点赞一次。

客户端请求 (POST):

    // URL: localhost:3001/approval
    
    {
      "uid": 1,                     // number - 用户Id  
      "password": "password",   // string - 用户密码   
      "type": 1,                    // number [1,2,3] - 1表示景点的赞,2表示游记的赞,3表示评论的赞
      "approvalId": 111              // number - 景点/游记/评论的 id
    }
    
    
服务端返回:
    
    // success
    {
      "status": 0,        // number 
      "sceneryId": 111    // number - 获赞景点/游记/评论 id
    }
    
    // error
    {
      "status": 404,                // number - 错误状态码 (HTTP状态码/自定义状态码)
      "message": "page not found"   // string - 错误信息
    }




**2. 收藏景点/游记/评论**

功能简介:
    
    用户对游记景点/游记/评论进行收藏操作，服务端纪录进行收藏操作的用户和时间，以及所收藏的景点/游记/评论；每个用户对每个景点/游记/评论只能收藏一次。

客户端请求 (POST):

    // URL: localhost:3001/approval
    
    {
      "uid": 1,            // number - 用户 id 
      "password": "password",   // string - 用户密码   
      "type": 1,           // number [1,2,3] - 1表示景点的收藏, 2表示游记的收藏, 3表示评论的收藏
      "collectionId": 111     // number - 景点/游记/评论的 id
    }
    
    
服务端返回:
    
    // success
    {
      "status": 0,        // number 
      "collectionId": 111    // number - 景点/游记/评论 id
    }
    
    // error
    {
      "status": 404,                // number - 错误状态码 (HTTP状态码/自定义状态码)
      "message": "page not found"   // string - 错误信息
    }
    


**3. 分享景点/游记/评论**

功能简介:
    
    用户对游记景点/游记/评论进行分享操作，服务端纪录进行分享操作的用户和时间，以及所分享的景点/游记/评论；每个用户对每个景点/游记/评论可分享多次。

客户端请求 (POST):

    // URL: localhost:3001/share
    
    {
      "uid": 1,                   // number - 用户 id
      "password": "password",     // string - 用户密码   
      "type": 1,                 // number [1,2,3] - 1表示景点的分享, 2表示游记的分享, 3表示评论的分享
      "shareId": 111              // number - 景点/游记/评论的 id
    }
    
    
服务端返回:
    
    // success
    {
      "status": 0,        // number 
      "shareId": 111    // number - 景点/游记/评论的 id
    }
    
    // error
    {
      "status": 404,                // number - 错误状态码 (HTTP状态码或自定义状态码)
      "message": "page not found"   // string - 错误信息
    }



**4. 评论**

功能简介:
    
    1. 用户对游记景点/游记/评论进行分享操作，服务端纪录进行分享操作的用户和时间，以及所分享的景点/游记/评论；每个用户对每个景点/游记/评论可分享多次。
    
    2. 评论字数限制在 2000

客户端请求 (POST):

    // URL: localhost:3001/comment
    
    {
      "uid": 1,                 // number - 用户 id
      "password": "password",   // string - 用户密码   
      "commentId": 111          // number - 景点/游记/评论的 id
      "parentId": 110           // number [0, ... ] 该评论的父级 id；如果不存在，则为 0
      "content": "this is a comment" // string - 评论内容。字数限制在 2000
    }
    
    
服务端返回:
    
    // success
    {
      "status": 0,        // number 
      "commentId": 111    // number - 景点/游记/评论的 id
    }
    
    // error
    {
      "status": 404,                // number - 错误状态码 (HTTP状态码或自定义状态码)
      "message": "page not found"   // string - 错误信息
    }

 

--- 

6,	关注出行纪录
方式:POST
Key/value:
  URL=www.sdfwef.xxx;  //API接口
  Uid="452345";        //关注用户Id
  Type="FocusNote";    //请求类型
  NoteId="652345";     //游记Id
  From="324242";      //关注人Id

服务器返回:
Success:
   "State":"1".      //状态码
   "NoteId":"54325",  //游记Id

Failure:
   "State":"0",      //状态码
   "ErrorCode":"xxx",   //错误码
   "ErrorMsg":"xxx",    //错误信息

7,	分享-游记
方式:POST
Key/value:
  URL=www.safwef.xxx;   // API接口
  Uid="234215";      //用户Id
  Type="ShareNote";   //请求类型
  NoteId="643253";    //游记Id
  From="412345";      //分享用户Id
服务器返回:
Success:
  "State":"1",       //状态码
  "NoteId":"4541",   // 游记Id

Failure:
  "State":"0",      //状态码
"ErrorCode":"xxx",   //错误码
  "ErrorMsg":"xxx",    //错误信息

8,	点赞-短评
   方式:POST
   Key/value:
      URL=www.asf.xxx;   // API接口
      Uid="324234";      //短评用户Id
      Type="PraiseCom";  //请求类型
 ShortId="54245";    //短评Id
 From="235135";     //点赞人Id

  服务器返回:
  Success:
     "State":"1",       //状态码
     "ComId":"35435",   //短评Id
  
  Failure:
     "State":"0",    //状态码
"ErrorCode":"xxx",  //错误码
"ErrorMsg":"xxx",   //错误信息

9,	分享-短评
方式:POST
Key/value:
  URL:"www.sdfe.xxx";    //API接口
  Uid:"454325";          //用户Id
  Type:"ShareCom";       //请求类型
  ShortId:"435345";       //短评Id
  From:"4534523";        //分享人Id

服务器返回:
Success:
  "State":"1",            //状态码
  "ComId":"4431245",     //短评Id

Failure:
  "State":"0",            //状态码
"ErrorCode":"xxx",      //错误码
  "ErrorMsg":"xxx",       //错误信息

10,	发布出行
方式:POST
Key/value:
   URL:"www.ssdfw.xxx";   //API接口
   Uid:"5435345";         //用户Id
   Type:"ReleaseTrip";      //请求类型
   Info:{"name":"xxxx","sex":"0","releasedate":"xx-xx-xx-xx","destname":"xxx","godate":" 13:20","phonenum":"153424","extracontent":"xxx"};   
            "name":发布用户名；"sex":性别；"releasedate":发表时间
            "destname":"目的地名"；"godate":"出发日期"；
            "phonenum":联系电话；"extracontent":"附加说明"
   
服务器返回:
Success:
   "State":"1",    //状态码
   "TripId":"54623",  //出行Id

Failure:
  "State":"0",    //状态码
"ErrorCode":"xxx",   //错误码
  "ErrorMsg":"xxx",    //错误信息

11,	发布游记
方式:POST
Key/value:
   "URL":"www.sdfw.xxx"   //API 接口
   "Uid":"245345",         //用户Id
   "Type":"ReleaseNote",    //请求类型
   "Info":{"name":"xxx","title":"xxx","date":"2014-03-13-22:21","content":"xxcs","useri
mage ":"xssdf","school":"sdwf","focusnum":"xx","praisenum":"xx","sharenum":"xx" },

"noteimage":{"num":"xx",["ssdf","adsfsd","grews","sdfwfe"]},
注释:    "name":用户名；"title":游记标题；"date":发布时间；"content":内容
           "userimage":用户头像地址；"school":学校；"focusnum":关注人数
           "praisenum":"点赞人数"；"sharenum":分享人数
       "num":游记图片数量；  数组:游记图片地址
服务器返回:
Key/value:
Success:
   "State":"1",
   "NoteId":"234235",
Failure:
  "State":"0",
"ErrorCode":"xxx",
   "ErrorMsg":"xxx",

12,	获取收藏列表
方式:POST
Key/value:
   URL:"www.ssssd.xxx",
   Uid:"23425",
   Type:"GetCollectionList",
   
服务器返回:
Success:
   "State":"1",
   "Uid":"23425",   //用户Id
   "Notenum":"xx",     //列表游记数目
   "Spotnum":"xx",     //列表景点数目
   "Notelist":[
"noteid":"43532","info":["uid":"123","name":"xxx","title":"xxx","date":"2015-02-17-13:20","brief":"xxx","userimage":"www.xsdsx.xxx","school":"xxx","noteimage":"www.ssxs.xxx","focusnu":"xx";"praisenum":"xx","sharenum":"xxx"],
"noteid":"43532","info":["uid":"123","name":"xxx","title":"xxx","date":"2015-02-17-13:20","brief":"xxx","userImage":"www.xsdsx.xxx","school":"xxx","noteimage":"www.ssxs.xxx","focusnu":"xx";"praisenum":"xx","sharenum":"xxx"],
],
   
"Spotlist":[
"spotid":"43524","info":["name":"xxx","bief":"xxx","price","focusnum":"xxx","spotimage":"www.ssdfw.xxx"],
"spotid":"43524","info":["name":"xxx" ,"bief":"xxx","price","focusnum":"xxx","spotimage":"www.ssdfw.xxx"],
]
   注释:
//"collectionId":收藏Id；"Info":收藏信息
   "uid":作者Id；"name":作者名；"title":标题；"date":发表时间；"brief":简介
   "userimage":作者头像；"school":学校；"noteimage":游记图片地址;…….

  //spotid:景点Id；info:信息；name:景点名；brief:简介；price:票价；spotimage:景点图片地址(代表图片)
Failure:
   "State":"0",
"ErrorCode":"xxx",
  "ErrorMsg":"xxx",



13,	获取游记列表
方式:POST
Key/value:
  URL:"www.xxxs.xxx";
  Uid:"234212";
  Type:"GetTravalList";
  
服务器返回:
Success:
  "Sate":"1",
  "Uid":"234212",
  "Num":"xx",    //游记列表数目
  "Notelist":[ 
"noteid":"34213","info":["name":"xxx","title":"xxx","date":"2015-02-17-13:20","content":"xxx","userimage":"xxx","school":"xxx","focusnum":"xxx","praisenum":"xxx","sharenum":"xxx"],
"noteid":"342343","info":["name":"xxx","title":"xxx","date":"2015-02-17-13:20","content":"xxx","userimage":"xxx","school":"xxx","focusnum":"xxx","praisenum":"xxx","sharenum":"xxx"],
"noteid":"34543","info":["name":"xxx","title":"xxx","date":"2015-02-17-13:20","content":"xxx","userimage":"xxx","school":"xxx","focusnum":"xxx","praisenum":"xxx","sharenum":"xxx"]];
    .
       "Noteimage":{"num":"xxx",["xxxxxx","xxxxx","xxxx"]},
        
        注释:
           //noteid:游记Id；info:信息；"name":发布者信息；"title":标题；"date":发布时间； content:内容 ；userimage:发布者图片 ；school:学校…….
           //num: 照片数量     数组:照片地址
     Failure:
        "State":"0",
"ErrorCode":"xxx",
   "ErrorMsg":"xxx",

14,	获取短评列表
方式:POST
Key/value:
   "URL":"34154",
   "Type":"GetShortList",
   
服务器返回:
Success:
   "State":"1",
   "Uid":"34532",
   "Num":"xx",
   "ShortList":[
     "Spotid":"23431","info":["name":"xxxx","date":"2013-02-23-17:43","userimage":"xxxw","content":"xxxx"],
"spotid":"23431","info":["name":"xxxx","date":"2013-02-23-17:43","userimage":"xxxw","content":"xxxx"],
"spotid":"23431","info":["name":"xxxx","date":"2013-02-23-17:43","userimage":"xxxw","content":"xxxx"]
],

             //Spotid:景点Id；  info:信息； name: 短评用户名  
             Date:短评时间 ；   "userimage":短评用户头像 ；content:短评内容
     Failure:
        "State":"0";
      "ErrorCode":"xxx",
   "ErrorMsg":"xxx",

15,	获取赞列表
方式:POST
Key/value:
   "URL":"35451",
   "Type":"GetPraiseList",
   
服务器返回:
Success:
   "state":"1",
   "Uid":"5432",
   "Num":"xx",
   "PraiseList":[
      ["kind":"0","id":"xxx","from":"55432","name":"xxx","date":"xxxx-xx-xx-xx:xx"],
      ["kind":"1","id":"xxx","from":"55432","name":"xxx","date":"xxxx-xx-xx-xx:xx"],
["kind":"0","id":"xxx","from":"55432","name":"xxx","date":"xxxx-xx-xx-xx:xx"],
],
注释:
Kind:0 表示游记, 1表示评论
Id:获赞游记(评论)Id
From:点赞人Id
Name:点赞人用户名
Date:点赞时间
Failure:
   "state":"0";
   "ErrorCode":"xxx",
   "ErrorMsg":"xxx",































系统部分
1,	用户登录
方式:POST
Key/value:
  "Type":"Sign",    
  "Phone":"15799834512",
  "Pwd":"asdfw2",

服务器返回:
Success:
  "State":"1",
  "Uid":"24554213",   //返回用户ID

Failure:
  "State":"0,
   "ErrorCode":"xxx",
   "ErrorMsg":"xxx",

2,	请求验证码
方式:POST
Key/value:
   "Type":"TestCode",
   "Phone":"15622234521",
   
服务器返回:
Success:
   "State":"1",
   "TestCode":"5342",  //返回验证码,并发送到手机

Failure:
   "State":"0",
   "ErrorCode":"xxx",
   "ErrorMsg":"xxx",

3,	注册请求
方式:POST
Key/value:
  "Phone":"14577753678",
  "Pwd":"5ff3243",
  "TestCode":"1356",
  "Province":"湖南",
  "City":"成都",
  "School":"四川大学",
  
服务器返回:
Success:
   "State":"1",
   "Uid":"2355123",   //注册成功,返回用户Id

Failure:
   "State":"0",
   "ErrorCode":"xxx",
   "ErrorMsg":"xxx",


4,	获取用户基本信息
方式:POST
Key/value:
   "Uid":"1234",
   "Type":"getuserinfo",
   
服务器返回:
Success:
"State":"1",
"info":{"name":"用户名","sex":"性别","borndate":"2013-07-12","userimage":"头像地址","school":"学校名","phone":"15608082031","city":"成都","province":"湖南"}

     Failure:
         "State":"0",
         "ErrorCode":"xxxx",
         "ErrorMsg":"xxxx",
5,	推荐景点列表
方式:POST
Key/value:
   "Type":"PromoteList",
   
服务器返回:
Success:
   "State":"1",
   "Num":"xx",  //推荐列表数目
   "PromoteList":[
["SpotId":"3512","name":"xxx","brief":"xxx","price":"34","spotimage":"xxxx","focusnum":"xx",],
["SpotId":"5663","name":"xxx","brief":"xxx","price":"42", "spotimage":"xxxx","focusnum":"xx"],
["SpotId":"5663","name":"xxx","brief":"xxx","price":"42", "spotimage":"xxxx","focusnum":"xx"],
],
   注释:SpotId:景点Id；name:景点名 ；brief:景点简介 
         Price:票价 ；  focusnum: 关注人数  
         Spotimage:推荐图片       
     Failure:
       "State":"0",
  "ErrorCode":"xxx",
  "ErrorMsg":"xxx",

6,	评论请求
方式:POST
Key/value:
   "Type":"SearchCom",
 "ComId":"xxx",
 "SpotId":"xxx",
服务器返回:
Success:
"State":"1",
"ComInfo":{
    "SpotId":"xxx","name":"用户名","date":"2013-11-12","content":"评论内容","userimage":"xxxxx"
},
    
     Failure:
         "State":"0",
         "ErrorCode":"xxx",
         "ErrorMsg":"xxx",
7,	搜索游记
方式:POST
Key/value:
   "Type":"SearchNote",
   "NoteId":"xxxx",
服务器返回:
    Success:
       "State":"1",
       "NoteInfo":
   {"Uid":"xxxx","name":"xxx","title":"xxx","date":"2014-05-12-12:51","brief":"xxxx","userimage":"xxxx","noteimage":"xxxx","focusnum":"xxx","praisenum":"xxx","sharenum":"xxx"}
// info:信息；"name":发布者信息；"title":标题；"date":发布时间； content:内容 ；userimage:发布者图片 ；school:学校……..
     "Noteimage":{"num":"xxx",["xxxxxx","xxxxx","xxxx"]},
               //num: 照片数量     数组:照片地址
        
Failure:
   "State":"0",
   "ErrorCode":"xxxx",
   "ErrorMsg":"xxxx",

8,	搜索景点-景点Id
方式:POST
  Key/value:
   "Type":"SearchSpotId",
   "SpotId":"xxx",

服务器返回:
Success:
   "State":"1",
   "SpotInfo":
{"name":"xxx","brief":"xxx","price":"34","focusnum":"xx",},
  name:景点名；brief:简介；price:价格 ；focusnum:关注人数
"ImageList":{
  "Num":"xx",["xxxxx","xxxxx","xxxx","xxx"]
}
   "Num":数量；   地址

Failure:
   "State":"0",
   "ErrorCode":"xxx",
   "ErrorMsg":"xxx",


9,	搜索景点—景点名
方式:POST
Key/value:
   "Type":"SearchSpotName",
   "Name":"xxx",

服务器返回:
Success:
   "State":"1",
   "SpotInfo":
{"name":"xxx","brief":"xxx","price":"34","focusnum":"xx",},
  name:景点名；brief:简介；price:价格 ；focusnum:关注人数
"ImageList":{
  "Num":"xx",["xxxxx","xxxxx","xxxx","xxx"]
}
   "Num":数量；   地址

Failure:
   "State":"0",
   "ErrorCode":"xxx",
   "ErrorMsg":"xxx",

10,	搜索周边—Id
方式:POST
Key/value:
   "Type":"SearchSurround",
   "SurroundId":"xxxx",

服务器返回:
Success:
   "State":"1",
   "SurroundInfo":{
    "name":"xxx","title":"xxx","image":"xxxx","content":"xxxx",
},
"SurroundImage":{
"Num":"xx",["xxxx","xxxx","xxxx"]},  
Name:活动名称(如:真人Cs,九寨沟一日游)；  title:活动主题；
Image:图片路径 ； content:活动内容
        Num:周边活动图片数量     数组:图片路径
     Failure:
        "State":"0",
        "ErrorCode":"xxx",
   "ErrorMsg":"xxxx",



11,	获取出行详情
方式:POST
Key/value:
  "Type":"GetTrip",
  "TripId":"xxxx",
服务器返回:
Success:
   "State":"1",
  "Num":"xx",
  "TripList":[
   ["uid":"xxxx","name":"xxx","title":"xxx","releasedate":"2013-02-13-22:22","destname":"xxxx","sex":"男","godate":" 2013-02-13-22:22","phonenum":"15608082312","content":"dsdsfe"],
  ["uid":"xxxx","name":"xxx","title","xxx","releasedate":"2013-02-13-22:22","destname":"xxxx","sex":"男","godate":" 2013-02-13-22:22","phonenum":"15608082312","content":"dsdsfe"],
]
        注释:uid:发布者id；"name":发布用户名；"sex":性别；"releasedate":发表时间
             "title":主题
            "destname":"目的地名"；"godate":"出发日期"；
            "phonenum":联系电话；"content":"附加说明"

   Failure:
      "State":"0",
      "ErrorCode":"xxx",
      "ErrorMsg":"xxxx",


12,	热门搜索列表获取
方式:POST
Key/value:
   "Type":"HotSpotList",
   
服务器返回:
Success:
   "State":"1",
"Num":"xxx",
    "NameList":[
   ["name":"xxx","SpotId":"xxxx"],
   ["name":"xxx","SpotId":"xxxx"],
   ["name":"xxx","SpotId":"xxxx"],
],
   Name:景点名 ；  SpotId: 景点Id  
Failure:
   "State":"0",
   "ErrorCode":"xxx",
   "ErrorMsg":"xxx",
  
13,	获取游记列表
方式:POST
Key/value:
   "Type":"NoteList",
   ""
     服务器返回:
     Success:
       "State":"1",
       "Num":"xx",
       "NoteList":[
   ["NoteId":"xxx","Uid":"xxxx","name":"xxx","title":"xxx","date":"2014-05-12-12:51","brief":"xxxx","userimage":"xxxx","noteimage":"xxxx","focusnum":"xxx","praisenum":"xxx","sharenum":"xxx"],
   ["NoteId":"xxx","Uid":"xxxx","name":"xxx","title":"xxx","date":"2014-05-12-12:51","brief":"xxxx","userimage":"xxxx","noteimage":"xxxx","focusnum":"xxx","praisenum":"xxx","sharenum":"xxx"],
   ["NoteId":"xxx","Uid":"xxxx","name":"xxx","title":"xxx","date":"2014-05-12-12:51","brief":"xxxx","userimage":"xxxx","noteimage":"xxxx","focusnum":"xxx","praisenum":"xxx","sharenum":"xxx"],
],
//noteid:游记Id；info:信息；"name":发布者信息；"title":标题；"date":发布时间； content:内容 ；userimage:发布者图片 ；school:学校……..
  
        
Failure:
   "State":"0",
   "ErrorCode":"xxxx",
   "ErrorMsg":"xxxx",


14,	获得出行列表
方式:POST
Key/value:
  "Type":"TripList",
  
服务器返回:
Success:
   "State":"1",
  "Num":"xx",
  "TripList":[
   ["TripId":"xxxx","uid":"xxxx","name":"xxx","title":"主题","releasedate":"2013-02-13-22:22","destname":"xxxx","godate":" 2013-02-13-22:22",],
  ["TripId":"xxxx","uid":"xxxx","name":"xxx","releasedate":"2013-02-13-22:22","destname":"xxxx","sex":"男","godate":" 2013-02-13-22:22",],
]
        注释:
TripId:出行信息Id
uid:发布者id；"name":发布用户名；"releasedate":发表时间
     title:主题
            "destname":"目的地名"；"godate":"出发日期"；

   Failure:
      "State":"0",
      "ErrorCode":"xxx",
      "ErrorMsg":"xxxx",

15,	景点评论列表
方式:POST
Key/value:
   "Type":"SpotComList",

服务器返回:
Success:
   "State":"1",
   "Num":"xx",
   "SpotComList":[
          ["SpotId":"xxx","comid":"xxx","uid":"xxx","userimage":"xxxx","name":"xxx","date":"2013-03-14-12:33","content":"xxxx"],
            ["SpotId":"xxx","comid":"xxx","uid":"xxx","userimage":"xxxx","name":"xxx","date":"2013-03-14-12:33","content":"xxxx"],
],
  注释:SpotId:景点Id；comid:短评Id；uid:评论者Id；name:用户名
        Date:评论时间； "content":评论内容
     Failure:
       "State":"0",
       "ErrorCode":"xxx",
       "ErrorMsg":"xxx",

16,	周边列表
方式:POST
Key/value:
   "Type":"SurroundList",
   "Location":{"Province":"xxx","City":"xxx"},

服务器返回:
Success:
"State":"1",
"Num":"xxx",  //列表项数目
"SurroundList":[
    ["SurroundId":"xxx","name":"xxx","title":"xxx","image":"xxxx"],
["SurroundId":"xxx","name":"xxx","title":"xxx","image":"xxxx"],
["SurroundId":"xxx","name":"xxx","title":"xxx","image":"xxxx"],
],
         注释:name:区名；title:周边活动主题；"image":主题图片地址

Failure:
    "State":"0",
    "ErrorCode":"xx",
    "ErrorMsg":"xxx",

17,	获取城市列表
方式:POST
Key/value:
   "Type":"CityList",
   "Province":"xxx",

服务器返回:
Success:
   "State":"1",
   "Province":"xxx",
   "Num":"xxx",  //城市列表数目
   "CityList":[
   ["CityId":"xxx","name":"xxx"],
["CityId":"xxx","name":"xxx"],
["CityId":"xxx","name":"xxx"],
],

Failure:
   
"State":"0",
    "ErrorCode":"xx",
    "ErrorMsg":"xxx",


18,	获取学校列表
方式:POST
Key/value:
"Type":"SchoolList",
"City":"xxx",

服务器返回:
Success:
    "State":"1",
    "Num":"xxx",    //学校列表数目
    "SchoolList":[
    ["SchoolId":"xxx","name":"xxx"],
    ["SchoolId":"xxx","name":"xxx"],
    ["SchoolId":"xxx","name":"xxx"],
],

   Failure:
 "State":"0",
    "ErrorCode":"xx",
    "ErrorMsg":"xxx",

