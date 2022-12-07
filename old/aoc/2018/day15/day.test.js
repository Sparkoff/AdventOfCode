const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer1 = Answer([
		'#######',
		'#.G...#',
		'#...EG#',
		'#.#.#G#',
		'#..G#E#',
		'#.....#',
		'#######'
	])
	let answer2 = Answer([
		'#######',
		'#E..EG#',
		'#.#G.E#',
		'#E.##E#',
		'#G..#.#',
		'#..E#.#',
		'#######'
	])
	let answer3 = Answer([
		'#######',
		'#E.G#.#',
		'#.#G..#',
		'#G.#.G#',
		'#G..#.#',
		'#...E.#',
		'#######'
	])
	let answer4 = Answer([
		'#######',
		'#.E...#',
		'#.#..G#',
		'#.###.#',
		'#E#G#G#',
		'#...#G#',
		'#######'
	])
	let answer5 = Answer([
		'#########',
		'#G......#',
		'#.E.#...#',
		'#..##..G#',
		'#...##..#',
		'#...#...#',
		'#.G...G.#',
		'#.....G.#',
		'#########'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer1.part1).toBe(27730)
		})

		test('test 2', () => {
			expect(Answer([
				'#######',
				'#G..#E#',
				'#E#E.E#',
				'#G.##.#',
				'#...#E#',
				'#...E.#',
				'#######'
			]).part1).toBe(36334)
		})

		test('test 3', () => {
			expect(answer2.part1).toBe(39514)
		})

		test('test 4', () => {
			expect(answer3.part1).toBe(27755)
		})

		test('test 5', () => {
			expect(answer4.part1).toBe(28944)
		})

		test('test 6', () => {
			expect(answer5.part1).toBe(18740)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer1.part2).toBe(4988)
		})

		test('test 2', () => {
			expect(answer2.part2).toBe(31284)
		})

		test('test 3', () => {
			expect(answer3.part2).toBe(3478)
		})

		test('test 4', () => {
			expect(answer4.part2).toBe(6474)
		})

		test('test 5', () => {
			expect(answer5.part2).toBe(1140)
		})
	})
})
