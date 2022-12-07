class NanobotCould {
	constructor(nanobots) {
		this.nanobots = new Map()
		this.min = {
			x: Number.MAX_SAFE_INTEGER,
			y: Number.MAX_SAFE_INTEGER,
			z: Number.MAX_SAFE_INTEGER
		}
		this.max = {
			x: -Number.MAX_SAFE_INTEGER,
			y: -Number.MAX_SAFE_INTEGER,
			z: -Number.MAX_SAFE_INTEGER
		}

		this.parseNanobots(nanobots)
	}

	parseNanobots(nanobots) {
		let id = 0
		for (const nanobot of nanobots) {
			let data = nanobot.match(/\-?\d+/g).map(x => parseInt(x))

			this.min.x = Math.min(this.min.x, data[0])
			this.min.y = Math.min(this.min.y, data[1])
			this.min.z = Math.min(this.min.z, data[2])
			this.max.x = Math.max(this.max.x, data[0])
			this.max.y = Math.max(this.max.y, data[1])
			this.max.z = Math.max(this.max.z, data[2])

			this.nanobots.set(id++, {
				x: data[0],
				y: data[1],
				z: data[2],
				r: data[3]
			})
		}
	}

	manhattan(id1, id2) {
		let p1 = typeof id1 == 'number' ? this.nanobots.get(id1) : id1
		let p2 = typeof id2 == 'number' ? this.nanobots.get(id2) : (id2 !== undefined ? id2 : { x: 0, y: 0, z: 0 })
		return Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y) + Math.abs(p2.z - p1.z)
	}
	inRange(id) {
		return ([...this.nanobots.keys()]).reduce((a, c) => a + (this.manhattan(c, id) <= this.nanobots.get(id).r ? 1 : 0), 0)
	}
	selectPoint(pt1, pt2) {
		if (pt1.nanobotsInRange != pt2.nanobotsInRange) {
			return pt1.nanobotsInRange > pt2.nanobotsInRange ? pt1 : pt2
		} else {
			// closest to origin
			return this.manhattan(pt1) < this.manhattan(pt2) ? pt1 : pt2
		}
	}


	get part1() {
		let id = ([...this.nanobots.values()]).reduce((a, c, i) => this.nanobots.get(a).r > c.r ? a : i, 0)
		return ([...this.nanobots.keys()]).reduce((a, c) => a + (this.manhattan(id, c) <= this.nanobots.get(id).r ? 1 : 0), 0)
	}

	get part2() {
		// try Mean shift algo

		let best = null
		let factor = 1E7
		while (factor >= 1) {
			const start = best ? {
				x: best.x - (factor * 10),
				y: best.y - (factor * 10),
				z: best.z - (factor * 10)
			} : this.min

			const end = best ? {
				x: best.x + (factor * 10),
				y: best.y + (factor * 10),
				z: best.z + (factor * 10)
			} : this.max

			// reset best position
			best = null
			for (let x = start.x; x <= end.x; x += factor) {
				for (let y = start.y; y <= end.y; y += factor) {
					for (let z = start.z; z <= end.z; z += factor) {

						let nanobotsInRange = 0
						for (let i = 0; i < this.nanobots.size; i++) {
							nanobotsInRange += (this.manhattan(i, { x, y, z }) <= this.nanobots.get(i).r) ? 1 : 0
						}
						const pt = {
							x, y, z,
							nanobotsInRange//: ([...this.nanobots.keys()]).reduce((a, c) => a + (this.manhattan(c, { x, y, z }) <= this.nanobots.get(c).r ? 1 : 0), 0)
						}

						if (best === null) {
							best = pt
						} else {
							best = this.selectPoint(best, pt)
						}
					}
				}
			}

			factor /= 10
		}

		return this.manhattan(best)
	}
}

module.exports = {
	answer: function (input) {
		return new NanobotCould(input)
	},

	part1: 399,
	part2: 81396996
}
