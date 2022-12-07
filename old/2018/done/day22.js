console.log("** DAY 22 **");
console.log("");


////// Part 1

function init(input) {
	var cave = [];
	for (let y = 0; y <= input.target.y + 5; y++) {
		cave.push([]);
		for (let x = 0; x <= input.target.x + 5; x++) {
			var interest = "";
			var geologicIndex = -1;
			
			if (x == 0 && y == 0) {
				geologicIndex = 0;
				interest = "M";
			} else if (x == input.target.x && y == input.target.y) {
				geologicIndex = 0;
				interest = "T";
			} else if (y == 0) {
				geologicIndex = x * 16807;
			} else if (x == 0) {
				geologicIndex = y * 48271;
			} else {
				geologicIndex = cave[y][x - 1].erosionLevel * cave[y - 1][x].erosionLevel;
			}
			
			var erosionLevel = (input.depth + geologicIndex) % 20183;
			var type = erosionLevel % 3;
			
			cave[y].push({
				geologicIndex: geologicIndex,
				erosionLevel: erosionLevel,
				type: type,
				interest: interest,
				time: {
					climbingGear: 10000000,
					torch: 10000000,
					neither: 10000000
				}
			});
		}
	}

	return cave;
}

function print(cave) {
	console.log("");
	for (let y = 0; y < cave.length; y++) {
		var line = "";
		for (let x = 0; x < cave[y].length; x++) {
			if (cave[y][x].interest != "") {
				line += cave[y][x].interest;
			} else if (cave[y][x].type == 0) {
				line += "."
			} else if (cave[y][x].type == 1) {
				line += "="
			} else {
				line += "|"
			}
		}
		console.log(line);
	}
}

function getRiskLevel(caveData) {
	var cave = init(caveData);
	//print(cave);

	var risk = 0;
	for (let y = 0; y <= caveData.target.y; y++) {
		for (let x = 0; x <= caveData.target.x; x++) {
			risk += cave[y][x].type;
		}
	}

	return risk;
}


function testGetRiskLevel(input, expected) {
	let t1 = getRiskLevel(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testGetRiskLevel({
	depth: 510,
	target: {
		x: 10,
		y: 10
	}
}, 114);
console.log("");


////// Part 2

function acceptedTool(tool, type) {
	return (tool == "torch" && (type == 0 || type == 2)) ||
		(tool == "climbingGear" && (type == 0 || type == 1)) ||
		(tool == "neither" && (type == 1 || type == 2));
}

function buildNext(cave, current, x1, y1) {
	var next = [];
	if (acceptedTool("torch", cave[y1][x1].type)) {
		if (current.tool == "torch") {
			if (cave[y1][x1].time.torch > current.time + 1) {
				cave[y1][x1].time.torch = current.time + 1;
				next.push({
					x: x1,
					y: y1,
					tool: "torch",
					time: current.time + 1
				});
			}
		} else {
			if (cave[y1][x1].time.torch > current.time + 8) {
				cave[y1][x1].time.torch = current.time + 8;
				next.push({
					x: x1,
					y: y1,
					tool: "torch",
					time: current.time + 8
				});
			}
		}
	}
	if (acceptedTool("climbingGear", cave[y1][x1].type)) {
		if (current.tool == "climbingGear") {
			if (cave[y1][x1].time.climbingGear > current.time + 1) {
				cave[y1][x1].time.climbingGear = current.time + 1;
				next.push({
					x: x1,
					y: y1,
					tool: "climbingGear",
					time: current.time + 1
				});
			}
		} else {
			if (cave[y1][x1].time.climbingGear > current.time + 8) {
				cave[y1][x1].time.climbingGear = current.time + 8;
				next.push({
					x: x1,
					y: y1,
					tool: "climbingGear",
					time: current.time + 8
				});
			}
		}
	}
	if (acceptedTool("neither", cave[y1][x1].type)) {
		if (current.tool == "neither") {
			if (cave[y1][x1].time.neither > current.time + 1) {
				cave[y1][x1].time.neither = current.time + 1;
				next.push({
					x: x1,
					y: y1,
					tool: "neither",
					time: current.time + 1
				});
			}
		} else {
			if (cave[y1][x1].time.neither > current.time + 8) {
				cave[y1][x1].time.neither = current.time + 8;
				next.push({
					x: x1,
					y: y1,
					tool: "neither",
					time: current.time + 8
				});
			}
		}
	}
	return next;
}

function exploreCave(cave, current) {
	var next = [];

	if (current.x - 1 >= 0) {
		next = next.concat(buildNext(cave, current, current.x - 1, current.y));
	}
	if (current.x + 1 <= cave[current.y].length - 1) {
		next = next.concat(buildNext(cave, current, current.x + 1, current.y));
	}
	if (current.y - 1 >= 0) {
		next = next.concat(buildNext(cave, current, current.x, current.y - 1));
	}
	if (current.y + 1 <= cave.length - 1) {
		next = next.concat(buildNext(cave, current, current.x, current.y + 1));
	}

	return next;
}

function rescue(caveData) {
	var cave = init(caveData);
	//print(cave);

	var explored = [];
	explored.push({
		x: 0,
		y: 0,
		tool: "torch",
		time: 0
	});

	while (explored.length != 0) {
		var newExplored = [];
		for (let i = 0; i < explored.length; i++) {
			newExplored = newExplored.concat(exploreCave(cave, explored[i]));
		}

		explored = newExplored;
	}

	var min = 10000000;
	if (min > cave[caveData.target.y][caveData.target.x].time.climbingGear + 7) {
		min = cave[caveData.target.y][caveData.target.x].time.climbingGear + 7;
	}
	if (min > cave[caveData.target.y][caveData.target.x].time.torch) {
		min = cave[caveData.target.y][caveData.target.x].time.torch;
	}
	if (min > cave[caveData.target.y][caveData.target.x].time.neither + 7) {
		min = cave[caveData.target.y][caveData.target.x].time.neither + 7;
	}
	return min;
}


function testRescue(input, expected) {
	let t2 = rescue(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testRescue({
	depth: 510,
	target: {
		x: 10,
		y: 10
	}
}, 45);
console.log("");


////// Day Answer

function dayAnswer() {
	var cave = {
		depth: 9465,
		target: {
			x: 13,
			y: 704
		}
	};
	console.log("Aera risk level: " + getRiskLevel(cave));
	console.log("Shortest exploration duration: " + rescue(cave));
}

dayAnswer();