package tk.amorim.state;

import tk.amorim.model.Allocation;

/**
 * Created by lucas on 01/09/2017.
 */
public class PendingAllocation extends Allocation implements IAllocationState {

    public PendingAllocation(Allocation alloc) {
        super(alloc);
    }

    @Override
    public IAllocationState go() {
        System.out.println("Passando Alocação do Status \"Em processo de alocação\" para \"Alocado\"");
        return new AllocatedAllocation(this);
    }
}