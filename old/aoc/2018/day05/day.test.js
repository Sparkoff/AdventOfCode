const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer('dabAcCaCBAcCcaDA')

	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('aA').part1).toBe(0)
		})

		test('test 2', () => {
			expect(Answer('abBA').part1).toBe(0)
		})

		test('test 3', () => {
			expect(Answer('abAB').part1).toBe(4)
		})

		test('test 4', () => {
			expect(Answer('aabAAB').part1).toBe(6)
		})

		test('test 5', () => {
			expect(answer.part1).toBe(10)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(4)
		})
	})
})
