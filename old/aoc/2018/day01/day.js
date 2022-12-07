class Calibration {
	constructor(frequencies) {
		this.frequencies = frequencies.map(f => parseInt(f))
	}

	calibrate() {
		let f = 0
		let seen = {
			0: true
		}

		while (true) {
			for (const freq of this.frequencies) {
				f += freq

				if (seen[f]) {
					return f
				}
				seen[f] = true
			}
		}
	}

	get part1() {
		return this.frequencies.reduce((a, c) => a + c, 0)
	}

	get part2() {
		return this.calibrate()
	}
}

module.exports = {
	answer: function (input) {
		return new Calibration(input)
	},

	part1: 477,
	part2: 390
}