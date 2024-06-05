drop database library_oop1;

create database library_oop1;

use library_oop1;

CREATE TABLE `nguoiMuon` (
  `IDNguoiMuon` VARCHAR(10) PRIMARY KEY,
  `TenNguoiMuon` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `soDienThoai` varchar(20) DEFAULT NULL
);


CREATE TABLE `book` (
  `BookID` VARCHAR(10) PRIMARY KEY,
  `TieuDeSach` varchar(255) DEFAULT NULL,
  `TacGia` varchar(255) DEFAULT NULL,
  `NamXuatBan` int DEFAULT NULL,
  `SoLuong` int DEFAULT NULL
);

CREATE TABLE `ebook` (
  `eBookID` VARCHAR(10) PRIMARY KEY,
  `TenSach` varchar(255) DEFAULT NULL,
  `TacGia` varchar(255) DEFAULT NULL,
  `NamXuatBan` int DEFAULT NULL,
  `SoLuong` int DEFAULT NULL,
  `DinhDangFile` varchar(255) DEFAULT NULL,
  `KichThuocFile` int DEFAULT NULL
);

CREATE TABLE `muonsach` (
  `borrowID` VARCHAR(10) PRIMARY KEY,
  `TenSach` varchar(255) DEFAULT NULL,
  `BookID` VARCHAR(10),
  `IDNguoiMuon` varchar(255) DEFAULT NULL,
  `NgayMuon` varchar(255) DEFAULT NULL,
  `NgayHenTra` VARCHAR(255),
  CONSTRAINT fk_BookID_muonsach FOREIGN KEY (BookID) REFERENCES Book(BookID),
  CONSTRAINT fk_IDNguoiMuon_muonsach FOREIGN KEY (IDNguoiMuon) REFERENCES nguoimuon(IDNguoiMuon)
	ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `trasach` (
  `ReturnID` VARCHAR(10) PRIMARY KEY,
  `IDNguoiMuon` varchar(10) DEFAULT NULL,
  `BookID` VARCHAR(10),
  `NgayTra` date DEFAULT NULL,
  `KhoanPhat` varchar(255) DEFAULT NULL,
  CONSTRAINT fk_BookID_trasach FOREIGN KEY (BookID) REFERENCES Book(BookID),
  CONSTRAINT fk_IDNguoiMuon_trasach FOREIGN KEY (IDNguoiMuon) REFERENCES nguoimuon(IDNguoiMuon)
  
);
