package tk.amorim.state;

import tk.amorim.model.Allocation;

/**
 * Created by lucas on 01/09/2017.
 */
public class InProgressAllocation extends Allocation implements IAllocationState {

    public InProgressAllocation(Allocation alloc) {
        super(alloc);
    }
    @Override
    public IAllocationState go() {
        System.out.println("Passando Alocação do Status \"Em Andamento\" para \"Concluído\"");
        return new FinishedAllocation(this);
    }
}
