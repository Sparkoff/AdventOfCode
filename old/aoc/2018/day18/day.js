class Settler {
	constructor(map) {
		this.openGrounds = new Set()
		this.trees = new Set()
		this.lumberyards = new Set()

		this.acres = 0

		this.knowns = new Map()

		this.parseMap(map)
		this.knowns.set(this.hash(), this.trees.size * this.lumberyards.size)
	}

	parseMap(map) {
		this.acres = map.length

		for (let y = 0; y < map.length; y++) {
			for (let x = 0; x < map[y].length; x++) {
				switch (map[y][x]) {
					case '.':
						this.openGrounds.add(this.idx(x, y))
						break
					case '|':
						this.trees.add(this.idx(x, y))
						break
					case '#':
						this.lumberyards.add(this.idx(x, y))
						break
				}
			}
		}
	}

	idx(x, y) {
		return y * this.acres + x
	}
	adjacents(id) {
		let neighboors = {
			openGrounds: 0,
			trees: 0,
			lumberyards: 0
		}

		const xdiffs = [-1, 0, 1]
		const ydiffs = [-this.acres, 0, this.acres]
		xdiffs.forEach((xdiff, xi) => {
			ydiffs.forEach((ydiff, yi) => {
				const newId = id + xdiff + ydiff
				if (
					(xi == 0 && newId % this.acres == this.acres - 1) ||
					(xi == 2 && newId % this.acres == 0) ||
					(xi == 1 && yi == 1) ||
					(yi == 0 && newId < 0) ||
					(yi == 2 && newId > Math.pow(this.acres, 2))
				) {
					return
				}
				if (this.openGrounds.has(newId)) {
					neighboors.openGrounds++
				} else if (this.trees.has(newId)) {
					neighboors.trees++
				} else {
					neighboors.lumberyards++
				}
			})
		})
		return neighboors
	}
	hash() {
		return ([...this.trees.values()]).concat([...this.lumberyards.values()]).join(',')
	}

	tick() {
		let tmpOpenGrounds = new Set()
		let tmpTrees = new Set()
		let tmpLumberyards = new Set()

		const max = Math.pow(this.acres, 2)
		for (let id = 0; id < max; id++) {
			let adjacents = this.adjacents(id)
			if (this.openGrounds.has(id)) {
				if (adjacents.trees >= 3) {
					tmpTrees.add(id)
				} else {
					tmpOpenGrounds.add(id)
				}
			} else if (this.trees.has(id)) {
				if (adjacents.lumberyards >= 3) {
					tmpLumberyards.add(id)
				} else {
					tmpTrees.add(id)
				}
			} else {
				if (adjacents.lumberyards == 0 || adjacents.trees == 0) {
					tmpOpenGrounds.add(id)
				} else {
					tmpLumberyards.add(id)
				}
			}
		}

		this.openGrounds = tmpOpenGrounds
		this.trees = tmpTrees
		this.lumberyards = tmpLumberyards
	}

	print(time) {
		let map = [`After ${time} minutes:`]
		for (let y = 0; y < this.acres; y++) {
			let row = ''
			for (let x = 0; x < this.acres; x++) {
				if (this.openGrounds.has(this.idx(x, y))) {
					row += '.'
				} else if (this.trees.has(this.idx(x, y))) {
					row += '|'
				} else {
					row += '#'
				}
			}
			map.push(row)
		}
		console.log(map.join('\n'))
	}

	get part1() {
		while (this.knowns.size <= 10) {
			this.tick()
			this.knowns.set(this.hash(), this.trees.size * this.lumberyards.size)
		}
		return this.trees.size * this.lumberyards.size
	}

	get part2() {
		const end = 1E9

		let hash = ''
		while (true) {
			this.tick()

			hash = this.hash()
			if (this.knowns.size == end) {
				return this.trees.size * this.lumberyards.size
			} else if (this.knowns.has(hash)) {
				break
			} else {
				this.knowns.set(hash, this.trees.size * this.lumberyards.size)
			}
		}

		let pattern = []
		for (const [id, value] of this.knowns.entries()) {
			if (id == hash || pattern.length != 0) {
				pattern.push(value)
			}
		}

		return pattern[(end - this.knowns.size) % pattern.length]
	}
}

module.exports = {
	answer: function (input) {
		return new Settler(input)
	},

	part1: 614812,
	part2: 212176
}
