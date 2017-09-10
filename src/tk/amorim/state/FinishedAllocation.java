package tk.amorim.state;

import tk.amorim.model.Allocation;

/**
 * Created by lucas on 01/09/2017.
 */
public class FinishedAllocation extends Allocation implements IAllocationState {

    FinishedAllocation(Allocation alloc) {
        super(alloc);
    }

    @Override
    public AllocationState getState() {
        return AllocationState.FINISHED;
    }

    @Override
    public IAllocationState go() throws Exception {
        System.out.println("Alocação está em seu status final, não é possível alterar.");
        return this;
    }
}
