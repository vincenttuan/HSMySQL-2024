/**
 * GPS定位
 */
// 檢查瀏覽器是否支援Geolocation API
if ("geolocation" in navigator) {
	// 支援Geolocation API
	navigator.geolocation.getCurrentPosition(function(position) {
		// 獲取使用者目前位置的經緯度
		var userLatitude = position.coords.latitude;
		var userLongitude = position.coords.longitude;
		// 印出使用者位置資訊
		console.log("使用者緯度: " + userLatitude);
		console.log("使用者經度: " + userLongitude);

		// 目標位置的經緯度
		var targetLatitude = 24.997726004837432; // 例如：目標位置的緯度
		var targetLongitude = 121.3097577355822; // 例如：目標位置的經度

		// 呼叫函式計算兩個地點之間的距離
		calculateDistance(userLatitude, userLongitude, targetLatitude, targetLongitude);
	});
} else {
	// 不支援Geolocation API
	console.log("此瀏覽器不支援地理位置功能。");
	alert('GPS 不開啟無法打卡');
	// 網頁全部空白
	document.write('');
}

// 計算兩個位置之間的距離
function calculateDistance(lat1, lon1, lat2, lon2) {
	var R = 6371; // 地球的半徑（單位：公里）
	var dLat = deg2rad(lat2 - lat1);
	var dLon = deg2rad(lon2 - lon1);
	var a =
		Math.sin(dLat / 2) * Math.sin(dLat / 2) +
		Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
		Math.sin(dLon / 2) * Math.sin(dLon / 2);
	var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	var distance = R * c; // 距離（單位：公里）
	console.log("兩個地點之間的距離為: " + distance + " 公里");
	
	if (distance > 0.005) {
		alert('無法打卡');
		// 網頁全部空白
		document.write('');
	}
}

// 將角度轉換為弧度
function deg2rad(deg) {
	return deg * (Math.PI / 180);
}