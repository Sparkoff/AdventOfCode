const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer1 = Answer(18)
	let answer2 = Answer(42)

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer1.part1).toBe('33,45')
		})

		test('test 2', () => {
			expect(answer2.part1).toBe('21,61')
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer1.part2).toBe('90,269,16')
		})

		test('test 2', () => {
			expect(answer2.part2).toBe('232,251,12')
		})
	})
})
