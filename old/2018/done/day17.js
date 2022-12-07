console.log("** DAY 17 **");
console.log("");


////// Part 1

function parseScanData(input) {
	var clay = [];
	for (let i = 0; i < input.length; i++) {
		var coord = input[i].split(", ");
		coord[0] = coord[0].split("=");
		coord[1] = coord[1].split("=");
		coord[1][1] = coord[1][1].split("..");

		if (coord[1][1][0] - coord[1][1][coord[1][1].length - 1] >= 0) {
			console.log("Warn: unintended coord", input[i]);
		}

		for (let i = coord[1][1][0]; i <= coord[1][1][coord[1][1].length - 1]; i++) {
			var c = {};
			c[coord[0][0]] = Number(coord[0][1]);
			c[coord[1][0]] = Number(i);
			clay.push(c);
		}
	}

	return clay;
}

function init(input) {
	var clay = parseScanData(input);

	var xmin = clay[0].x;
	var xmax = clay[0].x;
	var ymin = clay[0].y;
	var ymax = clay[0].y;

	for (let i = 1; i < clay.length; i++) {
		if (clay[i].x < xmin) {
			xmin = clay[i].x;
		} else if (clay[i].x > xmax) {
			xmax = clay[i].x;
		}
		if (clay[i].y < ymin) {
			ymin = clay[i].y;
		} else if (clay[i].y > ymax) {
			ymax = clay[i].y;
		}
	}
	console.log(xmin, xmax, ymin, ymax);

	var dimX = xmax - xmin + 2;
	var dimY = ymax;

	var grid = [];
	for (let y = 0; y <= dimY; y++) {
		grid.push([]);
		for (let x = 0; x <= dimX; x++) {
			grid[y].push({
				type: ".",
				water: null
			})
		}
	}

	for (let i = 0; i < clay.length; i++) {
		grid[clay[i].y][clay[i].x - xmin + 1].type = "#";
	}
	grid[0][500 - xmin + 1].type = "+";

	return {
		grid: grid,
		dim: {
			x: dimX,
			y: dimY
		},
		box: {
			xmin: xmin,
			xmax: xmax,
			ymin: ymin,
			ymax: ymax
		}
	};
}

function printUnderground(grid) {
	console.log("");
	for (let y = 0; y < grid.length; y++) {
		var line = "";
		for (let x = 0; x < grid[y].length; x++) {
			if (grid[y][x].water != null) {
				line += grid[y][x].water;
			} else {
				line += grid[y][x].type;
			}
		}
		console.log(line);
	}
}

function exist(pt, grid) {
	return grid[pt.y] && grid[pt.y][pt.x];
}

function isFree(pt, grid) {
	return grid[pt.y][pt.x].type == "." && grid[pt.y][pt.x].water == null;
}

function isFallingWaterIntoWater(pt, grid) {
	return grid[pt.y - 1] && grid[pt.y + 1] && grid[pt.y - 1][pt.x].water == "|" && grid[pt.y + 1][pt.x].water == "~";
}

function putWater(pt, grid) {
	if (grid[pt.y + 1] && (grid[pt.y + 1][pt.x].water == "~" || grid[pt.y + 1][pt.x].type == "#") && grid[pt.y - 1][pt.x].water == null) {
		grid[pt.y][pt.x].water = "~";
	} else {
		grid[pt.y][pt.x].water = "|";
	}
}

function explore(dir, pt, grid) {
	var xc = pt.x;
	var yc = pt.y;

	if (dir == -1) {
		var res = explore(0, pt, grid);

		if (res) {
			var res1 = explore(1, pt, grid);
			var res2 = explore(2, pt, grid);

			return res1 && res2;
		} else {
			return false;
		}
	} else if (dir == 0) { // down
		var newPt = {
			x: xc,
			y: yc + 1
		};
		if (exist(newPt, grid)) {
			if (isFree(newPt, grid)) {
				putWater(newPt, grid);

				var res = explore(-1, newPt, grid);
				return res;
			} else if (isFallingWaterIntoWater(pt, grid)) {
				return false;
			} else {
				return true;
			}
		}
		return false;

	} else if (dir == 1) { // right
		var newPt = {
			x: xc + 1,
			y: yc
		};
		if (exist(newPt, grid)) {
			if (isFree(newPt, grid)) {
				putWater(newPt, grid);

				var res = explore(-1, newPt, grid);
				return res;
			}
		}
		return true;
	
	} else if (dir == 2) { // left
		var newPt = {
			x: xc - 1,
			y: yc
		};
		if (exist(newPt, grid)) {
			if (isFree(newPt, grid)) {
				putWater(newPt, grid);

				var res = explore(-1, newPt, grid);
				return res;
			}
		}
		return true;
	}
}

function runSpring(input) {
	var gridData = init(input);

	var grid = gridData.grid;
	//printUnderground(grid);

	var source = {
		x: 500 - gridData.box.xmin + 1,
		y: 0
	};

	var res = explore(-1, source, grid);
	printUnderground(grid);

	var waterCount = 0;
	for (let y = 0; y < grid.length; y++) {
		for (let x = 0; x < grid[y].length; x++) {
			if (grid[y][x].water != null) {
				waterCount++;
			}
		}
	}

	return waterCount;
}


function testRunSpring(input, expected) {
	let t1 = runSpring(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testRunSpring([
	"x=495, y=2..7",
	"y=7, x=495..501",
	"x=501, y=3..7",
	"x=498, y=2..4",
	"x=506, y=1..2",
	"x=498, y=10..13",
	"x=504, y=10..13",
	"y=13, x=498..504"
], 57);
console.log("");


////// Part 2

/*function runSpringNoLoss(input) {
	var attack = 4;
	while (true) {
		var result = runCycle(input, attack);
		if (result.elfsCountStart == result.elfCount) {
			return result.outcome;
		} else {
			attack++;
		}
	}
}


function testRunSpringNoLoss(input, expected) {
	let t2 = runSpringNoLoss(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testRunSpringNoLoss([
	"#######",
	"#.G...#",
	"#...EG#",
	"#.#.#G#",
	"#..G#E#",
	"#.....#",
	"#######"
], 4988);
console.log("");*/


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
		console.log("Total water: " + runSpring(output));
		//console.log("Final outcome without losses: " + runSpringNoLoss(output));
	});
}

dayAnswer("./day17-input");