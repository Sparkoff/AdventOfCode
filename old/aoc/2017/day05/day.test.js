const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([0, 3, 0, 1, -3])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(5)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(10)
		})
	})
})
