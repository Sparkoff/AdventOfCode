console.log("** DAY 06 **");
console.log("");


////// Part 1

function manhattan(input) {
	var xmax = 0;
	var ymax = 0;
	for (let index = 0; index < input.length; index++) {
		if (input[index][0] > xmax) {
			xmax = input[index][0];
		}
		if (input[index][1] > ymax) {
			ymax = input[index][1];
		}
	}

	var map = [];
	for (let x = 0; x <= xmax; x++) {
		for (let y = 0; y <= ymax; y++) {
			var min = xmax + ymax;
			var points = [];
			for (let i = 0; i < input.length; i++) {
				var dist = Math.abs(x - input[i][0]) + Math.abs(y - input[i][1]);
				if (dist < min) {
					points = [i];
					min = dist;
				} else if (dist == min) {
					points.push(i);
				}
			}
			map.push({
				x: x,
				y: y,
				closest: (points.length == 1) ? points[0] : "."
			})
		}
	}

	var largest = 0;
	for (let i = 0; i < input.length; i++) {
		var l = 0;
		for (let index = 0; index < map.length; index++) {
			if (map[index].closest == i) {
				l++;
				if (map[index].x == 0 || map[index].x == xmax || map[index].y == 0 || map[index].y == ymax) {
					l = 0;
					break;
				}
			}
		}
		if (l > largest) {
			largest = l;
		} 
	}

	return largest;
}


function testManhattan(input, expected) {
	let t1 = manhattan(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testManhattan([
	[1, 1],
	[1, 6],
	[8, 3],
	[3, 4],
	[5, 5],
	[8, 9]
], 17);
console.log("");


////// Part 2

function manhattanControled(input,limit) {
	var xmax = 0;
	var ymax = 0;
	for (let index = 0; index < input.length; index++) {
		if (input[index][0] > xmax) {
			xmax = input[index][0];
		}
		if (input[index][1] > ymax) {
			ymax = input[index][1];
		}
	}

	var map = [];
	for (let x = 0; x <= xmax; x++) {
		for (let y = 0; y <= ymax; y++) {
			var sumDist = 0;
			for (let i = 0; i < input.length; i++) {
				sumDist += Math.abs(x - input[i][0]) + Math.abs(y - input[i][1]);
			}
			map.push({
				x: x,
				y: y,
				value: (sumDist < limit) ? 1 : 0
			})
		}
	}

	var area = 0;
	for (let index = 0; index < map.length; index++) {
		area += map[index].value;
	}

	return area;
}


function testManathanControlled(input, limit, expected) {
	let t2 = manhattanControled(input, limit);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests part 2:");
testManathanControlled([
	[1, 1],
	[1, 6],
	[8, 3],
	[3, 4],
	[5, 5],
	[8, 9]
], 32, 16);
console.log("");


////// Day Answer

function dayAnswer(file) {
	let output = [];

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		var s = input.split(", ");
		output.push([Number(s[0]), Number(s[1])]);
	});

	rl.on('close', () => {
		console.log("Largest area: " + manhattan(output));
		console.log("Area limited: " + manhattanControled(output, 10000));
	});
}

dayAnswer("./day06-input");