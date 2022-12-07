class Manhattan {
	constructor(coords, limit) {
		this.grid = {
			points: [],
			xmin: Number.MAX_SAFE_INTEGER,
			xmax: 0,
			ymin: Number.MAX_SAFE_INTEGER,
			ymax: 0,
			map: []
		}
		this.limit = limit || 1E4
		this.controledAera = 0

		this.parseCoordinates(coords)
		this.run()
	}

	parseCoordinates(coords) {
		for (let coord of coords) {
			coord = coord.split(/,\s/).map(x => parseInt(x))
			this.grid.xmin = Math.min(this.grid.xmin, coord[0])
			this.grid.xmax = Math.max(this.grid.xmax, coord[0])
			this.grid.ymin = Math.min(this.grid.ymin, coord[1])
			this.grid.ymax = Math.max(this.grid.ymax, coord[1])
			this.grid.points.push(coord)
		}
	}

	run() {
		for (let x = this.grid.xmin; x <= this.grid.xmax; x++) {
			for (let y = this.grid.ymin; y <= this.grid.ymax; y++) {
				let distances = this.grid.points.map(p => this.distance([x, y], p))
				let minDist = Math.min(...distances)
				let ptId = distances.indexOf(minDist)

				if (distances.reduce((a, c) => a + c, 0) < this.limit) {
					this.controledAera++
				}
				if (distances.lastIndexOf(minDist) == ptId) {
					this.grid.map.push({
						x,
						y,
						id: ptId
					})
				}
			}
		}
	}

	distance(p1, p2) {
		return Math.abs(p2[0] - p1[0]) + Math.abs(p2[1] - p1[1])
	}

	get part1() {
		let distances = Array(this.grid.points.length).fill(0)
		for (const point of this.grid.map) {
			if (distances[point.id] == -1) {
				continue
			}
			if (
				point.x == this.grid.xmin ||
				point.x == this.grid.xmax ||
				point.y == this.grid.ymin ||
				point.y == this.grid.ymax
			) {
				distances[point.id] = -1
			} else {
				distances[point.id]++
			}
		}
		return Math.max(...distances)
	}

	get part2() {
		return this.controledAera
	}
}

module.exports = {
	answer: function (input, limit) {
		return new Manhattan(input, limit)
	},

	part1: 4290,
	part2: 37318
}