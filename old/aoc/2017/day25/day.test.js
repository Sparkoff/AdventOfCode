const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'Begin in state A.',
				'Perform a diagnostic checksum after 6 steps.',
				'',
				'In state A:',
				'  If the current value is 0:',
				'    - Write the value 1.',
				'    - Move one slot to the right.',
				'    - Continue with state B.',
				'  If the current value is 1:',
				'    - Write the value 0.',
				'    - Move one slot to the left.',
				'    - Continue with state B.',
				'',
				'In state B:',
				'  If the current value is 0:',
				'    - Write the value 1.',
				'    - Move one slot to the left.',
				'    - Continue with state A.',
				'  If the current value is 1:',
				'    - Write the value 1.',
				'    - Move one slot to the right.',
				'    - Continue with state A.'
			]).part1).toBe(3)
		})
	})
})
