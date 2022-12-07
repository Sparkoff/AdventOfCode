class ChronalCharge {
	constructor(serial) {
		this.serial = parseInt(serial)

		this.powerfullSquares = this.poweredSquare()
	}

	powerLevel(x, y) {
		const rackID = x + 10
		const power = ((rackID * y) + this.serial) * rackID
		return ((power % 1000 - power % 100) / 100) - 5
	}

	poweredSquare() {
		let grid = {}
		let gridSize = 300

		let bestCoord3 = "0,0"
		let bestCoord = "0,0,0"
		let bestPower3 = 0
		let bestPower = 0

		let currentMax = 0

		for (let n = 1; n <= gridSize; n++) {
			let max = 0

			for (let x = 1; x <= gridSize - n + 1; x++) {
				for (let y = 1; y <= gridSize - n + 1; y++) {
					let power = 0
					const label = `${x},${y}`

					if (n == 1) {
						grid[label] = {
							1: this.powerLevel(x, y)
						}
						power += grid[label][1]
					} else {
						// ####   ###.   ....   ....   ...#   ....
						// ####   ###.   .###   ....   ....   .##.
						// #### = ###. + .### + .... + .... - .##.
						// ####   ....   .###   #...   ....   ....
						power += grid[`${x},${y}`][n - 1]
							+ grid[`${x + 1},${y + 1}`][n - 1]
							+ grid[`${x + n - 1},${y}`][1]
							+ grid[`${x},${y + n - 1}`][1]
						if (n != 2) {
							power -= grid[`${x + 1},${y + 1}`][n - 2]
						}
						grid[label][n] = power
					}

					if (power > 0) {
						if (n == 3 && power > bestPower3) {
							bestCoord3 = label
							bestPower3 = power
						}
						if (power > bestPower) {
							bestCoord = `${label},${n}`
							bestPower = power
						}
					}

					max = Math.max(power, max)
				}
			}

			if (currentMax > max) {
				break
			} else {
				currentMax = max
			}
		}

		return {
			3: bestCoord3,
			n: bestCoord
		}
	}

	get part1() {
		return this.powerfullSquares[3]
	}

	get part2() {
		return this.powerfullSquares.n
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new ChronalCharge(input)
	},

	part1: '19,17',
	part2: '233,288,12'
}