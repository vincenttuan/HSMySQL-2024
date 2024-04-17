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
		console.log("status=" + status);
		globalCheckArgsKeyStatus = status;
		return status;
	} catch (e) {
		console.log("No key");
		return false;
	}
};
