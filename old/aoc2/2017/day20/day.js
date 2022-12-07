class Swarm {
	constructor(particules) {
		this.particules = particules.map((p, i) => this.parseParticule(p, i))
	}

	parseParticule(p, id) {
		p = p.split(/,\s/)
		p = p.map(x => x.match(/.*<(.*)>/)[1].split(','))
		return {
			id: id,
			p: this.parseCoordinates(p[0]),
			v: this.parseCoordinates(p[1]),
			a: this.parseCoordinates(p[2])
		}
	}
	parseCoordinates(c) {
		return {
			x: parseInt(c[0]),
			y: parseInt(c[1]),
			z: parseInt(c[2])
		}
	}

	distanceAt(particule, time) {
		let p = this.positionAt(particule, time)
		return Math.abs(p.x) + Math.abs(p.y) + Math.abs(p.z)
	}
	positionAt(particule, time) {
		return {
			x: particule.p.x + (time * particule.v.x) + ((time * (time + 1) * particule.a.x) / 2),
			y: particule.p.y + (time * particule.v.y) + ((time * (time + 1) * particule.a.y) / 2),
			z: particule.p.z + (time * particule.v.z) + ((time * (time + 1) * particule.a.z) / 2)
		}
	}
	closest(time) {
		let closest = 0
		let dist = this.distanceAt(this.particules[0], time)

		for (let p = 1; p < this.particules.length; p++) {
			let d = this.distanceAt(this.particules[p], time)
			if (dist > d) {
				dist = d
				closest = p
			} else if (dist == d) {
				closest = -1
			}
		}

		return closest
	}

	checkCollision(p1, p2) {
		let tx = this.solve(p1, p2, 'x')
		if (tx.hasSolution) {
			let ty = this.solve(p1, p2, 'y')
			if (ty.hasSolution) {
				let t = []
				if (tx.t.length != 0 && ty.t.length != 0) {
					t = tx.t.filter(x => ty.t.includes(x))
					if (t.length == 0) {
						return -1
					}
				} else if (tx.t.length == 0) {
					t = ty.t
				} else {
					t = tx.t
				}

				let tz = this.solve(p1, p2, 'z')
				if (tz.hasSolution) {
					if (t.length == 0) {
						if (tz.t.length == 0) {
							return 0
						} else {
							return Math.min(...tz.t)
						}
					} else if (tz.t.length == 0) {
						return Math.min(...t)
					} else {
						t = t.filter(x => tz.t.includes(x))
						if (t.length != 0) {
							return Math.min(...t)
						}
					}
				}
			}
		}

		return -1
	}
	solve(p1, p2, axe) {
		// second degree equation : (A0/2)t2 + (V0 + A0/2)t + P0 = at2 + bt + c = 0
		let a = (p2.a[axe] - p1.a[axe]) / 2
		let b = p2.v[axe] - p1.v[axe] + a
		let c = p2.p[axe] - p1.p[axe]

		let res = {
			t: [],
			hasSolution: false
		}

		if (a == 0) {
			if (b != 0) {
				let r = -c / b
				if (r > 0 && Number.isInteger(r)) {
					res.t.push(r)
					res.hasSolution = true
				} else if (r == 0) {
					res.hasSolution = true
				}
			} else if (c == 0) {
				res.hasSolution = true
			}
		} else if (b == 0) {
			let r = -c / a
			if (r > 0 && Number.isInteger(Math.sqrt(r))) {
				res.t.push(Math.sqrt(r))
				res.hasSolution = true
			} else if (r == 0) {
				res.hasSolution = true
			}
		} else {
			let delta = Math.pow(b, 2) - 4 * a * c
			if (delta >= 0) {
				let r1 = -(b + Math.sqrt(delta)) / (2 * a)
				let r2 = -(b - Math.sqrt(delta)) / (2 * a)

				if (r1 >= 0 && Number.isInteger(r1)) {
					res.t.push(r1)
					res.hasSolution = true
				}
				if (r2 >= 0 && Number.isInteger(r2)) {
					res.t.push(r2)
					res.hasSolution = true
				}
			}
		}

		return res
	}

	runWithCollisions() {
		let particules = this.particules.reduce((a, c) => {
			a[c.id] = c
			return a
		}, {})
		let collisions = {}

		for (let p1 = 0; p1 < this.particules.length; p1++) {
			for (let p2 = p1 + 1; p2 < this.particules.length; p2++) {
				let t = this.checkCollision(this.particules[p1], this.particules[p2])
				if (t != -1) {
					if (!collisions.hasOwnProperty(t)) {
						collisions[t] = []
					}
					collisions[t].push([p1, p2])
				}
			}
		}

		let ticks = Object.keys(collisions)
		ticks.sort((a, b) => a - b)

		for (let i = 0; i < ticks.length; i++) {
			let toRemove = collisions[ticks[i]].filter(c => particules.hasOwnProperty(c[0]) && particules.hasOwnProperty(c[1]))
			toRemove = toRemove.flat()
			for (let j = 0; j < toRemove.length; j++) {
				if (particules.hasOwnProperty(toRemove[j])) {
					delete particules[toRemove[j]]
				}
			}
		}

		return Object.keys(particules).length
	}

	get part1() {
		return this.closest(1E9)
	}

	get part2() {
		return this.runWithCollisions()
	}
}

module.exports = {
	answer: function (input) {
		return new Swarm(input)
	},

	part1: 125,
	part2: 461
}