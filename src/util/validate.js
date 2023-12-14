export function validPnumber(str) {
	// 使用正则表达式进行校验
	const pattern = /^1\d{10}$/;
	return pattern.test(str);
}
