class Gantt {
	constructor(instructions, workers, duration) {
		this.tasks = {}
		this.init = []
		this.workers = workers || 5
		this.duration = duration === undefined ? 60 : duration

		this.parseInstructions(instructions)
	}

	parseInstructions(instructions) {
		for (const inst of instructions) {
			let first = inst[5]
			let next = inst[36]

			if (!this.tasks[first]) {
				this.tasks[first] = this.makeTask(first)
			}
			this.tasks[first].childs.push(next)

			if (!this.tasks[next]) {
				this.tasks[next] = this.makeTask(next)
			}
			this.tasks[next].parents.push(first)
		}
		for (const id in this.tasks) {
			if (this.tasks[id].parents.length == 0) {
				this.init.push(id)
			}
		}
	}
	makeTask(id) {
		return {
			id,
			childs: [],
			parents: [],
			duration: this.duration + id.charCodeAt() - 64
		}
	}

	run(simpleMode) {
		let remainings = Object.keys(this.tasks)
		let nexts = this.init.slice(0)
		let inProgress = []
		let dones = []
		let time = 0
		let availableWorkers = simpleMode ? 1 : this.workers

		while (remainings.length) {
			for (let i = 0; i < inProgress.length; i++) {
				if (--inProgress[i].duration == 0) {
					availableWorkers++
					remainings.splice(remainings.indexOf(inProgress[i].id), 1)
					dones.push(inProgress[i].id)

					for (const child of this.tasks[inProgress[i].id].childs) {
						if (nexts.indexOf(child) == -1) {
							let pushNext = true
							for (const parent of this.tasks[child].parents) {
								if (dones.indexOf(parent) == -1) {
									pushNext = false
									break
								}
							}
							if (pushNext) {
								nexts.push(child)
							}
						}
					}

					inProgress.splice(i--, 1)
				}
			}

			while (availableWorkers && nexts.length) {
				nexts.sort()
				availableWorkers--
				inProgress.push(Object.assign({}, this.tasks[nexts.shift()]))

				if (simpleMode) {
					inProgress[inProgress.length - 1].duration = 1
				}
			}

			if (inProgress.length) {
				time++
			}
		}

		return {
			taskCompletion: dones,
			time
		}
	}

	get part1() {
		return this.run('simpleMode').taskCompletion.join('')
	}

	get part2() {
		return this.run().time
	}
}

module.exports = {
	answer: function (input, workers, duration) {
		return new Gantt(input, workers, duration)
	},

	part1: 'BGKDMJCNEQRSTUZWHYLPAFIVXO',
	part2: 941
}