package com.st0x0ef.beyond_earth.common.blocks.entities.machines;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import com.st0x0ef.beyond_earth.common.blocks.entities.machines.power.NamedComponentRegistry;
import com.st0x0ef.beyond_earth.common.capabilities.energy.EnergyStorageExtractOnly;

public abstract class GeneratorBlockEntity extends AbstractMachineBlockEntity {

    public static final String KEY_GENERATING = "generating";

    private IEnergyStorage internalEnergyStorage;
    private IEnergyStorage energyStorage;

    private int generatingCache;

    public GeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void onTickProcessingPost() {
        super.onTickProcessingPost();

        this.setGenerating(this.generatingCache);
        this.generatingCache = 0;
    }

    @Override
    protected void tickProcessing() {
        if (this.canGenerateEnergy() && this.hasSpaceInOutput() && this.consumePowerForOperation() != null) {
            this.generateEnergy();
        }
        this.ejectEnergy();
    }

    protected abstract boolean canGenerateEnergy();

    protected abstract void generateEnergy();

    protected void generateEnergy(int energy) {
        this.generatingCache += this.getInternalEnergyStorage().receiveEnergy(energy, false);
        this.setProcessedInThisTick();
    }

    @Override
    protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
        super.createEnergyStorages(registry);

        this.internalEnergyStorage = this.createGeneratingEnergyStorage();
        this.energyStorage = new EnergyStorageExtractOnly(this.internalEnergyStorage,
                this.internalEnergyStorage.getMaxEnergyStored());
        registry.put(this.internalEnergyStorage);
    }

    protected abstract IEnergyStorage createGeneratingEnergyStorage();

    public static class EjectingTuple {
        public final IEnergyStorage energyStorage;
        public int receivable;
        public int receiving;

        public EjectingTuple(IEnergyStorage energyStorage) {
            this.energyStorage = energyStorage;
        }

        public boolean isFull() {
            return this.receiving >= this.receivable;
        }

    }

    protected void ejectEnergy() {
        IEnergyStorage source = this.getGeneratingEnergyStorage();
        int ejectingEnergy = source.extractEnergy(this.getEjectingExtractEnergy(), true);
        List<IEnergyStorage> sinks = this.findNearEnergyStorages();
        List<EjectingTuple> tuples = new ArrayList<>();

        for (IEnergyStorage sink : sinks) {
            EjectingTuple e = new EjectingTuple(sink);
            e.receivable = sink.receiveEnergy(ejectingEnergy, true);
            e.receiving = 0;
            tuples.add(e);
        }

        ejectingEnergy = this.calculateBalance(ejectingEnergy, tuples);

        for (EjectingTuple tuple : tuples) {
            int extracted = source.extractEnergy(tuple.receiving, false);
            tuple.energyStorage.receiveEnergy(extracted, false);
        }

//              System.out.println(tuples.stream().map(t -> t.receiving).collect(Collectors.toList()).toString() + ", remain=" + ejectingEnergy);
    }

    /**
     * provide remain energy to not full tuples
     * 
     * @param energy
     * @param tuples
     * @return
     */
    private int calculateRemained(int energy, List<EjectingTuple> tuples) {
        int tuplesSize = tuples.size();
        int fullCount = (int) tuples.stream().filter(EjectingTuple::isFull).count();
        int remainCount = tuplesSize - fullCount;

        if (remainCount > 0) {
            int divided = (int) Math.ceil(energy / (double) remainCount);

            for (int i = 0; i < tuplesSize; i++) {
                EjectingTuple tuple = tuples.get(i);

                if (!tuple.isFull()) {
                    if (energy <= 0) {
                        break;
                    }

                    int give = Math.min(divided, energy);
                    tuple.receiving += give;
                    energy -= give;
                    int over = tuple.receiving - tuple.receivable;

                    if (over >= 0) {
                        energy += over;
                    }
                }
            }
        }

        return energy;
    }

    /**
     * calculate possible same receiving until each tuple canRecive
     * 
     * @param energy
     * @param tuples
     * @return
     */
    private int calculateBalance(int energy, List<EjectingTuple> tuples) {
        List<EjectingTuple> orderedReceivables = new ArrayList<>(tuples);
        orderedReceivables.sort(Comparator.comparingInt(o -> o.receivable));

        for (int i = 0; i < orderedReceivables.size(); i++) {
            int amount = i == 0 ? orderedReceivables.get(i).receivable
                    : orderedReceivables.get(i).receivable - orderedReceivables.get(i - 1).receivable;

            if (amount <= 0) {
                continue;
            }

            int amountSum = 0;
            int receiveCount = 0;

            for (EjectingTuple ejectingTuple : tuples) {
                if (!ejectingTuple.isFull()) {
                    amountSum += amount;
                    receiveCount++;
                }
            }

            if (receiveCount == 0) {
                break;
            }

            int give = 0;

            if (amountSum > energy) {
                give = Math.floorDiv(energy, receiveCount);
            } else {
                give = amount;
            }

            for (EjectingTuple tuple : tuples) {
                if (!tuple.isFull()) {
                    tuple.receiving += give;
                    energy -= give;
                    int over = tuple.receiving - tuple.receivable;

                    if (over >= 0) {
                        energy += over;
                    }
                }
            }
        }

        return energy;
    }

    protected List<IEnergyStorage> findNearEnergyStorages() {
        List<IEnergyStorage> energyStorages = new ArrayList<>();

        for (Direction direction : this.getEjectDirections()) {
            BlockEntity blockEntity = this.getLevel().getBlockEntity(this.getBlockPos().offset(direction.getNormal()));

            if (blockEntity != null) {
                LazyOptional<IEnergyStorage> capability = blockEntity.getCapability(ForgeCapabilities.ENERGY,
                        direction.getOpposite());

                if (capability.isPresent()) {
                    energyStorages.add(capability.resolve().get());
                }

            }

        }

        return energyStorages;
    }

    protected int getEjectingExtractEnergy() {
        return this.getGeneratingEnergyStorage().getEnergyStored();
    }

    protected List<Direction> getEjectDirections() {
        return new ArrayList<>();
    }

    @Override
    public boolean hasSpaceInOutput() {
        return this.getInternalEnergyStorage().receiveEnergy(1, true) > 0;
    }

    @Override
    public IEnergyStorage getPrimaryEnergyStorage() {
        return this.getGeneratingEnergyStorage();
    }

    protected IEnergyStorage getInternalEnergyStorage() {
        return this.internalEnergyStorage;
    }

    public IEnergyStorage getGeneratingEnergyStorage() {
        return this.energyStorage;
    }

    public int getGenerating() {
        return this.getPersistentData().getInt(KEY_GENERATING);
    }

    public void setGenerating(int generating) {
        generating = Math.max(generating, 0);

        if (this.getGenerating() != generating) {
            this.getPersistentData().putInt(KEY_GENERATING, generating);
            this.setChanged();
        }
    }

    public double getGeneratingRatio() {
        return (double) this.getGenerating() / (double) this.getMaxGeneration();
    }

    public abstract int getMaxGeneration();
}
