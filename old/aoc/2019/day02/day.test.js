const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('1,9,10,3,2,3,11,0,99,30,40,50', "debugMode").part1).toBe(3500)
		})

		test('test 2', () => {
			expect(Answer('1,0,0,0,99', "debugMode").part1).toBe(2)
		})

		test('test 3', () => {
			expect(Answer('2,3,0,3,99', "debugMode").part1).toBe(2)
		})

		test('test 4', () => {
			expect(Answer('2,4,4,5,99,0', "debugMode").part1).toBe(2)
		})

		test('test 5', () => {
			expect(Answer('1,1,1,4,99,5,6,0,99', "debugMode").part1).toBe(30)
		})
	})
})
