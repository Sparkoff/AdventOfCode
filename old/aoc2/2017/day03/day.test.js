const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer(1).part1).to.equal(0)
		})

		it('test 2', () => {
			expect(Answer(12).part1).to.equal(3)
		})

		it('test 3', () => {
			expect(Answer(23).part1).to.equal(2)
		})

		it('test 4', () => {
			expect(Answer(1024).part1).to.equal(31)
		})
	})

	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer(12).part2).to.equal(23)
		})

		it('test 2', () => {
			expect(Answer(23).part2).to.equal(25)
		})
	})
})
