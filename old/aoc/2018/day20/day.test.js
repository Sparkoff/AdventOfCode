const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('^WNE$').part1).toBe(3)
		})

		test('test 2', () => {
			expect(Answer('^ENWWW(NEEE|SSE(EE|N))$').part1).toBe(10)
		})

		test('test 3', () => {
			expect(Answer('^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$').part1).toBe(18)
		})

		test('test 4', () => {
			expect(Answer('^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$').part1).toBe(23)
		})

		test('test 5', () => {
			expect(Answer('^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$').part1).toBe(31)
		})
	})
})
