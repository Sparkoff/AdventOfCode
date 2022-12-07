from re import findall

class Group:
 def __init__(self, rawCode, boost = 0):
  numbers = [int(x) for x in findall(r'-?\d+', rawCode)]
  self.units, self.hp, self.damage, self.initiative = numbers
  self.damage   += boost
  self.weakness  = []
  self.immune    = []
  self.defending = False
  
  if "weak" in rawCode:
   weakS = rawCode.index("weak") + 8
   if "immune" in rawCode and rawCode.index("immune") > rawCode.index("weak"):
    weakE = rawCode.index(";")
   else:
    weakE = rawCode.index(")")
   
   weakStr = rawCode[weakS:weakE]
   self.weakness = weakStr.split(", ")
  
  if "immune" in rawCode:
   immuneS = rawCode.index("immune") + 10
   if "weak" in rawCode and rawCode.index("immune") < rawCode.index("weak"):
    immuneE = rawCode.index(";")
   else: 
    immuneE = rawCode.index(")")
   immuneStr = rawCode[immuneS:immuneE]
   self.immune = immuneStr.split(", ")
  
  words = rawCode.split()
  
  self.damageType = words[words.index("damage")-1]
 
 def effectivePower(self):
  return self.units * self.damage

def calcDamage(attacker, defender):
 if attacker.damageType in defender.immune:
  return 0
 elif attacker.damageType in defender.weakness:
  return 2 * attacker.damage * attacker.units 
 else:
  return attacker.damage * attacker.units 

def sortForDefend(attacker, groups):
 damageTaken = [calcDamage(attacker, defender) for defender in groups]
 effective   = [group.effectivePower() for group in groups]
 inits       = [group.initiative for group in groups]
 
 return [group[3] for group in sorted(zip(damageTaken, effective, inits, groups), key = lambda k: (k[0], k[1], k[2]), reverse = True)]

def sortForAttack(groups):
 effective = [group.effectivePower() for group in groups]
 inits     = [group.initiative for group in groups]
 
 return [group[2] for group in sorted(zip(effective, inits, groups), key = lambda k: (k[0], k[1]), reverse = True)]

def attack(attacker, defender):
 damage = calcDamage(attacker, defender)
 killed = min(defender.units, damage // defender.hp)
 defender.units = defender.units - killed

def fight():
 pairs = []
 
 for attackerGroups, defenderGroups in [(immuneGroups, infectGroups), (infectGroups, immuneGroups)]:
  for attacker in sortForAttack(attackerGroups):
   for defender in sortForDefend(attacker, defenderGroups):
    if not defender.defending and calcDamage(attacker, defender):
     defender.defending = True
     pairs.append([attacker, defender])
     break
  
 pairs.sort(key = lambda k: (k[0].initiative), reverse = True)
 
 return len([attack(*pair) for pair in pairs])

def cleanup():
 for groups in [immuneGroups, infectGroups]:
  marked = []
  for group in groups:
   if not group.units:
    marked.append(group)
   else:
    group.defending = False

  for dead in marked:
   groups.remove(dead)
  
def readFile(name):
 with open(name) as f:
  content = f.readlines()
 return content

input = readFile("day24-input")

### Part 1

immuneGroups, infectGroups = [], []

for i in range(len(input) // 2 - 1):
 immuneGroups.append(Group(input[i+1]))
 infectGroups.append(Group(input[i+13]))

while len(immuneGroups) and len(infectGroups):
 fight()
 cleanup()

result = 0
for group in immuneGroups + infectGroups:
 result += group.units
 
print("Solution 1: " + str(result))

### Part 2

boost = 0

while len(infectGroups):
 boost += 1
 immuneGroups, infectGroups = [], []
 
 for i in range(len(input) // 2 - 1):
  immuneGroups.append(Group(input[i+1], boost))
  infectGroups.append(Group(input[i+13]))
 
 while len(immuneGroups) and len(infectGroups):
  pairs = fight()
  if pairs < 2:
   break
  cleanup()

result = 0

for group in immuneGroups:
 result += group.units

print("Solution 2: " + str(result))
