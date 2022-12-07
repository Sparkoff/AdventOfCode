console.log("** DAY 03 **");
console.log("");


////// Part 1

function covering(input) {
	let fabricMap = {};
	let output = 0;

	for (let index = 0; index < input.length; index++) {
		let pos = input[index].split(": ");
		let dim = pos[1].split("x");
		pos = pos[0].split(" @ ")[1].split(",");

		if (dim[0] != 0 && dim[1] != 0) {
			for (let ix = Number(pos[0]) + 1; ix <= Number(pos[0]) + Number(dim[0]); ix++) {
				for (let iy = Number(pos[1]) + 1; iy <= Number(pos[1]) + Number(dim[1]); iy++) {
					if (fabricMap.hasOwnProperty(ix + "." + iy)) {
						fabricMap[ix + "." + iy]++;
					} else {
						fabricMap[ix + "." + iy] = 1;
					}
				}
			}
		}
	}

	cells = Object.keys(fabricMap);
	for (let index = 0; index < cells.length; index++) {
		if (fabricMap[cells[index]] > 1) {
			output++;
		}
	}

	return output;
}


function testCovering(input, expected) {
	let t1 = covering(input);
	if (t1 == expected) {
		console.log("- " + input + " -> " + t1 + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testCovering([
	"#1 @ 1,3: 4x4",
	"#2 @ 3,1: 4x4",
	"#3 @ 5,5: 2x2"
], 4);
console.log("");


////// Part 2

function uncovered(input) {
	let fabricMap = {};

	for (let index = 0; index < input.length; index++) {
		let pos = input[index].split(": ");
		let dim = pos[1].split("x");
		pos = pos[0].split(" @ ");
		let id = pos[0];
		pos = pos[1].split(",");
		
		if (dim[0] != 0 && dim[1] != 0) {
			for (let ix = Number(pos[0]) + 1; ix <= Number(pos[0]) + Number(dim[0]); ix++) {
				for (let iy = Number(pos[1]) + 1; iy <= Number(pos[1]) + Number(dim[1]); iy++) {
					if (fabricMap.hasOwnProperty(ix + "." + iy)) {
						fabricMap[ix + "." + iy].count++;
						fabricMap[ix + "." + iy].ids.push(id);
					} else {
						fabricMap[ix + "." + iy] = {
							count: 1,
							ids: []
						};
						fabricMap[ix + "." + iy].ids.push(id);
					}
				}
			}
		}
	}

	cells = Object.keys(fabricMap);
	let lonelyIDS = [];
	let coveredIDS = [];
	for (let index = 0; index < cells.length; index++) {
		let ids = fabricMap[cells[index]].ids;
		if (fabricMap[cells[index]].count == 1) {
			if (lonelyIDS.indexOf(ids[0]) == -1) {
				lonelyIDS.push(ids[0]);
			}
		} else {
			for (let id = 0; id < ids.length; id++) {
				if (coveredIDS.indexOf(ids[id]) == -1) {
					coveredIDS.push(ids[id]);
				}
			}
		}
	}

	for (let index = 0; index < lonelyIDS.length; index++) {
		if (coveredIDS.indexOf(lonelyIDS[index]) == -1) {
			return lonelyIDS[index];
		}
	}
}


////// Day Answer

function dayAnswer(file) {
	let output = [];

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		output.push(input);
	});

	rl.on('close', () => {
		console.log("Covering: " + covering(output));
		console.log("Uncovered ID: " + uncovered(output));
	});
}

dayAnswer("./day03-input");