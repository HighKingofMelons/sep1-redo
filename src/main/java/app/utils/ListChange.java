package app.utils;

// When using this just pass it into newValue and put null into oldValue
public class ListChange {
    private final ChangeType changeType; // Either ChangeType.ADD if the object was added or ChangeType.REMOVE if it was removed
    private final Object changedObject;

    public ListChange (ChangeType changeType, Object changedObject) {
        this.changeType = changeType;
        this.changedObject = changedObject;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public Object getChangedObject() {
        return changedObject;
    }
}
