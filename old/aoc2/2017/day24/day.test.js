const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'0/2',
		'2/2',
		'2/3',
		'3/4',
		'3/5',
		'0/1',
		'10/1',
		'9/10'
	])

	describe('part 1', () => {
		it('test 1', () => {
			expect(answer.part1).to.equal(31)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(answer.part2).to.equal(19)
		})
	})
})
