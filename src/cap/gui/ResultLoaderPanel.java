package cap.gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import cap.ResultPart;
import cap.db.jpa.CaptchaAudio;
import cap.db.jpa.CaptchaText;
import cap.img.CaptchaImage;
import cap.img.CompoundImage;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class ResultLoaderPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = -858161075567555651L;

    private LinkedList<ResultPart> results = null;
    private StepIterator<ResultPart> current = null;

    private ResultPanel<CaptchaText> resultPanelText = null; // not used
    private ResultPanel<CaptchaAudio> resultPanelAudio = null; // not used
    private ResultPanel<CompoundImage> resultPanelCompoundImage = null;
    private ResultPanel<CaptchaImage> resultPanelImage = null;
    private ResultPanel currentPanel = null;

    private CardLayout cardLayout = new CardLayout();
    private JPanel content = new JPanel();

    private JButton nextButton = null;
    private JButton prevButton = null;

    public ResultLoaderPanel() {
        this.init();
    }

    public void setResultData(final Collection<ResultPart> parts) {
        assert parts != null;

        this.results = (LinkedList<ResultPart>)parts;
        this.current = new StepIterator(this.results.listIterator());
        this.setResultData(this.current.next());
    }

    public void setResultData(final ResultPart part) {
        assert part != null;
        assert part.getData() != null;

        this.changeContent(part.getData().getClass().getName());
        this.currentPanel.setResultData(part);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command.equals("next")) {
            this.showNext();
        } else if (command.equals("prev")) {
            this.showPrev();
        }
    }

    private void changeContent(String name) {
        this.cardLayout.show(this.content, name);

        if (name.equals("cap.img.CompoundImage")) {
            this.currentPanel = this.resultPanelCompoundImage;
        } else if (name.equals("cap.img.CaptchaImage")) {
            this.currentPanel = this.resultPanelImage;
        }

        this.setNextButtonState();
        this.setPrevButtonState();
    }

    private void showNext() {
        assert this.current != null : "Set result data first.";

        if (this.current.hasNext()) {
            this.setResultData(this.current.next());
        }
    }

    private void showPrev() {
        assert this.current != null : "Set result data first.";

        if (this.current.hasPrevious()) {
            this.setResultData(this.current.previous());
        }
    }

    private void setNextButtonState() {
        if (this.current == null) {
            this.nextButton.setEnabled(false);
        } else {
            this.nextButton.setEnabled(this.current.hasNext());
        }
    }

    private void setPrevButtonState() {
        if (this.current == null) {
            this.prevButton.setEnabled(false);
        } else {
            this.prevButton.setEnabled(this.current.hasPrevious());
        }
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.prevButton = new JButton("<");
        this.prevButton.setActionCommand("prev");
        this.prevButton.addActionListener(this);

        this.nextButton = new JButton(">");
        this.nextButton.setActionCommand("next");
        this.nextButton.addActionListener(this);

        this.resultPanelCompoundImage = new ResultPanelCompoundImage();
        this.resultPanelImage = new ResultPanelImage();

        this.content.setLayout(this.cardLayout);
        this.content.add(this.resultPanelCompoundImage, "cap.img.CompoundImage");
        this.content.add(this.resultPanelImage, "cap.img.CaptchaImage");
        //content.add(this.resultPanelAudio, "Audio");
        //content.add(this.resultPanelText, "Text");

        this.add(this.prevButton);
        this.add(this.content);
        this.add(this.nextButton);
    }
}

class StepIterator<T> {
    private ListIterator<T> iterator = null;

    private boolean next = false;
    private boolean prev = false;

    public StepIterator(ListIterator<T> iterator) {
        this.iterator = iterator;
    }

    public T next() {
        this.next = true;

        if (this.prev) {
            this.prev = false;
            this.iterator.next();
        }

        return this.iterator.next();
    }

    public T previous() {
        this.prev = true;

        if (this.next) {
            this.next = false;
            this.iterator.previous();
        }

        return this.iterator.previous();
    }

    public void add(final T t) {
        this.iterator.add(t);
    }

    public int previousIndex() {
        return this.iterator.previousIndex();
    }

    public int nextIndex() {
        return this.iterator.nextIndex();
    }

    public void remove() {
        this.iterator.remove();
    }

    public void set(final T t) {
        this.iterator.set(t);
    }

    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    public boolean hasPrevious() {
        return this.iterator.hasPrevious();
    }
}