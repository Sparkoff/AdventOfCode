const utils = require('../utils/utils')

const YEAR = utils.getDirname(__dirname)
const DAY = utils.getLastDay(YEAR)
const DAY_LABEL = 'DAY ' + String(DAY).padStart(2, '0')

utils.getAnswer(YEAR, DAY).then(res => {
    console.log(`** ${YEAR} - ${DAY_LABEL} **`)
    console.log('')
    console.log('part1:', res.answer.part1)
    console.log('part2:', res.answer.part2)
})
