const FREE = 'free'
const ELF = 'elf'
const GOBLIN = 'goblin'

class CaveBattle {
	constructor(map) {
		this.cavern = new Set()
		this.dimensions = {
			x: 0,
			y: 0,
			max: 0
		}
		this.units = {
			elfs: new Map(),
			goblins: new Map()
		}
		this.default = {
			attack: 3,
			hp: 200
		}

		this.elfs = null
		this.goblins = null

		this.results = {}

		this.parseMap(map)
	}

	parseMap(map) {
		this.dimensions.x = map[0].length
		this.dimensions.y = map.length
		this.dimensions.max = (map.length * map[0].length) - 1

		map.forEach((row, y) => {
			row.split('').forEach((slot, x) => {
				if (slot != '#') {
					const id = this.idx(x, y)

					if (slot == 'E') {
						this.units.elfs.set(id, {
							attack: this.default.attack,
							hp: this.default.hp
						})
					} else if (slot == 'G') {
						this.units.goblins.set(id, {
							attack: this.default.attack,
							hp: this.default.hp
						})
					}
					this.cavern.add(id)
				}
			})
		})
	}

	clone(map) {
		let newMap = new Map()
		map.forEach((unit, id) => {
			newMap.set(id, Object.assign({}, unit))
		})
		return newMap
	}
	idx(x, y) {
		return y * this.dimensions.x + x
	}
	sortedIdx() {
		return [ELF, GOBLIN].reduce((ids, type) => {
			let unitIds = [...this[`${type}s`].keys()].map(id => ({
				id,
				type
			}))
			return ids.concat(unitIds)
		}, []).sort((a, b) => a.id - b.id)
	}
	remainingHP() {
		return [...this.elfs.values()].concat([...this.goblins.values()]).reduce((hp, unit) => hp + unit.hp, 0)
	}
	adjacents(id, type) {
		return([-this.dimensions.x, -1, 1, this.dimensions.x]).reduce((adjacentIds, diff, i) => {
			let newId = id + diff
			if (
				newId >= 0 &&
				newId <= this.dimensions.max &&
				!(i == 1 && newId % this.dimensions.x == this.dimensions.x - 1) &&
				!(i == 2 && newId % this.dimensions.x == 0) &&
				this.cavern.has(newId)
			) {
				if (
					(type == FREE && !this.elfs.has(newId) && !this.goblins.has(newId)) ||
					(type == ELF && this.elfs.has(newId)) ||
					(type == GOBLIN && this.goblins.has(newId))
				) {
					adjacentIds.push(newId)
				}
			}
			return adjacentIds
		}, [])
	}
	destination(type) {
		return new Set([...(type == ELF ? this.elfs : this.goblins).keys()].reduce((destinationIds, id) => destinationIds.concat(this.adjacents(id, 'free')), []))
	}

	run(elfAttack) {
		this.elfs = this.clone(this.units.elfs)
		this.goblins = this.clone(this.units.goblins)

		let earlyStop = false
		if (elfAttack) {
			this.elfs.forEach((_, id) => this.elfs.get(id).attack = elfAttack)
			earlyStop = true
		}

		let turn = 0
		loop: while (true) {
			for (let { id, type } of this.sortedIdx()) {
				if (this[`${type}s`].has(id)) {
					if (this.elfs.size == 0 || this.goblins.size == 0) {
						break loop
					}

					id = this.move(id, type)
					this.fight(id, type)
				}
			}
			
			if (earlyStop && this.elfs.size != this.units.elfs.size) {
				return {
					success: false,
					outcome: 0
				}
			}

			turn++
		}

		return {
			success: this.elfs.size == this.units.elfs.size,
			outcome: this.remainingHP() * turn
		}
	}
	move(id, type) {
		let unit = this[`${type}s`].get(id)
		const ennemyType = (type == ELF) ? GOBLIN : ELF

		// unit can move if it has a free space and no fight already engaged with ennemy
		if (this.adjacents(id, ennemyType).length == 0) {
			let targets = this.destination(ennemyType)

			// only move if an ennemy has a reachable free slot
			if (targets.size != 0) {
				// store list of complete path and already visited free slots
				let paths = [[id]]
				let visited = new Set()

				while (paths.length != 0) {
					let newPaths = []
					let validPaths = []

					paths.forEach(path => {
						let adjacents = this.adjacents(path[path.length - 1], FREE)
						adjacents.forEach(adjacent => {
							if (targets.has(adjacent)) {
								// if a path reached a target, store it in valid list
								validPaths.push([...path, adjacent])
							} else if (!visited.has(adjacent)) {
								// if a path can be extended with a free slot, the new path is stored separatly
								newPaths.push([...path, adjacent])
							}
							visited.add(adjacent)
						})
					})

					if (validPaths.length > 0) {
						validPaths = validPaths.sort((a, b) => a[a.length - 1] - b[b.length - 1]).shift()

						this[`${type}s`].delete(id)
						this[`${type}s`].set(validPaths[1], unit)
						return validPaths[1]
					}

					// replace existing paths list by new paths updated. paths that reach dead-end or already visited slots are not registed in the new paths
					paths = newPaths
				}
			}
		}
		return id
	}
	fight(id, type) {
		let unit = this[`${type}s`].get(id)
		const ennemyType = (type == ELF) ? GOBLIN : ELF

		let ennemies = this.adjacents(id, ennemyType).map(ennemyId => ({
			id: ennemyId,
			hp: this[`${ennemyType}s`].get(ennemyId).hp
		}))
		if (ennemies.length != 0) {
			let ennemy = ennemies.sort((a, b) => {
				if (a.hp == b.hp) {
					return a.id - b.id
				}
				return a.hp - b.hp
			})[0]

			ennemy.hp -= unit.attack

			if (ennemy.hp <= 0) {
				this[`${ennemyType}s`].delete(ennemy.id)
			} else {
				this[`${ennemyType}s`].get(ennemy.id).hp = ennemy.hp
			}
		}
	}

	print(turn) {
		let m = [`After ${turn} rounds:`]
		for (let y = 0; y < this.dimensions.y; y++) {
			let r = ''
			let hp = []
			for (let x = 0; x < this.dimensions.x; x++) {
				const id = this.idx(x, y)
				if (this.elfs.has(id)) {
					r += 'E'
					hp.push(`E(${this.elfs.get(id).hp})`)
				} else if (this.goblins.has(id)) {
					r += 'G'
					hp.push(`G(${this.goblins.get(id).hp})`)
				} else if (this.cavern.has(id)) {
					r += '.'
				} else {
					r += '#'
				}
			}
			if (hp.length != 0) {
				hp = hp.join(', ')
				r += `    ${hp}`
			}
			m.push(r)
		}
		console.log(m.join('\n'));
	}

	get part1() {
		this.results[this.default.attack] = this.run()

		return this.results[this.default.attack].outcome
	}

	get part2() {
		// *2 until victory, then half difference of min-max dps
		//   dps   outcome                   lower  upper
		//     3   elves won with losses         0    inf
		//     6   elves won with losses         3    inf
		//    12   elves won with losses         6    inf
		//    24   elves won with losses        12    inf
		//    48   elves flawless victory       24    inf
		//    36   elves flawless victory       24     48
		//    30   elves flawless victory       24     36
		//    27   elves won with losses        24     30
		//    28   elves won with losses        27     30
		//    29   elves flawless victory       28     30
		// Round 28: remaining elf hitpoints 1493 with dps 29

		let lower = 1
		let upper = Number.MAX_SAFE_INTEGER

		if (!this.results[this.default.attack]) {
			this.results[this.default.attack] = this.run()
		}

		let current = 1
		if (this.results[this.default.attack].success) {
			upper = this.default.attack
		} else {
			lower = this.default.attack
			current = 3
		}

		while (true) {
			if (this.results[1] && this.results[1].success) {
				return this.results[1].outcome
			} else if (upper == Number.MAX_SAFE_INTEGER) {
				current = (lower == 1) ? 1 : current * 2
			} else if (upper - lower != 1) {
				current = lower + Math.floor((upper - lower) / 2)
			} else {
				return this.results[upper].outcome
			}

			this.results[current] = this.run(current)

			if (this.results[current].success) {
				upper = current
			} else {
				lower = current
			}
		}
	}
}

module.exports = {
	answer: function (input) {
		return new CaveBattle(input)
	},

	part1: 248235,
	part2: 46784
}
