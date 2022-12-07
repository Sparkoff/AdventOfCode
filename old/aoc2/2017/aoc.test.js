const expect = require('chai').expect
const utils = require('../utils/utils')
const YEAR = utils.getDirname(__dirname)

describe(`Advent of Code ${YEAR}`, function () {
	this.timeout(0)

	for (let i = 1; i <= utils.getLastDay(YEAR); i++) {

		describe(`day ${String(i).padStart(2, '0')}`, () => {
			let day = null
			
			before(() => new Promise(resolve => {
				utils.getAnswer(YEAR, i)
					.then(d => {
						day = d
						resolve()
					})
			}))

			it('part 1', () => {
				expect(day.answer.part1).to.equal(day.part1)
			})

			it('part 2', () => {
				expect(day.answer.part2).to.equal(day.part2)
			})
		})
		
	}
})