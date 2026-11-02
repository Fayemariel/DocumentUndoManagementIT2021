
package documentundomanagement;

public class ClearDocumentAction extends DocumentAction {

    // Constructor
    public ClearDocumentAction(String previousText) {

        super(previousText, "", "CLEAR DOCUMENT");
    }

    // Override parent method
    @Override
    public String getDescription() {
        return "Cleared the document";
    }
}
