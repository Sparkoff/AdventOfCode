class License {
	constructor(numbers) {
		this.nodes = this.extractNode(numbers.split(/\s/).map(n => parseInt(n)))
	}

	extractNode(numbers) {
		let node = {
			childs: [],
			metadata:[],
			checksum: 0,
			license: 0
		}

		let header = numbers.splice(0, 2)

		for (let i = 0; i < header[0]; i++) {
			let child = this.extractNode(numbers)
			node.childs.push(child)
			node.checksum += child.checksum
		}

		node.metadata = numbers.splice(0, header[1])
		node.checksum += node.metadata.reduce((a, c) => a + c, 0)

		if (node.childs.length) {
			for (const i of node.metadata) {
				if (i <= node.childs.length) {
					node.license += node.childs[i - 1].license
				}
			}
		} else {
			node.license = node.checksum
		}

		return node
	}

	get part1() {
		return this.nodes.checksum
	}

	get part2() {
		return this.nodes.license
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new License(input)
	},

	part1: 43996,
	part2: 35189
}