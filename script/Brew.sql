CREATE DATABASE Brew
USE Brew;
GO

-- =============================
-- 1️ BẢNG KHÁCH HÀNG
-- =============================
CREATE TABLE Customer (
    customerId NVARCHAR(10) PRIMARY KEY,
    firstName NVARCHAR(50) NOT NULL,
    lastName NVARCHAR(50) NOT NULL,
    phoneNumber NVARCHAR(20),
    email VARCHAR(100),
    sex BIT  -- 1 = Nam, 0 = Nữ
);
GO

CREATE TRIGGER trg_TuDongTaoMaKH
ON Customer
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SoCuoi INT;
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(customerId, 3, LEN(customerId)) AS INT)), 0)
    FROM Customer;

    INSERT INTO Customer (customerId, firstName, lastName, phoneNumber, email, sex)
    SELECT
        'KH' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        firstName, lastName, phoneNumber, email, sex
    FROM inserted;
END;
GO


-- =============================
-- 2️ BẢNG NHÂN VIÊN
-- =============================
CREATE TABLE Employee (
    employeeId NVARCHAR(10) PRIMARY KEY,
    firstName NVARCHAR(50) NOT NULL,
    lastName NVARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(20),
    email NVARCHAR(100),
    sex BIT,  -- 1 = Nam, 0 = Nữ
    role NVARCHAR(50),
    address NVARCHAR(100)
);
GO

CREATE TRIGGER trg_TuDongTaoMaNV
ON Employee
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SoCuoi INT;
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(employeeId, 3, LEN(employeeId)) AS INT)), 0)
    FROM Employee;

    INSERT INTO Employee (employeeId, firstName, lastName, phoneNumber, email, sex, role, address)
    SELECT
        'NV' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        firstName, lastName, phoneNumber, email, sex, role, address
    FROM inserted;
END;
GO


-- =============================
-- 3️ BẢNG BÀN (CAFE TABLE)
-- =============================
-- Bảng bàn--
USE Brew CREATE TABLE CafeTable(
	tableId NVARCHAR(10) PRIMARY KEY,
    floor VARCHAR(50) NOT NULL,			     -- Tầng của bàn (ví dụ: Tầng 1, Tầng 2)
	current_occupancy INT,					 -- Sức chứa hiện tại (số người đang ngồi hiện tại)
    capacity INT,                            -- Sức chứa (số người ngồi tối đa)
    status NVARCHAR(50)                       -- Trạng thái bàn (VD: Trống, Đang sử dụng, Đã đặt)
);
GO

CREATE TRIGGER trg_TuDongTaoMaBan
ON CafeTable
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @SoCuoi INT;
    SELECT @SoCuoi = ISNULL(MAX(CAST(tableId AS INT)), 0)
    FROM CafeTable;

    INSERT INTO CafeTable (tableId, floor, capacity, status)
    SELECT
        @SoCuoi + ROW_NUMBER() OVER (ORDER BY (SELECT NULL)),
        floor, capacity, status
    FROM inserted;
END;
GO


-- =============================
-- 4️ BẢNG MENU ITEM
-- =============================
CREATE TABLE MenuItem (
    itemId NVARCHAR(10) PRIMARY KEY,
    item_name VARCHAR(100),
    price FLOAT,
    category NVARCHAR(50),
    description NVARCHAR(100)
);
GO

ALTER TABLE MenuItem
ALTER COLUMN item_name NVARCHAR(100);
Go

CREATE TRIGGER trg_TuDongTaoMaMI
ON MenuItem
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SoCuoi INT;
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(itemId, 3, LEN(itemId)) AS INT)), 0)
    FROM MenuItem;

    INSERT INTO MenuItem (itemId, item_name, price, category, description)
    SELECT
        'MI' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        item_name, price, category, description
    FROM inserted;
END;
GO


-- =============================
-- 5️ BẢNG HÓA ĐƠN
-- =============================
CREATE TABLE Bill (
    billId NVARCHAR(10) PRIMARY KEY,
    dateCreated DATE,
    hourIn DATETIME,
    hourOut DATETIME,
    phoneNumber VARCHAR(20),
    total FLOAT,
    custPayment FLOAT,
    status VARCHAR(50),
    customerId NVARCHAR(10),
    employeeId NVARCHAR(10),
    tableId NVARCHAR(10),
    FOREIGN KEY (customerId) REFERENCES Customer(customerId),
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
    FOREIGN KEY (tableId) REFERENCES CafeTable(tableId)
);
GO

CREATE TRIGGER trg_TuDongTaoMaHD
ON Bill
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SoCuoi INT;
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(billId, 3, LEN(billId)) AS INT)), 0)
    FROM Bill;

    INSERT INTO Bill (billId, dateCreated, hourIn, hourOut, phoneNumber, total, custPayment, status, customerId, employeeId, tableId)
    SELECT
        'BI' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        dateCreated, hourIn, hourOut, phoneNumber, total, custPayment, status, customerId, employeeId, tableId
    FROM inserted;
END;
GO


-- =============================
-- 6️ BẢNG CHI TIẾT HÓA ĐƠN
-- =============================
CREATE TABLE BillDetail (
    billDetailId INT IDENTITY(1,1) PRIMARY KEY,
    billId NVARCHAR(10),
    menuId NVARCHAR(10),
    amount INT,
    org_price FLOAT,
    totalPrice FLOAT,
    FOREIGN KEY (billId) REFERENCES Bill(billId),
    FOREIGN KEY (menuId) REFERENCES MenuItem(itemId)
);
GO


-- =============================
-- 7️ BẢNG TÀI KHOẢN (ACCOUNT)
-- =============================
CREATE TABLE Account (
    accountId NVARCHAR(10) PRIMARY KEY,
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(100) NOT NULL,
    role NVARCHAR(50),
    employeeId NVARCHAR(10),
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)
);
GO

CREATE TRIGGER trg_TuDongTaoMaTK
ON Account
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SoCuoi INT;
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(accountId, 3, LEN(accountId)) AS INT)), 0)
    FROM Account;

    INSERT INTO Account (accountId, username, password, role, employeeId)
    SELECT
        'TK' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        username, password, role, employeeId
    FROM inserted;
END;
GO
-- Thêm cột ngày thêm khách hàng và dữ liệu của khách hàng
ALTER TABLE Customer ADD customerCreatedDate DATETIME DEFAULT GETDATE();
-- Chạy đoạn trên trước mới đc thêm customer k bị lỗi 
INSERT INTO Customer (firstName, lastName, phoneNumber, email, sex, customerCreatedDate)
VALUES
(N'Bảo', N'Nguyễn', '0910000001', 'bao.nguyen@gmail.com', 1, DATEADD(DAY, -2, GETDATE())),
(N'An', N'Trần', '0910000002', 'an.tran@gmail.com', 1, DATEADD(DAY, -5, GETDATE())),
(N'Vy', N'Lê', '0910000003', 'vy.le@gmail.com', 0, DATEADD(DAY, -8, GETDATE())),
(N'Hà', N'Phạm', '0910000004', 'ha.pham@gmail.com', 0, DATEADD(DAY, -11, GETDATE())),
(N'Khoa', N'Hồ', '0910000005', 'khoa.ho@gmail.com', 1, DATEADD(DAY, -14, GETDATE())),
(N'Quỳnh', N'Võ', '0910000006', 'quynh.vo@gmail.com', 0, DATEADD(DAY, -17, GETDATE())),
(N'Tùng', N'Đặng', '0910000007', 'tung.dang@gmail.com', 1, DATEADD(DAY, -20, GETDATE())),
(N'Ngọc', N'Bùi', '0910000008', 'ngoc.bui@gmail.com', 0, DATEADD(DAY, -23, GETDATE())),
(N'Phát', N'Ngô', '0910000009', 'phat.ngo@gmail.com', 1, DATEADD(DAY, -26, GETDATE())),
(N'Trang', N'Đỗ', '0910000010', 'trang.do@gmail.com', 0, DATEADD(DAY, -29, GETDATE())),
(N'Thắng', N'Phan', '0910000011', 'thang.phan@gmail.com', 1, DATEADD(DAY, -1, GETDATE())),
(N'Như', N'Trương', '0910000012', 'nhu.truong@gmail.com', 0, DATEADD(DAY, -4, GETDATE())),
(N'Hoài', N'Lý', '0910000013', 'hoai.ly@gmail.com', 1, DATEADD(DAY, -7, GETDATE())),
(N'Mai', N'Đinh', '0910000014', 'mai.dinh@gmail.com', 0, DATEADD(DAY, -10, GETDATE())),
(N'Tú', N'Tạ', '0910000015', 'tu.ta@gmail.com', 1, DATEADD(DAY, -13, GETDATE())),
(N'Yến', N'Vũ', '0910000016', 'yen.vu@gmail.com', 0, DATEADD(DAY, -16, GETDATE())),
(N'Bình', N'Cao', '0910000017', 'binh.cao@gmail.com', 1, DATEADD(DAY, -19, GETDATE())),
(N'Hương', N'Nguyễn', '0910000018', 'huong.nguyen@gmail.com', 0, DATEADD(DAY, -22, GETDATE())),
(N'Long', N'Nguyễn', '0910000019', 'long.nguyen@gmail.com', 1, DATEADD(DAY, -25, GETDATE())),
(N'Chi', N'Phạm', '0910000020', 'chi.pham@gmail.com', 0, DATEADD(DAY, -28, GETDATE()));

INSERT INTO Employee (firstName, lastName, phoneNumber, email, sex, role, address)
VALUES
(N'Nguyễn', N'Anh Tuấn', '0905123456', N'anh.tuan@gmail.com', 1, N'Nhân viên', N'123 Lê Lợi, Quận 1, TP. Hồ Chí Minh'),
(N'Lê', N'Thi Mai', '0906789123', N'mai.le@gmail.com', 0, N'Nhân viên', N'45 Trần Hưng Đạo, Quận 1, TP. Hồ Chí Minh'),
(N'Trần', N'Minh Quang', '0912345678', N'minh.quang@gmail.com', 1, N'Nhân viên', N'88 Nguyễn Trãi, Quận 5, TP. Hồ Chí Minh'),
(N'Phạm', N'Thi Hồng', '0933456789', N'hong.pham@gmail.com', 0, N'Nhân viên', N'27 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh'),
(N'Hoàng', N'Văn Bình', '0978456123', N'binh.hoang@gmail.com', 1, N'Nhân viên', N'59 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh'),
(N'Võ', N'Thảo Nhi', '0909988776', N'thao.nhi@gmail.com', 0, N'Nhân viên', N'76 Điện Biên Phủ, Quận 3, TP. Hồ Chí Minh'),
(N'Hữu', N'An', '0988000001', N'admin.brew@gmail.com', 1, N'Quản trị viên', N'TP. Hồ Chí Minh'),
(N'Nguyễn', N'Minh Quân', '0988000002', N'quanly1.brew@gmail.com', 1, N'Quản lý', N'TP. Hồ Chí Minh'),
(N'Trần', N'Thiên Phúc', '0988000003', N'quanly2.brew@gmail.com', 0, N'Quản lý', N'Hà Nội'),
(N'Lê', N'Thị Mai', '0988000004', N'nv1.brew@gmail.com', 1, N'Nhân viên', N'Đà Nẵng'),
(N'Phạm', N'Thi Hồng', '0988000005', N'nv2.brew@gmail.com', 0, N'Nhân viên', N'Cần Thơ'),
(N'Nguyễn', N'Nhật Tấn', '07658885', N'quanly1.brew@gmail.com', 1, N'Quản lý', N'TP. Hồ Chí Minh');

INSERT INTO MenuItem (item_name, price, category, description) VALUES
(N'Cà phê sữa đá', 25000, N'Cà phê', N'Cà phê pha phin với sữa đặc và đá.'),
(N'Cà phê đen', 20000, N'Cà phê', N'Cà phê pha phin nguyên chất, không đường.'),
(N'Cà phê muối', 30000, N'Cà phê', N'Cà phê pha với kem muối tạo hương vị đặc trưng.'),
(N'Cappuccino', 45000, N'Cà phê', N'Espresso pha với sữa nóng và bọt sữa.'),
(N'Latte', 42000, N'Cà phê', N'Espresso kết hợp với sữa nóng mịn.'),
(N'Mocha', 45000, N'Cà phê', N'Espresso pha với socola và sữa.'),
(N'Espresso', 35000, N'Cà phê', N'Cà phê Ý nguyên chất, đậm đặc.'),
(N'Trà đào cam sả', 35000, N'Trà', N'Trà đào kết hợp cam tươi và sả thơm mát.'),
(N'Trà chanh', 25000, N'Trà', N'Trà đen pha chanh tươi và đá.'),
(N'Trà vải', 32000, N'Trà', N'Trà đen pha với vải, vị ngọt dịu.'),
(N'Trá quất mật ong', 28000, N'Trà', N'Trà chanh kết hợp quất và mật ong nguyên chất.'),
(N'Trá sữa trân châu', 35000, N'Trà', N'Trá sữa pha trân châu dai.'),
(N'Trá sữa Matcha', 38000, N'Trà', N'Trá sữa vị matcha Nhật Bản.'),
(N'Sữa tươi trân châu đường nâu', 42000, N'Khác', N'Sữa tươi lạnh pha trân châu đường nâu.'),
(N'Soda chanh', 30000, N'Khác', N'Soda tươi pha chanh mát lạnh.'),
(N'Soda việt quất', 32000, N'Khác', N'Soda pha vị việt quất chua ngọt.'),
(N'Sinh tố bơ', 40000, N'Khác', N'Sinh tố bơ tươi mịn.'),
(N'Sinh tố xoài', 38000, N'Khác', N'Sinh tố xoài chín tự nhiên.'),
(N'Sinh tố dâu', 38000, N'Khác', N'Sinh tố dâu tươi mát.'),
(N'Nước cam', 30000, N'Khác', N'Nước cam tươi giàu vitamin C.'),
(N'Nước dưa hấu', 28000, N'Khác', N'Nước dưa hấu tươi mát.'),
(N'Blended Matcha', 45000, N'Trà', N'Trá matcha xay với kem và đá.'),
(N'Caramel Frappuccino', 45000, N'Cà phê', N'Cà phê caramel xay với kem tươi.'),
(N'Chocolate Frappuccino', 48000, N'Cà phê', N'Đồ uống socola xay, béo mịn.'),
(N'Bánh mì bơ tỏi', 30000, N'Bánh ngọt', N'Bánh mì nướng thơm bơ tỏi.'),
(N'Bánh su kem', 30000, N'Bánh ngọt', N'Bánh su mềm nhân kem vani.'),
(N'Bánh flan', 20000, N'Bánh ngọt', N'Bánh flan caramen mềm, ngọt dịu.'),
(N'Bánh pancake mật ong', 35000, N'Bánh ngọt', N'Bánh pancake mềm, rưới mật ong.'),
(N'Khoai tây chiên', 30000, N'Bánh ngọt', N'Khoai tây chiên giòn vàng, kèm sốt.'),
(N'Salad hoa quả', 32000, N'Bánh ngọt', N'Hỗn hợp hoa quả tươi trộn với sữa chua.'),
(N'Cà phê dừa', 38000, N'Cà phê', N'Cà phê đen pha với nước cốt dừa.'),
(N'Cà phê trứng', 40000, N'Cà phê', N'Cà phê đậm topping kem trứng.'),
(N'Cold Brew', 45000, N'Cà phê', N'Cà phê ủ lạnh, mịn và ít axit.'),
(N'Affogato', 48000, N'Cà phê', N'Espresso rót lên kem vani.'),
(N'Trá cúc mật ong', 32000, N'Trà', N'Trá cúc pha mật ong.'),
(N'Trá Oolong sữa', 38000, N'Trà', N'Trá Oolong pha sữa béo.'),
(N'Trá nhài', 28000, N'Trà', N'Trá nhài thơm nhẹ.'),
(N'Trá táo quế', 35000, N'Trà', N'Trá hoa quả ấm với táo và quế.'),
(N'Sinh tố chuối', 35000, N'Khác', N'Sinh tố chuối mịn và ngọt tự nhiên.'),
(N'Sinh tố cam xoài', 38000, N'Khác', N'Sinh tố cam và xoài kết hợp.'),
(N'Soda dâu', 32000, N'Khác', N'Soda pha vị dâu tươi.'),
(N'Soda kiwi', 32000, N'Khác', N'Soda pha vị kiwi nhẹ.'),
(N'Bánh donut socola', 25000, N'Bánh ngọt', N'Donut phủ socola ngọt.'),
(N'Bánh tiramisu', 45000, N'Bánh ngọt', N'Bánh tiramisu Ý, kem mascarpone.'),
(N'Bánh quy bơ', 30000, N'Bánh ngọt', N'Bánh quy bơ giòn thơm vani.'),
(N'Bánh croissant', 35000, N'Bánh ngọt', N'Bánh bơ Pháp, giòn xốp.'),
(N'Bánh mì trứng', 35000, N'Bánh ngọt', N'Bánh mì kẹp trứng và rau tươi.'),
(N'Bánh mì gà nướng', 40000, N'Bánh ngọt', N'Bánh mì kẹp gà nướng và sốt mayo.'),
(N'Phô mai que', 30000, N'Bánh ngọt', N'Phô mai mozzarella chiên giòn.'),
(N'Bánh khoai lang', 28000, N'Bánh ngọt', N'Bánh khoai lang chiên nhân mềm.'),
(N'Nước suối', 15000, N'Khác', N'Nước khoáng thiên nhiên.');




-- Thêm dữ liệu mẫu cho bảng Account với mật khẩu dễ nhớ
INSERT INTO Account (username, password, role, employeeId)
VALUES
('admin', '123', 'Admin', 'NV00001'),
('quanly1', '123', 'QuanLy', 'NV00002'),
('quanly2', 'l123', 'QuanLy', 'NV00003'),
('nv1', '123', 'NhanVien', 'NV00004'),
('nv2', '123', 'NhanVien', 'NV00005')


USE Brew;
INSERT INTO CafeTable (floor, current_occupancy, capacity, status)
VALUES
('Tầng trệt', 0, 3, 'AVAILABLE'),
('Tầng trệt', 0, 3, 'AVAILABLE'),
('Tầng trệt', 0, 3, 'AVAILABLE'),
('Tầng trệt', 0, 3, 'AVAILABLE'),
('Tầng trệt', 0, 3, 'AVAILABLE'),
('Tầng trệt', 0, 3, 'AVAILABLE');


INSERT INTO Bill (dateCreated, hourIn, hourOut, phoneNumber, total, custPayment, status, customerId, employeeId, tableId)
VALUES
(GETDATE(), GETDATE(), DATEADD(HOUR, 1, GETDATE()), '0905123456', 120000, 120000, N'Đã thanh toán', 'KH00001', 'NV00004', '1'),
(DATEADD(DAY, -1, GETDATE()), DATEADD(DAY, -1, GETDATE()), DATEADD(HOUR, 1, DATEADD(DAY, -1, GETDATE())), '0905234567', 95000, 95000, N'Đã thanh toán', 'KH00002', 'NV00005', '2'),
(DATEADD(DAY, -5, GETDATE()), DATEADD(DAY, -5, GETDATE()), DATEADD(HOUR, 1, DATEADD(DAY, -5, GETDATE())), '0905345678', 185000, 185000, N'Đã thanh toán', 'KH00003', 'NV00003', '3'),
(DATEADD(MONTH, -1, GETDATE()), DATEADD(MONTH, -1, GETDATE()), DATEADD(HOUR, 2, DATEADD(MONTH, -1, GETDATE())), '0905456789', 275000, 275000, N'Đã thanh toán', 'KH00004', 'NV00002', '4'),
(DATEADD(MONTH, -2, GETDATE()), DATEADD(MONTH, -2, GETDATE()), DATEADD(HOUR, 1, DATEADD(MONTH, -2, GETDATE())), '0905567890', 210000, 210000, N'Đã thanh toán', 'KH00005', 'NV00005', '5');

INSERT INTO Bill (dateCreated, hourIn, hourOut, phoneNumber, total, custPayment, status, customerId, employeeId, tableId)
VALUES
('2023-01-10', '2023-01-10 08:30', '2023-01-10 09:15', '0909123456', 95000, 95000, N'Ðã thanh toán', 'KH00001', 'NV00001', '1'),
('2023-02-14', '2023-02-14 14:00', '2023-02-14 15:10', '0909345678', 120000, 120000, N'Ðã thanh toán', 'KH00002', 'NV00002', '2'),
('2023-03-05', '2023-03-05 09:45', '2023-03-05 10:30', '0909567890', 185000, 185000, N'Ðã thanh toán', 'KH00003', 'NV00003', '3'),
('2023-04-18', '2023-04-18 07:50', '2023-04-18 08:45', '0909789012', 220000, 220000, N'Ðã thanh toán', 'KH00004', 'NV00004', '4'),
('2023-05-25', '2023-05-25 13:10', '2023-05-25 14:20', '0909123987', 265000, 265000, N'Ðã thanh toán', 'KH00005', 'NV00005', '5'),
('2023-06-03', '2023-06-03 10:05', '2023-06-03 11:00', '0909789001', 145000, 145000, N'Ðã thanh toán', 'KH00006', 'NV00001', '1'),
('2023-07-22', '2023-07-22 16:30', '2023-07-22 17:25', '0909345111', 200000, 200000, N'Ðã thanh toán', 'KH00007', 'NV00002', '2'),
('2023-09-10', '2023-09-10 08:45', '2023-09-10 09:40', '0909789009', 300000, 300000, N'Ðã thanh toán', 'KH00008', 'NV00003', '3'),
('2023-11-15', '2023-11-15 07:30', '2023-11-15 08:10', '0909678008', 90000, 90000, N'Ðã thanh toán', 'KH00009', 'NV00004', '4'),
('2023-12-20', '2023-12-20 18:00', '2023-12-20 19:20', '0909123123', 180000, 180000, N'Ðã thanh toán', 'KH00010', 'NV00005', '5'),

('2024-01-08', '2024-01-08 09:00', '2024-01-08 10:15', '0909345999', 155000, 155000, N'Ðã thanh toán', 'KH00001', 'NV00001', '1'),
('2024-03-10', '2024-03-10 14:30', '2024-03-10 15:25', '0909678123', 250000, 250000, N'Ðã thanh toán', 'KH00002', 'NV00002', '2'),
('2024-04-25', '2024-04-25 10:10', '2024-04-25 11:05', '0909345333', 175000, 175000, N'Ðã thanh toán', 'KH00003', 'NV00003', '3'),
('2024-05-12', '2024-05-12 07:55', '2024-05-12 08:45', '0909789111', 195000, 195000, N'Ðã thanh toán', 'KH00004', 'NV00004', '4'),
('2024-06-18', '2024-06-18 09:15', '2024-06-18 10:30', '0909123888', 285000, 285000, N'Ðã thanh toán', 'KH00005', 'NV00005', '5'),
('2024-08-07', '2024-08-07 16:00', '2024-08-07 17:10', '0909789222', 130000, 130000, N'Ðã thanh toán', 'KH00006', 'NV00001', '1'),
('2024-09-21', '2024-09-21 08:30', '2024-09-21 09:20', '0909567222', 100000, 100000, N'Ðã thanh toán', 'KH00007', 'NV00002', '2'),
('2024-10-30', '2024-10-30 13:00', '2024-10-30 14:10', '0909345444', 230000, 230000, N'Ðã thanh toán', 'KH00008', 'NV00003', '3'),

('2025-01-03', '2025-01-03 08:45', '2025-01-03 09:30', '0909789555', 165000, 165000, N'Ðã thanh toán', 'KH00009', 'NV00004', '4'),
('2025-02-11', '2025-02-11 17:15', '2025-02-11 18:05', '0909345666', 200000, 200000, N'Ðã thanh toán', 'KH00010', 'NV00005', '5'),
('2025-03-19', '2025-03-19 10:05', '2025-03-19 11:00', '0909123999', 270000, 270000, N'Ðã thanh toán', 'KH00001', 'NV00001', '1'),
('2025-06-08', '2025-06-08 08:15', '2025-06-08 09:05', '0909789777', 85000, 85000, N'Ðã thanh toán', 'KH00002', 'NV00002', '2'),
('2025-08-27', '2025-08-27 15:00', '2025-08-27 15:50', '0909678333', 150000, 150000, N'Ðã thanh toán', 'KH00003', 'NV00003', '3'),
('2025-10-12', '2025-10-12 07:45', '2025-10-12 08:30', '0909345777', 280000, 280000, N'Ðã thanh toán', 'KH00004', 'NV00004', '4');

-- ===============================
-- DỮ LIỆU BILL (2023–2025)
-- ===============================
INSERT INTO Bill (dateCreated, hourIn, hourOut, phoneNumber, total, custPayment, status, customerId, employeeId, tableId)
VALUES
-- Năm 2023
('2023-01-15', '2023-01-15 08:30', '2023-01-15 09:20', '0903000001', 150000, 150000, N'Đã thanh toán', 'KH00001', 'NV00001', '1'),
('2023-02-20', '2023-02-20 09:00', '2023-02-20 09:45', '0903000002', 180000, 180000, N'Đã thanh toán', 'KH00002', 'NV00002', '2'),
('2023-03-12', '2023-03-12 14:00', '2023-03-12 15:00', '0903000003', 210000, 210000, N'Đã thanh toán', 'KH00003', 'NV00003', '3'),
('2023-04-18', '2023-04-18 10:00', '2023-04-18 10:50', '0903000004', 170000, 170000, N'Đã thanh toán', 'KH00004', 'NV00004', '4'),
('2023-05-22', '2023-05-22 08:30', '2023-05-22 09:15', '0903000005', 190000, 190000, N'Đã thanh toán', 'KH00005', 'NV00005', '5'),
('2023-06-10', '2023-06-10 16:00', '2023-06-10 17:00', '0903000006', 200000, 200000, N'Đã thanh toán', 'KH00006', 'NV00001', '1'),
('2023-07-08', '2023-07-08 08:45', '2023-07-08 09:30', '0903000007', 230000, 230000, N'Đã thanh toán', 'KH00007', 'NV00002', '2'),
('2023-08-19', '2023-08-19 15:00', '2023-08-19 16:00', '0903000008', 260000, 260000, N'Đã thanh toán', 'KH00008', 'NV00003', '3'),
('2023-09-25', '2023-09-25 09:10', '2023-09-25 10:00', '0903000009', 185000, 185000, N'Đã thanh toán', 'KH00009', 'NV00004', '4'),
('2023-10-05', '2023-10-05 07:45', '2023-10-05 08:30', '0903000010', 175000, 175000, N'Đã thanh toán', 'KH00010', 'NV00005', '5'),
('2023-11-14', '2023-11-14 17:00', '2023-11-14 17:45', '0903000011', 155000, 155000, N'Đã thanh toán', 'KH00001', 'NV00001', '1'),
('2023-12-22', '2023-12-22 13:00', '2023-12-22 14:00', '0903000012', 220000, 220000, N'Đã thanh toán', 'KH00002', 'NV00002', '2'),

-- Năm 2024
('2024-01-12', '2024-01-12 08:00', '2024-01-12 09:00', '0904000001', 180000, 180000, N'Đã thanh toán', 'KH00003', 'NV00003', '3'),
('2024-02-19', '2024-02-19 14:00', '2024-02-19 15:00', '0904000002', 190000, 190000, N'Đã thanh toán', 'KH00004', 'NV00004', '4'),
('2024-03-25', '2024-03-25 07:30', '2024-03-25 08:15', '0904000003', 210000, 210000, N'Đã thanh toán', 'KH00005', 'NV00005', '5'),
('2024-04-15', '2024-04-15 09:00', '2024-04-15 10:00', '0904000004', 165000, 165000, N'Đã thanh toán', 'KH00006', 'NV00001', '1'),
('2024-05-08', '2024-05-08 10:30', '2024-05-08 11:20', '0904000005', 195000, 195000, N'Đã thanh toán', 'KH00007', 'NV00002', '2'),
('2024-06-24', '2024-06-24 08:10', '2024-06-24 09:00', '0904000006', 250000, 250000, N'Đã thanh toán', 'KH00008', 'NV00003', '3'),
('2024-07-30', '2024-07-30 16:00', '2024-07-30 16:45', '0904000007', 230000, 230000, N'Đã thanh toán', 'KH00009', 'NV00004', '4'),
('2024-08-18', '2024-08-18 07:40', '2024-08-18 08:25', '0904000008', 200000, 200000, N'Đã thanh toán', 'KH00010', 'NV00005', '5'),
('2024-09-14', '2024-09-14 11:00', '2024-09-14 11:45', '0904000009', 185000, 185000, N'Đã thanh toán', 'KH00001', 'NV00001', '1'),
('2024-10-22', '2024-10-22 13:10', '2024-10-22 14:00', '0904000010', 210000, 210000, N'Đã thanh toán', 'KH00002', 'NV00002', '2'),
('2024-11-09', '2024-11-09 09:00', '2024-11-09 09:50', '0904000011', 240000, 240000, N'Đã thanh toán', 'KH00003', 'NV00003', '3'),
('2024-12-27', '2024-12-27 15:00', '2024-12-27 15:50', '0904000012', 280000, 280000, N'Đã thanh toán', 'KH00004', 'NV00004', '4'),

-- Năm 2025
('2025-01-11', '2025-01-11 08:30', '2025-01-11 09:20', '0905000001', 150000, 150000, N'Đã thanh toán', 'KH00005', 'NV00005', '5'),
('2025-02-16', '2025-02-16 14:00', '2025-02-16 14:45', '0905000002', 175000, 175000, N'Đã thanh toán', 'KH00006', 'NV00001', '1'),
('2025-03-10', '2025-03-10 10:00', '2025-03-10 10:50', '0905000003', 190000, 190000, N'Đã thanh toán', 'KH00007', 'NV00002', '2'),
('2025-04-22', '2025-04-22 07:30', '2025-04-22 08:20', '0905000004', 200000, 200000, N'Đã thanh toán', 'KH00008', 'NV00003', '3'),
('2025-05-13', '2025-05-13 15:00', '2025-05-13 15:50', '0905000005', 225000, 225000, N'Đã thanh toán', 'KH00009', 'NV00004', '4'),
('2025-06-17', '2025-06-17 09:15', '2025-06-17 10:00', '0905000006', 260000, 260000, N'Đã thanh toán', 'KH00010', 'NV00005', '5'),
('2025-07-23', '2025-07-23 08:45', '2025-07-23 09:30', '0905000007', 195000, 195000, N'Đã thanh toán', 'KH00001', 'NV00001', '1'),
('2025-08-14', '2025-08-14 13:00', '2025-08-14 13:50', '0905000008', 180000, 180000, N'Đã thanh toán', 'KH00002', 'NV00002', '2'),
('2025-09-20', '2025-09-20 17:00', '2025-09-20 17:45', '0905000009', 220000, 220000, N'Đã thanh toán', 'KH00003', 'NV00003', '3'),
('2025-10-29', '2025-10-29 07:45', '2025-10-29 08:35', '0905000010', 240000, 240000, N'Đã thanh toán', 'KH00004', 'NV00004', '4'),
('2025-11-18', '2025-11-18 10:30', '2025-11-18 11:20', '0905000011', 210000, 210000, N'Đã thanh toán', 'KH00005', 'NV00005', '5'),
('2025-12-31', '2025-12-31 18:00', '2025-12-31 18:50', '0905000012', 280000, 280000, N'Đã thanh toán', 'KH00006', 'NV00001', '1');


INSERT INTO BillDetail (billId, menuId, amount, org_price, totalPrice)
VALUES
('BI00001', 'MI00001', 2, 25000, 50000),
('BI00001', 'MI00003', 1, 40000, 40000),
('BI00001', 'MI00005', 1, 30000, 30000),

('BI00002', 'MI00002', 2, 30000, 60000),
('BI00002', 'MI00004', 1, 35000, 35000),

('BI00003', 'MI00001', 3, 25000, 75000),
('BI00003', 'MI00006', 2, 30000, 60000),
('BI00003', 'MI00007', 1, 15000, 15000),

('BI00004', 'MI00002', 3, 30000, 90000),
('BI00004', 'MI00003', 2, 40000, 80000),
('BI00004', 'MI00005', 1, 45000, 45000),

('BI00005', 'MI00004', 2, 35000, 70000),
('BI00005', 'MI00006', 1, 30000, 30000),
('BI00005', 'MI00007', 2, 15000, 30000);

-- Tháng 10/2025 (BI00001, BI00002, BI00003, BI00029)
INSERT INTO BillDetail (billId, menuId, amount, org_price, totalPrice) VALUES
('BI00001', 'MI00001', 2, 25000, 50000),
('BI00001', 'MI00003', 2, 45000, 90000),

('BI00002', 'MI00002', 1, 30000, 30000),
('BI00002', 'MI00006', 2, 35000, 70000),

('BI00003', 'MI00004', 2, 40000, 80000),
('BI00003', 'MI00005', 2, 15000, 30000),

('BI00029', 'MI00007', 3, 42000, 126000),
('BI00029', 'MI00001', 2, 25000, 50000);

-- Tháng 9/2025 (BI00004)
INSERT INTO BillDetail VALUES
('BI00004', 'MI00003', 3, 45000, 135000),
('BI00004', 'MI00005', 2, 15000, 30000);

-- Tháng 8/2025 (BI00005, BI00028)
INSERT INTO BillDetail VALUES
('BI00005', 'MI00006', 3, 35000, 105000),
('BI00005', 'MI00002', 2, 30000, 60000),
('BI00028', 'MI00003', 2, 45000, 90000),
('BI00028', 'MI00004', 1, 40000, 40000);

-- Tháng 6/2025 (BI00027)
INSERT INTO BillDetail VALUES
('BI00027', 'MI00001', 2, 25000, 50000),
('BI00027', 'MI00005', 1, 15000, 15000);

-- Tháng 3/2025 (BI00026)
INSERT INTO BillDetail VALUES
('BI00026', 'MI00004', 3, 40000, 120000),
('BI00026', 'MI00006', 2, 35000, 70000);

-- Tháng 1-2/2025 (BI00024, BI00025)
INSERT INTO BillDetail VALUES
('BI00024', 'MI00001', 2, 25000, 50000),
('BI00024', 'MI00003', 3, 45000, 135000),
('BI00025', 'MI00002', 2, 30000, 60000),
('BI00025', 'MI00004', 2, 40000, 80000);

-- ===============================
-- DỮ LIỆU BILLDETAIL
-- ===============================
INSERT INTO BillDetail (billId, menuId, amount, org_price, totalPrice)
VALUES
('BI00030', 'MI00001', 2, 25000, 50000),
('BI00030', 'MI00009', 1, 25000, 25000),
('BI00030', 'MI00025', 1, 30000, 30000),
('BI00030', 'MI00015', 1, 30000, 30000),

('BI00031', 'MI00002', 1, 20000, 20000),
('BI00031', 'MI00008', 2, 35000, 70000),
('BI00031', 'MI00028', 1, 35000, 35000),
('BI00031', 'MI00040', 1, 28000, 28000),

('BI00032', 'MI00005', 1, 42000, 42000),
('BI00032', 'MI00014', 1, 42000, 42000),
('BI00032', 'MI00027', 2, 20000, 40000),
('BI00032', 'MI00036', 1, 38000, 38000),

('BI00033', 'MI00003', 2, 30000, 60000),
('BI00033', 'MI00022', 1, 45000, 45000),
('BI00033', 'MI00043', 1, 30000, 30000),
('BI00033', 'MI00011', 1, 28000, 28000),

('BI00034', 'MI00004', 2, 45000, 90000),
('BI00034', 'MI00035', 1, 32000, 32000),
('BI00034', 'MI00026', 1, 30000, 30000),

('BI00035', 'MI00007', 1, 35000, 35000),
('BI00035', 'MI00018', 2, 38000, 76000),
('BI00035', 'MI00038', 1, 35000, 35000),
('BI00035', 'MI00041', 1, 32000, 32000),
-- BI00036
('BI00036', 'MI00006', 1, 28000, 28000),
('BI00036', 'MI00019', 2, 35000, 70000),
('BI00036', 'MI00030', 1, 30000, 30000),

-- BI00037
('BI00037', 'MI00002', 1, 20000, 20000),
('BI00037', 'MI00009', 2, 25000, 50000),
('BI00037', 'MI00025', 1, 30000, 30000),

-- BI00038
('BI00038', 'MI00011', 1, 28000, 28000),
('BI00038', 'MI00014', 2, 42000, 84000),
('BI00038', 'MI00028', 1, 35000, 35000),

-- BI00039
('BI00039', 'MI00003', 1, 30000, 30000),
('BI00039', 'MI00022', 1, 45000, 45000),
('BI00039', 'MI00036', 2, 38000, 76000),

-- BI00040
('BI00040', 'MI00005', 2, 42000, 84000),
('BI00040', 'MI00026', 1, 30000, 30000),
('BI00040', 'MI00043', 1, 30000, 30000),

-- BI00041
('BI00041', 'MI00004', 1, 45000, 45000),
('BI00041', 'MI00018', 2, 38000, 76000),
('BI00041', 'MI00038', 1, 35000, 35000),

-- BI00042
('BI00042', 'MI00007', 1, 35000, 35000),
('BI00042', 'MI00019', 1, 35000, 35000),
('BI00042', 'MI00030', 1, 30000, 30000),

-- BI00043
('BI00043', 'MI00008', 2, 35000, 70000),
('BI00043', 'MI00027', 1, 20000, 20000),
('BI00043', 'MI00035', 1, 32000, 32000),

-- BI00044
('BI00044', 'MI00001', 1, 25000, 25000),
('BI00044', 'MI00009', 1, 25000, 25000),
('BI00044', 'MI00026', 2, 30000, 60000),

-- BI00045
('BI00045', 'MI00005', 1, 42000, 42000),
('BI00045', 'MI00022', 2, 45000, 90000),
('BI00045', 'MI00011', 1, 28000, 28000),

-- BI00046
('BI00046', 'MI00003', 1, 30000, 30000),
('BI00046', 'MI00014', 1, 42000, 42000),
('BI00046', 'MI00036', 1, 38000, 38000),

-- BI00047
('BI00047', 'MI00004', 2, 45000, 90000),
('BI00047', 'MI00027', 1, 20000, 20000),
('BI00047', 'MI00038', 1, 35000, 35000),

-- BI00048
('BI00048', 'MI00006', 1, 28000, 28000),
('BI00048', 'MI00019', 1, 35000, 35000),
('BI00048', 'MI00025', 2, 30000, 60000),

-- BI00049
('BI00049', 'MI00001', 2, 25000, 50000),
('BI00049', 'MI00009', 1, 25000, 25000),
('BI00049', 'MI00028', 1, 35000, 35000),

-- BI00050
('BI00050', 'MI00003', 1, 30000, 30000),
('BI00050', 'MI00014', 2, 42000, 84000),
('BI00050', 'MI00030', 1, 30000, 30000),

-- BI00051
('BI00051', 'MI00002', 1, 20000, 20000),
('BI00051', 'MI00008', 1, 35000, 35000),
('BI00051', 'MI00027', 2, 20000, 40000),

-- BI00052
('BI00052', 'MI00004', 2, 45000, 90000),
('BI00052', 'MI00011', 1, 28000, 28000),
('BI00052', 'MI00022', 1, 45000, 45000),

-- BI00053
('BI00053', 'MI00005', 1, 42000, 42000),
('BI00053', 'MI00018', 2, 38000, 76000),
('BI00053', 'MI00026', 1, 30000, 30000),

-- BI00054
('BI00054', 'MI00007', 1, 35000, 35000),
('BI00054', 'MI00019', 2, 35000, 70000),
('BI00054', 'MI00035', 1, 32000, 32000),

-- BI00055
('BI00055', 'MI00003', 1, 30000, 30000),
('BI00055', 'MI00022', 1, 45000, 45000),
('BI00055', 'MI00036', 2, 38000, 76000),

-- BI00056
('BI00056', 'MI00008', 2, 35000, 70000),
('BI00056', 'MI00028', 1, 35000, 35000),
('BI00056', 'MI00043', 1, 30000, 30000),

-- BI00057
('BI00057', 'MI00001', 1, 25000, 25000),
('BI00057', 'MI00009', 1, 25000, 25000),
('BI00057', 'MI00027', 1, 20000, 20000),

-- BI00058
('BI00058', 'MI00005', 2, 42000, 84000),
('BI00058', 'MI00026', 1, 30000, 30000),
('BI00058', 'MI00038', 1, 35000, 35000),

-- BI00059
('BI00059', 'MI00004', 1, 45000, 45000),
('BI00059', 'MI00018', 1, 38000, 38000),
('BI00059', 'MI00030', 2, 30000, 60000),

-- BI00060
('BI00060', 'MI00003', 1, 30000, 30000),
('BI00060', 'MI00011', 1, 28000, 28000),
('BI00060', 'MI00022', 1, 45000, 45000),

-- BI00061
('BI00061', 'MI00007', 1, 35000, 35000),
('BI00061', 'MI00014', 2, 42000, 84000),
('BI00061', 'MI00035', 1, 32000, 32000),

-- BI00062
('BI00062', 'MI00002', 2, 20000, 40000),
('BI00062', 'MI00008', 1, 35000, 35000),
('BI00062', 'MI00026', 1, 30000, 30000),

-- BI00063
('BI00063', 'MI00005', 1, 42000, 42000),
('BI00063', 'MI00019', 2, 35000, 70000),
('BI00063', 'MI00038', 1, 35000, 35000),

-- BI00064
('BI00064', 'MI00004', 1, 45000, 45000),
('BI00064', 'MI00027', 2, 20000, 40000),
('BI00064', 'MI00043', 1, 30000, 30000),

-- BI00065
('BI00065', 'MI00006', 1, 28000, 28000),
('BI00065', 'MI00022', 1, 45000, 45000),
('BI00065', 'MI00036', 1, 38000, 38000);

USE Brew
INSERT INTO Employee (firstName, lastName, phoneNumber, email, sex, role, address)
VALUES
(N'Nguyễn', N'Nhật Tấn', '07658885', N'nhattan.brew@gmail.com', 1, N'Quản lý', N'TP. Hồ Chí Minh'),
(N'Phạm', N'Ngọc Thành', '07658885', N'ngocthanh.brew@gmail.com', 1, N'Quản lý', N'Gia Lai'),
(N'Dương', N'Thế Khánh', '07658885', N'thekhanh.brew@gmail.com', 1, N'Quản lý', N'Đồng Nai'),
(N'Nguyễn', N'Hoàng Phong', '07658885', N'hoangphong.brew@gmail.com', 1, N'Quản lý', N'TP. Hồ Chí Minh');

-- Thêm dữ liệu mẫu cho bảng Account với mật khẩu dễ nhớ
USE Brew
INSERT INTO Account (username, password, role, employeeId)
VALUES
('AdminTan', '123', 'Admin', 'NV00012'),
('AdminThanh', '123', 'Admin', 'NV00013'),
('AdminKhanh', '123', 'Admin', 'NV00014'),
('AdminPhong', '123', 'Admin', 'NV00015');