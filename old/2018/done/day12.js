console.log("** DAY 12 **");
console.log("");


////// Part 1

function toState(input, start) {
	var state =  {};
	for (let i = 0; i < input.length; i++) {
		state[start] = input[i];
		start++;
	}
	return state;
}

function compareState(state1, state2) {
	var idx1 = Object.keys(state1);
	var idx2 = Object.keys(state2);
	
	for (let i = 0; i < idx1.length; i++) {
		if (!state2.hasOwnProperty(idx1[i]) && state1[idx1[i]] == "#") {
			return false;
		} else if (state1[idx1[i]] != state2[idx1[i]]) {
			return false;
		}
	}
	for (let i = 0; i < idx2.length; i++) {
		if (!state1.hasOwnProperty(idx2[i]) && state2[idx2[i]] == "#") {
			return false;
		} else if (state1[idx2[i]] != state2[idx2[i]]) {
			return false;
		}
	}
	return true;
}

function nextGeneration(currentState, rules) {
	var next = {};
	var idx = Object.keys(currentState);
	idx.sort(function (a,b) {
		return Number(a) - Number(b);
	});
	currentState[(Number(idx[0]) - 3).toString()] = ".";
	currentState[(Number(idx[0]) - 2).toString()] = ".";
	currentState[(Number(idx[0]) - 1).toString()] = ".";
	currentState[(Number(idx[idx.length - 1]) + 1).toString()] = ".";
	currentState[(Number(idx[idx.length - 1]) + 2).toString()] = ".";
	currentState[(Number(idx[idx.length - 1]) + 3).toString()] = ".";
	idx = Object.keys(currentState);
	for (let i = 0; i < idx.length; i++) {
		var section = "";
		for (let c = Number(idx[i]) - 2; c <= Number(idx[i]) + 2; c++) {
			if (!currentState.hasOwnProperty(c.toString())) {
				currentState[c.toString()] = ".";
				next[c.toString()] = ".";
			}
			section += currentState[c.toString()];
		}
		next[idx[i]] = rules.includes(section) ? "#" : ".";
	}

	idx = Object.keys(next);
	idx.sort(function (a, b) {
		return Number(a) - Number(b);
	});
	for (let i = 0; i < idx.length; i++) {
		if (next[idx[i]] == ".") {
			delete next[idx[i]];
		} else {
			break;
		}
	}
	for (let i = idx.length - 1; i >= 0; i--) {
		if (next[idx[i]] == ".") {
			delete next[idx[i]];
		} else {
			break;
		}
	}

	// idx = Object.keys(next);
	// idx.sort(function (a, b) {
	// 	return Number(a) - Number(b);
	// });
	// var nextString = "--->";
	// for (let i = 0; i < idx.length; i++) {
	// 	nextString += " (" + idx[i] + ")" + next[idx[i]];
	// }
	// console.log(nextString);

	return next;
}

function numberPlants(input, generation) {
	var states = [];

	var init = {};
	for (let i = 0; i < input.initialState.length; i++) {
		init[i.toString()] = input.initialState[i];
	}
	states.push(init);

	for (let i = 0; i < generation + 1; i++) {
		if (i % 10000 == 0) {
			console.log(i, states.length, states[states.length - 1]);
		}
		states.push(nextGeneration(states[i], input.rules));
	}

	var sumPlants = 0;
	var indexes = Object.keys(states[generation]);
	for (let i = 0; i < indexes.length; i++) {
		if (states[generation][indexes[i]] == "#") {
			sumPlants += Number(indexes[i]);
		}
	}

	return sumPlants;
}


function testNextGeneration(currentState, rules, expected) {
	let t1 = nextGeneration(currentState, rules);
	if (compareState(t1, expected)) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}
function testNumberPlants(input, generation, expected) {
	let t1 = numberPlants(input, generation);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}

var exRules = [
	"...##",
	"..#..",
	".#...",
	".#.#.",
	".#.##",
	".##..",
	".####",
	"#.#.#",
	"#.###",
	"##.#.",
	"##.##",
	"###..",
	"###.#",
	"####."
];
console.log("Unit tests part 1:");
// testNextGeneration("...#..#.#..##......###...###...........", exRules, toState("...#...#....#.....#..#..#..#..........."));
// testNextGeneration("...#...#....#.....#..#..#..#...........", exRules, toState("...##..##...##....#..#..#..##.........."));
// testNextGeneration("...##..##...##....#..#..#..##..........", exRules, toState("..#.#...#..#.#....#..#..#...#.........."));
// testNextGeneration("..#.#...#..#.#....#..#..#...#..........", exRules, toState("...#.#..#...#.#...#..#..##..##........."));
// testNextGeneration("...#.#..#...#.#...#..#..##..##.........", exRules, toState("....#...##...#.#..#..#...#...#........."));
// testNextGeneration("....#...##...#.#..#..#...#...#.........", exRules, toState("....##.#.#....#...#..##..##..##........"));
// testNextGeneration("....##.#.#....#...#..##..##..##........", exRules, toState("...#..###.#...##..#...#...#...#........"));
// testNextGeneration("...#..###.#...##..#...#...#...#........", exRules, toState("...#....##.#.#.#..##..##..##..##......."));
// testNextGeneration("...#....##.#.#.#..##..##..##..##.......", exRules, toState("...##..#..#####....#...#...#...#......."));
// testNextGeneration("...##..#..#####....#...#...#...#.......", exRules, toState("..#.#..#...#.##....##..##..##..##......"));
// testNextGeneration("..#.#..#...#.##....##..##..##..##......", exRules, toState("...#...##...#.#...#.#...#...#...#......"));
// testNextGeneration("...#...##...#.#...#.#...#...#...#......", exRules, toState("...##.#.#....#.#...#.#..##..##..##....."));
// testNextGeneration("...##.#.#....#.#...#.#..##..##..##.....", exRules, toState("..#..###.#....#.#...#....#...#...#....."));
// testNextGeneration("..#..###.#....#.#...#....#...#...#.....", exRules, toState("..#....##.#....#.#..##...##..##..##...."));
// testNextGeneration("..#....##.#....#.#..##...##..##..##....", exRules, toState("..##..#..#.#....#....#..#.#...#...#...."));
// testNextGeneration("..##..#..#.#....#....#..#.#...#...#....", exRules, toState(".#.#..#...#.#...##...#...#.#..##..##..."));
// testNextGeneration(".#.#..#...#.#...##...#...#.#..##..##...", exRules, toState("..#...##...#.#.#.#...##...#....#...#..."));
// testNextGeneration("..#...##...#.#.#.#...##...#....#...#...", exRules, toState("..##.#.#....#####.#.#.#...##...##..##.."));
// testNextGeneration("..##.#.#....#####.#.#.#...##...##..##..", exRules, toState(".#..###.#..#.#.#######.#.#.#..#.#...#.."));
// testNextGeneration(".#..###.#..#.#.#######.#.#.#..#.#...#..", exRules, toState(".#....##....#####...#######....#.#..##."));
// testNumberPlants({
// 	initialState: "#..#.#..##......###...###",
// 	rules: exRules
// }, 20, 325);
console.log("");


////// Part 2

function getValueAt(generation) {
	var positions = [
		120000 - 119983,
		120000 - 119984,
		120000 - 119991,
		120000 - 119992,
		120000 - 120054,
		120000 - 120055,
		120000 - 120057,
		120000 - 120059,
		120000 - 120062,
		120000 - 120063,
		120000 - 120076,
		120000 - 120077,
		120000 - 120079,
		120000 - 120082,
		120000 - 120083
	];
	var result = 0;
	for (let i = 0; i < positions.length; i++) {
		result += generation - positions[i];
	}

	return result;
}


////// Day Answer

function dayAnswer(file) {
	let output = {
		initialState: "",
		rules: []
	};

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		if (input.indexOf("initial state: ") == 0) {
			output.initialState = input.substr(15, input.length);
		} else if (input != "") {
			input = input.split(" => ");
			if (input[1] == "#") {
				output.rules.push(input[0]);
			}
		}
	});

	rl.on('close', () => {
		console.log("Sum of plants until 20th generation: " + numberPlants(output, 20));
		//console.log("Sum of plants until 50000000000th generation: " + numberPlants(output, 50000000000));
		console.log("Sum of plants until 50000000000th generation: " + getValueAt(50000000000));
	});
}

dayAnswer("./day12-input");