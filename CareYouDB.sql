-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 10, 2024 at 03:20 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `CareYouDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `transactionId` int(11) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `username` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`transactionId`, `content`, `username`, `amount`) VALUES
(1, 'Good Luck!', 'Anonymous', 300000);

-- --------------------------------------------------------

--
-- Table structure for table `donations`
--

CREATE TABLE `donations` (
  `transactionId` int(11) NOT NULL,
  `paymentMethod` varchar(255) NOT NULL,
  `hasComment` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`transactionId`, `paymentMethod`, `hasComment`) VALUES
(1, 'Bank Transfer', 1),
(2, 'Digital Wallet', 0);

-- --------------------------------------------------------

--
-- Table structure for table `programs`
--

CREATE TABLE `programs` (
  `programId` int(11) NOT NULL,
  `fundraiserId` int(11) NOT NULL,
  `programTitle` varchar(255) NOT NULL,
  `fundraiserName` varchar(255) DEFAULT NULL,
  `beneficiaryName` varchar(255) DEFAULT NULL,
  `programDesc` varchar(255) DEFAULT NULL,
  `programTarget` int(11) DEFAULT NULL,
  `startDate` datetime(6) NOT NULL,
  `programRaised` int(11) DEFAULT NULL,
  `withdrawn` int(11) DEFAULT NULL,
  `programStatus` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `programs`
--

INSERT INTO `programs` (`programId`, `fundraiserId`, `programTitle`, `fundraiserName`, `beneficiaryName`, `programDesc`, `programTarget`, `startDate`, `programRaised`, `withdrawn`, `programStatus`) VALUES
(1, 1, 'f', 'e', 'f', 'f', 100000, '2024-12-08 00:00:00.000000', 350000, 0, 'Completed');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transactionId` int(11) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `transactionDate` datetime(6) NOT NULL,
  `amount` int(11) NOT NULL,
  `transactionType` varchar(31) NOT NULL,
  `programId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`transactionId`, `userId`, `transactionDate`, `amount`, `transactionType`, `programId`) VALUES
(1, 2, '2024-12-08 00:00:00.000000', 300000, 'Donation', 1),
(2, 2, '2024-12-10 00:00:00.000000', 50000, 'Donation', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` int(11) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `userEmail` varchar(255) NOT NULL,
  `userPassword` varchar(255) NOT NULL,
  `joinDate` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `userName`, `userEmail`, `userPassword`, `joinDate`) VALUES
(1, 'jsap', 'julian@gmail.com', '123456', '2024-12-07 00:00:00.000000'),
(2, 'justin@gmail.com', 'justin@gmail.com', 'jus123', '2024-12-08 00:00:00.000000'),
(3, 'test', 'test@gmail.com', 'test123', '2024-12-09 00:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `withdrawals`
--

CREATE TABLE `withdrawals` (
  `transactionId` int(11) NOT NULL,
  `withdrawMethod` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`transactionId`);

--
-- Indexes for table `donations`
--
ALTER TABLE `donations`
  ADD PRIMARY KEY (`transactionId`);

--
-- Indexes for table `programs`
--
ALTER TABLE `programs`
  ADD PRIMARY KEY (`programId`),
  ADD KEY `fundraiserId` (`fundraiserId`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transactionId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `programId` (`programId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `UK33uo7vet9c79ydfuwg1w848f` (`userEmail`);

--
-- Indexes for table `withdrawals`
--
ALTER TABLE `withdrawals`
  ADD PRIMARY KEY (`transactionId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `programs`
--
ALTER TABLE `programs`
  MODIFY `programId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transactionId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`transactionId`) REFERENCES `transactions` (`transactionId`);

--
-- Constraints for table `donations`
--
ALTER TABLE `donations`
  ADD CONSTRAINT `donations_ibfk_1` FOREIGN KEY (`transactionId`) REFERENCES `transactions` (`transactionId`);

--
-- Constraints for table `programs`
--
ALTER TABLE `programs`
  ADD CONSTRAINT `FKl4uccicqsxyu0rsxtpoy41m48` FOREIGN KEY (`fundraiserid`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `programs_ibfk_1` FOREIGN KEY (`fundraiserId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `FK3ssmn9suneo436wk56y7g7ca6` FOREIGN KEY (`programid`) REFERENCES `programs` (`programid`),
  ADD CONSTRAINT `FKf4xayc06sikj4iwfrfi38o4eh` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`programId`) REFERENCES `programs` (`programId`);

--
-- Constraints for table `withdrawals`
--
ALTER TABLE `withdrawals`
  ADD CONSTRAINT `withdrawals_ibfk_1` FOREIGN KEY (`transactionId`) REFERENCES `transactions` (`transactionId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
