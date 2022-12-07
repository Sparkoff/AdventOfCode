console.log("** DAY 19 **");
console.log("");


////// Part 1

function execute(opcode, A, B, C, registers) {
	switch (opcode) {
		case "addr":
			registers[C] = registers[A] + registers[B];
			break;
		case "addi":
			registers[C] = registers[A] + B;
			break;

		case "mulr":
			registers[C] = registers[A] * registers[B];
			break;
		case "muli":
			registers[C] = registers[A] * B;
			break;

		case "banr":
			registers[C] = registers[A] & registers[B];
			break;
		case "bani":
			registers[C] = registers[A] & B;
			break;

		case "borr":
			registers[C] = registers[A] | registers[B];
			break;
		case "bori":
			registers[C] = registers[A] | B;
			break;

		case "setr":
			registers[C] = registers[A];
			break;
		case "seti":
			registers[C] = A;
			break;

		case "gtir":
			registers[C] = (A > registers[B]) ? 1 : 0;
			break;
		case "gtri":
			registers[C] = (registers[A] > B) ? 1 : 0;
			break;

		case "gtrr":
			registers[C] = (registers[A] > registers[B]) ? 1 : 0;
			break;


		case "eqir":
			registers[C] = (A == registers[B]) ? 1 : 0;
			break;
		case "eqri":
			registers[C] = (registers[A] == B) ? 1 : 0;
			break;
		case "eqrr":
			registers[C] = (registers[A] == registers[B]) ? 1 : 0;
			break;
		
		default:
			console.log("unknown instruction");
	}
	return registers
}

function runProgam(input) {
	var registers = [0, 0, 0, 0, 0, 0];

	var count = 1;
	var ip = 0;
	while (ip <= input.instructions.length - 1) {
		registers[input.ip] = ip;

		var ins = input.instructions[ip];
		registers = execute(ins[0], ins[1], ins[2], ins[3], registers);
		//console.log(registers);

		ip = registers[input.ip];

		ip++;

		count++;
		if (count % 10000000 == 0) {
			console.log("count:", count);
		}
	}
	console.log("count:", count);
	console.log(registers);

	return registers[0];
}



function testRunProgam(input, expected) {
	let t1 = runProgam(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testRunProgam({
	ip: 0,
	instructions: [
		["seti", 5, 0, 1],
		["seti", 6, 0, 2],
		["addi", 0, 1, 0],
		["addr", 1, 2, 3],
		["setr", 1, 0, 0],
		["seti", 8, 0, 4],
		["seti", 9, 0, 5]
	]
}, 6);
console.log("");


////// Part 2

function runSecondProgam(input) {
	var registers = [1, 0, 0, 0, 0, 0];
	//var registers = [6, 4, 10551367, 4, 10551367, 10551367];

	var count = 1;
	var ip = 0;
	while (ip <= input.instructions.length - 1) {
		registers[input.ip] = ip;

		var ins = input.instructions[ip];
		//console.log(registers, ins);
		registers = execute(ins[0], ins[1], ins[2], ins[3], registers);

		ip = registers[input.ip];

		ip++;

		count++;

		if (count > 40) {
			console.log(registers, ins);
			registers = [registers[0], registers[1], registers[5], 4, registers[5], registers[5]];
			ip = 4;
			count = 1;
		}
	}
	console.log("count:", count);
	console.log(registers);

	return registers[0];
}


////// Day Answer

function dayAnswer_part1(file) {
	let output = {
		ip: 0,
		instructions: []
	};

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		if (input.indexOf("#ip") != -1) {
			var ip = input.split(" ");
			output.ip = Number(ip[1]);
		} else {
			var ins = input.split(" ");
			ins[1] = Number(ins[1]);
			ins[2] = Number(ins[2]);
			ins[3] = Number(ins[3]);
			output.instructions.push(ins)
		}
	});

	rl.on('close', () => {
		console.log("Program end value: " + runProgam(output));
		//console.log("Second program ens value: " + runSecondProgam(output));

		(function () {
			var number = 10551367;
			var tot = 0;
			for (let i = 1; i <= number; i++) {
				if (number % i == 0) {
					tot += i;
				}
			}
			console.log(tot);
		})();
	});
}

dayAnswer_part1("./day19-input");