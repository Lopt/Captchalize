package cap.db.jpa;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class FunctionPipeline implements ICaptchalizeEntity{

    @Id
    @GeneratedValue
    private long id = 0;

    private String name = "";

    @OneToMany
    private Collection<Slot> slots = new ArrayList<Slot>();

    @ManyToMany
    @JoinTable(
        name="CapSys_Pipeline",
        joinColumns        = { @JoinColumn(name="captchaSystemId", referencedColumnName="id") },
        inverseJoinColumns = { @JoinColumn(name="functionPipelineId", referencedColumnName="id") })
    private Collection<CaptchaSystem> captchaSystems = null;

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

    public Collection<Slot> getSlots() {
        return this.slots;
    }

    public void setSlots(final Collection<Slot> slots) {
        this.slots = slots;
    }

    public Collection<CaptchaSystem> getCaptchaSystems() {
        return this.captchaSystems;
    }

    public void setCaptchaSystems(final Collection<CaptchaSystem> captchaSystems) {
        this.captchaSystems = captchaSystems;
    }
}
