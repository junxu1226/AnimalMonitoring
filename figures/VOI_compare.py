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

df1 = pd.read_csv('../Greedy_VOI_1.txt', sep=" ", header=None)
df1.columns=['VOI', 'num_all_events', 'num_collected_events']

df2 = pd.read_csv('VOI_MDP_2.txt', sep=" ", header=None)
df2.columns=['VOI', 'num_all_events', 'num_collected_events']

df3 = pd.read_csv('VOI_MDP_penality.txt', sep=" ", header=None)
df3.columns=['VOI', 'num_all_events', 'num_collected_events']

df4 = pd.read_csv('VOI_MDP_4.txt', sep=" ", header=None)
df4.columns=['VOI', 'num_all_events', 'num_collected_events']

df5 = pd.read_csv('VOI_MDP_5.txt', sep=" ", header=None)
df5.columns=['VOI', 'num_all_events', 'num_collected_events']

df6 = pd.read_csv('TSP_VOI_1.txt', sep=" ", header=None)
df6.columns=['VOI', 'num_all_events', 'num_collected_events']

range = 1000

x=np.arange(0,range)
plt.plot(x, df1['VOI'][:range], label='1')
plt.plot(x, df2['VOI'][:range], label='2')
plt.plot(x, df3['VOI'][:range], label='3')
plt.plot(x, df4['VOI'][:range], label='4')
plt.plot(x, df5['VOI'][:range], label='5')
plt.plot(x, df6['VOI'][:range], label='5')
plt.legend()

plt.show()
