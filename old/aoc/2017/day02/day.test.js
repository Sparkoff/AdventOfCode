const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer(['5 1 9 5']).part1).toBe(8)
		})

		test('test 2', () => {
			expect(Answer(['7 5 3']).part1).toBe(4)
		})

		test('test 3', () => {
			expect(Answer(['2 4 6 8']).part1).toBe(6)
		})

		test('test 4', () => {
			expect(Answer([
				'5 1 9 5',
				'7 5 3',
				'2 4 6 8'
			]).part1).toBe(18)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer(['5 9 2 8']).part2).toBe(4)
		})

		test('test 2', () => {
			expect(Answer(['9 4 7 3']).part2).toBe(3)
		})

		test('test 3', () => {
			expect(Answer(['3 8 6 5']).part2).toBe(2)
		})

		test('test 4', () => {
			expect(Answer([
				'5 9 2 8',
				'9 4 7 3',
				'3 8 6 5'
			]).part2).toBe(9)
		})
	})
})
