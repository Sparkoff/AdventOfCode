class InventoryManagementSystem {
	constructor(ids) {
		this.ids = ids
	}

	get checksum() {
		let check = this.ids.slice(0)
			.map(id => id.split('').reduce((a, c) => {
				if (!a[c]) {
					a[c] = 0
				}
				a[c]++
				return a
			}, {}))
			.reduce((a, c) => {
				let counts = Object.values(c)
				a[0] += counts.indexOf(2) != -1 ? 1 : 0
				a[1] += counts.indexOf(3) != -1 ? 1 : 0
				return a
			}, [0, 0])
		return check[0] * check[1]
	}

	get closestIDSLetters() {
		for (let id1 = 0; id1 < this.ids.length; id1++) {
			for (let id2 = id1 + 1; id2 < this.ids.length; id2++) {
				let w1 = this.ids[id1]
				let w2 = this.ids[id2]

				let common = ''
				let distance = w1.length
				for (let index = 0; index < w1.length; index++) {
					if (w1[index] == w2[index]) {
						distance--
						common += w1[index]
					}
				}
				if (distance == 1) {
					return common
				}
			}
		}
	}

	get part1() {
		return this.checksum
	}

	get part2() {
		return this.closestIDSLetters
	}
}

module.exports = {
	answer: function (input) {
		return new InventoryManagementSystem(input)
	},

	part1: 6150,
	part2: 'rteotyxzbodglnpkudawhijsc'
}