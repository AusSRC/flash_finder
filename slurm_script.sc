#!/bin/bash -l
#SBATCH --time=12:00:00
#SBATCH --ntasks=20
#SBATCH --ntasks-per-node=20
#SBATCH --job-name=flashfinder_test
#SBATCH --no-requeue
#SBATCH --export=NONE

module load python
module load argparse
module load numpy
module load matplotlib
module load astropy
module unload PrgEnv-cray/6.0.4
module load mpi4py
module unload gcc
module load scipy
module use /group/askap/jallison/multinest/modulefiles
module load multinest
module use /group/askap/jallison/pymultinest/modulefiles
module load pymultinest
module use /group/askap/jallison/corner/modulefiles
module load corner

export MPICH_GNI_MALLOC_FALLBACK=enabled
export FINDER=$ACES/UserScripts/jallison/flash_finder
export MATPLOTLIBRC=$FINDER/matplotlib/

srun --export=ALL --ntasks=20 --ntasks-per-node=20 python $FINDER/flash_finder.py \
--x_units 'optvel' \
--y_units 'mJy' \
--plot_switch \
--out_path $FINDER'/examples/chains' \
--data_path $FINDER'/examples/data/' \
--nlive 500 \
--channel_function 'none' \
--channel_path $FINDER'/examples/data/hipass_channel.dat' \
--plot_restframe 'none' \
--noise_factor '1.00' \
--x0_sigma '200' \
--mmodal \
--mpi_switch \
