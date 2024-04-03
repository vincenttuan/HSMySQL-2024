const REMOTE_URL = '';

const $ = (id) => document.getElementById(id);

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
// 等待 DOM 加載完成後再執行
document.addEventListener("DOMContentLoaded", async () => {
	await loadHTML('gps.html', 'gps-container');
});