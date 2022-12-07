console.log("** DAY 08 **");
console.log("");


////// Part 1

function extractNode(input) {
	var childNodeCount = input.shift();
	var metadataCount = input.shift();
	var childs = [];
	for (let i = 0; i < childNodeCount; i++) {
		childs.push(extractNode(input));
	}
	var metadatas = [];
	for (let i = 0; i < metadataCount; i++) {
		metadatas.push(input.shift());
	}
	return {
		childs: childs,
		metadatas: metadatas,
		childNodeCount: childNodeCount,
		metadataCount: metadataCount
	};
}

function parseLicense(input) {
	return extractNode(input.split(" ").map(Number));
}

function getChecksum(input) {
	var checksum = input.metadatas.reduce((a, b) => a + b, 0);
	for (let i = 0; i < input.childs.length; i++) {
		checksum += getChecksum(input.childs[i]);
	}
	return checksum;
}

function licenseChecksum(input) {
	return getChecksum(parseLicense(input));
}


function testChecksum(input, expected) {
	let t1 = licenseChecksum(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testChecksum("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", 138);
console.log("");


////// Part 2

function getValue(input) {
	var value = 0;

	if (input.childNodeCount == 0) {
		value = input.metadatas.reduce((a, b) => a + b, 0);
	} else {
		for (let i = 0; i < input.metadatas.length; i++) {
			if (input.childs[input.metadatas[i] - 1]) {
				value += getValue(input.childs[input.metadatas[i] - 1]);
			}
		}
	}

	return value;
}

function licenseValue(input) {
	return getValue(parseLicense(input));
}


function testLicenseValue(input, expected) {
	let t2 = licenseValue(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests part 2:");
testLicenseValue("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", 66);
console.log("");


////// Day Answer

function dayAnswer(file) {
	let output = "";

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		output = input;
	});

	rl.on('close', () => {
		console.log("Lisence metadata checksum: " + licenseChecksum(output));
		console.log("License value: " + licenseValue(output));
	});
}

dayAnswer("./day08-input");