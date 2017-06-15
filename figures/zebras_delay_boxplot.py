import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.pylab as pylab
from matplotlib import gridspec
params = {'legend.fontsize': 'xx-large',
          'figure.figsize': (10, 8), # width, height
         'axes.labelsize': 'xx-large',
         'axes.titlesize':'xx-large',
         'xtick.labelsize':'xx-large',
         'ytick.labelsize':'xx-large'}
pylab.rcParams.update(params)



mdp = pd.read_csv('./zebras/MDP_time_delay_2.txt', sep=" ", header=None, names=['delay'])  # 2 or 6
mdpe = pd.read_csv('./zebras/MDP-E_time_delay_6.txt', sep=" ", header=None, names=['delay'])
greedy = pd.read_csv('./zebras/Greedy_time_delay_2.txt', sep=" ", header=None, names=['delay'])
tsp = pd.read_csv('./zebras/TSP_time_delay_1.txt', sep=" ", header=None, names=['delay'])
random = pd.read_csv('./zebras/Random_time_delay_5.txt', sep=" ", header=None, names=['delay'])




range = 151

x=np.arange(0,range)
# plt.plot(x, df1['delay'][:range], label='1')
# plt.plot(x, df2['delay'][:range], label='2')
# plt.plot(x, df3['delay'][:range], label='3')
# plt.plot(x, df4['delay'][:range], label='4')
# plt.plot(x, df5['delay'][:range], label='5')

mdp = mdp[:range]
mdpe = mdpe[:range]
greedy = greedy[:range]
tsp = tsp[:range]
random = random[:range]

y=[mdp,mdpe,greedy,tsp,random]
n_groups = 5

figure = plt.figure()
ax = figure.add_subplot(111)
index = np.arange(n_groups)
bar_width = 0.4
opacity = 0.5

boxprops = dict(linestyle='--', linewidth=3, color='darkgoldenrod')
flierprops = dict(marker='o', markerfacecolor='green', markersize=12,
                  linestyle='none')
medianprops = dict(linestyle='-.', linewidth=2.5, color='firebrick')
meanpointprops = dict(marker='D', markeredgecolor='black',
                      markerfacecolor='firebrick')
meanlineprops = dict(linestyle='--', linewidth=2.5, color='purple')

ax.set_xticklabels([r'MDP-$prob$', r'MDP-$\varepsilon$', 'Greedy',
'TSP', 'Random'])


r=ax.boxplot(y)
plt.setp(r['boxes'], color='black', lw=2)
plt.setp(r['caps'], color='black', lw=2)
plt.setp(r['medians'], lw=2, color='black')
plt.setp(r['fliers'], lw=2, color='black')
plt.setp(r['whiskers'], color='black', lw=2)

plt.ylabel('Time delay (rounds)')
plt.tight_layout()
figure.savefig("zebras_delay_compare.eps",format='eps', dpi=600)
plt.show()
