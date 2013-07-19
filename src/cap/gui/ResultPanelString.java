package cap.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cap.ResultPart;
import cap.img.CaptchaImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ResultPanelString extends ResultPanel<String>  {
    private static final long serialVersionUID = -858161075567555654L;

    private JLabel text = null;

    public ResultPanelString() {
        this.init();
    }

    @Override
    public void setResultData(ResultPart<String> result) {
        assert result != null;
        assert this.text != null;

        super.setResultData(result);

        try {
            this.text.setText(result.getData());
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    private void init() {
        this.text = new JLabel();

        this.add(this.text);
    }
}
