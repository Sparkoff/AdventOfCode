class Trampolines {
	constructor (jumps) {
		this.jumps = jumps.map(j => parseInt(j))

	}

	run (forward) {
		let jumps = this.jumps.slice(0)
		let current = 0
		let next = 0
		let step = 0

		while (next >= 0 && next < jumps.length) {
			step++
			current = next
			next = current + jumps[current]

			jumps[current] += (forward && jumps[current] >= 3) ? -1 : 1
		}
		return step
	}

	get part1() {
		return this.run()
	}

	get part2() {
		return this.run('forward')
	}
}

module.exports = {
	answer: function (input) {
		return new Trampolines(input)
	},

	part1: 342669,
	part2: 25136209
}