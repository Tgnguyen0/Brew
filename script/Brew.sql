CREATE DATABASE Brew

-- Bảng Khách hàng
USE Brew CREATE TABLE Customer (
    customerId NVARCHAR(10) PRIMARY KEY,
    firstName NVARCHAR(50) NOT NULL,
    lastName NVARCHAR(50) NOT NULL,
    phoneNumber NVARCHAR(20),
    email VARCHAR(100),
    sex BIT  -- 1 = Nam, 0 = Nữ
);
GO

USE Brew;
GO

CREATE TRIGGER trg_TuDongTaoMaKH
ON Customer
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @SoCuoi INT;

    -- Lấy số cuối cùng hiện có trong bảng (phần số sau 'KH')
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(customerId, 3, LEN(customerId)) AS INT)), 0)
    FROM Customer;

    -- Chèn các dòng mới với mã tự động tăng
    INSERT INTO Customer (customerId, firstName, lastName, phoneNumber, email, sex)
    SELECT
        'KH' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        firstName, lastName, phoneNumber, email, sex
    FROM inserted;
END;
GO

/*INSERT INTO Customer (customerId, firstName, lastName, phoneNumber, email, sex) VALUES
('KH00001', N'Nguyễn', N'Văn Minh', '0905123456', 'minh.nguyen@example.com', 1),
('KH00002', N'Lê', N'Thị Lan', '0906234789', 'lan.le@example.com', 0),
('KH00003', N'Trần', N'Quang Huy', '0912345678', 'huy.tran@example.com', 1),
('KH00004', N'Phạm', N'Thị Hương', '0934567890', 'huong.pham@example.com', 0),
('KH00005', N'Hoàng', N'Anh Tuấn', '0978456123', 'tuan.hoang@example.com', 1),
('KH00006', N'Võ', N'Thảo Nhi', '0909988776', 'nhi.vo@example.com', 0),
('KH00007', N'Đặng', N'Minh Khang', '0987766554', 'khang.dang@example.com', 1),
('KH00008', N'Bùi', N'Thị Mai', '0911223344', 'mai.bui@example.com', 0),
('KH00009', N'Ngô', N'Hoàng Nam', '0944332211', 'nam.ngo@example.com', 1),
('KH00010', N'Đỗ', N'Thị Thu', '0966778899', 'thu.do@example.com', 0),
('KH00011', N'Phan', N'Văn Tài', '0905432198', 'tai.phan@example.com', 1),
('KH00012', N'Trương', N'Thị Hạnh', '0907654321', 'hanh.truong@example.com', 0),
('KH00013', N'Lý', N'Minh Đức', '0912789456', 'duc.ly@example.com', 1),
('KH00014', N'Đinh', N'Thị Ngọc', '0903245890', 'ngoc.dinh@example.com', 0),
('KH00015', N'Tạ', N'Văn Phúc', '0923456789', 'phuc.ta@example.com', 1),
('KH00016', N'Vũ', N'Thị Hồng', '0911987654', 'hong.vu@example.com', 0),
('KH00017', N'Cao', N'Minh Trí', '0932134567', 'tri.cao@example.com', 1),
('KH00018', N'Nguyễn', N'Thị Ngân', '0971234560', 'ngan.nguyen@example.com', 0),
('KH00019', N'Trần', N'Hữu Lộc', '0956784321', 'loc.tran@example.com', 1),
('KH00020', N'Phạm', N'Thị Yến', '0945678234', 'yen.pham@example.com', 0);*/

USE Brew INSERT INTO Customer (firstName, lastName, phoneNumber, email, sex) VALUES
(N'Minh', N'Nguyen', '0905123456', 'minh.nguyen@example.com', 1),
(N'Lan', N'Le', '0906234789', 'lan.le@example.com', 0),
(N'Huy', N'Tran', '0912345678', 'huy.tran@example.com', 1),
(N'Huong', N'Pham', '0934567890', 'huong.pham@example.com', 0),
(N'Tuan', N'Hoang', '0978456123', 'tuan.hoang@example.com', 1),
(N'Nhi', N'Vo', '0909988776', 'nhi.vo@example.com', 0),
(N'Khang', N'Dang', '0987766554', 'khang.dang@example.com', 1),
(N'Mai', N'Bui', '0911223344', 'mai.bui@example.com', 0),
(N'Nam', N'Ngo', '0944332211', 'nam.ngo@example.com', 1),
(N'Thu', N'Do', '0966778899', 'thu.do@example.com', 0),
(N'Tai', N'Phan', '0905432198', 'tai.phan@example.com', 1),
(N'Hanh', N'Truong', '0907654321', 'hanh.truong@example.com', 0),
(N'Duc', N'Ly', '0912789456', 'duc.ly@example.com', 1),
(N'Ngoc', N'Dinh', '0903245890', 'ngoc.dinh@example.com', 0),
(N'Phuc', N'Ta', '0923456789', 'phuc.ta@example.com', 1),
(N'Hong', N'Vu', '0911987654', 'hong.vu@example.com', 0),
(N'Tri', N'Cao', '0932134567', 'tri.cao@example.com', 1),
(N'Ngan', N'Nguyen', '0971234560', 'ngan.nguyen@example.com', 0),
(N'Loc', N'Tran', '0956784321', 'loc.tran@example.com', 1),
(N'Yen', N'Pham', '0945678234', 'yen.pham@example.com', 0);

--Bảng nhấn viên--
USE Brew CREATE TABLE Employee (
	employeeId NVARCHAR(10) PRIMARY KEY,
    firstName NVARCHAR(50) NOT NULL,
    lastName NVARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(20),
    email NVARCHAR(100),
    sex BIT,  -- 1 = Nam, 0 = Nữ
	role NVARCHAR(50),
	address NVARCHAR(50)
);

-- Trigger tự động tạo mã NV
USE Brew 
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

/*INSERT INTO Employee (firstName, lastName, phoneNumber, email, sex, role, address) VALUES
(N'Nguyễn', N'Anh Tuấn', '0905123456', N'anh.tuan@coffeebar.vn', 1, N'Barista', N'123 Lê Lợi, Q.1, TP.HCM'),
(N'Lê', N'Thị Mai', '0906789123', N'mai.le@coffeebar.vn', 0, N'Cashier', N'45 Trần Hưng Đạo, Q.1, TP.HCM'),
(N'Trần', N'Minh Quang', '0912345678', N'minh.quang@coffeebar.vn', 1, N'Server', N'88 Nguyễn Trãi, Q.5, TP.HCM'),
(N'Phạm', N'Thị Hồng', '0933456789', N'hong.pham@coffeebar.vn', 0, N'Cleaner', N'27 Nguyễn Văn Cừ, Q.5, TP.HCM'),
(N'Hoàng', N'Văn Bình', '0978456123', N'binh.hoang@coffeebar.vn', 1, N'Security', N'59 Nguyễn Huệ, Q.1, TP.HCM'),
(N'Võ', N'Thảo Nhi', '0909988776', N'thao.nhi@coffeebar.vn', 0, N'Barista', N'76 Điện Biên Phủ, Q.3, TP.HCM')*/

USE Brew INSERT INTO Employee (firstName, lastName, phoneNumber, email, sex, role, address) VALUES
(N'Nguyen', N'Anh Tuan', '0905123456', N'anh.tuan@coffeebar.vn', 1, N'Barista', N'123 Le Loi St, Dist.1, Ho Chi Minh City'),
(N'Le', N'Thi Mai', '0906789123', N'mai.le@coffeebar.vn', 0, N'Cashier', N'45 Tran Hung Dao St, Dist.1, Ho Chi Minh City'),
(N'Tran', N'Minh Quang', '0912345678', N'minh.quang@coffeebar.vn', 1, N'Server', N'88 Nguyen Trai St, Dist.5, Ho Chi Minh City'),
(N'Pham', N'Thi Hong', '0933456789', N'hong.pham@coffeebar.vn', 0, N'Cleaner', N'27 Nguyen Van Cu St, Dist.5, Ho Chi Minh City'),
(N'Hoang', N'Van Binh', '0978456123', N'binh.hoang@coffeebar.vn', 1, N'Security', N'59 Nguyen Hue St, Dist.1, Ho Chi Minh City'),
(N'Vo', N'Thao Nhi', '0909988776', N'thao.nhi@coffeebar.vn', 0, N'Barista', N'76 Dien Bien Phu St, Dist.3, Ho Chi Minh City')

--Bảng hóa đơn--
USE Brew CREATE TABLE Bill (
	bilId NVARCHAR(10) PRIMARY KEY,
    dateCreated DATE,               -- LocalDate -> DATE
    hourIn DATETIME,                -- LocalDate -> DATETIME (chứa cả ngày + giờ)
    hourOut DATETIME,				-- LocalDate -> DATETIME
    phoneNumber VARCHAR(20),        -- String -> VARCHAR
    total FLOAT,					-- float
    custPayment FLOAT,				-- float
    status VARCHAR(50),				-- String -> VARCHAR
);

-- Trigger tự động tạo mã Bill
USE Brew 
GO

CREATE TRIGGER trg_TuDongTaoMaHD
ON Bill
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @SoCuoi INT;

    -- Lấy số cuối cùng hiện có trong bảng (phần số sau 'BI')
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(bilId, 3, LEN(bilId)) AS INT)), 0)
    FROM Bill;

    -- Chèn các dòng mới với mã tự động tăng
    INSERT INTO Bill (bilId, dateCreated, hourIn, hourOut, phoneNumber, total, custPayment, status)
    SELECT
        'BI' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        dateCreated, hourIn, hourOut, phoneNumber, total, custPayment, status
    FROM inserted;
END;
GO

--Bảng MenuItem--
USE Brew CREATE TABLE MenuItem (
	itemId NVARCHAR(10) PRIMARY KEY,
    item_name VARCHAR(100), 
    price FLOAT,
    category NVARCHAR(50),
	description NVARCHAR(255)
);

-- Trigger tự động tạo mã vật phẩm menu
USE Brew 
GO

CREATE TRIGGER trg_TuDongTaoMaMI
ON MenuItem
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @SoCuoi INT;

    -- Lấy số cuối cùng hiện có trong bảng (phần số sau 'MI')
    SELECT @SoCuoi = ISNULL(MAX(CAST(SUBSTRING(itemId, 3, LEN(itemId)) AS INT)), 0)
    FROM MenuItem;

    -- Chèn các dòng mới với mã tự động tăng
    INSERT INTO MenuItem (itemId, item_name, price, category, description)
    SELECT
        'MI' + RIGHT('00000' + CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(5)), 5),
        item_name, price, category, description
    FROM inserted;
END;
GO

/*INSERT INTO MenuItem (item_name, price, category, description) VALUES
(N'Cà phê sữa đá', 25000, N'Đồ uống', N'Cà phê rang xay pha sữa đặc và đá.'),
(N'Cà phê đen', 20000, N'Đồ uống', N'Cà phê nguyên chất đậm đà, không đường.'),
(N'Cà phê muối', 30000, N'Đồ uống', N'Cà phê pha cùng kem muối béo mặn nhẹ.'),
(N'Cappuccino', 45000, N'Đồ uống', N'Cà phê espresso với sữa và bọt sữa mịn.'),
(N'Latte', 42000, N'Đồ uống', N'Cà phê espresso hòa quyện sữa nóng.'),
(N'Mocha', 45000, N'Đồ uống', N'Cà phê kết hợp sô-cô-la và sữa tươi.'),
(N'Espresso', 35000, N'Đồ uống', N'Cà phê Ý đậm đặc, thơm mạnh.'),
(N'Trà đào cam sả', 35000, N'Đồ uống', N'Trà đào kết hợp cam tươi và sả thơm.'),
(N'Trà chanh', 25000, N'Đồ uống', N'Trà tươi pha cùng chanh và đá.'),
(N'Trà vải', 32000, N'Đồ uống', N'Trà đen pha vải tươi, ngọt dịu.'),
(N'Trà tắc mật ong', 28000, N'Đồ uống', N'Trà chanh tắc pha cùng mật ong nguyên chất.'),
(N'Trà sữa trân châu', 35000, N'Đồ uống', N'Trà sữa béo nhẹ với trân châu dẻo.'),
(N'Trà sữa matcha', 38000, N'Đồ uống', N'Trà sữa vị matcha Nhật Bản thơm dịu.'),
(N'Sữa tươi trân châu đường đen', 42000, N'Đồ uống', N'Sữa tươi lạnh pha cùng trân châu đường đen.'),
(N'Soda chanh', 30000, N'Đồ uống', N'Soda tươi mát kết hợp chanh tươi.'),
(N'Soda việt quất', 32000, N'Đồ uống', N'Soda vị việt quất chua ngọt, mát lạnh.'),
(N'Sinh tố bơ', 40000, N'Đồ uống', N'Sinh tố từ bơ tươi béo ngậy.'),
(N'Sinh tố xoài', 38000, N'Đồ uống', N'Sinh tố xoài chín ngọt tự nhiên.'),
(N'Sinh tố dâu', 38000, N'Đồ uống', N'Sinh tố dâu tây tươi mát.'),
(N'Nước cam ép', 30000, N'Đồ uống', N'Nước cam nguyên chất giàu vitamin C.'),
(N'Nước ép dưa hấu', 28000, N'Đồ uống', N'Nước ép dưa hấu mát lạnh, giải nhiệt.'),
(N'Matcha đá xay', 45000, N'Đồ uống', N'Matcha xay cùng kem sữa và đá.'),
(N'Caramel đá xay', 45000, N'Đồ uống', N'Cà phê caramel xay lạnh cùng kem tươi.'),
(N'Sô-cô-la đá xay', 48000, N'Đồ uống', N'Sô-cô-la xay lạnh béo ngậy.'),
(N'Bánh mì bơ tỏi', 30000, N'Ăn nhẹ', N'Bánh mì nướng giòn kèm bơ tỏi thơm.'),
(N'Bánh su kem', 30000, N'Tráng miệng', N'Bánh su kem nhân vani mềm mịn.'),
(N'Bánh flan', 20000, N'Tráng miệng', N'Bánh flan béo mịn, vị caramel dịu ngọt.'),
(N'Bánh pancake mật ong', 35000, N'Tráng miệng', N'Pancake mềm ăn kèm mật ong.'),
(N'Khoai tây chiên', 30000, N'Ăn nhẹ', N'Khoai tây chiên vàng giòn, ăn kèm tương cà.'),
(N'Salad trái cây', 32000, N'Tráng miệng', N'Trái cây tươi trộn sữa chua lạnh.'),
(N'Cà phê dừa', 38000, N'Đồ uống', N'Cà phê đen hòa cùng cốt dừa béo ngậy.'),
(N'Cà phê trứng', 40000, N'Đồ uống', N'Cà phê đậm đặc kết hợp trứng đánh bông.'),
(N'Cold Brew', 45000, N'Đồ uống', N'Cà phê ủ lạnh vị êm dịu, ít chua.'),
(N'Affogato', 48000, N'Đồ uống', N'Cà phê espresso rót lên kem vanilla lạnh.'),
(N'Trà hoa cúc mật ong', 32000, N'Đồ uống', N'Trà hoa cúc thanh mát pha mật ong.'),
(N'Trà ô long sữa', 38000, N'Đồ uống', N'Trà ô long hương thơm dịu hòa sữa béo.'),
(N'Trà nhài', 28000, N'Đồ uống', N'Trà nhài thanh khiết, vị nhẹ nhàng.'),
(N'Trà táo quế', 35000, N'Đồ uống', N'Trà trái cây thơm quế và táo đỏ.'),
(N'Sinh tố chuối', 35000, N'Đồ uống', N'Sinh tố chuối chín mịn màng, béo thơm.'),
(N'Sinh tố cam xoài', 38000, N'Đồ uống', N'Sinh tố kết hợp giữa cam và xoài tươi.'),
(N'Soda dâu tây', 32000, N'Đồ uống', N'Soda pha dâu tây tươi mát lạnh.'),
(N'Soda kiwi', 32000, N'Đồ uống', N'Soda vị kiwi xanh ngọt nhẹ.'),
(N'Bánh donut socola', 25000, N'Tráng miệng', N'Donut phủ socola ngọt ngào.'),
(N'Bánh tiramisu', 45000, N'Tráng miệng', N'Bánh tiramisu vị cà phê và kem mascarpone.'),
(N'Bánh cookies bơ', 30000, N'Tráng miệng', N'Bánh quy bơ giòn, thơm vị vanilla.'),
(N'Bánh croissant', 35000, N'Ăn nhẹ', N'Bánh sừng bò nướng vàng giòn.'),
(N'Sandwich trứng', 35000, N'Ăn nhẹ', N'Sandwich kẹp trứng chiên và rau tươi.'),
(N'Sandwich gà nướng', 40000, N'Ăn nhẹ', N'Sandwich kẹp thịt gà nướng và sốt mayonnaise.'),
(N'Phô mai que', 30000, N'Ăn nhẹ', N'Phô mai mozzarella chiên giòn chảy dẻo.'),
(N'Khoai lang kén', 28000, N'Ăn nhẹ', N'Khoai lang nghiền chiên giòn, ngọt bùi.');*/

USE Brew INSERT INTO MenuItem (item_name, price, category, description) VALUES
(N'Iced Milk Coffee', 25000, N'Beverage', N'Roasted coffee mixed with condensed milk and ice.'),
(N'Black Coffee', 20000, N'Beverage', N'Strong pure coffee without sugar.'),
(N'Salted Coffee', 30000, N'Beverage', N'Coffee blended with salted cream for a rich flavor.'),
(N'Cappuccino', 45000, N'Beverage', N'Espresso topped with steamed milk and milk foam.'),
(N'Latte', 42000, N'Beverage', N'Smooth espresso combined with steamed milk.'),
(N'Mocha', 45000, N'Beverage', N'Espresso mixed with chocolate and milk.'),
(N'Espresso', 35000, N'Beverage', N'Strong Italian-style concentrated coffee.'),
(N'Peach Orange Lemongrass Tea', 35000, N'Beverage', N'Peach tea with fresh orange and lemongrass aroma.'),
(N'Lemon Tea', 25000, N'Beverage', N'Fresh tea blended with lemon juice and ice.'),
(N'Lychee Tea', 32000, N'Beverage', N'Black tea mixed with lychee fruit, sweet and smooth.'),
(N'Kumquat Honey Tea', 28000, N'Beverage', N'Lemon tea mixed with kumquat and pure honey.'),
(N'Milk Tea with Tapioca', 35000, N'Beverage', N'Milk tea with chewy tapioca pearls.'),
(N'Matcha Milk Tea', 38000, N'Beverage', N'Milk tea flavored with Japanese matcha.'),
(N'Fresh Milk with Brown Sugar Pearls', 42000, N'Beverage', N'Cold fresh milk with brown sugar pearls.'),
(N'Lemon Soda', 30000, N'Beverage', N'Refreshing soda combined with fresh lemon.'),
(N'Blueberry Soda', 32000, N'Beverage', N'Soda with sweet and tangy blueberry flavor.'),
(N'Avocado Smoothie', 40000, N'Beverage', N'Creamy smoothie made from fresh avocado.'),
(N'Mango Smoothie', 38000, N'Beverage', N'Smoothie from ripe, naturally sweet mango.'),
(N'Strawberry Smoothie', 38000, N'Beverage', N'Fresh and cool strawberry smoothie.'),
(N'Orange Juice', 30000, N'Beverage', N'Pure orange juice rich in vitamin C.'),
(N'Watermelon Juice', 28000, N'Beverage', N'Fresh watermelon juice, perfect for cooling down.'),
(N'Blended Matcha', 45000, N'Beverage', N'Blended matcha with cream and ice.'),
(N'Caramel Frappuccino', 45000, N'Beverage', N'Blended caramel coffee with whipped cream.'),
(N'Chocolate Frappuccino', 48000, N'Beverage', N'Blended chocolate drink, rich and creamy.'),
(N'Garlic Butter Bread', 30000, N'Snack', N'Toasted bread with garlic butter aroma.'),
(N'Cream Puff', 30000, N'Dessert', N'Soft cream puff filled with vanilla custard.'),
(N'Flan Cake', 20000, N'Dessert', N'Soft caramel flan with sweet and mild flavor.'),
(N'Honey Pancake', 35000, N'Dessert', N'Fluffy pancakes served with honey.'),
(N'French Fries', 30000, N'Snack', N'Crispy golden fries served with ketchup.'),
(N'Fruit Salad', 32000, N'Dessert', N'Mixed fresh fruits with chilled yogurt.'),
(N'Coconut Coffee', 38000, N'Beverage', N'Black coffee blended with creamy coconut milk.'),
(N'Egg Coffee', 40000, N'Beverage', N'Strong coffee topped with whipped egg cream.'),
(N'Cold Brew', 45000, N'Beverage', N'Smooth cold-brewed coffee with low acidity.'),
(N'Affogato', 48000, N'Beverage', N'Espresso poured over vanilla ice cream.'),
(N'Chrysanthemum Honey Tea', 32000, N'Beverage', N'Chrysanthemum tea sweetened with honey.'),
(N'Oolong Milk Tea', 38000, N'Beverage', N'Oolong tea with creamy milk flavor.'),
(N'Jasmine Tea', 28000, N'Beverage', N'Fragrant jasmine tea with a light taste.'),
(N'Apple Cinnamon Tea', 35000, N'Beverage', N'Warm fruit tea with apple and cinnamon.'),
(N'Banana Smoothie', 35000, N'Beverage', N'Creamy banana smoothie with a natural sweetness.'),
(N'Orange Mango Smoothie', 38000, N'Beverage', N'Smoothie made from orange and mango mix.'),
(N'Strawberry Soda', 32000, N'Beverage', N'Sparkling soda mixed with fresh strawberry flavor.'),
(N'Kiwi Soda', 32000, N'Beverage', N'Refreshing soda with mild kiwi taste.'),
(N'Chocolate Donut', 25000, N'Dessert', N'Donut covered with sweet chocolate glaze.'),
(N'Tiramisu Cake', 45000, N'Dessert', N'Tiramisu cake with coffee and mascarpone cream.'),
(N'Butter Cookies', 30000, N'Dessert', N'Crispy butter cookies with vanilla scent.'),
(N'Croissant', 35000, N'Snack', N'Golden-baked, flaky butter croissant.'),
(N'Egg Sandwich', 35000, N'Snack', N'Sandwich with fried egg and fresh vegetables.'),
(N'Grilled Chicken Sandwich', 40000, N'Snack', N'Sandwich with grilled chicken and mayonnaise sauce.'),
(N'Cheese Sticks', 30000, N'Snack', N'Fried mozzarella cheese sticks, crispy outside and gooey inside.'),
(N'Sweet Potato Balls', 28000, N'Snack', N'Fried sweet potato balls with a soft, sweet filling.');

--Bảng chi tiết hóa đơn--
USE Brew CREATE TABLE BillDetail (
    menuId NVARCHAR(50),
	soLuong NVARCHAR(20),
	ammount INT,
	totalPrice FLOAT
);

-- Bảng bàn--
USE Brew CREATE TABLE CafeTable(
	tableId NVARCHAR(10) PRIMARY KEY,
    floor VARCHAR(50) NOT NULL,			     -- Tầng của bàn (ví dụ: Tầng 1, Tầng 2)
	current_occupancy INT,					 -- Sức chứa hiện tại (số người đang ngồi hiện tại)
    capacity INT,                            -- Sức chứa (số người ngồi tối đa)
    status VARCHAR(50)                       -- Trạng thái bàn (VD: Trống, Đang sử dụng, Đã đặt)
);

USE Brew 
GO

CREATE TRIGGER trg_TuDongTaoMaBan
ON CafeTable
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @SoCuoi INT;

    -- Lấy phần số lớn nhất từ tableId hiện có
    SELECT @SoCuoi = ISNULL(MAX(TRY_CAST(SUBSTRING(tableId, 2, LEN(tableId)) AS INT)), 0)
    FROM CafeTable;

    -- Chèn dữ liệu mới với tableId tự động tăng
    INSERT INTO CafeTable (tableId, floor, current_occupancy, capacity, status)
    SELECT
        CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) + @SoCuoi AS VARCHAR(10)),
        floor,
		current_occupancy,
        capacity,
        status
    FROM inserted;
END;
GO

USE Brew INSERT INTO CafeTable (floor, current_occupancy, capacity, status)
VALUES
('Ground Floor', 0, 3, 'AVAILABLE'),
('Ground Floor', 0, 3, 'AVAILABLE'),
('Ground Floor', 0, 3, 'AVAILABLE'),
('Ground Floor', 0, 3, 'AVAILABLE'),
('Ground Floor', 0, 3, 'AVAILABLE'),
('Ground Floor', 0, 3, 'AVAILABLE');

