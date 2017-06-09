import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.pylab as pylab
from matplotlib import gridspec
params = {'legend.fontsize': 'x-large',
          'figure.figsize': (10, 10), # width, height
         'axes.labelsize': 'x-large',
         'axes.titlesize':'x-large',
         'xtick.labelsize':'x-large',
         'ytick.labelsize':'x-large'}
pylab.rcParams.update(params)

df = pd.read_csv('Leopard11.txt', sep=" ", header=None)
df.columns=['station', 'node', 'ind', 'year', 'month', 'date', 'hour', 'minute', 'X', 'Y', 'ID']

dfa = df[df['ID'] == 'FA']

id = np.array(df['ID'])
name, num = np.unique(id, return_counts=True)

# In [34]: np.min(dfa['X'])
# Out[34]: 21.823455300000003
#
# In [35]: np.max(dfa['X'])
# Out[35]: 21.908208600000002
#
# dfb = df[df['ID'] == 'FB']
#
# In [38]: np.min(dfb['X'])
# Out[38]: 21.801485600000003
#
# In [39]: np.max(dfb['X'])
# Out[39]: 21.834186399999997

df=df[(df['X'] > 21.80) & (df['X'] < 21.87) & (df['Y'] > 26.95) & (df['Y'] < 27.00)]
x = np.array(df['X'])  #x range  (21.74 - 21.92) 0.18
y = np.array(df['Y'])  # y range (26.90 - 27.04) 0.14

fig=plt.figure()
plt.plot(x,y, 'black')
# plt.grid(True)
# plt.xlim((21.74, 21.92))
# plt.ylim((26.90))
# fig.savefig("figures/real_animal_movement.eps",format='eps', dpi=600)
plt.show()
