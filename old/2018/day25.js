console.log("** DAY 25 **");
console.log("");


////// Part 1

function manhattan(p1, p2) {
	return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]) + Math.abs(p1[2] - p2[2]) + Math.abs(p1[3] - p2[3]);
}

function constellationCount(input) {
	var constellations = [];

	while (input.length != 0) {
		var constellation = [];
		constellation.push(input.shift());

		var end = false;
		while (!end) {
			end = true;
			for (let i = 0; i < input.length; i++) {
				for (let c = 0; c < constellation.length; c++) {
					if (manhattan(input[i], constellation[c]) <= 3) {
						end = false;
						constellation.push(input[i]);
						input.splice(i, 1);
						i--;
						break;
					}
				}
			}
		}

		constellations.push(constellation);
	}

	return constellations.length;
}


function testConstellationCount(input, expected) {
	let t1 = constellationCount(input);
	if (t1 == expected) {
		console.log("- " + input + " -> " + t1 + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testConstellationCount([
	[0, 0, 0, 0],
	[3, 0, 0, 0],
	[0, 3, 0, 0],
	[0, 0, 3, 0],
	[0, 0, 0, 3],
	[0, 0, 0, 6],
	[9, 0, 0, 0],
	[12, 0, 0, 0]
], 2);
testConstellationCount([
	[-1, 2, 2, 0],
	[0, 0, 2, -2],
	[0, 0, 0, -2],
	[-1, 2, 0, 0],
	[-2, -2, -2, 2],
	[3, 0, 2, -1],
	[-1, 3, 2, 2],
	[-1, 0, -1, 0],
	[0, 2, 1, -2],
	[3, 0, 0, 0]
], 4);
testConstellationCount([
	[1, -1, 0, 1],
	[2, 0, -1, 0],
	[3, 2, -1, 0],
	[0, 0, 3, 1],
	[0, 0, -1, -1],
	[2, 3, -2, 0],
	[-2, 2, 0, 0],
	[2, -2, 0, -1],
	[1, -1, 0, -1],
	[3, 2, 0, 2]
], 3);
testConstellationCount([
	[1, -1, -1, -2],
	[-2, -2, 0, 1],
	[0, 2, 1, 3],
	[-2, 3, -2, 1],
	[0, 2, 3, -2],
	[-1, -1, 1, -2],
	[0, -2, -1, 0],
	[-2, 2, 3, -1],
	[1, 2, 2, 0],
	[-1, -2, 0, -2]
], 8);
console.log("");


////// Part 2

/*function polymentImprovement(input) {
	let treated = {};
	for (let i = 0; i < input.length - 1; i++) {
		if (!treated.hasOwnProperty(input[i].toLowerCase())) {
			let pattern = "[" + input[i].toLowerCase() + input[i].toUpperCase() + "]";
			treated[input[i].toLowerCase()] = react(input.replace(new RegExp(pattern, "g"), "")).length;
		}
	}

	let characters = Object.keys(treated);
	let remains = 100000;
	for (let index = 0; index < characters.length; index++) {
		if (treated[characters[index]] < remains) {
			remains = treated[characters[index]];
		}
	}

	return remains;
}


function testPolymerImprovement(input, expected) {
	let t2 = polymentImprovement(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests part 2:");
testPolymerImprovement("dabAcCaCBAcCcaDA", 4);
console.log("");*/


////// Day Answer

function dayAnswer(file) {
	let output = [];

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		output.push(input.split(",").map(x => Number(x)));
	});

	rl.on('close', () => {
		console.log("Constallation count: " + constellationCount(output));
		//console.log("Polymer improvement: " + polymentImprovement(output));
	});
}

dayAnswer("./day25-input");