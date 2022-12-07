const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'Step C must be finished before step A can begin.',
		'Step C must be finished before step F can begin.',
		'Step A must be finished before step B can begin.',
		'Step A must be finished before step D can begin.',
		'Step B must be finished before step E can begin.',
		'Step D must be finished before step E can begin.',
		'Step F must be finished before step E can begin.'
	], 2, 0)

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe('CABDFE')
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(15)
		})
	})
})
