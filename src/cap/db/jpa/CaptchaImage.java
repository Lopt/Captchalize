package cap.db.jpa;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class CaptchaImage implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "captchaImage")
    private Set<CaptchaSample> captchaSamples;

    @Lob
    private byte[] sourceImageData = null;

    @Lob
    private byte[] destinationImageData = null;

    public CaptchaImage() {}

    @Override
    public long getId() {
        return this.id;
    }

    public byte[] getSourceImageData() {
        return this.sourceImageData;
    }

    public void setSourceImageData(final byte[] sourceImageData) {
        this.sourceImageData = sourceImageData;
    }

    public byte[] getDestinationImageData() {
        return this.destinationImageData;
    }

    public void setDestinationImageData(final byte[] destinationImageData) {
        this.destinationImageData = destinationImageData;
    }
}
