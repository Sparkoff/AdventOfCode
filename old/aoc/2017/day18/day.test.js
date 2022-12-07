const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'set a 1',
				'add a 2',
				'mul a a',
				'mod a 5',
				'snd a',
				'set a 0',
				'rcv a',
				'jgz a -1',
				'set a 1',
				'jgz a -2'
			]).part1).toBe(4)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer([
				'snd 1',
				'snd 2',
				'snd p',
				'rcv a',
				'rcv b',
				'rcv c',
				'rcv d'
			]).part2).toBe(3)
		})
	})
})
