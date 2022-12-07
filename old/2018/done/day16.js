console.log("** DAY 16 **");
console.log("");


////// Part 1

var debug = false;

function addr(rA, rB, rC, registers) {
	registers[rC] = registers[rA] + registers[rB];
	if (debug) {
		console.log("addr", registers);
	}
	return registers;
}
function addi(rA, vB, rC, registers) {
	registers[rC] = registers[rA] + vB;
	if (debug) {
		console.log("addi", registers);
	}
	return registers;
}

function mulr(rA, rB, rC, registers) {
	registers[rC] = registers[rA] * registers[rB];
	if (debug) {
		console.log("mulr", registers);
	}
	return registers;
}
function muli(rA, vB, rC, registers) {
	registers[rC] = registers[rA] * vB;
	if (debug) {
		console.log("muli", registers);
	}
	return registers;
}

function banr(rA, rB, rC, registers) {
	registers[rC] = registers[rA] & registers[rB];
	if (debug) {
		console.log("banr", registers);
	}
	return registers;
}
function bani(rA, vB, rC, registers) {
	registers[rC] = registers[rA] & vB;
	if (debug) {
		console.log("bani", registers);
	}
	return registers;
}

function borr(rA, rB, rC, registers) {
	registers[rC] = registers[rA] | registers[rB];
	if (debug) {
		console.log("borr", registers);
	}
	return registers;
}
function bori(rA, vB, rC, registers) {
	registers[rC] = registers[rA] | vB;
	if (debug) {
		console.log("bori", registers);
	}
	return registers;
}

function setr(rA, rC, registers) {
	registers[rC] = registers[rA];
	if (debug) {
		console.log("setr", registers);
	}
	return registers;
}
function seti(vA, rC, registers) {
	registers[rC] = vA;
	if (debug) {
		console.log("seti", registers);
	}
	return registers;
}

function gtir(vA, rB, rC, registers) {
	registers[rC] = (vA > registers[rB]) ? 1 : 0;
	if (debug) {
		console.log("gtir", registers);
	}
	return registers;
}
function gtri(rA, vB, rC, registers) {
	registers[rC] = (registers[rA] > vB) ? 1 : 0;
	if (debug) {
		console.log("gtri", registers);
	}
	return registers;

}
function gtrr(rA, rB, rC, registers) {
	registers[rC] = (registers[rA] > registers[rB]) ? 1 : 0;
	if (debug) {
		console.log("gtrr", registers);
	}
	return registers;

}

function eqir(vA, rB, rC, registers) {
	registers[rC] = (vA == registers[rB]) ? 1 : 0;
	if (debug) {
		console.log("eqir", registers);
	}
	return registers;
}
function eqri(rA, vB, rC, registers) {
	registers[rC] = (registers[rA] == vB) ? 1 : 0;
	if (debug) {
		console.log("eqri", registers);
	}
	return registers;
}
function eqrr(rA, rB, rC, registers) {
	registers[rC] = (registers[rA] == registers[rB]) ? 1 : 0;
	if (debug) {
		console.log("eqrr", registers);
	}
	return registers;
}

function getDuplicates(input) {
	var duplicates = [];
	for (let i = 0; i < input.length; i++) {
		duplicates.push([]);

		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(addr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("addr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(addi(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("addi");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(mulr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("mulr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(muli(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("muli");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(banr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("banr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(bani(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("bani");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(borr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("borr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(bori(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("bori");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(setr(input[i].op[1], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("setr");
		}
		if (JSON.stringify(seti(input[i].op[1], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("seti");
		}
		if (input[i].op[2] <= 3 && JSON.stringify(gtir(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("gtir");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(gtri(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("gtri");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(gtrr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("gtrr");
		}
		if (input[i].op[2] <= 3 && JSON.stringify(eqir(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("eqir");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(eqri(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("eqri");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(eqrr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("eqrr");
		}

		//console.log(duplicates[i]);
	}

	var duplicateCount = 0;
	for (let i = 0; i < duplicates.length; i++) {
		if (duplicates[i].length >= 3) {
			duplicateCount++;
		}
	}

	return duplicateCount;
}



function testGetDuplicates(input, expected) {
	let t1 = getDuplicates(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
// console.log("addr", JSON.stringify(addr(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 6, 4]) ? "ok" : "failed");
// console.log("addi", JSON.stringify(addi(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 5, 4]) ? "ok" : "failed");
// console.log("mulr", JSON.stringify(mulr(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 8, 4]) ? "ok" : "failed");
// console.log("muli", JSON.stringify(muli(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 6, 4]) ? "ok" : "failed");
// console.log("banr", JSON.stringify(banr(1, 3, 2, [1, 5, 3, 3])) == JSON.stringify([1, 5, 1, 3]) ? "ok" : "failed");
// console.log("bani", JSON.stringify(bani(1, 3, 2, [1, 5, 3, 4])) == JSON.stringify([1, 5, 1, 4]) ? "ok" : "failed");
// console.log("borr", JSON.stringify(borr(1, 3, 2, [1, 5, 3, 3])) == JSON.stringify([1, 5, 7, 3]) ? "ok" : "failed");
// console.log("bori", JSON.stringify(bori(1, 5, 2, [1, 3, 3, 4])) == JSON.stringify([1, 3, 7, 4]) ? "ok" : "failed");
// console.log("setr", JSON.stringify(setr(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 2, 4]) ? "ok" : "failed");
// console.log("seti", JSON.stringify(seti(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 1, 4]) ? "ok" : "failed");
// console.log("gtir", JSON.stringify(gtir(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 0, 4]) ? "ok" : "failed");
// console.log("gtir", JSON.stringify(gtir(5, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 1, 4]) ? "ok" : "failed");
// console.log("gtri", JSON.stringify(gtri(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 0, 4]) ? "ok" : "failed");
// console.log("gtri", JSON.stringify(gtri(1, 1, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 1, 4]) ? "ok" : "failed");
// console.log("gtrr", JSON.stringify(gtrr(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 0, 4]) ? "ok" : "failed");
// console.log("gtrr", JSON.stringify(gtrr(3, 1, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 1, 4]) ? "ok" : "failed");
// console.log("eqir", JSON.stringify(eqir(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 0, 4]) ? "ok" : "failed");
// console.log("eqir", JSON.stringify(eqir(4, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 1, 4]) ? "ok" : "failed");
// console.log("eqri", JSON.stringify(eqri(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 0, 4]) ? "ok" : "failed");
// console.log("eqri", JSON.stringify(eqri(1, 2, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 1, 4]) ? "ok" : "failed");
// console.log("eqrr", JSON.stringify(eqrr(1, 3, 2, [1, 2, 3, 4])) == JSON.stringify([1, 2, 0, 4]) ? "ok" : "failed");
// console.log("eqrr", JSON.stringify(eqrr(1, 3, 2, [1, 4, 3, 4])) == JSON.stringify([1, 4, 1, 4]) ? "ok" : "failed");
testGetDuplicates([{
	before: [3, 2, 1, 1],
	op: [9, 2, 1, 2],
	after: [3, 2, 2, 1]
}], 1);
console.log("");


////// Part 2

function identifyOps(input) {
	var duplicates = [];
	for (let i = 0; i < input.length; i++) {
		duplicates.push([]);

		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(addr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("addr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(addi(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("addi");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(mulr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("mulr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(muli(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("muli");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(banr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("banr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(bani(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("bani");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(borr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("borr");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(bori(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("bori");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(setr(input[i].op[1], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("setr");
		}
		if (JSON.stringify(seti(input[i].op[1], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("seti");
		}
		if (input[i].op[2] <= 3 && JSON.stringify(gtir(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("gtir");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(gtri(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("gtri");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(gtrr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("gtrr");
		}
		if (input[i].op[2] <= 3 && JSON.stringify(eqir(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("eqir");
		}
		if (input[i].op[1] <= 3 && JSON.stringify(eqri(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("eqri");
		}
		if (input[i].op[1] <= 3 && input[i].op[2] <= 3 && JSON.stringify(eqrr(input[i].op[1], input[i].op[2], input[i].op[3], JSON.parse(JSON.stringify(input[i].before)))) == JSON.stringify(input[i].after)) {
			duplicates[i].push("eqrr");
		}

		//console.log(input[i].op[0], duplicates[i].join(", "));
	}
}

function runProgram(input) {
	var register = [0, 0, 0, 0];
	for (let i = 0; i < input.length; i++) {
		switch (input[i][0]) {
			case 0:
				register = seti(input[i][1], input[i][3], register);
				break;
			case 1:
				register = eqir(input[i][1], input[i][2], input[i][3], register);
				break;
			case 2:
				register = setr(input[i][1], input[i][3], register);
				break;
			case 3:
				register = gtir(input[i][1], input[i][2], input[i][3], register);
				break;
			case 4:
				register = addi(input[i][1], input[i][2], input[i][3], register);
				break;
			case 5:
				register = muli(input[i][1], input[i][2], input[i][3], register);
				break;
			case 6:
				register = mulr(input[i][1], input[i][2], input[i][3], register);
				break;
			case 7:
				register = gtrr(input[i][1], input[i][2], input[i][3], register);
				break;
			case 8:
				register = bani(input[i][1], input[i][2], input[i][3], register);
				break;
			case 9:
				register = gtri(input[i][1], input[i][2], input[i][3], register);
				break;
			case 10:
				register = bori(input[i][1], input[i][2], input[i][3], register);
				break;
			case 11:
				register = banr(input[i][1], input[i][2], input[i][3], register);
				break;
			case 12:
				register = borr(input[i][1], input[i][2], input[i][3], register);
				break;
			case 13:
				register = eqri(input[i][1], input[i][2], input[i][3], register);
				break;
			case 14:
				register = eqrr(input[i][1], input[i][2], input[i][3], register);
				break;
			case 15:
				register = addr(input[i][1], input[i][2], input[i][3], register);
				break;
			default:
				console.log("error");
		}
	}

	return register;
}


////// Day Answer

function dayAnswer_part1(file) {
	let output = [];

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		var op = null;
		if (input.indexOf("Before") != -1) {
			op = input.substr(9, input.length - 10).split(", ").map(x => Number(x));
			output.push({
				before: [],
				op: [],
				after: []
			});
			output[output.length - 1].before = op;
		} else if (input.indexOf("After") != -1) {
			op = input.substr(9, input.length - 10).split(", ").map(x => Number(x));
			output[output.length - 1].after = op;
		} else if (input != "") {
			op = input.split(" ").map(x => Number(x));
			output[output.length - 1].op = op;
		}
	});

	rl.on('close', () => {
		console.log("Count of duplicates: " + getDuplicates(output));
		identifyOps(output);
	});
}

dayAnswer_part1("./day16-input-part1");


function dayAnswer_part2(file) {
	let output = [];

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		output.push(input.split(" ").map(x => Number(x)));
	});

	rl.on('close', () => {
		console.log("final register: ", runProgram(output));
	});
}

dayAnswer_part2("./day16-input-part2");