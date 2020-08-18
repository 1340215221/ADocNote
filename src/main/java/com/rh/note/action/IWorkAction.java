package com.rh.note.action;

public interface IWorkAction {
    void openFrame();

    void loadTitleList();

    void openAdocFile();

    public void rename(String componentId) throws Exception;

    void hiddenOrShowTitleList();

    public void generateIncludeBlock(String componentId) throws Exception;

    void saveAllEditContent();

    void initProjectStructure();
}
