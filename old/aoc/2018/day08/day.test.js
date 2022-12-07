const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer('2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2')

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(138)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(66)
		})
	})
})
