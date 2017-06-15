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


N=7
mdp=0.0
for i in range(2,N):
    if i==3:
        continue
    df_MDP_each = pd.read_csv('./zebras/MDP_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    mdp=mdp+df_MDP_each['VOI']
    print df_MDP_each.tail(5)

mdp=mdp/4.

N=7
mdpe=0.0
for i in range(1,N):
    if i==4:
        continue
    df_MDPE_each = pd.read_csv('./zebras/MDP-E_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    mdpe=mdpe+df_MDPE_each['VOI']
    # print df_MDPE_each.tail(5)

mdpe=mdpe/5.


N=4
greedy=0.0
for i in range(1,N):
    df_greedy_each = pd.read_csv('./zebras/Greedy_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    greedy=greedy+df_greedy_each['VOI']
    # print df_greedy_each.tail(5)

greedy=greedy/3.


N=2
tsp=0.0
for i in range(1,N):
    df_tsp_each = pd.read_csv('./zebras/TSP_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    tsp=tsp+df_tsp_each['VOI']
    # print df_tsp_each.tail(5)



N=7
random=0.0
for i in range(4,N):
    df_random_each = pd.read_csv('./zebras/Random_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    random=random+df_random_each['VOI']
    # print df_random_each.tail(5)

random=random/3.

range = 1000
fig=plt.figure()
x_label = np.arange(0,range)
ax=fig.add_subplot(111)


ax.plot(x_label, mdp[:range], '-', markersize=10, linewidth=2.5, label=r'MDP-$prob$')
ax.plot(x_label, mdpe[:range], '--', markersize=10, linewidth=2.5, label=r'MDP-$\varepsilon$')
ax.plot(x_label, greedy[:range], '-', markersize=8, linewidth=2.5, label='Greedy')
ax.plot(x_label, tsp[:range], markersize=10, linewidth=2.5, label='TSP')
ax.plot(x_label, random[:range], markersize=10, linewidth=2.5, label='Random')

plt.ylabel('Value of information')
plt.xlabel('Simulation time (rounds)')


plt.tight_layout()
plt.legend(loc=0)

fig.savefig("zebras_voi_compare.eps",format='eps', dpi=600)

plt.show()
