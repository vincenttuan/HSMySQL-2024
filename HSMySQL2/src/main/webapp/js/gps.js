/**
 * GPS定位
 */
// 檢查瀏覽器是否支援Geolocation API

if ("geolocation" in navigator) {
	// 隱藏網頁
	document.documentElement.style.display = "none";
	// 支援Geolocation API
	navigator.geolocation.getCurrentPosition(function(position) {
		// 獲取使用者目前位置的經緯度
		var userLatitude = position.coords.latitude;
		var userLongitude = position.coords.longitude;
		// 只取到小數點 5 位
		userLatitude = parseFloat(userLatitude.toFixed(5));
		userLongitude = parseFloat(userLongitude.toFixed(5));
		// 印出使用者位置資訊
		console.log("使用者緯度: " + userLatitude);
		console.log("使用者經度: " + userLongitude);

		// 正確使用 userLatitude
		const url = `/HSMySQL/mvc/gps/findGpsWithinDistance?lng=${userLongitude}&lat=${userLatitude}`;
		console.log(url);
		// 使用 fetch 發送請求
		fetch(url)
			.then(response => {
				// 檢查響應是否成功
				if (response.ok) {
					return response.json(); // 將響應體解析為 JSON
				}
			})
			.then(apiResponse => {
				// 處理數據
				console.log(apiResponse);
				//const resp = JSON.parse(apiResponse);
				console.log(apiResponse.status);
				if (apiResponse.status == 'false' || apiResponse.status == false) {
					alert('無法打卡');
					return;
				}
				// 顯示網頁
				document.documentElement.style.display = "";

			})
			.catch(error => {
				// 處理錯誤情況
				console.error('There has been a problem with your fetch operation:', error);

			});

	}, function(error) {
		// 錯誤回調函數
		console.error("Error Code = " + error.code + " - " + error.message);
	}, {
		enableHighAccuracy: true, // 要求高準確度
		timeout: 5000,            // 最長等待 5 秒
		maximumAge: 0             // 不接受緩存的位置信息
	});
} else {
	// 不支援Geolocation API
	console.log("此瀏覽器不支援地理位置功能。");
	alert('GPS 不開啟無法打卡');
	// 網頁全部空白
	document.write('');
}
