const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'pbga (66)',
		'xhth (57)',
		'ebii (61)',
		'havc (66)',
		'ktlj (57)',
		'fwft (72) -> ktlj, cntj, xhth',
		'qoyq (66)',
		'padx (45) -> pbga, havc, qoyq',
		'tknk (41) -> ugml, padx, fwft',
		'jptl (61)',
		'ugml (68) -> gyxo, ebii, jptl',
		'gyxo (61)',
		'cntj (57)'
	])

	describe('part 1', () => {
		it('test 1', () => {
			expect(answer.part1).to.equal('tknk')
		})
	})

	describe('part 2', () => {
		it('test 1', () => {
			expect(answer.part2).to.equal(60)
		})
	})
})
