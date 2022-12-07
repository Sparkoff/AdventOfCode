console.log("** DAY 21 **");
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

function printReg(reg) {
	var line = [];
	for (let i = 0; i < reg.length; i++) {
		var v = reg[i].toString();
		while (v.length < 12){
			v = " " + v;
		}
		line.push(v);
	}
	return "[ " + line.join(", ") + " ]";
}

function runCheckValue(input) {
	var registers = [10961197, 65536, 255, 19, 10626258, 254];
	//var registers = [1,0,0,0,0,0];

	var cycle = 1;
	var ip = 19;
	while (ip <= input.instructions.length - 1 && cycle < 10000000) {
		registers[input.ip] = ip;

		var ins = input.instructions[ip];
		console.log(printReg(registers), ins);
		registers = execute(ins[0], ins[1], ins[2], ins[3], registers);

		ip = registers[input.ip];

		ip++;

		cycle++;
		if (cycle % 10000000 == 0) {
			console.log("cycle:", cycle, i);
		}
	}

	return int;
}


////// Part 2

function runCheckMinimalValue(input) {
	//var registers = [10961197, 65536, 255, 19, 10626258, 254];
	var registers = [0,0,0,0,0,0];

	var refs = [];

	var cycle = 1;
	var ip = 0;
	while (ip <= input.instructions.length - 1) {
		registers[input.ip] = ip;

		var ins = input.instructions[ip];
		//console.log(printReg(registers), ins);
		registers = execute(ins[0], ins[1], ins[2], ins[3], registers);

		ip = registers[input.ip];

		ip++;

		if (ip == 28) {
			if (refs.indexOf(registers[4]) == -1) {
				refs.push(registers[4]);
				console.log(registers[4], cycle);
			} else {
				console.log(registers[4] + " already in refs");
				return refs[refs.length - 1];
			}
		}

		cycle++;
		// if (cycle % 10000000 == 0) {
		// 	console.log("cycle:", cycle);
		// }
	}

	
}


////// Day Answer

function dayAnswer(file) {
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
		//console.log("lowest positve int value on shortest cycle: " + runCheckValue(output));
		console.log("lowest positve int value on longest cycle: " + runCheckMinimalValue(output));
	});
}

dayAnswer("./day21-input");