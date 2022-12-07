console.log("** DAY 02 **");
console.log("");


////// Part 1

function parseIDS(input) {
	let letterCount = {};
	let output = [0, 0];

	let letters = input.split('');

	for (let index = 0; index < letters.length; index++) {
		let letter = letters[index];
		if (letterCount.hasOwnProperty(letter)) {
			letterCount[letter]++;
		} else {
			letterCount[letter] = 1;
		}
	}

	letters = Object.keys(letterCount)
	for (let index = 0; index < letters.length; index++) {
		if (letterCount[letters[index]] == 2) {
			output[0]++;
		} else if (letterCount[letters[index]] == 3) {
			output[1]++;
		}
	}

	return output;
}
function checkSum(input) {
	let countDouble = 0;
	let countTriple = 0;

	let ids = input.split(", ");

	for (let index = 0; index < ids.length; index++) {
		let idCounts = parseIDS(ids[index]);

		if (idCounts[0] != 0) {
			countDouble++;
		}
		if (idCounts[1] != 0) {
			countTriple++;
		}
	}

	return countDouble * countTriple;
}


function testParser(input, expected) {
	let t1 = parseIDS(input);
	if (t1[0] == expected[0] && t1[1] == expected[1]) {
		console.log("- " + input + " -> " + t1 + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t1 + ", but expected " + expected);
	}
}
function testChecksum(input, expected) {
	let t1 = checkSum(input);
	if (t1 == expected) {
		console.log("- " + input + " -> " + t1 + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testParser("abcdef", [0, 0]);
testParser("bababc", [1, 1]);
testParser("abbcde", [1, 0]);
testParser("abcccd", [0, 1]);
testParser("aabcdd", [2, 0]);
testParser("abcdee", [1, 0]);
testParser("ababab", [0, 2]);
testChecksum("abcdef, bababc, abbcde, abcccd, aabcdd, abcdee, ababab", 12);
console.log("");


////// Part 2

function closestIDSLetters(input) {
	let distances = [];

	let ids = input.split(", ");

	for (let id1 = 0; id1 < ids.length; id1++) {
		for (let id2 = id1 + 1; id2 < ids.length; id2++) {
			let w1 = ids[id1];
			let w2 = ids[id2];
			let distance = {
				w1: w1,
				w2: w2,
				dist: w1.length,
				common: ""
			};
			const wordLength = w1.length;

			for (let index = 0; index < wordLength; index++) {
				if (w1[index] == w2[index]) {
					distance.dist--;
					distance.common += w1[index];
				}
			}

			distances.push(distance);
		}
	}

	for (let index = 0; index < distances.length; index++) {
		const dist = distances[index];
		if (dist.dist == 1) {
			return dist.common;
		}
	}
}


function testClosestLetters(input, expected) {
	let t2 = closestIDSLetters(input);
	if (t2 == expected) {
		console.log("- " + input + " -> " + t2 + " : ok");
	} else {
		console.error("- " + input + " -> result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests part 2:");
testClosestLetters("abcde, fghij, klmno, pqrst, fguij, axcye, wvxyz", "fgij");
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
		console.log("CheckSum: " + checkSum(output));
		console.log("Closest box IDS letter: " + closestIDSLetters(output));
	});
}

dayAnswer("./day02-input");