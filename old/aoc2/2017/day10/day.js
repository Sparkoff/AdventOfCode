class KnotHash {
	constructor(lengths, size) {
		this.size = size || 256
		this.hash = null
		this.denseHash = null
		this.lengths = lengths
	}

	runRound(lengths, step) {
		step = step || { current: 0, skip: 0 }
		for (let i = 0; i < lengths.length; i++) {
			let l = lengths[i]
			if (l > 1) {
				let n = l / 2 >> 0

				for (let ni = 0; ni < n; ni++) {
					let e1 = this.validateCircularIndex(step.current + ni)
					let e2 = this.validateCircularIndex(step.current + l - 1 - ni)

					this.hash[e1] = this.hash.splice(e2, 1, this.hash[e1])[0]
				}
			}

			step.current = this.validateCircularIndex(step.current + l + step.skip)
			step.skip++
		}
		return step
	}

	demo() {
		const lengths = this.lengths.split(',').map(l => parseInt(l))
		this.hash = [...Array(this.size).keys()]

		this.runRound(lengths)
	}

	run() {
		const lengths = this.lengths.split('').map(l => l.charCodeAt(0)).concat([17, 31, 73, 47, 23])
		this.hash = [...Array(this.size).keys()]
		this.denseHash = []


		let step = null
		for (let i = 0; i < 64; i++) {
			step = this.runRound(lengths, step)
		}

		while (this.hash.length > 0) {
			this.denseHash.push(this.toDense(this.hash.splice(0, 16)))
		}
	}

	validateCircularIndex(index) {
		return index % this.hash.length
	}

	toDense(hash) {
		return hash.reduce((a, c) => a ^ c)
	}

	toHexa(value) {
		return Number(value).toString(16).padStart(2, '0')
	}

	get knotHash() {
		this.run()
		return this.denseHash.map(h => this.toHexa(h)).join('')
	}

	get part1() {
		this.demo()
		return this.hash[0] * this.hash[1]
	}

	get part2() {
		return this.knotHash
	}
}

module.exports = {
	answer: function (input, size) {
		input = (Array.isArray(input)) ? input[0] : input
		return new KnotHash(input, size)
	},

	part1: 29240,
	part2: '4db3799145278dc9f73dcdbc680bd53d'
}