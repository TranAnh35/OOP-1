create database library_oop;
use library_oop;
create table ThuVien(
TenSach varchar(500),
TacGia varchar(500),
NamXuatBan int,
NgayMuon date,
NgayTra date,
DinhDangFile char(50),
KichThuocFile float
);

select * from ThuVien;
# xoá sách
DELETE FROM ThuVien WHERE TenSach = "d"