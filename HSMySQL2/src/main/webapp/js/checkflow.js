// 登入檢查流程
// checkArgsKey -> checkGpsMeter
// 網頁隱藏
document.documentElement.style.display = "none";

// 檢查流程
const checkflow = () => {
	// 1.檢查 Key
	if(checkArgsKey()) {
		return true;
	}
	// 2.檢查 GPS
	if(checkGpsMeter()) {
		return true;
	}
	return false;
};

// 得到檢查流程的結果
if(checkflow()) { // 成功
	// 顯示網頁
	document.documentElement.style.display = "";
} else { // 失敗
	// 網頁全部空白
	document.write('');
}
