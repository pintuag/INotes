package com.inotes.Models;

import android.net.Uri;

public class UploadUri {

    String uri;
    String realpath;
    Uri selectedUri;

    public Uri getSelectedUri() {
        return selectedUri;
    }

    public void setSelectedUri(Uri selectedUri) {
        this.selectedUri = selectedUri;
    }

    public String getRealpath() {
        return realpath;
    }

    public void setRealpath(String realpath) {
        this.realpath = realpath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
