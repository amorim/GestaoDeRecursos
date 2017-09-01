package tk.amorim.state;

import tk.amorim.model.Allocation;

/**
 * Created by lucas on 01/09/2017.
 */
public class AllocatedAllocation extends Allocation implements IAllocationState {

    public AllocatedAllocation(Allocation alloc) {
        super(alloc);
    }

    @Override
    public IAllocationState go() {
        System.out.println("Passando Alocação do Status \"Alocado\" para \"Em Andamento\"");
        return new InProgressAllocation(this);
    }
}
