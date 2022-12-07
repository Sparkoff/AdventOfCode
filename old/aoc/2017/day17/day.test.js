const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer(3)

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(638)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(1222153)
		})
	})
})
