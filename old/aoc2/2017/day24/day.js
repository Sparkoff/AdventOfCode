class Bridge {
	constructor(components) {
		this.components = components.map(c => c.split('/').map(v => parseInt(v)))
		this.maxStrength = 0
		this.maxLength = {
			l: 0,
			s: 0
		}
		this.next(this.components, [], 0)
	}

	next(remains, current, end) {
		let components = remains.filter(c => c[0] == end || c[1] == end)

		if (components.length != 0) {
			for (let i = 0; i < components.length; i++) {
				let newBridge = current.slice(0) // clone
				newBridge.push(components[i])
				this.next(
					remains.filter(c => c != components[i]),
					newBridge,
					components[i][0] == end ? components[i][1] : components[i][0]
				)
			}
		} else {
			let strength = current.flat().reduce((a, c) => a + c)
			if (this.maxStrength < strength) {
				this.maxStrength = strength
			}
			if (this.maxLength.l < current.length) {
				this.maxLength.l = current.length
				this.maxLength.s = strength
			} else if (this.maxLength.l == current.length && this.maxLength.s < strength) {
				this.maxLength.s = strength
			}
		}
	}

	get part1() {
		return this.maxStrength
	}

	get part2() {
		return this.maxLength.s
	}
}

module.exports = {
	answer: function (input) {
		return new Bridge(input)
	},

	part1: 1940,
	part2: 1928
}