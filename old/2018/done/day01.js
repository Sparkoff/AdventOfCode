console.log("** DAY 01 **");
console.log("");


////// Part 1

function adjustFrequency(input) {
	let f = 0;

	let seq = input.split(", ");

	for (let index = 0; index < seq.length; index++) {
		let elt = seq[index];
		let sign = elt[0];
		elt = elt.slice(1);

		if (sign == "+") {
			f += Number(elt);
		} else {
			f -= Number(elt);
		}
	}

	return f;
}


function testAdjustement(input, expected) {
	let t1 = adjustFrequency(input);
	if (t1 == expected) {
		console.log("- " + input + " -> " + t1 + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests frequence:");
testAdjustement("+1, -2, +3, +1", 3);
testAdjustement("+1, +1, +1", 3);
testAdjustement("+1, +1, -2", 0);
testAdjustement("-1, -2, -3", -6);
console.log("");


////// Part 2

function calibrate(input) {
	let f = 0;
	let freqList = [];
	freqList.push(f);

	let seq = input.split(", ");

	while (true) {
		for (let index = 0; index < seq.length; index++) {
			let elt = seq[index];
			let sign = elt[0];
			elt = elt.slice(1);

			if (sign == "+") {
				f += Number(elt);
			} else {
				f -= Number(elt);
			}

			if (freqList.indexOf(f) == -1) {
				freqList.push(f);
			} else {
				return f;
			}
		}
	}
}


function testCalibration(input, expected) {
	let t2 = calibrate(input);
	if (t2 == expected) {
		console.log("- " + input + " -> " + t2 + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests first duplicate frequence:");
testCalibration("+1, -1", 0);
testCalibration("+3, +3, +4, -2, -4", 10);
testCalibration("-6, +3, +8, +5, -6", 5);
testCalibration("+7, +7, -2, -7, -4", 14);
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
		if (output == "") {
			output = input;
		} else {
			output += ", " + input;
		}
	});

	rl.on('close', () => {
		console.log(adjustFrequency(output));
		console.log(calibrate(output));
	});
}

dayAnswer("./day01-input");