-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 04, 2018 at 09:48 PM
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
-- Database: `std`
--

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `studentID` int(15) UNSIGNED NOT NULL,
  `schoolName` text COLLATE utf32_thai_520_w2,
  `schoolAddr` text COLLATE utf32_thai_520_w2,
  `studentName` text COLLATE utf32_thai_520_w2,
  `gender` text COLLATE utf32_thai_520_w2,
  `dateOfBirth` varchar(20) COLLATE utf32_thai_520_w2 DEFAULT NULL,
  `studentAddr` text COLLATE utf32_thai_520_w2,
  `nationality` text COLLATE utf32_thai_520_w2,
  `religion` text COLLATE utf32_thai_520_w2,
  `dadName` text COLLATE utf32_thai_520_w2,
  `momName` text COLLATE utf32_thai_520_w2,
  `dadStatus` text COLLATE utf32_thai_520_w2,
  `momStatus` text COLLATE utf32_thai_520_w2,
  `dadJob` text COLLATE utf32_thai_520_w2,
  `momJob` text COLLATE utf32_thai_520_w2,
  `parentName` text COLLATE utf32_thai_520_w2,
  `parentTel` text COLLATE utf32_thai_520_w2,
  `parentAddr` text COLLATE utf32_thai_520_w2,
  `teacherName` text COLLATE utf32_thai_520_w2,
  `masterName` text COLLATE utf32_thai_520_w2,
  `decayNum` varchar(20) COLLATE utf32_thai_520_w2 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_thai_520_w2;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentID`, `schoolName`, `schoolAddr`, `studentName`, `gender`, `dateOfBirth`, `studentAddr`, `nationality`, `religion`, `dadName`, `momName`, `dadStatus`, `momStatus`, `dadJob`, `momJob`, `parentName`, `parentTel`, `parentAddr`, `teacherName`, `masterName`, `decayNum`) VALUES
(1, 'book', '', '', '', '0000-00-00', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '0'),
(2, 'test', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, NULL, NULL, NULL, NULL, 'd', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'd'),
(4, 's', NULL, NULL, NULL, 's', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 's');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `studentID` int(15) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
