package com.xzbd.mail.utils.mailContentUtil;

public abstract class  AbstractEmailContentItem {
    private BiEmailContentTypeEnum contentType;

    public BiEmailContentTypeEnum getContentType() {
        return contentType;
    }

    public void setContentType(BiEmailContentTypeEnum contentType) {
        this.contentType = contentType;
    }

}
