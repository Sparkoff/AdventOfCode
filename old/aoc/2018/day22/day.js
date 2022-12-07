const ROCKY = 'rocky'
const NARROW = 'narrow'
const WET = 'wet'

const CLIMBINGGEAR = 'climbingGear'
const TORCH = 'torch'
const NEITHER = 'neither'

class Cave {
	constructor(scan) {
		this.map = new Map()
		this.target = { id: 0, x: 0, y: 0 }
		this.mapOffset = 0
		
		this.buildMap(scan)
	}

	buildMap(scan) {
		let depth = parseInt(scan.shift().split(/:\s/)[1])
		
		scan = scan[0].split(/:\s/)[1].split(',')
		this.target.x = parseInt(scan[0])
		this.target.y = parseInt(scan[1])

		// set an offset in case the shortest path come outside the target coordinates
		// this path should not exceed the smallest coordinates (hope so!)
		this.mapOffset = Math.min(this.target.x, this.target.y)

		this.map.set('xmax', this.target.x + this.mapOffset)
		this.map.set('ymax', this.target.y + this.mapOffset)
		this.target.id = this.idx(this.target.x, this.target.y)

		for (let y = 0; y <= this.target.y + this.mapOffset; y++) {
			for (let x = 0; x <= this.target.x + this.mapOffset; x++) {
				let id = this.idx(x, y)

				let geologicIndex = -1

				if (id == 0 || id == this.target.id) {
					geologicIndex = 0
				} else if (y == 0) {
					geologicIndex = x * 16807
				} else if (x == 0) {
					geologicIndex = y * 48271
				} else {
					geologicIndex = this.map.get(id - 1).erosionLevel * this.map.get(id - this.map.get('xmax')).erosionLevel
				}

				let erosionLevel = (depth + geologicIndex) % 20183
				let risk = erosionLevel % 3
				let type = (risk == 0) ? ROCKY : ((risk == 1) ? WET : NARROW)

				this.map.set(id, {
					geologicIndex: geologicIndex,
					erosionLevel: erosionLevel,
					type: type,
					risk: risk
				})
			}
		}
	}

	idx(x, y) {
		return y * this.map.get('xmax') + x
	}
	validateTool(tool, type) {
		return (tool == TORCH && (type == ROCKY || type == NARROW)) ||
			(tool == CLIMBINGGEAR && (type == ROCKY || type == WET)) ||
			(tool == NEITHER && (type == WET || type == NARROW))
	}

	explore() {
		let explored = new Map()
		let next = [{
			id: 0,
			tool: TORCH,
			time: 0
		}]

		const xmax = this.map.get('xmax')
		const max = this.map.get('xmax') * this.map.get('ymax')
		const offsets = [0, -1, -xmax, 1, xmax]
		const tools = [CLIMBINGGEAR, TORCH, NEITHER]

		while (next.length != 0) {
			let step = next.shift()

			if (!explored.has(step.id)) {
				explored.set(step.id, {
					climbingGear: Number.MAX_SAFE_INTEGER,
					torch: Number.MAX_SAFE_INTEGER,
					neither: Number.MAX_SAFE_INTEGER
				})
			}

			if (explored.get(step.id)[step.tool] > step.time) {
				explored.get(step.id)[step.tool] = step.time

				offsets.forEach(offset => {
					let id = step.id + offset
					if (offset == 0) {
						// explore current location by switching tool, then require exploration with the new tool
						tools.forEach(tool => {
							let time = step.time + 7
							if (
								tool != step.tool &&
								this.validateTool(tool, this.map.get(id).type) &&
								explored.get(id)[tool] > time
							) {
								next.push({ id, tool, time })
							}
						})
					} else {
						if (
							id < 0 ||
							id > max ||
							(offset == -1 && id % xmax == xmax - 1) ||
							(offset == 1 && id % xmax == 0)
						) {
							return
						}
						// explore adjacent locations with current tool
						if (this.validateTool(step.tool, this.map.get(id).type)) {
							let time = step.time + 1
							if (!explored.has(id) || explored.get(id)[step.tool] > time) {
								next.push({
									id,
									tool: step.tool,
									time
								})
							}
						}
					}
				})
			}
		}

		return explored.get(this.target.id)[TORCH]
	}


	get part1() {
		let riskLevel = 0

		const xmax = this.map.get('xmax')
		const max = this.map.get('xmax') * this.map.get('ymax')

		for (let id = 0; id <= max; id++) {
			let x = id % xmax
			let y = (id - x) / xmax

			if (x <= this.target.x && y <= this.target.y) {
				riskLevel += this.map.get(id).risk
			} else if (y == this.target.y && x > this.target.x) {
				break
			}
		}

		return riskLevel
	}

	get part2() {
		return this.explore()
	}
}

module.exports = {
	answer: function (input) {
		return new Cave(input)
	},

	part1: 9940,
	part2: 944
}
