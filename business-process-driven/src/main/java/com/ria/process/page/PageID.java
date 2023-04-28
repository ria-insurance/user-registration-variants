package com.ria.process.page;

public class PageID {
    private final String namespace;
    private final String pageName;

    public PageID(String namespace, String pageName) {
        this.namespace = namespace;
        this.pageName = pageName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPageName() {
        return pageName;
    }
}
