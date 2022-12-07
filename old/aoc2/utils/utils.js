module.exports = {
	getAnswer: function (year, day) {
		day = String(day).padStart(2, '0')

		const PATH = this.getBasePath()

		return new Promise(resolve => {
			this.readFileAsArray(`${PATH}/${year}/day${day}/puzzle`).then(input => {
				let dayAnswer = require(`${PATH}/${year}/day${day}/day`)
				resolve({
					answer: dayAnswer.answer(input),
					part1: dayAnswer.part1,
					part2: dayAnswer.part2
				})
			})
		})
	},

	getLastDay: function (year) {
		return Math.max(...require('fs').readdirSync(`${this.getBasePath()}/${year}/`)
			.filter(p => p.match(/day/))
			.map(f => parseInt(f.match(/day(\d+)/)[1]))
		)
	},

	readFileAsArray: function (file) {
		return new Promise(resolve => {
			const rl = require('readline').createInterface({
				input: require('fs').createReadStream(file)
			})

			let buffer = []

			rl.on('line', (line) => {
				buffer.push(line)
			})
				.on('close', () => {
					rl.close()
					resolve(buffer)
				})
		})
	},

	getBasePath: function () {
		return __dirname.split('/').slice(0, -1).join('/')
	},

	getDirname: function (dirPath) {
		return dirPath.split('/').pop()
	}
}
