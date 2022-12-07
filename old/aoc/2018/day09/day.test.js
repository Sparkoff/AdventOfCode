const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer1 = Answer('9 players; last marble is worth 25 points')
	let answer2 = Answer('10 players; last marble is worth 1618 points')
	let answer3 = Answer('13 players; last marble is worth 7999 points')
	let answer4 = Answer('17 players; last marble is worth 1104 points')
	let answer5 = Answer('21 players; last marble is worth 6111 points')
	let answer6 = Answer('30 players; last marble is worth 5807 points')

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer1.part1).toBe(32)
		})

		test('test 2', () => {
			expect(answer2.part1).toBe(8317)
		})

		test('test 3', () => {
			expect(answer3.part1).toBe(146373)
		})

		test('test 4', () => {
			expect(answer4.part1).toBe(2764)
		})

		test('test 5', () => {
			expect(answer5.part1).toBe(54718)
		})

		test('test 6', () => {
			expect(answer6.part1).toBe(37305)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer1.part2).toBe(22563)
		})

		test('test 2', () => {
			expect(answer2.part2).toBe(74765078)
		})

		test('test 3', () => {
			expect(answer3.part2).toBe(1406506154)
		})

		test('test 4', () => {
			expect(answer4.part2).toBe(20548882)
		})

		test('test 5', () => {
			expect(answer5.part2).toBe(507583214)
		})

		test('test 6', () => {
			expect(answer6.part2).toBe(320997431)
		})
	})
})
