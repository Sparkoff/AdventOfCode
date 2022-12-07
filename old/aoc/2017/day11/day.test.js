const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('ne,ne,ne').part1).toBe(3)
		})

		test('test 2', () => {
			expect(Answer('ne,ne,sw,sw').part1).toBe(0)
		})

		test('test 3', () => {
			expect(Answer('ne,ne,s,s').part1).toBe(2)
		})

		test('test 4', () => {
			expect(Answer('se,sw,se,sw,sw').part1).toBe(3)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer('ne,ne,ne').part2).toBe(3)
		})

		test('test 2', () => {
			expect(Answer('ne,ne,sw,sw').part2).toBe(2)
		})

		test('test 3', () => {
			expect(Answer('ne,ne,s,s').part2).toBe(2)
		})

		test('test 4', () => {
			expect(Answer('se,sw,se,sw,sw').part2).toBe(3)
		})
	})
})
