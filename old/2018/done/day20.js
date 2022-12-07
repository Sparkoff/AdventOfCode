console.log("** DAY 20 **");
console.log("");


////// Part 1

function runPath(input) {
	input = input.substr(1, input.length - 2);

	while (input.indexOf("(") != -1) {
		input = input.replace(/\([NEWS]+\|\)/g, "");

		var match = input.match(/\([NEWS\|]+\)/);
		if (match) {
			var res = match[0].substr(1, match[0].length - 2).split("|");
			var longest = "";
			for (let i = 0; i < res.length; i++) {
				if(res[i].length > longest.length) {
					longest = res[i];
				}
			}
			input = input.replace(match[0], longest);
		}
	}

	return input.length;
}


function testRunPath(input, expected) {
	let t1 = runPath(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testRunPath("^WNE$", 3);
testRunPath("^ENWWW(NEEE|SSE(EE|N))$", 10);
testRunPath("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$", 18);
testRunPath("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$", 23);
testRunPath("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$", 31);
console.log("");

////// Part 2

function getRooms1000doors(input) {
	input = input.substr(1, input.length - 2);

	var grid = {};
	var dist = 0;
	var x = 0;
	var y = 0;
	var last = [];
	var pos = "";
	for (let i = 0; i < input.length; i++) {
		if (input[i] == "(") {
			last.push({
				x: x,
				y: y,
				dist: dist
			});
		} else if (input[i] == ")") {
			var coord = last.pop();
			x = coord.x;
			y = coord.y;
			dist = coord.dist;
		} else if (input[i] == "|") {
			var coord = last[last.length - 1];
			x = coord.x;
			y = coord.y;
			dist = coord.dist;
		} else {
			if (input[i] == "E") {
				x++;
			} else if (input[i] == "W") {
				x--;
			} else if (input[i] == "S") {
				y++;
			} else if (input[i] == "N") {
				y--;
			}
			dist++;

			pos = x + "," + y;
			if (!grid.hasOwnProperty(pos) || grid[pos] > dist) {
				grid[pos] = dist;
			}
		}
	}

	pos = Object.keys(grid);
	var roomCount = 0;
	for (let i = 0; i < pos.length; i++) {
		if (grid[pos[i]] >= 1000) {
			roomCount++;
		}
	}

	return roomCount;
}



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
		console.log("Further room's doors count: " + runPath(output));
		console.log("Closest rooms count behind 1000 doors: " + getRooms1000doors(output));
	});
}

dayAnswer("./day20-input");