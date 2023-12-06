from math import floor as f,sqrt
u=open("i").readlines()
y=u[1].split()[1:]
def c(t,d):
 k=t*t-4*d
 x=sqrt(k)/2
 m=f(t/2+x)-f(t/2-x)
 return m-1 if(f(x)-x==0)else m
r=1
for i,x in enumerate(u[0].split()[1:]):
 r*=c(int(x),int(y[i]))
print(r)