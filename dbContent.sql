-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主機： localhost:8889
-- 產生時間： 2023 年 01 月 28 日 06:13
-- 伺服器版本： 5.7.34
-- PHP 版本： 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫: `nsy`
--

-- --------------------------------------------------------

--
-- 資料表結構 `systemnoticemessage`
--

CREATE TABLE `systemnoticemessage` (
  `MessageId` int(11) NOT NULL,
  `MessageContent` char(255) NOT NULL,
  `ReceiverId` int(11) NOT NULL,
  `MessageRead` char(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `systemnoticemessage`
--

INSERT INTO `systemnoticemessage` (`MessageId`, `MessageContent`, `ReceiverId`, `MessageRead`) VALUES
(1, '請盡快安排訂單#220101574出貨。', 1, 'N'),
(2, '#220154879訂單已完成撿貨及出貨。', 2, 'N'),
(3, '庫存商品牛奶貝已向XXX供應商自動補貨，等待XXX供應商確認。', 3, 'N'),
(4, '收到XXX廠商的鱸魚訂單，請於2022/09/08 10:30前進行訂單確認。', 1, 'N'),
(5, '訂單#220154666的貨品已送達採購商。', 1, 'N');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `systemnoticemessage`
--
ALTER TABLE `systemnoticemessage`
  ADD PRIMARY KEY (`MessageId`),
  ADD KEY `NoticeFk` (`ReceiverId`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `systemnoticemessage`
--
ALTER TABLE `systemnoticemessage`
  MODIFY `MessageId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `systemnoticemessage`
--
ALTER TABLE `systemnoticemessage`
  ADD CONSTRAINT `NoticeFk` FOREIGN KEY (`ReceiverId`) REFERENCES `accounts` (`AccountId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
