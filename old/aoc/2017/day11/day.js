class HexGrid {
	constructor(path) {
		this.path = path.split(',')
		this.childCoord = null
		this.furthestDistance = 0

		this.run()
	}

	run() {
		let current = {
			x: 0,
			y: 0
		}
		for (let i = 0; i < this.path.length; i++) {
			current = this.evalNextHexe(current, this.path[i])
		}
		this.childCoord = current
	}

	evalNextHexe(current, direction) {
		let next = {
			x: current.x,
			y: current.y
		}
		switch (direction) {
			case 'n':
				next.y++
				break;
			case 'nw':
				next.x--
				next.y++
				break;
			case 'ne':
				next.x++
				break;
			case 's':
				next.y--
				break;
			case 'sw':
				next.x--
				break;
			case 'se':
				next.x++
				next.y--
				break;

			default:
				console.log(`unknown direction: ${direction}`)
				break;
		}

		const dist = this.manhattanDistance(next)
		if (this.furthestDistance < dist) {
			this.furthestDistance = dist
		}

		return next
	}

	manhattanDistance(coord) {
		if (coord.x == 0 || coord.y == 0 || Math.sign(coord.x) == Math.sign(coord.y)) {
			return Math.abs(coord.x + coord.y)
		}
		return Math.max(Math.abs(coord.x), Math.abs(coord.y))
	}

	get part1() {
		return this.manhattanDistance(this.childCoord)
	}

	get part2() {
		return this.furthestDistance
	}
}

module.exports = {
	answer: function (input, part) {
		input = (Array.isArray(input)) ? input[0] : input
		return new HexGrid(input)
	},

	part1: 773,
	part2: 1560
}