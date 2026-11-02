
package documentundomanagement;

public class ActionStack {

    private DocumentAction[] actions;
    private DocumentAction[] history;

    private int top;
    private int historyCount;

    private int capacity;
    private int historyCapacity;

    // Constructor
    public ActionStack() {

        capacity = 100;
        historyCapacity = 500;

        actions = new DocumentAction[capacity];
        history = new DocumentAction[historyCapacity];

        top = -1;
        historyCount = 0;
    }

    // PUSH: Save an action
    public boolean push(DocumentAction action) {

        if (action == null || top == capacity - 1
                || historyCount == historyCapacity) {
            return false;
        }

        top++;
        actions[top] = action;

        // Also save in permanent history
        history[historyCount] = action;
        historyCount++;

        return true;
    }

    // POP: Remove the latest action
    public DocumentAction pop() {

        if (isEmpty()) {
            return null;
        }

        DocumentAction action = actions[top];

        actions[top] = null;
        top--;

        return action;
    }

    // PEEK: View the latest action
    public DocumentAction peek() {

        if (isEmpty()) {
            return null;
        }

        return actions[top];
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Get stack size
    public int size() {
        return top + 1;
    }

    // Get total recorded history
    public int getHistoryCount() {
        return historyCount;
    }

    // Get a recorded action
    public DocumentAction getHistoryAction(int index) {

        if (index < 0 || index >= historyCount) {
            return null;
        }

        return history[index];
    }

    // Search recorded actions using linear search
    public DocumentAction searchHistory(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }

        String search = keyword.toLowerCase();

        for (int i = 0; i < historyCount; i++) {

            DocumentAction action = history[i];

            if (action.getDescription().toLowerCase()
                    .contains(search)
                    || action.getNewText().toLowerCase()
                    .contains(search)
                    || action.getActionType().toLowerCase()
                    .contains(search)) {

                return action;
            }
        }

        return null;
    }
}
