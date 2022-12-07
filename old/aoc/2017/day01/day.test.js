const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('1122').part1).toBe(3)
		})

		test('test 2', () => {
			expect(Answer('1111').part1).toBe(4)
		})

		test('test 3', () => {
			expect(Answer('1234').part1).toBe(0)
		})

		test('test 4', () => {
			expect(Answer('91212129').part1).toBe(9)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer('1212').part2).toBe(6)
		})

		test('test 2', () => {
			expect(Answer('1221').part2).toBe(0)
		})

		test('test 3', () => {
			expect(Answer('123425').part2).toBe(4)
		})

		test('test 4', () => {
			expect(Answer('123123').part2).toBe(12)
		})

		test('test 5', () => {
			expect(Answer('12131415').part2).toBe(4)
		})
	})
})
