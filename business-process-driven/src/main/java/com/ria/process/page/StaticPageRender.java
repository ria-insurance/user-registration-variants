package com.ria.process.page;


public class StaticPageRender implements PageRenderer {

    private final String url;

    public StaticPageRender(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public PageRendererType getRendererType() {
        return PageRendererType.STATIC_PAGE;
    }
}
