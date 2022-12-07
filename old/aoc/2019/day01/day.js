class PrepareLaunch {
	constructor(masses) {
		this.masses = masses.map(f => parseInt(f))
		this.fuels = {
			modules: 0,
			dependancies: 0
		}

		this.calculateFuel()
	}

	calculateFuel() {
		let masses = this.masses
		while (masses.length != 0) {
			let dependancies = []
			let requiredFuel = masses.reduce((totalFuel, moduleMass) => {
				let mass = Math.floor(moduleMass / 3) - 2
				if (mass > 2) {
					dependancies.push(mass)
				} else if (mass < 0) {
					mass = 0
				}
				return totalFuel + mass
			}, 0)

			if (this.fuels.modules == 0) {
				this.fuels.modules = requiredFuel
			}
			this.fuels.dependancies += requiredFuel

			masses = dependancies
		}
	}

	get part1() {
		return this.fuels.modules
	}

	get part2() {
		return this.fuels.dependancies
	}
}

module.exports = {
	answer: function (input) {
		return new PrepareLaunch(input)
	},

	part1: 3303995,
	part2: 4953118
}