package mx.com.camacho.mdnotes.constants;

public enum ApplicationIcon {
    ERROR("icons/error.png"),
    ANXIOUS_FACE("icons/anxious.png"),
    FILE("icons/file.png"),
    SAVE("icons/save.png"),
    FOLDER("icons/folder.png"),
    FOLDER_CLOSED("icons/folder-closed.png"),
    FOLDER_OPEN("icons/folder-open.png"),
    LOADING("icons/loading.png"),
    APP_ICON("icons/icon.png"),
    EDIT("icons/edit.png"),
    ADD("icons/add.png"),
    DELETE("icons/delete.png");

    public final String path;
    ApplicationIcon (String path) {
        this.path = path;
    }
}
