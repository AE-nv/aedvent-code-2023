import fs from "fs";

const numberMap = {
	zero: "0",
	one: "1",
	two: "2",
	three: "3",
	four: "4",
	five: "5",
	six: "6",
	seven: "7",
	eight: "8",
	nine: "9"
};
const numbers = Object.values(numberMap);

function extractNumbers(line) {
	const extracted = [];
	let temp = "";
	line.split('')
		.forEach(char => {
		if (numbers.includes(char)) {
			extracted.push(char);
		} else {
			temp += char;
			Object.keys(numberMap).forEach(key => {
				if (temp.endsWith(key)) {
					extracted.push(numberMap[key]);
				}
			});
		}
	});
	return extracted.join('');
}

const file = fs.readFileSync('./input.txt');
const lines = file.toString('utf8').split('\n');
	
const result = lines
	.filter(line => line.length)
	.map(line => extractNumbers(line))
	.map(line => {
		if (line.length === 1) {
			return line.repeat(2);
		} else {
			return `${line.charAt(0)}${line.slice(-1)}`;
		}
	})
	.map(line => parseInt(line))
	.reduce((curr, acc) => curr += acc, 0);

console.log(result);
