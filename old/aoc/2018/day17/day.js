class Reservoir {
	constructor(map) {
		this.clay = new Set()
		this.restWater = new Set()
		this.passingWater = new Set()
		this.infinitePassingWater = new Set()
		this.max = {
			x: 2000,
			y: 0
		}
		this.printLimit = {
			xmin: Number.MAX_SAFE_INTEGER,
			xmax: 0
		}
		this.source = this.idx(500, 0)

		this.parseMap(map)
	}

	parseMap(map) {
		//this.max.x = Math.max(...[...map.join(' ').matchAll(/x=(\d+\.+)?(\d+)/g)].reduce((a, c) => { a.push(c[2]); return a }, []))
		map.forEach(vein => {
			vein = vein.match(/(x|y)=(\d+),\s[xy]=(\d+)\.\.(\d+)/)
			let x = 0
			let y = 0
			if (vein[1] == 'x') {
				x = parseInt(vein[2])
				for (y = parseInt(vein[3]); y <= parseInt(vein[4]); y++) {
					this.clay.add(this.idx(x, y))
				}
			} else {
				y = parseInt(vein[2])
				for (x = parseInt(vein[3]); x <= parseInt(vein[4]); x++) {
					this.clay.add(this.idx(x, y))
				}
			}
			this.max.y = Math.max(this.max.y, y)
			this.printLimit.xmin = Math.min(this.printLimit.xmin, x)
			this.printLimit.xmax = Math.max(this.printLimit.xmax, x)
		})
		this.max.y += 2
		this.printLimit.xmin--
		this.printLimit.xmax++
	}

	idx(x, y) {
		return y * this.max.x + x
	}
	isFree(id) {
		return !this.clay.has(id) && !this.restWater.has(id) && !this.passingWater.has(id)
	}
	isHorizontalObstacle(id) {
		return this.clay.has(id) || this.restWater.has(id)
	}
	isVerticalObstacle(id) {
		return this.clay.has(id) || this.passingWater.has(id)
	}
	isWater(id) {
		return this.restWater.has(id) || this.passingWater.has(id)
	}

	spill() {
		let next = [this.source + this.max.x]
		while (next.length) {
			let id = next.pop()

			if (this.isFree(id)) {
				this.passingWater.add(id)
			}
			if (id >= this.max.x * this.max.y) {
				continue
			}

			if (this.isFree(id + this.max.x)) {
				next.push(id + this.max.x)
				continue
			} else if (this.isHorizontalObstacle(id + this.max.x)) {
				if (this.isFree(id + 1)) {
					next.push(id + 1)
				}
				if (this.isFree(id - 1)) {
					next.push(id - 1)
				}

				if (this.isVerticalObstacle(id + 1) && this.isVerticalObstacle(id - 1)) {
					let explore = id
					while (this.isWater(explore + 1)) {
						explore++
					}
					if (!this.clay.has(explore + 1)) {
						continue
					}

					explore = id
					while (this.isWater(explore - 1)) {
						explore--
					}
					if (!this.clay.has(explore - 1)) {
						continue
					}

					explore = id
					this.passingWater.delete(explore)
					this.restWater.add(explore)

					if (this.passingWater.has(explore - this.max.x)) {
						next.push(explore - this.max.x)
					}

					while (this.isWater(explore + 1)) {
						this.passingWater.delete(explore + 1)
						this.restWater.add(explore + 1)

						explore++
						if (this.passingWater.has(explore - this.max.x)) {
							next.push(explore - this.max.x)
						}
					}
					while (this.isWater(explore - 1)) {
						this.passingWater.delete(explore - 1)
						this.restWater.add(explore - 1)

						explore--
						if (this.passingWater.has(explore - this.max.x)) {
							next.push(explore - this.max.x)
						}
					}
				}
			}
		}

		this.clearInfinite()
	}

	clearInfinite() {
		let min = (Math.floor(Math.min(...[...this.clay.values()]) / this.max.x)) * this.max.x
		let max = (Math.floor(Math.max(...[...this.clay.values()]) / this.max.x) + 1) * this.max.x

		let ids = ([...this.passingWater.values()]).filter(id => id < min || id > max)
		ids.forEach(id => {
			this.passingWater.delete(id)
			this.infinitePassingWater.add(id)
		})
	}

	print() {
		let scan = []
		for (let y = 0; y <= this.max.y; y++) {
			let row = ''
			for (let x = this.printLimit.xmin; x <= this.printLimit.xmax; x++) {
				if (this.restWater.has(this.idx(x, y))) {
					row += '~'
				} else if (this.passingWater.has(this.idx(x, y)) || this.infinitePassingWater.has(this.idx(x, y))) {
					row += '|'
				} else if (this.clay.has(this.idx(x, y))) {
					row += '#'
				} else if (this.idx(x, y) == this.source) {
					row += '+'
				} else {
					row += '.'
				}
			}
			scan.push(row)
		}
		console.log(scan.join('\n'));
	}

	get part1() {
		this.spill()
		//this.print()
		return this.restWater.size + this.passingWater.size
	}

	get part2() {
		if (this.restWater.size == 0) {
			this.spill()
		}
		return this.restWater.size
	}
}

module.exports = {
	answer: function (input) {
		return new Reservoir(input)
	},

	part1: 31788,
	part2: 25800
}
