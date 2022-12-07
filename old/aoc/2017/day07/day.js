class Tower {
	constructor(programs) {
		this.tower = {}
		this.root = ""

		this.parseTower(programs)
	}

	parseTower(programs) {
		let parentList = []

		for (let i = 0; i < programs.length; i++) {
			let line = programs[i].split(/\s\->\s/)

			let info = line[0].match(/(\w+)\s\((\d+)\)/)
			this.tower[info[1]] = {
				weight: parseInt(info[2]),
				childsWeight: 0,
				childs: [],
				parent: null
			}

			if (line[1]) {
				this.tower[info[1]].childs = line[1].split(/,\s/)
				parentList.push(info[1])
			}
		}

		for (const p in this.tower) {
			if (this.tower[p].childs.length != 0) {
				for (let i = 0; i < this.tower[p].childs.length; i++) {
					this.tower[this.tower[p].childs[i]].parent = p

					const index = parentList.indexOf(this.tower[p].childs[i])
					if (index != -1) {
						parentList.splice(index, 1)
					} 
				}
			}
		}
		this.root = parentList[0]
	}


	getWeight(node) {
		if (this.tower[node].childs.length != 0) {
			for (let i = 0; i < this.tower[node].childs.length; i++) {
				this.tower[node].childsWeight += this.getWeight(this.tower[node].childs[i])
			}
			return this.tower[node].childsWeight + this.tower[node].weight
		} else {
			return this.tower[node].weight
		}
	}

	computeWeight() {
		this.tower[this.root].childsWeight = this.getWeight(this.root)
	}

	get balance() {
		let current = this.root
		let parent = null
		let weights = []

		while (this.tower[current].childs.length != 0) {
			weights = []
			for (let i = 0; i < this.tower[current].childs.length; i++) {
				let node = this.tower[current].childs[i]
				weights.push({
					name: node,
					weight: this.tower[node].weight + this.tower[node].childsWeight
				})
			}
			weights.sort((a, b) => {
				return a.weight - b.weight
			})

			if (weights[0].weight != weights[1].weight) {
				parent = current
				current = weights[0].name
			} else if (weights[weights.length - 1].weight != weights[weights.length - 2].weight) {
				parent = current
				current = weights[weights.length - 1].name
			} else {
				break
			}
		}

		let other = this.tower[parent].childs[0]
		if (other == current) {
			other = this.tower[parent].childs[1]
		}

		return this.tower[other].weight + this.tower[other].childsWeight - this.tower[current].childsWeight
	}

	get part1() {
		return this.root
	}

	get part2() {
		this.computeWeight()
		return this.balance
	}
}

module.exports = {
	answer: function (input) {
		return new Tower(input)
	},

	part1: 'cyrupz',
	part2: 193
}