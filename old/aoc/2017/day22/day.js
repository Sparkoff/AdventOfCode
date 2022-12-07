class SporificaVirus {
	constructor(grid, limit) {
		this.grid = this.parseGrid(grid)
		this.limit = limit

		this.dirs = [
			{ x: 0, y: 1 },
			{ x: 1, y: 0 },
			{ x: 0, y: -1 },
			{ x: -1, y: 0 }
		]
	}

	parseGrid(map) {
		let size = (map.length - 1) / 2
		map = map.map(r => r.split(''))

		let grid = {}

		for (let y = 0; y < map.length; y++) {
			for (let x = 0; x < map[y].length; x++) {
				grid[`${x - size},${size - y}`] = map[y][x]
			}
		}

		return grid
	}

	run() {
		let grid = Object.assign({}, this.grid)
		let x = 0, y = 0
		let dir = 0
		let infected = 0

		let limit = this.limit || 1E4

		for (let i = 0; i < limit; i++) {
			const coord = `${x},${y}`
			switch (grid[coord]) {
				case '.':
					dir = (dir + 3) % 4
					grid[coord] = '#'
					infected++
					break
				case '#':
					dir = (dir + 1) % 4
					grid[coord] = '.'
					break

				default:
					console.log(`Unknown state: ${grid[coord]}`)
					break
			}

			x += this.dirs[dir].x
			y += this.dirs[dir].y

			if (!grid.hasOwnProperty(`${x},${y}`)) {
				grid[`${x},${y}`] = '.'
			}
		}

		return infected
	}

	runEvolved() {
		let grid = Object.assign({}, this.grid)
		let x = 0, y = 0
		let dir = 0
		let infected = 0
		let limit = this.limit || 1E7

		for (let i = 0; i < limit; i++) {
			const coord = `${x},${y}`
			switch (grid[coord]) {
				case '.':
					dir = (dir + 3) % 4
					grid[coord] = 'W'
					break
				case 'W':
					grid[coord] = '#'
					infected++
					break
				case '#':
					dir = (dir + 1) % 4
					grid[coord] = 'F'
					break
				case 'F':
					dir = (dir + 2) % 4
					grid[coord] = '.'
					break

				default:
					console.log(`Unknown state: ${grid[coord]}`)
					break
			}

			x += this.dirs[dir].x
			y += this.dirs[dir].y

			if (!grid.hasOwnProperty(`${x},${y}`)) {
				grid[`${x},${y}`] = '.'
			}
		}

		return infected
	}

	get part1() {
		return this.run()
	}

	get part2() {
		return this.runEvolved()
	}
}

module.exports = {
	answer: function (input, limit) {
		return new SporificaVirus(input, limit)
	},

	part1: 5369,
	part2: 2510774
}