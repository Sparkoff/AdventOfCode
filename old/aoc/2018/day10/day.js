class StarsMessage {
	constructor(stars) {
		this.stars = this.parseStars(stars)

		this.message = this.run()
	}

	parseStars(stars) {
		let x = []
		let y = []
		let vx = []
		let vy = []

		stars.forEach(star => {
			star = star.match(/(\-?\d+)/g)
			x.push(parseInt(star[0]))
			y.push(parseInt(star[1]))
			vx.push(parseInt(star[2]))
			vy.push(parseInt(star[3]))
		})
		return { x, y, vx, vy }
	}

	run() {
		let current = null
		let next = this.makeState(this.stars.x, this.stars.y)
		let time = 0

		do {
			current = next
			let x = current.x.slice(0)
			let y = current.y.slice(0)

			for (let i = 0; i < x.length; i++) {
				x[i] += this.stars.vx[i]
				y[i] += this.stars.vy[i]
			}

			next = this.makeState(x, y)
			time++
		} while (current.w >= next.w && current.h >= next.h)
		time--

		return {
			sky: this.print(current),
			time
		}
	}
	makeState(x, y) {
		return {
			x: x,
			y: y,
			w: Math.abs(Math.max(...x) - Math.min(...x)),
			h: Math.abs(Math.max(...y) - Math.min(...y))
		}
	}

	print(state) {
		let sky = new Array(state.h + 1).fill("")
		for (let h = 0; h < sky.length; h++) {
			sky[h] = new Array(state.w + 1).fill(".")
		}
		

		let xmin = Math.min(...state.x)
		let ymin = Math.min(...state.y)
		
		for (let i = 0; i < state.x.length; i++) {
			sky[state.y[i] - ymin][state.x[i] - xmin] = "#"
		}
		return sky.map(s => s.join('')).join('\n')
	}

	get part1() {
		return this.message.sky
	}

	get part2() {
		return this.message.time
	}
}

module.exports = {
	answer: function (input) {
		return new StarsMessage(input)
	},

	part1: [
		'#....#..#####...#####.....##....#.......######..#####...#....#',
		'##...#..#....#..#....#...#..#...#............#..#....#..#....#',
		'##...#..#....#..#....#..#....#..#............#..#....#..#....#',
		'#.#..#..#....#..#....#..#....#..#...........#...#....#..#....#',
		'#.#..#..#####...#####...#....#..#..........#....#####...######',
		'#..#.#..#....#..#..#....######..#.........#.....#.......#....#',
		'#..#.#..#....#..#...#...#....#..#........#......#.......#....#',
		'#...##..#....#..#...#...#....#..#.......#.......#.......#....#',
		'#...##..#....#..#....#..#....#..#.......#.......#.......#....#',
		'#....#..#####...#....#..#....#..######..######..#.......#....#'
	].join('\n'),
	part2: 10454
}