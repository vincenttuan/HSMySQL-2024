// 登入檢查流程
// checkArgsKey -> checkGpsMeter
var globalCheckArgsKeyStatus = null;
var globalCheckGpsMeterStatus = null;
var delayTime = 2000;

// 網頁隱藏
document.documentElement.style.display = "none";
// 檢查 key 是否正確
const checkArgsKey = async() => {
	// 取得 URL key 資料
	// 例如: /HSMySQL/mvc/argkey/check?key=abc123
	try {
		const url1 = window.location.href;
		const args = url1.split("?")[1];
		const key = args.split("=")[1];
		console.log("key=" + key);
		// 檢查 key 是否正確 
		// 到 /HSMySQL/mvc/argkey/check?key=abc123 檢查 key 是否正確
		const url2 = "/HSMySQL/mvc/argkey/check?key=" + key;
		const response = await fetch(url2);    // 送出 request 並取得 response
		const result = await response.json(); // 將 response 轉 json
		console.log("result=" + result);
		// 取得 status
		const status = result.status;
		console.log("key result=" + JSON.stringify(result));
		globalCheckArgsKeyStatus = status;
		return status;
	} catch (e) {
		console.log("No key");
		globalCheckArgsKeyStatus = false;
	}
	return false;
};

const checkGpsMeter = async() => {
	
	if ("geolocation" in navigator) {
		// 隱藏網頁
		document.documentElement.style.display = "none";
		// 支援Geolocation API
		 navigator.geolocation.getCurrentPosition(async(position) => {
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
			const response = await fetch(url);    // 送出 request 並取得 response
			const result = await response.json(); // 將 response 轉 json
			console.log("gps result=" + JSON.stringify(result));
			// 取得 status
			const status = result.status;	
			globalCheckGpsMeterStatus = status;
			return status;
		}, function(error) {
			// 錯誤回調函數
			console.error("Error Code = " + error.code + " - " + error.message);
			globalCheckGpsMeterStatus = false;
	
		}, {
			enableHighAccuracy: true, // 要求高準確度
			timeout: 5000,            // 最長等待 5 秒
			maximumAge: 0             // 不接受緩存的位置信息
		});
	} else {
		// 不支援Geolocation API
		globalCheckGpsMeterStatus = false;
		console.log("此瀏覽器不支援地理位置功能。");
	}
	
};

function delay(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

// 檢查流程
checkArgsKey();
checkGpsMeter();

// 檢查時間
delay(delayTime).then(async() => {
	console.log("驗證結果", "key", globalCheckArgsKeyStatus, "gps", globalCheckGpsMeterStatus);
	if(globalCheckArgsKeyStatus || globalCheckGpsMeterStatus) {
		// 顯示網頁
		document.documentElement.style.display = "";
		// 若 globalCheckArgsKeyStatus 為 true 則要改變 key
		if(globalCheckArgsKeyStatus) {
			var newKey = prompt("單次登入key正確，請輸入新的 key", "");
			// 若 newKey 為空值則不執行
			if (newKey == null || newKey == "") {
				return;
			}
			const response = await fetch('/HSMySQL/mvc/argkey', {
			    method: 'PATCH', // HTTP 請求方法
			    headers: {
			      'Content-Type': 'application/json'
			    },
			    body: `{"strArg1": "${newKey}"}`
			  });
			const result = await response.json(); 
			console.log(result);
		}
	} else {
		// 網頁全部空白
		document.write('');
	}
});