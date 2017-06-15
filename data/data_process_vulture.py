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

date_parser = pd.tseries.tools.to_datetime

df=pd.read_csv(f, index_col = False, header=0, usecols=[1, 5, 6, 11],
parse_dates=[0], infer_datetime_format=True)

dfa=df.sort(columns=['timestamp'])

N=30000
x=df['location-lat']
y=df['location-long']
x=x[:N]
y=y[:N]
plt.plot(x,y)
plt.show()



df = pd.read_csv('vultures.csv', index_col = False, header=0, usecols=[2, 3, 4, 11], parse_dates=[0], infer_datetime_format=True, names=['time', 'long', 'lat', 'ID'])

df=df.sort(columns=['time'])



df['time'] = df['time'].apply(lambda x: x.replace(minute=int(np.ceil(x.minute/10) * 10), second=0))
df.drop_duplicates(subset=['time', 'ID'], keep='first', inplace=True)
df=df.reset_index()

dfa=df[200000:]

df.to_csv('v_all.txt', header=None, index=None, sep=" ", mode='a', date_format='%Y %m %d %H %M %S')

# df['month'] = 1
# dfa = df[df['ID'] == 'FA']

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
x = (x - (-19.4)) * 1000.0 / 0.9
y = (y - (15.85)) * 1000.0 / 1.65

df=df[(df['X'] > 21.80) & (df['X'] < 21.87) & (df['Y'] > 26.95) & (df['Y'] < 27.00)]

# df=df.sort(columns=['month', 'date', 'hour'])
# df.to_csv('pandas.txt', header=None, index=None, sep=" ", mode='a')
x = np.array(df['X'])  #x range  (21.74 - 21.92) 0.18
y = np.array(df['Y'])  # y range (26.90 - 27.04) 0.14

fig=plt.figure()
plt.plot(x,y, 'black')
# plt.grid(True)
# plt.xlim((21.74, 21.92))
# plt.ylim((26.90))
# fig.savefig("figures/real_animal_movement.eps",format='eps', dpi=600)




plt.show()
