
package documentundomanagement;

public class TextEditAction extends DocumentAction {

    // Constructor
    public TextEditAction(String previousText,
                          String newText) {

        super(previousText, newText, "TEXT EDIT");
    }

    // Override parent method
    @Override
    public String getDescription() {
        return "Edited document text: " + getNewText();
    }
}
