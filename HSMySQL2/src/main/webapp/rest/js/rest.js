const REMOTE_URL = '';

const $ = (id) => document.getElementById(id);


// 渲染GPS列表的函數
const renderGPS = ({ id, latitude, longitude, location, locationName, meter }) => `
    <tr>
        <td>${id}</td>
        <td>${latitude}</td>
        <td>${longitude}</td>
        <td>${location}</td>
        <td>${locationName}</td>
        <td>${meter}</td>
    </tr>`;

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
	await loadHTML('gps.html', 'gps-container');
	
	fetchAndRenderData('../mvc/gps', 'gps-body', renderGPS);
	
});