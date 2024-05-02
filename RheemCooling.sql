-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 22, 2023 at 09:18 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eproject1_new`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `email`, `password`, `role`, `status`) VALUES
(1, 'shopfp@gmail.com', '5fxxJot1lXSumxiALaV4xP+0eYY=', 0, 0),
(2, 'admin1@gmail.com', '5fxxJot1lXSumxiALaV4xP+0eYY=', 1, 0),
(3, 'admin2@gmail.com', '5fxxJot1lXSumxiALaV4xP+0eYY=', 1, 0),
(4, 'admin3@gmail.com', '5fxxJot1lXSumxiALaV4xP+0eYY=', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `Id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`Id`, `name`, `parent_id`, `status`) VALUES
(1, 'Điều hòa', 0, 0),
(2, 'Điều hòa tủ đứng', 0, 0),
(3, 'Điều hòa âm trần', 0, 0),
(4, 'Điều hòa nối ống gió', 0, 0),
(5, 'Điều hòa Multi', 0, 0),
(6, 'Điều hòa LG', 1, 0),
(7, 'Điều hòa Samsung', 1, 0),
(8, 'Điều hòa Casper', 1, 0),
(10, 'Điều hòa Daikin', 1, 0),
(11, 'Điều hòa Panasonic', 1, 0),
(12, 'Điều hòa Dairry', 1, 0),
(13, 'Điều hòa Gree', 1, 0),
(14, 'Điều hòa Funiki', 1, 0),
(15, 'Điều hòa Sumikura', 1, 0),
(16, 'Điều hòa Mitsubishi Heavy', 1, 0),
(17, 'Điều hòa Fujitsu', 1, 0),
(18, 'Điều hòa Nagakawa', 1, 0),
(19, 'Điều hòa General', 1, 0),
(20, 'Điều hòa Midea', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `category_product`
--

CREATE TABLE `category_product` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category_product`
--

INSERT INTO `category_product` (`id`, `product_id`, `category_id`) VALUES
(33, 10, 1),
(34, 10, 6),
(35, 1, 3),
(36, 1, 6),
(37, 11, 3),
(38, 11, 6);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `name`, `email`, `phone`, `address`, `password`, `status`) VALUES
(1, 'Hoàng', 'hoang@gmail.com', '0923456789', '123 Thái Thịnh, Hà Nội', '5fxxJot1lXSumxiALaV4xP+0eYY=', 0),
(2, 'Minh', 'minh@gmail.com', '09123123456', '99 Phú Thượng , Tây Hồ, Hà Nội', '5fxxJot1lXSumxiALaV4xP+0eYY=', 0),
(3, 'Liên', 'lien@gmail.com', '0123456789', '285 Đội Cấn, Ba Đình, Hà Nội', '5fxxJot1lXSumxiALaV4xP+0eYY=', 0),
(4, 'Tiên', 'tien@gmail.com', '0123456789', '285 Đội Cấn, Ba Đình, Hà Nội', '5fxxJot1lXSumxiALaV4xP+0eYY=', 0),
(5, 'Phương', 'phuong@gmail.com', '0123456789', '285 Đội Cấn, Ba Đình, Hà Nội', '5fxxJot1lXSumxiALaV4xP+0eYY=', 0);

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `link_image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`id`, `product_id`, `link_image`) VALUES
(41, 10, '/uploads/image/149777254043670102102023.jpg'),
(42, 10, '/uploads/image/149777254043670202102023.jpg'),
(43, 10, '/uploads/image/149777254043670302102023.jpg'),
(44, 1, '/uploads/image/149794288103590102102023.jpg'),
(45, 1, '/uploads/image/149794288103590202102023.jpg'),
(46, 1, '/uploads/image/149794288103590302102023.jpg'),
(47, 11, '/uploads/image/160284970481090103102023.jpg'),
(48, 11, '/uploads/image/160284970481090203102023.jpg'),
(49, 11, '/uploads/image/160284970481090303102023.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `order_buy`
--

CREATE TABLE `order_buy` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `total_price` float NOT NULL,
  `note` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_buy`
--

INSERT INTO `order_buy` (`id`, `customer_id`, `total_price`, `note`, `status`, `time_stamp`) VALUES
(1, 1, 28690000, 'giao vào cuối tuần ', 2, '2023-09-27 10:45:50'),
(2, 1, 70690000, 'giao sáng thứ 7', 2, '2023-10-13 04:53:32'),
(3, 1, 43690000, '', 2, '2023-10-22 16:48:50'),
(4, 1, 89070000, 'giao chủ nhật', 2, '2023-10-22 18:56:55');

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`id`, `order_id`, `product_id`, `price`, `quantity`, `time_stamp`) VALUES
(1, 1, 1, 16690000, 1, '2023-09-27 10:47:12'),
(2, 1, 10, 12000000, 1, '2023-09-27 10:47:12'),
(3, 2, 1, 16690000, 1, '2023-10-13 04:53:32'),
(4, 2, 10, 13000000, 2, '2023-10-13 04:53:32'),
(5, 2, 11, 14000000, 2, '2023-10-13 04:53:32'),
(6, 3, 1, 16690000, 1, '2023-10-22 16:48:50'),
(7, 3, 10, 13000000, 1, '2023-10-22 16:48:50'),
(8, 3, 11, 14000000, 1, '2023-10-22 16:48:50'),
(9, 4, 1, 16690000, 3, '2023-10-22 18:56:55'),
(10, 4, 10, 13000000, 3, '2023-10-22 18:56:55');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `thumbnail` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `description` varchar(255) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `thumbnail`, `price`, `description`, `status`) VALUES
(1, 'Điều hòa LG DUALCOOL™Inverter 1 chiều 9.000 BTU (1HP)|V10APFUV', '/uploads/thumbnail/149742565853400102102023.jpg', 16690000, 'Công nghệ diệt khuẩn UVnano™\nLàm lạnh tức thì\nTiết kiệm năng lượng\nThinQ', 0),
(10, 'test0', '/uploads/thumbnail/149693187497350102102023.jpg', 13000000, 'test0', 0),
(11, 'test1', '/uploads/thumbnail/160284970626640103102023.jpg', 14000000, 'test1', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `category_product`
--
ALTER TABLE `category_product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `order_buy`
--
ALTER TABLE `order_buy`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `category_product`
--
ALTER TABLE `category_product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `order_buy`
--
ALTER TABLE `order_buy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `category_product`
--
ALTER TABLE `category_product`
  ADD CONSTRAINT `category_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `category_product_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`Id`);

--
-- Constraints for table `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `image_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Constraints for table `order_buy`
--
ALTER TABLE `order_buy`
  ADD CONSTRAINT `order_buy_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`Id`);

--
-- Constraints for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order_buy` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
