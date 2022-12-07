const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'b inc 5 if a > 1',
		'a inc 1 if b < 5',
		'c dec -10 if a >= 1',
		'c inc -20 if c == 10'
	])

	describe('part 1', () => {
		it('test 1', () => {
			expect(answer.part1).to.equal(1)
		})
	})

	describe('part 2', () => {
		it('test 1', () => {
			expect(answer.part2).to.equal(10)
		})
	})
})
