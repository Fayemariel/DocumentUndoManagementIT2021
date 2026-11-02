
package documentundomanagement;

public class DocumentAction {

    private String previousText;
    private String newText;
    private String actionType;

    // Constructor
    public DocumentAction(String previousText,
                          String newText,
                          String actionType) {

        this.previousText = previousText;
        this.newText = newText;
        this.actionType = actionType;
    }

    // Getters
    public String getPreviousText() {
        return previousText;
    }

    public String getNewText() {
        return newText;
    }

    public String getActionType() {
        return actionType;
    }

    // Undo method
    public String undo() {
        return previousText;
    }

    // Action description
    public String getDescription() {
        return actionType + ": " + newText;
    }
}
