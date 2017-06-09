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

# df1 = pd.read_csv('VOI_MDP_1.txt', sep=" ", header=None)
# df1.columns=['VOI', 'num_all_events', 'num_collected_events']
#
# df2 = pd.read_csv('VOI_MDP_2.txt', sep=" ", header=None)
# df2.columns=['VOI', 'num_all_events', 'num_collected_events']

df3 = pd.read_csv('time_delay_MDP_penality.txt', sep=" ", header=None)
df3.columns=['delay']

df4 = pd.read_csv('time_delay_MDP_4.txt', sep=" ", header=None)
df4.columns=['delay']

df5 = pd.read_csv('time_delay_MDP_5.txt', sep=" ", header=None)
df5.columns=['delay']

df6 = pd.read_csv('TSP_time_delay_1.txt', sep=" ", header=None)
df6.columns=['delay']

range = 200

x=np.arange(0,range)
# plt.plot(x, df1['VOI'][:range], label='1')
# plt.plot(x, df2['VOI'][:range], label='2')
# plt.plot(x, df3['delay'][:range], label='3')
# plt.plot(x, df4['delay'][:range], label='4')
# plt.plot(x, df5['delay'][:range], label='5')

y3 = df3['delay'][:range]
y4 = df4['delay'][:range]
y5 = df5['delay'][:range]
y6 = df6['delay'][:range]
y=[y3,y4,y5,y6]
n_groups = 4

figure = plt.figure()
ax = figure.add_subplot(111)
index = np.arange(n_groups)

r=ax.boxplot(y)
# figure.savefig("delay_compare_zebras.eps",format='eps', dpi=600)
plt.show()
