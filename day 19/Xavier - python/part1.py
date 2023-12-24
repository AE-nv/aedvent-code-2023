import sys

def create_rule_lambda(rule=None):
    return lambda x,m,a,s: [r.split(':')[-1] for r in rule if (":" in r and eval(f"{r[:r.index(':')]}", {'x':x,'m':m,'a':a,'s':s})) or ':' not in r][0]

workflows, parts = [x.splitlines() for x in open(sys.argv[1]).read().split('\n\n')]

flows = dict()

for workflow in workflows:
    name, flow = workflow.split('{')
    rules = flow[:-1].split(',')

    flows[name] = create_rule_lambda(rules)

part_1 = 0

for part in parts:
    part = part[1:-1].split(',')
    part = [int(x.split('=')[-1]) for x in part]
    
    state = 'in'
    while state != 'R' and state != 'A':
       state = flows[state](*part)
    
    if state == 'A':
        part_1 += sum(part)

print(part_1)