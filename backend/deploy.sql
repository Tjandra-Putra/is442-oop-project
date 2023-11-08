SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `gs` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gs`;

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `date` datetime(6) NOT NULL,
  `ticker` varchar(255) NOT NULL,
  `adj_close_price` double DEFAULT NULL,
  `high_price` double DEFAULT NULL,
  `low_price` double DEFAULT NULL,
  `open_price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_token`
--

CREATE TABLE `password_reset_token` (
  `token_id` bigint(20) NOT NULL,
  `expiration_time` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `portfolio`
--

CREATE TABLE `portfolio` (
  `portfolio_id` bigint(20) NOT NULL,
  `capital_amount` double NOT NULL,
  `description` varchar(255) NOT NULL,
  `portfolio_name` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `portfolio_sequence`
--

CREATE TABLE `portfolio_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `portfolio_stock`
--

CREATE TABLE `portfolio_stock` (
  `buy_date` datetime(6) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `ticker` varchar(255) NOT NULL,
  `portfolio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `ticker` varchar(255) NOT NULL,
  `stock_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stock_info`
--

CREATE TABLE `stock_info` (
  `ticker` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `industry` varchar(255) NOT NULL,
  `sector` varchar(255) NOT NULL,
  `today_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_sequence`
--

CREATE TABLE `user_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`date`,`ticker`),
  ADD KEY `FKd44m1c3mkuis4bii20adeyyxj` (`ticker`);

--
-- Indexes for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  ADD PRIMARY KEY (`token_id`),
  ADD UNIQUE KEY `UK_f90ivichjaokvmovxpnlm5nin` (`user_id`);

--
-- Indexes for table `portfolio`
--
ALTER TABLE `portfolio`
  ADD PRIMARY KEY (`portfolio_id`),
  ADD KEY `FKclxr2fqkko1a1kjw4pvijvej3` (`user_id`);

--
-- Indexes for table `portfolio_stock`
--
ALTER TABLE `portfolio_stock`
  ADD PRIMARY KEY (`portfolio_id`,`ticker`),
  ADD KEY `FKp0dihgdgxl5pxd6g14tbp1ptw` (`ticker`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`ticker`);

--
-- Indexes for table `stock_info`
--
ALTER TABLE `stock_info`
  ADD PRIMARY KEY (`ticker`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  MODIFY `token_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `FKd44m1c3mkuis4bii20adeyyxj` FOREIGN KEY (`ticker`) REFERENCES `stock` (`ticker`);

--
-- Constraints for table `password_reset_token`
--
ALTER TABLE `password_reset_token`
  ADD CONSTRAINT `FK5lwtbncug84d4ero33v3cfxvl` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `portfolio`
--
ALTER TABLE `portfolio`
  ADD CONSTRAINT `FKclxr2fqkko1a1kjw4pvijvej3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `portfolio_stock`
--
ALTER TABLE `portfolio_stock`
  ADD CONSTRAINT `FK7x1vm1n2o0sho0korvsx8jw1c` FOREIGN KEY (`portfolio_id`) REFERENCES `portfolio` (`portfolio_id`),
  ADD CONSTRAINT `FKp0dihgdgxl5pxd6g14tbp1ptw` FOREIGN KEY (`ticker`) REFERENCES `stock` (`ticker`);

--
-- Constraints for table `stock_info`
--
ALTER TABLE `stock_info`
  ADD CONSTRAINT `FK7kl6qkqvfxrdwgejwxlfqawjw` FOREIGN KEY (`ticker`) REFERENCES `stock` (`ticker`);
COMMIT;

