-- phpMyAdmin SQL Dump
-- version 4.4.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-08-23 11:34:58
-- 服务器版本： 5.7.7-rc
-- PHP Version: 5.5.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `friendstrip`
--

-- --------------------------------------------------------

--
-- 表的结构 `ft_ad`
--

CREATE TABLE IF NOT EXISTS `ft_ad` (
  `ad_id` int(11) NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `picture` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '[1.jpg,2.jpg]'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='首页轮播图';

-- --------------------------------------------------------

--
-- 表的结构 `ft_comment`
--

CREATE TABLE IF NOT EXISTS `ft_comment` (
  `comment_id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL COMMENT '类型.1 景区  2 景点',
  `content` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '内容',
  `user_id` int(11) NOT NULL COMMENT '评论者ID',
  `spotview_id` int(11) NOT NULL COMMENT '所属景区（景点）ID',
  `date` int(11) NOT NULL COMMENT '评论时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='评论';

-- --------------------------------------------------------

--
-- 表的结构 `ft_join`
--

CREATE TABLE IF NOT EXISTS `ft_join` (
  `join_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL COMMENT '出行 ID',
  `user_id` int(11) NOT NULL COMMENT '加入者人ID',
  `time` int(11) NOT NULL COMMENT '加入时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='出行信息的加入者人';

-- --------------------------------------------------------

--
-- 表的结构 `ft_message`
--

CREATE TABLE IF NOT EXISTS `ft_message` (
  `message_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL COMMENT '上一条私信id',
  `senduser_id` int(11) NOT NULL COMMENT '发送者',
  `reciveuser_id` int(11) NOT NULL COMMENT '收接者',
  `content` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '私信内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `ft_spot`
--

CREATE TABLE IF NOT EXISTS `ft_spot` (
  `spot_id` int(11) NOT NULL COMMENT '景区ID',
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '景区名',
  `view_number` int(11) NOT NULL COMMENT '子景点数',
  `want_number` int(11) NOT NULL COMMENT '想去数',
  `went_number` int(11) NOT NULL COMMENT '去过数',
  `comment_number` int(11) NOT NULL COMMENT '评论数',
  `country` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '所属省份',
  `city` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '所属城市',
  `picture` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '图册 [1.jpg,2,jpg,3.jpg]',
  `description` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '景区描述'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `ft_token`
--

CREATE TABLE IF NOT EXISTS `ft_token` (
  `token_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `token_value` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `ft_trip`
--

CREATE TABLE IF NOT EXISTS `ft_trip` (
  `trip_id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL COMMENT '1  表示景区（城市） 2 表示景点   {因为景点、景区都有出行记录和发布出行的功能，12统一表现为游玩}  3表示拼车',
  `spotview_id` int(11) NOT NULL DEFAULT '0' COMMENT '出行来源景区（景点）ID; 0 为直接发布',
  `start_school` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '始发学校',
  `destination` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '目的地',
  `start_time` int(11) NOT NULL COMMENT '出发时间',
  `date` int(11) NOT NULL COMMENT '发起时间',
  `more_info` varchar(1000) COLLATE utf8_unicode_ci NOT NULL COMMENT '附加信息',
  `people_number` int(11) NOT NULL COMMENT '期望人数',
  `people_hadnum` int(11) NOT NULL COMMENT '已有人数'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='出行，游玩/拼车';

-- --------------------------------------------------------

--
-- 表的结构 `ft_user`
--

CREATE TABLE IF NOT EXISTS `ft_user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `phone` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '手机',
  `country` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '省份',
  `city` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市',
  `school` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '学校名',
  `campus` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '校区',
  `grade` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '年级',
  `avatar` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '头像[1.jpg]',
  `gender` tinyint(1) NOT NULL COMMENT '性别 1boy,2girl',
  `date` int(11) NOT NULL COMMENT '注册时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息';

-- --------------------------------------------------------

--
-- 表的结构 `ft_view`
--

CREATE TABLE IF NOT EXISTS `ft_view` (
  `view_id` int(11) NOT NULL COMMENT '景点ID',
  `spot_id` int(11) NOT NULL COMMENT '景区ID',
  `location` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '详细地址',
  `open_time` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '开放时间',
  `play_time` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '游玩时间',
  `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '联系电话',
  `price` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '门票价格',
  `picture` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '图片名称，用英文逗号隔开；如[1.jpg,2.jpg]',
  `want_numebr` int(11) NOT NULL COMMENT '想去数',
  `went_number` int(11) NOT NULL COMMENT '去过数',
  `comment_number` int(11) NOT NULL COMMENT '评论数',
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '景点名',
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '景点简介'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='景点表';

-- --------------------------------------------------------

--
-- 表的结构 `ft_wantgo`
--

CREATE TABLE IF NOT EXISTS `ft_wantgo` (
  `wantgo_number` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL COMMENT '1 表示想去景点，2 表示想去景区',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `time` int(11) NOT NULL COMMENT '点击想去的时间',
  `gps` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '该人点击时的GPS坐标'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='想去';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ft_ad`
--
ALTER TABLE `ft_ad`
ADD PRIMARY KEY (`ad_id`);

--
-- Indexes for table `ft_comment`
--
ALTER TABLE `ft_comment`
ADD PRIMARY KEY (`comment_id`);

--
-- Indexes for table `ft_join`
--
ALTER TABLE `ft_join`
ADD PRIMARY KEY (`join_id`);

--
-- Indexes for table `ft_message`
--
ALTER TABLE `ft_message`
ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `ft_spot`
--
ALTER TABLE `ft_spot`
ADD PRIMARY KEY (`spot_id`);

--
-- Indexes for table `ft_token`
--
ALTER TABLE `ft_token`
ADD PRIMARY KEY (`token_id`);

--
-- Indexes for table `ft_trip`
--
ALTER TABLE `ft_trip`
ADD PRIMARY KEY (`trip_id`);

--
-- Indexes for table `ft_user`
--
ALTER TABLE `ft_user`
ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `ft_view`
--
ALTER TABLE `ft_view`
ADD PRIMARY KEY (`view_id`);

--
-- Indexes for table `ft_wantgo`
--
ALTER TABLE `ft_wantgo`
ADD PRIMARY KEY (`wantgo_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ft_ad`
--
ALTER TABLE `ft_ad`
MODIFY `ad_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ft_comment`
--
ALTER TABLE `ft_comment`
MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ft_join`
--
ALTER TABLE `ft_join`
MODIFY `join_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ft_message`
--
ALTER TABLE `ft_message`
MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ft_spot`
--
ALTER TABLE `ft_spot`
MODIFY `spot_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景区ID';
--
-- AUTO_INCREMENT for table `ft_token`
--
ALTER TABLE `ft_token`
MODIFY `token_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ft_trip`
--
ALTER TABLE `ft_trip`
MODIFY `trip_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ft_user`
--
ALTER TABLE `ft_user`
MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ft_view`
--
ALTER TABLE `ft_view`
MODIFY `view_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景点ID';
--
-- AUTO_INCREMENT for table `ft_wantgo`
--
ALTER TABLE `ft_wantgo`
MODIFY `wantgo_number` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
