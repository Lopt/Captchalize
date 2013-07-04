package cap.db.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class Slot implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    @ManyToOne
    @JoinColumn(name = "functionPipelineId")
    private FunctionPipeline functionPipeline = null;

    private String name       = "";
    private String className  = "";
    private long   foreignKey = 0;

    public Slot() {}

    @Override
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public long getForeignKey() {
        return this.foreignKey;
    }

    public void setForeignKey(final long foreignKey) {
        this.foreignKey = foreignKey;
    }

    public FunctionPipeline getFunctionPipeline() {
        return this.functionPipeline;
    }

    public void setFunctionPipeline(final FunctionPipeline functionPipeline) {
        this.functionPipeline = functionPipeline;
    }

    public <T extends ISlotFunctionData> T getFunctionData() {
        try {
            return Managers.slotManager.getFunctionData(this.className, this.foreignKey);
        } catch (SlotNotFoundException exception) {
            return null;
        }
    }
}
