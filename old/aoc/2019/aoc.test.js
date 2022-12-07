const utils = require('../utils/utils')
const YEAR = utils.getDirname(__dirname)

describe(`Advent of Code ${YEAR}`, () => {
	for (let i = 1; i <= utils.getLastDay(YEAR); i++) {
		describe(`day ${String(i).padStart(2, '0')}`, () => {
			let day = null
			beforeAll(() => utils.getAnswer(YEAR, i).then(d => day = d))

			test('part 1', () => {
				expect(day.answer.part1).toBe(day.part1)
			})

			test('part 2', () => {
				expect(day.answer.part2).toBe(day.part2)
			})
		})
	}
})