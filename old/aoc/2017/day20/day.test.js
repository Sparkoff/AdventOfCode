const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>',
				'p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>'
			]).part1).toBe(0)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer([
				'p=<-6,0,0>, v=<3,0,0>, a=<0,0,0>',
				'p=<-4,0,0>, v=<2,0,0>, a=<0,0,0>',
				'p=<-2,0,0>, v=<1,0,0>, a=<0,0,0>',
				'p=<3,0,0>, v=<-1,0,0>, a=<0,0,0>'
			]).part2).toBe(1)
		})
	})
})
