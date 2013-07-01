package cap.db.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
@Entity
public class CaptchaAudio implements ICaptchalizeEntity {

    @Id
    @GeneratedValue
    private long id = 0;

    // Abtastrate etc...

    @Lob
    private byte[] sourceAudio = null;


    public CaptchaAudio() {
    }

    @Override
    public long getId() {
        return this.id;
    }

    public byte[] getSourceAudio() {
        return this.sourceAudio;
    }

    public void setSourceAudio(final byte[] sourceAudio) {
        this.sourceAudio = sourceAudio;
    }
}
