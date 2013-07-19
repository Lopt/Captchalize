package cap.gui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cap.ResultPart;
import cap.img.CaptchaImage;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ResultPanelCompoundImage extends ResultPanel<CompoundImage> {
    private static final long serialVersionUID = -858161075567555652L;

    public ResultPanelCompoundImage() {
        this.init();
    }

    @Override
    public void setResultData(ResultPart<CompoundImage> result) {
        assert result != null;

        super.setResultData(result);

        this.removeAll();
        for (CaptchaImage image : result.getData().getImages()) {
            JLabel label = new JLabel();

            try {
                label.setIcon(new ImageIcon(image.getImage().getBufferedImage()));
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }

            this.add(label);
        }

        this.updateUI();
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
}
