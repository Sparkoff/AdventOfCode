const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer(['12']).part1).toBe(2)
		})

		test('test 2', () => {
			expect(Answer(['14']).part1).toBe(2)
		})

		test('test 3', () => {
			expect(Answer(['1969']).part1).toBe(654)
		})

		test('test 4', () => {
			expect(Answer(['100756']).part1).toBe(33583)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer(['14']).part2).toBe(2)
		})

		test('test 2', () => {
			expect(Answer(['1969']).part2).toBe(966)
		})

		test('test 3', () => {
			expect(Answer(['100756']).part2).toBe(50346)
		})
	})
})
