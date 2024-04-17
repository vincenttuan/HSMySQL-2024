-- GPS 資料表
CREATE TABLE IF NOT Exists gps(
    id int NOT NULL AUTO_INCREMENT, -- 主鍵(GPS序號)
    latitude double not null, -- (緯度)
    longitude double not null, -- (經度)
    meter int not null, -- (誤差公尺)
    location varchar(50) not null UNIQUE, -- (位置)
    location_name varchar(50) not null UNIQUE, -- (場所名稱)
    PRIMARY KEY (id)
);

insert into gps(latitude, longitude, meter, location, location_name) values (24.99782, 121.30977, 5, '桃園市桃園區新生路64、66號', '診所');
insert into gps(latitude, longitude, meter, location, location_name) values (25.00195, 121.30668, 5, '桃園市桃園區中正路176號7樓', '公司');