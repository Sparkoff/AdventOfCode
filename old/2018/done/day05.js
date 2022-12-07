console.log("** DAY 05 **");
console.log("");


////// Part 1

function react(input) {
	while (true) {
		let act = false;
		for (let i = 0; i < input.length - 1; i++) {
			if (input[i] != input[i + 1] && input[i].toLowerCase() == input[i + 1].toLowerCase()) {
				input = input.substr(0, i) + input.substr(i + 2);
				act = true;
			}
		}
		if (!act) {
			return {
				polymer: input,
				length: input.length
			};
		}
	}
}


function testReaction(input, expected) {
	let t1 = react(input);
	if (t1.polymer == expected) {
		console.log("- " + input + " -> " + t1.polymer + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t1.polymer + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testReaction("aA", "");
testReaction("abBA", "");
testReaction("abAB", "abAB");
testReaction("aabAAB", "aabAAB");
testReaction("dabAcCaCBAcCcaDA", "dabCBAcaDA");
console.log("");


////// Part 2

function polymentImprovement(input) {
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
		console.log("Alchemy reaction: " + react(output).length);
		console.log("Polymer improvement: " + polymentImprovement(output));
	});
}

dayAnswer("./day05-input");