console.log("** DAY 10 **");
console.log("");


////// Part 1

function printStars(input, limit) {
	var stars = [];

	for (let i = 0; i < input.length; i++) {
		var s = input[i].split("> velocity=<");
		var p = s[0].split(", ");
		var v = s[1].split(", ");
		stars.push({
			x: Number(p[0].split("position=<")[1]),
			y: Number(p[1]),
			vx: Number(v[0]),
			vy: Number(v[1].split(">")[0])
		});
	}

	var xmin = 0;
	var ymin = 0;
	var xmax = 0;
	var ymax = 0;
	var reading = false;
	for (let i = 1; i <= limit; i++) {
		for (let star = 0; star < stars.length; star++) {
			stars[star].x += stars[star].vx;
			stars[star].y += stars[star].vy;

			if (star == 0) {
				xmin = stars[star].x;
				ymin = stars[star].y;
				xmax = stars[star].x;
				ymax = stars[star].y;
			} else {
				xmin = Math.min(stars[star].x, xmin);
				ymin = Math.min(stars[star].y, ymin);
				xmax = Math.max(stars[star].x, xmax);
				ymax = Math.max(stars[star].y, ymax);
			}
		}

		console.log(xmax - xmin, ymax - ymin, i);

		if (Math.abs(xmax - xmin) <= 100 && Math.abs(ymax - ymin) <= 30) {
			var reading = true;
			var sky = new Array(30).fill("");
			for (let j = 0; j < sky.length; j++) {
				sky[j] = new Array(100).fill(".");
			}

			for (let star = 0; star < stars.length; star++) {
				sky[stars[star].y - ymin][stars[star].x - xmin] = "#";
			}
			for (let j = 0; j < sky.length; j++) {
				console.log(sky[j].join(""));
			}
		} else if (reading) {
			return "end";
		}
	}
}


function testPrintStars(input, limit) {
	printStars(input, limit);
}


console.log("Unit tests part 1:");
// testPrintStars([
// 	"position=< 9,  1> velocity=< 0,  2>",
// 	"position=< 7,  0> velocity=<-1,  0>",
// 	"position=< 3, -2> velocity=<-1,  1>",
// 	"position=< 6, 10> velocity=<-2, -1>",
// 	"position=< 2, -4> velocity=< 2,  2>",
// 	"position=<-6, 10> velocity=< 2, -2>",
// 	"position=< 1,  8> velocity=< 1, -1>",
// 	"position=< 1,  7> velocity=< 1,  0>",
// 	"position=<-3, 11> velocity=< 1, -2>",
// 	"position=< 7,  6> velocity=<-1, -1>",
// 	"position=<-2,  3> velocity=< 1,  0>",
// 	"position=<-4,  3> velocity=< 2,  0>",
// 	"position=<10, -3> velocity=<-1,  1>",
// 	"position=< 5, 11> velocity=< 1, -2>",
// 	"position=< 4,  7> velocity=< 0, -1>",
// 	"position=< 8, -2> velocity=< 0,  1>",
// 	"position=<15,  0> velocity=<-2,  0>",
// 	"position=< 1,  6> velocity=< 1,  0>",
// 	"position=< 8,  9> velocity=< 0, -1>",
// 	"position=< 3,  3> velocity=<-1,  1>",
// 	"position=< 0,  5> velocity=< 0, -1>",
// 	"position=<-2,  2> velocity=< 2,  0>",
// 	"position=< 5, -2> velocity=< 1,  2>",
// 	"position=< 1,  4> velocity=< 2,  1>",
// 	"position=<-2,  7> velocity=< 2, -2>",
// 	"position=< 3,  6> velocity=<-1, -1>",
// 	"position=< 5,  0> velocity=< 1,  0>",
// 	"position=<-6,  0> velocity=< 2,  0>",
// 	"position=< 5,  9> velocity=< 1, -2>",
// 	"position=<14,  7> velocity=<-2,  0>",
// 	"position=<-3,  6> velocity=< 2, -1>"
// ], 3);
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
		console.log("Star's message: " + printStars(output, 10000000));
	});
}

dayAnswer("./day10-input");