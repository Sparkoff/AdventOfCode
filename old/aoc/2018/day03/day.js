class ChimneySqueezeFabric {
	constructor(claims) {
		this.claims = []
		this.ids = []
		this.map = {}

		this.coverage = 0
		this.id_noOverlap = 0
		
		this.parseClaim(claims)
		this.run()
	}

	parseClaim(claims) {
		for (const claim of claims) {
			let c = claim.match(/^#(\d+)\s@\s(\d+),(\d+):\s(\d+)x(\d+)$/)
			this.claims.push({
				id: parseInt(c[1]),
				pos: {
					x: parseInt(c[2]),
					y: parseInt(c[3])
				},
				size: {
					w: parseInt(c[4]),
					h: parseInt(c[5])
				}
			})
			this.ids.push(parseInt(c[1]))
		}
	}

	run() {
		for (const claim of this.claims) {
			for (let ix = claim.pos.x; ix < claim.pos.x + claim.size.w; ix++) {
				for (let iy = claim.pos.y; iy < claim.pos.y + claim.size.h; iy++) {
					const label = `${ix},${iy}`
					if (!this.map[label]) {
						this.map[label] = []
					}
					this.map[label].push(claim.id)
				}
			}
		}

		let ids = this.ids.slice(0)
		for (const square of Object.values(this.map)) {
			if (square.length != 1) {
				this.coverage++

				for (const id of square) {
					let index = ids.indexOf(id)
					if (index != -1) {
						ids.splice(index, 1)
					}
				}
			}
		}
		this.id_noOverlap = ids.pop()
	}

	get part1() {
		return this.coverage
	}

	get part2() {
		return this.id_noOverlap
	}
}

module.exports = {
	answer: function (input) {
		return new ChimneySqueezeFabric(input)
	},

	part1: 112378,
	part2: 603
}