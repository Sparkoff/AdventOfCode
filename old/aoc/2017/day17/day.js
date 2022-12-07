class SpinLock {
	constructor(step) {
		this.step = parseInt(step)
		this.circular = []
		this.index = 0

		this.lastNeightboor = 0
		this.cancelValue = 0

		this.run()
	}

	run() {
		this.circular.push(0, 1)
		this.index = 1
		this.cancelValue = 1
		let circularLength = 2

		for (let i = 2; i <= 5E7; i++) {
			this.stepIncrementCircular(circularLength)

			if (i <= 2017) {
				this.circular.splice(this.index, 0, i)
			}

			circularLength++

			if (i == 2017) {
				this.lastNeightboor = (this.index + 1 == circularLength) ? this.circular[0] : this.circular[this.index + 1]
			}

			if (this.index == 1) {
				this.cancelValue = i
			}
		}
	}

	stepIncrementCircular(length) {
		this.index += this.step % length
		if (this.index >= length) {
			this.index -= length
		}
		this.index += 1
	}

	get part1() {
		return this.lastNeightboor
	}

	get part2() {
		return this.cancelValue
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new SpinLock(input)
	},

	part1: 808,
	part2: 47465686
}