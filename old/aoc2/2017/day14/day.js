const generateHash = require('../day10/day').answer

class HardDisk {
	constructor(key) {
		this.grid = []

		this.makeGrid(key)
	}

	makeGrid(key) {
		for (let row = 0; row < 128; row++) {
			let column = 0
			generateHash(key + '-' + row).knotHash.split('').map(c => {
				const indexes = this.toBinary(c).split('').reduce((a, c, i) => {
					if (c == '1') {
						a.push(i)
					}
					return a
				}, [])
				indexes.forEach(index => this.grid.push(row + ',' + (column + index)))
				column += 4
			})

		}
	}

	toBinary(hex) {
		return parseInt(hex, 16).toString(2).padStart(4, '0')
	}

	get regions() {
		let count = 1
		let next = []
		next.push(this.grid.shift())

		while (this.grid.length > 0) {
			if (next.length > 0) {
				let pos = next.shift().split(',').map(x => parseInt(x))

				Array([1, 0], [-1, 0], [0, 1], [0, -1]).forEach(mut => {
					let index = this.grid.indexOf((pos[0] + mut[0]) + ',' + (pos[1] + mut[1]))
					if (index != -1) {
						next = next.concat(this.grid.splice(index, 1))
					}
				})
			} else {
				next.push(this.grid.shift())
				count++
			}
		}

		return count
	}

	get part1() {
		return this.grid.length
	}

	get part2() {
		return this.regions
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new HardDisk(input)
	},

	part1: 8106,
	part2: 1164
}