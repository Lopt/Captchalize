package cap.gui;

import javax.swing.JLabel;

import cap.ResultPart;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ResultPanelUnknown extends ResultPanel<String>  {
    private static final long serialVersionUID = -858161075567555655L;

    private JLabel text = null;

    public ResultPanelUnknown() {
        this.init();
    }

    @Override
    public void setResultData(ResultPart result) {
    }

    private void init() {
        this.text = new JLabel("Result-type could not be matched.");

        this.add(this.text);
    }
}
