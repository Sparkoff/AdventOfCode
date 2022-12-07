class Firewall {
	constructor(records) {
		this.layers = {}
		this.depth = 0

		this.buildLayers(records)
	}

	buildLayers(records) {
		records = records.map(r => r.split(/:\s/).map(l => parseInt(l))).map(l => {
			return {
				depth: l[0],
				range: l[1],
				layer: (l[1] - 1) * 2
			}
		})
		this.layers = records.reduce((map, entry) => {
			map[entry.depth] = entry
			return map
		}, {})

		this.depth = Math.max(...records.map(l => l.depth))
	}

	ride(delay) {
		delay = delay || 0
		let trip = {
			severity: 0,
			caught: false
		}

		for (let step = 0; step <= this.depth; step++) {
			if (this.layers.hasOwnProperty(step) && ((step + delay) % this.layers[step].layer) === 0) {
				trip.severity += this.layers[step].depth * this.layers[step].range
				trip.caught = true

				if (delay != 0) {
					return trip
				}
			}
		}

		return trip
	}

	passThrough() {
		let delay = 0
		while (true) {
			if (!this.ride(delay).caught) {
				return delay
			}
			delay++
		}
	}

	get part1() {
		return this.ride().severity
	}

	get part2() {
		return this.passThrough()
	}
}

module.exports = {
	answer: function (input) {
		return new Firewall(input)
	},

	part1: 3184,
	part2: 3878062
}