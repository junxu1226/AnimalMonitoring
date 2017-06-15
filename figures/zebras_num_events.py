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

n_groups = 5
figure = plt.figure()
ax = figure.add_subplot(111)
index = np.arange(n_groups)

df=pd.read_csv('./zebras/MDP_VOI_1.txt', sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
real=float(df['num_all_events'][1000])

N=6
mdp=[]
for i in range(2,N):
    if i==3:
        continue
    df_MDP_each = pd.read_csv('./zebras/MDP_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    mdp.append(df_MDP_each['num_collected_events'][1000]/real)
    print mdp



N=7
mdpe=[]
for i in range(1,N):
    if i==4 or i==2:
        continue
    df_MDPE_each = pd.read_csv('./zebras/MDP-E_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    mdpe.append(df_MDPE_each['num_collected_events'][1000]/real)
    print mdpe



N=4
greedy=[]
for i in range(1,N):
    df_greedy_each = pd.read_csv('./zebras/Greedy_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    greedy.append(df_greedy_each['num_collected_events'][1000]/real)
    print greedy



N=2
tsp=[]
for i in range(1,N):
    df_tsp_each = pd.read_csv('./zebras/TSP_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    tsp.append(df_tsp_each['num_collected_events'][1000]/real)
    print tsp



N=7
random=[]
for i in range(4,N):
    df_random_each = pd.read_csv('./zebras/Random_VOI_%d.txt' % i, sep=" ", header=None, names=['VOI', 'num_all_events', 'num_collected_events'])
    random.append(df_random_each['num_collected_events'][1000]/real)
    print random



y=[np.mean(mdp), np.mean(mdpe), np.mean(greedy), np.mean(tsp), np.mean(random)]
y_std=[np.std(mdp), np.std(mdpe), np.std(greedy), np.std(tsp), np.std(random)]


patterns = [ "//" , "\\\\" , "||" , "--" , "+" , "xx", "o", "O", ".", "*" ]

width = 0.5
rects = ax.bar(width/2.+index, y, width, color='grey', hatch=patterns[0])
ax.errorbar(width/2.+index + width/2., y, ecolor='k', fmt=None, yerr=y_std, elinewidth=2)
ax.set_xticks(index + width)
ax.set_xticklabels([r'MDP-$prob$', r'MDP-$\varepsilon$', 'Greedy', 'TSP', 'Random'])


plt.ylabel('Percentage of events collected')
# plt.xlabel('Simulation time (rounds)')


# plt.tight_layout()
plt.legend(loc=0)

figure.savefig("zebras_num_events.eps",format='eps', dpi=600)

plt.show()
