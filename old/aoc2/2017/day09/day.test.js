const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer('{}').part1).to.equal(1)
		})

		it('test 2', () => {
			expect(Answer('{{{}}}').part1).to.equal(6)
		})

		it('test 3', () => {
			expect(Answer('{{},{}}').part1).to.equal(5)
		})

		it('test 4', () => {
			expect(Answer('{{{},{},{{}}}}').part1).to.equal(16)
		})

		it('test 5', () => {
			expect(Answer('{<a>,<a>,<a>,<a>}').part1).to.equal(1)
		})

		it('test 6', () => {
			expect(Answer('{{<ab>},{<ab>},{<ab>},{<ab>}}').part1).to.equal(9)
		})

		it('test 7', () => {
			expect(Answer('{{<!!>},{<!!>},{<!!>},{<!!>}}').part1).to.equal(9)
		})

		it('test 8', () => {
			expect(Answer('{{<a!>},{<a!>},{<a!>},{<ab>}}').part1).to.equal(3)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer('{<>}').part2).to.equal(0)
		})

		it('test 2', () => {
			expect(Answer('{<random characters>}').part2).to.equal(17)
		})

		it('test 3', () => {
			expect(Answer('{<<<<>}').part2).to.equal(3)
		})

		it('test 4', () => {
			expect(Answer('{<{!>}>}').part2).to.equal(2)
		})

		it('test 5', () => {
			expect(Answer('{<!!>}').part2).to.equal(0)
		})

		it('test 6', () => {
			expect(Answer('{<!!!>>}').part2).to.equal(0)
		})

		it('test 7', () => {
			expect(Answer('{<{o"i!a,<{i<a>}').part2).to.equal(10)
		})
	})
})
