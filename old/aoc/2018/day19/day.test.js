const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'#ip 0',
		'seti 5 0 1',
		'seti 6 0 2',
		'addi 0 1 0',
		'addr 1 2 3',
		'setr 1 0 0',
		'seti 8 0 4',
		'seti 9 0 5'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(7)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.sumPrimeNumbers('debug')).toBe(require('./day').part1)
		})
	})
})
