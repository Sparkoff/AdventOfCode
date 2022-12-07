const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'0 <-> 2',
		'1 <-> 1',
		'2 <-> 0, 3, 4',
		'3 <-> 2, 4',
		'4 <-> 2, 3, 6',
		'5 <-> 6',
		'6 <-> 4, 5'
	])

	describe('part 1', () => {
		it('test 1', () => {
			expect(answer.part1).to.equal(6)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(answer.part2).to.equal(2)
		})
	})
})
