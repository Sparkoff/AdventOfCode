class GuardSleep {
	constructor(records) {
		this.guards = {}

		this.parseShift(records)
	}

	parseShift(records) {
		records = records.sort()

		let currentGuard = 0
		let sleep = 0

		for (let record of records) {
			record = record.match(/\[[\d\-]+\s\d+:(\d+)\]\s(.*)/)

			if (record[2].indexOf("Guard #") == 0) {
				currentGuard = record[2].match(/#(\d+)/)[1]

				if (!this.guards[currentGuard]) {
					this.guards[currentGuard] = {
						shift: Array(60).fill(0),
						sum: 0,
						topMinute: 0,
						topMinuteCount: 0
					}
				}
			} else if (record[2] == 'falls asleep') {
				sleep = parseInt(record[1])
			} else {
				for (let minute = sleep; minute < parseInt(record[1]); minute++) {
					this.guards[currentGuard].shift[minute]++
					this.guards[currentGuard].sum++

					if (this.guards[currentGuard].topMinuteCount < this.guards[currentGuard].shift[minute]) {
						this.guards[currentGuard].topMinute = minute
						this.guards[currentGuard].topMinuteCount = this.guards[currentGuard].shift[minute]
					}
				}
			}
		}
	}



	get part1() {
		let asleepGuard = -1
		for (const id in this.guards) {
			if (asleepGuard == -1 || this.guards[asleepGuard].sum < this.guards[id].sum) {
				asleepGuard = id
			}
		}
		return asleepGuard * this.guards[asleepGuard].topMinute
	}

	get part2() {
		let asleepGuard = -1
		for (const id in this.guards) {
			if (asleepGuard == -1 || this.guards[asleepGuard].topMinuteCount < this.guards[id].topMinuteCount) {
				asleepGuard = id
			}
		}
		return asleepGuard * this.guards[asleepGuard].topMinute
	}
}

module.exports = {
	answer: function (input) {
		return new GuardSleep(input)
	},

	part1: 77084,
	part2: 23047
}