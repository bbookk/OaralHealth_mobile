-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 07, 2018 at 05:00 PM
-- Server version: 10.1.29-MariaDB
-- PHP Version: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oralhealth`
--

-- --------------------------------------------------------

--
-- Table structure for table `result_status`
--

CREATE TABLE `result_status` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` text COLLATE utf8_thai_520_w2 NOT NULL,
  `teeth_11` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_12` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_13` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_14` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_15` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_16` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_17` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_18` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_21` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_22` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_23` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_24` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_25` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_26` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_27` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_28` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_31` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_32` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_33` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_34` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_35` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_36` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_37` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_38` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_41` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_42` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_43` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_44` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_45` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_46` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_47` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL,
  `teeth_48` text CHARACTER SET utf32 COLLATE utf32_thai_520_w2 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_thai_520_w2;

--
-- Dumping data for table `result_status`
--

INSERT INTO `result_status` (`id`, `name`, `teeth_11`, `teeth_12`, `teeth_13`, `teeth_14`, `teeth_15`, `teeth_16`, `teeth_17`, `teeth_18`, `teeth_21`, `teeth_22`, `teeth_23`, `teeth_24`, `teeth_25`, `teeth_26`, `teeth_27`, `teeth_28`, `teeth_31`, `teeth_32`, `teeth_33`, `teeth_34`, `teeth_35`, `teeth_36`, `teeth_37`, `teeth_38`, `teeth_41`, `teeth_42`, `teeth_43`, `teeth_44`, `teeth_45`, `teeth_46`, `teeth_47`, `teeth_48`) VALUES
(5, 'สุชาดา', '1', '1', '7', 'C', 'E', 'F', 'E', 'F', '1', 'A', '1', '3', '4', 'F', '4', '7', '1', '2', '1', '2', '1', 'F', 'F', '1', 'F', '4', '2', '4', '4', '4', 'F', 'A'),
(9, 'สุเทพ', '4', 'E', 'E', 'C', 'F', 'C', '', 'A', '1', '1', 'F', '9', '1', 'E', '6', '', 'F', 'F', 'F', 'C', '1', 'F', 'F', '4', '4', 'A', '1', 'D', '1', 'F', '4', '1'),
(12, 'book', '0', '1', '7', 'C', '9', '3', 'D', '3', '4', '4', 'D', 'D', '4', '1', '4', 'F', '7', '1', 'F', '7', '3', '3', '3', '3', '7', '7', '7', 'A', 'E', 'D', 'C', '1'),
(18, 'เมธาวี', 'D', '9', '7', '9', 'E', 'C', 'E', '3', '8', '8', '8', 'E', '1', '8', '1', '4', '', '1', '1', '1', '1', '7', '1', '9', '6', '6', 'C', '1', '1', '7', 'E', '4');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `result_status`
--
ALTER TABLE `result_status`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `result_status`
--
ALTER TABLE `result_status`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
