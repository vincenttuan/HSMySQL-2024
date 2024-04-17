const REMOTE_URL = '';

const $ = (id) => document.getElementById(id);

// 通用函數，用於檢查事件目標是否包含指定的類別，如果是則執行回調函數
const handleEvent = async (event, className, callback) => {
	var gpsId;
    if (event.target.classList.contains(className)) {
		switch(className) {
			case 'gps-add': // 新增GPS
				await callback();
				break;
			case 'update-gps-button': // 修改客戶
			case 'delete-gps-button': // 刪除客戶
				gpsId = event.target.getAttribute('data-id');
				await callback(gpsId);
				break;	
		}
    }
};
// 新增GPS的函數
const handleAddGPS = async () => {
	
	// 使用 Swal.fire
	const result = await Swal.fire({
	    title: '新增GPS',
	    html:`
	        <input id="gps-latitude-input" class="swal2-input" placeholder="請輸入緯度">
	        <input id="gps-longitude-input" class="swal2-input" placeholder="請輸入經度">
	        <input id="gps-location-input" class="swal2-input" placeholder="請輸入地址">
	        <input id="gps-locationName-input" class="swal2-input" placeholder="請輸入單位名稱">
	        <input id="gps-meter-input" type="number" class="swal2-input" placeholder="請輸入公尺">`,
	    focusConfirm: false,
	    preConfirm: () => {
	        const latitude = $('gps-latitude-input').value;
	        const longitude = $('gps-longitude-input').value;
	        const location = $('gps-location-input').value;
	        const locationName = $('gps-locationName-input').value;
	        const meter = $('gps-meter-input').value;
	        
	        // 檢查輸入值，如果需要的話
	        if (!latitude || !longitude || !location || !locationName || !meter) {
	            Swal.showValidationMessage("所有欄位都是必填的");
	            return false; // 阻止彈窗關閉
	        }
	
	        return { latitude, longitude, location, locationName, meter };
	    }
	});
	
	if (result.value) {
		console.log(result.value);
	} else {
	    console.log('No values');
	    return;
	}
	
	const fullUrl = `${REMOTE_URL}../mvc/gps`;
	try {
		const response = await fetch(fullUrl, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				latitude: result.value.latitude,
				longitude: result.value.longitude,
				location: result.value.location,
				locationName: result.value.locationName,
				meter: result.value.meter
			})
		});
		if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
		const { status, message } = await response.json();
		if (!status) throw new Error(`Failed to add customer: ${message}`);
		// 重新渲染GPS列表
		fetchAndRenderData('../mvc/gps', 'gps-body', renderGPS);
	} catch (error) {
		console.error('Error adding customer:', error);
	}
};

// 處理修改GPS的異步邏輯
const handleUpdateGPS = async (gpsId) => {
	const getGPSUrl = `${REMOTE_URL}../mvc/gps/${gpsId}`;
	const gpsResponse = await fetch(getGPSUrl);
	const { status, message, data } = await gpsResponse.json();
	console.log(status, message, data);
	
	// 使用 Swal.fire 白底彈窗請求修改用戶
	const result = await Swal.fire({
		title: '修改GPS',
		html: `
            <input id="gps-latitude-input" class="swal2-input" value="${data.latitude}" placeholder="請輸入緯度">
	        <input id="gps-longitude-input" class="swal2-input" value="${data.longitude}" placeholder="請輸入經度">
	        <input id="gps-location-input" class="swal2-input" value="${data.location}" placeholder="請輸入地址">
	        <input id="gps-locationName-input" class="swal2-input" value="${data.locationName}" placeholder="請輸入單位名稱">
	        <input id="gps-meter-input" type="number" class="swal2-input" value="${data.meter}" placeholder="請輸入公尺">`,
		focusConfirm: false,
		preConfirm: () => {
	        const latitude = $('gps-latitude-input').value;
	        const longitude = $('gps-longitude-input').value;
	        const location = $('gps-location-input').value;
	        const locationName = $('gps-locationName-input').value;
	        const meter = $('gps-meter-input').value;
	        
	        // 檢查輸入值，如果需要的話
	        if (!latitude || !longitude || !location || !locationName || !meter) {
	            Swal.showValidationMessage("所有欄位都是必填的");
	            return false; // 阻止彈窗關閉
	        }
	
	        return { latitude, longitude, location, locationName, meter };
	    }
	});
	
	if (result.value) {
		console.log(result.value);
		console.log('Role:', result.value.role);
		console.log('Username:', result.value.username);
	} else {
		console.log('No values');
		return;
	}
	
	// 進行遠端修改
	const fullUrl = `${REMOTE_URL}../mvc/gps/${gpsId}`;
	try {
		const response = await fetch(fullUrl, {
			method: 'PATCH',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				latitude: result.value.latitude,
				longitude: result.value.longitude,
				location: result.value.location,
				locationName: result.value.locationName,
				meter: result.value.meter
			})
		});
		if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
		const { status, message } = await response.json();
		if (!status) throw new Error(`Failed to add customer: ${message}`);
		// 重新渲染GPS列表
		fetchAndRenderData('../mvc/gps', 'gps-body', renderGPS);
	} catch (error) {
		console.error('Error adding customer:', error);
	}
	
};

// 處理刪除客戶的異步邏輯
const handleDeleteGPS = async (gpsId) => {
	handleDelete(`${REMOTE_URL}../mvc/gps/${gpsId}`, () => fetchAndRenderData('../mvc/gps', 'gps-body', renderGPS));
};

// 渲染GPS列表的函數
const renderGPS = ({ id, latitude, longitude, location, locationName, meter }) => `
    <tr>
        <td>${id}</td>
        <td>${latitude}</td>
        <td>${longitude}</td>
        <td>${location}</td>
        <td>${locationName}</td>
        <td>${meter}</td>
        <td title='修改'><span class="button-update pure-button update-gps-button" data-id="${id}">修改</span></td>
        <td title='刪除'><span class="button-delete pure-button delete-gps-button" data-id="${id}">刪除</span></td>
	</tr>`;

// 通用刪除
// 使用 Swal.fire 跳出 yes/no 彈窗來決定是否要刪除
const handleDelete = async (fullUrl, fetchAndRender) => {
	const result = await Swal.fire({
		title: '確定要刪除嗎?',
		text: "刪除後將無法恢復!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: '是的，刪除它!',
		cancelButtonText: '取消'
	});
	if (result.isConfirmed) {
		console.log('Confirmed');
	} else {
		console.log('Cancelled');
		return;
	}

	// 進行遠端刪除
	// delete fullUrl
	try {
		const response = await fetch(fullUrl, { method: 'DELETE' });
		if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
		const { status, message } = await response.json();
		if (!status) throw new Error(`Failed to delete: ${message}`);
		// 重新渲染列表
		fetchAndRender();
	} catch (error) {
		console.error('Delete error', fullUrl, error);
		// Swal.fire dialog
		Swal.fire({
			title: '刪除失敗!',
			text: error,
			icon: 'error',
			confirmButtonText: '確定'
		});
	}
};

// 使用解構賦值和模板字符串來簡化代碼
const fetchAndRenderData = async (url, containerId, renderFn) => {
	url = REMOTE_URL + url;
    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        const { status, message, data } = await response.json();
        console.log(status, message, data, containerId);
        if (!status) throw new Error(`Failed to fetch data: ${message}`);
		
		// data.map(renderFn) 會得到一個數組，其中每個元素是 renderFn 函數的返回值
		// .join('') 用於將數組轉換為字符串給 innerHTML 屬性當作參數使用 
		// Array.isArray(data) 用於判斷 data 是否是數組
		// 如果 data 是數組，則使用 data.map(renderFn).join('') 作為 innerHTML 的參數
		// 如果 data 不是數組，則直接使用 renderFn(data) 作為 innerHTML 的參數
        $(containerId).innerHTML = Array.isArray(data) ? data.map(renderFn).join('') : renderFn(data);
    } catch (error) {
        console.error(`Error fetching data from ${url}:`, error);
        $(containerId).innerHTML = `<tr><td colspan="6">沒有權限讀取</td></tr>`;
    }
};

// 定義一個異步函數來加載 HTML 內容
const loadHTML = async (url, containerId) => {
	const fullUrl = REMOTE_URL + url;
    try {
        const response = await fetch(fullUrl); // 等待 fetch 请求完成
        const data = await response.text(); // 等待响应的文本内容
        $(containerId).innerHTML = data; // 将加载的 HTML 内容设置到指定的容器中
    } catch (error) {
        console.error('Error loading the content:', error); // 处理可能的错误
    }
};

//-----------------------------------------------------------------------------------------------------
// 啟動點: 等待 DOM 加載完成後再執行
document.addEventListener("DOMContentLoaded", async () => {
	// 加載 GPS HTML 內容
	await loadHTML('gps.html', 'gps-container');
	
	// 加載 GPS 列表資料
	fetchAndRenderData('../mvc/gps', 'gps-body', renderGPS);
	
	// GPS 新增按鈕點擊事件
	$("gps-add-link").addEventListener("click", async (event) => {
		event.preventDefault();  // 取消默認動作，這裡是阻止超鏈接跳轉
		console.log(event.target);
		await handleEvent(event, 'gps-add', handleAddGPS);
	});
	
	$("gps-body").addEventListener("click", async (event) => {
		event.preventDefault();  // 取消默認動作，這裡是阻止超鏈接跳轉
		console.log(event.target);
		await handleEvent(event, 'update-gps-button', handleUpdateGPS);
		await handleEvent(event, 'delete-gps-button', handleDeleteGPS);
	});
});