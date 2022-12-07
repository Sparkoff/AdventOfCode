const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer(1).part1).toBe(0)
		})

		test('test 2', () => {
			expect(Answer(12).part1).toBe(3)
		})

		test('test 3', () => {
			expect(Answer(23).part1).toBe(2)
		})

		test('test 4', () => {
			expect(Answer(1024).part1).toBe(31)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer(12).part2).toBe(23)
		})

		test('test 2', () => {
			expect(Answer(23).part2).toBe(25)
		})
	})
})
