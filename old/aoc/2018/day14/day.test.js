const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('9').part1).toBe('5158916779')
		})

		test('test 2', () => {
			expect(Answer('5').part1).toBe('0124515891')
		})

		test('test 3', () => {
			expect(Answer('18').part1).toBe('9251071085')
		})

		test('test 4', () => {
			expect(Answer('2018').part1).toBe('5941429882')
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer('51589').part2).toBe(9)
		})

		test('test 2', () => {
			expect(Answer('01245').part2).toBe(5)
		})

		test('test 3', () => {
			expect(Answer('92510').part2).toBe(18)
		})

		test('test 4', () => {
			expect(Answer('59414').part2).toBe(2018)
		})
	})
})
