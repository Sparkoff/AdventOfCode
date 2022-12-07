console.log("** DAY 23 **");
console.log("");


////// Part 1

function parse(input) {
	var nanobots = [];

	for (let i = 0; i < input.length; i++) {
		var nanobot = {
			x: 0,
			y: 0,
			z: 0,
			r: 0,
			id: i + 1
		};
		
		var c  = input[i].split(", ");
		nanobot.r = Number(c[1].substr(2, c[1].length - 2));

		c = c[0].substr(5, c[0].length - 6).split(",");
		nanobot.x = Number(c[0]);
		nanobot.y = Number(c[1]);
		nanobot.z = Number(c[2]);

		nanobots.push(nanobot);
	}

	return nanobots;
}

function findInRange(input) {
	var nanobots = parse(input);

	var nanobotRef = nanobots[0];
	for (let i = 1; i < nanobots.length; i++) {
		if (nanobotRef.r < nanobots[i].r) {
			nanobotRef = nanobots[i];
		}
	}

	var count = 0;
	for (let i = 0; i < nanobots.length; i++) {
		var dist = Math.abs(nanobotRef.x - nanobots[i].x);
		dist += Math.abs(nanobotRef.y - nanobots[i].y);
		dist += Math.abs(nanobotRef.z - nanobots[i].z);

		if (dist <= nanobotRef.r) {
			count++;
		}
	}

	return count;
}


function testFindInRange(input, expected) {
	let t1 = findInRange(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testFindInRange([
	"pos=<0,0,0>, r=4",
	"pos=<1,0,0>, r=1",
	"pos=<4,0,0>, r=3",
	"pos=<0,2,0>, r=1",
	"pos=<0,5,0>, r=3",
	"pos=<0,0,3>, r=1",
	"pos=<1,1,1>, r=1",
	"pos=<1,1,2>, r=1",
	"pos=<1,3,1>, r=1"
], 7);
testFindInRange([
	"pos=<1,0,0>, r=1",
	"pos=<4,0,0>, r=3",
	"pos=<0,2,0>, r=1",
	"pos=<0,5,0>, r=3",
	"pos=<0,0,0>, r=4",
	"pos=<0,0,3>, r=1",
	"pos=<1,1,1>, r=1",
	"pos=<1,1,2>, r=1",
	"pos=<1,3,1>, r=1"
], 7);
console.log("");


////// Part 2

function initGrid(nanobots) {
	var xmin = 0;
	var xmax = 0;
	var ymin = 0;
	var ymax = 0;
	var zmin = 0;
	var zmax = 0;
	for (let i = 0; i < nanobots.length; i++) {
		if (xmin > nanobots[i].x) {
			xmin = nanobots[i].x;
		} else if (xmax < nanobots[i].x) {
			xmax = nanobots[i].x;
		}
		if (ymin > nanobots[i].y) {
			ymin = nanobots[i].y;
		} else if (ymax < nanobots[i].y) {
			ymax = nanobots[i].y;
		}
		if (zmin > nanobots[i].z) {
			zmin = nanobots[i].z;
		} else if (zmax < nanobots[i].z) {
			zmax = nanobots[i].z;
		}
	}

	var grid = [];
	for (let z = 0; z <= zmax - zmin; z++) {
		grid.push([]);
		for (let y = 0; y <= ymax - ymin; y++) {
			grid[z].push([]);
			for (let x = 0; x <= xmax - xmin; x++) {
				grid[z][y].push({
					nanobot: null,
					inRange: {},
					dist: 10000000
				});
			}
		}
	}

	for (let i = 0; i < nanobots.length; i++) {
		grid[nanobots[i].z - zmin][nanobots[i].y - ymin][nanobots[i].x - xmin].nanobot = nanobots[i];
		grid[nanobots[i].z - zmin][nanobots[i].y - ymin][nanobots[i].x - xmin].inRange[nanobots[i].id] = 0;
	}

	return {
		grid: grid,
		box: {
			xmin: xmin,
			xmax: xmax,
			ymin: ymin,
			ymax: ymax,
			zmin: zmin,
			zmax: zmax
		},
		dim: {
			x: xmax - xmin + 1,
			y: ymax - ymin + 1,
			z: zmax - zmin + 1
		},
		c0: {
			x: -xmin,
			y: -ymin,
			z: -zmin
		}
	}
}

function explore(gridData, nanobot) {
	var explored = [];
	explored.push({
		x: gridData.c0.x + nanobot.x,
		y: gridData.c0.y + nanobot.y,
		z: gridData.c0.z + nanobot.z
	});

	var range = 1;
	while (explored.length != 0) {
		var newExplored = [];
		for (let i = 0; i < explored.length; i++) {
			if (range <= nanobot.r) {
				var x = explored[i].x;
				var y = explored[i].y;
				var z = explored[i].z;
				
				if (z - 1 >= 0 && (!gridData.grid[z - 1][y][x].inRange.hasOwnProperty(nanobot.id) || gridData.grid[z - 1][y][x].inRange[nanobot.id] > range)) {
					gridData.grid[z - 1][y][x].inRange[nanobot.id] = range;
					newExplored.push({
						x: x,
						y: y,
						z: z - 1
					})
				}
				if (z + 1 < gridData.grid.length && (!gridData.grid[z + 1][y][x].inRange.hasOwnProperty(nanobot.id) || gridData.grid[z + 1][y][x].inRange[nanobot.id] > range)) {
					gridData.grid[z + 1][y][x].inRange[nanobot.id] = range;
					newExplored.push({
						x: x,
						y: y,
						z: z + 1
					})
				}
				if (y - 1 >= 0 && (!gridData.grid[z][y - 1][x].inRange.hasOwnProperty(nanobot.id) || gridData.grid[z][y - 1][x].inRange[nanobot.id] > range)) {
					gridData.grid[z][y - 1][x].inRange[nanobot.id] = range;
					newExplored.push({
						x: x,
						y: y - 1,
						z: z
					})
				}
				if (y + 1 < gridData.grid[z].length && (!gridData.grid[z][y + 1][x].inRange.hasOwnProperty(nanobot.id) || gridData.grid[z][y + 1][x].inRange[nanobot.id] > range)) {
					gridData.grid[z][y + 1][x].inRange[nanobot.id] = range;
					newExplored.push({
						x: x,
						y: y + 1,
						z: z
					})
				}
				if (x - 1 >= 0 && (!gridData.grid[z][y][x - 1].inRange.hasOwnProperty(nanobot.id) || gridData.grid[z][y][x - 1].inRange[nanobot.id] > range)) {
					gridData.grid[z][y][x - 1].inRange[nanobot.id] = range;
					newExplored.push({
						x: x - 1,
						y: y,
						z: z
					})
				}
				if (x + 1 < gridData.grid[z].length && (!gridData.grid[z][y][x + 1].inRange.hasOwnProperty(nanobot.id) || gridData.grid[z][y][x + 1].inRange[nanobot.id] > range)) {
					gridData.grid[z][y][x + 1].inRange[nanobot.id] = range;
					newExplored.push({
						x: x + 1,
						y: y,
						z: z
					})
				}
			}
		}
		range++;
		explored = newExplored;
	}

	return gridData;
}

function exploreDist(gridData) {
	var explored = [];
	explored.push(gridData.c0);

	var dist = 1;
	while (explored.length != 0) {
		var newExplored = [];
		for (let i = 0; i < explored.length; i++) {
			var x = explored[i].x;
			var y = explored[i].y;
			var z = explored[i].z;
			
			if (z - 1 >= 0 && gridData.grid[z - 1][y][x].dist > dist) {
				gridData.grid[z - 1][y][x].dist = dist;
				newExplored.push({
					x: x,
					y: y,
					z: z - 1
				})
			}
			if (z + 1 < gridData.grid.length && gridData.grid[z + 1][y][x].dist > dist) {
				gridData.grid[z + 1][y][x].dist = dist;
				newExplored.push({
					x: x,
					y: y,
					z: z + 1
				})
			}
			if (y - 1 >= 0 && gridData.grid[z][y - 1][x].dist > dist) {
				gridData.grid[z][y - 1][x].dist = dist;
				newExplored.push({
					x: x,
					y: y - 1,
					z: z
				})
			}
			if (y + 1 < gridData.grid[z].length && gridData.grid[z][y + 1][x].dist > dist) {
				gridData.grid[z][y + 1][x].dist = dist;
				newExplored.push({
					x: x,
					y: y + 1,
					z: z
				})
			}
			if (x - 1 >= 0 && gridData.grid[z][y][x - 1].dist > dist) {
				gridData.grid[z][y][x - 1].dist = dist;
				newExplored.push({
					x: x - 1,
					y: y,
					z: z
				})
			}
			if (x + 1 < gridData.grid[z].length && gridData.grid[z][y][x + 1].dist > dist) {
				gridData.grid[z][y][x + 1].dist = dist;
				newExplored.push({
					x: x + 1,
					y: y,
					z: z
				})
			}
		}
		dist++;
		explored = newExplored;
	}

	return gridData;
}

function getTeleportDistance(input) {
	var nanobots = parse(input);
	var gridData = initGrid(nanobots);

	for (let i = 0; i < nanobots.length; i++) {
		gridData = explore(gridData, nanobots[i]);
	}
	exploreDist(gridData);

	var dist = 10000000;
	var inRangeCount = 0;
	var grid = gridData.grid;
	for (let z = 0; z < grid.length; z++) {
		for (let y = 0; y < grid[z].length; y++) {
			for (let x = 0; x < grid[z][y].length; x++) {
				var count = Object.keys(grid[z][y][x].inRange).length;
				if (inRangeCount < count || (inRangeCount == count && grid[z][y][x].dist < dist)) {
					dist = grid[z][y][x].dist;
					inRangeCount = count;
				}
			}
		}
	}

	return dist;
}

function getTeleportDistance2(input) {
	var nanobots = parse(input);
	var xmin = 0;
	var xmax = 0;
	var ymin = 0;
	var ymax = 0;
	var zmin = 0;
	var zmax = 0;
	for (let i = 0; i < nanobots.length; i++) {
		if (xmin > nanobots[i].x) {
			xmin = nanobots[i].x;
		} else if (xmax < nanobots[i].x) {
			xmax = nanobots[i].x;
		}
		if (ymin > nanobots[i].y) {
			ymin = nanobots[i].y;
		} else if (ymax < nanobots[i].y) {
			ymax = nanobots[i].y;
		}
		if (zmin > nanobots[i].z) {
			zmin = nanobots[i].z;
		} else if (zmax < nanobots[i].z) {
			zmax = nanobots[i].z;
		}
	}
	console.log(zmin, zmax);

	var dist = 0;
	var inRangeCount = 0;
	for (let z = zmin; z <= zmax; z++) {
		console.log(z);
		for (let y = ymin; y <= ymax; y++) {
			for (let x = xmin; x <= xmax; x++) {
				var d0 = x + y + z;
				var count = 0;
				for (let i = 0; i < nanobots.length; i++) {
					var di = Math.abs(x - nanobots[i].x) + Math.abs(y - nanobots[i].y) + Math.abs(z - nanobots[i].z);
					if (di <= nanobots[i].r) {
						count++;
					}
				}
				if (count > inRangeCount || (count == inRangeCount && d0 < dist)) {
					dist = d0;
					inRangeCount = count;
				}
			}
		}
	}

	return dist;
}


function testGetTeleportDistance2(input, expected) {
	let t2 = getTeleportDistance2(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testGetTeleportDistance2([
	"pos=<-1,-1,-1>, r=2",
	"pos=<10,12,12>, r=2",
	"pos=<12,14,12>, r=2",
	"pos=<16,12,12>, r=4",
	"pos=<14,14,14>, r=6",
	"pos=<50,50,50>, r=200",
	"pos=<10,10,10>, r=5"
], 36);
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
		output.push(input);
	});

	rl.on('close', () => {
		console.log("Robots in ranges: " + findInRange(output));
		console.log("Last cart survivor: " + getTeleportDistance2(output));
	});
}

dayAnswer("./day23-input");