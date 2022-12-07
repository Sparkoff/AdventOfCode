const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'/->-\\        ',
				'|   |  /----\\',
				'| /-+--+-\\  |',
				'| | |  | v  |',
				'\\-+-/  \\-+--/',
				'  \\------/   '
			]).part1).toBe('7,3')
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer([
				'/>-<\\  ',
				'|   |  ',
				'| /<+-\\',
				'| | | v',
				'\\>+</ |',
				'  |   ^',
				'  \\<->/'
			]).part2).toBe('6,4')
		})
	})
})
