package cap.gui;

import javax.swing.JPanel;

import cap.ResultPart;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public abstract class ResultPanel<T> extends JPanel {
    private ResultPart<T> resultData = null;

    public void setResultData(final ResultPart<T> result) {
        this.resultData = result;
    }

    public ResultPart<T> getResultData() {
        return this.resultData;
    }
}
