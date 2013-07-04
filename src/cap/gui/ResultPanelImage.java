package cap.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cap.ResultPart;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ResultPanelImage extends ResultPanel<CaptchaImage> {
    private static final long serialVersionUID = -858161075567555653L;

    private JLabel image = null;

    public ResultPanelImage() {
        this.init();
    }

    @Override
    public void setResultData(ResultPart<CaptchaImage> result) {
        assert result != null;
        assert this.image != null;

        super.setResultData(result);

        try {
            this.image.setIcon(new ImageIcon(result.getData().getImage().getBufferedImage()));
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        this.image = new JLabel();

        this.add(this.image);
    }
}
